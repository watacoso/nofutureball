package nofutureball;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Random;

import nofutureball.AudioCollection.AudioInfo;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class SoundManager {

	private static AudioCollection ac = new AudioCollection();

	//private static SoundStore st;

	public static void playSoundEffect(String key) {

		AudioInfo a = ac.sounds.get(key);
		System.out.println("assets/sounds/" + a.href);
		try {
			Sound s=new Sound("assets/sounds/" + a.href);
			if(a.loop)
				s.loop(a.pitch,a.volume);
			else
				s.play(a.pitch,a.volume);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void playMusic(String key) {
		AudioInfo a = ac.music.get(key);
		System.out.println("assets/sounds/" + a.href);
		try {
			Sound s=new Sound("assets/sounds/" + a.href);
			if(a.loop)
				s.loop(a.pitch,a.volume);
			else
				s.play(a.pitch,a.volume);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void mixedSound(String effect) {
		Random r=new Random();
		String key=ac.mixedEffects.get(effect).get(r.nextInt(ac.mixedEffects.get(effect).size()));
		playSoundEffect(key);
	}

	public static void extendedSound(String effect) {

	}

	@SuppressWarnings("unused")
	private int getIndex(String name) {
		return 0;
	}

	public static void load() {
		JsonReader reader = null;
		try {
			reader = new JsonReader(new InputStreamReader(new FileInputStream(
					"assets/json/soundManager.json")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();

		AudioCollection target = gson.fromJson(reader, AudioCollection.class);
		ac.mixedEffects = target.mixedEffects;
		ac.extendedEffects = target.extendedEffects;
		ac.sounds = target.sounds;
		ac.music = target.music;

		System.out.println(ac.sounds.get("tap").loop);
	}
}
