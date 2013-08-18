package nofutureball;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Wall extends Entity {

	Graphics g;
	private int type;

	final int padding = Room.wallSpessor;
	static final float height = 10;
	static final float lowHeight=10;
	float length;
	
	public Room room;


	public Wall(Room room,float x, float y, int type, float length) { // 1:horizzontalSection
															// 2:vertical
															// (3:horizzontalFull)
		super(x, y-Room.wallSpessor);
		this.type = type;
		this.length = length;

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
		}
		pivot.x=size.x/2;
		pivot.y=size.y/2;
		this.room=room;
		room.walls.add(this);
		g = new Graphics();

	}

	public void render(Camera camera) {
		//if(true) return;
		//if(!room.visited) return;
		Vector2f screenPos = getScreenPos(camera);
		// _ means scaled, ok?
		float _padding =padding * camera.getZoom();
		float _height = height * camera.getZoom();
		float _length = length * camera.getZoom();
		

		
		switch (type) {
			case 1:
				g.setColor(Color.decode("#45495F"));
				g.fillRect( screenPos.x,  (screenPos.y - _height), _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height), _length, _padding);
	
				break;
			case 2:
				float t;
				if(position.y-room.position.y==0)
					t=_padding;
				else
					t=0;
				g.setColor(Color.decode("#45495F"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height-t), _padding, _length + _padding + _height+t);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( (screenPos.x),  (screenPos.y - _height-t), _padding, _length + _padding+t);
				break;
		}
		//super.render(camera);
	}

}
