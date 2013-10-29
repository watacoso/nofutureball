package oppression;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public abstract class Player extends GameObject{
	
	public Augmentation passive, active; 
	

	public Action action = Action.IDLE;
	public Facing facing = Facing.DOWN;
	public Action torsoAction=Action.AIM;
	public Facing torsoFacing=Facing.UP;
	private String playerClass="SHARPSHOOTER";
	
	
	
	public Player(Room room, float x, float y, KeySet keySet) {
		super(room, x, y, 200, 100, true);
		spritePivot.set(128,256);
		spriteBox.setPivot(128, 256);
		this.keySet = keySet;
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(0, 1);
	}

	protected KeySet keySet = KeySet.ONE;

	public Player(Room room, float x, float y) {
		super(room, x, y, 200, 100, true);
		spritePivot.set(128,256);
		spriteBox.setPivot(128, 256);
		direction = new Vector2f(0, 0);
		lastDirection = new Vector2f(-1, 0);
	}
	
	@Override
	public void update(Game game) {
		
		Input input = Window.getGameContainer().getInput();
		
		Vector2f goalSpeed = new Vector2f(0, 0);
		direction.x = (input.isKeyDown(keySet.right) ? 1 : 0)
				- (input.isKeyDown(keySet.left) ? 1 : 0);
		direction.y = (input.isKeyDown(keySet.down) ? 1 : 0)
				- (input.isKeyDown(keySet.up) ? 1 : 0);

		truncate(direction,1);
		if(direction.length()!=0)	lastDirection.set(direction);
		
		goalSpeed.x = (float) (movementSpeed * (direction.x != 0 ? direction.x * 0.3
				: direction.x*0.1));
		goalSpeed.y = (float) (movementSpeed * (direction.y != 0 ? direction.y * 0.3
				: direction.y * 0.1));

		speed.x += (goalSpeed.x - this.speed.x) / 5;
		speed.y += (goalSpeed.y - this.speed.y) / 5;
		
		if (speed.length()<1) speed.set(0, 0);
		
		if (direction.x != 0 || direction.y != 0) {
			action = Action.WALKING;
		} else {
			action = Action.IDLE;
		}
		
		/* System 1
		
		if (input.isKeyDown(keySet.left)) {
			facing = Facing.LEFT;
		} else if (input.isKeyDown(keySet.right)) {
			facing = Facing.RIGHT;
		}
		
		if(lastDirection.y!=0 && lastDirection.x==0){
			if (lastDirection.y<0) {
				torsoFacing = Facing.UP;
			} else if (lastDirection.y>0) {
				torsoFacing = Facing.DOWN;
			}
		}
		else torsoFacing=null;
		
		if(input.isKeyDown(keySet.action1)){
		//facing=(Mouse.getX()<position.x?Facing.LEFT:Facing.RIGHT);
		setAnimation(playerClass+"_LEGS", action + "_" + facing);
		
		if(torsoFacing!=null)
			setAnimation(playerClass+"_TORSO", "AIM" + "_" +torsoFacing+"_"+ facing,1);
		else
			setAnimation(playerClass+"_TORSO", "AIM" +"_"+ facing,1);
		hideLayer(1, false);
		movementSpeed = firingSpeed();
		}
		else{
			setAnimation(playerClass, action + "_" + facing);
			hideLayer(1, true);
			movementSpeed = normalSpeed();
		}
		
		*/
		
		//System 2
		
		/*
		
		Vector2f aimDirection=new Vector2f();
		aimDirection.x = (input.isKeyDown(keySet.aimRight) ? 2 : 0)
				- (input.isKeyDown(keySet.aimLeft) ? 1 : 0);
		aimDirection.y = (input.isKeyDown(keySet.aimDown) ? 2 : 0)
				- (input.isKeyDown(keySet.aimUp) ? 1 : 0);
		
		
		
		if(lastDirection.y==0 && lastDirection.x!=0){
			if(lastDirection.x<0)
				facing=Facing.LEFT;
			else if(lastDirection.x>0){
				facing=Facing.RIGHT;
			}
		}
		else{
			if(lastDirection.y<0)
				facing=Facing.UP;
			else
				facing=Facing.DOWN;
		}
		
		
		
		
		if(aimDirection.length()!=0){
			if(aimDirection.y==0 && aimDirection.x!=0){
				if(aimDirection.x<0)
					torsoFacing=Facing.LEFT;
				else if(aimDirection.x>0){
					torsoFacing=Facing.RIGHT;
				}
			}
			else{
				if(aimDirection.y<0)
					torsoFacing=Facing.UP;
				else
					torsoFacing=Facing.DOWN;
			}
		
			setAnimation(playerClass+"_LEGS", action + "_" + facing);
			setAnimation(playerClass+"_TORSO", "SHOOT" + "_" + torsoFacing,1);
			action1();
			hideLayer(1, false);
		}
		else{
		setAnimation(playerClass, action + "_" + facing);
		hideLayer(1, true);
		movementSpeed = normalSpeed();
		}
		*/
		
		//System 3
		
		
		if(lastDirection.y==0 && lastDirection.x!=0){
			if(lastDirection.x<0)
				facing=torsoFacing=Facing.LEFT;
			else if(lastDirection.x>0){
				facing=torsoFacing=Facing.RIGHT;
			}
		}
		else{
			if(lastDirection.y<0)
				facing=torsoFacing=Facing.UP;
			else
				facing=torsoFacing=Facing.DOWN;
		}
		
		
		if(input.isKeyDown(keySet.action1) || input.isKeyDown(Input.KEY_LSHIFT)){
			
			if(input.isKeyDown(Input.KEY_LSHIFT)){
				action=Action.IDLE;
				torsoAction=Action.AIM;	
				speed.set(0,0);
			}
			if(input.isKeyDown(keySet.action1)){
				torsoAction=Action.SHOOT;
				action1();
			}
	
			//System.out.println(playerClass+"_TORSO"+"_"+torsoAction+"_"+torsoFacing);
			setAnimation(playerClass+"_LEGS", action + "_" + facing);
			setAnimation(playerClass+"_TORSO",torsoAction + "_" + torsoFacing,1);
			hideLayer(1, false);
		}
		else{
		setAnimation(playerClass, action + "_" + facing);
		hideLayer(1, true);
		movementSpeed = normalSpeed();
		}
		
		
		
		super.update(game);
		
	}
	
	protected void action1(){
		
	}
	
	
	
	public int rollDamage()
	{
		return 0;
	}
	
	private enum Action {
		WALKING(), IDLE(),AIM(),SHOOT();
	}
	private enum Facing {
		LEFT(), RIGHT(), UP(),DOWN();
	}

}
