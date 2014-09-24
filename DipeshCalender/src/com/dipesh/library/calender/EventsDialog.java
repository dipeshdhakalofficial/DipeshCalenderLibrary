package com.dipesh.library.calender;


import com.example.testproject.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author dipesh
 *
 */
public class EventsDialog extends DialogFragment  implements View.OnClickListener{

	TextView tvdate,tvtype,tvdecsription,tvshift;
	Button btnok;
	String date,title,decription;
	
	
	public EventsDialog(String date,String title, String descrption) {
		this.date= date;
		this.title = title;
		this.decription = descrption;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.layout_dialog_events, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		tvdate = (TextView) view.findViewById(R.id.tv_date);
		tvdate.setText("Date: "+date);
		tvdecsription = (TextView) view.findViewById(R.id.tv_description);
		tvdecsription.setText("Description: "+decription);
		tvtype = (TextView) view.findViewById(R.id.tv_title);
		tvtype.setText("Title: "+title);
		btnok = (Button) view.findViewById(R.id.btn_dialog);
		btnok.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		this.dismiss();
	}
	
}
