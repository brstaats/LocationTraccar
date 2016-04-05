package locationserver.restendpoints;


import locationserver.model.Tracker;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/tracker")
public class TrackerController {

    List<Tracker> list = new ArrayList<>();

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Tracker> getAllTrackers(@PathVariable("clientId") int clientId){


    return null;
    }

    @RequestMapping(value = "removeTracker/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public List<Tracker> deleteTracker(@PathVariable("id") int id) {
return list;
    }

    @RequestMapping(value = "/getTracker/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Tracker getTracker(@PathVariable("id") int id) {
        if (id > list.size()) {
            return null;
        }

        for (Tracker Tracker : list) {
            if (id == Tracker.getId()) {
                return Tracker;
            }
        }
        return null;
    }

    @RequestMapping(value = "/addTracker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Tracker> addTracker(@RequestBody Tracker Tracker) {

        list.add(Tracker);
        return list;
    }





}
