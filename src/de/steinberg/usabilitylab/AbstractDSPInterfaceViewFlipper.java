package de.steinberg.usabilitylab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public abstract class AbstractDSPInterfaceViewFlipper extends ViewFlipper{

	private Context context;
	protected int DSPInterfaceBackground;
	
	public AbstractDSPInterfaceViewFlipper(Context context) {
		super(context);
		this.context = context;
	}
	
	protected void initialize() {
		addDSPInterfaceIntro();
		addDSPInterface();
		addDSPInterfaceResult();
	}
	
	private void addDSPInterfaceIntro(){
		DSPInterfaceIntro interfaceIntro = new DSPInterfaceIntro(context, DSPInterfaceBackground);
		addView(interfaceIntro);
	}
	
	protected abstract void addDSPInterface();
	
	private void addDSPInterfaceResult(){
		DSPInterfaceResult interfaceResult = new DSPInterfaceResult(context, DSPInterfaceBackground);
		addView(interfaceResult);
	}

}
