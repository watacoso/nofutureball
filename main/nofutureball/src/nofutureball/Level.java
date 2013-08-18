package nofutureball;

import java.util.Random;

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
		//startRoom.addPanel(entities,"TEST");
		//expand(startRoom,0);
		
		
		appendRoom(startRoom,antiChamber(),1,0,0);
		appendRoom(startRoom,antiChamber(),1,7,0);
		appendRoom(startRoom,antiChamber(),-1,0,0);
		appendRoom(startRoom,antiChamber(),-1,7,0);
		appendRoom(startRoom,antiChamber(),0,0,0);
		appendRoom(startRoom,antiChamber(),0,7,0);
		appendRoom(startRoom,antiChamber(),2,0,0);
		appendRoom(startRoom,antiChamber(),2,7,0);
		//appendRoom(startRoom,antiChamber(),0,2,2);
		//appendRoom(startRoom,genericRoom(),1,0,2);
		
		renderAllWalls();
	}
	
	
	private void expand(Room r,int haltProbability){
		
		
		if(nRooms<=0) return;
		Random v=new Random();
		int value;
		String side = null;
		Room exp = null;
		for(int i=0;i<3;i++){
			int attemps=3;
			while(attemps>0){
				value=v.nextInt(4);
				switch(value){
				case 0:
					side="left";
					break;
				case 1:
					side="right";
					break;
				case 2:
					side="top";
					break;
				case 3:
					side="bottom";
					break;
				}
				value=v.nextInt(2);
				switch(value){
				case 0:
					boolean n;
					if(side=="left" || side=="right")
						n=false;
					else
						n=true;
					exp=corridor(r,n);
					break;
				case 1:
					exp=genericRoom(r);
					break;
				case 2:
					exp=specialRoom(r);
					break;
				}

				value=v.nextInt(8);
			
				if(addRoom(r,exp,side,value-4,false)){
					nRooms--;
					//System.out.println(nRooms);
					value=v.nextInt(50)+50;
					if(value>haltProbability){
							expand(exp,haltProbability);

					}
					lastRoom=exp;
					continue;
				}
				else
					attemps--;
			}
		}
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
			
			
			door.position.y=doorPosA*Room.tileHeight;
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
			
			if(doorPosA<0)
				doorPosA=0;
			else if(doorPosA>root.width-door.width)
				doorPosA=root.width-door.width;
			
			if(doorPosB<0)
				doorPosB=0;
			else if(doorPosB>room.width-door.width)
				doorPosB=room.width-door.width;
			
			
			door.position.x=doorPosA*Room.tileWidth;
			room.position.x=door.position.x-doorPosB*Room.tileWidth;
			
		}
		
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).getClass()==Room.class && map.get(i)!=room && map.get(i)!=root){
				Room r=(Room) map.get(i);
				if(room.checkCollision(r,10))
					return false;
			}
		}
		System.out.println("Added room");
		door.setRelativePos(doorPosA, doorPosB);
		door.confirmRooms();
		map.add(room);
		map.add(door);
		return true;	
		
	}
	
	
	public boolean addRoom(Room currentRoom,Room room,String side,int offset,boolean compositeRoom){
		Door door=new Door(2,currentRoom,room);
		switch (side){
		case "left":
			door.setPosition(currentRoom.position.x-Room.wallSpessor,currentRoom.position.y);
			room.position.x=currentRoom.position.x-room.size.x-Room.wallSpessor;
			break;
		case "right":
			door.setPosition(currentRoom.position.x+currentRoom.size.x,currentRoom.position.y);
			room.position.x=currentRoom.position.x+currentRoom.size.x+Room.wallSpessor;
			break;
		case "top":
			door.setPosition(currentRoom.position.x,currentRoom.position.y-Room.wallSpessor);
			room.position.y=currentRoom.position.y-room.size.y-Room.wallSpessor;
			break;
		case "bottom":
			door.setPosition(currentRoom.position.x,currentRoom.position.y+currentRoom.size.y);
			room.position.y=currentRoom.position.y+currentRoom.size.y+Room.wallSpessor;
			break;
		}
		door.setSide(side);
		if(side=="left"||side=="right"){
			float d=currentRoom.size.y/Room.tileHeight-door.width-2;
			if(offset>d)
				offset=(int) d;
			else{
			d=-room.size.y/Room.tileHeight+door.width+2;
			
			if(offset<d)
				offset=(int) d;
			}
			
			
			room.position.y=currentRoom.position.y+offset*Room.tileHeight;
			float min=Math.min(currentRoom.position.y+currentRoom.size.y, room.position.y+room.size.y);
			float max=Math.max(currentRoom.position.y, room.position.y);
			d=(min-max)/Room.tileHeight -door.width-2;
			
			door.position.y=(float) (max+Math.floor(Math.random()*d+1)*Room.tileHeight);
			
		}
		else{
			float d=currentRoom.size.x/Room.tileWidth-door.width-2;
			if(offset>d)
				offset=(int) d;
			else{
			d=-room.size.x/Room.tileWidth+door.width+2;
			if(offset<d)
				offset=(int) d;
			}
			
			
			room.position.x=currentRoom.position.x+offset*Room.tileWidth;
			float min=Math.min(currentRoom.position.x+currentRoom.size.x, room.position.x+room.size.x);
			float max=Math.max(currentRoom.position.x, room.position.x);
			d=(min-max)/Room.tileWidth -door.width-2;
			
			door.position.x=(float) (max+Math.floor(Math.random()*d+1)*Room.tileWidth);
		}
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).getClass()==Room.class && map.get(i)!=room && map.get(i)!=currentRoom){
				Room r=(Room) map.get(i);
				if(room.checkCollision(r,60)){
					return false;
				}
			}
		}
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
	}
	
	//ROOM TYPES DEFINITION//
	
	private Room corridor(Room parent,boolean Horizzontal){
		if(Horizzontal)
			return new Room(4,(int) (Math.random()*1+4));
		else
			return new Room((int) (Math.random()*1+4),5);
	}
	
	private Room genericRoom(Room parent){
		return new Room((int) (Math.random()*10+4),(int) (Math.random()*10+8));
	}
	
	private Room genericRoom(){
		return new Room((int) (Math.random()*10+4),(int) (Math.random()*10+8));
	}
	
	private Room antiChamber(){
		return new Room(2,3);
	}
	
	private Room specialRoom(Room parent){
		return new Room(4,5);
	}
	
	
	
}
