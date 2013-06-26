package nofutureball;

import java.awt.Point;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.internal.LinkedTreeMap;

public class Player extends Animatable{

	private float maxSpeed = 3;

	
	public static LinkedTreeMap STATS=(LinkedTreeMap) JsonManager.loadJson("player.json");

	
	public Player(float x, float y) {
		super(x, y, 30, 50);
		animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.PLAYER);
	}
	
	public void update(){
		
		super.update();
		
		Input input = NoFutureBall.getGameContainer().getInput();
		
		Point direction = new Point(0, 0);
		Vector2f goalSpeed = new Vector2f(0, 0);
		
		direction.x = (input.isKeyDown(Input.KEY_D)?1:0)-(input.isKeyDown(Input.KEY_A)?1:0);
		direction.y = (input.isKeyDown(Input.KEY_S)?1:0)-(input.isKeyDown(Input.KEY_W)?1:0);
		
		
		goalSpeed.x=(float) (maxSpeed*(direction.x !=0?direction.x*0.9:direction.x));
		goalSpeed.y=(float) (maxSpeed*(direction.y !=0?direction.y*0.8:direction.y*0.9));


		speed.x += (goalSpeed.x-this.speed.x)/20;
		speed.y += (goalSpeed.y-this.speed.y)/20;
		position.x += speed.x;
		position.y += speed.y;
		
		if (direction.x != 0 || direction.y != 0) {
			animations.setAnimation(Animatable.STATE.WALKING);
		} else {
			animations.setAnimation(Animatable.STATE.IDLE);
		}
		if (input.isKeyDown(Input.KEY_A)) {
			animations.setAnimation(Facing.LEFT);
		} else if (input.isKeyDown(Input.KEY_D)) {
			animations.setAnimation(Facing.RIGHT);
		}
	}
	
	
	//public void render(){
	//	g.drawRect(position.x+offset.x,position.y+offset.y,size.x,size.y);
	//}
	

}
