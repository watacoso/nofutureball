package entityPackage;

import java.util.ArrayList;
import java.util.HashMap;

import mainPackage.Camera;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 * Sprite
 * directly extends the Entity class
 * adds Sprites or animation to the Entity
 * loads all the Animation
 * @author watacoso
 *
 */

public class Sprite extends Entity {

    private static HashMap<String, HashMap<String,Animation>> animationList;
    private static HashMap<String, HashMap<String,Image>> spriteList;
    //private static HashMap<String, SpriteSheet> spriteSheetList;
    private static SpriteSheet ss;
    private Animation currentAnimation;
    private Image currentImage;
    private boolean usingAnimation=false;
    public boolean dynamicStats=false;
    public RectangleF spriteBox;
    public Vector2f spriteSize=new Vector2f(0,0);
    public Vector2f spritePivot=new Vector2f(0,0);
    public Vector2f scale=new Vector2f(1,1);
    private ArrayList<Layer> layers;
	
    /**
     * Constructor
     * @param x X-Position
     * @param y Y-Position
     */
	public Sprite(float x, float y) {
		super(x, y);
		spriteBox=new RectangleF(x,y);
		layers=new ArrayList<Layer>();
		layers.add(new Layer());
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with Box Settings
	 * @param x X-Position
	 * @param y Y-Position
	 * @param width Width of the Box
	 * @param height Height of the box
	 */
	public Sprite(float x, float y, float width, float height) {
		super(x, y, width, height);
		spriteBox=new RectangleF(x,y);
		layers=new ArrayList<Layer>();
		layers.add(new Layer());
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * General Constructor
	 * @param x X-Position
	 * @param y Y-Position
	 * @param width Width of the Box
	 * @param height Height of the Box
	 * @param pivotX Pivot shift on X
	 * @param pivotY Pivot shift on Y
	 */
	public Sprite(float x, float y, float width, float height,
			float pivotX, float pivotY) {
		super(x, y, width, height, pivotX, pivotY);
		spriteBox=new RectangleF(x,y);
		layers=new ArrayList<Layer>();
		layers.add(new Layer());
	}

	/**
	 * Initialising and Loading all them images
	 * Called once in the beginning
	 */
	public static void init(){
		
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		//GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		animationList=new HashMap<String, HashMap<String,Animation>>();
		spriteList=new HashMap<String, HashMap<String,Image>>();
		
		//PLAYER TMP//
		/*Deprecated*/
		//setSpriteSheet("assets/sprites/player01_sheet_idle01.png",256,256);
		//addAnimation("PLAYER","IDLE_LEFT",buildAnimation(0,6,100),true);
		//addAnimation("PLAYER","IDLE_RIGHT",buildAnimation(0,6,100),false);
		//setSpriteSheet("assets/sprites/player01_sheet_run01.png",256,256);
		//addAnimation("PLAYER","WALKING_LEFT",buildAnimation(0,7,100),true);
		//addAnimation("PLAYER","WALKING_RIGHT",buildAnimation(0,7,100),false);
		
		/*System 1*/
		/*
		setSpriteSheet("assets/sprites/char_modular01.png",256,256);
		addAnimation("SHARPSHOOTER","IDLE_LEFT",buildAnimationFromStrip(false,0,3,100),true);
		addAnimation("SHARPSHOOTER","IDLE_RIGHT",buildAnimationFromStrip(false,0,3,100),false);
		addAnimation("SHARPSHOOTER","WALKING_LEFT",buildAnimationFromStrip(false,1,6,100),true);
		addAnimation("SHARPSHOOTER","WALKING_RIGHT",buildAnimationFromStrip(false,1,6,100),false);
		
		addAnimation("SHARPSHOOTER_LEGS","WALKING_LEFT",buildAnimationFromStrip(false,2,6,400),true);
		addAnimation("SHARPSHOOTER_LEGS","WALKING_RIGHT",buildAnimationFromStrip(false,2,6,400),false);
		addAnimation("SHARPSHOOTER_LEGS","IDLE_LEFT",buildAnimation(14,1,100),true);
		addAnimation("SHARPSHOOTER_LEGS","IDLE_RIGHT",buildAnimation(14,1,100),false);
		
		addAnimation("SHARPSHOOTER_TORSO","AIM_LEFT",buildAnimationFromStrip(false,3,1,100),true);
		addAnimation("SHARPSHOOTER_TORSO","AIM_RIGHT",buildAnimationFromStrip(false,3,1,100),false);
		addAnimation("SHARPSHOOTER_TORSO","AIM_DOWN_LEFT",buildAnimationFromStrip(false,4,1,100),true);
		addAnimation("SHARPSHOOTER_TORSO","AIM_UP_LEFT",buildAnimationFromStrip(false,5,1,100),true);
		addAnimation("SHARPSHOOTER_TORSO","AIM_DOWN_RIGHT",buildAnimationFromStrip(false,4,1,100),false);
		addAnimation("SHARPSHOOTER_TORSO","AIM_UP_RIGHT",buildAnimationFromStrip(false,5,1,100),false);
		
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_LEFT",buildAnimationFromStrip(false,3,3,100),true);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_RIGHT",buildAnimationFromStrip(false,3,3,100),false);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_DOWN_LEFT",buildAnimationFromStrip(false,4,3,100),true);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_UP_LEFT",buildAnimationFromStrip(false,5,3,100),true);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_DOWN_RIGHT",buildAnimationFromStrip(false,4,3,100),false);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_UP_RIGHT",buildAnimationFromStrip(false,5,3,100),false);
		*/
		
		/*System 2*/
		
		setSpriteSheet("assets/sprites/char_modular02.png",256,256);
		addAnimation("SHARPSHOOTER","IDLE_LEFT",buildAnimationFromStrip(false,0,4,100),true);
		addAnimation("SHARPSHOOTER","IDLE_RIGHT",buildAnimationFromStrip(false,0,4,100),false);
		addAnimation("SHARPSHOOTER","IDLE_DOWN",buildAnimationFromStrip(false,4,4,100),false);
		addAnimation("SHARPSHOOTER","IDLE_UP",buildAnimationFromStrip(false,8,4,100),false);
		
		addAnimation("SHARPSHOOTER","WALKING_LEFT",buildAnimationFromStrip(false,1,6,100),true);
		addAnimation("SHARPSHOOTER","WALKING_RIGHT",buildAnimationFromStrip(false,1,6,100),false);
		addAnimation("SHARPSHOOTER","WALKING_DOWN",buildAnimationFromStrip(false,5,4,100),false);
		addAnimation("SHARPSHOOTER","WALKING_UP",buildAnimationFromStrip(false,9,4,100),false);		
		
		//addAnimation("SHARPSHOOTER_LEGS","IDLE_LEFT",buildAnimationFromStrip(false,2,6,100),true);
		//addAnimation("SHARPSHOOTER_LEGS","IDLE_RIGHT",buildAnimationFromStrip(false,2,6,100),false);
		addAnimation("SHARPSHOOTER_LEGS","IDLE_LEFT",buildAnimation(26,1,100),true);
		addAnimation("SHARPSHOOTER_LEGS","IDLE_RIGHT",buildAnimation(26,1,100),false);
		addAnimation("SHARPSHOOTER_LEGS","IDLE_DOWN",buildAnimationFromStrip(false,6,1,100),false);
		addAnimation("SHARPSHOOTER_LEGS","IDLE_UP",buildAnimationFromStrip(false,10,1,100),false);
		
		addAnimation("SHARPSHOOTER_LEGS","WALKING_LEFT",buildAnimationFromStrip(false,2,6,100),true);
		addAnimation("SHARPSHOOTER_LEGS","WALKING_RIGHT",buildAnimationFromStrip(false,2,6,100),false);
		addAnimation("SHARPSHOOTER_LEGS","WALKING_DOWN",buildAnimationFromStrip(false,6,4,100),false);
		addAnimation("SHARPSHOOTER_LEGS","WALKING_UP",buildAnimationFromStrip(false,10,4,100),false);
		
		addAnimation("SHARPSHOOTER_TORSO","AIM_LEFT",buildAnimationFromStrip(false,3,1,100),true);
		addAnimation("SHARPSHOOTER_TORSO","AIM_RIGHT",buildAnimationFromStrip(false,3,1,100),false);
		addAnimation("SHARPSHOOTER_TORSO","AIM_DOWN",buildAnimationFromStrip(false,7,1,100),false);
		addAnimation("SHARPSHOOTER_TORSO","AIM_UP",buildAnimationFromStrip(false,11,1,100),false);
		
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_LEFT",buildAnimationFromStrip(false,3,3,100),true);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_RIGHT",buildAnimationFromStrip(false,3,3,100),false);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_DOWN",buildAnimationFromStrip(false,7,3,100),false);
		addAnimation("SHARPSHOOTER_TORSO","SHOOT_UP",buildAnimationFromStrip(false,11,3,100),false);
		
		
		///ENEMY TMP//
		
		setSpriteSheet("assets/sprites/cybot_sheet_idle01.png",256,256);
		addAnimation("ENEMY","IDLE_LEFT",buildAnimation(0,6,100),true);
		addAnimation("ENEMY","IDLE_RIGHT",buildAnimation(0,6,100),false);
		setSpriteSheet("assets/sprites/cybot_sheet_run01.png",256,256);
		addAnimation("ENEMY","WALKING_LEFT",buildAnimation(0,7,100),true);
		addAnimation("ENEMY","WALKING_RIGHT",buildAnimation(0,7,100),false);
		

		
		//BULLETS//
		
		setSpriteSheet("assets/sprites/BULLETS.png",20, 20);
		addAnimation("BULLETS","STANDARD",buildAnimation(0,3,100),false);
		
		//FLOOR//
		
		setSpriteSheet("assets/sprites/FloorTiles.png",256, 256);
		addSprite("FLOOR","R1",0,0,false);
		addSprite("FLOOR","R2",1,0,false);
		addSprite("FLOOR","R3",2,0,false);
		addSprite("FLOOR","R4",3,0,false);
		
		//WALL//
		setSpriteSheet("assets/sprites/panel.png",150,100);
		addAnimation("PANEL","A",buildAnimation(0,5,100),false);

		
		//DOOR//
		setSpriteSheet("assets/sprites/doorV.png",64,712);
		//addSprite("DOORV","OPEN","assets/sprites/doorV.png",false);
		addSprite("DOORV","OPEN",0,0,false);
		addSprite("DOORV","CLOSE",3,0,false);
		
		setSpriteSheet("assets/sprites/doorH.png",512,264);
		addSprite("DOORH","OPEN",0,0,false);
		addSprite("DOORH","CLOSE",0,3,false);
		
		//addSprite("DOORH","OPEN","assets/sprites/doorH.png",false);
		
		addSprite("DOOR","H","assets/sprites/HDoor.png",false);
		
		addSprite("DOOR","V","assets/sprites/VDoor.png",false);
		//addSprite("FLOOR","TEST","assets/sprites/tileTest.png",false);
		
		

		
	}
	
	/**
	 * Sets a spritesheet for the sprite
	 * @param ref Reference of the spritesheet
	 * @param tw tile-width
	 * @param th tile-height
	 */
	private static void setSpriteSheet(String ref,int tw,int th){
		try {
			ss=new SpriteSheet(ref,tw,th);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Builds up the Animation
	 * @todo document the params
	 * @param index
	 * @param length
	 * @param speed
	 * @return The Animation
	 */
	private static Animation buildAnimation(int index,int length,int speed){
		int a,b,x,y;
		
		a=index/ss.getHorizontalCount();
		b=index%ss.getHorizontalCount();
		
		x=(index+length-1)%ss.getHorizontalCount();
		y=(index+length-1)/ss.getHorizontalCount();
		

		//System.out.println(a+" "+b+" : "+x+" "+y);
	
		return new Animation(ss,a,b,x,y,true,speed,true);
	}
	
	private static Animation buildAnimationFromStrip(boolean horizzontal,int index,int length,int speed){
		return new Animation(ss,index,0,index,length-1,true,speed,horizzontal);
	}
	
	/**
	 * Adds an animation to the animationList
	 * @param owner The name of the Object this animation is assigned to
	 * @param name Name of the animation
	 * @param anim The Animation to add
	 * @param flipped if true the animation is flipped
	 */
	private static void addAnimation(String owner,String name,Animation anim,boolean flipped){
		
		Animation rev=new Animation();
		for(int i=0;i<anim.getFrameCount();i++){
			Image t=anim.getImage(i);
			t.bind();
			//t.setFilter(Image.FILTER_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			
			
			if(flipped){
				t=t.getFlippedCopy(true, false);
			}
			rev.addFrame(t, anim.getDuration(i));		
		}
		anim=rev;
		if(!animationList.containsKey(owner))
			animationList.put(owner, new HashMap<String,Animation>());
		
		animationList.get(owner).put(name, anim);
	}
	
	/**
	 * Adds a sprite to the spritelist of the owner
	 * @param owner Owner of this sprite
	 * @param name Name of the sprite
	 * @param ref Reference to the Sprite file
	 * @param flipped if tru ethe sprite is flipped
	 */
	private static void addSprite(String owner,String name,String ref,boolean flipped){
		Image img=null;
		try {
			img = new Image(ref);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		
		if(flipped)
			img=img.getFlippedCopy(true, false);
		
		if(!spriteList.containsKey(owner))
			spriteList.put(owner, new HashMap<String,Image>());
		
		spriteList.get(owner).put(name, img);
	}
	
	/**
	 * Adds Sprite to the owners spriteList
	 * @param owner Owner of the Sprite
	 * @param name Name of the Sprite
	 * @param x X-Position you get the Sprite at off the SpriteSheet
	 * @param y Y-Position you get the Sprite at off the SpriteSheet
	 * @param flipped if true the sprite is added flipped
	 */
	private static void addSprite(String owner,String name,int x,int y,boolean flipped){
		Image img=ss.getSprite(x, y);
		img.bind();
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		if(flipped)
			img=img.getFlippedCopy(true, false);
		
		if(!spriteList.containsKey(owner))
			spriteList.put(owner, new HashMap<String,Image>());
		
		spriteList.get(owner).put(name, img);
	}
	
	/**
	 * Sets a new animation on the base layer
	 * @param owner Owner of the animation
	 * @param name Name of the animation
	 */
	public void setAnimation(String owner,String name){
		if(layers.get(0).animation==null || layers.get(0).animation!=animationList.get(owner).get(name)){
			layers.get(0).animation=animationList.get(owner).get(name);
			spriteSize.x=animationList.get(owner).get(name).getWidth();
			spriteSize.y=animationList.get(owner).get(name).getHeight();
			spriteBox.setSize(animationList.get(owner).get(name).getWidth(), animationList.get(owner).get(name).getHeight());
			//spriteBox.width=animationList.get(owner).get(name).getWidth();
			//spriteBox.height=animationList.get(owner).get(name).getHeight();
			layers.get(0).usingAnimation=true;
		}
	}
	
	/**
	 * Sets a new animation on a certain layer
	 * @param owner Owner of the animation
	 * @param name Name of the animation
	 * @param index Index number of the layer
	 */
	public void setAnimation(String owner,String name,int index){
		checkLayerExistance(index);
		if(layers.get(index).animation==null || layers.get(0).animation!=animationList.get(owner).get(name)){
			layers.get(index).animation=animationList.get(owner).get(name);
			spriteSize.x=animationList.get(owner).get(name).getWidth();
			spriteSize.y=animationList.get(owner).get(name).getHeight();
			spriteBox.setSize(animationList.get(owner).get(name).getWidth(), animationList.get(owner).get(name).getHeight());
			//spriteBox.width=animationList.get(owner).get(name).getWidth();
			//spriteBox.height=animationList.get(owner).get(name).getHeight();
			layers.get(index).usingAnimation=true;
		}
	}
	
	/**
	 * Sets a new Image on the base layer
	 * @param owner Owner of the animation
	 * @param name Name of the Animation
	 */
	public void setImage(String owner, String name){
		if(layers.get(0).image==null || layers.get(0).image!=spriteList.get(owner).get(name)){
			layers.get(0).image=spriteList.get(owner).get(name);
			spriteSize.x=spriteList.get(owner).get(name).getWidth();
			spriteSize.y=spriteList.get(owner).get(name).getHeight();
			spriteBox.setSize(spriteList.get(owner).get(name).getWidth(), spriteList.get(owner).get(name).getHeight());
			//spriteBox.width=spriteList.get(owner).get(name).getWidth();
			//spriteBox.height=spriteList.get(owner).get(name).getHeight();
			layers.get(0).usingAnimation=false;
		}
	}
	
	/**
	 * Sets a new image on a certain layer
	 * @param owner Owner of the Animation
	 * @param name  name of the animation
	 * @param index Index of the Layer
	 */
	public void setImage(String owner, String name,int index){
		checkLayerExistance(index);
		if(layers.get(index).image==null || layers.get(0).image!=spriteList.get(owner).get(name)){
			layers.get(index).image=spriteList.get(owner).get(name);
			spriteSize.x=spriteList.get(owner).get(name).getWidth();
			spriteSize.y=spriteList.get(owner).get(name).getHeight();
			spriteBox.setSize(spriteList.get(owner).get(name).getWidth(), spriteList.get(owner).get(name).getHeight());
			//spriteBox.width=spriteList.get(owner).get(name).getWidth();
			//spriteBox.height=spriteList.get(owner).get(name).getHeight();
			layers.get(index).usingAnimation=false;
		}
	}
	
	public static Image getImage(String owner, String name){
			return spriteList.get(owner).get(name);
	}
	
	private void checkLayerExistance(int index){
		while(layers.size()<=index)
			layers.add(new Layer());
	}
	
	public void hideLayer(int index,boolean value){
		checkLayerExistance(index);
		layers.get(index).hide=value;
	}
	
	public float getSpriteScaledWidth(Camera cam){
		if(usingAnimation && currentAnimation!=null)
			return currentAnimation.getWidth()*cam.getZoom();
		if(currentImage!=null)
			return currentImage.getWidth()*cam.getZoom();
		return 0;
	}
	
	public float getSpriteScaledHeight(Camera cam){
		if(usingAnimation && currentAnimation!=null)
			return currentAnimation.getHeight()*cam.getZoom();
		if(currentImage!=null)
			return currentImage.getHeight()*cam.getZoom();
		return 0;
	}
	
/*	
	public  void render(Camera cam){
		spriteBox.setPosition(position.x, position.y);
		if(currentImage!=null && !usingAnimation){
			Vector2f screenPos = getScreenPos(cam);
			currentImage.draw(screenPos.x-spriteBox.pivotX*cam.getZoom(), screenPos.y-spriteBox.pivotY*cam.getZoom(), spriteBox.width*cam.getZoom(), spriteBox.height*cam.getZoom());
		}
		else
		if (currentAnimation != null) {
			Vector2f screenPos = getScreenPos(cam);
			currentAnimation.draw(screenPos.x-spriteBox.pivotX*cam.getZoom(), screenPos.y-spriteBox.pivotY*cam.getZoom(), spriteBox.width*cam.getZoom(), spriteBox.height*cam.getZoom());	
		}
		super.render(cam);
		spriteBox.render(cam);
	}
	*/
	public  void render(Camera cam){
		spriteBox.setPosition(position.x, position.y);
		for(int i=0;i<layers.size();i++){
			if(layers.get(i).hide) continue;
			Image currentImage=layers.get(i).image;
			Animation currentAnimation=layers.get(i).animation;
			boolean usingAnimation=layers.get(i).usingAnimation;
			if(currentImage!=null && !usingAnimation){
				Vector2f screenPos = getScreenPos(cam);
				currentImage.draw(screenPos.x-spriteBox.pivotX*cam.getZoom(), screenPos.y-spriteBox.pivotY*cam.getZoom(), spriteBox.width*cam.getZoom(), spriteBox.height*cam.getZoom());
			}
			else
			if (currentAnimation != null) {
				Vector2f screenPos = getScreenPos(cam);
				currentAnimation.draw(screenPos.x-spriteBox.pivotX*cam.getZoom(), screenPos.y-spriteBox.pivotY*cam.getZoom(), spriteBox.width*cam.getZoom(), spriteBox.height*cam.getZoom());	
			}
			//super.render(cam);
			//spriteBox.render(cam);
		}
	}
}

/**
 * Layer Class
 * Each Layer can have one image/animation
 * An Object can have several layers
 * @author watacoso
 *
 */
class Layer{
	public Image image;
	public Animation animation;
	public boolean usingAnimation;
	public boolean hide=false;
	public Layer(){
	}
	
	public Layer(Image image){
		this.image=image;
	}
	
	public Layer(Animation animation){
		this.animation=animation;
		usingAnimation=true;
	}
	
	public Layer(Image image,Animation animation){
		this.image=image;
		this.animation=animation;
		usingAnimation=true;
	}
	
}
