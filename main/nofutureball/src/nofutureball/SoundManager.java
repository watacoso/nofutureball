package nofutureball;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


public class SoundManager {

	private class AugmentedAudio extends Sound{

		public String name;
		public int volume;
		public int pitch;
		public boolean loop;
		
		public AugmentedAudio(String name,int volume,int pitch,boolean loop) throws SlickException {
			super(name);
			this.name=name;
			this.volume=volume;
			this.pitch=pitch;
			this.loop=loop;
		}
		
	}
	
	private static HashMap<String,ArrayList<String>> mixedEffects;
	private static HashMap<String,ArrayList<String>> extendedEffects;
	private static HashMap<String,AugmentedAudio> sounds;
	private static HashMap<String,AugmentedAudio> music;
	
	public static void load(){
		
		
		JsonReader reader = null;
		try {
			reader = new JsonReader(
			        new InputStreamReader(new FileInputStream("assets/json/SoundManager.json")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		
		
		
	}

	
	public static void mixedSound(String effect){
	
		
	}
	
	public static void extendedSound(String effect){

	}
	
	private int getIndex(String name){
		return 0;
	}
}
