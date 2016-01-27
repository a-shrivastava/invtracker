package invtracker.dto;

import org.joda.time.DateTime;

public class TickerData {
	private DateTime date;
	private float closingPrice;
	private int daysSinceLastMin;
	private int daysSinceLastMax;
	private float greenPoint;
	private float redPoint;

	public float getGreenPoint() {
		return greenPoint;
	}

	public void setGreenPoint(float greenPoint) {
		this.greenPoint = greenPoint;
	}

	public float getRedPoint() {
		return redPoint;
	}

	public void setRedPoint(float redPoint) {
		this.redPoint = redPoint;
	}

	public int getDaysSinceLastMax() {
		return daysSinceLastMax;
	}

	public void setDaysSinceLastMax(int daysSinceLastMax) {
		this.daysSinceLastMax = daysSinceLastMax;
	}

	public int getDaysSinceLastMin() {
		return daysSinceLastMin;
	}

	public void setDaysSinceLastMin(int l) {
		this.daysSinceLastMin = l;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public float getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(float closing_price) {
		this.closingPrice = closing_price;
	}

	@Override
	public String toString() {
		return "TickerData [date=" + date + ", closingPrice=" + closingPrice
				+ ", daysSinceLastMin=" + daysSinceLastMin
				+ ", daysSinceLastMax=" + daysSinceLastMax + ", greenPoint="
				+ greenPoint + ", redPoint=" + redPoint + "]\n";
	}

}
