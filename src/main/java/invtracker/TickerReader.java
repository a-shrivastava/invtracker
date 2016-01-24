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

import org.joda.time.DateTime;

public class TickerReader {
	/**
	 * 
	 * @param ticker
	 * @return 
	 */
	public List<TickerData> read(String ticker) {
		try {
			//getting date for 200 days
			DateTime todaysDate = new DateTime(System.currentTimeMillis());
			int currentMonth = todaysDate.getMonthOfYear()-1;
			String currentMonthString = (currentMonth<10)?"0"+currentMonth:""+currentMonth;
			int currentDay = todaysDate.getDayOfMonth();
			String currentDayString = (currentDay<10)?"0"+currentDay:""+currentDay;
			int currentYear = todaysDate.getYear();
			
			DateTime hundredDaysBeforeDate = todaysDate.minusDays(200);
			int hundredDaysBeforeMonth = hundredDaysBeforeDate.getMonthOfYear()-1;
			String hundredDaysBeforeMonthString = (hundredDaysBeforeMonth<10)?"0"+hundredDaysBeforeMonth:""+hundredDaysBeforeMonth;
			int hundredDaysBeforeDay = hundredDaysBeforeDate.getDayOfMonth();
			String hundredDaysBeforeDayString = (hundredDaysBeforeDay<10)?"0"+hundredDaysBeforeDay:""+hundredDaysBeforeDay;
			int hundredDaysBeforeYear = hundredDaysBeforeDate.getYear();
			
			
			URL stockURL = new URL(
					"http://real-chart.finance.yahoo.com/table.csv?s="+ticker+"&a="+hundredDaysBeforeMonthString+"&b="+hundredDaysBeforeDayString+"&c="+hundredDaysBeforeYear+"&d="+currentMonthString+"&e="+currentDayString+"&f="+currentYear+"&g=d&ignore=.csv");
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
