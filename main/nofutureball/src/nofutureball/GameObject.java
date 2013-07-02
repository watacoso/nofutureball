package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class GameObject extends Animatable {

	public Vector2f speed;

	private CollisionBox collisionBox;
	private Vector2f boxPos, boxSize;

	public Room room;

	public GameObject(Room room, float x, float y, float width, float height) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		this.room = room;
		collisionBox=new CollisionBox(this.position.x,this.position.y+this.size.y/2,this.size.x,this.size.y);

		speed = new Vector2f(0, 0);
		boxPos = new Vector2f(0, height / 2);
		boxSize = new Vector2f(width, height / 2);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		//stayOnRoom();

		collision();
		position.x += speed.x;
		position.y += speed.y;

	}

	public void stayOnRoom() {
		if (position.x + boxPos.x < room.position.x)
			speed.x = Math.abs(speed.x) * 0.5f;
		if (position.y + boxPos.y < room.position.y)
			speed.y = Math.abs(speed.y) * 0.5f;
		if (position.x + boxPos.x + boxSize.x > room.position.x + room.size.x)
			speed.x = -Math.abs(speed.x) * 0.5f;
		if (position.y + boxPos.y + boxSize.y > room.position.y + room.size.y)
			speed.y = -Math.abs(speed.y) * 0.5f;
	}

	public void travel() {

	}

	
	private void collision() {
		for(int i=0;i<parent.size();i++){
			Entity e=parent.get(i);
			if(e==this) continue;
			
			
			
			switch (e.getClass().getName()){
			case "Wall":
					System.out.println("wall");
				break;
			case "GameObject":
					System.out.println("test");
				break;
			}
		}
	}
	
	
	class CollisionBox extends Entity{

		public CollisionBox(float x, float y, float width, float height) {
			super(x, y, width, height);
			// TODO Auto-generated constructor stub
		}
	}


}
