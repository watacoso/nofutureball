package entityPackage;

import java.util.ArrayList;
import java.util.Collections;

import mainPackage.Camera;

import org.newdawn.slick.GameContainer;

/**
 * A container is a collection of instances of the Entity class. it exports capabilities of the ArrayList Class, 
 * an update and a render function.
 * Since Container extends Entity, a container can contain other containers.
 * 
 * @author watacoso
 *
 */

public class Container extends Entity {

	public ArrayList<Entity> list;

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

	/**
	 * calls the update() function of the container in the order they are placed 
	 */
	
	public void update(GameContainer gc) {
		for (int i = 0; i < size(); i++) {
			get(i).update(gc);
		}
	}

	/**
	 * calls the render() function of the elements on the container in the order they are placed 
	 */
	
	
	public void render(Camera cam) {
		for (int i = 0; i < size(); i++) {
			get(i).render(cam);
		}
	}



}
