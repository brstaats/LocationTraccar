package locationserver.model;

/**
 * Created by biezenj on 5-4-2016.
 */

import java.util.UUID;
public class Job {

    UUID uuid;
    String name, address;
    int client_uuid, outerregion_meter,innerregion_meter, priority;
    boolean finished;
    double latitude, longitude, altitude;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClient_uuid() {
        return client_uuid;
    }

    public void setClient_uuid(int client_uuid) {
        this.client_uuid = client_uuid;
    }

    public int getOuterregion_meter() {
        return outerregion_meter;
    }

    public void setOuterregion_meter(int outerregion_meter) {
        this.outerregion_meter = outerregion_meter;
    }

    public int getInnerregion_meter() {
        return innerregion_meter;
    }

    public void setInnerregion_meter(int innerregion_meter) {
        this.innerregion_meter = innerregion_meter;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
