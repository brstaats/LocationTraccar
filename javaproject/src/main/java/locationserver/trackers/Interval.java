package locationserver.trackers;

/**
 * Created by biezenj on 17-12-2015.
 */
public class Interval {

    String uuid, geomtask, enddate, innerregion_meter, outerregion_meter, priority, name, latitude, finished, longitude, altitude;

    public Interval(String uuid, String enddate, String innerregion_meter, String outerregion_meter, String priority, String name, String latitude, String finished, String longitude, String altitude) {
        this.uuid = uuid;
        this.enddate = enddate;
        this.innerregion_meter = innerregion_meter;
        this.outerregion_meter = outerregion_meter;
        this.priority = priority;
        this.name = name;
        this.latitude = latitude;
        this.finished = finished;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getInnerregion_meter() {
        return innerregion_meter;
    }

    public String getOuterregion_meter() {
        return outerregion_meter;
    }

    public String getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getFinished() {
        return finished;
    }

    public String getAltitude() {
        return altitude;
    }
}