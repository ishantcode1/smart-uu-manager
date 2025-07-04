import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.DatabaseConnection;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.getWriter().write("User deleted successfully.");
            } else {
                response.getWriter().write("User not found.");
            }
        } catch (Exception e) {
            response.getWriter().write("Error deleting user.");
            e.printStackTrace();
        }
    }
}
