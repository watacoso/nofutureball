package nofutureball;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.geom.Vector2f;

public class Container extends Entity {

	protected ArrayList<Entity> list;

	public Container() {
		super(0, 0);
		list = new ArrayList<Entity>();
		// System.out.println(position.x);
	}

	public Container(float x, float y) {
		super(x, y);
		list = new ArrayList<Entity>();
	}

	public void sort() {
		Collections.sort(list);
	}

	public void add(Entity element) {
		element.parent = this;
		list.add(element);
	}

	public Entity get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}

	public void update() {
		for (int i = 0; i < size(); i++) {
			get(i).update();
		}
	}

	// public void update(Vector2f pos){
	// Vector2f offset = new
	// Vector2f((int)(position.x+pos.x),(int)(position.y+pos.y));
	// for(int i = 0; i < size(); i ++){
	// get(i).update(offset);
	// }
	// }

	public void render() {
		for (int i = 0; i < size(); i++) {
			get(i).render(position);
		}
	}

	public void render(Vector2f pos) {
		offset = new Vector2f((int) (position.x + pos.x),
				(int) (position.y + pos.y));
		for (int i = 0; i < size(); i++) {
			get(i).offset = offset;
			get(i).render(offset);
		}
	}

	public float round(float value) {
		if (value < 0)
			return (value - 0.4999f);
		else
			return (value + 0.4999f);

	}

}
