package com.github.nofutureball.entity.player;

import org.newdawn.slick.geom.Vector2f;

import com.github.nofutureball.control.SoundManager;
import com.github.nofutureball.entity.Bullet;
import com.github.nofutureball.main.Augmentation;
import com.github.nofutureball.main.Game;
import com.github.nofutureball.main.KeySet;
import com.github.nofutureball.main.StatSet;
import com.github.nofutureball.map.Room;


public class Sharpshooter extends Player {

	private boolean shotReady=false;
	private int shotTimer=0;
	
	
	
	public Sharpshooter(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);
		base = StatSet.SHARPSHOOTER;
		health=maxHealth();
		passive=Augmentation.W_TRIPLESHOT;
	}
	
	public void update(Game game){
		
		shotTimer++;
		super.update(game);
	}

	protected void action1(){
		if(shotReady){
			if(passive == Augmentation.W_TRIPLESHOT)
				tripleShot(aimDirection);
			else
				standardShot(aimDirection);
			SoundManager.mixedSound("shot");
			shotReady=false;
			shotTimer=0;
		}
		else if(shotTimer > attackSpeed()){
			shotReady=true;
		}	
		movementSpeed = firingSpeed();
	}
	

	
	private void standardShot(Vector2f aimDirection){
		
		Bullet b=new Bullet(this, bulletSize(), aimDirection, bulletSpeed() + speed.length());
		parent.add(b);
		
	}
	
	private void tripleShot(Vector2f aimDirection){
		
		Bullet b1=new Bullet(this, bulletSize(), aimDirection.add(5),bulletSpeed() + speed.length());
		Bullet b2=new Bullet(this, bulletSize(), aimDirection.sub(10),bulletSpeed() + speed.length());
		Bullet b3=new Bullet(this, bulletSize(), aimDirection.add(5),bulletSpeed() + speed.length());
		parent.add(b1);
		parent.add(b2);
		parent.add(b3);
	}


	

}
