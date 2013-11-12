package oppression;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Wall extends Entity {

	Graphics g;
	private int type;

	final int padding = Room.wallSpessor;
	static final float height = 200;
	static final float lowHeight=10;
	private ArrayList<Sprite> decorations;
	static float decoPadding=70;
	public float length;
	
	public Room room;


	public Wall(Room room,float x, float y, int type, float length) { 
		super(x, y-Room.wallSpessor,0,0);
		this.type = type;
		setLength(length);
		g = new Graphics();
		if(type==1){
			decorations=new ArrayList<Sprite>();			
		}
	}
	
	public void setLength(float l){
		this.length = l;

		switch (type) {
			case 1:
				box.setSize(length,padding);
				break;
			case 2:
				position.y+=padding;
				box.setSize(padding,length+padding);
				break;
			case 3:
				box.setSize(length,padding);
				break;
			case 4:
				position.y+=padding;
				box.setSize(padding,length+padding);
				break;
		}
		box.setPosition(position.x, position.y);
		
	}
	
	public void addWallToRoom(Room room){
		this.room=room;
		room.walls.add(this);
	}
	
	public int getType(){
		return type;
	};
	
	public boolean addNewDecoration(String owner, String name,float x,float height){
		if(type!=1) return false;
		Sprite sprite=new Sprite(0,0);
		sprite.dynamicStats=true;
		sprite.setAnimation(owner,name);
		if(sprite.spriteBox.getSize().y>Wall.height || sprite.spriteBox.getSize().x>box.getSize().x)
			return false;
		sprite.position.x=position.x+x;
		sprite.position.y=position.y+Room.wallSpessor-sprite.spriteSize.y-height;
		if(sprite.spriteBox.getSize().y+height>Wall.height) sprite.spriteSize.y=Wall.height-height;
		if(sprite.position.x <position.x+decoPadding ) 
		sprite.position.x=position.x+decoPadding;
		if(sprite.position.x+sprite.spriteSize.x >position.x+box.getSize().x-decoPadding)
		sprite.position.x=position.x+box.getSize().x-decoPadding-sprite.spriteSize.x;
		for(int i=0;i<decorations.size();i++){
			Sprite p=decorations.get(i);
			if(sprite.position.x<p.position.x+p.spriteSize.x+decoPadding && sprite.position.x+sprite.spriteSize.x>p.position.x-decoPadding)
				return false;
		}
		decorations.add(sprite);
		return true;
	}

	public void render(Camera camera) {
		Vector2f screenPos = getScreenPos(camera);

		float _padding =padding * camera.getZoom();
		float _height =  height * camera.getZoom();
		float _length =  length * camera.getZoom();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		switch (type) {
			case 1:
				g.setColor(Color.decode(room.baseColor));
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _padding);
				for(int i=0;i<decorations.size();i++)
					decorations.get(i).render(camera);
				//super.render(camera);
				break;
			case 2:
				g.setColor(Color.decode("#43457A"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _padding, _length + _padding  + _height);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x, screenPos.y - _height, _padding, _length + _padding );
				//super.render(camera);
				break;
			case 3:
				g.setColor(Color.decode("#0C060E")); 
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _padding);
				//super.render(camera);
				break;
			case 4:
				g.setColor(Color.decode("#0C060E")); //#0C060E #45495F
				g.fillRect( screenPos.x,  screenPos.y - _height, _padding, _length + _padding  + _height);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _padding, _length + _padding );
				//super.render(camera);
				break;
		}	
	}
}
