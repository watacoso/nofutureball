package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Enemy extends GameObject implements Actor{

	
	private float maxForce = 1;
	private Player target;
	private Vector2f direction=new Vector2f(0,0);
	private Vector2f steering=new Vector2f(0,0);
	private Vector2f torque=new Vector2f(0,0);
	
	
	
	private ArrayList<Door> path;
	private ArrayList<Room> roomPool;
	private int pathIndex;
	private Room startRoom=null;
	private Room targetRoom=null;
	private Room stepRoom=null;
	private Door stepDoor=null;
	private boolean updatePath=false;
	private boolean roomTravel=false;
	private boolean onDoor=false;
	
	public String action="IDLE";
	public String facing="LEFT";
	public float health;
	
	private int tmpIndex=0;

	public Enemy(Room room, float x, float y) {
		super(room, x, y, 128,256,true);
		//animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.ENEMY);
		defineStats();
		
	}
	
	public Enemy(Room room, float x, float y,Player target) {
		super(room, x, y, 128,256,true);
		//animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.ENEMY);
		this.target=target;
		steering=getDirectionVector(target);
		
		defineStats();
	}
	
	private void defineStats(){
		maxSpeed=(float) (10f+Math.random()*3);
		maxHealth=100;
		armor=1;
		health=maxHealth;
		startRoom=room;
	}

	public void update(Game game) {
		
		if(target!=null && targetRoom!=target.room || startRoom!=room && startRoom!=null){
			if(room==target.room)
				roomTravel=false;
			else
			if(!updatePath){ 
				System.out.println("Player Changed Room");
				updatePath=true;
				targetRoom=target.room;
				startRoom=room;
				tmpIndex=0;
				path=getPathFrom(room,null);
				
				if(path!=null){
					/*System.out.println("------PATH-------");
					System.out.println("SIZE : "+path.size());
					for(int i=0;i<path.size();i++)
						System.out.println("STEP "+i+" : "+path.get(i).side);	*/
					
					pathIndex=0;
					roomTravel=nextRoom();

				}
				else
					System.out.println("path null");
			}
		}
		else{
			updatePath=false;
		}
		

				
		if(roomTravel){
			
			//Movement BETWEEN ROOMS
			if(door==stepDoor){
				if(stepDoor.size.x<stepDoor.size.y){
					if(speed.x>0)
						steering=getDirectionVector(stepDoor.position.x+stepDoor.pivot.x+100,position.y+pivot.y);
					else
						steering=getDirectionVector(stepDoor.position.x+stepDoor.pivot.x-100,position.y+pivot.y);
				}
				else{
					if(speed.y>0)
						steering=getDirectionVector(position.x+pivot.x,stepDoor.position.y+stepDoor.pivot.y+100);
					else
						steering=getDirectionVector(position.x+pivot.x,stepDoor.position.y+stepDoor.pivot.y-100);
				}
				//steering=getDirectionVector(stepRoom);
				onDoor=true;
			}
			else{
				steering=getDirectionVector(stepDoor);
				onDoor=false;
			}
			
			if(stepRoom==room){
				roomTravel=nextRoom();
			
			}
		}
		else
		if(target!=null){
			//SUBSTITUTE WITH Local movement AI 
			steering=getDirectionVector(target).scale(maxForce);
			//SUBSTITUTE WITH Local movement AI 
		}
		
		
		
		//speed vector adjustment
		
		
		direction.set(steering);
		direction.normalise();
		
		torque.set(0,0);

		
		
		if(speed.length()>=1){
			action="WALKING";
		}
		else
			action="IDLE";
		if(direction.x<=0)
			facing="LEFT";
		else
			facing="RIGHT";


		setAnimation("ENEMY",action+"_"+facing);
		
		Entity.truncate(speed.add(steering),maxSpeed);
		super.update(game);

	}
		
	private ArrayList<Door> getPathFrom(Room room,Door door){
		if(target==null || room.doors.size()==0 || room==target.room) return null;
		
		ArrayList <Door> path=new ArrayList<Door>();
		Door d;
		Room r;
		ArrayList<Door> l=new ArrayList<Door>();
		for(int i=0;i<room.doors.size();i++){
			
			d=room.doors.get(i);
			if(d==door) continue;
			r=getDoorDestination(room,d);
			if(r==target.room){
				//System.out.println(tmpIndex);
				path.add(d);
				return path;
			}
		}
		tmpIndex++;
		for(int i=0;i<room.doors.size();i++){
			d=room.doors.get(i);
			if(d!=door) {
				r=getDoorDestination(room,d);
				l=getPathFrom(r,d);
				
				if(l!=null){
						path.add(d);
						path.addAll(l);
						System.out.println(path.size());
						return path;
				}
			}
		}
		return null;
		
	}
	
	public void setTarget(Player target){
		this.target=target;
		targetRoom=target.room;
	}
	
	private boolean nextRoom(){
		if(path==null || path.size()==pathIndex)
			return false;
		stepDoor=path.get(pathIndex);
		stepRoom=getDoorDestination(room, stepDoor);
		pathIndex++;
		return true;
	}
	
	private Room getDoorDestination(Room room,Door d){
		if(room!=d.rA)
			return d.rA;
		else
			return d.rB;
	}
	
	
	//AVOIDANCE
	
	
	
	
	protected void handleObjectCollision(GameObject object,String direction){
		if(object instanceof Player)
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
		}
		if(object instanceof Bullet){
			SoundManager.mixedSound("enemyDamage");
			float k=object.knockback;
			
			switch (direction){
			case "left":
				speed.x=k;
				break;
			case "right":
				speed.x=-k;
				break;
			case "top":
				speed.y=k;
				break;
			case "bottom":
				speed.y=-k;
				break;
			}
			
			health-=object.damage/armor;
			
			if(health<0)
				die();
			
			object.die();
		}
	}
	
	
	//DEBUG
	


	@Override
	public void execActive(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execPassive(int index) {
		// TODO Auto-generated method stub
		
	}
	
	

}