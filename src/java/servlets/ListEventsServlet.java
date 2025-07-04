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
import com.google.gson.Gson;
import models.Event;

@WebServlet("/ListEventsServlet")
public class ListEventsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        ArrayList<Event> eventList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_uu_event_manager", "root", "9999");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM events");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("date"),
                    rs.getString("organizer")
                );
                eventList.add(event);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(eventList);
        out.print(json);
        out.flush();
    }
}
