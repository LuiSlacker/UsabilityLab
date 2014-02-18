package de.steinberg.usabilitylab;

import de.steinberg.usabilitylab.singletons.DSPInterfaceOrderPreferences;
import de.steinberg.usabilitylab.singletons.LatinSquareFactory;
import de.steinberg.usabilitylab.singletons.Writer;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;

public class DSPInterfaceResult extends AnalyseRating{

	private Context context;
	
	public DSPInterfaceResult(Context context, int DSPInterfaceID) {
		super(context);
		this.context = context;
		this.DSPInterfaceID = DSPInterfaceID;
		init();
	}
	public DSPInterfaceResult(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	public DSPInterfaceResult(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	
	private void init() {
		Activity  activity = (Activity) context;
		activity.getLayoutInflater().inflate(R.layout.dsp_interface_result, this,true);
		
		rGroupIds = new int[]{R.id.radioGroup1,R.id.radioGroup2,R.id.radioGroup3,R.id.radioGroup4,
							  R.id.radioGroup5,R.id.radioGroup6,R.id.radioGroup7,R.id.radioGroup8,
							  R.id.radioGroup9,R.id.radioGroup10,R.id.radioGroup11,R.id.radioGroup12};
	}
	@Override
	protected void onAttachedToWindow() {
	    
	    Button send = (Button) findViewById(R.id.btn_interface_result);
	    send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (analyseRating()){ 
					addToHshMap();
					de.steinberg.usabilitylab.DSPInterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.DSPInterfaceViewFlipper) getParent();
					ViewFlipper rootViewFlipper = (ViewFlipper) interfaceViewFlipper.getParent();
					if (rootViewFlipper.getDisplayedChild() == rootViewFlipper.getChildCount()-2){
						Writer.getInstance(context).writeToFile();
						
						int novice = LatinSquareFactory.getInstance(context).getNovice();
						DSPInterfaceOrderPreferences.getInstance(context).saveSharedPreferences("Novice5", novice);
						
						int expert = LatinSquareFactory.getInstance(context).getExpert();
						DSPInterfaceOrderPreferences.getInstance(context).saveSharedPreferences("Expert5", expert);
					}
					interfaceViewFlipper.showNext();
					resetRadioButtons();
				} else{
					showAlert("Please rate all statements!");
				}
				rButtons.clear();
			}
		});
	    
		super.onAttachedToWindow();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getPointerCount() == 6) {
			for (int  rG:rGroupIds){
				RadioGroup RG = (RadioGroup) findViewById(rG);
				RG.check(R.id.radio0);
			}
		}
		super.onTouchEvent(event);
		return true;
	}
	
}
