package nofutureball;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	
	public Vector2d position;
	public Vector2d pivot;
	public Vector2d speed;
	public Vector2d size;
	private boolean canCollide;
	public Graphics g = new Graphics();
	
	public Entity(double x, double y, double width, double height){
		position = new Vector2d(x, y);
		size = new Vector2d(width, height);
		speed = new Vector2d(0, 0);
		pivot = new Vector2d(width / 2,height / 2);
	}
	
	public Entity(double x, double y, double width, double height, double pivotX, double pivotY) {
		position = new Vector2d(x, y);
		size = new Vector2d(width, height);
		speed = new Vector2d(0, 0);
		pivot = new Vector2d(pivotX, pivotY);
	}

	
	public void update() {
		position.x += speed.x;
		position.y += speed.y;
	}
	
	public void render(Vector2f offset){
		g.drawRect((float)(position.x + offset.x - pivot.x), (float)(position.y + offset.y - pivot.y), (float)(size.x), (float)(size.y));
	}
	
	public void render(){
		//placeholder
		g.drawRect((float)(position.x - pivot.x), (float)(position.y - pivot.y), (float)(size.x), (float)(size.y));
		System.out.println("Specify " + this.getClass().getName() + "'s render method.");
	}
	
	public boolean canCollide() {
		return canCollide;
	}

	public void setCollision(boolean collide) {
		this.canCollide = collide;
	}
	
	
}
