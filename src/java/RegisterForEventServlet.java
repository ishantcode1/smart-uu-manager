import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/RegisterForEventServlet")
public class RegisterForEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String eventId = request.getParameter("eventId");
        System.out.println("👉 Received eventId: " + eventId); // Debugging line

        if (eventId == null || eventId.trim().isEmpty() || eventId.equals("undefined")) {
            System.out.println("❌ Invalid eventId received.");
            response.sendRedirect("view_events.html?error=missingEventId");
            return;
        }

        HttpSession session = request.getSession(false);
        String studentId = (session != null) ? (String) session.getAttribute("username") : null;

        if (studentId == null) {
            System.out.println("🚫 No user in session. Redirecting to login.");
            response.sendRedirect("login.html");
            return;
        }

        System.out.println("✅ Registering student: " + studentId + " for event ID: " + eventId);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_uu_event_manager", "root", "9999");

            // Step 1: Check if already registered
            String checkQuery = "SELECT COUNT(*) FROM event_registrations WHERE event_id = ? AND student_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, Integer.parseInt(eventId));
            checkStmt.setString(2, studentId);

            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            checkStmt.close();

            if (count > 0) {
                System.out.println("⚠️ Student already registered for this event.");
                response.sendRedirect("view_events.html?alreadyRegistered=true");
                return;
            }

            // Step 2: Register the student
            String insertQuery = "INSERT INTO event_registrations (event_id, student_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setInt(1, Integer.parseInt(eventId));
            ps.setString(2, studentId);

            int rows = ps.executeUpdate();
            ps.close();
            conn.close();

            if (rows > 0) {
                System.out.println("✅ Registration successful.");
                response.sendRedirect("view_events.html?success=true");
            } else {
                System.out.println("❌ Registration failed.");
                response.sendRedirect("view_events.html?error=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("view_events.html?error=true");
        }
    }
}
