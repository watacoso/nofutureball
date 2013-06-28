package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Room extends Entity {

	public Boolean occupied;

	private Graphics g = new Graphics();

	public Room(float x, float y, int width, int height) {
		super(x, y, width * 60, height * 30);
	}

	public void update() {

	}

	public void render(Vector2f offset) {
		g.setColor(Color.decode("#B8BCCC"));
		g.setLineWidth(10);
		g.drawRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		g.setColor(Color.decode("#565C76"));
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
	}

}
