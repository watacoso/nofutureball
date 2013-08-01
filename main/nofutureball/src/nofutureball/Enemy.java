package nofutureball;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.google.gson.internal.LinkedTreeMap;

public class Enemy extends GameObject {

	private float maxSpeed = 2;
	private Player target;
	private Vector2f direction=new Vector2f(0,0);
	private ArrayList<Door> path;
	private int pathIndex;
	private Room targetRoom=null;
	private Room stepRoom=null;
	private Door stepDoor=null;
	private boolean updatePath=false;
	private boolean roomTravel=false;
	
	
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
	}

	public void update() {
		
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
					System.out.println("------PATH-------");
					System.out.println("SIZE : "+path.size());
					for(int i=0;i<path.size();i++)
						System.out.println("STEP "+i+" : "+path.get(i).side);	
					
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
			
			//if(Math.abs(position.x-stepDoor.position.x)>20 || Math.abs(position.y-stepDoor.position.y)>20)
			if(Entity.getDistance(this, stepDoor)>100)
				direction=Entity.getDirectionVector(this, stepDoor);
			if(stepRoom==room){
				roomTravel=nextRoom();
				//System.out.println("roomTravel = "+roomTravel);
			}
		}
		else
		if(target!=null){
			direction=Entity.getDirectionVector(this,target);
		}
		
		speed.x=direction.x*maxSpeed;
		speed.y=direction.y*maxSpeed;
		
		if(speed.length()>=1)
			animations.setAnimation(Animatable.STATE.WALKING);
		else
			animations.setAnimation(Animatable.STATE.IDLE);
		if(direction.x<=0)
			animations.setAnimation(Facing.LEFT);
		else
			animations.setAnimation(Facing.RIGHT);

		super.update();
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
				//System.out.println("l: "+l);
				if(l!=null){
					r=getDoorDestination(room,l.get(l.size()-1));
					if(r==target.room){
						path.add(d);
						//System.out.println("DOOR: "+d.side);
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
		//Door d=path.get(pathIndex);
		stepDoor=path.get(pathIndex);
		stepRoom=getDoorDestination(room, stepDoor);

		/*if(d.side=="left" || d.side=="right"){
			if(d.position.x<position.x)
				moveTo(d.position.x+d.pivot.x-size.x,d.position.y+d.pivot.y);
			else
				moveTo(d.position.x+d.pivot.x+size.x,d.position.y+d.pivot.y);
		}
		else{
			if(d.position.y<position.y)
				moveTo(d.position.x+d.pivot.x,d.position.y+d.pivot.y-size.y);
			else
				moveTo(d.position.x+d.pivot.x,d.position.y+d.pivot.y-size.y);
		}*/
		//direction=Entity.getDirectionVector(this, d);
		pathIndex++;
		return true;
	}
	
	public void moveTo(Entity B){
		//direction.set(x-position.x-pivot.x, y-position.y-pivot.y).normalise();
		direction=Entity.getDirectionVector(this, B);
	}
	
	private Room getDoorDestination(Room room,Door d){
		if(room!=d.rA)
			return d.rA;
		else
			return d.rB;
	}
	
	
	//DEBUG
	
	public void render(Camera cam)
	{
		super.render(cam);
		Vector2f screenPos = getScreenPos(cam);
		Graphics g=new Graphics();
		g.setLineWidth(1);
		g.setColor(Color.red);
		g.drawLine(screenPos.x+pivot.x,screenPos.y+pivot.y,screenPos.x+pivot.x+ direction.x*50,screenPos.y+ pivot.y+ direction.y*50);
	}
	
	

}