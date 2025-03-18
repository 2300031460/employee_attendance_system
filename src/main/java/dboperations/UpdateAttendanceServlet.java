package dboperations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateAttendanceServlet")
public class UpdateAttendanceServlet extends HttpServlet {
    
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get parameters from the request
        String employeeId = request.getParameter("employeeId");
        String date = request.getParameter("date");
        String status = request.getParameter("status");
        
        try {
            Connection con = null;
            
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Class Loaded Successfully");
            
            // Connect to the MySQL database
            con = DriverManager.getConnection("jdbc:mysql://localhost:8080/EmployeeAttendanceDB", "root", "Saketh@123");
            System.out.println("DB Connected Successfully");
            
            // SQL query to update the attendance status
            PreparedStatement pstmt = con.prepareStatement("UPDATE attendance SET status=? WHERE employee_id=? AND date=?");
            pstmt.setString(1, status); // New status value
            pstmt.setString(2, employeeId); // Employee ID to identify the record
            pstmt.setString(3, date); // Date to identify the record
            
            // Execute the update
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Attendance updated successfully.");
            } else {
                System.out.println("No attendance record found for the given employee ID and date.");
            }
            
            // Close resources
            pstmt.close();
            con.close();
            
            // Redirect to success page
            response.sendRedirect("updatesuccess.html");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
