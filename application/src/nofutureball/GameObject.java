package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class GameObject extends ObjectAnimationList {

	public Vector2f speed;
	public CollisionBox collisionBox;
	public Room room,nextRoom;
	public Door door;
	private boolean transition=false;
	private boolean countMe;
	private boolean dead=false;
	private boolean collide=true;
	public float maxHealth,
	damage,
	armor,
	maxSpeed,
	regen,
	attackSpeed,
	range,
	knockback;
	
	
	public GameObject(Room room, float x, float y, float width, float height,boolean countMe) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		
		this.countMe=countMe;
		this.room = room;
		if(countMe){
			room.numActors++;
		}
		
		collisionBox = new CollisionBox(this.position.x,this.position.y+this.size.y/2,this.size.x,this.size.y/2);
		//System.out.println(position.y-room.position.y+collisionBox.position.x);
		speed = new Vector2f(0, 0);

		// TODO Auto-generated constructor stub
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


}
