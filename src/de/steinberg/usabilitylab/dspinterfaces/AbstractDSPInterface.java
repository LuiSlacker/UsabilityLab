package de.steinberg.usabilitylab.dspinterfaces;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class AbstractDSPInterface extends View{

	public AbstractDSPInterface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AbstractDSPInterface(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}public AbstractDSPInterface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public abstract int[] getValues();

}
