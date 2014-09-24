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
 * @author dipesh
 *
 */
public class CustomDialog extends DialogFragment  implements View.OnClickListener{

	TextView tvmessage;
	Button btnok;
	String message;
	
	public CustomDialog(String message) {
		this.message = message;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.layout_dialog, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		tvmessage = (TextView) view.findViewById(R.id.tv_dialog_header);
		tvmessage.setText(message);
		btnok = (Button) view.findViewById(R.id.btn_dialog);
		btnok.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		
		this.dismiss();
	}
}
