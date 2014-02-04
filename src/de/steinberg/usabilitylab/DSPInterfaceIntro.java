package de.steinberg.usabilitylab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

	
public class DSPInterfaceIntro extends RelativeLayout{
	
	private String DSPInterfaceName;
	private Context context;

	public DSPInterfaceIntro(Context context, String name) {
		super(context);
		this.DSPInterfaceName = name;
		this.context = context;
		init();
	}
	
	private void init() {
		Activity  activity = (Activity) context;
		activity.getLayoutInflater().inflate(R.layout.dsp_interface_intro, this,true);
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return super.getChildCount();
	}

	@Override
	protected void onAttachedToWindow() {
		
		TextView introText = (TextView) findViewById(R.id.txt_interface_intro);
		introText.setText(DSPInterfaceName);
		
		Button intro = (Button) findViewById(R.id.btn_interface_intro);
		intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				de.steinberg.usabilitylab.DSPInterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.DSPInterfaceViewFlipper) getParent();
				interfaceViewFlipper.showNext();
				interfaceViewFlipper.showCutomActionBar(true);
				TimeSingleton.getInstance().start();
			}
		});
		super.onAttachedToWindow();
	}

}
