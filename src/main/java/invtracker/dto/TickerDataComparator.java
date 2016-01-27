package invtracker.dto;

import java.util.Comparator;

public class TickerDataComparator implements Comparator<TickerData>{
	
	@Override
	public int compare(TickerData o1, TickerData o2) {
		if (o1.getClosingPrice() > o2.getClosingPrice())
			return 1;
		else if (o1.getClosingPrice() < o2.getClosingPrice())
			return -1;
		else return 0;
	}
}
