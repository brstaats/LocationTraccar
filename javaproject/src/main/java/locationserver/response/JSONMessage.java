package locationserver.response;

import locationserver.trackers.Client;
import locationserver.trackers.Job;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.postgis.Point;
import locationserver.trackers.Tracker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by biezenj on 8-12-2015.
 */
public class JSONMessage {

    Response response;

    public JSONMessage(Response response) {
        this.response = response;
    }


    public JSONObject getJSONMessage() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("responseCode", response.getCode());
            jsonObject.put("responseMessage", response.getMessage());


            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray appendTrackers(ArrayList<Tracker> trackers) {
        JSONArray array = new JSONArray();
        try {

            for (Tracker tracker : trackers) {

                LinkedHashMap<String, String> map = tracker.getMap();
                JSONObject object = new JSONObject();

                for (Map.Entry<String, String> entry : map.entrySet()) {


                    if (entry.getKey().equals("loc_uuid")) {


                        JSONObject loc = new JSONObject();

                        String newpoint = tracker.getLocation().getLocation().replace(" Z ", "");

                        Point point = new Point(newpoint);
                        loc.put("lat", point.getX());
                        loc.put("lng", point.getY());
                        loc.put("alt", point.getZ());
                        loc.put("acc", Double.parseDouble(tracker.getLocation().getAccuracy()));
                        loc.put("accheight", Double.parseDouble(tracker.getLocation().getAccuracyHeight()));
                        object.put("location", loc);

                    } else {

                        object.put(entry.getKey(), entry.getValue());
                    }


                }
                array.put(object);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static JSONArray appendJobs(ArrayList<Job> jobs) {
        JSONArray array = new JSONArray();
        try {
            for (Job job : jobs) {


                HashMap<String, String> map = job.getMap();
                JSONObject object = new JSONObject();

                for (Map.Entry<String, String> entry : map.entrySet()) {


                    if (entry.getKey().equals("uuid")) {

                        JSONObject intobject = new JSONObject();
                        intobject.put("uuid", entry.getValue());
                        intobject.put("uuid", job.getInterval().getUuid());
                        intobject.put("enddate", job.getInterval().getEnddate());
                        intobject.put("innerregion_meter", job.getInterval().getInnerregion_meter());
                        intobject.put("outerregion_meterclose", job.getInterval().getOuterregion_meter());
                        intobject.put("priority", job.getInterval().getPriority());
                        intobject.put("name", job.getInterval().getName());
                        intobject.put("latitude", job.getInterval().getLatitude());
                        intobject.put("finished", job.getInterval().getFinished());
                        intobject.put("longitude", job.getInterval().getLongitude());
                        intobject.put("altitude", job.getInterval().getAltitude());

                        object.put("interval", intobject);
                    } else {
                        object.put(entry.getKey(), entry.getValue());
                    }

                }

                array.put(object);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static JSONArray appendClients(ArrayList<Client> clients) {
        JSONArray array = new JSONArray();
        try {
            for (Client client : clients) {


                HashMap<String, String> map = client.getMap();
                JSONObject object = new JSONObject();

                for (Map.Entry<String, String> entry : map.entrySet()) {

                        object.put(entry.getKey(), entry.getValue());


                }

                array.put(object);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }


}
