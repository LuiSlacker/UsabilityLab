package de.steinberg.usabilitylab;

import de.steinberg.usabilitylab.singletons.Timer;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

	
public class DSPInterfaceIntro extends RelativeLayout{
	
	private int DSPInterfaceBG;
	private Context context;

	public DSPInterfaceIntro(Context context, int background) {
		super(context);
		this.DSPInterfaceBG = background;
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
		Resources res = getResources();
		Drawable t = res.getDrawable(DSPInterfaceBG); 
		setBackgroundDrawable(t);
		
		Button intro = (Button) findViewById(R.id.btn_interface_intro);
		intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				de.steinberg.usabilitylab.DSPInterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.DSPInterfaceViewFlipper) getParent();
				interfaceViewFlipper.showNext();
				interfaceViewFlipper.showCutomActionBar(true);
				Timer.getInstance().start();
			}
		});
		super.onAttachedToWindow();
	}

}
