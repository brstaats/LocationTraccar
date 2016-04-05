package locationserver.trackers;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by biezenj on 16-12-2015.
 */
public class Tracker {


    LinkedHashMap<String, String> map;
    Location location;

    public Tracker(Location location, LinkedHashMap<String, String> map) {
       this.map = map;
        this.location = location;

    }


    public LinkedHashMap<String, String> getMap() {
        return map;
    }

    public Location getLocation(){
        return  location;
    }

    @Override
    public String toString(){
        return map.get("name");
    }

}
