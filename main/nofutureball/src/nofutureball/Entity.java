package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity implements Comparable<Entity> {

	public Vector2f position;
	public Vector2f pivot;
	public Vector2f size;

	public Container parent = null;
	private Graphics g = new Graphics();

	public Entity(float x, float y) {
		position = new Vector2f(x, y);
		size = new Vector2f(0, 0);
		pivot = new Vector2f(0, 0);
	}

	public Entity(float x, float y, float width, float height) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(width/2, height/2);
	}

	public Entity(float x, float y, float width, float height, float pivotX, float pivotY) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(pivotX, pivotY);
	}

	// update()
	
	public void update() {

	}

	public void update(Vector2f offset) {

	}
	
	public void render(Camera cam) {
		Vector2f screenPos = getScreenPos(cam);
		g.setColor(Color.white);
		g.drawRect(screenPos.x, screenPos.y, getScaledWidth(cam), getScaledHeight(cam));
		g.setColor(Color.blue);
		g.fillRect(screenPos.x+pivot.x-2, screenPos.y+pivot.y-2, 4, 4);
	}
	/*public void render(Vector2f screenPosThatsAlreadyBeenDerivedFromCam, float zoom)
	{
		g.drawRect(screenPosThatsAlreadyBeenDerivedFromCam.x, screenPosThatsAlreadyBeenDerivedFromCam.y, getScaledWidth(zoom), getScaledHeight(zoom));
	}*/
	

	public int compareTo(Entity o) {
		if (o.position.y+o.size.y  < this.position.y+this.size.y)
			return 1;
		if (o.position.y+o.size.y  > this.position.y+this.size.y)
			return -1;
		return 0;
	}
	
	

	// Feel free to make getScreenPos(Vector2f offset), if you need that. (just replace camera.position with offset)
	/**
	 * Call this whenever you render to get the actual position on screen to render to!
	 */
	protected Vector2f getScreenPos(Camera camera)
	{
		return new Vector2f(NoFutureBall.WIDTH / 2 + (position.x - camera.position.x) * camera.getZoom(),
							NoFutureBall.HEIGHT / 2 + (position.y - camera.position.y) * camera.getZoom());
	}
	protected float getScaledWidth(Camera camera)
	{
		return size.x * camera.getZoom();
	}
	protected float getScaledHeight(Camera camera)
	{
		return size.y * camera.getZoom();
	}
		
	protected boolean checkCollision(Entity e){
		if(position.x>e.position.x+e.size.x ||
		   position.x+size.x<e.position.x)
			return false;
		if(position.y>e.position.y+e.size.y ||
		   position.y+size.y<e.position.y)
			return false;
		return true;
		
	}
	
	protected String checkBoxSide(Entity e){
		Vector2f pos1=new Vector2f(position.x+size.x/2,position.y+size.y/2);
		Vector2f pos2=new Vector2f(e.position.x+e.size.x/2,e.position.y+e.size.y/2);
		
		float overlapX=Math.min(position.x+size.x, e.position.x+e.size.x)-
				       Math.max(position.x, e.position.x);
		
		float overlapY=Math.min(position.y+size.y, e.position.y+e.size.y)-
				        Math.max(position.y, e.position.y);
		
		if(overlapY>overlapX){
			if(pos1.x<=pos2.x)
				return "right";
			return "left";
		}
		if(pos1.y>=pos2.y)
			return "top";
		return "bottom";
	}
	
	
	public static float getDistance(Entity A,Entity B){
		float X=B.position.x+B.pivot.x-A.position.x-A.pivot.x;
		float Y=B.position.y+B.pivot.y-A.position.y-A.pivot.y;
		return (float) Math.sqrt(X*X+Y*Y);
	}
	public static Vector2f getDirectionVector(Entity A,Entity B){
		float X=B.position.x+B.pivot.x-A.position.x-A.pivot.x;
		float Y=B.position.y+B.pivot.y-A.position.y-A.pivot.y;
		float l=(float) Math.sqrt(X*X+Y*Y);
		return new Vector2f(X/l,Y/l);
	}
}
