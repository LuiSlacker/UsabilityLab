package de.steinberg.usabilitylab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.flat20.fingerplay.socket.commands.midi.MidiControlChange;

import de.steinberg.usabilitylab.network.ConnectionManager;

public class Fader extends View{

	private int channel, controllerNumber, value_y = 0, lastValue = 0;
	private Context context;
	private AttributeSet attrs;
	private Canvas canvas;
	private Paint rect_blank, rect_filled, text;
	private float y = 600, tmp_y = 0;
	
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiControlChange mControlChange = new MidiControlChange();
	
	public Fader(Context context) {
		super(context);
		this.context = context;
		init();
	}
	public Fader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.attrs = attrs;
		init();
	}
	public Fader(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		this.attrs = attrs;
		init();
	}
	
	private void init() {
		
		/******************************************************************************************
		 ** Parse ControllerNumber from custom XMl
		 ******************************************************************************************/
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.de_steinberg_usabilitylab_Fader);
						
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
		    int attr = a.getIndex(i);
		    switch (attr) {
		    	case R.styleable.de_steinberg_usabilitylab_Fader_channelFader:
		            channel = a.getInt(attr, 0);
//		            Log.d("Pad", "Channel: " + channelNumber);
		            break;
		        case R.styleable.de_steinberg_usabilitylab_Fader_controllerNumber:
		            controllerNumber = a.getInt(attr, 0);
//		            Log.d("Pad", "Controller Number: " + controllerNumber);
		            break;
		        
		    }
		}
		a.recycle();
		
		rect_blank = new Paint();
		rect_blank.setColor(Color.WHITE);
		rect_blank.setStyle(Paint.Style.STROKE);
		rect_blank.setStrokeWidth(20);
		
		
		rect_filled = new Paint();
		rect_filled.setColor(getResources().getColor(R.color.fader));
		
//		text = new Paint();
//		text.setColor(Color.WHITE);
//		text.setTextSize(50);	 
		
		invalidate();
	}
	
	private void sendControlChange(int value, int channel, int controllerNumber){
		if (value != lastValue) {
			mControlChange.set(0xB0, channel, controllerNumber, value);
			mConnectionManager.send( mControlChange );		
			lastValue = value;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;
		
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), rect_blank);
		canvas.drawRect(10, y, canvas.getWidth()-10, canvas.getHeight()-10, rect_filled);
		
//		canvas.drawText(String.valueOf(y), 80, 500, text);
//		canvas.drawText(String.valueOf(tmp_y), 80, 550, text);
//		canvas.drawText(String.valueOf(value_y), 80, 600, text);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			y = event.getY();
			checkBorders();
			tmp_y = (y-10)/(canvas.getHeight()-20);
			tmp_y = Math.abs(tmp_y-1);
			value_y = (int) Math.max(0, Math.min(tmp_y * 0x7F, 0x7F));
			sendControlChange(value_y, channel, controllerNumber);
			invalidate();
		}
		super.onTouchEvent(event);
		return true;
	}
	private void checkBorders() {
		
		y = (int) Math.max(10, Math.min(y, getHeight()-10));
	}

}

