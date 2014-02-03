package de.steinberg.usabilitylab;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Questionare extends AnalyseRating{

	private ViewFlipper flipper;
	private int[] answers;
	private int check = 0;
	private Context context;
	
	public Questionare(Context context) {
		super(context);
		init();
	}
	public Questionare(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public Questionare(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}
	
	private void init() {
		rGroupIds = new int[]{R.id.radioGroup1,R.id.radioGroup2,R.id.radioGroup3,R.id.radioGroup4,
							  R.id.radioGroup5,R.id.radioGroup6,R.id.radioGroup7};
		answers = new int[]{2,3,2,1,2}; 

	}
	@Override
	protected void onAttachedToWindow() {
		flipper = (ViewFlipper) getParent();
		Button send = (Button) findViewById(R.id.btn_questionare_send);
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(analyseRating()){
					flipper.showNext();
					evaluateExperience();
					resetRadioButtons();
				} else{
					showAlert("Please answer all questions!");
				}
				rButtons.clear();
			}
		});
		super.onAttachedToWindow();
	}
	
	private void evaluateExperience(){
		
		int tmp = Integer.valueOf(rButtons.get(1).toString());
		check = (tmp == 4)? 0:tmp*5;
		for(int i=2;i<rButtons.size();i++){
			Log.d("gggz", String.valueOf(rButtons.get(i)));
			if (Integer.valueOf(rButtons.get(i).toString()) == answers[i-2]){
				check += 10;
			}
		}
		if (check>=35) {
			Toast.makeText(getContext(), String.valueOf(check)+" YOU ARE A DSP EXPERT!!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getContext(), String.valueOf(check)+" YOU ARE A LOOSER!!", Toast.LENGTH_SHORT).show();
		}
		
	}

}
