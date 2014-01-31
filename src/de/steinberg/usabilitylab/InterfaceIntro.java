package de.steinberg.usabilitylab;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

	
public class InterfaceIntro extends RelativeLayout{
	
	private AttributeSet mattrs;
	private Context mcontext;
	private String interfaceName;

	public InterfaceIntro(Context context) {
		super(context);
		init();
	}
	
	public InterfaceIntro(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext = context;
		mattrs = attrs;
		init();
	}
	
	public InterfaceIntro(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		parseInterfaceName();
	}

	@Override
	public int getChildCount() {
		// TODO Auto-generated method stub
		return super.getChildCount();
	}

	@Override
	protected void onAttachedToWindow() {
		
		TextView introText = (TextView) findViewById(R.id.txt_interface_intro);
		introText.setText(interfaceName);
		
		Button intro = (Button) findViewById(R.id.btn_interface_intro);
		intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				de.steinberg.usabilitylab.InterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.InterfaceViewFlipper) getParent();
				interfaceViewFlipper.showNext();
				interfaceViewFlipper.showCutomActionBar(true);
				TimeSingleton.getInstance().start();
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
//			Log.d("INsdf", String.valueOf(interfaceName));
	}

}
