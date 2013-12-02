package mapElements;

import org.newdawn.slick.GameContainer;

import playerPackage.Player;
import controlPackage.LevelManager;
import entityPackage.GameObject;

public class Panel extends GameObject {

	public Panel(Room room, float x,String type) {
		super(room, x, -40, 48, 70, false);
		setCollision(false);
		setAnimation("PANEL","TEMP");
		// TODO Auto-generated constructor stub
	}
	
	public void update(GameContainer game){
		super.update(game);
		
		for(int i=0;i<LevelManager.players.size();i++){
			Player p=(Player) LevelManager.players.get(i);
			if(getDistance(p)<30 && p.room==room){
				System.out.println("TEST");
			}
		}
	}
}
