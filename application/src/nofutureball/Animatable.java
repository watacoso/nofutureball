package nofutureball;

import org.newdawn.slick.geom.Vector2f;

/**
 * ENUM THINGGGG
 */
public abstract class Animatable extends Entity {

	public AnimationSet animations = null;

	public Animatable(float x, float y, float width, float height)
	{
		super(x, y, width, height);
	}

	public Animatable(float x, float y, float width, float height, float pivotX, float pivotY)
	{
		super(x, y, width, height, pivotX, pivotY);
	}

	@Override
	public void render(Camera cam)
	{
		Vector2f screenPos = getScreenPos(cam);
		
		if (animations != null) {
			animations.getCurrentlyPlaying().draw(screenPos.x, screenPos.y, getScaledWidth(cam), getScaledHeight(cam));
		} else {
			System.out.println("ERRRROOOOR: You have to override the animations property in subclasses!");
		}
		//super.render(cam);
	}
	
	  //////////////////YY\\\\\\\\\\\\\\\\\\
	 /////////////////______\\\\\\\\\\\\\\\\\
	///////////////// STATIC \\\\\\\\\\\\\\\\\
	//\\\\\\\\\\\\\\\________/////////////////
	
	
	
	// TODO Whenever you add a subclass to Entity that has an animation saved in
	// assets/, remember to update this:

	public static enum SUBCLASS implements StringEnum {
		PLAYER("PLAYER", 30, 63),
		ENEMY("ENEMY", 30, 63);
		
		SUBCLASS(String str, int gridWidth, int gridHeight) {
			this.str = str;
			this.gridWidth = gridWidth;
			this.gridHeight = gridHeight;

		}

		private int gridWidth = 1;
		private int gridHeight = 1;

		private String str = "";

		public String toint() {
			return str;
		}

		public int getSpriteWidth() {
			return gridWidth;
		}

		public int getSpriteHeight() {
			return gridHeight;
		}
	}

	public static enum STATE implements StringEnum {
		WALKING("WALKING"), IDLE("IDLE");

		STATE(String str) {

			this.str = str;
		}

		private String str = "";

		public String toint() {
			return str;
		}

	}

}
