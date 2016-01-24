package invtracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;
import javax.naming.*;

import com.opencsv.CSVReader;

import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;

public class TickerAdder extends HttpServlet {
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// reading the user input
		String ticker = request.getParameter("ticker");
		PrintWriter out = response.getWriter();
		out.println("I got ticker: " + ticker);

		try {
			// get data from URL
			URL stockURL = new URL(
					"http://real-chart.finance.yahoo.com/table.csv?s=GOOG&a=00&b=01&c=2016&d=00&e=24&f=2016&g=d&ignore=.csv");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					stockURL.openStream()));
			CSVReader reader = new CSVReader(in);

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				if (nextLine != null && nextLine.length > 0) {
					for (String s : nextLine) {
						out.print(s + ", ");
					}
					out.println();
				}
			}

			reader.close();

			// adding ticker table
			Connection result = null;
			// try {
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource) initialContext
					.lookup("java:jboss/datasources/MySQLDS");
			result = datasource.getConnection();
			Statement stmt = result.createStatement();

			String query = "select * from names;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				out.println(rs.getString(1) + " " + rs.getString(2) + " "
						+ rs.getString(3) + "<br />");
			}
		} catch (Exception ex) {
			out.println("Exception: " + ex + ex.getMessage());
		}

		//
	}
}