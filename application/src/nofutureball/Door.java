package nofutureball;

public class Door extends GameObject {
	
	public Conn conn;
	public boolean open;
	public boolean locked;
	
	
	
	public Door(Conn conn,int type){
		super(0,0,0,0);
		this.conn=conn;
		if(conn.side=="left" || conn.side=="right"){
			position.x=conn.position.x;
			position.y=conn.position.y-Wall.height;
			//spriteRelativePosition.y-=Wall.height;
			size.x=conn.size.x;
			size.y=Wall.height+1;
			setImage("DOORV", "OPEN");
		}
		else{
			position.x=conn.position.x;
			position.y=conn.position.y-Wall.height;
			size.x=conn.size.x;
			size.y=conn.size.y+Wall.height;
			setImage("DOORH", "OPEN");
		}
	}
	
	public void openDoor(){
		
	}
	
	public void closeDoor(){
		
	}
	
	public void unlockDoor(){
		
	}
	

}
