package com.dipesh.library.calender;

import java.util.ArrayList;
import java.util.List;

import com.dipesh.library.calender.dto.EventsDto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author dipesh
 *
 */
public class DatabaseHandler extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "VortexEvents";
	private static final String TABLE_EVENTS = "events";
	
	private static final String  _YEAR = "year";
	private static final String  _MONTH = "month";
	private static final String  _DAY = "day";
	private static final String  _TITLE = "title";
	private static final String  _DESCRIPTION = "description";
	private static final String  _SETALARM = "setAlarm";
	
	
	 public DatabaseHandler(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE "+ TABLE_EVENTS + " ("+_YEAR+" INTEGER , "+_MONTH+" INTEGER ,"+_DAY+" INTEGER, "+_TITLE+" TEXT, " 
				+_DESCRIPTION+" TEXT, "+_SETALARM+" TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS);
		onCreate(db);
	}
	
	 public void addEvent(EventsDto event) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(_YEAR, event.getYear()); 
	        values.put(_MONTH, event.getMonth()); 
	        values.put(_DAY, event.getDay()); 
	        values.put(_DESCRIPTION, event.getDescription());
	        values.put(_TITLE, event.getTitle());
	        values.put(_SETALARM, event.getAlarm());
	 
	        // Inserting Row
	        db.insert(TABLE_EVENTS, null, values);
	        db.close();                                     // Closing database connection
	    }

	 public void deleteAll(){
		 SQLiteDatabase db = this.getWritableDatabase();
		 db.delete(TABLE_EVENTS, null, null);	
		 db.close();
		 }
	 
	 public List<EventsDto> getAllEvents(){
		 List<EventsDto> eventList = new ArrayList<EventsDto>();
		 SQLiteDatabase db = this.getWritableDatabase();
		 Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EVENTS, null);
		 
		 if(cursor.moveToFirst()){
			 do{
				 EventsDto eventsDto = new EventsDto(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5));
				 eventList.add(eventsDto);
			 }while(cursor.moveToNext());
		 }
		 db.close();  
		 return eventList;
	 }
}
