package nofutureball;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

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
	
	public Room(float x, float y, int width, int height) {
		super(x, y, width * tileWidth, height * tileHeight,width * tileWidth/2,height * tileHeight/2);
		
		this.width = width;
		this.height = height;
		/*try {
			floor = new Image("assets/sprites/floorTiles.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		doors=new ArrayList<Conn>();
		walls=new ArrayList<Wall>();
		childs=new ArrayList<Room>();
		wallTiles=new ArrayList<Image>();
		numActors=0;
		
		roomType=(int)Math.ceil(Math.random()*4);
		floor=Sprite.getSprite("FLOOR", "R"+roomType);
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
		//floor=Sprite.getSprite("FLOOR", "TEST");
		
	}
	
	public Room(int width, int height) {
		super(0, 0, width * tileWidth, height * tileHeight);
		this.width = width;
		this.height = height;
		/*try {
			floor = new Image("assets/sprites/floorTiles.png");
		} catch (SlickException e) {

			e.printStackTrace();
		}*/
		doors=new ArrayList<Conn>();
		walls=new ArrayList<Wall>();
		childs=new ArrayList<Room>();
		numActors=0;
		
		roomType=(int)Math.ceil(Math.random()*4);
		floor=Sprite.getSprite("FLOOR", "R"+roomType);
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
		//floor=Sprite.getSprite("FLOOR", "TEST");
	}
	
	

	public void update(Game game) {
		if(!visited)
			if(numActors>0)
				visited=true;
	}

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
		
		
		

	}

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
					TX=r.getRelativePos(this)*Room.tileWidth+r.size.x;
					break;
				case "bottom":
					w=new Wall(this,position.x + BX, position.y + size.y + wallSpessor, 3, r.getRelativePos(this)*Room.tileWidth-BX);
					BX=r.getRelativePos(this)*Room.tileWidth+r.size.x;
					break;
				case "left":
					w=new Wall(this,position.x - wallSpessor, position.y+LY, 2, r.getRelativePos(this)*Room.tileHeight-LY-wallSpessor);
					LY=r.getRelativePos(this)*Room.tileHeight+r.size.y;
					break;
				case "right":
					w=new Wall(this,position.x + size.x, position.y+RY, 2, r.getRelativePos(this)*Room.tileHeight-RY-wallSpessor);
					RY=r.getRelativePos(this)*Room.tileHeight+r.size.y;
					break;
			}

			if(w!=null)
				addWall(w,target);
			
		}
		
		w=new Wall(this,position.x+TX, position.y, 1, size.x-TX);
		addWall(w,target);
		w=new Wall(this,position.x - wallSpessor, position.y+LY, 4, size.y-LY);
		addWall(w,target);
		w=new Wall(this,position.x + size.x, position.y+RY, 4, size.y-RY);
		addWall(w,target);
		w=new Wall(this,position.x+BX, position.y + size.y + wallSpessor, 3, size.x-BX);
		addWall(w,target);

	}
	
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
	
	public void addPanel(Container target,String type){
		target.add(new Panel(this,30,type));
	}
	
	protected boolean checkCollision(Room e,float span){
		if(position.x-span>e.position.x+e.size.x ||
		   position.x+size.x+span<e.position.x)
			return false;
		if(position.y-span>e.position.y+e.size.y ||
		   position.y+size.y+span<e.position.y)
			return false;
		return true;
		
	}
	

}
