package de.steinberg.usabilitylab;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;
import de.steinberg.usabilitylab.dspinterfaces.AbstractDSPInterface;

public class RootViewFlipper extends ViewFlipper{
	
	private ArrayList<Integer> dspInterfaces = new ArrayList<Integer>(){{
		add(2);
		add(3);
		add(4);
		add(5);
		add(6);
		add(7);
	}};
	public RootViewFlipper(Context context) {
		super(context);
	}
	public RootViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void showNext() {
		super.showNext();
		int childIndex = getDisplayedChild();
		if (dspInterfaces.contains(childIndex)) {
			DSPInterfaceViewFlipper dspVF = (DSPInterfaceViewFlipper) getChildAt(childIndex);
			if (dspVF.getDisplayedChild() == 0) {
				DSPInterfaceIntro dspIntro = (DSPInterfaceIntro) dspVF.getChildAt(0);
				dspIntro.loading();
			}
		}
		
	}
}
