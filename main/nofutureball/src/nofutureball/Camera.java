package nofutureball;

public class Camera {

	private Container target;
	
	public float x,y,zoom;
	
	public Camera(Container target,float x,float y){
		this.target=target;
		this.x=x;
		this.y=y;
		zoom=1;
		target.position.x=-x;
		target.position.y=-y;
	}
	
	public void setPosition(float x,float y){
		target.position.x-=x-this.x;
		target.position.y-=y-this.y;
		this.x=x;
		this.y=y;
	}
	
	public void setZoom(float zoom){
		if(zoom<=0) return;
		
	}
	
}
