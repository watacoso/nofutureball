package nofutureball;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;


public class JsonManager {
	
	 Gson gsonBuilder = new GsonBuilder()
     .registerTypeAdapter(Object.class, new NaturalDeserializer())
     .enableComplexMapKeySerialization()
     .serializeNulls()
     .setPrettyPrinting()
     .setVersion(1.0)
     .create();
	 
	 
	 public static Object loadJson(String src){
			
			
			JsonReader reader = null;
			try {
				reader = new JsonReader(
				        new InputStreamReader(new FileInputStream(src)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Gson gson = new Gson();
			
			 return gson.fromJson(reader, Object.class);
			
		}
	 



}
