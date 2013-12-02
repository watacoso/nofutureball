package playerPackage;

import mainPackage.Augmentation;
import mainPackage.KeySet;
import mainPackage.StatSet;
import mapElements.Room;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import controlPackage.SoundManager;
import entityPackage.Bullet;

/**
 * The Sharpshooter Class
 * @author watacoso
 *
 */
public class Sharpshooter extends Player {

	private boolean shotReady=false;
	private int shotTimer=0;
	
    /**
     * Constructor
     * @param room The Room this sharpshooter shall spawn
     * @param x X-Position
     * @param y Y-Position
     * @param keySet keySet this Player is using
     */
	public Sharpshooter(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);
		base = StatSet.SHARPSHOOTER;
		health=maxHealth();
		passive=Augmentation.W_TRIPLESHOT;
	}
	
	public void update(GameContainer game){
		
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
	
    /*
     * Augmentation functions
     */
	
    /**
     * Normal shot without augm
     * @param aimDirection Vector2f with direction of the shot
     */	
	private void standardShot(Vector2f aimDirection){
		
		Bullet b=new Bullet(this, bulletSize(), aimDirection, bulletSpeed() + speed.length());
		parent.add(b);
		
	}
	
    /**
     * triple shot augm
     * @param aimDirection Vector2f direction of the bullets
     */	
	private void tripleShot(Vector2f aimDirection){
		
		Bullet b1=new Bullet(this, bulletSize(), aimDirection.add(5),bulletSpeed() + speed.length());
		Bullet b2=new Bullet(this, bulletSize(), aimDirection.sub(10),bulletSpeed() + speed.length());
		Bullet b3=new Bullet(this, bulletSize(), aimDirection.add(5),bulletSpeed() + speed.length());
		parent.add(b1);
		parent.add(b2);
		parent.add(b3);
	}


	

}

