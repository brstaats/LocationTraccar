package locationserver.restendpoints;


import locationserver.model.Position;
import locationserver.response.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jim on 15-3-2016.
 */
@RestController
@RequestMapping("/position")
public class PositionController {

    List<Position> list;

    @RequestMapping(value = "/getAllPositions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllPositions(){
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "deletePosition/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response deletePosition(@PathVariable("id") int id) {
        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/getPosition/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Response getPosition(@PathVariable("id") String id) {

        return Response.DELETE_SUCCES;
    }

    @RequestMapping(value = "/addPosition", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addPosition(@RequestBody Position Position) {
        return Response.SUCCES;
    }

    @RequestMapping(value = "/updatePosition", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response updatePosition(@RequestBody Position Position) {
        return Response.SUCCES;
    }


}
