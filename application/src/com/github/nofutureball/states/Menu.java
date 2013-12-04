package com.github.nofutureball.states;

import java.awt.Font;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.github.nofutureball.control.SoundManager;
import com.github.nofutureball.entity.Sprite;
import com.github.nofutureball.main.Window;


/**
 * Menu class
 * @todo instead of an int-ID we should use an ENUM so you can tell what ID is what
 * @author watacoso
 *
 */
public class Menu extends BasicGameState{

	private int ID=1;
	private int nOps=3;
	private int opID=0;
	private StateBasedGame game;
	private TrueTypeFont font;
	GameLevel gameLevel;
	
	/**
	 * Constructor of Menu
	 * @param g GameLevel
	 */
	public Menu(GameLevel g){
		super();
		gameLevel=g;
	}
	
	@Override
	/**
	 * Initialises the menu
	 */
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		Sprite.init();
		SoundManager.load();
		font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 30), false);
		this.game=game;
	}

	@Override
	/**
	 * Slick render-function
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g.setColor(Color.white);

		font.drawString( 80,Window.HEIGHT/2,(opID==0?"-":"")+ "Play", Color.white);
		font.drawString( 80,Window.HEIGHT/2+30,(opID==1?"-":"")+ "Settings", Color.white);
		font.drawString( 80,Window.HEIGHT/2+60,(opID==2?"-":"")+ "Quit", Color.white);
	}

	@Override
	/**
	 * Slick update-function
	 */
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	}
	
	/**
	 * Handles Keyboard Input
	 * Moves selector
	 */
	public void keyReleased(int key, char c){
		switch(key){
		case(Input.KEY_DOWN):
			if(opID<nOps-1)
				opID++;
			break;
		case(Input.KEY_UP):
			if(opID>0)
				opID--;
			break;
		case(Input.KEY_ENTER):
			executeOption(opID);
		break;
		}
	}
	
	/**
	 * Executes the selected Option
	 * @param opID
	 */
	private void executeOption(int opID){
		switch(opID){
		case 0:
			gameLevel.start();
			game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			break;
		case 1:
			break;
		case 2:
			Window.quit();
			break;
		}	
		opID=0;
	}

	@Override
	public int getID() {
		return ID;
	}

}
