package nofutureball;


public class Door extends Entity{

	public Room rA,rB;
	private String sideA,sideB;
	private int posA,posB;
	public int width;
	public String side;
	public Door(int doorWidth) {
		super(0, 0);
		width=doorWidth;

	}
	
	public Door(int doorWidth,Room rA,Room rB) {
		super(0, 0);
		this.rA=rA;
		this.rB=rB;
		width=doorWidth;

	}
	
	
	public void setPosition(float x,float y){
		position.x=x;
		position.y=y;
	}
	
	public void confirmRooms(){
		if(rA==null || rB==null) return;
		rA.doors.add(this);
		rB.doors.add(this);
	}
	
	public void setSide(String side){
		if(side=="top" || side=="bottom"){
			size.x = width*Room.tileWidth;
			size.y = Room.wallSpessor;
		}
		else{
			width+=1;
			size.y = width*Room.tileHeight;
			size.x = Room.wallSpessor;
		}
		
		pivot.x=size.x/2;
		pivot.y=size.y/2;
		this.side=side;
		sideA=side;
		switch (side){
		case "left":
			sideB="right";
			break;
		case "right":
			sideB="left";
			break;
		case "top":
			sideB="bottom";
			break;
		case "bottom":
			sideB="top";
			break;
		}
	}
	
	public String getSide(Room room){
		if(room==rA)
			return sideA;
		if(room==rB)
			return sideB;
		return null;
	}
	
	public void setRelativePos(int posA,int posB){
		this.posA=posA;
		this.posB=posB;
	}
	
	public int getRelativePos(Room room){
		if(room==rA)
			return posA;
		if(room==rB)
			return posB;
		return -1;
	}
	
	public int compareTo(Door o) {
		if(side=="top" || side=="bottom"){
			if (o.position.x  < this.position.x)
				return 1;
			if (o.position.x > this.position.x)
				return -1;
			return -1;
		}
		else{
			if (o.position.y  < this.position.y)
				return 1;
			if (o.position.y  > this.position.y)
				return -1;
			return -1;
		}
	}
	

	public void render(Camera cam){
	//	super.render(cam);
	}	

}
	

