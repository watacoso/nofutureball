package nofutureball;

import org.newdawn.slick.Input;

public class Sharpshooter extends Player {

	private boolean shotReady=false;
	private int shotTimer=0;

	
	
	public Sharpshooter(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);

		base = StatSet.SHARPSHOOTER;
	}
	
	public void update(Game game){
		
		Input input = NoFutureBall.getGameContainer().getInput();
		if(input.isKeyDown(keySet.action1)){
			if(shotReady){
				if(passive == Augmentation.W_TRIPLESHOT)
					tripleShot();
				else
					standardShot();
				SoundManager.mixedSound("shot");
				shotReady=false;
				shotTimer=0;
			}
			else if(shotTimer > attackSpeed()){
				shotReady=true;
			}
			else shotTimer ++;
			
			movementSpeed = firingSpeed();
		}
		else
			movementSpeed = normalSpeed();
		
		
		super.update(game);
	}

	@Override
	public void execActive(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execPassive(int index) {
		// TODO Auto-generated method stub
		
	}
	
	private void standardShot(){
		
		Bullet b=new Bullet(this, bulletSize(), lastDirection, bulletSpeed() + speed.length());
		parent.add(b);
	}
	
	private void tripleShot(){
		
		Bullet b1=new Bullet(this, bulletSize(), lastDirection.add(5),bulletSpeed() + speed.length());
		Bullet b2=new Bullet(this, bulletSize(), lastDirection.sub(10),bulletSpeed() + speed.length());
		Bullet b3=new Bullet(this, bulletSize(), lastDirection.add(5),bulletSpeed() + speed.length());
		parent.add(b1);
		parent.add(b2);
		parent.add(b3);
	}


	

}
