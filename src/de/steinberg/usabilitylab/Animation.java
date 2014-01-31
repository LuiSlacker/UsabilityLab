package de.steinberg.usabilitylab;

import android.view.animation.TranslateAnimation;

public class Animation {

	public static TranslateAnimation inFromRightAnimation() {
		 
		TranslateAnimation inFromRight = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, +1.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(1000);
        return inFromRight;
	}

	//ViewFlipper Animations
	public static TranslateAnimation outToLeftAnimation() {
		TranslateAnimation outtoLeft = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, -1.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(1000);
        return outtoLeft;
	}
}
