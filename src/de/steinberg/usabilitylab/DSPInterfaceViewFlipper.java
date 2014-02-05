package de.steinberg.usabilitylab;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ViewFlipper;

public class DSPInterfaceViewFlipper extends AbstractDSPInterfaceViewFlipper {

	private ActionBar actionBar;
	private Context context;
	private int DSPInterfaceID;
	private int DSPInterface;
	
	public DSPInterfaceViewFlipper(Context context, int DSPInterface) {
		super(context);
		this.context = context;
		this.DSPInterface = DSPInterface;
		init();
	}
	
	private void init(){
		mapDSPInterfaces();
		initialize();
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
		} //else {
			super.showNext();	
//		}
	}
	@Override
	protected void addDSPInterface() {
		DSPInterface userInterface = new DSPInterface(context, DSPInterfaceID); 
		addView(userInterface);
	}
	
	private void mapDSPInterfaces(){
		switch (DSPInterface) {
		case 0:
			DSPinterfaceName = "4 Fader";
			DSPInterfaceID = R.layout.fader_4; 
			break;
		case 1:
			DSPinterfaceName = "2 XYPads";
			DSPInterfaceID = R.layout.xypad_2; 
			break;
		case 2:
			DSPinterfaceName = "Fader + XYPads";
			DSPInterfaceID = R.layout.fader_xypad; 	
			break;
		case 3:
			DSPinterfaceName = "Light + XYPads";
			DSPInterfaceID = R.layout.fader_xypad; 
			break;
		default:
			break;
		}
	}
}
