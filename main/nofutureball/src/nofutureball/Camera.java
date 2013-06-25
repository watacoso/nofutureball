package nofutureball;

import org.lwjgl.util.vector.Vector2f;

public class Camera {

	private Container follower = null;
	private Entity toFollow = null;
	
	private Vector2f position = new Vector2f();
	private float zoom;
	
	public Camera(Container follower, Entity toFollow){

		setZoom(1f);
		// 
		this.follower = follower;
		this.toFollow = toFollow;
		// Initialize the position
		follower.position.x = NoFutureBall.WIDTH / 2 - toFollow.position.x;
		follower.position.y = NoFutureBall.HEIGHT / 2 - toFollow.position.y;
	}
	
	
	// Controls how fast the follower can follow the target. The higher the slower.
	private final int quotient = 10;
	
	public void update()
	{
		// follower position = ((target position) - (current position))/4
		follower.position.x += ((NoFutureBall.WIDTH / 2 - toFollow.position.x) - follower.position.x) / quotient;
		follower.position.y += ((NoFutureBall.HEIGHT / 2 - toFollow.position.y) - follower.position.y) / quotient;
	}
<<<<<<< HEAD
=======
	
	
	public void setZoom(float zoom){
		if(zoom <= 0) return;
>>>>>>> d081efe87244e6eff02a7e4f0246c0ba4f2479fc
		
}
