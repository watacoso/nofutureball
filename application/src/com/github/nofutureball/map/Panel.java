package com.github.nofutureball.map;

import com.github.nofutureball.control.LevelManager;
import com.github.nofutureball.entity.GameObject;
import com.github.nofutureball.entity.player.Player;
import com.github.nofutureball.main.Game;

public class Panel extends GameObject {

	public Panel(Room room, float x,String type) {
		super(room, x, -40, 48, 70, false);
		setCollision(false);
		setAnimation("PANEL","TEMP");
		// TODO Auto-generated constructor stub
	}
	
	public void update(Game game){
		super.update(game);
		
		for(int i=0;i<LevelManager.players.size();i++){
			Player p=(Player) LevelManager.players.get(i);
			if(getDistance(p)<30 && p.room==room){
				System.out.println("TEST");
			}
		}
	}
}
