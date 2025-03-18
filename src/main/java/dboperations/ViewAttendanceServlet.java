package dboperations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewAttendanceServlet")
public class ViewAttendanceServlet extends HttpServlet {
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Connection con = null;
            
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Class Loaded Successfully");
            
            // Connect to the MySQL database
            con = DriverManager.getConnection("jdbc:mysql://localhost:8080/EmployeeAttendanceDB", "root", "Saketh@123");
            System.out.println("DB Connected Successfully");
            
            // SQL query to fetch attendance data
            String qry = "SELECT * FROM attendance";
            PreparedStatement pstmt = con.prepareStatement(qry);
            ResultSet rs = pstmt.executeQuery();
            
            // Generate HTML response
            out.println("<h3 align='center'><u>View All Employee Attendance</u></h3>");
            out.println("<table align='center' border=3>");
            out.println("<tr bgcolor='lightblue'>");
            out.println("<td>Attendance ID</td>");
            out.println("<td>Employee ID</td>");
            out.println("<td>Date</td>");
            out.println("<td>Status</td>");
            out.println("</tr>");
            
            // Iterate through result set and display data
            while (rs.next()) {
                out.println("<tr bgcolor='lightgrey'>");
                out.println("<td>" + rs.getInt("attendance_id") + "</td>");
                out.println("<td>" + rs.getString("employee_id") + "</td>");
                out.println("<td>" + rs.getDate("date") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("</tr>");
            }
            
            // Close table and HTML tags
            out.println("</table>");
            rs.close();
            pstmt.close();
            con.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
