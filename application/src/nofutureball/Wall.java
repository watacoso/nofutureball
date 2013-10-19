package nofutureball;

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
	//private Image wallTile;
	public float length;
	
	public Room room;


	public Wall(Room room,float x, float y, int type, float length) { // 1:horizzontalSection
															// 2:vertical
															// (3:horizzontalFull)
		super(x, y-Room.wallSpessor);
		this.type = type;
		setLength(length);
		g = new Graphics();
		if(type==1){
			//wallTile=Sprite.getSprite("", "");
			decorations=new ArrayList<Sprite>();
			
		}
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
	};
	
	public boolean addNewDecoration(String owner, String name,float x,float height){
		if(type!=1) return false;
		Sprite sprite=new Sprite(0,0);
		sprite.dynamicStats=true;
		sprite.setAnimation(owner,name);
		if(sprite.size.y>Wall.height || sprite.size.x>size.x)
			return false;
		sprite.position.x=position.x+x;
		sprite.position.y=position.y+Room.wallSpessor-sprite.spriteSize.y-height;
		if(sprite.size.y+height>Wall.height) sprite.spriteSize.y=Wall.height-height;
		if(sprite.position.x <position.x+decoPadding ) 
		sprite.position.x=position.x+decoPadding;
		if(sprite.position.x+sprite.spriteSize.x >position.x+size.x-decoPadding)
		sprite.position.x=position.x+size.x-decoPadding-sprite.spriteSize.x;
		for(int i=0;i<decorations.size();i++){
			Sprite p=decorations.get(i);
			if(sprite.position.x<p.position.x+p.spriteSize.x+decoPadding && sprite.position.x+sprite.spriteSize.x>p.position.x-decoPadding)
				return false;
		}
		decorations.add(sprite);
		return true;
	}

	public void render(Camera camera) {
		//if(!room.visited) return;
		Vector2f screenPos = getScreenPos(camera);


		float _padding =padding * camera.getZoom();
		float _height =  height * camera.getZoom();
		float _length =  length * camera.getZoom();
		//float _tileWidth=256*camera.getZoom();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		switch (type) {
			case 1:
				//if(true)return;
				g.setColor(Color.decode(room.baseColor));
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _padding);
				//super.render(camera);
				
				for(int i=0;i<decorations.size();i++)
					decorations.get(i).render(camera);
				
				/*float lastX=0,i;
					for ( i=0; i<length/Room.tileWidth-1; lastX += _tileWidth,i++) {
						wallTile.draw(screenPos.x + lastX, screenPos.y-_tileWidth+_padding, _tileWidth, _tileWidth);
					}
					
				wallTile.getSubImage(0, 0, (int)(length-256*i), 256).draw(screenPos.x + lastX, screenPos.y-_tileWidth+_padding, _length-lastX, _tileWidth);
				//wallTile.draw(screenPos.x + lastX, screenPos.y-_tileWidth+_padding, _tileWidth, _tileWidth,0,0,_tileWidth,_tileWidth);*/
				break;
			case 2:
				//if(true)return;
				g.setColor(Color.decode("#43457A"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _padding, _length + _padding  + _height);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x, screenPos.y - _height, _padding, _length + _padding );
				//wallTile.draw(screenPos.x, screenPos.y-_tileWidth+_padding+_length,_tileWidth/4,_tileWidth);
				//super.render(camera);
				
				break;
			case 3:
				//if(true)return;
				g.setColor(Color.decode("#0C060E")); //#0C060E #45495F
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _height+_padding);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _length, _padding);
				
				//super.render(camera);
				
				break;
			case 4:
				//if(true)return;
				g.setColor(Color.decode("#0C060E")); //#0C060E #45495F
				g.fillRect( screenPos.x,  screenPos.y - _height, _padding, _length + _padding  + _height);
				g.setColor(Color.decode("#9AA0B6"));
				g.fillRect( screenPos.x,  screenPos.y - _height, _padding, _length + _padding );
				//super.render(camera);
				//wallTile.draw(screenPos.x, screenPos.y-_tileWidth+_length,64*camera.getZoom(), _tileWidth);
				
				break;
		}
		
		
		
		
		
		
		
	}

}
