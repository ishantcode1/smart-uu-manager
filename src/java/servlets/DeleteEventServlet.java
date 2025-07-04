package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String eventId = request.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_uu_event_manager", "root", "9999");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE id=?");
            stmt.setInt(1, Integer.parseInt(eventId));

            int rowsDeleted = stmt.executeUpdate();
            conn.close();

            if (rowsDeleted > 0) {
                response.getWriter().write("Event deleted successfully!");
            } else {
                response.getWriter().write("Error deleting event.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Database error!");
        }
    }
}
