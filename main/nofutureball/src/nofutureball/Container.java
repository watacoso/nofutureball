package nofutureball;

import java.util.ArrayList;
import java.util.Collections;

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
	
	public void remove(Entity element) {
		list.remove(element);
	}

	public Entity get(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}

	public void update(Game game) {
		for (int i = 0; i < size(); i++) {
			get(i).update(game);
		}
	}

	// public void update(Vector2f pos){
	// Vector2f offset = new
	// Vector2f((int)(position.x+pos.x),(int)(position.y+pos.y));
	// for(int i = 0; i < size(); i ++){
	// get(i).update(offset);
	// }
	// }

	public void render(Camera cam) {
		for (int i = 0; i < size(); i++) {
			get(i).render(cam);
		}
	}


	public float round(float value) {
		if (value < 0)
			return (float)(int)(value - 0.4999f);
		else
			return (float)(int)(value + 0.4999f);

	}

}
