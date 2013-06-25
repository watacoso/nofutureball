package nofutureball;

public class Camera {

	private Container target;
	
	public float x, y;
	
	public Camera(Container target, float x, float y){
		this.target = target;
		this.x = x;
		this.y = y;
		target.position.x = -x;
		target.position.y = -y;
	}
	
	public void setPosition(float x,float y){
		target.position.x -= x - this.x;
		target.position.y -= y - this.y;
		this.x = x;
		this.y = y;
	}
	
}
