package locationserver.restendpoints;


import locationserver.database.DatabaseConnection;
import locationserver.model.Device;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/tracker")
public class DeviceController {

    List<Device> list;

    @RequestMapping(value = "/getAllDevices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Device> getAllTrackers(){
    return new DatabaseConnection().getDevices();
    }

    @RequestMapping(value = "removeTracker/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public List<Device> deleteTracker(@PathVariable("id") int id) {
return new ArrayList<>();
    }

    @RequestMapping(value = "/getTracker/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Device getTracker(@PathVariable("id") int id) {
        if (id > list.size()) {
            return null;
        }

        for (Device device : list) {
            if (id == device.getId()) {
                return null;
            }
        }
        return null;
    }

    @RequestMapping(value = "/addTracker", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Device> addTracker(@RequestBody Device device) {


        return list;
    }





}
