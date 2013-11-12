package oppression;

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
			box.width=conn.box.width;
			box.height=Wall.height+1;
			box.setPosition(conn.position.x, conn.position.y-Wall.height);
			box.setSize(conn.box.getSize().x, Wall.height+1);
			setImage("DOORV", "OPEN");

		}
		else{
			position.x=conn.position.x;
			position.y=conn.position.y-Wall.height;
			box.width=conn.box.width;
			box.height=conn.box.height+Wall.height;
			box.setPosition(conn.position.x, conn.position.y-Wall.height);
			box.setSize(conn.box.width, conn.box.height+Wall.height);
			setImage("DOORH", "OPEN");
		}
		collisionBox=box;
	}
	
	public void openDoor(){
		
	}
	
	public void closeDoor(){
		
	}
	
	public void unlockDoor(){
		
	}
	

}
