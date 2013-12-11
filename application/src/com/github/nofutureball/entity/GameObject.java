package com.github.nofutureball.entity;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

import com.github.nofutureball.entity.player.Player;
import com.github.nofutureball.main.Augmentation;
import com.github.nofutureball.main.Camera;
import com.github.nofutureball.main.StatSet;
import com.github.nofutureball.map.Conn;
import com.github.nofutureball.map.Room;
import com.github.nofutureball.map.Wall;



/**
 * Base Class of all GameObjects
 * GameObjects can spawn in Rooms
 * @author hollowspecter
 *
 */
public class GameObject extends Sprite {

	public Vector2f speed;
	//public CollisionBox collisionBox;
	public Room room,nextRoom;
	public Conn door;
	private boolean transition=false;
	private boolean countMe;
	public boolean dead=false;
	public boolean collide=true;
	protected Vector2f direction,lastDirection;
	public RectangleF collisionBox;
	public float health;
	public int movementSpeed;
	
	public StatSet base;
	public StatSet augmentation;
	public Augmentation active;
	public ArrayList<StatSet> buffs = new ArrayList<StatSet>();

    /**
     * General Constructor
     * @param room Room to be spawned in
     * @param x X-Position in the room
     * @param y Y-Position in the room
     * @param boxWidth Width of the box
     * @param boxHeight Height of the box
     * @param countMe Whether or not this Object is counted in room.numActors
     */	
	public GameObject(Room room, float x, float y, float boxWidth, float boxHeight, boolean countMe) {
		super(x + room.position.x, y + room.position.y,boxWidth,boxHeight);
		this.countMe=countMe;
		this.room = room;
		if(countMe)
			room.numActors++;
		
		collisionBox =box;
		speed = new Vector2f(0, 0);
	}

    /**
     * Simple Constructor
     * @param x X-Position
     * @param y Y-Position
     * @param boxWidth Width of the box
     * @param boxHeight Height of the box
     */	
	public GameObject(float x, float y,  float boxWidth, float boxHeight) {
		super(x, y,boxWidth,boxHeight);
		this.countMe=false;
		collide=false;
		collisionBox=box;
		speed = new Vector2f(0, 0);
	}

    /**
     * Set Collision
     * @param value Whether or not this Object can be collided with
     */	
	public void setCollision(boolean value){
		collide=value;
	}

    /**
     * Slick update-function
     */	
	public void update(GameContainer game) {
		if(collide)collisionTest();
		if(room!=null)testLocation();
		
		position.add(speed);
		box.setPosition(position.x, position.y);
		if(collisionBox!=box){
			collisionBox.setPosition(position.x, position.y);
		}
	}
	
    /**
     * @todo document
     */	
	private void testLocation(){
		
		if(!transition){
			if(!boxInside(room)){
				for(int i=0;i<room.doors.size();i++){
					Conn d=room.doors.get(i);
					if(pivotInside(d)){
						door=d;
						transition=true;
						nextRoom=door.rA!=room?door.rA:door.rB;						
					}	
				}
			}
		}
		else{
			if(!pivotInside(door)){
				if(!pivotInside(room)){
					if(pivotInside(nextRoom)){
						if(countMe)
						room.numActors--;
						//if(this instanceof Player){
						//	LevelManager.cam.addTarget(nextRoom);
						//	LevelManager.cam.removeTarget(room);
						//}
						room=nextRoom;
						
						nextRoom=null;
						if(countMe)
						room.numActors++;
					}
				}
				else{
					//.out.println("into old Room");
				}
				door=null;
				transition=false;
			}	
		}
	}

	private boolean pivotInside(Entity e){
		if (collisionBox.getPosition().x < e.box.left())
			return false;
		if (collisionBox.getPosition().y < e.box.top())
			return false;
		if (collisionBox.getPosition().x > e.box.right())
			return false;
		if (collisionBox.getPosition().y > e.box.bottom())
			return false;
		return true;
	}
	
	private boolean boxInside(Entity e){
		if (collisionBox.left() < e.box.left())
			return false;
		if (collisionBox.top() < e.box.top())
			return false;
		if (collisionBox.right() > e.box.right())
			return false;
		if (collisionBox.bottom() >e.box.bottom())
			return false;
		return true;
	}
		
	private void collisionTest() {
		Wall w;
		for(int i=0;i<room.walls.size();i++){
			w=room.walls.get(i);
			if(Math.abs(w.length)>Room.wallSpessor && collisionBox.checkCollision(w.box)){
				String direction=collisionBox.checkBoxSide(w.box);
				handleWallCollision(w,direction);
			}
		}
		if(nextRoom!=null)
		for(int i=0;i<nextRoom.walls.size();i++){
			w=nextRoom.walls.get(i);
			if(Math.abs(w.length)>Room.wallSpessor && collisionBox.checkCollision(w.box)){
				String direction=collisionBox.checkBoxSide(w.box);
				handleWallCollision(w,direction);
			}
		}
		Entity e;
		for(int i=0;i<parent.size();i++){
			e=parent.get(i);
			if(e==this) continue;
			if (e instanceof GameObject){
				GameObject p= (GameObject) e;
				if(collisionBox.checkCollision(p.collisionBox)){
					String direction=collisionBox.checkBoxSide(p.collisionBox);
					handleObjectCollision(p,direction);
				}
			}
		}
	}
	
