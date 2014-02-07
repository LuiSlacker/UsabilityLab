package de.steinberg.usabilitylab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class FinishScreen extends RelativeLayout{

	private Context context;
	
	public FinishScreen(Context context) {
		super(context);
		this.context = context;	}
	public FinishScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;	}
	public FinishScreen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	@Override
	protected void onAttachedToWindow() {
		
		Button btn_again = (Button) findViewById(R.id.btn_again);
		if (!isInEditMode()) {
			btn_again.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ViewFlipper  rootViewFlipper = (ViewFlipper) getParent();
					rootViewFlipper.removeViews(2, 4);
					rootViewFlipper.showNext();
				}
			});
		}
		
		Button btn_close = (Button) findViewById(R.id.btn_close);
		btn_close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Activity activity = (Activity) context;
				activity.finish();
			}
		});
		super.onAttachedToWindow();
	}
}
