package nofutureball;


class RectangleF{
	
	public float x,y,width,height,pivotX,pivotY;
	
	
	public RectangleF(){
		
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
}