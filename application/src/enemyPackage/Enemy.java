package enemyPackage;

import mapElements.Room;

import org.newdawn.slick.GameContainer;

import playerPackage.Player;
import controlPackage.LevelManager;
import entityPackage.NPC;

/**
 * Enemy class
 * @author watacoso
 *
 */

public class Enemy extends NPC {

    /**
     * Constructor
     * @param room Room
     * @param x x-position
     * @param y y-position
     */
	public Enemy(Room room, float x, float y) {
		super(room, x, y);
		target=getNewTarget();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Makes the Enemy choose a new target
	 * @return The Player target the enemy has chosen
	 */
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
	
	public void update(GameContainer game){
		
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
