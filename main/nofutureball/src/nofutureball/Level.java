package nofutureball;

import org.newdawn.slick.geom.Vector2f;

public class Level {

	public Room startRoom,lastRoom;
	private Container entities,map;
	
	public int nRooms=20;
	
	public Level(Container entities,Container map){
		this.map=map;
		this.entities=entities;
		startRoom=new Room(0,0, 6, 10);
		startRoom.flowVector.set(0, -1);
		map.add(startRoom);
	}
	
	public void generateMap(){
		
		//TEST 1
		
		//appendRoom(startRoom,corridor(16,true),1,0,0);
		//appendRoom(startRoom,corridor(16,true),-1,7,16);
		//appendRoom(startRoom,corridor(8,false),0,0,0);
		//appendRoom(startRoom,corridor(8,false),2,7,16);

		//TEST 2
		appendRoom(startRoom, lastRoom=corridor(6,true),0,2,0);
		//lastRoom=corridor(6,false);
		appendRoom(lastRoom , lastRoom=corridor(10,false),0,0,4);
		appendRoom(lastRoom , squareRoom(6),-1,0,4);
		appendRoom(lastRoom , squareRoom(6), 1,0,4);
		appendRoom(lastRoom , squareRoom(6), 0,4,2);
		renderAllWalls();
	}
	

	
	public boolean appendRoom(Room root,Room room,int dir,int doorPosA,int doorPosB){
		Door door=new Door(2,root,room);
		Vector2f flowVector=new Vector2f(root.flowVector);
		
		flowVector.add(90*dir);
		
		room.flowVector=flowVector;
		
		if(Math.abs(flowVector.x)>Math.abs(flowVector.y)){
			if(flowVector.x>0){
				door.setPosition(root.position.x+root.size.x,root.position.y);
				room.position.x=root.position.x+root.size.x+Room.wallSpessor;
				door.setSide("right");
			}
			else{
				door.setPosition(root.position.x-Room.wallSpessor,root.position.y);
				room.position.x=root.position.x-room.size.x-Room.wallSpessor;
				door.setSide("left");
			}
			
			
			if(doorPosA<0)
				doorPosA=0;
			else if(doorPosA>root.height-door.width)
				doorPosA=root.height-door.width;
			
			if(doorPosB<0)
				doorPosB=0;
			else if(doorPosB>room.height-door.width)
				doorPosB=room.height-door.width;
			
			
			door.position.y=root.position.y+doorPosA*Room.tileHeight;
			room.position.y=door.position.y-doorPosB*Room.tileHeight;
			
		}
		else{
			if(flowVector.y>0){
				door.setPosition(root.position.x,root.position.y+root.size.y);
				room.position.y=root.position.y+root.size.y+Room.wallSpessor;
				door.setSide("bottom");
			}
			else{
				door.setPosition(root.position.x,root.position.y-Room.wallSpessor);
				room.position.y=root.position.y-room.size.y-Room.wallSpessor;
				door.setSide("top");
			}		
			
			//System.out.println(doorPosA+" "+doorPosB+" "+root.width+" "+door.width);
			
			if(doorPosA<0)
				doorPosA=0;
			else if(doorPosA>root.width-door.width)
				doorPosA=root.width-door.width;
			
			if(doorPosB<0)
				doorPosB=0;
			else if(doorPosB>room.width-door.width)
				doorPosB=room.width-door.width;
			
			//System.out.println(doorPosA+" "+doorPosB);
			
			door.position.x=root.position.x+doorPosA*Room.tileWidth;
			room.position.x=door.position.x-doorPosB*Room.tileWidth;
			
		}
		
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).getClass()==Room.class && map.get(i)!=room && map.get(i)!=root){
				Room r=(Room) map.get(i);
				if(room.checkCollision(r,10))
					return false;
			}
		}
		//System.out.println("Added room");
		door.setRelativePos(doorPosA, doorPosB);
		door.confirmRooms();
		map.add(room);
		map.add(door);
		return true;	
		
	}
	
	
	
	public Room getStartRoom(){
		return startRoom;
	}
	
	
	public void renderAllWalls(){
		for(int i=0;i<map.size();i++){
			if(map.get(i).getClass()==Room.class){
			Room t=(Room) map.get(i);
			t.addWalls(entities);
			}
		}
		
		for(int i=0;i<entities.size();i++){
			if(entities.get(i).getClass()==Wall.class){
				Wall w=(Wall) entities.get(i);
				if(Math.abs(w.length)==0){
					entities.remove(w);
					w.room.walls.remove(w);
					System.out.println(w.length);
				}
				
				
			}
		}
		
	}
	
	//ROOM TEMPLATES//
	
	//BASIC ROOMS//
	
	private Room squareRoom(int r){
		r=(r>3?r:3);
		return new Room(r,r*2);
	}
		
	private Room antiChamber(){
		return new Room(2,3);
	}
	
	private Room corridor(int r,boolean vertical){
		if(vertical)
			return new Room(2,r>3?r:3);
		else
			return new Room(r>2?r:2,3);
	}

	
	
	
}