	protected void handleWallCollision(Wall wall,String direction){
		switch (direction){
		case "left":
			speed.x=Math.abs(speed.x)/2;
			break;
		case "right":
			speed.x=-Math.abs(speed.x)/2;
			break;
		case "top":
			speed.y=Math.abs(speed.y)/2;
			break;
		case "bottom":
			speed.y=-Math.abs(speed.y)/2;
			break;
		}
	}
	
	protected void handleObjectCollision(GameObject object,String direction){
		if(object instanceof Player)
		switch (direction){
		case "left":
			speed.x=1;
			break;
		case "right":
			speed.x=-1;
			break;
		case "top":
			speed.y=1;
			break;
		case "bottom":
			speed.y=-1;
			break;
		}
	}
	
	public  float getDistance(Vector2f B){
		float X=B.x-position.x;
		float Y=B.y-position.y;
		return (float) Math.sqrt(X*X+Y*Y);
	}
	
	public  float getDistance(Entity B){
		float X=B.position.x-position.x;
		float Y=B.position.y-position.y;
		return (float) Math.sqrt(X*X+Y*Y);
	}
	
	public  Vector2f getDirectionVector(Entity B){
		float X=B.position.x-position.x;
		float Y=B.position.y-position.y;
		float l=(float) Math.sqrt(X*X+Y*Y);
		if(l==0) return new Vector2f(0,0);
		return new Vector2f(X/l,Y/l);
	}
	
	public  Vector2f getDirectionVector(Vector2f B){
		float X=B.x-position.x;
		float Y=B.y-position.y;
		float l=(float) Math.sqrt(X*X+Y*Y);
		if(l==0) return new Vector2f(0,0);
		return new Vector2f(X/l,Y/l);
	}
	
	public  Vector2f getDirectionVector(float x,float y){
		float X=x-position.x;
		float Y=y-position.y;
		float l=(float) Math.sqrt(X*X+Y*Y);
		if(l==0) return new Vector2f(0,0);
		return new Vector2f(X/l,Y/l);
	}
	
	public void die(){
		if(dead) return;
		dead=true;
		if(countMe){
			room.numActors--;
			//System.out.println(room.numActors);
		}
		parent.remove(this);
	}
		
	public void render(Camera cam){
		super.render(cam);
	}

	
	// STATS GETTERS
	public int longDmg()
	{
		int a = 0;
		if (base != null) a += base.longDmg();
		if (augmentation != null) a += augmentation.longDmg();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).longDmg(); }
		return a;
	}
	public int shortDmg()
	{
		int a = 0;
		if (base != null) a += base.shortDmg();
		if (augmentation != null) a += augmentation.shortDmg();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).shortDmg(); }
		return a;
	}
	public int armor()
	{
		int a = 0;
		if (base != null) a += base.armor();
		if (augmentation != null) a += augmentation.armor();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).armor(); }
		return a;
	}
	public int normalSpeed()
	{
		int a = 0;
		if (base != null) a += base.normalSpeed();
		if (augmentation != null) a += augmentation.normalSpeed();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).normalSpeed(); }
		return a;
	}
	public int firingSpeed()
	{
		int a = 0;
		if (base != null) a += base.firingSpeed();
		if (augmentation != null) a += augmentation.firingSpeed();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).firingSpeed(); }
		return a;
	}
	public int range()
	{
		int a = 0;
		if (base != null) a += base.range();
		if (augmentation != null) a += augmentation.range();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).range(); }
		return a;
	}
	public int attackSpeed()
	{
		int a = 0;
		if (base != null) a += base.attackSpeed();
		if (augmentation != null) a += augmentation.attackSpeed();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).attackSpeed(); }
		return a;
	}
	public int knockback()
	{
		int a = 0;
		if (base != null) a += base.knockback();
		if (augmentation != null) a += augmentation.knockback();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).knockback(); }
		return a;
	}
	public int bulletSpeed()
	{
		int a = 0;
		if (base != null) a += base.bulletSpeed();
		if (augmentation != null) a += augmentation.bulletSpeed();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).bulletSpeed(); }
		return a;
	}
	public int bulletSize()
	{
		int a = 0;
		if (base != null) a += base.bulletSize();
		if (augmentation != null) a += augmentation.bulletSize();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).bulletSize(); }
		return a;
	}
	public int regen()
	{
		int a = 0;
		if (base != null) a += base.regen();
		if (augmentation != null) a += augmentation.regen();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).regen(); }
		return a;
	}
	public int maxHealth()
	{
		int a = 0;
		if (base != null) a += base.maxHealth();
		if (augmentation != null) a += augmentation.maxHealth();
		for (int i = 0; i < buffs.size(); i ++) { a += buffs.get(i).maxHealth(); }
		return a;
	}
	
	// Create new combined StatSet:
}

