package locationserver.restendpoints;


import locationserver.response.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/user")
public class UserController {



    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllUsers(){
       return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "deleteUser/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response deleteUser(@PathVariable("id") int id) {
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Response getUser(@PathVariable("id") String id) {

        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addUser(@RequestBody Response User) {
        return Response.SUCCES;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response updateUser(@RequestBody Response User) {
        return Response.SUCCES;
    }


}
