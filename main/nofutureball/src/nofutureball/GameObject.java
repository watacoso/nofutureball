package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class GameObject extends Animatable {

	public Vector2f speed;

	public CollisionBox collisionBox;

	public Room room;
	public Door door;
	private boolean transition=false;
	
	public GameObject(Room room, float x, float y, float width, float height) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		this.room = room;
		
		collisionBox=new CollisionBox(this.position.x,this.position.y+this.size.y/2,this.size.x,this.size.y/2);
		//System.out.println(position.y-room.position.y+collisionBox.position.x);
		speed = new Vector2f(0, 0);

		// TODO Auto-generated constructor stub
	}

	public void update() {
		collisionTest();
		testLocation();
		position.x += speed.x;
		position.y += speed.y;
		collisionBox.position.x+=speed.x;
		collisionBox.position.y+=speed.y;
	}
	
	private void testLocation(){
		
		if(!transition){
			if(!onRoom()){
				transition=true;
				System.out.println(transition);
				for(int i=0;i<room.doors.size();i++){
					Door d=room.doors.get(i);
					if(collisionBox.checkCollision(d)){
						door=d;
						System.out.println("test"+d.position.x);
					}
						
				}
			}
		}
		else{
			if(!collisionBox.checkCollision(door)){
				room=door.rA!=room?door.rA:door.rB;
				door=null;
				transition=false;
				System.out.println(transition);
				System.out.println(room);
			}
		}
	}

	private boolean onRoom() {
		if (collisionBox.position.x < room.position.x-10)
			return false;
		if (collisionBox.position.y < room.position.y-10)
			return false;
		if (collisionBox.position.x + collisionBox.size.x > room.position.x + room.size.x+10)
			return false;
		if (collisionBox.position.y + collisionBox.size.y > room.position.y + room.size.y+10)
			return false;
		return true;
	}
	
	private boolean onDoor(Door d){
		if (collisionBox.position.x + collisionBox.size.x/2< d.position.x)
			return false;
		if (collisionBox.position.y + collisionBox.size.y/2< d.position.y)
			return false;
		if (collisionBox.position.x + collisionBox.size.x/2 > d.position.x + d.size.x)
			return false;
		if (collisionBox.position.y + collisionBox.size.y/2 > d.position.y + d.size.y)
			return false;
		return true;
	}

	public void travel() {

	}

	
	private void collisionTest() {
		
		Wall w;
		for(int i=0;i<room.walls.size();i++){
			w=room.walls.get(i);
			if(collisionBox.checkCollision(w)){
				String direction=collisionBox.checkBoxSide(w);
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
		}
		Entity e;
		for(int i=0;i<parent.size();i++){
			e=parent.get(i);
			if(e==this) continue;

			//System.out.println(e.getClass().getName());
			
			if (e.getClass()==Player.class){
				Player p=(Player) e;
				if(collisionBox.checkCollision(p.collisionBox)){
					String direction=collisionBox.checkBoxSide(p.collisionBox);
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
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void handleCollision(){
		
	}
	
	
	class CollisionBox extends Entity{

		public CollisionBox(float x, float y, float width, float height) {
			super(x, y, width, height);
			// TODO Auto-generated constructor stub
		}
	}


}
