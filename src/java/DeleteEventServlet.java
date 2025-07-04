import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smart_uu_event_manager";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "9999";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE id = ?");
            stmt.setInt(1, eventId);
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("manage_events.html");
    }
}
