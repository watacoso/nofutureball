package com.github.nofutureball.entity.player;

import com.github.nofutureball.main.KeySet;
import com.github.nofutureball.main.StatSet;
import com.github.nofutureball.map.Room;

public class AoEGuy extends Player {

	public AoEGuy(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);
		base = StatSet.AOEGUY;
	}
}
