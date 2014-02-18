package de.steinberg.usabilitylab;

import com.flat20.fingerplay.socket.commands.midi.MidiNoteOn;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import de.steinberg.usabilitylab.network.ConnectionManager;
import de.steinberg.usabilitylab.singletons.Timer;

	
public class DSPInterfaceIntro extends RelativeLayout{
	
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiNoteOn mNoteOn = new MidiNoteOn(); 
	private int DSPInterfaceBG;
	private Context context;
	private int INIT_LOADING = 2000;
	private Button intro;
	private ProgressBar spinner;

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
		
		intro = (Button) findViewById(R.id.btn_interface_intro);
		intro.setVisibility(GONE);
		intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				de.steinberg.usabilitylab.DSPInterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.DSPInterfaceViewFlipper) getParent();
				interfaceViewFlipper.showNext();
				interfaceViewFlipper.showCutomActionBar(true);
				mNoteOn.set(0,1,0x7F);
        		mConnectionManager.send( mNoteOn);
        		Log.d("counterbalanced", "started");
				Timer.getInstance().start();
				
			}
		});
		
		spinner = (ProgressBar) findViewById(R.id.progressBar1);
		
		super.onAttachedToWindow();
	}
	
	public void loading() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
				{
					@Override
					public void run() {
						spinner.setVisibility(GONE);
						intro.setVisibility(VISIBLE);
					}
				}, INIT_LOADING);
	}
}
