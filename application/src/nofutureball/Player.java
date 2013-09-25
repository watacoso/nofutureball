package nofutureball;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public abstract class Player extends GameObject implements Actor{
	
	
	
	
	
	public Augmentation passive, active; 
	

	public Action action = Action.IDLE;
	public Facing facing = Facing.LEFT;

	
	
	public Player(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, 128, 256, true);
		this.keySet = keySet;
		//animations = AnimationSet
		//		.createAnimationSet(Animatable.SUBCLASS.PLAYER);
		
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(-1, 0);
	}

	protected KeySet keySet = KeySet.ONE;

	public Player(Room room, float x, float y) {
		super(room, x, y, 128, 256, true);
		//animations = AnimationSet
		//		.createAnimationSet(Animatable.SUBCLASS.PLAYER);
		
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(-1, 0);
	}
	
	@Override
	public void update(Game game) {
		Input input = NoFutureBall.getGameContainer().getInput();

		
		Vector2f goalSpeed = new Vector2f(0, 0);

		direction.x = (input.isKeyDown(keySet.right) ? 1 : 0)
				- (input.isKeyDown(keySet.left) ? 1 : 0);
		direction.y = (input.isKeyDown(keySet.down) ? 1 : 0)
				- (input.isKeyDown(keySet.up) ? 1 : 0);

		truncate(direction,1);
		
		if(direction.length()!=0)	lastDirection.set(direction);
		
		goalSpeed.x = (float) (movementSpeed * (direction.x != 0 ? direction.x * 0.9
				: direction.x));
		goalSpeed.y = (float) (movementSpeed * (direction.y != 0 ? direction.y * 0.8
				: direction.y * 0.9));

		speed.x += (goalSpeed.x - this.speed.x) / 20;
		speed.y += (goalSpeed.y - this.speed.y) / 20;
		
		//if (speed.length()<1) speed.set(0, 0);
		
		if (direction.x != 0 || direction.y != 0) {
			action = Action.WALKING;
		} else {
			action = Action.IDLE;
		}
		if (input.isKeyDown(keySet.left)) {
			facing = Facing.LEFT;
		} else if (input.isKeyDown(keySet.right)) {
			facing = Facing.RIGHT;
		}
		setAnimation("PLAYER", action + "_" + facing);
		super.update(game);
	}
	
	
	
	public int rollDamage()
	{
		return 0;
	}
	
	private enum Action {
		WALKING(), IDLE();
	}
	private enum Facing {
		LEFT(), RIGHT();
	}

}
