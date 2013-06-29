package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Wall extends Animatable {

	Graphics g;
	private int type;
	final int padding=15;
	final float height=60;
	float length;

	public Wall(float x, float y, int type, float lenght) { // 1:horizzontalSection
															// 2:vertical
															// 3:horizzontalFull
		super(x, y, 0, 0);
		this.type = type;
		this.length = lenght;
		g = new Graphics();

	}

	public void render(Vector2f offset) {
		switch (type) {
		case 1:
			g.setColor(Color.decode("#45495F"));
			g.fillRect(position.x + offset.x, position.y + offset.y-height, length, height);
			g.setColor(Color.decode("#9AA0B6"));
			g.fillRect(position.x + offset.x, position.y + offset.y-height, length, padding);
			break;
		case 2:
			g.setColor(Color.decode("#45495F"));
			g.fillRect(position.x + offset.x, position.y + offset.y-height,padding ,length+padding+height);
			g.setColor(Color.decode("#9AA0B6"));
			g.fillRect(position.x + offset.x, position.y + offset.y-height,padding ,length+padding*2);
			break;
		case 3:
			
			break;
		}
	}




}
