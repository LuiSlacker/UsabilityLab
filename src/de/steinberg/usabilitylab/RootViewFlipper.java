package de.steinberg.usabilitylab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class RootViewFlipper extends ViewFlipper{

	private Context context;
	
	public RootViewFlipper(Context context) {
		super(context);
		this.context = context;
	}
	public RootViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public RootViewFlipper(Context context, AttributeSet attrs, int defstyle) {
		super(context, attrs);
		this.context = context;
	}
	
	protected void onAttachedToWindow() {
		int[] interfaceOrder = LatinSquareFactory.getInstance().getInterfaceOrder("novice");
		for (int i:interfaceOrder){
			DSPInterfaceViewFlipper dspInterfaceViewFlipper = new DSPInterfaceViewFlipper(context, i);
			addView(dspInterfaceViewFlipper);
		}
	}

}
