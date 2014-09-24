package com.dipesh.library.calender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.dipesh.library.calender.dto.EventsDto;
import com.example.testproject.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * @author dipesh
 * source referenced from http://www.androidhub4you.com/2012/10/custom-calendar-in-android.html
 */

public class CalenderActivity extends ActionBarActivity implements OnClickListener,OnTouchListener {
	private static final String tag = "Dipesh Calender";

	private TextView currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private CalenderAdapter adapter;
	private Calendar _calendar;
	private int month, year;
	DatabaseHandler db;
	private static final String dateTemplate = "MMMM yyyy";
	List <EventsDto> eventsList = new ArrayList<EventsDto>();
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	private GestureDetector gestureDetector;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_calendar_view);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().show();
		
		
		getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
		
		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "+ year);
		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (TextView) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,_calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);
		
		db = new DatabaseHandler(this);
		eventsList = db.getAllEvents();
		
		calendarView = (GridView) this.findViewById(R.id.calendar);
		calendarView.setOnTouchListener(this);
		
		gestureDetector = new GestureDetector(this, new GestureListener());
		
		// Initialized
		adapter = new CalenderAdapter(getApplicationContext(),R.id.calendar_day_gridcell, month, year,eventsList,CalenderActivity.this);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	/**
	 * 
	 * @param month
	 * @param year
	 */
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new CalenderAdapter(getApplicationContext(),R.id.calendar_day_gridcell, month, year,eventsList,CalenderActivity.this);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v == prevMonth) {
			showPrevious();
		}
		if (v == nextMonth) {
			showNext();
		}
	}

	public void showNext(){
		if (month > 11) {
			month = 1;
			year++;
		} else {
			month++;
		}
		Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "+ month + " Year: " + year);
		setGridCellAdapterToDate(month, year);
	}
	public void showPrevious(){
		if (month <= 1) {
			month = 12;
			year--;
		} else {
			month--;
		}
		Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "+ month + " Year: " + year);
		setGridCellAdapterToDate(month, year);
	}
	
	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View ...");
		super.onDestroy();
	}


	
	private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            showPrevious();
                        } else {
                        	 showNext();
                        }
                    }
                    }
                    result = true;
              
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
	@Override
	 public boolean onTouchEvent(MotionEvent event) {
	    return gestureDetector.onTouchEvent(event);
	 }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		 return gestureDetector.onTouchEvent(event);
	}

	
}
