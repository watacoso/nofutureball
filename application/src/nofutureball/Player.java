package nofutureball;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public abstract class Player extends GameObject implements Actor{

	public Weapon weapon;
	
	
	private float maxSpeed = 40;
	public Vector2f direction,lastDirection;
	
	
	public Augmentation passive, active; 
	

	public String action="IDLE";
	public String facing="LEFT";
	public float health=maxHealth;


	public Player(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, 128,256,true);
		this.keySet = keySet;
		//animations = AnimationSet
		//		.createAnimationSet(Animatable.SUBCLASS.PLAYER);
		
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(-1, 0);
	}

	protected KeySet keySet = KeySet.ONE;

	public Player(Room room, float x, float y) {
		super(room, x, y, 128,256,true);
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
		
		goalSpeed.x = (float) (maxSpeed * (direction.x != 0 ? direction.x * 0.9
				: direction.x));
		goalSpeed.y = (float) (maxSpeed * (direction.y != 0 ? direction.y * 0.8
				: direction.y * 0.9));

		speed.x += (goalSpeed.x - this.speed.x) / 20;
		speed.y += (goalSpeed.y - this.speed.y) / 20;
		
		if (direction.x != 0 || direction.y != 0) {
			action="WALKING";
		} else {
			action="IDLE";
		}
		if (input.isKeyDown(keySet.left)) {
			facing="LEFT";
		} else if (input.isKeyDown(keySet.right)) {
			facing="RIGHT";
		}
		setAnimation("PLAYER",action+"_"+facing);
		super.update(game);
	}

}
