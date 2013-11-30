package enemyPackage;

import controlPackage.LevelManager;
import entityPackage.NPC;
import playerPackage.Player;
import mainPackage.Game;
import mapElements.Room;

public class Enemy extends NPC {


	
	public Enemy(Room room, float x, float y) {
		super(room, x, y);
		target=getNewTarget();
		// TODO Auto-generated constructor stub
	}

	
	private Player getNewTarget(){
		float k=-1;
		Player p=null;
		for(int i=0;i<LevelManager.players.size();i++){
			if(LevelManager.players.get(i).dead) continue;
			float j=getDistance(LevelManager.players.get(i));
			if(k==-1 || k>j){
				k=j;
				p=(Player) (LevelManager.players.get(i));
			}
		}
		
		return p;
	}
	
	public void update(Game game){
		
		if(target == null || target.dead)
			target=getNewTarget();
		super.update(game);
		
	}
	
	public void die(){
		LevelManager.nEnemies--;
		super.die();
	}
	
	//ENEMY GENERAL ACTIONS//
}
