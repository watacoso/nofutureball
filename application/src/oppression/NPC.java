package oppression;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class NPC extends GameObject {

	
	private float maxForce = 1;
	protected GameObject target;
	private Vector2f direction=new Vector2f(0,0);
	private Vector2f lastDirection=new Vector2f(0,0);
	private Vector2f steering=new Vector2f(0,0);
	private Vector2f separationVector=new Vector2f(0,0);
	private String status;
	
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

	public NPC(Room room, float x, float y) {
		super(room, x, y, 200, 100, true);
		spritePivot.set(128,236);
		spriteBox.setPivot(128, 226);
		steering= new Vector2f(0,0);
		LevelManager.nEnemies++;
		defineStats();
		
	}
	
	public NPC(Room room, float x, float y,Player target) {
		super(room, x, y, 200,100,true);
		spritePivot.set(128,236);
		spriteBox.setPivot(128, 226);
		this.target=target;
		targetRoom=target.room;
		steering=getDirectionVector(target);
		LevelManager.nEnemies++;
		defineStats();
	}
	
	private void defineStats(){
		//stats.normalSpeed += Math.random() * 3;
		base=StatSet.ENEMY;
		health=maxHealth();
	}

	public void update(Game game) {
		
		if(target==null)
			idleAI();
		else
			pathFinding(game);
		
		//////////////////
		
		for(int i=0;i<game.entities.size();i++){
			if(game.entities.get(i) instanceof NPC && game.entities.get(i)!=this){
				NPC e=(NPC)game.entities.get(i);
				if(room==e.room){	
					steering.sub(avoidVector(e,80,500));
				}
			}
		}

		//speed vector adjustment
		
		direction.add(steering);
		direction.normalise();

		//ANIMATION SECTOR (PLACEHOLDER, needs to be moved to each enemy subclass)
		if(direction.length()!=0)
			lastDirection.set(direction);
		if(speed.length()>=1){
			action="WALKING";
		}
		else
			action="IDLE";
		

		setAnimation("ENEMY",action+"_"+facing);
		
		if(steering.length()!=0)
			Entity.truncate(speed.add(steering), normalSpeed());
		else{
			speed.x*=0.5;
			speed.y*=0.5;
		}
		
		super.update(game);

	}
	
	protected void roomAI(Game game){
		followTarget(game);
		
	}
	////NPC GENERAL MOVEMENTS AI//
	
	protected void moveTo(int x, int y){
		
	}
	
	
	protected void followTarget(Game game){
		steering=getDirectionVector(target).scale(maxForce);
		separationVector.set(0,0);
		

		if(room==target.room && getDistance(target)<200 )
			separationVector.set(steering);
	
		for(int i=0;i<LevelManager.players.size();i++){
			Player e=(Player)LevelManager.players.get(i);
			if(room==e.room){
				separationVector.add(avoidVector(e,100,700));
			}
		}
		
		if(steering.length()<separationVector.length() &&
		   target.speed.length()==0 && 
		   getDistance(target)<500 &&
		   target.door==null)
			steering.set(0,0);
		else
			steering.sub(separationVector);
	}

	/////////////////////////////
	
	protected void idleAI(){
		steering.set(0,0);
	}

	protected void setFacing(Vector2f target){
		if(target.x<=position.x)
			facing="LEFT";
		else
			facing="RIGHT";
	}
	
	
	//PATHFINDING AND TARGET LOCK

	public void setTarget(GameObject target){
		this.target=target;
		targetRoom=target.room;
	}
	
	public void resetTarget(){
		this.target=null;
	}
	
	
	
	private void pathFinding(Game game){
		if(target==null)
			return;
		if(!movingUnit){
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
					setFacing(target.position);
				}
			}
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
	
	private void pathAI(){
		if(path==null) return;
		if(door==stepConn){	
			if(stepConn.box.width<stepConn.box.height){
				if(speed.x>0)
					steering=getDirectionVector(stepConn.box.getCenter().x+100,position.y);
				else
					steering=getDirectionVector(stepConn.box.getCenter().x-100,position.y);
			}
			else{
				if(speed.y>0)
					steering=getDirectionVector(position.x,stepConn.box.getCenter().y+100);
				else
						steering=getDirectionVector(position.x,stepConn.box.getCenter().y-100);
			}
		}
		else{
			//System.out.println(stepConn.box.x-stepConn.position.x);
			steering=getDirectionVector(stepConn.box.getCenter().x,stepConn.box.getCenter().y);
		}
		if(stepRoom==room){
			roomTravel=nextRoom();
		}
		setFacing(stepRoom.box.getCenter());
	}
		
	private Room getDoorDestination(Room room,Conn d){
		if(room!=d.rA)
			return d.rA;
		else
			return d.rB;
	}
	
	private Vector2f avoidVector(Entity e, int distance, int weight){
		Vector2f s=new Vector2f(0,0);
		if(getDistance(e)<distance){	
			s.add(getDirectionVector(e).scale(weight/(1+getDistance(e))));
		}
		return s;
	}
	
	//COLLISION
	
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
		if(object instanceof Bullet && ((Bullet) object).damageNPC){
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
}