package nofutureball;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity implements Comparable<Entity> {

	public Vector2f position;
	public Vector2f pivot;
	public Vector2f size;
	public Vector2f offset;

	public Container parent = null;
	private Graphics g = new Graphics();

	public Entity(float x, float y) {
		position = new Vector2f(x, y);
		size = new Vector2f(0, 0);
		pivot = new Vector2f(0, 0);
		offset = new Vector2f();
	}

	public Entity(float x, float y, float width, float height) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(0, 0);
		offset = new Vector2f();
	}

	public Entity(float x, float y, float width, float height, float pivotX, float pivotY) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(pivotX, pivotY);
		offset = new Vector2f();
	}

	public void update() {

	}

	public void update(Vector2f offset) {

	}

	public void render(Vector2f offset) {
		// System.out.println("e");
		g.drawRect(position.x + offset.x - pivot.x, position.y + offset.y
				- pivot.y, size.x, size.y);
	}

	public void render() {
		// System.out.println("e");
	}

	public int compareTo(Entity o) {
		if (o.position.y - 10 < this.position.y)
			return 1;
		if (o.position.y - 10 > this.position.y)
			return -1;
		return -1;
	}
}
