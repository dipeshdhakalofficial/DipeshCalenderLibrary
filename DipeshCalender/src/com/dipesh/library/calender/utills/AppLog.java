package com.dipesh.library.calender.utills;

import android.util.Log;


/**
 * @author dipesh
 *
 */
public class AppLog {

	private static final String APP_TAG = "Dipesh Calender";

	public static int showLog(String message) {
			return Log.i(APP_TAG, message);
//			return 0;
	}

}
