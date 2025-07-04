import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import database.DBConnection;

@WebServlet("/MyRegistrationsServlet")
public class MyRegistrationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You must be logged in.");
            return;
        }

        String studentId = (String) session.getAttribute("username");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        JSONArray eventsArray = new JSONArray();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT e.id, e.title, e.description, e.date, e.organizer, e.location " +
                         "FROM events e " +
                         "JOIN event_registrations r ON e.id = r.event_id " +
                         "WHERE r.student_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                JSONObject event = new JSONObject();
                event.put("id", rs.getInt("id"));
                event.put("title", rs.getString("title"));
                event.put("description", rs.getString("description"));
                event.put("date", rs.getString("date"));
                event.put("organizer", rs.getString("organizer"));
                event.put("location", rs.getString("location"));

                eventsArray.put(event);
            }

            out.print(eventsArray.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while fetching registrations.\"}");
        }
    }
}
