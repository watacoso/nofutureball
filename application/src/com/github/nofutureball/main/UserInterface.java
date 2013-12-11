package com.github.nofutureball.main;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import com.github.nofutureball.control.LevelManager;
import com.github.nofutureball.entity.Entity;
import com.github.nofutureball.entity.player.Player;
import com.github.nofutureball.states.GameLevel;


/**
 * Class UserInterface
 * Adds the players to the user-interface container
 * @author watacoso
 */

public class UserInterface {

	//public static void init(Game game){
	//	game.ui.add(new PlayerProfile((Player) LevelManager.players.get(0),10,10));
	//	if(LevelManager.players.size()>=2)
	//	game.ui.add(new PlayerProfile((Player) LevelManager.players.get(1),Window.WIDTH-260,10));
	//}
	
    /**
     * Adds PlayerProfiles to the UserInterface Container
     * @param index Number of players
     */
	public static void addProfile(int index){
		switch (index){
		case 0:
			GameLevel.ui.add(new PlayerProfile(LevelManager.players.get(0),10,10));
			break;
		case 1:
			GameLevel.ui.add(new PlayerProfile(LevelManager.players.get(1),Window.WIDTH-110,10));
			break;
		case 2:
			GameLevel.ui.add(new PlayerProfile(LevelManager.players.get(2),10,Window.HEIGHT-140));
			break;
		case 3:
			GameLevel.ui.add(new PlayerProfile(LevelManager.players.get(3),Window.WIDTH-260,Window.HEIGHT-140));
			break;
		}
	}
	
	//public void update(Game game){
	//	
	//}
	
}

/**
 * class PlayerProfile
 * Handles HeadUp Display of each Player
 * 
 * @author watacoso
 */
class PlayerProfile extends Entity{
	private Graphics g;
	private Player p;
	private TrueTypeFont font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 14), false);
	
	/**
	 * Constructor Player Profile
	 * @param p Player this profile is assigned to
	 * @param x X-Position
	 * @param y Y-Position
	 */
	public PlayerProfile(Player p,int x,int y){
		super(x,y,100,25,0,0);
		this.p=p;
		g=new Graphics();
		
	}
	
	/**
	 * Slick render-function
	 * Renders health bar and cooldown bar for designated player
	 */
	public void render(Camera cam){
		
		
		if(p.dead){
			g.setColor(Color.decode("#1A1E20"));
			g.fillRect( position.x,position.y,box.getSize().x,box.getSize().y);
			g.setColor(Color.decode("#0B0C0F"));
			g.fillRect( position.x+10,position.y+5,80,5);
			g.fillRect( position.x+10,position.y+15,80,5);
			
			if(p.cooldown-LevelManager.getTime()+p.deathTime>0){
				Integer s=(p.cooldown*1000-LevelManager.getTime()+p.deathTime)/1000;
				font.drawString( position.x+45,position.y+5, s.toString(), Color.white);
			}
			
		}
		else{
			g.setColor(Color.decode("#3A3E50"));
			g.fillRect( position.x,position.y,box.getSize().x,box.getSize().y);
			g.setColor(Color.decode("#0B0C0F"));
			g.fillRect( position.x+10,position.y+5,80,5);
			g.fillRect( position.x+10,position.y+15,80,5);
			g.setColor(Color.decode("#32763D"));
			g.fillRect( position.x+10,position.y+5,(p.health/p.maxHealth())*80,5);
			g.setColor(Color.decode("#C8782D"));
			g.fillRect( position.x+10,position.y+15,80,5);
		}
	}	
}



