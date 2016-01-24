package invtracker;

import invtracker.dto.TickerData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TickerReader {
	/**
	 * 
	 * @param ticker
	 * @return 
	 */
	public List<TickerData> read(String ticker) {
		try {
			URL stockURL = new URL(
					"http://real-chart.finance.yahoo.com/table.csv?s=GOOG&a=00&b=01&c=2016&d=00&e=24&f=2016&g=d&ignore=.csv");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					stockURL.openStream()));

			List<TickerData> tickerDataList = new ArrayList<TickerData>();
			
			in.readLine();
			String nextLine;
			TickerData tickerData = null;
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			while ((nextLine = in.readLine()) != null) {
				String[] lineData = nextLine.split(",");
				tickerData = new TickerData();
				Date date = format.parse(lineData[0]);
				tickerData.setDate(date);
				tickerData.setClosing_price(Float.parseFloat(lineData[4]));
				tickerDataList.add(tickerData);

//				System.out.println(nextLine + ", ");
			}
			return tickerDataList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
