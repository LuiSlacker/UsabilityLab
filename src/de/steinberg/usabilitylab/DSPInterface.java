package de.steinberg.usabilitylab;

import java.util.ArrayList;

import de.steinberg.usabilitylab.dspinterfaces.AbstractDSPInterface;
import de.steinberg.usabilitylab.dspinterfaces.Fader;
import de.steinberg.usabilitylab.dspinterfaces.LightSensor;
import de.steinberg.usabilitylab.dspinterfaces.XYPad;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class DSPInterface extends LinearLayout{

	private Context context;
	private int DSPInterfaceID;
	private ArrayList<AbstractDSPInterface> dspinterfaces;
	
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
	
	public int getDSPInterfaceID() {
		return DSPInterfaceID;
	}
	
	public ArrayList<Double> retrieveValues(){
		ArrayList<Double> metrics = new ArrayList<Double>(); 
		getInterfaces();
		for (AbstractDSPInterface dspInterface : dspinterfaces) {
			int[] parameters = dspInterface.getValues();
			for (int j=0; j<parameters.length; j++){
				metrics.add((double)parameters[j]);
			}
		}

		return metrics;
	}
	
	private void getInterfaces() {
		dspinterfaces = new ArrayList<AbstractDSPInterface>();
		switch (DSPInterfaceID) {
		case R.layout.fader_2:
			for (int i=0;i<2;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			}
			break;
		case R.layout.single_xypad:
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(0)));
			break;
		case R.layout.fader_4:
			for (int i=0;i<4;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			}
			break;
		case R.layout.xypad_2:
			for (int i=0;i<2;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			}
			break;
		case R.layout.fader_xypad_left:
			for (int i=0;i<2;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			}	
			break;
		case R.layout.fader_xypad_right:
			for (int i=0;i<2;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			}
			break;
		case R.layout.light_xypad_left:
			for (int i=0;i<2;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			} 
			break;
		case R.layout.light_xypad_right:
			for (int i=0;i<2;i++) {
				dspinterfaces.add((AbstractDSPInterface) findViewWithTag(String.valueOf(i)));
			} 
			break;
		default:
			break;
		}
	}
}
