package com.example;

import java.util.Calendar;

public enum App {
	EXCHANGE_MANAGER(0);

	App(double rate){
		this.rate = rate;
		this.timeStamp = "" + calendar.get(Calendar.DAY_OF_MONTH) + 
				"." + calendar.get(Calendar.MONTH) +
				"." + calendar.get(Calendar.YEAR);
	}

	private double rate;
	private String timeStamp;
	private Calendar calendar = Calendar.getInstance();

	synchronized public void setRate(double rate){
		this.rate = rate;
		this.timeStamp = "" + calendar.get(Calendar.DAY_OF_MONTH) + 
				"." + calendar.get(Calendar.MONTH) +
				"." + calendar.get(Calendar.YEAR);
	}
	synchronized public double getRate(){
		return this.rate;
	}
	synchronized public String getTimeStamp(){
		return timeStamp;
	}
}