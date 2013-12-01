package com.github.nofutureball.entity.enemy;

import com.github.nofutureball.entity.Bullet;
import com.github.nofutureball.entity.GameObject;
import com.github.nofutureball.main.Game;
import com.github.nofutureball.map.Room;

public class RoboRifler extends Enemy{

	private int shootTimer=0;
	
	public RoboRifler(Room room, float x, float y) {
		super(room, x, y);
		// TODO Auto-generated constructor stub
	}

	
		protected void roomAI(Game game){
	followTarget(game);
		if(target!= null && getDistance(target)<range()){
			fireRoutine(target);
			
		}
		//else{
		//	for(int i=0;i<LevelManager.players.size();i++){
		//		if(getDistance(LevelManager.players.get(i))<1000)
		//			fireRoutine((GameObject) LevelManager.players.get(i));
		//	}
		//}
		shootTimer++;
	}
	
	//ROBORIFLER GENERAL ACTIONS//
	
	private void fireRoutine(GameObject target){
		if(shootTimer<attackSpeed()) return;
		Bullet b=new Bullet(this, bulletSize(), getDirectionVector(target), bulletSpeed() + speed.length());
		parent.add(b);
		shootTimer=0;
	}
	
}
