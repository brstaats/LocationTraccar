package locationserver.servlets.login;

import locationserver.response.JSONResponse;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by biezenj on 5-1-2016.
 */
public class AdminLogin extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");


        try {
            JSONObject jsonResponse = JSONResponse.getAdminLogin(request.getParameterMap());
            jsonResponse.write(response.getWriter());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}