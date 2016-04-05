package locationserver.trackers;

/**
 * Created by biezenj on 17-12-2015.
 */
public class Interval {

    String uuid, geomtask, enddate, innerregion_meter, outerregion_meter, priority;

    public Interval(String uuid, String geomtask, String enddate, String innerregion_meter, String outerregion_meter, String priority) {
        this.uuid = uuid;
        this.geomtask = geomtask;
        this.enddate = enddate;
        this.innerregion_meter = innerregion_meter;
        this.outerregion_meter = outerregion_meter;
        this.priority = priority;
    }

    public String getUuid() {
        return uuid;
    }

    public String getGeomtask() {
        return geomtask;
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
}