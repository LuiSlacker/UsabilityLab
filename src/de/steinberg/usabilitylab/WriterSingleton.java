package de.steinberg.usabilitylab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;

public class WriterSingleton {
	
	private Context context;
	private static File file = null;
	private static File Novice = null;
	private static File Expert = null;
	private static WriterSingleton instance = null;
	
	private WriterSingleton(Context context){
		this.context = context;
	}

	public static WriterSingleton getInstance(Context context){
		
		if(instance ==null){
			instance = new WriterSingleton(context);
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
		
	public void writeToFile(String metrics) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(metrics);
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
