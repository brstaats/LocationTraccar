package locationserver.trackers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by biezenj on 18-12-2015.
 */
public class Job {

    HashMap<String, String> map = new HashMap<>();
    Interval interval;

    public Job(Interval interval, HashMap<String, String> map) {
        this.map = map;
        this.interval = interval;

    }

    public Interval getInterval() {
        return interval;
    }

    public HashMap<String, String> getMap() {
        return map;
    }
}
