package de.steinberg.usabilitylab;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class InterfaceResult extends AnalyseRating{

	private String interfaceName;
	private Context mcontext;
	private AttributeSet mattrs;
	
	public InterfaceResult(Context context) {
		super(context);
		init();
	}
	public InterfaceResult(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext = context;
		mattrs = attrs;
		init();
	}
	public InterfaceResult(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	
	private void init() {
		parseInterfaceName();
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
					ViewFlipper interfaceviewflipper = (ViewFlipper) getParent();
					interfaceviewflipper.showNext();
					resetRadioButtons();
				} else{
					showAlert("Please rate all statements!");
				}
				rButtons.clear();
			}
		});
	    
		super.onAttachedToWindow();
	}
	
	
	/******************************************************************************************
	 ** Parse ControllerNumber from custom XMl
	 ******************************************************************************************/
	private void parseInterfaceName() {
		
			TypedArray a = mcontext.obtainStyledAttributes(mattrs, R.styleable.de_steinberg_usabilitylab_InterfaceResult);
							
			final int N = a.getIndexCount();
			for (int i = 0; i < N; ++i) {
			    int attr = a.getIndex(i);
			    switch (attr) {
			    	case R.styleable.de_steinberg_usabilitylab_InterfaceResult_interfaceName:
			    		interfaceName = a.getString(attr);
			            break;
			    }
			}
			a.recycle();
			Log.d("INsdf", String.valueOf(interfaceName));
	}
	
}
