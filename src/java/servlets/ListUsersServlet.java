package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/ListUsersServlet")
public class ListUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONArray userList = new JSONArray();

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_uu_event_manager", "root", "9999");

            // Fetch users
            PreparedStatement ps = con.prepareStatement("SELECT id, username, email, role FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject user = new JSONObject();
                user.put("id", rs.getInt("id"));
                user.put("username", rs.getString("username")); // ✅ Fixed field name
                user.put("email", rs.getString("email"));
                user.put("role", rs.getString("role"));
                userList.put(user);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        out.print(userList.toString());
        out.flush();
    }
}
