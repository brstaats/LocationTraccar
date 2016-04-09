package locationserver.restendpoints;


import locationserver.database.DatabaseConnection;
import locationserver.model.Device;
import locationserver.response.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {


    @RequestMapping(value = "/getAllDevices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Device> getAllDevices() {
        return new DatabaseConnection().getDevices();
    }

    @RequestMapping(value = "deleteDevice/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public List<Device> deleteDevice(@PathVariable("id") int id) {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/getDevice/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Device getDevice(@PathVariable("id") int id) {
        return new Device();
    }

    @RequestMapping(value = "/addDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addDevice(@RequestBody Device device) {
        return Response.SUCCES;
    }

    @RequestMapping(value = "/updateDevice", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response updateDevice(@RequestBody Device device) {
        return Response.SUCCES;
    }
}
