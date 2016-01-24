package invtracker.dto;

import java.util.Date;

public class TickerData {
	private Date date;
	private float closing_price;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getClosing_price() {
		return closing_price;
	}
	public void setClosing_price(float closing_price) {
		this.closing_price = closing_price;
	}
	@Override
	public String toString() {
		return "TickerData [date=" + date + ", closing_price=" + closing_price
				+ "]\n";
	}
									
}
