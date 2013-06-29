package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Room extends Entity {

	public Boolean occupied;

	private Graphics g = new Graphics();

	public Room(float x, float y, int width, int height) {
		super(x, y, width * 60, height * 30);
	}

	public void update() {

	}

	public void render(Vector2f offset) {
		//g.setColor(Color.decode("#B8BCCC"));
		//g.setLineWidth(30);
		//g.drawRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
		g.setColor(Color.decode("#565C76"));
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);
	}
	
	public void addWalls(Container target){
		target.add(new Wall(position.x,position.y,1,size.x));
		target.add(new Wall(position.x-15,position.y,2,size.y));
		target.add(new Wall(position.x+size.x,position.y,2,size.y));
		target.add(new Wall(position.x,position.y+size.y+15,1,size.x));
		
	}

}
