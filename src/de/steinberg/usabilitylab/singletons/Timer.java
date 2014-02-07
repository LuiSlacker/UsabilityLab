package de.steinberg.usabilitylab.singletons;

public class Timer {

	private static Timer instance = null;
	private double start;
	
	private Timer() {}
	
	public static Timer getInstance() {
		if (instance == null) {
			instance = new Timer();
		}
		return instance;
	}
	
	public void start() {
		start = System.currentTimeMillis();
	}
	
	public double end() {
		return System.currentTimeMillis()-start;		
	}
}
