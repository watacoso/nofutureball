package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class GameObject extends Sprite {

	public Vector2f speed;
	public CollisionBox collisionBox;
	public Room room,nextRoom;
	public Door door;
	private boolean transition=false;
	private boolean countMe;
	private boolean dead=false;
	private boolean collide=true;
	protected Vector2f direction,lastDirection;
	
	public float health;
	public int movementSpeed;
	
	public StatSet base;
	public StatSet augmentation;
	public ArrayList<StatSet> buffs = new ArrayList<StatSet>();
	
	
	
	
	public GameObject(Room room, float x, float y, float width, float height, boolean countMe) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		
		this.countMe=countMe;
		this.room = room;
		if(countMe){
			room.numActors++;
		}
		collisionBox = new CollisionBox(this.position.x, this.position.y + this.size.y / 2, this.size.x, this.size.y / 2);
		//System.out.println(position.y-room.position.y+collisionBox.position.x);
		speed = new Vector2f(0, 0);

	}
	
	public void setCollision(boolean value){
		collide=value;
	}

	@Override
	public void update(Game game) {
		if(collide)collisionTest();
		testLocation();
		position.x += speed.x;
		position.y += speed.y;
		collisionBox.position.x+=speed.x;
		collisionBox.position.y+=speed.y;
	}
	
	private void testLocation(){
		
		if(!transition){
			if(!boxInside(room)){
				for(int i=0;i<room.doors.size();i++){
					Door d=room.doors.get(i);
					if(pivotInside(d)){
						door=d;
						transition=true;
						nextRoom=door.rA!=room?door.rA:door.rB;
						//System.out.println("into Door at the "+d.getSide(room));
						//System.out.println(d.getSide(room));
						
					}
						
				}
			}
		}
		else{
			if(!pivotInside(door)){
				if(!pivotInside(room)){
					//System.out.println("not in old Room "+room);
					
					if(pivotInside(nextRoom)){
						//System.out.println("into new Room");
						if(countMe)
						room.numActors--;
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
		if (collisionBox.position.x + collisionBox.pivot.x < e.position.x)
			return false;
		if (collisionBox.position.y + collisionBox.pivot.y < e.position.y)
			return false;
		if (collisionBox.position.x + collisionBox.pivot.x > e.position.x + e.size.x)
			return false;
		if (collisionBox.position.y + collisionBox.pivot.y > e.position.y + e.size.y)
			return false;
		return true;
	}
	
	private boolean boxInside(Entity e){
		if (collisionBox.position.x < e.position.x)
			return false;
		if (collisionBox.position.y < e.position.y)
			return false;
		if (collisionBox.position.x + collisionBox.size.x > e.position.x + e.size.x)
			return false;
		if (collisionBox.position.y + collisionBox.size.y > e.position.y + e.size.y)
			return false;
		return true;
	}
		
	private void collisionTest() {
		
		Wall w;
		for(int i=0;i<room.walls.size();i++){
			w=room.walls.get(i);
			if(Math.abs(w.length)>Room.wallSpessor && collisionBox.checkCollision(w)){
				String direction=collisionBox.checkBoxSide(w);
				handleWallCollision(w,direction);
			}
		}
		if(nextRoom!=null)
		for(int i=0;i<nextRoom.walls.size();i++){
			w=nextRoom.walls.get(i);
			if(Math.abs(w.length)>Room.wallSpessor && collisionBox.checkCollision(w)){
				String direction=collisionBox.checkBoxSide(w);
				handleWallCollision(w,direction);
			}
		}
		Entity e;
		for(int i=0;i<parent.size();i++){
			e=parent.get(i);
			if(e==this) continue;
			//System.out.println(e.getClass().getName());
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
	
	public  float getDistance(Entity B){
		float X=B.position.x+B.pivot.x-collisionBox.position.x-collisionBox.pivot.x;
		float Y=B.position.y+B.pivot.y-collisionBox.position.y-collisionBox.pivot.y;
		return (float) Math.sqrt(X*X+Y*Y);
	}
	
	public  Vector2f getDirectionVector(Entity B){
		float X=B.position.x+B.pivot.x-collisionBox.position.x-collisionBox.pivot.x;
		float Y=B.position.y+B.pivot.y-collisionBox.position.y-collisionBox.pivot.y;
		float l=(float) Math.sqrt(X*X+Y*Y);
		return new Vector2f(X/l,Y/l);
	}
	
	public  Vector2f getDirectionVector(float x,float y){
		float X=x-collisionBox.position.x-collisionBox.pivot.x;
		float Y=y-collisionBox.position.y-collisionBox.pivot.y;
		float l=(float) Math.sqrt(X*X+Y*Y);
		return new Vector2f(X/l,Y/l);
	}
	
	public void die(){
		if(dead) return;
		dead=true;
		if(countMe){
			room.numActors--;
			System.out.println(room.numActors);
		}
		parent.remove(this);
	}
		
	class CollisionBox extends Entity{

		public CollisionBox(float x, float y, float width, float height) {
			super(x, y, width, height);
			// TODO Auto-generated constructor stub
		}
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
