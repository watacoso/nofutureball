package oppression;

import org.newdawn.slick.geom.Vector2f;

public abstract class Entity implements Comparable<Entity> {

	public Vector2f position;
	public Vector2f pivot;
	public Vector2f size;
	public boolean dead=false;
	public RectangleF box;
	public Container parent = null;

	public Entity(float x, float y) {
		position = new Vector2f(x, y);
		size = new Vector2f(0, 0);
		pivot = new Vector2f(0, 0);
		box=new RectangleF(x,y,0,0);
	}

	public Entity(float x, float y, float width, float height) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(width/2, height/2);
		box=new RectangleF(x,y,width,height,width/2,height/2);
	}

	public Entity(float x, float y, float width, float height, float pivotX, float pivotY) {
		position = new Vector2f(x, y);
		size = new Vector2f(width, height);
		pivot = new Vector2f(pivotX, pivotY);
		box=new RectangleF(x,y,width,height,pivotX,pivotY);
	}

	// update()
	
	public void update(Game game) {
		
	}
	
	public void render(Camera cam) {	
		//box.render(cam);
	}


	public int compareTo(Entity o) {
		if (o.box.bottom()  < box.bottom())
			return 1;
		if (o.box.bottom()  > box.bottom())
			return -1;
		if(this instanceof Wall && o instanceof Wall){
			Wall w1=(Wall)this;
			Wall w2=(Wall)o;
			if(w1.room!=w2.room){
				if(w1.room.position.y > w2.room.position.y)
					return 1;
				else
				if(w1.room.position.y < w2.room.position.y)
					return -1;
				
			}
		}
		return 0;
	}
	
	

	// Feel free to make getScreenPos(Vector2f offset), if you need that. (just replace camera.position with offset)
	/**
	 * Call this whenever you render to get the actual position on screen to render to!
	 */
	protected Vector2f getScreenPos(Camera camera)
	{
		return new Vector2f(Window.WIDTH / 2 + (position.x - camera.position.x) * camera.getZoom(),
				Window.HEIGHT / 2 + (position.y - camera.position.y) * camera.getZoom());
	}
	protected float getScaledWidth(Camera camera)
	{
		return size.x * camera.getZoom();
	}
	protected float getScaledHeight(Camera camera)
	{
		return size.y * camera.getZoom();
	}
		
		
	public static void truncate(Vector2f A,float B){
		if(A.length()>B)
		 A.normalise().scale(B);
	}
}
