package locationserver.trackers;

import org.postgis.Point;

import java.util.LinkedHashMap;

/**
 * Created by biezenj on 16-12-2015.
 */
public class Location {

    private String uuid, tracker_uuid, accuracy, accuracyHeight, location;

    public Location(String accuracy, String accuracyHeight, String location) {

        this.accuracy = accuracy;
        this.accuracyHeight = accuracyHeight;
        this.location = location;


    }

    public String getUuid() {
        return uuid;
    }

    public String getTracker_uuid() {
        return tracker_uuid;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public String getAccuracyHeight() {
        return accuracyHeight;
    }

    public String getLocation() {
        return location;
    }
}
