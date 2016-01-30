package invtracker.impl;

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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TickerReader {
	/**
	 * 
	 * @param ticker
	 * @param windowSize
	 * @return
	 */
	public List<TickerData> read(String ticker, int windowSize) {
		try {
			// getting date for 2*windowsSize (e.g. 2*100) days (business days)
			//in order account for weekends multiply by 1.4; 
			//to account for holidays and boundary problems add 50*windowSize/100
			//thus we need data for 1.4*2*windowsize+ceil(50*windowSize/100)
			
			DateTime todaysDate = new DateTime(System.currentTimeMillis());
			int currentMonth = todaysDate.getMonthOfYear() - 1;
			String currentMonthString = (currentMonth < 10) ? "0"
					+ currentMonth : "" + currentMonth;
			int currentDay = todaysDate.getDayOfMonth();
			String currentDayString = (currentDay < 10) ? "0" + currentDay : ""
					+ currentDay;
			int currentYear = todaysDate.getYear();

			// Suppose windowSize = x, then xDaysBeforeDate is as
			// hundredDaysBeforeDate
			int daysBefore = (int) Math.ceil(1.4*2*windowSize+50*windowSize/100);
			System.out.println("cal days of data to pull:"+daysBefore);
			DateTime xDaysBeforeDate = todaysDate.minusDays(daysBefore);
			int xDaysBeforeMonth = xDaysBeforeDate.getMonthOfYear() - 1;
			String xDaysBeforeMonthString = (xDaysBeforeMonth < 10) ? "0"
					+ xDaysBeforeMonth : "" + xDaysBeforeMonth;
			int xDaysBeforeDay = xDaysBeforeDate.getDayOfMonth();
			String xDaysBeforeDayString = (xDaysBeforeDay < 10) ? "0"
					+ xDaysBeforeDay : "" + xDaysBeforeDay;
			int xDaysBeforeYear = xDaysBeforeDate.getYear();

			URL stockURL = new URL(
					"http://real-chart.finance.yahoo.com/table.csv?s=" + ticker
							+ "&a=" + xDaysBeforeMonthString + "&b="
							+ xDaysBeforeDayString + "&c=" + xDaysBeforeYear
							+ "&d=" + currentMonthString + "&e="
							+ currentDayString + "&f=" + currentYear
							+ "&g=d&ignore=.csv");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					stockURL.openStream()));

			List<TickerData> tickerDataList = new ArrayList<TickerData>();

			in.readLine();
			String nextLine;
			TickerData tickerData = null;
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("yyyy-mm-dd");
			while ((nextLine = in.readLine()) != null) {
				String[] lineData = nextLine.split(",");
				tickerData = new TickerData();
				DateTime date = formatter.parseDateTime(lineData[0]);
				tickerData.setDate(date);
				tickerData.setClosingPrice(Float.parseFloat(lineData[4]));
				tickerDataList.add(tickerData);

				// System.out.println(nextLine + ", ");
			}
			return tickerDataList;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
