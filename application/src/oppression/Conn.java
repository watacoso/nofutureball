package oppression;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;


public class Conn extends Entity{

	public Room rA,rB;
	private String sideA,sideB;
	private int posA,posB;
	public int width;
	public String side;
	private Image sprite;
	public boolean visible;
	
	public Conn(int doorWidth) {
		super(0, 0);
		width=doorWidth;

	}
	
	public Conn(int doorWidth,Room rA,Room rB) {
		super(0, 0);
		this.rA=rA;
		this.rB=rB;
		rA.childs.add(rB);
		rB.parent=rA;
		width=doorWidth;
	}
	
	
	public void setPosition(float x,float y){
		position.x=x;
		position.y=y;
		box.setPosition(x, y);
	}
	
	public void confirmRooms(){
		if(rA==null || rB==null) return;
		rA.doors.add(this);
		rB.doors.add(this);
	}
	
	public void setSide(String side){
		float x,y;
		if(side=="top" || side=="bottom"){
			size.x =x= width*Room.tileWidth;
			size.y =y= Room.wallSpessor;
			sprite=Sprite.getImage("DOOR","H");
		}
		else{
			//width+=1;
			size.y =y= width*Room.tileHeight;
			size.x =x= Room.wallSpessor;
			sprite=Sprite.getImage("DOOR","V");
		}
		
		pivot.x=size.x/2;
		pivot.y=size.y/2;
		box.setSize(x, y);
		//box.setPivot(x/2, y/2);
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
	
	
	
	public int compareTo(Conn o) {
		if(side=="top" || side=="bottom"){
			if (o.box.left()  < box.left())
				return 1;
			if (o.box.left() > box.left())
				return -1;
			return -1;
		}
		else{
			if (o.box.top()  < box.top())
				return 1;
			if (o.box.top()  > box.top())
				return -1;
			return -1;
		}
	}
	

	public void render(Camera cam){
		
			//if(!visible){
			//	if(rA.visited || rB.visited)
			//		visible=true;
			// return;
			//}
				
		
			Vector2f screenPos = getScreenPos(cam);

			float _width = box.width * cam.getZoom();
			float _height = box.height * cam.getZoom();
			
			sprite.draw(screenPos.x, screenPos.y, _width, _height);
		
	}	

}
	

