package com.dipesh.library.calender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.dipesh.library.calender.dto.CalenderItemDto;
import com.dipesh.library.calender.dto.EventsDto;
import com.example.testproject.R;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class CalenderAdapter extends BaseAdapter{

	Context _context;
	CalenderActivity calendarActivity;
	private final List<String> list;
	private final List<CalenderItemDto> daysList;
	private static final int DAY_OFFSET = 1;
	private final String[] weekdays = new String[] { "Sun", "Mon", "Tue","Wed", "Thu", "Fri", "Sat" };
	private final String[] months = { "January", "February", "March",
			"April", "May", "June", "July", "August", "September","October", "November", "December" };
	private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,31, 30, 31 };
	private int daysInMonth;
	private int currentDayOfMonth;
	private int todayMonth;
	private int currentWeekDay;
	private Button gridcell;
	List <EventsDto> eventsList;
	String tag = "calender adapter";
	public CalenderAdapter(Context context, int textViewResourceId,
			int month, int year,List <EventsDto> eventsList, CalenderActivity calendarActivity) {
		super();
		this._context = context;
		this.list = new ArrayList<String>();
		this.daysList = new ArrayList<CalenderItemDto>();
		this.eventsList = eventsList;
		this.calendarActivity = calendarActivity;
		
		Calendar calendar = Calendar.getInstance();
		setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
		setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
		todayMonth = calendar.get(Calendar.MONTH);
		
		Log.d(tag, "==> Passed in Date FOR Month: " + month + " "+ "Year: " + year);
		Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
		Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
		Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());
		
		
		// Print Month
		printMonth(month, year);

	}
	
	@Override
	public int getCount() {
		return daysList.size();
	}

	@Override
	public Object getItem(int position) {
		return daysList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.layout_screen_gridcell, parent, false);
		}

		// Get a reference to the Day grid cell
		gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
		gridcell.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				EventsDto eventsDto =(EventsDto)view.getTag();
				FragmentManager fmgr = calendarActivity.getSupportFragmentManager();
				if(eventsDto!=null){
				 EventsDialog alert = new EventsDialog(eventsDto.getYear()+"-"+eventsDto.getMonth()+"-"+eventsDto.getDay(),eventsDto.getTitle(),eventsDto.getDescription());
				 alert.show(fmgr, "eventsalert");
				}
				else{
					CustomDialog alert = new CustomDialog("No events");
					alert.show(fmgr, "alert");
				}
				
			}
		});

		// ACCOUNT FOR SPACING
		Log.i(tag, "Current Day:::: " + position);
		String day_color = daysList.get(position).getColor();
		String theday = daysList.get(position).getDay();
		String themonth = daysList.get(position).getMonth();
		String theyear = String.valueOf(daysList.get(position).getYear());

		String today = theyear + "-" + themonth + "-" + theday;
		gridcell.setText(theday);
		
		if (day_color.equals("WHITE")) {
			gridcell.setTextColor(calendarActivity.getResources().getColor(R.color.lightgray02));
		}
		if((position+1)%7==0 || (position)%7==0){
			gridcell.setTextColor(calendarActivity.getResources().getColor(R.color.red));
		}
		
		if (day_color.equals("BLUE")) {
			gridcell.setTextColor(calendarActivity.getResources().getColor(R.color.blue));
		}
		
		for (EventsDto event : eventsList) {
	           String month = months[event.getMonth()-1];
	           int year = event.getYear();
	           int day = event.getDay();
	           String gotDate = year+"-"+month+"-"+day;
	           if(gotDate.trim().equalsIgnoreCase(today.trim())){
	        	   gridcell.setTextColor(calendarActivity.getResources().getColor(R.color.red));
	        	   gridcell.setTag(event);
	           }
	    }
		
		if (day_color.equals("GREY")) {
			gridcell.setTextColor(calendarActivity.getResources().getColor(R.color.lightgray));
		}
		
		return row;
	
	}
	
	public int getCurrentDayOfMonth() {
		return currentDayOfMonth;
	}

	private void setCurrentDayOfMonth(int currentDayOfMonth) {
		this.currentDayOfMonth = currentDayOfMonth;
	}
	public void setCurrentWeekDay(int currentWeekDay) {
		this.currentWeekDay = currentWeekDay;
	}

	public int getCurrentWeekDay() {
		return currentWeekDay;
	}
	
	private void printMonth(int mm, int yy) {
		Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
		int trailingSpaces = 0;
		int daysInPrevMonth = 0;
		int prevMonth = 0;
		int prevYear = 0;
		int nextMonth = 0;
		int nextYear = 0;

		int currentMonth = mm - 1;
		String currentMonthName = getMonthAsString(currentMonth);
		daysInMonth = getNumberOfDaysOfMonth(currentMonth);

		Log.d(tag, "Current Month: " + " " + currentMonthName + " having "+ daysInMonth + " days.");
		
		GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
		Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

		if (currentMonth == 11) {
			prevMonth = currentMonth - 1;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 0;
			prevYear = yy;
			nextYear = yy + 1;
			Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"+ prevMonth + " NextMonth: " + nextMonth+ " NextYear: " + nextYear);
		} else if (currentMonth == 0) {
			prevMonth = 11;
			prevYear = yy - 1;
			nextYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			nextMonth = 1;
			Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"+ prevMonth + " NextMonth: " + nextMonth+ " NextYear: " + nextYear);
		} else {
			prevMonth = currentMonth - 1;
			nextMonth = currentMonth + 1;
			nextYear = yy;
			prevYear = yy;
			daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
			Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"+ prevMonth + " NextMonth: " + nextMonth+ " NextYear: " + nextYear);
		}

		int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		trailingSpaces = currentWeekDay;

		Log.d(tag, "Week Day:" + currentWeekDay + " is "+ getWeekDayAsString(currentWeekDay));
		Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
		Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

		if (cal.isLeapYear(cal.get(Calendar.YEAR)))
			if (mm == 2)
				++daysInMonth;
			else if (mm == 3)
				++daysInPrevMonth;

		// Trailing Month days
		for (int i = 0; i < trailingSpaces; i++) {
			Log.d(tag,"PREV MONTH:= "+ prevMonth+ " => "+ getMonthAsString(prevMonth)+ " "
		+ String.valueOf((daysInPrevMonth- trailingSpaces + DAY_OFFSET)+ i));
			daysList.add(new CalenderItemDto(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)+ i),"GREY",getMonthAsString(prevMonth),prevYear));
		}

		// Current Month Days
		for (int i = 1; i <= daysInMonth; i++) {
			Log.d(currentMonthName, String.valueOf(i) + " "+ getMonthAsString(currentMonth) + " " + yy);
			if (i == getCurrentDayOfMonth() && todayMonth == mm-1) {
				daysList.add(new CalenderItemDto(String.valueOf(i), "BLUE", getMonthAsString(currentMonth), yy));
			} else {
//				list.add(String.valueOf(i) + "-WHITE" + "-"+ getMonthAsString(currentMonth) + "-" + yy);
				daysList.add(new CalenderItemDto(String.valueOf(i), "WHITE", getMonthAsString(currentMonth), yy));
			}
		}

		// Leading Month days
		for (int i = 0; i < list.size() % 7; i++) {
			Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
			daysList.add(new CalenderItemDto(String.valueOf(i + 1), "GREY", getMonthAsString(nextMonth), nextYear));
		}
	}
	
	private String getMonthAsString(int i) {
		return months[i];
	}

	private String getWeekDayAsString(int i) {
		return weekdays[i];
	}

	private int getNumberOfDaysOfMonth(int i) {
		return daysOfMonth[i];
	}
	
}
