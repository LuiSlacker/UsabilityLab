package de.steinberg.usabilitylab;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class DSPInterfaceOrderPreferences {
	
	private Context context;
	private static DSPInterfaceOrderPreferences instance = null;
	
	private DSPInterfaceOrderPreferences (Context context) {
		this.context = context;
	}
	
	public static DSPInterfaceOrderPreferences getInstance(Context context) {
		if(instance == null) {
			instance = new DSPInterfaceOrderPreferences(context);
		}
		return instance;
	}
	
	public void saveSharedPreferences(String key, int value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public int loadSavedPreferences(String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		int value = sharedPreferences.getInt(key, -1);
		return value;
	}

}
