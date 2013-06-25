package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class Room extends Entity{

	public Room(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public void update(){
		
	}
	
	public void render(Vector2f offset){
		g.drawRect(position.x+offset.x,position.y+offset.y,size.x,size.y);
	}

}
