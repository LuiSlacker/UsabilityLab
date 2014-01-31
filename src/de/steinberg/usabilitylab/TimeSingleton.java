package de.steinberg.usabilitylab;

public class TimeSingleton {

	private static TimeSingleton instance = null;
	private double start;
	
	private TimeSingleton() {}
	
	public static TimeSingleton getInstance() {
		if (instance == null) {
			instance = new TimeSingleton();
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
