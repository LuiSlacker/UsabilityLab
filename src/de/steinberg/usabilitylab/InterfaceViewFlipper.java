package de.steinberg.usabilitylab;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class InterfaceViewFlipper extends ViewFlipper {

	ActionBar actionBar;
	
	public InterfaceViewFlipper(Context context) {
		super(context);
		init(context);
	}
	public InterfaceViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context){
		this.setInAnimation(Animation.inFromRightAnimation());
		this.setOutAnimation(Animation.outToLeftAnimation());
		
		Activity mainActivity = (Activity) context;
		actionBar = mainActivity.getActionBar();
	}

	public void showCutomActionBar(boolean showCustomActionBar){
		if (showCustomActionBar) {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		} else {
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
		}
	}
	
	@Override
	public void showNext() {
		if (this.getDisplayedChild() == this.getChildCount()-1){
			ViewFlipper flipper = (ViewFlipper) getParent();
			flipper.showNext();
		} else{
			super.showNext();	
		}
	}
}
