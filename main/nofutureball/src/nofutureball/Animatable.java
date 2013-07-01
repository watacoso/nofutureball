package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

/**
 * ENUM THINGGGG
 */
public abstract class Animatable extends Entity {

	public AnimationSet animations = null;

	public Animatable(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Animatable(float x, float y, float width, float height,
			float pivotX, float pivotY) {
		super(x, y, width, height, pivotX, pivotY);
	}

	@Override
	public void render(Vector2f offset) {
		super.render();
		if (animations != null) {
			animations.getCurrentlyPlaying().draw(
					 (position.x + offset.x - pivot.x),
					 (position.y + offset.y - pivot.y),
					new Color(1f, 1f, 1f));
		} else {
			System.out
					.println("ERRRROOOOR: You have to override the animations property in subclasses!");
		}

	//	g.drawRect((float) (position.x + offset.x - pivot.x),
	//			(float) (position.y + offset.y - pivot.y), (float) (size.x),
	//			(float) (size.y));
	}
	
	  //////////////////YY\\\\\\\\\\\\\\\\\\
	 /////////////////______\\\\\\\\\\\\\\\\\
	///////////////// STATIC \\\\\\\\\\\\\\\\\
	//\\\\\\\\\\\\\\\________/////////////////
	
	
	
	// TODO Whenever you add a subclass to Entity that has an animation saved in
	// assets/, remember to update this:

	public static enum SUBCLASS implements StringEnum {
		PLAYER("PLAYER", 30, 63);

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
