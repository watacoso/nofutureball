package nofutureball;
public class Door extends Entity{

	public Room rA,rB;
	public int width;
	public String side;
	public Door(Room rA,Room rB,int doorWidth) {
		super(0, 0);
		width=doorWidth;
		this.rA=rA;
		this.rB=rB;
		rA.doors.add(this);
		rB.doors.add(this);
	}
	
	public void setPosition(float x,float y){
		position.x=x;
		position.y=y;
	}
	
	public void setSide(String side){
		if(side=="top" || side=="bottom"){
			size.x = width*Room.tileWidth;
			size.y = Room.wallSpessor;
		}
		else{
			size.y = width*Room.tileHeight;
			size.x = Room.wallSpessor;
		}
		this.side=side;
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
		
	}

}
	

