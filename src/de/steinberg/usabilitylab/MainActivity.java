package de.steinberg.usabilitylab;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.flat20.fingerplay.socket.commands.midi.MidiNoteOff;
import com.flat20.fingerplay.socket.commands.midi.MidiNoteOn;

import de.steinberg.usabilitylab.network.ConnectionManager;
import de.steinberg.usabilitylab.settings.SettingsModel;
import de.steinberg.usabilitylab.settings.SettingsView;


public class MainActivity extends Activity {

	private ViewFlipper flipper;
	private  Animation slide_in_left, slide_out_right;
	
	private SettingsModel mSettingsModel;
	private ActionBar actionBar;
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiNoteOn mNoteOn = new MidiNoteOn(); 
	final private MidiNoteOff mNoteOff = new MidiNoteOff();
	
	private int comparing_count = 0;
	private double time;
	private  double[] times = new double[4];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper);
		
		// Fullscreen, Landscape and dimed SoftButtons on Activity
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE );
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// insert custom View in ActionBar
		actionBar = getActionBar();
		actionBar.setCustomView(R.layout.custom_actionbar);
		
		// load Server preferences
		mSettingsModel = SettingsModel.getInstance();
		mSettingsModel.init(this);
		
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flipper.setInAnimation(inFromRightAnimation());
		flipper.setOutAnimation(outToLeftAnimation());

		
		// Handle Custom View Button Events
	    Button btn_compare = (Button) actionBar.getCustomView().findViewById(R.id.button1);
        btn_compare.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					mNoteOn.set(5,60,0x7F);
	        		mConnectionManager.send( mNoteOn);
	        		mNoteOff.set(5,60,0x7F);
	        		mConnectionManager.send( mNoteOff);
	        		comparing_count++;
					break;
				case MotionEvent.ACTION_UP:
					mNoteOn.set(5,60,0x7F);
	        		mConnectionManager.send( mNoteOn);
					mNoteOff.set(5,60,0x7F);
	        		mConnectionManager.send( mNoteOff);
					break;
				default:
					break;
				}
				return false;
			}
		});
        
        Button btn_done = (Button) actionBar.getCustomView().findViewById(R.id.button2);
        btn_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				times[0] = System.currentTimeMillis()-time;
				flipper.showNext();
			}
		});
        
        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flipper.showNext();
			}
		});
        
        Button btn_q_end = (Button) findViewById(R.id.questionare_send);
        btn_q_end.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flipper.showNext();
			}
		});
        
        Button btn_4slider_intro = (Button) findViewById(R.id.btn_4slider_intro);
        btn_4slider_intro.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flipper.showNext();
				actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
			}
		});
	}
	
	@Override
	protected void onResume() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent settingsIntent = new Intent(getApplicationContext(), SettingsView.class);
	        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);    
		    startActivity(settingsIntent);
            return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	//ViewFlipper Animations
	protected Animation inFromRightAnimation() {
		 
        Animation inFromRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(1000);
        //inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
	}

	//ViewFlipper Animations
	protected Animation outToLeftAnimation() {
        Animation outtoLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, -1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(1000);
        //outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
	}

}
