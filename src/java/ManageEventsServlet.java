import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageEventsServlet")
public class ManageEventsServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smart_uu_event_manager";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "9999";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<Event> events = new ArrayList<>();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM events");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                events.add(new Event(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("date"),
                    rs.getString("organizer")
                ));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(events);
        out.print(json);
        out.flush();
    }
}
