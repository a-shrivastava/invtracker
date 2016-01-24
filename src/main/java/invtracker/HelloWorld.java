package invtracker;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;
import javax.naming.*;
import java.io.PrintWriter;
import java.sql.*;

public class HelloWorld extends HttpServlet { 
  protected void doGet(HttpServletRequest request, 
      HttpServletResponse response) throws ServletException, IOException 
  {
    // reading the user input
    String ticker= request.getParameter("ticker");    
    PrintWriter out = response.getWriter();
    out.println(ticker);
    Connection result = null;
    try {
        Context initialContext = new InitialContext();
        DataSource datasource = (DataSource)initialContext.lookup("java:jboss/datasources/MySQLDS");
        result = datasource.getConnection();
        Statement stmt = result.createStatement() ;
        String query = "select * from names;" ;
        ResultSet rs = stmt.executeQuery(query) ;
        while (rs.next()) {
            out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + "<br />");
        }
    } catch (Exception ex) {
        out.println("Exception: " + ex + ex.getMessage());
    }
  }  
}