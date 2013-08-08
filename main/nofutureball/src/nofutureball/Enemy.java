package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.internal.LinkedTreeMap;

public class Enemy extends GameObject {

	private float maxSpeed = 4f;
	private float maxForce = 0.2f;
	private Player target;
	private Vector2f direction=new Vector2f(0,0);
	private Vector2f steering=new Vector2f(0,0);
	private Vector2f torque=new Vector2f(0,0);

	private ArrayList<Door> path;
	private int pathIndex;
	private Room targetRoom=null;
	private Room stepRoom=null;
	private Door stepDoor=null;
	private boolean updatePath=false;
	private boolean roomTravel=false;
	private boolean onDoor=false;
	
	
	public static LinkedTreeMap<String, ?> STATS = PropsBuilder
			.loadProp("player.json");

	public Enemy(Room room, float x, float y) {
		super(room, x, y, 30, 63);
		animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.ENEMY);
		
	}
	
	public Enemy(Room room, float x, float y,Player target) {
		super(room, x, y, 30, 63);
		animations = AnimationSet.createAnimationSet(Animatable.SUBCLASS.ENEMY);
		this.target=target;
		steering=getDirectionVector(target);
	}

	public void update(Game game) {
		
		if(targetRoom!=target.room && target!=null){
			if(room==target.room)
				roomTravel=false;
			else
			if(!updatePath){ 
				//System.out.println("Player Changed Room");
				updatePath=true;
				targetRoom=target.room;
				path=getPathFrom(room,null);
				
				if(path!=null){
			/*		System.out.println("------PATH-------");
					System.out.println("SIZE : "+path.size());
					for(int i=0;i<path.size();i++)
						System.out.println("STEP "+i+" : "+path.get(i).side);	*/
					
					pathIndex=0;
					roomTravel=nextRoom();
					//System.out.println("roomTravel = "+roomTravel);
				}
			}
		}
		else{
			updatePath=false;
		}
		

				
		if(roomTravel){
			
			//EQUAL for all Enemies, for the inter Room Movement
			if(getDistance(stepDoor)>10 && !onDoor){
				steering=getDirectionVector(stepDoor);
			}
			else{
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
				//direction=getDirectionVector(stepRoom);
				onDoor=true;
			}
			if(stepRoom==room){
				roomTravel=nextRoom();
				onDoor=false;
				//System.out.println("roomTravel = "+roomTravel);
			}
		}
		else
		if(target!=null){
			//SUBSTITUTE WITH Local movement AI 
			//if(torque.length()==0)
			steering=getDirectionVector(target).scale(maxForce);
			//SUBSTITUTE WITH Local movement AI 
		}
		
		
		
		//speed vector adjustment
		
		
		direction.set(steering);
		direction.normalise();
		
		/*torque.set(0,0);
		Vector2f Q=new Vector2f(direction);
		Q.normalise().scale(120);
		Vector2f Q2=new Vector2f(Q);
		Q2.normalise().add(25).scale(50);
		Vector2f Q3=new Vector2f(Q);
		Q3.normalise().sub(25).scale(50);
		
		for(int i=0;i<room.walls.size();i++){
			if(pointInsideBox(Q,room.walls.get(i)))
				avoidTorque(Q,room.walls.get(i));
			else
			if(pointInsideBox(Q2,room.walls.get(i)))
				avoidTorque(Q2,room.walls.get(i));
			else
			if(pointInsideBox(Q3,room.walls.get(i)))
				avoidTorque(Q3,room.walls.get(i));
			
		}*/
		
		if(speed.length()>=1){
			animations.setAnimation(Animatable.STATE.WALKING);
			//Vector2f Q=new Vector2f(position.x+pivot.x+direction.x*100,position.y+pivot.y+direction.y*100);
			
			//System.out.println(room.walls.size());
			//for(int i=0;i<room.walls.size();i++)
			//	if(pointInsideBox(Q,room.walls.get(i))){
			//		avoidTorque(Q,room.walls.get(i));
			//	}
		}
		else
			animations.setAnimation(Animatable.STATE.IDLE);
		if(direction.x<=0)
			animations.setAnimation(Facing.LEFT);
		else
			animations.setAnimation(Facing.RIGHT);

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
				path.add(d);
				//System.out.println("DOOR: "+d.side);
				return path;
			}
		}
		
		for(int i=0;i<room.doors.size();i++){
			d=room.doors.get(i);
			if(d!=door) {
				r=getDoorDestination(room,d);
				l=getPathFrom(r,d);
				if(l!=null){
					r=getDoorDestination(room,l.get(l.size()-1));
					if(r==target.room){
						path.add(d);
						path.addAll(l);
						return path;
					}
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
	
	
	private  boolean pointInsideBox(Vector2f V,Entity N){
		if(position.x+pivot.x+V.x<N.position.x) return false;
		if(position.x+pivot.x+V.x>N.position.x+N.size.x) return false;
		if(position.y+pivot.y+V.y<N.position.y) return false;
		if(position.y+pivot.y+V.y>N.position.y+N.size.y) return false;
		return true;
	}
	
	private void avoidTorque(Vector2f V,Entity N){
		Vector2f L=new Vector2f(0,0);
		int M;
		if(Math.abs(V.x+collisionBox.position.x+collisionBox.pivot.x-N.position.x+N.pivot.x)>Math.abs(V.y+collisionBox.position.y+collisionBox.pivot.y-N.position.y+N.pivot.y)){
			if(collisionBox.position.x+collisionBox.pivot.x>N.position.x+N.pivot.x){
				L.x=N.position.x+N.size.x-V.x-position.x-pivot.x;
			}
			else{
				L.x=V.x+position.x+pivot.x-N.position.x;
			}
			
			M=(V.y>collisionBox.position.y+collisionBox.pivot.y?-1:1);
			
		}
		else{
			if(collisionBox.position.y+collisionBox.pivot.y>N.position.y+N.pivot.y){
				L.x=N.position.y+N.size.y-V.y-position.y-pivot.y;
			}
			else{
				L.y=V.y+position.y+pivot.y-N.position.y;
			}
			M=(V.x>collisionBox.position.x+collisionBox.pivot.x?-1:1);
			
		}
		torque=direction.getPerpendicular();
		float F=L.getNormal().dot(torque);
		System.out.println(F);
		torque.scale(F>0?-1:1);
		steering.add(torque.scale(0.5f));
		
	}
	
	
	//DEBUG
	
	public void render(Camera cam)
	{
		super.render(cam);
		Vector2f screenPos = getScreenPos(cam);
		Graphics g=new Graphics();
		g.setLineWidth(1);
		Vector2f Q=new Vector2f(direction);
		Q.normalise().scale(120);
		Vector2f Q2=new Vector2f(Q);
		Q2.normalise().add(25).scale(50);
		Vector2f Q3=new Vector2f(Q);
		Q3.normalise().sub(25).scale(50);
		g.setLineWidth(1);
		g.setColor(Color.black);
		g.drawLine(screenPos.x+size.x/2,screenPos.y+size.y*3/4,screenPos.x+size.x/2+ Q.x,screenPos.y+size.y*3/4+ Q.y);
		g.fillOval(screenPos.x+size.x/2+ Q.x-3,screenPos.y+size.y*3/4+ Q.y-3, 6, 6);
		g.drawLine(screenPos.x+size.x/2,screenPos.y+size.y*3/4,screenPos.x+size.x/2+ Q2.x,screenPos.y+size.y*3/4+ Q2.y);
		g.fillOval(screenPos.x+size.x/2+ Q2.x-3,screenPos.y+size.y*3/4+ Q2.y-3, 6, 6);
		g.drawLine(screenPos.x+size.x/2,screenPos.y+size.y*3/4,screenPos.x+size.x/2+ Q3.x,screenPos.y+size.y*3/4+ Q3.y);
		g.fillOval(screenPos.x+size.x/2+ Q3.x-3,screenPos.y+size.y*3/4+ Q3.y-3, 6, 6);
		g.setLineWidth(2);
		g.setColor(Color.blue);
		g.drawLine(screenPos.x+size.x/2,screenPos.y+size.y*3/4,screenPos.x+size.x/2+ direction.x*100,screenPos.y+size.y*3/4+ direction.y*100);
		g.setColor(Color.magenta);
		g.drawLine(screenPos.x+size.x/2,screenPos.y+size.y*3/4,screenPos.x+size.x/2+ speed.x*5,screenPos.y+size.y*3/4+ speed.y*5);
		g.setColor(Color.yellow);
		if(torque.length()>0)
			g.drawLine(screenPos.x+size.x/2,screenPos.y+size.y*3/4,screenPos.x+size.x/2+ torque.x/torque.length()*15,screenPos.y+size.y*3/4+ torque.y/torque.length()*15);
		
		
	}
	
	

}