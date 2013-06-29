package nofutureball;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.geom.Vector2f;

public class Container extends Entity{

	
	protected ArrayList<Entity> list;
	
	public Container(){
		super(0,0);
		list=new ArrayList<Entity>();
		//System.out.println(position.x);
	}
	
	public Container(float x, float y){
		super(x,y);
		list=new ArrayList<Entity>();
	}
	
	public void sort(){
		Collections.sort(list);
	}
	
	public void add(Entity element){
		list.add(element);
	}
	
	public Entity get(int index){
		return list.get(index);
	}
	
	public int size(){
		return list.size();
	}
	
	
	public void update(){
		for(int i = 0; i < size(); i ++){
			get(i).update();
		}
	}
	
	public void render(){
		for(int i = 0; i < size(); i ++){
			get(i).render(position);
		}
	}
	
	public void render(Vector2f pos){
		Vector2f offset = new Vector2f(position.x+pos.x,position.y+pos.y);
		for(int i = 0; i < size(); i ++){
			get(i).render(offset);
		}
	}
	
	
}
