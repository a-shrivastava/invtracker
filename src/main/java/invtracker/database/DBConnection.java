package invtracker.database;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {

	private static DBConnection instance = null;
	private Connection con = null;

	private DBConnection() {
		// Not accessible externally!
		createConnection();
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	private void createConnection() {

//		Connection result = null;
		try {
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource) initialContext
					.lookup("java:jboss/datasources/MySQLDS");
			con = datasource.getConnection();
//			Statement stmt = result.createStatement();

			// String query = "select * from names;";
			// ResultSet rs = stmt.executeQuery(query);
			// while (rs.next()) {
			// out.println(rs.getString(1) + " " + rs.getString(2) + " "
			// + rs.getString(3) + "<br />");
			// }
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}

	}

	public Connection getConnection() {
		return con;
	}

	public void setConnection(Connection con) {
		this.con = con;
	}

}
