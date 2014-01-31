package de.steinberg.usabilitylab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class WriterSingleton {
	
	private static WriterSingleton instance = null;
	
	private WriterSingleton(){}

	public static WriterSingleton getInstance(){
		if(instance ==null){
			instance = new WriterSingleton();
		}
		return instance;
	}
	
	public boolean writeToFile(String string, Context context) {
		
		String root = Environment.getExternalStorageDirectory().toString();
		File dir = new File("/storage/sdcard0");    
//		dir.mkdirs();
		    
		    
//		File externalStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
//		externalStorageDir.mkdirs();
		File myFile = new File(dir, "mysdfile.txt");

		if(myFile.exists())
		{
		   try
		   {
		       FileOutputStream fOut = new FileOutputStream(myFile);
		       OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		       myOutWriter.append(string);
		       myOutWriter.close();
		       fOut.close();
		       Log.d("write", "Done!");
		    } catch(Exception e)  {
		    	Toast.makeText(context, "Failed writing to File", Toast.LENGTH_SHORT).show();
		    	Log.d("write", "ERROR appending File" + e.getMessage());
		    }
		}
		else
		{
		    try {
				myFile.createNewFile();
			} catch (IOException e) {
				Log.d("write", "ERROR creating file: " + e.getMessage());
				Toast.makeText(context, "Failed to create File", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
		return true;
		
	/*	String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Log.d("write", "READ+WRITE");
		}
		return true;*/
	}
		
}
