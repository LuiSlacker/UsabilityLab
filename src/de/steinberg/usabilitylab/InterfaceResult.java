package de.steinberg.usabilitylab;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.ViewFlipper;

public class InterfaceResult extends ScrollView{

	private ArrayList<RadioGroup> rGroup = new ArrayList<RadioGroup>();
	private ArrayList<Object> rButton = new ArrayList<Object>();
	
	public InterfaceResult(Context context) {
		super(context);
	}
	public InterfaceResult(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public InterfaceResult(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void onAttachedToWindow() {
		//push RadioGroups into ArrayList 
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup1));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup2));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup3));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup4));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup5));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup6));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup7));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup8));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup9));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup10));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup11));
	    rGroup.add((RadioGroup) findViewById(R.id.radioGroup12));	
	    
	    Button send = (Button) findViewById(R.id.btn_interface_result);
	    send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (analyseRating()){
					ViewFlipper interfaceviewflipper = (ViewFlipper) getParent();
					interfaceviewflipper.showNext();
					resetRadioButtons();
				} else{
					showAlert("Please rate all statements!");
				}
				rButton.clear();
			}
		});
		super.onAttachedToWindow();
	}
	
	private boolean analyseRating() {
		// fill Tag-Object ArrayList with selection in each RadioGroup
		for (RadioGroup radioGroup:rGroup){
			rButton.add(findselectedRadioButton(radioGroup));
		}
		Log.d("list", String.valueOf(rButton));
		// if one RadioGroup has no selected RadioButton return false
		return !rButton.contains(null);
	}

	private Object findselectedRadioButton(RadioGroup rGroup) {
		//get selected RedioButtonID
		int selectedId =  rGroup.getCheckedRadioButtonId();
		//if selection exists --> return tag --otherwise return null
		if (selectedId != -1) {
			RadioButton rB = (RadioButton) findViewById(selectedId);
			return rB.getTag();
		} else return null; 
	}
	
	private void resetRadioButtons() {
		for (RadioGroup radioGroup:rGroup){
			radioGroup.clearCheck();
		}
	}
	
	private void showAlert(String string) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage(string);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
