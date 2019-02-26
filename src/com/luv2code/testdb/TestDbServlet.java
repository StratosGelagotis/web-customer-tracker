package com.luv2code.testdb;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {
    private static final long serLVersionUID=1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Setup Connection Variables
        String user = "springstudent";
        String pass = "springstudent";

        String jdbcUrl =
                "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";
        // Get connection to database
        try{
            PrintWriter out = response.getWriter();
            out.println("Connecting to Database: "+ jdbcUrl);
            Class.forName(driver);
            Connection myConn = DriverManager.getConnection(jdbcUrl,user, pass);
            out.println("Success!!!");
            myConn.close();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
