package com.github.nofutureball.states;

import java.awt.Font;

<<<<<<< HEAD:application/src/statesPackage/Menu.java
import mainPackage.OptionsList;
import mainPackage.Window;
=======
>>>>>>> e3edaf5a433642cdc9dc036ebdd8a3569314761f:application/src/com/github/nofutureball/states/Menu.java

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

<<<<<<< HEAD:application/src/statesPackage/Menu.java
import controlPackage.LevelManager;
import controlPackage.SoundManager;
import entityPackage.Sprite;
=======
import com.github.nofutureball.control.SoundManager;
import com.github.nofutureball.entity.Sprite;
import com.github.nofutureball.main.Window;

>>>>>>> e3edaf5a433642cdc9dc036ebdd8a3569314761f:application/src/com/github/nofutureball/states/Menu.java

/**
 * Menu class
 * @todo instead of an int-ID we should use an ENUM so you can tell what ID is what
 * @author watacoso
 *
 */
public class Menu extends BasicGameState{

	private int ID=Window.STATE_MENU;
	private OptionsList ol;
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
		LevelManager.loadGameSettings();
		font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 30), false);
		ol=new OptionsList();
		ol.add("Play",Window.STATE_LOBBY);
		ol.add("Settings",Window.STATE_SETTINGS);
		ol.add("Quit");
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
		
		for(int i=0;i<ol.size();i++){
			font.drawString( 80,Window.HEIGHT/4+i*30, ol.getName(i),ol.getIndex()==i?Color.red: Color.white);
			font.drawString( 300,Window.HEIGHT/4+i*30, ol.getValue(i), ol.getIndex()==i?Color.red: Color.white);
		}
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
			ol.goDown();
			break;
		case(Input.KEY_UP):
			ol.goUp();
			break;
		case(Input.KEY_ENTER):
			if(ol.isFunctional(ol.getIndex())){
				switch (ol.getName(ol.getIndex())){
				case "Quit":
					Window.quit();
					break;
				}
			}
			else
				ol.select(game);
			break;
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
