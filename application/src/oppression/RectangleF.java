package oppression;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


class RectangleF{
	
	public float x=0,y=0,width=0,height=0,pivotX=0,pivotY=0;
	public Entity owner;
	private Vector2f topLeft,pivot,size;
	

	
	public RectangleF(float x,float y){
		this.x=x;
		this.y=y;
		topLeft=new Vector2f(x,y);
		size=new Vector2f(0,0);
		pivot=new Vector2f(0,0);
	}
	
	public RectangleF(float x,float y,float width,float height){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		topLeft=new Vector2f(x,y);
		size=new Vector2f(width,height);
		pivot=new Vector2f(0,0);
	}
	
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
	
	public void setPosition(float x,float y){
		this.x=x-pivotX;this.y=y-pivotY;
		topLeft.set(x-pivot.x,y-pivot.y);
	}
	
	public void move(float x,float y){
		//pivot.x+=x;
		topLeft.x+=x;
		//pivot.y+=y;
		topLeft.y+=y;
	}
	
	public void setSize(float w,float h){
		width=w;height=h;
		size.set(w,h);
	}
	
	public void setPivot(float x,float y){
		pivotX=x;pivotY=y;
		pivot.set(x,y);
	}
	
	public Vector2f getPosition(){
		return topLeft.copy().add(pivot);
	}
	
	public Vector2f getSize(){
		return size;
	}
	
	public Vector2f getCenter(){
		return topLeft.copy().add(new Vector2f(size.x/2,size.y/2));
	}
	
	/*public float getCenterX(){
		return x+width/2;
	}
	
	public float getCenterY(){
		return y+height/2;
	}
	
	public Vector2f getPivot(){
		return pivot;
	}*/
	
	/*public float pivotedX(){
		return x+pivotX;
	}
	
	public float pivotedY(){
		return y+pivotY;
	}*/
	
	public float bottom(){
		return topLeft.y+size.y;
	}
	
	public float top(){
		return topLeft.y;
	}
	
	public float right(){
		return topLeft.x+size.x;
	}
	
	public float left(){
		return topLeft.x;
	}
	
	public float corner(boolean top,boolean left){
		return topLeft.x+topLeft.y+(top?0:size.y)+(left?0:size.x);
	}
	
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