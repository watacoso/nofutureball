package nofutureball;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Entity{

	public enum anims{
		WALK(0),SPRINT(1),ATTACK(2),DEFEND(3),DIE(4);
		
		private int value;
		private anims(int value){
			this.value=value;
		}
	};
		
	public Player(float x, float y) {
		
		super(x, y, 80, 130);
		
		SpriteSheet spriteSheet = null;
		try {
			spriteSheet = new SpriteSheet("assets/player.png",80,130,0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSpritesheet(spriteSheet);
		
		
		////////////////////////
		///////ANIMATIONS///////
		////////////////////////
		
		animations.add(anims.WALK.value,new Animation(getSpritesheet(),0,0,1,1,true,10,false));
		
	}
	
	public void update(){
		
		Input input=NoFutureBall.getGameContainer().getInput();
		
		if (input.isKeyDown(Input.KEY_UP)){
			speed.y--;
        }
        else if (input.isKeyDown(Input.KEY_DOWN)){
        	speed.y++;
        }
        else if (input.isKeyDown(Input.KEY_LEFT)){
        	speed.x--;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)){
        	speed.x++;
        }
        else{
        	speed.x*=0.5;
        	speed.y*=0.5;
        }
		
		super.update();
	}

}
