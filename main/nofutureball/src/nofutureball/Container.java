package nofutureball;

import java.util.ArrayList;
import org.newdawn.slick.geom.Vector2f;

@SuppressWarnings("serial")
public class Container extends ArrayList<Entity>{

	public Vector2f position;
	
	public Container(){
		position=new Vector2f(0,0);
	}
	
	public Container(float x,float y){
		position=new Vector2f(x,y);
	}
	
	public void sort(){
		
	}
	
	public void update(){
		for(int i=0;i<this.size();i++){
			this.get(i).update();
		}
	}
	

	
	public void render(){
		for(int i=0;i<this.size();i++){
			this.get(i).render(position);
		}
	}
	
	public void render(Vector2f pos){
		Vector2f offset=position.add(pos);
		for(int i=0;i<this.size();i++){
			this.get(i).render(offset);
		}
	}
	
	
}
