package table;

import java.util.Date;

public class Spotreba {
	
	Date timestamp;
	int ele;
	int plyn;
	int voda;
	int temp;
	
	
	// konstruktor
	public Spotreba(Date db_date, int db_value1, int db_value2, int db_value3, int db_value4) {

		this.timestamp = setTimestamp(db_date);
		this.ele = setEle(db_value1);
		this.plyn = setPlyn(db_value2);
		this.voda = setVoda(db_value3);
		this.temp = setTemp(db_value4);
	}

	// Getters + Setters
	
	public Date getTimestamp() {
		return timestamp;
	}

	public Date setTimestamp(Date db_date) {
		return this.timestamp = db_date;
	}

	public int getEle() {
		return ele;
	}



	public int setEle(int ele) {
		return this.ele = ele;
	}



	public int getPlyn() {
		return plyn;
	}



	public int setPlyn(int plyn) {
		return this.plyn = plyn;
	}



	public int getVoda() {
		return voda;
	}



	public int setVoda(int voda) {
		return this.voda = voda;
	}



	public int getTemp() {
		return temp;
	}



	public int setTemp(int temp) {
		return this.temp = temp;
	}


	
}
