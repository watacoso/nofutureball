package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Wall extends Entity {

	Graphics g;
	private int type;
	final int padding = 15;
	final float height = 80;
	float length;
	
	public Room room;

	public Wall(Room room,float x, float y, int type, float lenght) { // 1:horizzontalSection
															// 2:vertical
															// 3:horizzontalFull
		super(x, y);
		this.type = type;
		this.length = lenght;

		switch (type) {
		case 1:
			size.x = length;
			size.y = padding;
			break;
		case 2:
			size.y = length;
			size.x = padding;
			break;
		}
		this.room=room;
		g = new Graphics();

	}

	public void render(Vector2f offset) {

		switch (type) {
		case 1:
			g.setColor(g.getBackground());
			g.fillRect((int) (position.x + offset.x), (int) (position.y
					+ offset.y - height), length, padding);
			g.setColor(Color.decode("#45495F"));
			g.fillRect((int) (position.x + offset.x), (int) (position.y
					+ offset.y - height), length, height);
			g.setColor(Color.decode("#9AA0B6"));
			g.fillRect((int) (position.x + offset.x), (int) (position.y
					+ offset.y - height), length, padding);

			break;
		case 2:
			g.setColor(Color.decode("#45495F"));
			g.fillRect((int) (position.x + offset.x), (int) (position.y
					+ offset.y - height), padding, length + padding + height);
			g.setColor(Color.decode("#9AA0B6"));
			g.fillRect((int) (position.x + offset.x), (int) (position.y
					+ offset.y - height), padding, length + padding * 2);
			break;
		case 3:
			break;
		}
	}

}
