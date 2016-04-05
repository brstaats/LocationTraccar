package locationserver.model;

import java.sql.Timestamp;

/**
 * Created by biezenj on 5-4-2016.
 */
public class Position {

    int id, deviceId, interval;
    String protocol, address, attributes;
    Timestamp serverTime, deviceTime, fixTime;
    boolean valid;
    double latitude, longitude, altitude, speeds,course;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Timestamp getServerTime() {
        return serverTime;
    }

    public void setServerTime(Timestamp serverTime) {
        this.serverTime = serverTime;
    }

    public Timestamp getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(Timestamp deviceTime) {
        this.deviceTime = deviceTime;
    }

    public Timestamp getFixTime() {
        return fixTime;
    }

    public void setFixTime(Timestamp fixTime) {
        this.fixTime = fixTime;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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

    public double getSpeeds() {
        return speeds;
    }

    public void setSpeeds(double speeds) {
        this.speeds = speeds;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }
}
