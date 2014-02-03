package de.steinberg.usabilitylab;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.flat20.fingerplay.socket.commands.midi.MidiNoteOff;
import com.flat20.fingerplay.socket.commands.midi.MidiNoteOn;

import de.steinberg.usabilitylab.network.ConnectionManager;
import de.steinberg.usabilitylab.settings.SettingsModel;
import de.steinberg.usabilitylab.settings.SettingsView;


public class MainActivity extends Activity implements OnClickListener{

	private ViewFlipper flipper;
	
	private SettingsModel mSettingsModel;
	private ActionBar actionBar;
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiNoteOn mNoteOn = new MidiNoteOn(); 
	final private MidiNoteOff mNoteOff = new MidiNoteOff();
	
	private int comparing_count = 0;
	private ArrayList<Integer> comparing = new ArrayList<Integer>();
	private  ArrayList<Double> times = new ArrayList<Double>();
	
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
		
		// set ViewFlipper and connect animations
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flipper.setInAnimation(de.steinberg.usabilitylab.Animation.inFromRightAnimation());
		flipper.setOutAnimation(de.steinberg.usabilitylab.Animation.outToLeftAnimation());

		
		// handle ActionBars Custom View Compare Button Events
	    Button btn_compare = (Button) actionBar.getCustomView().findViewById(R.id.btn_compare);
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
       
       // connect onClickListeners 
       Button btn_done = (Button) actionBar.getCustomView().findViewById(R.id.btn_done);
       btn_done.setOnClickListener(this);
        
       Button btn_start = (Button) findViewById(R.id.btn_start);
       btn_start.setOnClickListener(this);
	}
        
	@Override
	protected void onResume() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			flipper.showNext();
			break;
		case R.id.btn_done:
			times.add(TimeSingleton.getInstance().end()/1000);
			comparing.add(comparing_count);
			
			// get active InterfaceViewFlipper and display next View
			int inx = flipper.getDisplayedChild();
			de.steinberg.usabilitylab.InterfaceViewFlipper interfaceViewFlipper = (de.steinberg.usabilitylab.InterfaceViewFlipper) flipper.getChildAt(inx);
			interfaceViewFlipper.showNext();
			
			WriterSingleton.getInstance().writeToFile(String.valueOf(times),getApplicationContext());
			
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
			comparing_count = 0;
			Log.d("list", String.valueOf(comparing));
			Log.d("list", String.valueOf(times));
			break;
		default:
			break;
		}
	}

}
