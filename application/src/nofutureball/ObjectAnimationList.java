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
		
		//PLAYER TMP//
		
		setSpriteSheet("assets/sprites/Player.png",128,256);
		addAnimation("PLAYER","IDLE_LEFT",buildAnimation(0,4,100),false);
		addAnimation("PLAYER","IDLE_RIGHT",buildAnimation(0,4,100),true);
		addAnimation("PLAYER","WALKING_LEFT",buildAnimation(4,4,100),false);
		addAnimation("PLAYER","WALKING_RIGHT",buildAnimation(4,4,100),true);
		
		///ENEMY TMP//
		
		setSpriteSheet("assets/sprites/Enemy.png",128,256);
		addAnimation("ENEMY","IDLE_LEFT",buildAnimation(0,4,100),false);
		addAnimation("ENEMY","IDLE_RIGHT",buildAnimation(0,4,100),true);
		addAnimation("ENEMY","WALKING_LEFT",buildAnimation(4,4,100),false);
		addAnimation("ENEMY","WALKING_RIGHT",buildAnimation(4,4,100),true);
		
		//BULLETS//
		
		setSpriteSheet("assets/sprites/BULLETS.png",20, 20);
		addAnimation("BULLETS","STANDARD",buildAnimation(0,3,100),false);
		
		//PANELS//
		
		//setSpriteSheet("assets/sprites/PANELS.png",48, 70);
		//addAnimation("PANEL","TEMP",buildAnimation(0,1,100),false);
		
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
		
		a=index/ss.getHorizontalCount();
		b=index%ss.getHorizontalCount();
		
		x=(index+length-1)%ss.getHorizontalCount();
		y=(index+length-1)/ss.getHorizontalCount();
		

		//System.out.println(a+" "+b+" : "+x+" "+y);
	
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
		}
		//super.render(cam);
	}
	
	
	
	

}
