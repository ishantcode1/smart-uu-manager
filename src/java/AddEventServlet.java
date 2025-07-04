import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddEventServlet")
public class AddEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        String location = request.getParameter("location");
        String organizer = request.getParameter("organizer");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_uu_event_manager", "root", "9999");

            // Insert query
            String sql = "INSERT INTO events (title, description, date, location, organizer) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, date);
            ps.setString(4, location);
            ps.setString(5, organizer);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("manage_events.html?success=true");
            } else {
                response.sendRedirect("add_event.html?error=true");
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("add_event.html?error=true");
        }
    }
}
