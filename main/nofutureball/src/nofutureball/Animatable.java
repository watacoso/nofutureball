package nofutureball;

import org.newdawn.slick.geom.Vector2f;

/**
 * ENUM THINGGGG
 */
public abstract class Animatable extends Entity {

	public AnimationSet animations = null;
	protected final String myself = "(Animatable)";
	
	public Animatable(float x, float y, float width, float height)
	{
		super(x, y, width, height);
	}
	
	
	public void render(Vector2f offset)
	{
		if (animations != null) {
			animations.getCurrentlyPlaying().draw(position.x + offset.x, position.y + offset.y);
		} else {
			System.out.println("ERRRROOOOR: You have to override the animations property in subclasses!");
		}
	}
	
	
	
	
	
	////////
	// STATIC ENUMS!!!!! Used mainly for animation classes
	
	
	// Subclass enums
	
	//TODO Whenever you add a subclass to Entity that has an animation saved in assets/, remember to update this:
	// this array is just for iterative stuff in my animation implementation btw
	
	public static enum SUBCLASS implements StringEnum {
		PLAYER("PLAYER");
		
		SUBCLASS (String str){
			this.str = str;
		}
		private String str = "";
		
		public String toString()
		{
			return str;
		}
	}
	
	public static enum STATE implements StringEnum {
		WALKING("WALKING"), IDLE("IDLE");

		
		STATE(String str)
		{
			this.str = str;
		}
		private String str = "";
		
		public String toString()
		{
			return str;
		}
		
	}
	
}
