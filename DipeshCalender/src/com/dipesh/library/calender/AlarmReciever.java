package com.dipesh.library.calender;



import com.dipesh.library.calender.utills.AppLog;
import com.dipesh.library.calender.utills.AppText;
import com.example.testproject.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author dipesh
 *
 */
public class AlarmReciever extends BroadcastReceiver{

	NotificationManager mManager;
	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		mManager = (NotificationManager) context.getApplicationContext().getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(context.getApplicationContext(),CalenderActivity.class);
		String title = arg1.getStringExtra("title");
		Notification notification = new Notification(R.drawable.ic_launcher,AppText.KEY_NOTIFICATION_TITLE, System.currentTimeMillis());
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity( context.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.setLatestEventInfo(context.getApplicationContext(), AppText.KEY_NOTIFICATION_TITLE, title+" event tomorrow", pendingNotificationIntent);
		AppLog.showLog("Alarm received:: "+arg1.getExtras());
		mManager.notify(0, notification);
	}

}
