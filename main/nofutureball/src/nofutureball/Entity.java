package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Entity {
	
	private SpriteSheet spritesheet;
	public Vector2f position;
	public Vector2f speed;
	private boolean collider;
	protected ArrayList<Animation> animations;
	private int currentAnimation;
	
	
	public Entity(float x,float y,float width,float height){
		position=new Vector2f(x,y);
		
	}
	
	public Entity(float x,float y,float width,float height,SpriteSheet spritesheet){
		position=new Vector2f(x,y);
		this.spritesheet=spritesheet;
		animations=new ArrayList<Animation>();
	}
	
	public void update(){
		position.x+=speed.x;
		position.y=speed.y;
	}
	
	public SpriteSheet getSpritesheet() {
		return spritesheet;
	}

	public void setSpritesheet(SpriteSheet spritesheet) {
		this.spritesheet = spritesheet;
	}


	public boolean isCollider() {
		return collider;
	}

	public void setCollider(boolean collider) {
		this.collider = collider;
	}

	public Animation getAnimation() {
		return animations.get(currentAnimation);
	}

	public void setAnimation(int index) {
		if(animations.get(index)==null) return;
		currentAnimation=index;
		animations.get(index).start();
	}

	public int getCurrentAnimation() {
		return currentAnimation;
	}


	
	
}
