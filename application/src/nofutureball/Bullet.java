package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class Bullet extends GameObject {
	
	//public Vector2f speed;
	public float life;
	public float size;
	public GameObject src;
	

	
	public Bullet(Player src, float size, Vector2f lastDirection,float speedModule) {
		super(src.room, src.position.x-src.room.position.x+src.pivot.x, src.position.y-src.room.position.y-100, size, size,false);
		spritePivot.set(size/2,size/2);
		setAnimation("BULLETS","STANDARD");		
		spriteSize.set(size,size);
		collisionBox.position.y+=100;
		speed=new Vector2f(lastDirection).scale(speedModule);
		
		base = new StatSet(src);
		
		health = (int) (src.range()/speedModule);
		this.src = src;
		//System.out.println(speed.x+" "+speed.y);
		// TODO Auto-generated constructor stub
	}
	
	public void update (Game game){
		
		if(health<0) die();
		else
			health--;
		//System.out.println(life);
		
		super.update(game);
		
		
	}
	


	
	protected void handleWallCollision(Wall wall,String direction){
		die();
	}
	
	protected void handleObjectCollision(GameObject object,String direction){
		/*if(object instanceof Player)
		switch (direction){
		case "left":
			speed.x=Math.abs(speed.x)/2;
			break;
		case "right":
			speed.x=-Math.abs(speed.x)/2;
			break;
		case "top":
			speed.y=Math.abs(speed.y)/2;
			break;
		case "bottom":
			speed.y=-Math.abs(speed.y)/2;
			break;
		}*/
		/*if(object!=src)
			die();*/
	}
	
	
	
	

}
