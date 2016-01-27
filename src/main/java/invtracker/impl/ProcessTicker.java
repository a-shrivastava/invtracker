package invtracker.impl;

import invtracker.dto.TickerData;
import invtracker.dto.TickerDataComparator;

import java.util.Collections;
import java.util.List;

import org.joda.time.Days;

public class ProcessTicker {
	private List<TickerData> tickerDataList;
	private int windowSize;

	public ProcessTicker(List<TickerData> tickerDataList, int windowSize) {
		this.tickerDataList = tickerDataList;
		this.windowSize = windowSize;
	}

	/**
	 * @return 
	 * 
	 */
	public List<TickerData> process() {
		TickerData lastMinTickerData = null;
		TickerData lastMaxTickerData = null;

		for (int i = windowSize + 1; i < tickerDataList.size(); i++) {
			TickerData currentTickerData = tickerDataList.get(i);
			
			// Computing for min in last 'windowSize' days 
			TickerData minTickerData = null;
			if (lastMinTickerData != null &&
			// check if last min date was after (current date - 'windowSize' days)
					lastMinTickerData.getDate().minusDays(windowSize)
							.compareTo(currentTickerData.getDate()) < 0) {
				if (currentTickerData.getClosingPrice() > lastMinTickerData
						.getClosingPrice()) {
					minTickerData = lastMinTickerData;
				} else {
					minTickerData = currentTickerData;
				}
			} else {
				List<TickerData> subsetListWindow = tickerDataList.subList(
						i - windowSize, i);
				minTickerData = Collections.min(subsetListWindow,
						new TickerDataComparator());
			}
			currentTickerData.setDaysSinceLastMin(Days.daysBetween(
					currentTickerData.getDate(), minTickerData.getDate())
					.getDays());
			
			// Computing for max in last 'windowSize' days
			TickerData maxTickerData = null;
			if (lastMaxTickerData != null &&
			// check if last max date was after (current date - 'windowSize' days)
					lastMaxTickerData.getDate().minusDays(windowSize)
							.compareTo(currentTickerData.getDate()) < 0) {
				if (currentTickerData.getClosingPrice() < lastMaxTickerData
						.getClosingPrice()) {
					maxTickerData = lastMaxTickerData;
				} else {
					maxTickerData = currentTickerData;
				}
			} else {
				List<TickerData> subsetListWindow = tickerDataList.subList(
						i - windowSize, i);
				maxTickerData = Collections.max(subsetListWindow,
						new TickerDataComparator());
			}
			currentTickerData.setDaysSinceLastMax(Days.daysBetween(
					currentTickerData.getDate(), maxTickerData.getDate())
					.getDays());

			// compute red plot data point
			currentTickerData.setRedPoint(100*(windowSize-currentTickerData.getDaysSinceLastMin())/windowSize);

			// compute green plot data point
			currentTickerData.setGreenPoint(100*(windowSize-currentTickerData.getDaysSinceLastMax())/windowSize);
		}

		return tickerDataList;
	}

}
