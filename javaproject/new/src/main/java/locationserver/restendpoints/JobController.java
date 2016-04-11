package locationserver.restendpoints;


import locationserver.database.DatabaseConnection;
import locationserver.database.repositories.JobRepository;
import locationserver.model.Job;
import locationserver.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    DatabaseConnection<Job> db;

    @Autowired
    JobRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Job> getAllJobs() {
        return db.getAll(repository);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Response deleteJobs(@PathVariable("id") int id) {
        db.delete(repository,id);
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Job getJobs(@PathVariable("id") UUID id) {
        return db.findByUuid(repository, (UUID) id);
    }

    @RequestMapping(value = "/add/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Job addJob(@RequestBody Job job) {
        return db.add(repository,job);
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Job updateJob(@RequestBody Job job) {
        return db.update(repository,job);
    }
}

