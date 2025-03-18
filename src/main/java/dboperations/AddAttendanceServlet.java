package dboperations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddAttendanceServlet")
public class AddAttendanceServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get parameters from the request
        String employeeId = request.getParameter("employee_id");
        String status = request.getParameter("status");
        LocalDate date = LocalDate.now(); // Current date
        
        try {
            Connection con = null;

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Class Loaded Successfully");

            // Connect to MySQL database
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:8080/EmployeeAttendanceDB", 
                "root", 
                "Saketh@123"
            );
            System.out.println("DB Connected Successfully");

            // Insert attendance record
            String qry = "INSERT INTO attendance (employee_id, date, status) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(qry);
            pstmt.setString(1, employeeId);
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            pstmt.setString(3, status);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h3>Attendance Record Added Successfully!</h3>");
            } else {
                out.println("<h3>Failed to Add Attendance Record.</h3>");
            }

            // Close resources
            pstmt.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
