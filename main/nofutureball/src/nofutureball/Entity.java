package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity implements Comparable<Entity>{

	public Vector2f position;
	public Vector2f pivot;
	public Vector2f size;



	public Entity(float x, float y) {
		position = new Vector2f(x, y);
		size = new Vector2f(0, 0);
		pivot = new Vector2f(0, 0);
	}
	
	public Entity(float x, float y, float width, float height) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(0, 0);
	}

	public Entity(float x, float y, float width, float height, float pivotX, float pivotY) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(pivotX, pivotY);
	}

	public void update() {

	}

	public void render(Vector2f offset) {

	}

	public void render() {

	}
	
	public int compareTo(Entity o) {
		if(o.position.y-10<this.position.y)
			return 1;
		if(o.position.y-10>this.position.y)
			return -1;
		return -1;
	}
}
