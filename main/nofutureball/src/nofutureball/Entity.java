package nofutureball;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {

	public Vector2f position;
	public Vector2f pivot;
	public Vector2f size;

	public Graphics g = new Graphics();

	public Entity(float x, float y, float width, float height) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(0, 0);
	}

	public Entity(float x, float y, float width, float height, float pivotX,
			float pivotY) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(pivotX, pivotY);
	}

	public void update() {

	}

	public void render(Vector2f offset) {
		// g.drawRect((float)(position.x + offset.x - pivot.x),
		// (float)(position.y + offset.y - pivot.y), (float)(size.x),
		// (float)(size.y));
	}

	public void render() {
		// placeholder
		// g.drawRect((float)(position.x - pivot.x), (float)(position.y -
		// pivot.y), (float)(size.x), (float)(size.y));
	}
}
