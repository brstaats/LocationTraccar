package locationserver.restendpoints;


import locationserver.database.DatabaseConnection;
import locationserver.model.Job;
import locationserver.response.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/job")
public class UserController {

    List<Job> list;

    @RequestMapping(value = "/getAllJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Job> getAllJobs(){
        return new DatabaseConnection().getJobs();
    }

    @RequestMapping(value = "deleteJob/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response deleteJob(@PathVariable("id") int id) {
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/getJob/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Job getJob(@PathVariable("id") String id) {

        return new DatabaseConnection().getJob(id);
    }

    @RequestMapping(value = "/addJob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addJob(@RequestBody Job Job) {
        return Response.SUCCES;
    }

    @RequestMapping(value = "/updateJob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response updateJob(@RequestBody Job Job) {
        return Response.SUCCES;
    }


}
