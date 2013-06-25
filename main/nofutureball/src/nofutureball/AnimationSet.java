package nofutureball;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

/**
 * Set of animations for a specific entity type.
 */
public class AnimationSet {

	private static HashMap<String, AnimationSet> sources = new HashMap<String, AnimationSet>();
	
	/**
	 * Get animation set for the entity type
	 */
	public static AnimationSet getFor(String entityType)
	{
		if (sources.containsKey(entityType)) {
			return sources.get(entityType);
		}
		return new AnimationSet();
	}
	
	//TODO define frame time for animations 
	private static final int frameTime = 100;
	
	// Function for creating an AnimationSet object on request, given the entity type!
	
	public static AnimationSet createAnimationSet(Animatable.SUBCLASS entityType)
	{
		
		AnimationSource source = AnimationSource.getSource(entityType); 

		// Simply make a new AnimationSet object using the source!
		AnimationSet aSet = new AnimationSet();
		
		String key;
		for (Animatable.STATE state : Animatable.STATE.values())
		{
			for (Facing facing : Facing.values())
			{
				Image[] animationSource = source.getAnimationSource(state, facing);
				System.out.println(animationSource.length);
				key = state.toString() + facing.toString();
				// aSet[key] = ......
				aSet.put(key, new Animation(animationSource, frameTime));
			}
		}
		return aSet;
	}
	
	
	
	/////////////////////
	// DYNAMIC part
	
	private AnimationSet()
	{
		
	}

	public Animation getCurrentlyPlaying()
	{
		if (!animations.containsKey(state.toString() + facing.toString())) {
			System.out.println("AnimationSet.getCurrentlyPlaying(): invalid key. Yell at Ploppz I guess. Though this should really never happen.");
		}
		return animations.get(state.toString() + facing.toString());
	}
	/**
	 * Syntax: state + facing
	 */
	private Animatable.STATE state = Animatable.STATE.IDLE;
	private Facing facing = Facing.LEFT;
	
	/**
	 * Calling it every frame or something will not kill anything. All this method does, is
	 * change the "key" to the current animation.
	 */
	public void setAnimation(Animatable.STATE state, Facing facing)
	{
		this.state = state;
		this.facing = facing;
	}
	public void setAnimation(Animatable.STATE state)
	{
		this.state = state;
	}
	public void setAnimation(Facing facing)
	{
		this.facing = facing;
	}
	
	
	
	public Animatable.STATE getState()
	{
		return state;
	}
	public Facing getFacingDirection()
	{
		return facing;
	}
	
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	/**
	 * Only used when creating the object.
	 */
	public void put(String key, Animation value)
	{
		animations.put(key, value);
	}
	
}
