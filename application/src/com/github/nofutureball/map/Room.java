package com.github.nofutureball.map;

import java.util.ArrayList;
import java.util.Collections;


import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.github.nofutureball.entity.Container;
import com.github.nofutureball.entity.Entity;
import com.github.nofutureball.entity.Sprite;
import com.github.nofutureball.main.Camera;


/**
 * Room Class!
 * Handles the doors, walls, wallTiles
 * @author watacoso
 *
 */
public class Room extends Entity {
	
	public ArrayList<Conn> doors;
	public ArrayList<Wall> walls;
	ArrayList<Image> wallTiles;
	public Room parent;
	public ArrayList<Room> childs;
	private Graphics g = new Graphics();
	Image floor;
	
	public static int wallSpessor=64;
	public static int tileWidth=256;
	public static int tileHeight=256;
	public int width,height;
	public Vector2f flowVector=new Vector2f();
	public boolean visited=false;
	public int numActors;
	public int roomType;
	public String baseColor;
	
	/**
	 * General Constructor of a room
	 * @param x X-Position
	 * @param y Y-Position
	 * @param width width of the Room
	 * @param height Height of the Room
	 * @param type type of the room
	 */
	public Room(float x, float y, int width, int height,int type) {
		super(x, y, width * tileWidth, height * tileHeight,0,0);
		
		this.width = width;
		this.height = height;
		doors=new ArrayList<Conn>();
		walls=new ArrayList<Wall>();
		childs=new ArrayList<Room>();
		wallTiles=new ArrayList<Image>();
		numActors=0;
		setType(type);
	}
	
	/**
	 * Constructor
	 * @param width Width of the Room
	 * @param height Height of the Room
	 * @param type Type of the room
	 */
	public Room(int width, int height,int type) {
		super(0, 0, width * tileWidth, height * tileHeight,0,0);
		this.width = width;
		this.height = height;
		doors=new ArrayList<Conn>();
		walls=new ArrayList<Wall>();
		childs=new ArrayList<Room>();
		numActors=0;
		
		setType(type);
	}
	
	/**
	 * The type is responsible for the color of the room
	 * @param type Between 1-4
	 */
	private void setType(int type) {
		roomType=type;
		floor=Sprite.getImage("FLOOR", "R"+roomType);
		switch(roomType){
		case 1:
			baseColor="#7A4848";
			break;
		case 2:
			baseColor="#546548";
			break;
		case 3:
			baseColor="#576B70";
			break;
		case 4:
			baseColor="#6F4B71";
			break;
		}
	}

	/**
	 * Slick Update-function
	 */
	public void update(Game game) {
		if(!visited)
			if(numActors>0)
				visited=true;
	}

	/**
	 * Slick render-function
	 */
	public void render(Camera cam) {		
		//if(!visited)return;
		Vector2f screenPos = getScreenPos(cam);
				
		// Floor

		// just caching values to limit calculations
		float floorWidth = tileWidth * cam.getZoom();
		float floorHeight = tileHeight * cam.getZoom();
		float _wallSpessor = wallSpessor*cam.getZoom();
		
		g.setColor(Color.decode("#565C76"));
		g.setLineWidth(_wallSpessor);
		//g.drawRect(screenPos.x, screenPos.y, floorWidth*width, floorHeight*height);

		g.setColor(Color.decode("#565C76"));
		
		g.fillRect(screenPos.x, screenPos.y, floorWidth*width, floorHeight*height);
		//floor.draw(screenPos.x, screenPos.y, floorWidth*width, floorHeight*height);
		
		for (float y = 0, i = 0; i < height; y += floorHeight, i ++) {
			for (float x = 0, j = 0; j < width; x += floorWidth, j ++) {
				
				// The commented out line would draw with a cached scaled floor (= more efficient) but it seems to leave
				// gaps between the tiles frequently. This should be fixed.
				floor.draw(screenPos.x + x, screenPos.y + y, floorWidth, floorHeight);
			}
		}
		//super.render(cam);
	}
	
	public void setPosition(float x, float y){
		position.set(x,y);
		box.setPosition(x, y);
	}
	
	public void move(float x, float y){
		position.x+=x;
		position.y+=y;
		box.move(x, y);
	}

	/**
	 * Adds walls, uses the addWall function
	 * @param target The Container
	 */
	public void addWalls(Container target) {
		Collections.sort(doors);
		float LY=0,RY=0,TX=0,BX=0;
		Wall w=null;
		for(int i=0;i<doors.size();i++){
			Conn r=doors.get(i);
			
			w=null;
			switch (r.getSide(this)){
				case "top":
					w=new Wall(this,position.x+TX, position.y, 1, r.getRelativePos(this)*Room.tileWidth-TX);
					TX=r.getRelativePos(this)*Room.tileWidth+r.box.getSize().x;
					break;
				case "bottom":
					w=new Wall(this,position.x + BX, position.y + box.getSize().y + wallSpessor, 3, r.getRelativePos(this)*Room.tileWidth-BX);
					BX=r.getRelativePos(this)*Room.tileWidth+r.box.getSize().x;
					break;
				case "left":
					w=new Wall(this,position.x - wallSpessor, position.y+LY, 2, r.getRelativePos(this)*Room.tileHeight-LY-wallSpessor);
					LY=r.getRelativePos(this)*Room.tileHeight+r.box.getSize().y;
					break;
				case "right":
					w=new Wall(this,position.x + box.getSize().x, position.y+RY, 2, r.getRelativePos(this)*Room.tileHeight-RY-wallSpessor);
					RY=r.getRelativePos(this)*Room.tileHeight+r.box.getSize().y;
					break;
			}

			if(w!=null)
				addWall(w,target);
			
		}
		
		w=new Wall(this,position.x+TX, position.y, 1, box.getSize().x-TX);
		addWall(w,target);
		w=new Wall(this,position.x - wallSpessor, position.y+LY, 4, box.getSize().y-LY);
		addWall(w,target);
		w=new Wall(this,position.x + box.getSize().x, position.y+RY, 4, box.getSize().y-RY);
		addWall(w,target);
		w=new Wall(this,position.x+BX, position.y + box.getSize().y + wallSpessor, 3, box.getSize().x-BX);
		addWall(w,target);

	}
	
	/**
	 * Adds one side of the walls
	 * @param w The Wall
	 * @param target the target container
	 */
	private void addWall(Wall w,Container target){
		if(Math.abs(w.length)>wallSpessor){
			float t=0;
			if(w.position.y-position.y==0 && w.getType()%2==0 || w.position.x-position.x==0 && w.getType()%2==1){
				t=wallSpessor;
				if(w.getType()%2==0){
					w.position.y-=t*2;
					w.setLength(w.length+t);
				}
				else if(w.getType()%2==1){
					w.position.x-=t;
					w.setLength(w.length+t);
				}
			}
			else if(w.getType()%2==0)
				w.position.y-=t*2;
			
			target.add(w);
			w.addWallToRoom(this);
		}
	}
	
	/**
	 * adds A Panel
	 * @todo is this used?
	 * @param target container
	 * @param type String
	 */
	public void addPanel(Container target,String type){
		target.add(new Panel(this,30,type));
	}
	
	/**
	 * Checking collision
	 * @param e Room
	 * @param span 
	 * @return true is colliding, false if not
	 */
	public boolean checkCollision(Room e,float span){
		if(position.x-span>e.position.x+e.box.getSize().x ||
		   position.x+box.getSize().x+span<e.position.x)
			return false;
		if(position.y-span>e.position.y+e.box.getSize().y ||
		   position.y+box.getSize().y+span<e.position.y)
			return false;
		return true;
	}
}
