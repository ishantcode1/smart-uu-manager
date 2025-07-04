import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewEventsServlet")
public class ViewEventsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (
            PrintWriter out = response.getWriter();
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/smart_uu_event_manager", "root", "9999");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM events")
        ) {
            StringBuilder json = new StringBuilder();
            json.append("[");

            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"title\":\"").append(escape(rs.getString("title"))).append("\",");
                json.append("\"date\":\"").append(escape(rs.getString("date"))).append("\",");
                json.append("\"location\":\"").append(escape(rs.getString("location"))).append("\",");
                json.append("\"organizer\":\"").append(escape(rs.getString("organizer"))).append("\",");
                json.append("\"description\":\"").append(escape(rs.getString("description"))).append("\"");
                json.append("}");
                first = false;
            }

            json.append("]");
            out.print(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
            }
        }
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
