package mapElements;

import mainPackage.Camera;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import entityPackage.Entity;
import entityPackage.Sprite;

/**
 * Connection between two rooms
 * @author watacoso
 * 
 */

public class Conn extends Entity{

	public Room rA,rB;
	private String sideA,sideB;
	private int posA,posB;
	public int width;
	public String side;
	private Image sprite;
	public boolean visible;
	
	/**
	 * Width of the door
	 * @param doorWidth int width of the door
	 */
	public Conn(int doorWidth) {
		super(0, 0);
		width=doorWidth;
	}
	
	/**
	 * General Constructor
	 * Connectes two rooms whereas rB is the child of rA
	 * @param doorWidth Width of the door
	 * @param rA First room that is connected to rB
	 * @param rB Second room that is connected to rA
	 */
	public Conn(int doorWidth,Room rA,Room rB) {
		super(0, 0);
		this.rA=rA;
		this.rB=rB;
		rA.childs.add(rB);
		rB.parent=rA;
		width=doorWidth;
	}

	/**
	 * Sets the absolute position of this connection
	 * @param x X-Position
	 * @param y Y-Position
	 */
	public void setPosition(float x,float y){
		position.x=x;
		position.y=y;
		box.setPosition(x, y);
	}
	
	/**
	 * Rooms have to get confirmed before they get added
	 */
	public void confirmRooms(){
		if(rA==null || rB==null) return;
		rA.doors.add(this);
		rB.doors.add(this);
	}
	
	/**
	 * Decides weather the side is top, bottom, left or right
	 * @param side The side (top, bottom, left or right)
	 */
	public void setSide(String side){
		float x,y;
		if(side=="top" || side=="bottom"){
			x= width*Room.tileWidth;
			y= Room.wallSpessor;
			
			sprite=Sprite.getImage("DOOR","H");
		}
		else{
			x= Room.wallSpessor;
			y= width*Room.tileHeight;			
			sprite=Sprite.getImage("DOOR","V");
		}
		box.setSize(x, y);
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
	
	/**
	 * Returns the String of the side (top, bottom, left or right)
	 * @param room Which room of the connection is meant
	 * @return String top bottom left or right
	 */
	public String getSide(Room room){
		if(room==rA)
			return sideA;
		if(room==rB)
			return sideB;
		return null;
	}
	
	/**
	 * Sets the relative Position
	 * @todo document?
	 * @param posA
	 * @param posB
	 */
	public void setRelativePos(int posA,int posB){
		this.posA=posA;
		this.posB=posB;
	}
	
	/**
	 * Returns the relative position of one of the rooms the Conn is connected to
	 * @param room The room you want the relative position from
	 * @return returns -1 if room was wrong
	 */
	public int getRelativePos(Room room){
		if(room==rA)
			return posA;
		if(room==rB)
			return posB;
		return -1;
	}
	
	/**
	 * Compares two connections
	 * @param o
	 * @return
	 */
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

	/**
	 * Slick render function!
	 */
	public void render(Camera cam){
		
			//if(!visible){
			//	if(rA.visited || rB.visited)
			//		visible=true;
			// return;
			//}
				
			Vector2f screenPos = getScreenPos(cam);

			float _width = box.getSize().x * cam.getZoom();
			float _height = box.getSize().y * cam.getZoom();
			
			sprite.draw(screenPos.x, screenPos.y, _width, _height);
			//super.render(cam);
	}	
}
	

