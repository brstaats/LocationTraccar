package locationserver.license;

/**
 * Created by biezenj on 15-12-2015.
 */
public class License {
    int max_trackers;
    String uuid;

    public License(String uuid, int max_trackers){
        this.uuid = uuid;
        this.max_trackers = max_trackers;
    }
}
