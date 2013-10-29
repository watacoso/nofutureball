package oppression;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Enemy extends GameObject {

	
	private float maxForce = 1;
	private Player target;
	private Vector2f direction=new Vector2f(0,0);
	private Vector2f lastDirection=new Vector2f(0,0);
	private Vector2f steering=new Vector2f(0,0);
	private Vector2f separationVector=new Vector2f(0,0);
	//private Vector2f torque=new Vector2f(0,0);
	
	
	private ArrayList<Conn> path;
	private int pathIndex;
	
	private Room targetRoom=null;
	
	private Room stepRoom=null;
	private Room currentPathRoom=null;
	private Conn stepConn=null;
	
	private boolean updatePath=false;
	private boolean roomTravel=false;

	
	private boolean movingUnit=true;
	
	public String action="IDLE";
	public String facing="LEFT";

	public Enemy(Room room, float x, float y) {
		super(room, x, y, 200, 100, true);
		spritePivot.set(128,236);
		spriteBox.setPivot(128, 256);
		steering= new Vector2f(0,0);
		LevelManager.nEnemies++;
		defineStats();
		
	}
	
	public Enemy(Room room, float x, float y,Player target) {
		super(room, x, y, 200,100,true);
		spritePivot.set(128,256);
		spriteBox.setPivot(128, 256);
		this.target=target;
		targetRoom=target.room;
		steering=getDirectionVector(target);
		LevelManager.nEnemies++;
		defineStats();
	}
	
	private void defineStats(){
		//stats.normalSpeed += Math.random() * 3;
		base=StatSet.ENEMY;
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

				roomAI(game);

			}
		}

		//speed vector adjustment
		
		direction.add(steering);
		direction.normalise();
		
		//torque.set(0,0);

		//ANIMATION SECTOR (PLACEHOLDER, needs to be moved to each enemy subclass)
		if(direction.length()!=0)
			lastDirection.set(direction);
		if(speed.length()>=1){
			action="WALKING";
		}
		else
			action="IDLE";
		setFacing(target.position);

		setAnimation("ENEMY",action+"_"+facing);

		Entity.truncate(speed.add(steering), normalSpeed());
		super.update(game);

	}
		
	private ArrayList<Conn> getPathFrom(Room room,Conn door){
		if(target==null || room.doors.size()==0 || room==target.room) return null;
		
		ArrayList <Conn> path=new ArrayList<Conn>();
		Conn d;
		Room r;
		ArrayList<Conn> l=new ArrayList<Conn>();
		for(int i=0;i<room.doors.size();i++){
			
			d=room.doors.get(i);
			if(d==door) continue;
			r=getDoorDestination(room,d);
			if(r==target.room){
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
		stepConn=path.get(pathIndex);
		stepRoom=getDoorDestination(room, stepConn);
		currentPathRoom=room;
		pathIndex++;
		return true;
	}
	
	protected void roomAI(Game game){
		
		if(target!=null && this.getDistance(target)<400){
			steering.set(0,0);
			speed.set(0,0);
		}
		else{

			steering=getDirectionVector(target).scale(maxForce);
			separationVector.set(0,0);
			for(int i=0;i<game.entities.size();i++){
				if(game.entities.get(i) instanceof Enemy && game.entities.get(i)!=this){
					Enemy e=(Enemy)game.entities.get(i);
					if(room==e.room && getDistance(e)<400){	
						separationVector.add(avoidVector(e,50));
					}
					
				}
			}
			steering.sub(separationVector);
			if(steering.length()<separationVector.length()){
				steering.set(0,0);
				//speed.set(0,0);
			}
		}		
	}
	
	private Vector2f avoidVector(Entity e,int weight){
		return getDirectionVector(e).scale(weight/(1+getDistance(e)));
	}
	
	private void pathAI(){
			if(path==null) return;
			if(door==stepConn){
				
				if(stepConn.box.width<stepConn.box.height){
					if(speed.x>0)
						steering=getDirectionVector(stepConn.box.getCenterX()+100,position.y);
					else
						steering=getDirectionVector(stepConn.box.getCenterX()-100,position.y);
				}
				else{
					if(speed.y>0)
						steering=getDirectionVector(position.x,stepConn.box.getCenterY()+100);
					else
						steering=getDirectionVector(position.x,stepConn.box.getCenterY()-100);
				}
			}
			else{
				//System.out.println(stepConn.box.x-stepConn.position.x);
				steering=getDirectionVector(stepConn.box.getCenterX(),stepConn.box.getCenterY());
			}
			
			if(stepRoom==room){
				roomTravel=nextRoom();
			
			}
			setFacing(stepConn.position);
	}
	
	protected void idleAI(){
		
	}
	
	protected void setFacing(Vector2f target){
		if(target.x<=position.x)
			facing="LEFT";
		else
			facing="RIGHT";
	}
	
	private Room getDoorDestination(Room room,Conn d){
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
			float k = object.knockback();
			
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
			
			health -= /** INSERT COLLISION DAMAGE HERE */ 3 / armor();
			
			if(health < 0)
				die();
			
			object.die();
		}
	}

	public void die(){
		LevelManager.nEnemies--;
		super.die();
	}
	
	public int rollDamage() {
		return 4;
	}
	
	
}