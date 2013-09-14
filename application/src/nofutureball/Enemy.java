package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Enemy extends GameObject{

	
	private float maxForce = 1;
	private Player target;
	private Vector2f direction=new Vector2f(0,0);
	private Vector2f steering=new Vector2f(0,0);
	private Vector2f torque=new Vector2f(0,0);
	
	
	private ArrayList<Door> path;
	private int pathIndex;
	
	private Room targetRoom=null;
	
	private Room stepRoom=null;
	private Room currentPathRoom=null;
	private Door stepDoor=null;
	
	private boolean updatePath=false;
	private boolean roomTravel=false;

	
	private boolean movingUnit=true;
	
	public String action="IDLE";
	public String facing="LEFT";
	public float health;

	public Enemy(Room room, float x, float y) {
		super(room, x, y, 128,256,true);
		defineStats();
		
	}
	
	public Enemy(Room room, float x, float y,Player target) {
		super(room, x, y, 128,256,true);
		this.target=target;
		targetRoom=target.room;
		steering=getDirectionVector(target);
		
		defineStats();
	}
	
	private void defineStats(){
		maxSpeed=(float) (10f+Math.random()*3);
		maxHealth=100;
		armor=1;
		health=maxHealth;
	}

	public void update(Game game) {
				
		if(target==null || !movingUnit){
			idleAI();
		}
		else
		if(roomTravel){
			
			if(isPathObsolete()){		
				if(target.room==room){
					roomTravel=false;			
				}
				else
				if(!updatePath){
					updatePath=true;
					newPath();
					roomTravel=nextRoom();
				}
			}
			else{
				if(room==stepRoom){
					roomTravel=nextRoom();
				}
				updatePath=false;
			}
			pathAI();
			
		}
		else{
			if(target.room!=room){
				roomTravel=true;
			}
			else{
				roomAI();
			}
		}

		//speed vector adjustment
		
		direction.set(steering);
		direction.normalise();
		
		torque.set(0,0);

		//ANIMATION SECTOR (PLACEHOLDER, needs to be moved to each enemy subclass)
		
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
		for(int i=0;i<room.doors.size();i++){
			d=room.doors.get(i);
			if(d!=door) {
				r=getDoorDestination(room,d);
				l=getPathFrom(r,d);
				
				if(l!=null){
						path.add(d);
						path.addAll(l);
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
		
	private boolean isPathObsolete(){
		
		if(path==null)
			return true;
		if(targetRoom!=null && targetRoom!=target.room )
			return true;
		if(currentPathRoom!=null && room!=currentPathRoom && room!=stepRoom)
			return true;
		return false;
	}
	
	private void newPath(){
		pathIndex=0;
		targetRoom=target.room;
		currentPathRoom=room;
		path=getPathFrom(room,null);
	}
	
	private boolean nextRoom(){
		if(path==null) return false;
		if( path.size()==pathIndex)	return false;
		stepDoor=path.get(pathIndex);
		stepRoom=getDoorDestination(room, stepDoor);
		currentPathRoom=room;
		pathIndex++;
		return true;
	}
	
	protected void roomAI(){
		steering=getDirectionVector(target).scale(maxForce);
	}
	
	private void pathAI(){
			if(path==null) return;
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
			}
			else{
				steering=getDirectionVector(stepDoor);
			}
			
			if(stepRoom==room){
				roomTravel=nextRoom();
			
			}
	}
	
	protected void idleAI(){
		
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
	
	
}