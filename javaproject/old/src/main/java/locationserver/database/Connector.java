package locationserver.database;


import locationserver.license.AuthCode;
import locationserver.license.License;
import locationserver.trackers.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgis.Point;
import locationserver.response.JSONMessage;
import locationserver.response.Response;

import javax.xml.transform.Result;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by biezenj on 15-12-2015.
 */
public class Connector {

    java.sql.Connection conn;
    String url;
    Statement s;

    public Connector() {
        try {
            Class.forName("org.postgresql.Driver");
            url = "jdbc:postgresql://localhost:5432/traccar";
            conn = DriverManager.getConnection(url, "postgres", "123456");
            s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getLicense(String licensenumber) {
        boolean result = false;

        try {

            ResultSet r = s.executeQuery("select uuid, max_trackers, in_use,valid from licenses WHERE uuid = '" + licensenumber + "'");
            result = (r.next()) ? true : false;
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean getPin(String pin) {

        boolean result = false;
        try {

            ResultSet r = s.executeQuery("SELECT pincode FROM clients WHERE pincode = " + pin + "");
            result = (r.next()) ? true : false;
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public JSONObject setTracker(HashMap<String, String> parameters) {

        String uuid = parameters.get("uuid");
        String name = parameters.get("name");
        String active = parameters.get("active");


        try {

            s.execute("UPDATE trackers SET name = '" + name + "', active= '" + active + "' WHERE uuid = '" + uuid + "'");
            if (s.getUpdateCount() == 0) {
                return new JSONMessage(Response.NO_ROWS_UPDATED).getJSONMessage();
            }
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        return new JSONMessage(Response.SUCCES).getJSONMessage();
    }

    public JSONObject setTrackerState(String uuid, String active) {


        try {

            s.execute("UPDATE trackers SET active= " + active + " WHERE uuid = '" + uuid + "'");
            if (s.getUpdateCount() == 0) {
                return new JSONMessage(Response.NO_ROWS_UPDATED).getJSONMessage();
            }
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        return new JSONMessage(Response.SUCCES).getJSONMessage();
    }

    public JSONObject getTrackers(HashMap<String, String> params) {

        String job_uuid = null;
        String client_uuid;
        ResultSet r = null;


        ArrayList<Tracker> trackers = new ArrayList<>();
        try {

            if (params.get("job_uuid").length() != 0) {
                job_uuid = params.get("job_uuid");
                r = s.executeQuery("SELECT tr.*, loc.accuracy, loc.accuracyheight,  ST_AsText(loc.geom) as geom FROM jobtrackerlink a JOIN trackers tr ON a.tracker_uuid=tr.uuid JOIN locations loc ON tr.loc_uuid=loc.uuid WHERE a.job_uuid='" + job_uuid + "'");
            } else if (params.get("client_uuid").length() != 0) {
                client_uuid = params.get("client_uuid");
                r = s.executeQuery("SELECT tr.*, loc.accuracy, loc.accuracyheight, ST_AsText(loc.geom) AS geom FROM trackers tr JOIN locations loc ON tr.loc_uuid=loc.uuid WHERE tr.client_uuid='" + client_uuid + "'");
            }

            while (r.next()) {
                LinkedHashMap<String, String> results = new LinkedHashMap<>();

                for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {

                    switch (r.getMetaData().getColumnName(i)) {
                        case "accuracy":
                        case "accuracyheight":
                        case "geom":
                            break;
                        default:
                            results.put(r.getMetaData().getColumnName(i), r.getString(i));

                            break;
                    }
                }

                Location location = new Location(r.getString("accuracy"), r.getString("accuracyheight"), r.getString("geom"));
                Tracker tracker = new Tracker(location, results);
                trackers.add(tracker);

            }

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        try {
            object.put("requestID", job_uuid);
            object.put("trackers", JSONMessage.appendTrackers(trackers));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    public JSONObject getInterval(HashMap<String, String> params) {

        String timestamp = params.get("timestamp");
        String deviceID = params.get("deviceId");
        ResultSet r = null;

        int interval = 0;
        try {

            r = s.executeQuery("SELECT interval FROM positions WHERE " + '"'+ "serverTime" + '"'+ " = " +"'"+timestamp + "'::timestamp AND " + '"' + "deviceId" + '"' + " = " + deviceID);

            while (r.next()) {
                for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {
                    interval = r.getInt(i);
                }
            }

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        try {
            object.put("interval", interval);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
    public JSONObject createJob(HashMap<String, String> parameters) {

        String name = parameters.get("name");
        String client_uuid = parameters.get("client_uuid");
        String start_date = parameters.get("startdate");
        String end_date = parameters.get("enddate");
        String start_time = parameters.get("starttime");
        String end_time = parameters.get("endtime");
        int far = Integer.parseInt(parameters.get("far"));
        int close = Integer.parseInt(parameters.get("close"));
        int far_still = Integer.parseInt(parameters.get("far_still"));
        int close_still = Integer.parseInt(parameters.get("close_still"));

        try {


            conn.setAutoCommit(false);

            //create UUID
            UUID uuid = UUID.randomUUID();
            UUID interval_uuid = UUID.randomUUID();

            s.execute("INSERT INTO jobs(uuid,name,client_uuid,startdate,enddate,starttime,endtime,interval_uuid) VALUES('" + uuid + "', '" + name + "', '" + client_uuid + "', '" + start_date + "','" + end_date + "', '" + start_time + "', '" + end_time + "', '" + interval_uuid + "')");
            s.execute("INSERT INTO intervals(uuid, job_uuid, far, close, far_still, close_still) VALUES('" + interval_uuid + "', '" + uuid + "', '" + far + "', '" + close + "', '" + far_still + "', '" + close_still + "')");


            conn.commit();
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject createClient(HashMap<String, String> parameters) {

        String name = parameters.get("name");
        String max_trackers = parameters.get("max_trackers");
        String auth_code = AuthCode.getAuthCode();
        String pincode = AuthCode.getPinCode();


        try {

            conn.setAutoCommit(false);

            //create UUID
            UUID uuid = UUID.randomUUID();
            UUID license_uuid = UUID.randomUUID();

            s.execute("INSERT INTO clients(uuid, name, license_uuid, auth_code, pincode) VALUES('" + uuid + "', '" + name + "', '" + license_uuid + "', '" + auth_code + "', " + pincode + ")");
            s.execute("INSERT INTO licenses(uuid, max_trackers, in_use, valid) VALUES('" + license_uuid + "', '" + max_trackers + "', 0, true)");
            conn.commit();
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }


        try {
            JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage().put("Auth_code", auth_code);
            return object.put("pincode", pincode);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONMessage(Response.JSON_ERROR).getJSONMessage();
        }


    }

    public JSONObject createJobTrackerLink(HashMap<String, String> parameters) {

        String job_uuid = parameters.get("job_uuid");
        String tracker_uuid = parameters.get("tracker_uuid");

        try {


            s.execute("INSERT INTO jobtrackerlink(job_uuid, tracker_uuid) VALUES('" + job_uuid + "', '" + tracker_uuid + "')");

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        return new JSONMessage(Response.SUCCES).getJSONMessage();

    }

    public ArrayList<String> getClient(HashMap<String, String> parameters) {

        String auth_code = parameters.get("auth_code");
        String pincode = parameters.get("pincode");
        ArrayList<String> client = null;
        try {


            ResultSet r = s.executeQuery("SELECT uuid, name FROM clients WHERE auth_code = '" + auth_code + "' AND pincode= " + pincode + "");
            if (r.next()) {
                client = new ArrayList<>();
                client.add(r.getString("uuid"));
                client.add(r.getString("name"));
            } else

                s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;

    }

    public ArrayList<String> getTracker(HashMap<String, String> parameters) {

        String name = parameters.get("name");

        ArrayList<String> tracker = null;
        try {


            ResultSet r = s.executeQuery("SELECT name, uuid FROM trackers WHERE name = '" + name + "'");
            if (r.next()) {
                tracker = new ArrayList<>();
                tracker.add(r.getString("uuid"));
                tracker.add(r.getString("name"));
            } else

                s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tracker;

    }

    public JSONObject setInterval(HashMap<String, String> parameters) {


        String job_uuid = parameters.get("job_uuid");
        int far = Integer.parseInt(parameters.get("far"));
        int close = Integer.parseInt(parameters.get("close"));
        int far_still = Integer.parseInt(parameters.get("far_still"));
        int close_still = Integer.parseInt(parameters.get("close_still"));


        try {

        /*    //select admins for job
            ResultSet r = s.executeQuery("SELECT * FROM admins WHERE job_uuid='" + job_uuid + "' AND tracker_uuid='" + uuid + "'");

            if (!r.next()) {
                return new JSONMessage(Response.NO_ADMIN_RIGHTS).getJSONMessage();
            }
*/
            s.execute("UPDATE intervals SET far=" + far + ", close=" + close + ", close_still=" + close_still + ", far_still=" + far_still + " WHERE job_uuid='" + job_uuid + "'");

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject setLocation(HashMap<String, String> parameters) {


        String uuid = parameters.get("uuid");
        String lat = parameters.get("lat");
        String lng = parameters.get("lng");
        String alt = parameters.get("alt");
        String acc = parameters.get("acc");
        String accalt = parameters.get("accalt");


        System.out.println(uuid + ", " + lat + ", " + lng + ", " + alt + ", " + acc + ", " + accalt + ", ");

        Point point = new Point(Double.parseDouble(lat), Double.parseDouble(lng), Double.parseDouble(alt));
        point.setSrid(4326);

        try {

            s.execute("UPDATE locations SET accuracy=" + acc + ", accuracyheight=" + accalt + ", geom='" + point.toString() + "' WHERE tracker_uuid='" + uuid + "'");

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject insertLocation(HashMap<String, String> parameters) {


        String uuid = parameters.get("id");
        String devid = parameters.get("devid");
        String timest = parameters.get("timestamp");
        String lng = parameters.get("lng");
        String lat = parameters.get("lat");
        String alt = parameters.get("alt");
        String acc = parameters.get("acc");
        String accalt = parameters.get("accalt");
        String speed = parameters.get("speed");
        String interval = parameters.get("interval");


        System.out.println(uuid + ", " + lat + ", " + lng + ", " + alt + ", " + acc + ", " + accalt + ", ");

        //Point point = new Point(Double.parseDouble(lat), Double.parseDouble(lng), Double.parseDouble(alt));
        //point.setSrid(4326);

        try {
            String protocol = "osmand";
            String adress = "whee";
            Integer course = 1;
            String attributes = "whaa";

            //Random random = new Random();
            //int n = random.nextInt(100) +1;
            //Integer interval = n * 1000;


            s.execute("INSERT INTO positions VALUES ("+ uuid + ",'" + protocol + "'," + devid +",'"+ timest + "'::timestamp,'" + timest + "'::timestamp,'" + timest + "'::timestamp,"+"true,"+ lat + "," + lng + "," + alt + "," + speed + "," + course + ",'" + adress + "','" + attributes + "'," + interval + ");");


            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject setJob(HashMap<String, String> parameters) {

        String uuid = parameters.get("uuid");
        String name = parameters.get("name");
        String startdate = parameters.get("startdate");
        String enddate = parameters.get("enddate");
        String starttime = parameters.get("starttime");
        String endtime = parameters.get("endtime");

        try {

            s.execute("UPDATE jobs SET name='" + name + "', startdate='" + startdate + "', enddate='" + enddate + "', starttime='" + starttime + "', endtime='" + endtime + "' WHERE uuid='" + uuid + "'");

            if (s.getUpdateCount() == 0) {
                return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
            }
            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }

        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject createTracker(HashMap<String, String> parameters) {


        String auth_code = parameters.get("auth_code");
        String name = parameters.get("name");
        UUID loc_uuid = UUID.randomUUID();
        UUID uuid = UUID.randomUUID();
        JSONObject object;
        String client_uuid = null;
        int in_use = 0;
        int max_trackers = 0;
        boolean valid = false;
        String license_uuid = null;

        try {


            ResultSet r = s.executeQuery("SELECT a.uuid, b.uuid AS license_uuid, b.max_trackers, b.in_use, b.valid FROM clients a JOIN licenses b ON a.license_uuid=b.uuid WHERE a.auth_code= '" + auth_code + "'");

            while (r.next()) {
                client_uuid = r.getString("uuid");
                in_use = r.getInt("in_use");
                valid = r.getBoolean("valid");
                license_uuid = r.getString("license_uuid");
                max_trackers = r.getInt("max_trackers");
            }

            if (client_uuid == null) {
                return new JSONMessage(Response.WRONG_AUTHENTICATION).getJSONMessage();
            }

            if (in_use >= max_trackers || valid == false) {
                return new JSONMessage(Response.MAX_TRACKERS_EXCEEDED).getJSONMessage();
            }
            conn.setAutoCommit(false);
            s.execute("INSERT INTO trackers(uuid, name, loc_uuid, active, client_uuid) VALUES('" + uuid + "', '" + name + "', '" + loc_uuid + "', false, '" + client_uuid + "')");
            s.execute("INSERT INTO locations(uuid, tracker_uuid, accuracy,accuracyheight,geom) VALUES('" + loc_uuid + "', '" + uuid + "', 0 , 0 , ST_GeomFromText('POINT(0 0 0)', 4326))");

            if (in_use + 1 > max_trackers) {
                valid = false;
            }
            s.execute("UPDATE licenses SET in_use= " + (in_use + 1) + ", valid=" + valid + " WHERE uuid = '" + license_uuid + "'");
            conn.commit();
            object = new JSONMessage(Response.SUCCES).getJSONMessage().put("trackerID", uuid);


            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }


        return object;

    }

    public JSONObject getJobs(HashMap<String, String> parameters) {

        String client_uuid = parameters.get("client_uuid");
        String tracker_uuid = parameters.get("tracker_uuid");

        ArrayList<Job> jobs = new ArrayList<>();

        try {
            ResultSet r;
            r=s.executeQuery("select uuid, enddate, outerregion_meter, innerregion_meter, st_astext(geomtask) geomtask, priority from jobs j join jobtrackerlink jo ON j.uuid = jo.job_uuid where device_uuid = " + tracker_uuid + " and + client_uuid = " + client_uuid);


//            if (client_uuid != null) {
//                r = s.executeQuery("SELECT j.*, i.* FROM jobs j JOIN intervals i ON j.uuid=i.job_uuid WHERE j.client_uuid='" + client_uuid + "'");
//            } else {
//                r = s.executeQuery("select j.*, i.* from jobs j JOIN intervals i ON j.uuid=i.job_uuid JOIN jobtrackerlink jl ON jl.job_uuid =j.uuid WHERE jl.tracker_uuid='"+tracker_uuid+"'");
//            }

            while (r.next()) {
                HashMap<String, String> results = new HashMap<>();

                for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {

                    switch (r.getMetaData().getColumnName(i)) {
                        case "name":
                        case "client_uuid":
                        case "startdate":
                        case "enddate":
                        case "geomtask":
                        case "innerregion_meter":
                        case "outerregion_meter":
                        case "priority":
                        default:
                            results.put(r.getMetaData().getColumnName(i), r.getString(i));
                    }
                }

                Interval interval = new Interval(r.getString("uuid"), r.getString("geomtask"), r.getString("enddate"), r.getString("outerregion_meter"), r.getString("innerregion_meter"), r.getString("priority"));
                Job job = new Job(interval, results);
                jobs.add(job);
            }

            s.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();
        try {
            object.put("requestClientID", client_uuid);
            object.put("jobs", JSONMessage.appendJobs(jobs));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONMessage(Response.JSON_ERROR).getJSONMessage();

        }

        return object;

    }

    public JSONObject getClients() {

        ArrayList<Client> clients = new ArrayList<>();

        try {


            ResultSet r = s.executeQuery("SELECT c.*, l.max_trackers, l.in_use, l.valid FROM clients c JOIN licenses l ON c.license_uuid=l.uuid;");


            while (r.next()) {
                LinkedHashMap<String, String> results = new LinkedHashMap<>();

                for (int i = 1; i <= r.getMetaData().getColumnCount(); i++) {
                    results.put(r.getMetaData().getColumnName(i), r.getString(i));
                }


                Client client = new Client(results);
                clients.add(client);

            }


            s.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();
        try {

            object.put("clients", JSONMessage.appendClients(clients));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONMessage(Response.JSON_ERROR).getJSONMessage();

        }

        return object;

    }

    public JSONObject deleteClient(HashMap<String, String> parameters) {

        String client_uuid = parameters.get("client_uuid");

        try {
            s.execute("DELETE FROM clients WHERE uuid='" + client_uuid + "'");
            s.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject deleteTracker(HashMap<String, String> parameters) {

        String tracker_uuid = parameters.get("tracker_uuid");
        String job_uuid = parameters.get("job_uuid");
        String delete = parameters.get("delete");

        try {
            if (delete.equals("link")) {
                s.execute("DELETE FROM jobtrackerlink WHERE tracker_uuid='" + tracker_uuid + "' AND job_uuid='" + job_uuid + "'");
            } else {
                s.execute("DELETE FROM trackers WHERE uuid='" + tracker_uuid + "'");
                s.execute("SELECT * FROM jobtrackerlink WHERE tracker_uuid='" + tracker_uuid + "' AND job_uuid='" + job_uuid + "'");
                if (s.getResultSet().next()) {
                    s.execute("DELETE FROM jobtrackerlink WHERE tracker_uuid='" + tracker_uuid + "' AND job_uuid='" + job_uuid + "'");
                }
            }
            if (s.getUpdateCount() == 0) {
                return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
            }
            s.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject deleteJob(HashMap<String, String> parameters) {

        String job_uuid = parameters.get("job_uuid");

        try {
            conn.setAutoCommit(false);
            s.execute("DELETE FROM jobs WHERE uuid='" + job_uuid + "'");
            s.execute("DELETE FROM jobtrackerlink WHERE job_uuid='" + job_uuid + "'");
            conn.commit();
            s.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }

    public JSONObject setClient(HashMap<String, String[]> parameters) {

        try {
            if (parameters.get("name") != null && parameters.get("client_uuid") != null) {
                System.out.println(parameters.get("name")[0] + ", " + parameters.get("client_uuid")[0]);
                s.execute("UPDATE clients SET name='" + parameters.get("name")[0] + "' WHERE uuid='" + parameters.get("client_uuid")[0] + "'");
            } else if (parameters.get("license_uuid") != null && parameters.get("max_trackers") != null) {
                s.execute("UPDATE licenses SET max_trackers=" + parameters.get("max_trackers")[0] + "WHERE uuid='" + parameters.get("license_uuid")[0] + "'");
            } else {
                return new JSONMessage(Response.MISSING_ARGUMENTS).getJSONMessage();
            }

            s.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONMessage(Response.SQL_EXCEPTION).getJSONMessage();
        }
        JSONObject object = new JSONMessage(Response.SUCCES).getJSONMessage();

        return object;

    }


}


