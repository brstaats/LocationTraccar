package locationserver.trackers;

import java.util.LinkedHashMap;

/**
 * Created by biezenj on 21-12-2015.
 */
public class Client {


    LinkedHashMap<String, String> map;


    public Client(LinkedHashMap<String, String> map) {
        this.map = map;

    }


    public LinkedHashMap<String, String> getMap() {
        return map;
    }


}
