package de.steinberg.usabilitylab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class InterfaceIntro extends RelativeLayout{

	public InterfaceIntro(Context context) {
		super(context);
	}
	
	public InterfaceIntro(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public InterfaceIntro(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return super.getChildCount();
	}

	@Override
	protected void onAttachedToWindow() {
		Button intro = (Button) findViewById(R.id.btn_interface_intro);
		intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				de.steinberg.usabilitylab.InterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.InterfaceViewFlipper) getParent();
				interfaceViewFlipper.showNext();
				interfaceViewFlipper.showCutomActionBar(true);
				TimeSingleton.getInstance().start();
			}
		});
		super.onAttachedToWindow();
	}
	

}
