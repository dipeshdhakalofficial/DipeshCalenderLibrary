package com.dipesh.library.calender.dto;

public class CalenderItemDto {

	int year;
	String color,day,month;
	public CalenderItemDto(String day, String color, String month,int year){
		this.year = year;
		this.color = color;
		this.day = day;
		this.month = month;
	}
	public String getColor() {
		return color;
	}
	public String getDay() {
		return day;
	}
	public String getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
}
