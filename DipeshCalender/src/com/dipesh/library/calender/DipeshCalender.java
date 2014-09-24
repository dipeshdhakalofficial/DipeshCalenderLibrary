package com.dipesh.library.calender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.dipesh.library.calender.dto.EventsDto;
import com.dipesh.library.calender.utills.AppLog;
import com.dipesh.library.calender.utills.AppText;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * @author dipesh
 *
 */
public class DipeshCalender{

	Context context;
	Activity activity;
	public DipeshCalender(Context context,Activity activity) {
		this.context = context;
		this.activity = activity;
	}  
	
	
	 public static boolean isNetworkAvailable(Context context) {
		    ConnectivityManager cm =
		        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		        return true;
		    }
		    return false;
		}
	 
	 
	 public static String convertStreamToString(InputStream is) {
	   	 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        StringBuilder sb = new StringBuilder();
	 
	        String line = null;
	        try {
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                is.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        return sb.toString();
	    }
	 
	 public static boolean isOperationSuccess(String jsonResult){
			
			boolean isSuccess = false;
			JSONObject json;
			
			try {
				json = new JSONObject(jsonResult.trim());
				if (json.has("status")) {
					String value = json.getString("status");
					
					if(value.equalsIgnoreCase("success")) {
						isSuccess = true;
					}
				}
				
			} catch (JSONException e) {
				isSuccess = false;
			} catch (NullPointerException exception){
				isSuccess = false;
			}
			
			AppLog.showLog("Is operation success :: "+ isSuccess);
			return isSuccess;
		}
	 
	 //adds event
	 public void addEvent(int year,int month,int day,String title,String description,boolean setAlarm){
			try {
					
					AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
					Intent intent = new Intent(context, AlarmReciever.class);
					PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent, PendingIntent.FLAG_ONE_SHOT);
					alarmManager.cancel(pendingIntent);
					
					DatabaseHandler db = new DatabaseHandler(context);
					String input = year+"-"+month+"-"+day+" 10:00";              //exactly at 10 am
					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).parse(input);
					long milliseconds = date.getTime()-86400000;
					long millisecondsFromNow = milliseconds - (new Date()).getTime();
						if(setAlarm){
							db.addEvent(new EventsDto(year, month,day, title, description, "true"));
							if(millisecondsFromNow>0){
								setOneTimeAlarm(milliseconds,Integer.parseInt(year+month+day+""),title);
								}
						}else{
							db.addEvent(new EventsDto(year, month,day, title, description, "false"));
						}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
	 }
	 
	 //sets alarm a day before
	 public void setOneTimeAlarm(Long millsFromNow,int i,String title) {
			Intent intent = new Intent(context, AlarmReciever.class);
			intent.putExtra("title", title);
			  PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i,intent, PendingIntent.FLAG_ONE_SHOT);
			  AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			  am.set(AlarmManager.RTC_WAKEUP,millsFromNow, pendingIntent);
			 }


	
	
	
	public void startCalenderActivity(String title) {
		Intent i = new Intent(activity,CalenderActivity.class);
		i.putExtra("title", title);
		activity.startActivity(i);
	}

	//delets all events
	public void deleteAppEvents() {
		DatabaseHandler db = new DatabaseHandler(context);
		db.deleteAll();
	}
	
	public void setNotification(String title,int resourceId) {
		if(!TextUtils.isEmpty(title)){
		AppText.KEY_NOTIFICATION_TITLE = title;
		AppText.KEY_NOTIFICATION_ICON = resourceId;
		}
		
	}
}
