package invtracker.database;

import invtracker.dto.TickerData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.resource.cci.Record;

public class UpdateDatabase {
	
	// Check if table with name tableName exists
	public boolean tableExists(String tableName) {
		DBConnection dbc = DBConnection.getInstance();
		Connection conn = dbc.getConnection();
		DatabaseMetaData meta;
		ResultSet rs;
		try {
			meta = conn.getMetaData();
			rs = meta.getTables(null, null, tableName, new String[] {"TABLE"});
			if (!rs.next()) {
				return false;
			}
			else 
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void createTableForTickerSymbol(String symbol, int windowSize) {
		DBConnection dbc = DBConnection.getInstance();
		Connection conn = dbc.getConnection();
		try {
			// create Table
			Statement stmt = conn.createStatement();
			String query = "CREATE TABLE " + symbol + windowSize + " ("
					+ " date TEXT, closing_price FLOAT, green_value FLOAT, red_value FLOAT );";
			stmt.execute(query);
			
			// Add entry to "tickers" table
			query = "INSERT INTO tickers VALUES ("
					+"'"+ symbol + "'," 
					+ windowSize + ","
					+ "'stock name'" + ","
					+ "0,0,0,0);";
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertIntoTableForTickerSymbol(String symbol, int windowSize, List<TickerData> tickerDataListWithMarkers) {
		DBConnection dbc = DBConnection.getInstance();
		Connection conn = dbc.getConnection();
		try {
			// insert data into ticker symbol's table
			
			String query = "INSERT INTO " + symbol + windowSize + " (date, closing_price, green_value, red_value) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(query);            
			for (TickerData record : tickerDataListWithMarkers) {
			    ps.setString(1, record.getDate().toString());
			    ps.setFloat(2, record.getClosingPrice());
			    ps.setFloat(3, record.getGreenPoint());
			    ps.setFloat(4, record.getRedPoint());
			    ps.addBatch();
			}
			ps.executeBatch();
			
			
			// update entries in 'ticker' table
			query = "UPDATE tickers SET "
					+ "last_high_day=" + tickerDataListWithMarkers.get(tickerDataListWithMarkers.size()-1).getDaysSinceLastMax() +","
					+ "last_low_day=" + tickerDataListWithMarkers.get(tickerDataListWithMarkers.size()-1).getDaysSinceLastMin() + " "
					+ "WHERE symbol='" + symbol + "' AND window_size=" + windowSize + ";";
			conn.createStatement().executeUpdate(query);
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
