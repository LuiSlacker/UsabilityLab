package de.steinberg.usabilitylab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.flat20.fingerplay.socket.commands.midi.MidiControlChange;

import de.steinberg.usabilitylab.network.ConnectionManager;


public class XYPad extends View implements SensorEventListener{

	private Context context;
	private AttributeSet attrs;
	private Bitmap raw_midi, midi;
	private Paint rect, coordinat, text, fill, zndFinger, transparent;
	Canvas mcanvas;
	private int color, lastValue, channelNumber, controllerNumber_x = -1, controllerNumber_y = -1, controllerNumber_z = -1, controllerNumber_accel = -1, value_accel;
	double distance;
	float x=-100,y=-100;
	long time_old = 0; 
	private boolean acceleration, drawbackground;
	
	private SensorManager sm;
	private Sensor accelerometer;
	
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiControlChange mControlChange = new MidiControlChange();
	
	public XYPad(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public XYPad(Context context, AttributeSet attrs) {
		 super(context, attrs);
		 this.context = context;
		 this.attrs = attrs;
		 init();
	}

	public XYPad(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}
	
	private void init() {
		
		/******************************************************************************************
		 ** Parse ControllerNumber from custom XMl
		 ******************************************************************************************/
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.de_steinberg_usabilitylab_Pad1);
						
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
		    int attr = a.getIndex(i);
		    switch (attr) {
		    	case R.styleable.de_steinberg_usabilitylab_Pad1_channel:
		            channelNumber = a.getInt(attr, 0);
//		            Log.d("Pad", "Channel: " + channelNumber);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Pad1_controllerNumber_x:
		            controllerNumber_x = a.getInt(attr, 0);
//		            Log.d("Pad", "Controller Number: " + controllerNumber);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Pad1_controllerNumber_y:
		            controllerNumber_y = a.getInt(attr, 0);
//		            Log.d("Pad", "Controller Number: " + controllerNumber);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Pad1_controllerNumber_z:
		            controllerNumber_z = a.getInt(attr, 0);
//		            Log.d("Pad", "Controller Number: " + controllerNumber);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Pad1_controllerNumber_accel:
		            controllerNumber_accel = a.getInt(attr, 0);
		            Log.d("Pad", "Controller Number: " + controllerNumber_accel);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Pad1_acceleration:
		            acceleration = a.getBoolean(attr, true);
		            Log.d("Pad", "Acceleration? " + acceleration);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Pad1_drawbackground:
		            drawbackground = a.getBoolean(attr, true);
		            Log.d("Pad", "drawbg? " + drawbackground);
		            break;
		    }
		}
		a.recycle();
		
		
//		sm = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
//		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		
		initializePaints();
		
