package de.steinberg.usabilitylab;

import de.steinberg.usabilitylab.settings.SettingsView;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class StartScreen extends RelativeLayout{
	
	private Context context;
	
	public StartScreen(Context context) {
		super(context);
		this.context = context;
	}
	public StartScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	public StartScreen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	@Override
	protected void onAttachedToWindow() {
		Button btn_start = (Button) findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ViewFlipper rootViewFlipper = (ViewFlipper) getParent();
				rootViewFlipper.showNext();
			}
		});
		
		Button btn_settings = (Button) findViewById(R.id.btn_settings);
	   	btn_settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent settingsIntent = new Intent(context, SettingsView.class);
		        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);    
			    context.startActivity(settingsIntent);
			}
		});
	   	
		super.onAttachedToWindow();
	}

}
