package de.steinberg.usabilitylab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class mTextView extends TextView{

	public mTextView(Context context) {
		super(context);
	}
	public mTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public mTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
//		invalidate();
//		getText().toString();
	}
}
