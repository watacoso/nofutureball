package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class Bullet extends GameObject {
	
	//public Vector2f speed;
	public float life;
	public float size;
	public GameObject src;
	


	
	public Bullet(GameObject src, float size, Vector2f lastDirection,float speedModule) {
		super(src.room, src.position.x-src.room.position.x+src.pivot.x, src.position.y-src.room.position.y+src.pivot.y, size, size,false);
		
		this.size=size;
		
		speed=new Vector2f(lastDirection).scale(speedModule);
		knockback=src.knockback;
		damage=src.damage;
		life=src.range/speedModule;
		this.src=src;
		range=src.range;
		//System.out.println(speed.x+" "+speed.y);
		// TODO Auto-generated constructor stub
	}
	
	public void update (Game game){
		
		if(life<0) die();
		else
			life--;
		//System.out.println(speed.x+" "+speed.y);
		
		super.update(game);
		
		setAnimation("BULLETS","STANDARD");
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
