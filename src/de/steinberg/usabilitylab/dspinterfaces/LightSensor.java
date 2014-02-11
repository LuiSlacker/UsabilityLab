package de.steinberg.usabilitylab.dspinterfaces;

import de.steinberg.usabilitylab.R;
import de.steinberg.usabilitylab.R.drawable;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LightSensor extends AbstractDSPInterface implements SensorEventListener{

	private Context context;
	private AttributeSet attrs;
	private Bitmap sun, raw_sun;
	private float sun_size, sun_size_init;
	private Boolean calibrated = false;
	private int value_sun, channel, controllerNumber;
	
	private SensorManager sensorManager;
	private Sensor sensorLight;
	
	public LightSensor(Context context) {
		super(context);
		this.context = context;
		this.attrs = attrs;
		init();
	}
	public LightSensor(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.attrs = attrs;
		init();
	}
	public LightSensor(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.attrs = attrs;
		init();
	}
	
	private void init() {
		
		/******************************************************************************************
		 ** Parse ControllerNumber from custom XMl
		 ******************************************************************************************/
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.de_steinberg_usabilitylab_dspinterfaces_LightSensor);
						
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
		    int attr = a.getIndex(i);
		    switch (attr) {
		    	case R.styleable.de_steinberg_usabilitylab_dspinterfaces_LightSensor_channelLight:
		            channel = a.getInt(attr, 0);
//		            Log.d("Pad", "Channel: " + channelNumber);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_dspinterfaces_LightSensor_controllerNumber_Light:
		            controllerNumber = a.getInt(attr, 0);
//		            Log.d("Pad", "Controller Number: " + controllerNumber);
		            break;
		        
		    }
		}
		a.recycle();
		
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		registerListener();
	}
		
	@Override
	protected void onAttachedToWindow() {
		raw_sun = BitmapFactory.decodeResource(getResources(), R.drawable.sun);
		invalidate();
		super.onAttachedToWindow();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		sun = Bitmap.createScaledBitmap(raw_sun, (int)sun_size, (int)sun_size, false);
		canvas.drawBitmap(sun, getWidth()/2-sun.getWidth()/2, getHeight()/2-sun.getHeight()/2, new Paint());
	}
	
	public void registerListener() {
		sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_FASTEST);
	}
	
	public void unregisterListener() {
		sensorManager.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
			if (!calibrated) {
				sun_size_init = event.values[0];
				calibrated = true;
			}
//			Log.d("sun", String.valueOf("init: "+sun_size_init));
			sun_size = event.values[0];
//			Log.d("sun", String.valueOf("value: "+sun_size));
			sun_size = (sun_size-20)/sun_size_init;
			float tmp = Math.max(0, Math.min(sun_size, 1));
//			Log.d("sun", String.valueOf("normalized: "+sun_size));
			value_sun= (int) Math.max(0, Math.min(tmp * 0x7F, 0x7F));
//			Log.d("sun", String.valueOf("MIDI: "+value_sun));
			sun_size = sun_size*550+100;
			sendControlChange(value_sun, channel, controllerNumber);
			
			invalidate();
		}
	}
	@Override
	public int[] getValues() {
		// TODO Auto-generated method stub
		return new int[]{value_sun};
	}
}
