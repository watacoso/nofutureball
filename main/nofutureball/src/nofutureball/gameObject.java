package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class gameObject extends Animatable {

	public Vector2f speed;

	private Vector2f boxPos, boxSize;

	public Room room;

	public gameObject(Room room, float x, float y, float width, float height) {
		super(x + room.position.x, y + room.position.y, width, height,
				width / 2, height / 2);
		this.room = room;

		speed = new Vector2f(0, 0);
		boxPos = new Vector2f(-width / 2, 0);
		boxSize = new Vector2f(width, height / 2);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		stayOnRoom();
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

}
