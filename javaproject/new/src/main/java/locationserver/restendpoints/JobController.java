package locationserver.restendpoints;


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
public class JobController {

    List<Job> list;

    @RequestMapping(value = "/getAllJobs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void getAllJobs(){

    }

    @RequestMapping(value = "deleteJob/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response deleteJob(@PathVariable("id") int id) {
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/getJob/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getJob(@PathVariable("id") String id) {


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
