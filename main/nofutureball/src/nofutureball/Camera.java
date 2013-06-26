package nofutureball;

import java.util.ArrayList;


public class Camera {

	private Container follower = null;
	/** List of targets to follow */
	private ArrayList<Entity> followList = new ArrayList<Entity>();
	
	private float zoom;
	
	public Camera(Container follower){

		setZoom(1);
		// 
		this.follower = follower;
	}
	public Camera()
	{
		
	}
	
	
	// Controls how fast the follower can follow the target. The higher the slower.
	private final int quotient = 10;
	
	public void update()
	{
		if (follower == null) return;
		
		Vector2d entityCenter = new Vector2d();
		for (Entity toFollow : followList) {
			entityCenter.x = toFollow.position.x + toFollow.pivot.x;
			entityCenter.y = toFollow.position.y + toFollow.pivot.y;
			
			//follower position += ((target position) 							   - (current position))  / quotient
			follower.position.x += ((NoFutureBall.WIDTH / 2 - entityCenter.x) - follower.position.x) / quotient;
			follower.position.y += ((NoFutureBall.HEIGHT / 2 - entityCenter.y) - follower.position.y) / quotient;
		}
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
	public void addTarget(Entity toFollow)
	{
		if (!followList.contains(toFollow)) followList.add(toFollow);
	}
	public void removeTarget(Entity toUnfollow)
	{
		if (followList.contains(toUnfollow)) followList.remove(toUnfollow);
	}
	public Entity getTargetAtIndex(int index)
	{
		if (followList.size() == 0) return null;
		return followList.get(index);
	}
}
