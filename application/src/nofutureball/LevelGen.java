package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class LevelGen {

	public Room startRoom;
	private Container entities,map;
	
	public int nRooms=4;
	private boolean validCompound;
	private ArrayList<Room> roomsBuffer=new ArrayList<Room>();
	private ArrayList<Door> doorsBuffer= new ArrayList<Door>();
	public ArrayList<Room> roomsPool=new ArrayList<Room>();
	
	public LevelGen(Container entities,Container map){
		this.map=map;
		this.entities=entities;
		startRoom=squareRoom(6);
		startRoom.flowVector.set(0, -1);
		map.add(startRoom);
		roomsPool.add(startRoom);
	}
	
	public void generateMap(){
			
		placeNewCompound();
		renderAllWalls();
	}
	
	
	private void placeNewCompound(){
		for(int i=0;i<50;i++){
			do{
				initBuffers();
				Room casualRoom= roomsPool.get((int) Math.floor(Math.random()*roomsPool.size()));
				int compoundChooser=(int) Math.ceil(Math.random()*3);
				switch(compoundChooser){
				case 1:
					rType1(casualRoom,(int) Math.ceil(Math.random()*3-2));
					break;
				case 2:
					rType2(casualRoom,(int) Math.ceil(Math.random()*3-2));
					break;
				case 3:
					rType3(casualRoom,(int) Math.ceil(Math.random()*3-2));
					break;
				}
				
				if(validCompound){
					executeBuffers();
				}
			}
			while(!validCompound);
		}
	}
	
	
	private void initBuffers(){
		doorsBuffer.clear();
		roomsBuffer.clear();
		validCompound=true;
	}
	
	private void executeBuffers(){
		
		//System.out.println(doorsBuffer.size());
		for(int i=0;i<doorsBuffer.size();i++){
			doorsBuffer.get(i).confirmRooms();
			map.add(doorsBuffer.get(i));
		}
		
		for(int i=0;i<roomsBuffer.size();i++){
			map.add(roomsBuffer.get(i));
			roomsPool.add(roomsBuffer.get(i));
		}
	}
	
	public boolean appendRoom(Room root,Room room,int dir,int doorWidth,int doorPosA,int doorPosB){

		Door door=null;
		Vector2f flowVector=new Vector2f(root.flowVector);
		
		flowVector.add(90*dir);
		
		room.flowVector=flowVector;
		
		if(Math.abs(flowVector.x)>Math.abs(flowVector.y)){
			if(doorWidth>Math.min(root.height, room.height))
				doorWidth=Math.min(root.height, room.height);
			if(doorWidth<1)
				doorWidth=1;
			door=new Door(doorWidth,root,room);
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
			if(doorWidth>Math.min(root.width, room.width))
				doorWidth=Math.min(root.width, room.width);
			if(doorWidth<1)
				doorWidth=1;
			door=new Door(doorWidth,root,room);
			
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
		door.setRelativePos(doorPosA, doorPosB);
		
		for(int i=0;i<map.size();i++){
			if(map.get(i).getClass()==Room.class && map.get(i)!=room && map.get(i)!=root){
				Room r=(Room) map.get(i);
				if(room.checkCollision(r,128)){
					validCompound=false;
					return false;
				}
			}
		}
		
		for(int i=0;i<roomsBuffer.size();i++){
			if(roomsBuffer.get(i).getClass()==Room.class && roomsBuffer.get(i)!=room && roomsBuffer.get(i)!=root){
				Room r=(Room) roomsBuffer.get(i);
				if(room.checkCollision(r,128)){
					validCompound=false;
					return false;
				}
			}
		}
		

		roomsBuffer.add(room);
		doorsBuffer.add(door);

		
		return true;	
		
	}
	
	
	
	public Room getStartRoom(){
		return startRoom;
	}
	
	public Room getRandomRoom(){
		return roomsPool.get((int)Math.floor(Math.random()*roomsPool.size()));
	}
	
	


	//BASIC BLOCKS//
	
	private Room orientedRoom(int width,int height,boolean rotate){
		if(rotate)
			return new Room(height>2?height:2,width>2?width:2);
		else
			return new Room(width>2?width:2,height>2?height:2);
	}
	
	private Room squareRoom(int r){
		r=(r>2?r:2);
		return new Room(r,r);
	}
		
	private Room antiChamber(){
		return new Room(2,2);
	}
	
	private Room corridor(int r,boolean rotate){
		return orientedRoom(r,2,rotate);
	}
	
	//X COMPOUNDS//
	
	
	//Y COMPOUNDS//
	
	//UNIVERSAL COMPOUNDS//
	
	private void rType1(Room startRoom,int direction){
		//System.out.println(getSizeByFlow(startRoom,direction));
		int size=getSizeByFlow(startRoom,direction);
		int index1=getDiscreteIndex(size,2,1,2);
		int w=(int) (2*Math.ceil(Math.random()*4+1));
		int h=(int) (2*Math.ceil(Math.random()*4+1));
		Room r=orientedRoom(w,h,false);
		
		size=getSizeByFlow(r,direction);
		int index2=getDiscreteIndex(size,2,1,2);
		
		//System.out.println(index1+" "+index2);
		
		appendRoom(startRoom,r,direction,2,index1,index2);
	}
	
	private void rType2(Room startRoom,int direction){
		//System.out.println(getSizeByFlow(startRoom,direction));
		int size=getSizeByFlow(startRoom,direction);
		int index1=getDiscreteIndex(size,2,1,2);
		int w=(int) (2*Math.ceil(Math.random()*4+1));
		int h=(int) (2*Math.ceil(Math.random()*4+1));
		Room r=orientedRoom(w,h,false);
		
		size=getSizeByFlow(r,direction);
		int index2=getDiscreteIndex(size,2,1,2);
		
		//System.out.println(index1+" "+index2);
		Room ac=antiChamber();
		appendRoom(startRoom,ac,direction,2,index1,0);
		appendRoom(ac,r,0,2,0,index2);
	}
	
	private void rType3(Room startRoom,int direction){
		//System.out.println(getSizeByFlow(startRoom,direction));
		int size=getSizeByFlow(startRoom,direction);
		int index1=getDiscreteIndex(size,2,1,2);
		int w=(int) (2*Math.ceil(Math.random()*4+1));
		int h=(int) (2*Math.ceil(Math.random()*4+1));
		Room r=orientedRoom(w,h,false);
		
		size=getSizeByFlow(r,direction);
		int index2=getDiscreteIndex(size,2,1,2);
		
		int cLength=(int) Math.ceil(Math.random()*5+2);
		Room co=corridor(cLength,isFlowVertical(startRoom,direction));
		appendRoom(startRoom,co,direction,2,index1,0);
		appendRoom(co,r,0,2,0,index2);
	}
	
	//OTHER TOOLS//
	
	private boolean isFlowVertical(Room room,int direction){
		Vector2f v=room.flowVector.copy().add(direction*90);
		if(Math.abs(v.x)==0)
			return true;
		return false;
	}
	
	private int getSizeByFlow(Room room,int direction){
		Vector2f v=room.flowVector.copy().add(direction*90);
		if(Math.abs(v.x)==0)
			return room.height;
		return room.width;
	}
	
	private int getDiscreteIndex(int sideSize,int doorWidth,int numerator,int divisor){
		return numerator*sideSize/divisor-doorWidth/2;
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
					//System.out.println(w.length);
				}	
			}
		}
	}
}
