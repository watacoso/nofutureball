package nofutureball;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class EntityProps{
	
	public int g1;
	public int g2;
	private int g3;
	
	String jsonText="{g1:1,g2:2,g3:3}";
	
	public EntityProps() throws FileNotFoundException{
		Gson gson=new Gson();
		JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream("player.json")));

	}
	
}
