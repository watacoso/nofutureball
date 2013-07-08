package nofutureball;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Room extends Entity {

	public Boolean occupied;
	public ArrayList<Door> doors;
	private Graphics g = new Graphics();
	Image floor;
	
	public static int wallSpessor=15;
	public static int tileWidth=60;
	public static int tileHeight=30;
	public Room(float x, float y, int width, int height) {
		super(x, y, width * tileWidth, height * tileHeight);
		this.width = width;
		this.height = height;
		try {
			floor = new Image("assets/sprites/floorTiles.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doors=new ArrayList<Door>();
	}
	
	public Room(int width, int height) {
		super(0, 0, width * tileWidth, height * tileHeight);
		this.width = width;
		this.height = height;
		try {
			floor = new Image("assets/sprites/floorTiles.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doors=new ArrayList<Door>();
	}
	
	/** Number of tiles on the x-axis */
	private int width = 0;
	/** Number of tiles on the y-axis */
	private int height = 0;

	public void update() {

	}

	public void render(Camera cam) {
		Vector2f screenPos = getScreenPos(cam);
		
		
		// Floor
		Image scaledFloorTile = floor.getScaledCopy(cam.getZoom() * 1.01f);
		// just caching values to limit calculations
		float floorWidth = tileWidth * cam.getZoom();
		float floorHeight = tileHeight * cam.getZoom();
		float _wallSpessor = wallSpessor*cam.getZoom();
		g.setColor(Color.decode("#585D78"));
		g.setLineWidth(_wallSpessor);
		g.drawRect(screenPos.x, screenPos.y, floorWidth*width, floorHeight*height);
		g.setColor(Color.decode("#565C76"));
		g.fillRect(screenPos.x, screenPos.y, floorWidth*width, floorHeight*height);
		for (float y = 0, i = 0; i < height; y += floorHeight, i ++) {
			for (float x = 0, j = 0; j < width; x += floorWidth, j ++) {
				
				/* The commented out line would draw with a cached scaled floor (= more efficient) but it seems to leave
				 * gaps between the tiles frequently. This should be fixed. */
				scaledFloorTile.draw((int)(screenPos.x + x - 0.5),  (int)(screenPos.y + y - 0.5));
				//floor.draw(screenPos.x + x, screenPos.y + y, floorWidth, floorHeight);
			}
		}
		

	}

	public void addWalls(Container target) {
		Collections.sort(doors);
		float LY=0,RY=0,TX=0,BX=0;
		for(int i=0;i<doors.size();i++){
			Door r=doors.get(i);
			
			if(r.side=="top"||r.side=="bottom"){
				if(position.y==r.position.y+15){
					System.out.println("top");
					target.add(new Wall(this,position.x+TX, position.y, 1, r.position.x-position.x+TX));
					TX=r.position.x-position.x+r.size.x;
				}
				else{
					target.add(new Wall(this,position.x+BX, position.y + size.y + wallSpessor, 1, r.position.x-position.x+BX));
					BX=r.position.x-position.x+r.size.x;
				}
			}
			else{
				if(position.x==r.position.x+15){
					target.add(new Wall(this,position.x - wallSpessor, position.y+LY, 2, r.position.y-position.y+LY-15));
					LY=r.position.y-position.y+r.size.y;
				}
				else{
					target.add(new Wall(this,position.x + size.x, position.y+RY, 2, r.position.y-position.y+RY-15));
					RY=r.position.y-position.y+r.size.y;
				}
			}

		}
		
		target.add(new Wall(this,position.x+TX, position.y, 1, size.x-TX));
		target.add(new Wall(this,position.x - wallSpessor, position.y+LY, 2, size.y-LY));
		target.add(new Wall(this,position.x + size.x, position.y+RY, 2, size.y-RY));
		target.add(new Wall(this,position.x+BX, position.y + size.y + wallSpessor, 1, size.x-BX));

	}
	

}
