package oppression;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


class RectangleF{
	
	public float x=0,y=0,width=0,height=0,pivotX=0,pivotY=0;
	public Entity owner;
	

	
	public RectangleF(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public RectangleF(float x,float y,float width,float height){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
	}
	
	public RectangleF(float x,float y,float width,float height,float pivotX,float pivotY){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.pivotX=pivotX;
		this.pivotY=pivotY;
	}
	
	public RectangleF(Entity owner,float x,float y,float width,float height){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.owner=owner;
	}
	
	public RectangleF(Entity owner,float x,float y,float width,float height,float pivotX,float pivotY){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.pivotX=pivotX;
		this.pivotY=pivotY;
		this.owner=owner;
	}
	
	public void setPosition(float x,float y){
		this.x=x-pivotX;this.y=y-pivotY;
	}
	
	public void setSize(float w,float h){
		width=w;height=h;
	}
	
	public void setPivot(float x,float y){
		pivotX=x;pivotY=y;
	}
	
	public float getCenterX(){
		return x+width/2;
	}
	
	public float getCenterY(){
		return y+height/2;
	}
	
	public float pivotedX(){
		return x+pivotX;
	}
	
	public float pivotedY(){
		return y+pivotY;
	}
	
	public float bottom(){
		return y+height;
	}
	
	public float top(){
		return y;
	}
	
	public float right(){
		return x+width;
	}
	
	public float left(){
		return x;
	}
	
	public float corner(boolean top,boolean left){
		return x+y+(top?0:height)+(left?0:width);
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
		Vector2f pos1=new Vector2f(getCenterX(),getCenterY());
		Vector2f pos2=new Vector2f(e.getCenterX(),e.getCenterY());
		
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
		g.setColor(Color.white);
		g.drawRect(screenPos.x, screenPos.y, getScaledWidth(cam), getScaledHeight(cam));
	}
	
	protected Vector2f getScreenPos(Camera camera)
	{
		return new Vector2f(Window.WIDTH / 2 + (x - camera.position.x) * camera.getZoom(),
				Window.HEIGHT / 2 + (y - camera.position.y) * camera.getZoom());
	}
	protected float getScaledWidth(Camera camera)
	{
		return width * camera.getZoom();
	}
	protected float getScaledHeight(Camera camera)
	{
		return height * camera.getZoom();
	}
}