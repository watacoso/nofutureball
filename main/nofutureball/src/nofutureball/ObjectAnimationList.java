package nofutureball;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class ObjectAnimationList extends Entity{

	public ObjectAnimationList(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	public ObjectAnimationList(float x, float y, float width, float height,
			float pivotX, float pivotY) {
		super(x, y, width, height, pivotX, pivotY);
		// TODO Auto-generated constructor stub
	}

	private static HashMap<String, HashMap<String,Animation>> animationList;
	private static SpriteSheet ss;
	private Animation currentAnimation;


	
	public static void init(){
		animationList=new HashMap<String, HashMap<String,Animation>>();
		
		//PLAYER//
		
		setSpriteSheet("assets/sprites/PLAYER_IDLE.png",30,63);
		addAnimation("PLAYER","IDLE_LEFT",buildAnimation(0,4,100),false);
		addAnimation("PLAYER","IDLE_RIGHT",buildAnimation(0,4,100),true);
		setSpriteSheet("assets/sprites/PLAYER_WALKING.png",30,63);
		addAnimation("PLAYER","WALKING_LEFT",buildAnimation(0,4,100),false);
		addAnimation("PLAYER","WALKING_RIGHT",buildAnimation(0,4,100),true);
		
		///ENEMY//
		
		setSpriteSheet("assets/sprites/ENEMY_IDLE.png",30,63);
		addAnimation("ENEMY","IDLE_LEFT",buildAnimation(0,4,100),false);
		addAnimation("ENEMY","IDLE_RIGHT",buildAnimation(0,4,100),true);
		setSpriteSheet("assets/sprites/ENEMY_WALKING.png",30,63);
		addAnimation("ENEMY","WALKING_LEFT",buildAnimation(0,4,100),false);
		addAnimation("ENEMY","WALKING_RIGHT",buildAnimation(0,4,100),true);
		
		//BULLET//
		
		setSpriteSheet("assets/sprites/BULLETS.png",20, 20);
		addAnimation("BULLETS","STANDARD",buildAnimation(0,3,100),false);
		
	}
	
	private static void setSpriteSheet(String ref,int tw,int th){
		try {
			ss=new SpriteSheet(ref,tw,th);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Animation buildAnimation(int index,int length,int speed){
		int a,b,x,y;
		
		a=index/ss.getWidth();
		b=index%ss.getWidth();
		
		x=(index+length-1)/ss.getWidth();
		y=(index+length-1)%ss.getWidth();
		
		System.out.println(a+" "+b+" : "+x+" "+y);
	
		return new Animation(ss,a,b,x,y,true,speed,true);
	}
	
	private static void addAnimation(String owner,String name,Animation anim,boolean flipped){
		if(flipped){
			Animation rev=new Animation();
			for(int i=0;i<anim.getFrameCount();i++){
				rev.addFrame(anim.getImage(i).getFlippedCopy(true, false), anim.getDuration(i));
			}	
			anim=rev;
		}
		if(!animationList.containsKey(owner))
			animationList.put(owner, new HashMap<String,Animation>());
		
		animationList.get(owner).put(name, anim);
	}
	
	
	
	public void setAnimation(String owner,String name){
		if(currentAnimation!=animationList.get(owner).get(name)){
			currentAnimation=animationList.get(owner).get(name);
			
		}
		
	}
	
	public  void render(Camera cam){
		
		if (currentAnimation != null) {
			Vector2f screenPos = getScreenPos(cam);
			currentAnimation.draw(screenPos.x, screenPos.y, getScaledWidth(cam), getScaledHeight(cam));
		} else {
			System.out.println("ERRRROOOOR: You have to override the animations property in subclasses!");
		}
		//super.render(cam);
	}
	
	
	
	

}
