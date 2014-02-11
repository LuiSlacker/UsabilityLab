package de.steinberg.usabilitylab.dspinterfaces;

import com.flat20.fingerplay.socket.commands.midi.MidiControlChange;

import de.steinberg.usabilitylab.network.ConnectionManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class AbstractDSPInterface extends View{

	protected int lastValue = 0;
	
	private ConnectionManager mConnectionManager = ConnectionManager.getInstance();
	final private MidiControlChange mControlChange = new MidiControlChange();
	
	public AbstractDSPInterface(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public AbstractDSPInterface(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}public AbstractDSPInterface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public abstract int[] getValues();
	
	protected void sendControlChange(int value, int channel, int controllerNumber){
		if (value != lastValue) {
			mControlChange.set(0xB0, channel, controllerNumber, value);
			mConnectionManager.send( mControlChange );		
			lastValue = value;
		}
	}
	
	

}
