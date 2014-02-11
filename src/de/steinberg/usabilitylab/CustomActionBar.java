package de.steinberg.usabilitylab;

import de.steinberg.usabilitylab.settings.SettingsView;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class CustomActionBar extends RelativeLayout{

	private Context context;
	
	public CustomActionBar(Context context) {
		super(context);
		this.context = context;
	}
	public CustomActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	public CustomActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getPointerCount() == 3) {
			Intent settingsIntent = new Intent(context, SettingsView.class);
	        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);    
		    context.startActivity(settingsIntent);
		}
		super.onTouchEvent(event);
		return true;
	}

}
