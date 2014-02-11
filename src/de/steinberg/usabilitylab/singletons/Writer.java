package de.steinberg.usabilitylab.singletons;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.widget.Toast;
import de.steinberg.usabilitylab.R;
import de.steinberg.usabilitylab.mArrayList;

public class Writer {
	
	private Context context;
	private static File file = null;
	private static File Novice = null;
	private static File Expert = null;
	
	private ArrayList<Integer> DSPInterfaces;
	
	private HashMap<Integer, mArrayList> Values =  new HashMap<Integer, mArrayList>();
	private static Writer instance = null;
	
	private Writer(Context context){
		this.context = context;
		DSPInterfaces = new ArrayList<Integer>();
		DSPInterfaces.add(R.layout.fader_2);
		DSPInterfaces.add(R.layout.single_xypad);
		DSPInterfaces.add(R.layout.fader_4);
		DSPInterfaces.add(R.layout.xypad_2);
		DSPInterfaces.add(R.layout.fader_xypad_left);
		DSPInterfaces.add(R.layout.fader_xypad_right);
		DSPInterfaces.add(R.layout.light_xypad_left);
		DSPInterfaces.add(R.layout.light_xypad_right);
	}

	public static Writer getInstance(Context context){
		
		if(instance ==null){
			instance = new Writer(context);
			File path = context.getExternalFilesDir(null);
			Novice = new File(path, "Novices.txt");
			Expert = new File(path, "Experts.txt");
			
		}
		return instance;
	}
	
	public void setExperience(String experience) {
		if (experience.equals("novice")){
			file = Novice;
		} else {
			file = Expert;
		}
		
	}
		
	public void fillHashMap(int DSPInterfaceID, mArrayList metrics){
		Values.put(DSPInterfaceID, metrics);
	}
	
	
	public void addtoHashMap(int interfaceID, ArrayList<Object> metrics){
		ArrayList<Double> tmp = new ArrayList<Double>();
		for (Object o : metrics){
			tmp.add(Double.valueOf(o.toString()));
		}
		Values.get(interfaceID).addAll(tmp);
	}
	
	public void writeToFile() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			String out = "";
			for (int interfaceID: DSPInterfaces){
				if (Values.get(interfaceID) != null) {
					out += Values.get(interfaceID).toString();
				}
			}
			writer.write(out.substring(0, out.length()-2));
			writer.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(context, "Failed to save metrics... Contact Supervisor!", Toast.LENGTH_SHORT).show();
		}
	    finally {
	        try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	     }
	}
}
