package nofutureball;

import org.newdawn.slick.Input;

public class Sharpshooter extends Player {

	private boolean shotReady=false;
	private int shotTimer=0;

	///////////////////////
	public int bulletSize=10
	,bulletSpeed=5;
	///////////////////////
	
	
	
	public Sharpshooter(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, keySet);

		//////////////////////
		knockback=5;
		attackSpeed=7;
		range=200;
		damage=20;
		//////////////////////
		
		passive=Augmentation.W_TRIPLESHOT;
	}
	
	public void update(Game game){
		
		Input input = NoFutureBall.getGameContainer().getInput();
		if(input.isKeyDown(keySet.action1) && shotReady){
			
			if(passive==Augmentation.W_TRIPLESHOT)
			tripleShot();
			else
				standardShot();
			SoundManager.mixedSound("shot");
			shotReady=false;
			shotTimer=0;
		}
		else if(shotTimer>attackSpeed){
				shotReady=true;
			}
		else shotTimer++;
		
		
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
		
		Bullet b=new Bullet(this, bulletSize, lastDirection,bulletSpeed);
		parent.add(b);
	}
	
	private void tripleShot(){
		
		Bullet b1=new Bullet(this, bulletSize, lastDirection.add(15),bulletSpeed);
		Bullet b2=new Bullet(this, bulletSize, lastDirection.sub(30),bulletSpeed);
		Bullet b3=new Bullet(this, bulletSize, lastDirection.add(15),bulletSpeed);
		parent.add(b1);
		parent.add(b2);
		parent.add(b3);
	}


	

}
