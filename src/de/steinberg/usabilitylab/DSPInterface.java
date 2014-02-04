package de.steinberg.usabilitylab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class DSPInterface extends LinearLayout{

	private Context context;
	private int DSPInterfaceID;
	
	public DSPInterface(Context context, int DSPInterfaceID) {
		super(context);
		this.context  = context;
		this.DSPInterfaceID = DSPInterfaceID; 
		init();
	}
	
	private void init(){
		Activity  activity = (Activity) context;
		activity.getLayoutInflater().inflate(DSPInterfaceID, this,true);
	}
}
