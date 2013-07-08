package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Room extends Entity {

	public Boolean occupied;

	private Graphics g = new Graphics();
	Image floor;

	public Room(float x, float y, int width, int height) {
		super(x, y, width * 60, height * 30);
		this.width = width;
		this.height = height;
		try {
			floor = new Image("assets/sprites/floorTiles.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** Number of tiles on the x-axis */
	private int width = 0;
	/** Number of tiles on the y-axis */
	private int height = 0;

	public void update() {

	}

	public void render(Camera cam) {
		Vector2f screenPos = getScreenPos(cam);
		g.setColor(Color.decode("#565C76"));
		
		// Floor
		Image scaledFloorTile = floor.getScaledCopy(cam.getZoom() * 1.01f);
		// just caching values to limit calculations
		float floorWidth = 60 * cam.getZoom();
		float floorHeight = 30 * cam.getZoom();
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
		target.add(new Wall(this,position.x, position.y, 1, size.x));
		target.add(new Wall(this,position.x - 15, position.y, 2, size.y));
		target.add(new Wall(this,position.x + size.x, position.y, 2, size.y));
		target.add(new Wall(this,position.x, position.y + size.y + 15, 1, size.x));

	}
	

}
