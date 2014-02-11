package de.steinberg.usabilitylab;

import java.util.ArrayList;

public class mArrayList extends ArrayList<Double>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (int i=0;i<size();i++){
			output.append(get(i)+", ");
		}
		return output.toString();
	}
	
	
}
