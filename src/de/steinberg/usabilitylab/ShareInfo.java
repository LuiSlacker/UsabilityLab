package de.steinberg.usabilitylab;

public class ShareInfo {

	private static ShareInfo instance = null;
	private float lux_init;
	
	private ShareInfo() {}
	
	public static ShareInfo getInstance() {
		if (instance == null) {
			instance = new ShareInfo();
		}
		return instance;
	}
	
	public void getluxinit(float lux_init) {
		this.lux_init = lux_init;
	}
	
	public float getluxinit() {
		return lux_init;		
	}
}
