package com.dipesh.library.calender.dto;


/**
 * @author dipesh
 *
 */
public class EventsDto {

	int eventmonth;
	String setAlarm,description,monthString,dayString,title;
	int eventid,eventyear,eventday;
	
	
	public EventsDto(int eventyear,int eventmonth,int eventday,String title, String description, String setAlarm) {
		this.eventyear = eventyear;
		this.eventmonth = eventmonth;
		this.eventday = eventday;
		this.setAlarm = setAlarm;
		this.description = description;
		this.title=title;
	}
	
	public int getDay() {
		return eventday;
	}
	public int getMonth() {
		return eventmonth;
	}
	public int getYear() {
		return eventyear;
	}
	public String getDescription() {
		return description;
	}
	
	public String getAlarm() {
		return setAlarm;
	}
	public String getDayString() {
		return dayString;
	}
	public String getMonthString() {
		return monthString;
	}
	public String getTitle() {
		return title;
	}
}
