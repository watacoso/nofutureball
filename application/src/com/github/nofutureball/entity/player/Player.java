package com.github.nofutureball.entity.player;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.github.nofutureball.control.LevelManager;
import com.github.nofutureball.control.SoundManager;
import com.github.nofutureball.entity.Bullet;
import com.github.nofutureball.entity.GameObject;
import com.github.nofutureball.main.Augmentation;
import com.github.nofutureball.main.KeySet;
import com.github.nofutureball.map.Room;


/**
 * Player class
 * Entity>GameObject>(abstract) Player>Playertypes(like Sharpshooter)
 * @author watacoso
 *
 */
public abstract class Player extends GameObject{
	
	public Augmentation passive, active; 
	

	public Action action = Action.IDLE;
	public Facing facing = Facing.DOWN;
	public Action torsoAction=Action.AIM;
	public Facing torsoFacing=Facing.UP;
	private String playerClass="SHARPSHOOTER";
	public Vector2f aimDirection=new Vector2f();
	public int cooldown=0, deathCount=0;
	public int deathTime=0;
	public static Vector2f midPoint=new Vector2f();
	protected KeySet keySet = KeySet.ONE;

    /**
     * General constructor
     * @param room Room the player spawns in
     * @param x X-Position
     * @param y Y-Position
     * @param keySet keySet this Player instance is using
     */	
	public Player(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, 200, 100, true);
		spritePivot.set(128,256);
		spriteBox.setPivot(128, 206);
		this.keySet = keySet;
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(0, 1);
		LevelManager.cam.addTarget(room);
	}

    /**
     * Constructor
     * @param room Room to spawn in
     * @param x X-Position
     * @param y Y-Position
     */	
	public Player(Room room, float x, float y) {
		super(room, x, y, 200, 100, true);
		spritePivot.set(128,236);
		spriteBox.setPivot(128, 206);
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(-1, 0);
		LevelManager.cam.addTarget(room);
	}
	
	@Override
    /**
     * Slick update-function
     */	
	public void update(GameContainer game) {
		
		//midPoint.set(LevelManager.players.get(0).box.getPosition());
		
		//if(LevelManager.players.size()>1)
		//for(int i=1;i<LevelManager.players.size();i++)
		//	midPoint.add(LevelManager.players.get(i).box.getPosition().copy().scale(1/2));
		
		Input input = game.getInput();
		
		Vector2f goalSpeed = new Vector2f(0, 0);
		direction.x = (input.isKeyDown(keySet.right) ? 1 : 0)
				- (input.isKeyDown(keySet.left) ? 1 : 0);
		direction.y = (input.isKeyDown(keySet.down) ? 1 : 0)
				- (input.isKeyDown(keySet.up) ? 1 : 0);
	
		truncate(direction,1);
		if(direction.length()!=0)	lastDirection.set(direction);

		goalSpeed.x = (float) (movementSpeed * (direction.x != 0 ? direction.x * 0.3
				: direction.x*0.1));
		goalSpeed.y = (float) (movementSpeed * (direction.y != 0 ? direction.y * 0.3
				: direction.y * 0.1));

		speed.x += (goalSpeed.x - this.speed.x) / 5;
		speed.y += (goalSpeed.y - this.speed.y) / 5;
		if(LevelManager.players.size()>1){
			if(getDistance(midPoint)>2000 && direction.length()!=0){
				speed.set(getDirectionVector(midPoint).scale(4));
			}
		}
		
		if (speed.length()<1) speed.set(0, 0);
		
		if (direction.x != 0 || direction.y != 0) {
			action = Action.WALKING;
		} else {
			action = Action.IDLE;
		}
		
		aimDirection.x = (input.isKeyDown(keySet.aimRight) ? 2 : 0)
				- (input.isKeyDown(keySet.aimLeft) ? 1 : 0);
		aimDirection.y = (input.isKeyDown(keySet.aimDown) ? 2 : 0)
				- (input.isKeyDown(keySet.aimUp) ? 1 : 0);
		
		if(lastDirection.y==0 && lastDirection.x!=0){
			if(lastDirection.x<0)
				facing=Facing.LEFT;
			else if(lastDirection.x>0){
				facing=Facing.RIGHT;
			}
		}
		else{
			if(lastDirection.y<0)
				facing=Facing.UP;
			else
				facing=Facing.DOWN;
		}
		
		if(aimDirection.length()!=0){
			if(aimDirection.y==0 && aimDirection.x!=0){
				if(aimDirection.x<0)
					torsoFacing=Facing.LEFT;
				else if(aimDirection.x>0){
					torsoFacing=Facing.RIGHT;
				}
				if(facing==Facing.UP)
					facing=Facing.DOWN;
			}
			else{
				if(aimDirection.y<0){
					torsoFacing=Facing.UP;
					if(facing==Facing.DOWN)
						facing=Facing.UP;
				}
				else{
					torsoFacing=Facing.DOWN;
					if(facing==Facing.UP)
						facing=Facing.DOWN;
				}
			}
		
			setAnimation(playerClass+"_LEGS", action + "_" + facing);
			setAnimation(playerClass+"_TORSO", "SHOOT" + "_" + torsoFacing,1);
			action1();
			hideLayer(1, false);
		}
		else{
			setAnimation(playerClass, action + "_" + facing);
			hideLayer(1, true);
			movementSpeed = normalSpeed();
		}
		super.update(game);		
	}
	
	protected void action1(){
		
	}

	public int rollDamage()
	{
		return 0;
	}
	
    /**
     * Handles damage dealing, "killing" the player
     */	
	protected void handleObjectCollision(GameObject object,String direction){
		if(object instanceof Bullet && ((Bullet) object).damagePlayer){
			SoundManager.mixedSound("enemyDamage");
			health -= /** INSERT COLLISION DAMAGE HERE */ 3 / armor();
			
			if(health < 0){
				health=0;
				die();
				SoundManager.mixedSound("playerDeath");
				deathCount++;
				boolean k=true;
				for(int i=0;i<LevelManager.players.size();i++){
					k &= LevelManager.players.get(i).dead;
				}
				if(k)
					LevelManager.gameOver();
				else{
					LevelManager.cam.removeTarget(this);
					LevelManager.cam.removeTarget(room);
					startCooldown();
				}
			}
			
			object.die();
		}
	}
	
	public void startCooldown(){
		cooldown=2*deathCount;
		deathTime=LevelManager.getTime();
	}
	
	private enum Action {
		WALKING(), IDLE(),AIM(),SHOOT();
	}
	private enum Facing {
		LEFT(), RIGHT(), UP(),DOWN();
	}

}