		raw_midi = BitmapFactory.decodeResource(getResources(), R.drawable.midi_white);
		distance = 0; // initialized 2nd Finger distance
	}
	
	private void initializePaints() {
		
		/******************************************************************************************
		** Initialize Paint for xyPad
		******************************************************************************************/
		rect = new Paint();
		rect.setColor(Color.WHITE);
		rect.setStyle(Paint.Style.STROKE);
		rect.setStrokeWidth(20);
		
		fill = new Paint();
		fill.setColor(Color.WHITE);
		
		coordinat = new Paint();
		coordinat.setColor(Color.WHITE);
		coordinat.setStyle(Paint.Style.STROKE);
		coordinat.setStrokeWidth(5);
		
		text = new Paint();
		text.setColor(Color.BLACK);
		text.setTextSize(30);
		
		zndFinger = new Paint();
		zndFinger.setColor(Color.WHITE);
		zndFinger.setStyle(Paint.Style.STROKE);
		zndFinger.setStrokeWidth(1);
		zndFinger.setAntiAlias(true);
		
		transparent = new Paint();
		transparent.setARGB(255, 183, 12, 35);
		transparent.setStyle(Paint.Style.FILL);
	}
	
	private void sendControlChange(int value, int channel, int controllerNumber){
		long time = System.currentTimeMillis(); 
		if (value != lastValue && time-time_old>14) {
			mControlChange.set(0xB0, channel, controllerNumber, value);
			mConnectionManager.send( mControlChange );		
			lastValue = value;
			time_old = time;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mcanvas = canvas;
		
		if (drawbackground) {
			// xyPad background
			canvas.drawRect(0, 0, getWidth(), getHeight(), fill);
		}
		
		// Axis
		canvas.drawLine(50, getHeight()-50, getWidth()-50, getHeight()-50, coordinat);
		canvas.drawLine(50, 50, 50, getHeight()-50, coordinat);
		
		// Top Arrow
		canvas.drawLine(50, 50, 30, 70, coordinat);
		canvas.drawLine(50, 50, 70, 70, coordinat);
		
		// Right Arrow
		canvas.drawLine(getWidth()-50, getHeight()-50, getWidth()-70, getHeight()-70, coordinat);
		canvas.drawLine(getWidth()-50, getHeight()-50, getWidth()-70, getHeight()-30, coordinat);
		
		canvas.drawText("String", getWidth()-200, getHeight()-20, text);
		
		if (controllerNumber_z != -1) {
			float tmp_znd = Math.max(150, Math.min((float)distance, 500));
			canvas.drawCircle(x, y, (float) tmp_znd, transparent);
			canvas.drawCircle(x, y, (float) tmp_znd, zndFinger);
		}
		
		// xyPad border
		canvas.drawRect(0, 0, getWidth(), getHeight(), rect);
		
		midi = Bitmap.createScaledBitmap(raw_midi, (int)180, (int)180, false);
		canvas.drawBitmap(midi, x-midi.getWidth()/2, y-midi.getHeight()/2, new Paint());
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int count = event.getPointerCount();
		if (count  == 2 && controllerNumber_z != -1) {
			float x2 = Math.round(event.getX(1)*10)/10;
			float y2 = Math.round(event.getY(1)*10)/10;
			distance = Math.sqrt(Math.abs(x2-x)*Math.abs(x2-x)+Math.abs(y2-y)*Math.abs(y2-y));
			Log.d("distance",String.valueOf(distance));
			float tmp_z = (float) (distance-150) / 350;
//			tmp_z = Math.max(0, Math.min(tmp_z, 1));
//			tmp_z = Math.abs(tmp_z-1);
			int value_z = (int) Math.max(0, Math.min(tmp_z * 0x7F, 0x7F));
			sendControlChange(value_z, channelNumber, controllerNumber_z);
			Log.d("bammm", String.valueOf(distance)+"  "+String.valueOf(tmp_z)+"r  "+String.valueOf(value_z));

		}
		switch (event.getAction()){
		case MotionEvent.ACTION_MOVE:
			x = event.getX();
			y = event.getY();
			invalidate();
			checkBorders();
			float tmp_x = (x-midi.getWidth()/2-10)/(getWidth()-2*10-midi.getWidth()); 		// normalize x to 0-1
			int value_x = (int) Math.max(0, Math.min(tmp_x * 0x7F, 0x7F));							// map to MIDI scale (0-0x7F)
			sendControlChange(value_x, channelNumber, controllerNumber_x);										// send control on previous parsed controllerNumber
			
			if (controllerNumber_y != -1) {
				float tmp_y = (y-midi.getHeight()/2-10)/(getHeight()-2*10-midi.getHeight()); 	// normalize y to 0-1
				tmp_y = Math.abs(tmp_y-1);																// invert scale
				int value_y = (int) Math.max(0, Math.min(tmp_y * 0x7F, 0x7F));							// map to MIDI scale (0-0x7F)
				sendControlChange(value_y, channelNumber, controllerNumber_y);										// send control on previous parsed controllerNumber
				Log.d("caccel", String.valueOf(controllerNumber_accel));
				Log.d("accel", String.valueOf(acceleration));
			}
			break;
		}
		
		super.onTouchEvent(event);
		return true;
	}
	
	private void checkBorders() {
//		Log.d("x",String.valueOf(x));
		
		x = (int) Math.max(10+midi.getWidth()/2, Math.min(x, getWidth()-10-midi.getWidth()/2));
		y = (int) Math.max(10+midi.getHeight()/2, Math.min(y, getHeight()-10-midi.getHeight()/2));
	}

	public void updateAccel(int value_accel) {
		this.value_accel = value_accel;
	}
	
//	public void updateColor(int c) {
////			Log.d("accel", String.valueOf(value_accel));
//			fill.setARGB(255, c*2, 255-c*2, 0);
//			invalidate();
//			if (acceleration) {
//				sendControlChange(c, 3, controllerNumber_accel);
//			}
//	}

	public void unregisterSensor(){
		sm.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float tmp_accel = (float) ((event.values[1])/9.6);
		int value_accel = (int) Math.max(0, Math.min(tmp_accel * 0x7F, 0x7F));
		fill.setARGB(255, value_accel*2, 255-value_accel*2, 0);
		invalidate();
		if (acceleration) {
			sendControlChange(value_accel, channelNumber, controllerNumber_accel);
		}
	}
}
