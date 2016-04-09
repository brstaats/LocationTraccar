package locationserver.database;

import locationserver.model.Device;
import locationserver.model.Job;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<Job> getJobs() {

        List<Job> jobs = new ArrayList<>();
        try {
            ResultSet r = s.executeQuery("SELECT * FROM jobs");

            while (r.next()) {
                Job job = new Job();
                setJobParameters(job, r);
                jobs.add(job);
            }

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return jobs;
    }

    public Job getJob(String uuid) {

        Job job = new Job();
        try {
            ResultSet r = s.executeQuery("SELECT * FROM jobs WHERE uuid='" + UUID.fromString(uuid) + "'");

            while (r.next()) {
                setJobParameters(job, r);
            }

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return job;
    }

    public void updateJob(String uuid) {

        Job job = new Job();
        try {
            ResultSet r = s.executeQuery("UPDATE Employee set salary = :salary WHERE id = :employee_id");

            while (r.next()) {
                setJobParameters(job, r);
            }

            s.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setJobParameters(Job job, ResultSet r) throws SQLException {
        job.setUuid(UUID.fromString(r.getString("uuid")));
        job.setClient_uuid(r.getInt("client_uuid"));
        job.setName(r.getString("name"));
        job.setStartDate(r.getTimestamp("startDate"));
        job.setEndDate(r.getTimestamp("endDate"));
        job.setOuterregion_meter(r.getInt("outerregion_meter"));
    }
}
