package nofutureball;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Entity {
	
	public Vector2f position;
	public Vector2f speed;
	private boolean canCollide;
	public Graphics g=new Graphics();
	
	public Entity(float x,float y,float width,float height){
		position=new Vector2f(x,y);
		speed=new Vector2f(0,0);
	}
	
	public Entity(float x,float y,float width,float height,SpriteSheet spritesheet){
		position=new Vector2f(x,y);


	}
	
	public void update(){
		position.x+=speed.x;
		position.y+=speed.y;
	}
	
	public void render(){
		g.drawRect(position.x,position.y,50,20);
	}
	
	public boolean canCollide() {
		return canCollide;
	}

	public void setCollide(boolean collide) {
		this.canCollide = collide;
	}

}
