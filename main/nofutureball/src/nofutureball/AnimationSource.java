package nofutureball;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Should only be used by AnimationSet.
 */
public class AnimationSource {


	private static HashMap<String, AnimationSource> sources = new HashMap<String, AnimationSource>();
	
	public static void init()
	{
		// Player
		for (Animatable.SUBCLASS type : Animatable.SUBCLASS.values())
		{
			sources.put(type.toString(), loadAllSources(type));
		}
	}


	public static AnimationSource getSource(Animatable.SUBCLASS entityType)
	{
		if (sources.containsKey(entityType.toString())) {
			return sources.get(entityType.toString());
		}
		System.out.println("AnimationSource.getSource: I bet you'll never reach this code hahaha");
		return new AnimationSource(true);
	}
	
	/**
	 * @param entityType	Animatable.PLAYER
	 * @param state			Animatable.STATE_x
	 * @param direction		Facing.UP, Facing.RIGHT, Facing.DOWN, Facing.LEFT
	 */
	// We presume all spritesheets face left originally.
	private static Image[] loadSourceFor(Animatable.SUBCLASS entityType, Animatable.STATE state, Facing facing)
	{
		int gridWidth = 0, gridHeight = 0;
		
		//TODO Whenever you add an entity type, remember to add it to this if/else thing 
		if (entityType == Animatable.SUBCLASS.PLAYER) {
			gridWidth = 60;
			gridHeight = 75;
		}

		// load the appropriate image for entityType and state
		SpriteSheet ss = null;
		try {
			Image img = new Image("assets/" + entityType.toString() + "_" + state.toString() + ".png");
			ss = new SpriteSheet(img, gridWidth, gridHeight);
		} catch (SlickException e) {
			e.printStackTrace();
			return new Image[0];
		}
		
		// number of pictures:
		int gridX = (int) (ss.getWidth() / gridWidth);
		int gridY = (int) (ss.getHeight() / gridHeight);
		
		// Convert the spritesheet to a source of animation
		Image[] animationSource = new Image[gridX * gridY];
		Image singleImage;
		int i = 0;
		for (int y = 0; y < gridY; y ++) {
			for (int x = 0; x < gridX; x ++) {
				singleImage = ss.getSprite(x, y);
				if (facing == Facing.RIGHT) { // If it's facing right, we should flip it.
					animationSource[i] = singleImage.getFlippedCopy(true, false);
				} else {
					animationSource[i] = singleImage;
				}
				i ++;
			}
		}
		
		return animationSource;
	}
	
	/**
	 * This will iteratively call loadSourceFor(entityType, state, facing), iterating through all available state and facing enums,
	 * and put the Image[] in the AnimationSource's HashMap with the key state+facing
	 */
	private static AnimationSource loadAllSources(Animatable.SUBCLASS entityType)
	{
		AnimationSource as = new AnimationSource();

		String key;
		for (Animatable.STATE state : Animatable.STATE.values())
		{
			for (Facing facing : Facing.values())
			{
				key = state.toString() + facing.toString();
				as.animations.put(key, loadSourceFor(entityType, state, facing));
			}
		}
		
		return as;
	}
	
	// DYNAMIC
	
	/**
	 * Contains the sources required to create an AnimationSet instance for a specific Animatable instance.
	 */
	private AnimationSource()
	{
		
	}
	private AnimationSource(boolean empty)
	{
		// make an empty object
		if (empty) {
			String key;
			for (Animatable.STATE state : Animatable.STATE.values())
			{
				for (Facing facing : Facing.values())
				{
					key = state.toString() + facing.toString();
					animations.put(key, new Image[1]);
				}
			}
		}
	}
	
	
	
	/**
	 *	List of the different animations. The key is state + facing
	 */
	public HashMap<String, Image[]> animations = new HashMap<String, Image[]>();
	
	/**
	 * 
	 * @return Returns the animation for this entity type given the state and facing arguments...
	 */
	public Image[] getAnimationSource(Animatable.STATE state, Facing facing)
	{
		return animations.get(state.toString() + facing.toString());
	}
	
	
}