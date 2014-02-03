package de.steinberg.usabilitylab;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AnalyseRating extends LinearLayout{
	
	private ArrayList<RadioGroup> rGroups = new ArrayList<RadioGroup>();
	protected ArrayList<Object> rButtons = new ArrayList<Object>();
	protected int[] rGroupIds;
	
	public AnalyseRating(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AnalyseRating(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public AnalyseRating(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAttachedToWindow() {
		//push RadioGroups into ArrayList 
		for(int rGroupId:rGroupIds){
			rGroups.add((RadioGroup) findViewById(rGroupId));
		}
		Log.d("size", String.valueOf(rGroups.size()));
		super.onAttachedToWindow();
	}
	
	protected boolean analyseRating() {
		// fill Tag-Object ArrayList with selection in each RadioGroup
		for (RadioGroup radioGroup:rGroups){
			rButtons.add(findselectedRadioButton(radioGroup));
		}
		Log.d("list", String.valueOf(rButtons));
		// if one RadioGroup has no selected RadioButton return false
		return !rButtons.contains(null);
	}
	
	protected Object findselectedRadioButton(RadioGroup rGroup) {
		//get selected RedioButtonID
		int selectedId =  rGroup.getCheckedRadioButtonId();
		//if selection exists --> return tag --otherwise return null
		if (selectedId != -1) {
			RadioButton rB = (RadioButton) findViewById(selectedId);
			return rB.getTag();
		} else return null; 
	}
	
	protected void resetRadioButtons() {
		for (RadioGroup radioGroup:rGroups){
			radioGroup.clearCheck();
		}
	}
	
	protected void showAlert(String string) {
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
