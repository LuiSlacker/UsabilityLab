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
	private  Animation slide_in_left, slide_out_right;
	
	private SettingsModel mSettingsModel;
	private ActionBar actionBar;
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiNoteOn mNoteOn = new MidiNoteOn(); 
	final private MidiNoteOff mNoteOff = new MidiNoteOff();
	
	private int comparing_count = 0;
	private double time;
	private ArrayList<Integer> comparing = new ArrayList<Integer>();
	private  ArrayList<Double> times = new ArrayList<Double>();
	private ArrayList<RadioGroup> rGroup = new ArrayList<RadioGroup>();
	private ArrayList<Object> rButton = new ArrayList<Object>();
	
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
		flipper.setInAnimation(inFromRightAnimation());
		flipper.setOutAnimation(outToLeftAnimation());

		
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
        
       Button btn_questionare_end = (Button) findViewById(R.id.questionare_send);
       btn_questionare_end.setOnClickListener(this);
        
       Button btn_4slider_intro = (Button) findViewById(R.id.btn_4slider_intro);
       btn_4slider_intro.setOnClickListener(this);
       
       Button btn_4slider_rating = (Button) findViewById(R.id.btn_fader4_rating);
       btn_4slider_rating.setOnClickListener(this);
       
       Button btn_2xypads_intro = (Button) findViewById(R.id.btn_2xypads_intro);
       btn_2xypads_intro.setOnClickListener(this);
       
       Button btn_2xypads_rating = (Button) findViewById(R.id.btn_xypad_2_rating);
       btn_2xypads_rating.setOnClickListener(this);
       
       Button btn_fader_xypad_intro = (Button) findViewById(R.id.btn_fader_xypad_intro);
       btn_fader_xypad_intro.setOnClickListener(this);
       
       Button btn_fader_xypad_rating = (Button) findViewById(R.id.btn_fader_xypad_rating);
       btn_fader_xypad_rating.setOnClickListener(this);
       
       //push RadioGroups into ArrayList 
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup1));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup2));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup3));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup4));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup5));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup6));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup7));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup8));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup9));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup10));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup11));
       rGroup.add((RadioGroup) findViewById(R.id.radioGroup12));
       
       
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
	
	//ViewFlipper Animations
	protected Animation inFromRightAnimation() {
		 
        Animation inFromRight = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, +1.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(1000);
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
        return outtoLeft;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			flipper.showNext();
			break;
		case R.id.questionare_send:
			flipper.showNext();
			break;
		case R.id.btn_4slider_intro:
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
			flipper.showNext();
			time = System.currentTimeMillis();
			break;
		case R.id.btn_fader4_rating:
			if (analyseRating() == true) {
				resetRadioButtons();
				flipper.showNext();
			} else {
				showAlert("Please rate all statements!");
			}
			rButton.clear();
			break;
		case R.id.btn_2xypads_intro:
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
			flipper.showNext();
			time = System.currentTimeMillis();
			break;
		case R.id.btn_xypad_2_rating:
			if (analyseRating()) {
				resetRadioButtons();
				flipper.showNext();
			} else {
				showAlert("Please rate all statements!");
			}
			rButton.clear();
			break;
		case R.id.btn_fader_xypad_intro:
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
			flipper.showNext();
			time = System.currentTimeMillis();
			break;
		case R.id.btn_fader_xypad_rating:
			if (analyseRating() == true) {
				resetRadioButtons();
				flipper.showNext();
			} else {
				showAlert("Please rate all statements!");
			}
			rButton.clear();
			break;
		case R.id.btn_done:
			times.add(System.currentTimeMillis()-time);
			comparing.add(comparing_count);
			flipper.showNext();
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
			comparing_count = 0;
			Log.d("list", String.valueOf(comparing));
			Log.d("list", String.valueOf(times));
			break;
		default:
			break;
		}
	}
	
	
	private void showAlert(String string) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(string);
		builder.setCancelable(false);
		builder.setPositiveButton("OK", new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private boolean analyseRating() {
		// fill Tag-Object ArrayList with selection in each RadioGroup
		for (RadioGroup radioGroup:rGroup){
			rButton.add(findselectedRadioButton(radioGroup));
		}
		Log.d("list", String.valueOf(rButton));
		// if one RadioGroup has no selected RadioButton return false
		
		return !rButton.contains(null);
		
		//if (rButton.contains(null)){
		//	return false;
		//} else return true;
	}

	private Object findselectedRadioButton(RadioGroup rGroup) {
		//get selected RedioButtonID
		int selectedId =  rGroup.getCheckedRadioButtonId();
		//if selection exists --> return tag --otherwise return null
		if (selectedId != -1) {
			RadioButton rB = (RadioButton) findViewById(selectedId);
			return rB.getTag();
		} else return null; 
	}
	
	/*private void resetRadioButtons() {
		for (RadioGroup radioGroup:rGroup){
			radioGroup.clearCheck();
		}
	}*/

}
