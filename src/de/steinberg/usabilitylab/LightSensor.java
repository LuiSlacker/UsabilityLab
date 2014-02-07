package de.steinberg.usabilitylab;

import android.content.Context;
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

public class LightSensor extends View implements SensorEventListener{

	private Context context;
	private Bitmap sun, raw_sun;
	private float sun_size, sun_size_init = 1130;
	
	private SensorManager sensorManager;
	private Sensor sensorLight;
	
	public LightSensor(Context context) {
		super(context);
		this.context = context;
		init();
	}
	public LightSensor(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	public LightSensor(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}
	
	private void init() {
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
			sun_size = event.values[0];
			sun_size = sun_size/sun_size_init;
			sun_size = sun_size*550+100;
			invalidate();
		}
	}
}
