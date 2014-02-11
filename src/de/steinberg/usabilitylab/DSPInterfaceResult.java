package de.steinberg.usabilitylab;

import de.steinberg.usabilitylab.singletons.Writer;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
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
					if (rootViewFlipper.getDisplayedChild() == 7){
						Writer.getInstance(context).writeToFile();
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
	
}
