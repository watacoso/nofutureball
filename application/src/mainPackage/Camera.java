package mainPackage;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import entityPackage.Container;
import entityPackage.Entity;


public class Camera {

	private Container follower = null;
	/** List of targets to follow */
	private ArrayList<Entity> followList = new ArrayList<Entity>();
	
	/**
	 * This position is not affected by zoom; it's the position in the model world where the middle of the camera is.
	 */
	public Vector2f position = new Vector2f();
	private float zoom;
	
	private final float maxZoom = 1;
	
	public Camera(Container follower){

		setZoom(1);
		// 
		this.follower = follower;
	
	}
	public Camera()
	{
		
	}
	
	
	// Controls how fast the follower can follow the target. The higher the slower.
	private final int quotient = 15;
	
	private final int cameraMargin = 400;
	
	public void update()
	{
		if (follower == null) return;
		
		// FINDING THE BOUNDING RECTANGLE 

		
		Rectangle bound = new Rectangle(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0);

		// Finding the minimum position
		for (Entity toFollow : followList) {
			if (toFollow.box.left() < bound.x) {
				bound.x = (int) toFollow.box.left();
			}
			if (toFollow.box.top() < bound.y) {
				bound.y = (int) toFollow.box.top();
			}

		}

		// Finding the maximum size
		for (Entity toFollow : followList) {
			int relativeX = (int) ((toFollow.box.right()) - bound.x); // relative to bound's x
			int relativeY = (int) ((toFollow.box.bottom()) -bound.y); // ...

			if (relativeX > bound.width) {
				bound.width = relativeX;
			}
			if (relativeY > bound.height) {
				bound.height = relativeY;
			}
		}
//	}
		// Target position:
		Vector2f targetPosition = new Vector2f((float) bound.getCenterX(), (float) bound.getCenterY());

		position.x += (targetPosition.x - position.x) / quotient;
		position.y += (targetPosition.y - position.y) / quotient;

		//Target zoom!!! To find this: what zoom do we need to make the bound rectangle fit screen? ( + a little margin yay!!! :D)
		float targetZoom;

		// Apply margins
		bound.width += cameraMargin * 2;
		bound.height += cameraMargin * 2;

		if ((float) bound.width / (float) Window.WIDTH > (float) bound.height / (float) Window.HEIGHT) {
			targetZoom = 1/ ((float) bound.width / (float) Window.WIDTH);
		} else {
			targetZoom = 1/ ((float) bound.height / (float) Window.HEIGHT);
		}
		setZoom(getZoom() + (targetZoom - zoom) / (quotient * 2));
	}
	public void setZoom(float zoom){
		//System.out.println(zoom);
		if(zoom <= 0) {
			zoom = 1;
			return;
		}
		else if (zoom >= maxZoom) {
			zoom = maxZoom;
			return;
		}
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
