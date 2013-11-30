package entityPackage;

import mainPackage.Camera;
import mainPackage.Window;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Abstract rectangle with float precision attributes 
 * @author watacoso
 *
 */

public class RectangleF{
	
	public float x=0,y=0,width=0,height=0,pivotX=0,pivotY=0;
	public Entity owner;
	private Vector2f topLeft,pivot,size;
	
/**
 * instances a rectangle with size=0 (so basically a point) at the x,y coordinates
 * @param x
 * @param y
 */
	
	public RectangleF(float x,float y){
		this.x=x;
		this.y=y;
		topLeft=new Vector2f(x,y);
		size=new Vector2f(0,0);
		pivot=new Vector2f(0,0);
	}
	
	/**
	 * instances a rectangle with size (width,height) at the x,y coordinates. it's pivot point is at the top left corner 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	
	public RectangleF(float x,float y,float width,float height){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		topLeft=new Vector2f(x,y);
		size=new Vector2f(width,height);
		pivot=new Vector2f(0,0);
	}
	
	/**
	 * instances a rectangle with size (width,height) at the x,y coordinates.
	 * it's pivot point is at the pivotX,pivotY coordinates.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param pivotX
	 * @param pivotY
	 */
	
	public RectangleF(float x,float y,float width,float height,float pivotX,float pivotY){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.pivotX=pivotX;
		this.pivotY=pivotY;
		topLeft=new Vector2f(x,y);
		size=new Vector2f(width,height);
		pivot=new Vector2f(pivotX,pivotY);
	}
	
	/**
	 * moves the rectangle so that it's pivot point is at x,y coordinates
	 * @param x
	 * @param y
	 */
	
	public void setPosition(float x,float y){
		this.x=x-pivotX;this.y=y-pivotY;
		topLeft.set(x-pivot.x,y-pivot.y);
	}
	/**
	 * translates the rectangle position by a x,y value
	 * @param x
	 * @param y
	 */
	public void move(float x,float y){
		//pivot.x+=x;
		topLeft.x+=x;
		//pivot.y+=y;
		topLeft.y+=y;
	}
	
	/**
	 * set the size of the rectangle
	 * @param w
	 * @param h
	 */
	
	public void setSize(float w,float h){
		width=w;height=h;
		size.set(w,h);
	}
	
	/**
	 * set the coordinates of the pivot , relative to the top left corner of the rectangle
	 * @param x
	 * @param y
	 */
	
	public void setPivot(float x,float y){
		pivotX=x;pivotY=y;
		pivot.set(x,y);
	}
	
	/**
	 * get the global coordinates of the pivot (that is, relative either to the container of the rectangle or to the main reference system of the game)
	 * @return
	 */
	
	public Vector2f getPosition(){
		return topLeft.copy().add(pivot);
	}
	
	/**
	 * get the size vector of the rectangle
	 * @return
	 */
	
	public Vector2f getSize(){
		return size;
	}
	
	/**
	 * get the global coordinates of the center of the rectangle (to not confound with the coordinates of the pivot)
	 * @return
	 */
	
	public Vector2f getCenter(){
		return topLeft.copy().add(new Vector2f(size.x/2,size.y/2));
	}
	
	
	/**
	 * get the y value of the bottom of the rectangle
	 * @return
	 */
	
	public float bottom(){
		return topLeft.y+size.y;
	}
	
	/**
	 * get the y value of the top of the rectangle
	 * @return
	 */
	
	public float top(){
		return topLeft.y;
	}
	
	/**
	 * get the x value of the right of the rectangle
	 * @return
	 */
	
	public float right(){
		return topLeft.x+size.x;
	}
	
	/**
	 * get the x value of the left of the rectangle
	 * @return
	 */
	
	public float left(){
		return topLeft.x;
	}
	
	/**
	 * get the coordinates of one of the corners of the rectangle.
	 * for example , corner(true, false) gets the coordinates of the top right corner
	 * @param top	choose top or bottom corner
	 * @param left  choose left or right corner
	 * @return
	 */
	
	public float corner(boolean top,boolean left){
		return topLeft.x+topLeft.y+(top?0:size.y)+(left?0:size.x);
	}
	
	/**
	 * returns true if the rectangle is intersetting the rectangle e
	 * @param e rectangle to test collision
	 * @return
	 */
	
	protected boolean checkCollision(RectangleF e){
		//System.out.println(e);
		if(left()>e.right() ||
		   right()<e.left())
			return false;
		if(top()>e.bottom() ||
		  bottom()<e.top())
			return false;
		return true;
		
	}
	
	/**
	 * returns a string that reveals what side of the rectangle is most committed on intersecting with e.
	 * @param e
	 * @return
	 */
	
	protected String checkBoxSide(RectangleF e){
		Vector2f pos1=new Vector2f(getCenter());
		Vector2f pos2=new Vector2f(e.getCenter());
		
		float overlapX=Math.min(right(), e.right())-
				       Math.max(left(), e.left());
		
		float overlapY=Math.min(bottom(), e.bottom())-
				        Math.max(top(),e.top());
		
		if(overlapY>overlapX){
			if(pos1.x<=pos2.x)
				return "right";
			return "left";
		}
		if(pos1.y>=pos2.y)
			return "top";
		return "bottom";
	}
	
	/**
	 * draws a white empty red rectangle using the attributes of the instance
	 * @param cam
	 */
	
	public void render(Camera cam){
		Graphics g=new Graphics();
		Vector2f screenPos = getScreenPos(cam);
		g.setColor(Color.red);
		g.drawRect(screenPos.x, screenPos.y, getScaledWidth(cam), getScaledHeight(cam));
	}
	
	protected Vector2f getScreenPos(Camera camera)
	{
		return new Vector2f(Window.WIDTH / 2 + (topLeft.x - camera.position.x) * camera.getZoom(),
				Window.HEIGHT / 2 + (topLeft.y - camera.position.y) * camera.getZoom());
	}
	protected float getScaledWidth(Camera camera)
	{
		return size.x * camera.getZoom();
	}
	protected float getScaledHeight(Camera camera)
	{
		return size.y * camera.getZoom();
	}
}