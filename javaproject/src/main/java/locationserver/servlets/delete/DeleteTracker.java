package locationserver.servlets.delete;

import locationserver.response.JSONResponse;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by biezenj on 29-12-2015.
 */
public class DeleteTracker extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        JSONObject jsonResponse = JSONResponse.deleteTracker(request.getParameterMap());

        try {
            jsonResponse.write(response.getWriter());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
