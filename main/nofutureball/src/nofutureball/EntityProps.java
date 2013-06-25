package nofutureball;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class EntityProps extends HashMap{
	
	public int g1;
	public int g2;
	public int g3;
	public int g4;
	
	public EntityProps(){
		
	}
	
	public void loadJson(String src){
		JsonReader reader = null;
		try {
			reader = new JsonReader(
			        new InputStreamReader(new FileInputStream(src)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		EntityProps ent = gson.fromJson(reader, EntityProps.class);
		
	}
}
