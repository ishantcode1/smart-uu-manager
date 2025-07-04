import database.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject; // Import JSON library

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONObject jsonResponse = new JSONObject();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                // Updated SQL to also fetch email
                String sql = "SELECT email, role FROM users WHERE username=? AND password=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("userEmail", rs.getString("email")); // 👈 Key update

                    String role = rs.getString("role");
                    jsonResponse.put("status", "success");
                    jsonResponse.put("role", role);
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Invalid Credentials!");
                }
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Database connection failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", e.getMessage());
        }

        response.getWriter().write(jsonResponse.toString());
    }
}
