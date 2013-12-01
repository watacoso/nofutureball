package com.github.nofutureball.control;

import java.util.ArrayList;
import java.util.HashMap;

public class AudioCollection {

	public HashMap<String, ArrayList<String>> mixedEffects;
	public HashMap<String, ArrayList<String>> extendedEffects;
	public HashMap<String, AudioInfo> sounds;
	public HashMap<String, AudioInfo> music;

	public AudioCollection() {

	}

	class AudioInfo {

		public String href;
		public float volume;
		public float pitch;
		public boolean loop;

		public AudioInfo(String href, int volume, int pitch, boolean loop) {
			this.href = href;
			this.volume = volume;
			this.pitch = pitch;
			this.loop = loop;

		}
	}
}
