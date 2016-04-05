package locationserver.database;

import locationserver.response.JSONMessage;
import locationserver.response.Response;
import locationserver.trackers.Location;
import locationserver.trackers.Tracker;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */
public class DatabaseConnection {

    java.sql.Connection conn;
    String url;
    Statement s;

    public DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            url = "jdbc:postgresql://localhost:5432/traccar";
            conn = DriverManager.getConnection(url, "postgres", "123456");
            s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Tracker> getTrackers(int client_id) {

        ResultSet r = null;


        List<Tracker> trackers = new ArrayList<>();
        try {


            r = s.executeQuery("SELECT tr.*, loc.accuracy, loc.accuracyheight, ST_AsText(loc.geom) AS geom FROM trackers tr JOIN locations loc ON tr.loc_uuid=loc.uuid WHERE tr.client_uuid='" + client_id + "'");

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

        }
       return new ArrayList<>();
    }
}
