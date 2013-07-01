package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class GameObject extends Animatable {

	public Vector2f speed;

	private Vector2f boxPos, boxSize;

	public Room room;

	public GameObject(Room room, float x, float y, float width, float height) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		this.room = room;

		speed = new Vector2f(0, 0);
		boxPos = new Vector2f(-width / 2, 0);
		boxSize = new Vector2f(width, height / 2);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		// stayOnRoom();

		//collision();
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

	/*
	private void collision() {
		for (int i = 0; i < parent.size(); i++) {

			Entity e = parent.get(i);
			if (e == this)
				continue;

			if (e.getClass() == Wall.class) {
				if (position.x + boxPos.x + offset.x <= e.position.x + e.size.x
						+ e.offset.x
						&& position.x + boxPos.x + boxSize.x + offset.x >= e.position.x
								+ e.offset.x
						&& position.y + boxPos.y + offset.y <= e.position.y
								+ e.size.y + e.offset.y
						&& position.y + boxPos.y + boxSize.y + offset.y >= e.position.y
								+ e.offset.y) {
					wallCollision(e);
					// break;
				}
			}
		}
	}

	private void wallCollision(Entity w) {


		String side=getSide(w);
		
		System.out.println(side);
		
		
		if (position.x + boxPos.x + offset.x <= w.position.x + w.size.x
				+ w.offset.x
				&& position.x + boxPos.x + boxSize.x + offset.x >= w.position.x
						+ w.offset.x){
			if (position.y + boxPos.y + boxSize.y + offset.y >= w.position.y
					+ w.offset.x)
				speed.y = Math.abs(speed.y);
			else if (position.y + boxPos.y + boxSize.y + offset.y >= w.position.y
					+ w.offset.y)
				speed.y = -Math.abs(speed.y);
		}
		else
		if (position.y + boxPos.y + offset.y <= w.position.y + w.size.y
				+ w.offset.y
				&& position.y + boxPos.y + boxSize.y + offset.y >= w.position.y
						+ w.offset.y){
			if (position.x + boxPos.x + boxSize.x + offset.x >= w.position.x
					+ w.offset.x)
				speed.x = Math.abs(speed.x);
			else if (position.x + boxPos.x + boxSize.x + offset.x >= w.position.x
					+ w.offset.x)
				speed.x = -Math.abs(speed.x);
		}
		
	}
	
	private String getSide(Entity B){
		
		float w = 0.5f * (this.boxSize.x+ B.size.x);
		float h = 0.5f * (this.boxSize.y + B.size.y);
		float dx =this.boxSize.x/2 - B.size.x/2;
		float dy =this.boxSize.y/2 - B.size.y/2;

		if (Math.abs(dx) <= w && Math.abs(dy) <= h)
		{
		    //collision! 
		    float wy = w * dy;
		    float hx = h * dx;

		    if (wy > hx)
		        if (wy > -hx)
		        	return "top";
		        else
		        	return "left";
		    else
		        if (wy > -hx)
		        	return "right";
		        else
		        	return "bottom";
		}
		
		return null;
		
	}
	
	*/

}
