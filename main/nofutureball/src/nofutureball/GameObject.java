package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class GameObject extends Animatable {

	public Vector2f speed;

	public CollisionBox collisionBox;
	private Vector2f boxPos, boxSize;

	public Room room;

	public GameObject(Room room, float x, float y, float width, float height) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		this.room = room;
		collisionBox=new CollisionBox(this.position.x,this.position.y+this.size.y/2,this.size.x,this.size.y/2);

		speed = new Vector2f(0, 0);

		// TODO Auto-generated constructor stub
	}

	public void update() {
		//stayOnRoom();

		collisionTest();
		position.x += speed.x;
		position.y += speed.y;
		collisionBox.position.x+=speed.x;
		collisionBox.position.y+=speed.y;
	}

	public boolean intoRoom() {
		if (position.x + boxPos.x < room.position.x)
			return false;
		if (position.y + boxPos.y < room.position.y)
			return false;
		if (position.x + boxPos.x + boxSize.x > room.position.x + room.size.x)
			return false;
		if (position.y + boxPos.y + boxSize.y > room.position.y + room.size.y)
			return false;
		return true;
	}

	public void travel() {

	}

	
	private void collisionTest() {
		for(int i=0;i<parent.size();i++){
			Entity e=parent.get(i);
			if(e==this) continue;

			//System.out.println(e.getClass().getName());
			
			switch (e.getClass().getName()) {
			case "nofutureball.Wall":
					if(collisionBox.checkCollision(e)){
						String direction=collisionBox.checkBoxSide(e);
						switch (direction){
						case "left":
							speed.x=Math.abs(speed.x);
							break;
						case "right":
							speed.x=-Math.abs(speed.x);
							break;
						case "top":
							speed.y=Math.abs(speed.y);
							break;
						case "bottom":
							speed.y=-Math.abs(speed.y);
							break;
						}
						 System.out.println(direction);
					}
					
				break;
			case "nofutureball.Player":
				Player p=(Player) e;
				if(collisionBox.checkCollision(p.collisionBox)){
					String direction=collisionBox.checkBoxSide(p.collisionBox);
					switch (direction){
					case "left":
						speed.x=Math.abs(speed.x);
						break;
					case "right":
						speed.x=-Math.abs(speed.x);
						break;
					case "top":
						speed.y=Math.abs(speed.y);
						break;
					case "bottom":
						speed.y=-Math.abs(speed.y);
						break;
					}
					 System.out.println(direction);
				}
				break;
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
