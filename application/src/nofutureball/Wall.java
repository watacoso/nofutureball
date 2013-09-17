package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Wall extends Entity {

	Graphics g;
	private int type;

	final int padding = Room.wallSpessor;
	static final float height = 128;
	static final float lowHeight=10;
	float length;
	
	public Room room;


	public Wall(Room room,float x, float y, int type, float length) { // 1:horizzontalSection
															// 2:vertical
															// (3:horizzontalFull)
		super(x, y-Room.wallSpessor);
		this.type = type;
		setLength(length);
		g = new Graphics();
	}
	
	public void setLength(float l){
		this.length = l;

		switch (type) {
			case 1:
				size.x = length;
				size.y = padding;
				break;
			case 2:
				position.y+=padding;
				size.y = length+padding;
				size.x = padding;
				break;
			case 3:
				size.x = length;
				size.y = padding;
				break;
			case 4:
				position.y+=padding;
				size.y = length+padding;
				size.x = padding;
				break;
		}
		pivot.x=size.x/2;
		pivot.y=size.y/2;
	}
	
	public void addWallToRoom(Room room){
		this.room=room;
		room.walls.add(this);
	}
	
	public int getType(){
		return type;
	}

	public void render(Camera camera) {
		//if(true) return;
		Vector2f screenPos = getScreenPos(camera);


		float _padding =padding * camera.getZoom();
		float _height = height * camera.getZoom();
		float _length = length * camera.getZoom();
		

		
		switch (type) {
			case 1:
				//if(true)return;
				g.setColor(Color.decode("#45495F"));
				g.fillRect( screenPos.x,  (screenPos.y - _height), _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _length, _padding);
				//super.render(camera);
				break;
			case 2:
				//if(true)return;
				g.setColor(Color.decode("#45495F"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _padding, _length + _padding  + _height);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _padding, _length + _padding );
				//super.render(camera);
				break;
			case 3:
				//if(true)return;
				g.setColor(Color.decode("#0C060E")); //#0C060E #45495F
				g.fillRect( screenPos.x,  (screenPos.y - _height), _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _length, _padding);
				//super.render(camera);
				break;
			case 4:
				//if(true)return;
				g.setColor(Color.decode("#0C060E")); //#0C060E #45495F
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _padding, _length + _padding  + _height);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _padding, _length + _padding );
				//super.render(camera);
				break;
		}
		
		
		
	}

}
