package nofutureball;

public class Level {

	public Room startRoom;
	private Container entities,map;
	public Level(Container entities,Container map){
		this.map=map;
		this.entities=entities;
		startRoom=new Room(0,-120, 6, 20);
		map.add(startRoom);
		//currentRoom.addWalls(entities);
	}
	
	public void generateMap(){
		addRoom(startRoom,new Room(5,10), "right",10, false);
		addRoom(startRoom,new Room(5,7), "left", 18, false);
		addRoom(startRoom,new Room(5,7), "top", 2, false);
		addRoom(startRoom,new Room(5,7), "bottom", 1, false);
		renderAllWalls();
	}
	
	public void addRoom(Room currentRoom,Room room,String side,int offset,boolean compositeRoom){
		Door door=new Door(room,currentRoom,2);
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
			System.out.println(d);
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
		
		
		map.add(room);
		map.add(door);
		//currentRoom=room;
		//room.addWalls(entities);
		
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
	
}
