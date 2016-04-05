package locationserver.database;

import locationserver.model.Device;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            conn = DriverManager.getConnection(url, "postgres", "renate");
            s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Device> getDevices() {

        List<Device> devices = new ArrayList<>();
        try {


            ResultSet r = s.executeQuery("SELECT * FROM devices");

            while (r.next()) {
                Device device = new Device();
                device.setId(r.getInt("id"));
                device.setPositionId(r.getInt("positionId"));
                device.setName(r.getString("name"));
                device.setUniqueId(r.getString("uniqueId"));
                device.setStatus(r.getString("status"));
                device.setLastUpdate(r.getTimestamp("lastUpdate"));
                devices.add(device);
            }

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return devices;
    }
}
