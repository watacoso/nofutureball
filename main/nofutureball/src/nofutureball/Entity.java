package nofutureball;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Entity {
	
	public Vector2f position;
	public Vector2f pivot;
	public Vector2f speed;
	public Vector2f size;
	private boolean canCollide;
	public Graphics g=new Graphics();
	
	public Entity(float x,float y,float width,float height){
		position=new Vector2f(x,y);
		size=new Vector2f(width,height);
		speed=new Vector2f(0,0);
		pivot=new Vector2f(width/2,height/2);
	}
	
	public Entity(float x,float y,float width,float height,float pivotX,float pivotY){
		position=new Vector2f(x,y);
		size=new Vector2f(width,height);
		speed=new Vector2f(0,0);
		pivot=new Vector2f(pivotX,pivotY);
	}

	
	public void update(){
		position.x+=speed.x;
		position.y+=speed.y;
	}
	
	public void render(Vector2f offset){
		g.drawRect(position.x+offset.x-pivot.x,position.y+offset.y-pivot.y,size.x,size.y);
	}
	
	public void render(){
		g.drawRect(position.x-pivot.x,position.y-pivot.y,size.x,size.y);
	}
	
	public boolean canCollide() {
		return canCollide;
	}

	public void setCollision(boolean collide) {
		this.canCollide = collide;
	}

}
