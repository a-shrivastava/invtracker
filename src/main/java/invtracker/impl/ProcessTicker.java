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
		int lastMinTickerDataID = 0;
		TickerData lastMaxTickerData = null;
		int lastMaxTickerDataID = 0;

		for (int i = windowSize-1; i < tickerDataList.size(); i++) {
			System.out.println("****on index:"+i);
			TickerData currentTickerData = tickerDataList.get(i);
			
			// Computing for min in last 'windowSize' days 
			TickerData minTickerData = null;
			TickerData maxTickerData = null;
			if (lastMinTickerData != null &&
			// check if last min date was after (current date - 'windowSize' days)
					(i-lastMinTickerDataID) < windowSize) {
				if (currentTickerData.getClosingPrice() > lastMinTickerData
						.getClosingPrice()) {
					minTickerData = lastMinTickerData;
				} else {
					minTickerData = currentTickerData;
					lastMinTickerData = currentTickerData;
					lastMinTickerDataID = i;
				}
			} else {
				List<TickerData> subsetListWindow = tickerDataList.subList(
						i - windowSize + 1, i+1);
				minTickerData = Collections.min(subsetListWindow,
						new TickerDataComparator());
				lastMinTickerData = minTickerData;
				lastMinTickerDataID = tickerDataList.indexOf(minTickerData);
			}
			currentTickerData.setDaysSinceLastMin(i-lastMinTickerDataID);
			
			// Computing for max in last 'windowSize' days
			if (lastMaxTickerData != null &&
					// check if last max date was after (current date - 'windowSize' days)
							(i-lastMaxTickerDataID) < windowSize) {
						if (currentTickerData.getClosingPrice() < lastMaxTickerData
								.getClosingPrice()) {
							maxTickerData = lastMaxTickerData;
						} else {
							maxTickerData = currentTickerData;
							lastMaxTickerData = currentTickerData;
							lastMaxTickerDataID = i;
						}
					} else {
						List<TickerData> subsetListWindow = tickerDataList.subList(
								i - windowSize + 1, i+1);
						maxTickerData = Collections.max(subsetListWindow,
								new TickerDataComparator());
						lastMaxTickerData = maxTickerData;
						lastMaxTickerDataID = tickerDataList.indexOf(maxTickerData);
					}
					currentTickerData.setDaysSinceLastMax(i-lastMaxTickerDataID);
					

			// compute red plot data point
			currentTickerData.setRedPoint(100*(windowSize-currentTickerData.getDaysSinceLastMin())/windowSize);

			// compute green plot data point
			currentTickerData.setGreenPoint(100*(windowSize-currentTickerData.getDaysSinceLastMax())/windowSize);
		}

		return tickerDataList;
	}

}
