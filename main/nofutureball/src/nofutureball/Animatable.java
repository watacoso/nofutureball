package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * ENUM THINGGGG
 */
public abstract class Animatable extends Entity {

	public AnimationSet animations = null;
	protected final String myself = "(Animatable)";
	
	public Animatable(double x, double y, double width, double height)
	{
		super(x, y, width, height);
	}
	
	@Override
	public void render(Vector2f offset)
	{
		if (animations != null) {
			animations.getCurrentlyPlaying().draw((int)(position.x + offset.x), (int)(position.y + offset.y), new Color(1f, 1f, 1f));
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
		PLAYER("PLAYER", 60, 75);

		SUBCLASS (String str, int gridWidth, int gridHeight){
			this.str = str;
			this.gridWidth = gridWidth;
			this.gridHeight = gridHeight;
		}
		
		private int gridWidth = 1;
		private int gridHeight = 1;
		
		public int getSpriteWidth()
		{
			return gridWidth;
		}
		public int getSpriteHeight()
		{
			return gridHeight;
		}
		
		private String str = "";
		public String toint()
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
		
		public String toint()
		{
			return str;
		}
		
	}
	
}
