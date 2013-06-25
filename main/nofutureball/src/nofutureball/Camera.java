package nofutureball;


public class Camera {

	private Container follower = null;
	private Entity toFollow = null;
	
	private float zoom;
	
	public Camera(Container follower, Entity toFollow){

		setZoom(1);
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
	public void setZoom(float zoom){
		if(zoom <= 0) return;
		this.zoom = zoom;
	}
	public float getZoom()
	{
		return zoom;
	}
	
	public void setFollower(Container follower)
	{
		this.follower = follower;
	}
	public void setTarget(Entity toFollow)
	{
		this.toFollow = toFollow;
	}
}
