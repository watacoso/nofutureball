package nofutureball;

import java.awt.Point;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.internal.LinkedTreeMap;

public class Player extends Animatable{

	private float maxSpeed = 3;

	
	public static LinkedTreeMap<String,?> STATS= JsonBuilder.loadProp("player.json");

	
	public Player(double x, double y, KeySet keySet)
	{
		super(x, y, 30, 50);
		this.keySet = keySet;
		animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.PLAYER);
	}
	private KeySet keySet = KeySet.ONE;
	
	public Player(double x, double y) {
		super(x, y, 30, 50);
		animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.PLAYER);
	}
	
	public void update(){
		
		super.update();
		
		Input input = NoFutureBall.getGameContainer().getInput();
		
		Point direction = new Point(0, 0);
		Vector2f goalSpeed = new Vector2f(0, 0);
		
		direction.x = (input.isKeyDown(keySet.right)?1:0)-(input.isKeyDown(keySet.left)?1:0);
		direction.y = (input.isKeyDown(keySet.down)?1:0)-(input.isKeyDown(keySet.up)?1:0);
		
		
		goalSpeed.x=(float) (maxSpeed*(direction.x !=0?direction.x*0.9:direction.x));
		goalSpeed.y=(float) (maxSpeed*(direction.y !=0?direction.y*0.8:direction.y*0.9));


		speed.x += (goalSpeed.x-this.speed.x) / 20;
		speed.y += (goalSpeed.y-this.speed.y) / 20;
		position.x += speed.x;
		position.y += speed.y;
		
		if (direction.x != 0 || direction.y != 0) {
			animations.setAnimation(Animatable.STATE.WALKING);
		} else {
			animations.setAnimation(Animatable.STATE.IDLE);
		}
		if (input.isKeyDown(keySet.left)) {
			animations.setAnimation(Facing.LEFT);
		} else if (input.isKeyDown(keySet.right)) {
			animations.setAnimation(Facing.RIGHT);
		}
	}
	

}
