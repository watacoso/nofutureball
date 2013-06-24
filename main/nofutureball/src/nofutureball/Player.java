package nofutureball;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Entity{

	private float maxSpeed=1;
	
	public Player(float x, float y) {
		
		super(x, y, 120, 675);
				
	}
	
	public void update(){
		
		super.update();
		
		Input input=NoFutureBall.getGameContainer().getInput();
		
		Vector2f direction=new Vector2f(0,0);
		Vector2f goalSpeed=new Vector2f(0,0);
		
		direction.x=(input.isKeyDown(Input.KEY_D)?1:0)-(input.isKeyDown(Input.KEY_A)?1:0);
		direction.y=(input.isKeyDown(Input.KEY_S)?1:0)-(input.isKeyDown(Input.KEY_W)?1:0);
		
		
		goalSpeed.x=(float) (maxSpeed*(direction.x !=0?direction.x*0.9:direction.x));
		goalSpeed.y=(float) (maxSpeed*(direction.y!=0?direction.y*0.8:direction.y*0.9));


		speed.x+=(goalSpeed.x-this.speed.x)/20;
		speed.y+=(goalSpeed.y-this.speed.y)/20;

	}
	
	
	public void render(){
		g.drawRect(position.x,position.y,size.x,size.y);
	}

}
