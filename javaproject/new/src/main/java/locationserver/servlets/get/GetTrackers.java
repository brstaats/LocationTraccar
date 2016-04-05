package locationserver.servlets.get;

import org.json.JSONException;
import org.json.JSONObject;
import locationserver.response.JSONResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by biezenj on 15-12-2015.
 */
public class GetTrackers extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");


        JSONObject jsonResponse = JSONResponse.getTrackers(request.getParameterMap());

        try {
            jsonResponse.write(response.getWriter());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
