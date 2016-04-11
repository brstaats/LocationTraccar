package locationserver.restendpoints;


import locationserver.database.DatabaseConnection;
import locationserver.database.repositories.DeviceRepository;
import locationserver.model.Device;
import locationserver.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DatabaseConnection<Device> db;

    @Autowired
    DeviceRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Device> getAllDevices() {
        return db.getAll(repository);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteDevice(@PathVariable("id") int id) {
        db.delete(repository,id);
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Device getDevice(@PathVariable("id") int id) {
        return db.getById(repository, id);
    }

    @RequestMapping(value = "/add/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Device addDevice(@RequestBody Device device) {
        return db.add(repository,device);
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Device updateDevice(@RequestBody Device device) {
        return db.update(repository,device);
    }
}
