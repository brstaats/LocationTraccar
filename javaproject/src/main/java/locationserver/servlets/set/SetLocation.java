package locationserver.servlets.set;

import org.json.JSONException;
import org.json.JSONObject;
import locationserver.response.JSONResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by biezenj on 18-12-2015.
 */
public class SetLocation extends HttpServlet {

 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        JSONObject jsonResponse = JSONResponse.setLocation(request.getParameterMap());

        try {
            jsonResponse.write(response.getWriter());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
