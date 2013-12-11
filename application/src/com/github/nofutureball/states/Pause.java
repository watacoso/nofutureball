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

import com.github.nofutureball.main.OptionsList;
import com.github.nofutureball.main.Window;

public class Pause extends BasicGameState {

	private int ID=Window.STATE_PAUSE;
	
	private OptionsList ol;
	private StateBasedGame game;
	private TrueTypeFont font;
	
	GameLevel gameLevel;
	
	public Pause(GameLevel g){
		super();
		gameLevel=g;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame game)
			throws SlickException {
		font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 30), false);
		ol=new OptionsList();
		ol.add("Resume");
		ol.add("Restart");
		ol.add("Menu");
		ol.add("Quit");
		this.game=game;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
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
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	}
	
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
				case "Resume":
					game.enterState(Window.STATE_LEVEL);
					break;
				case "Restart":
					gameLevel.restart();
					game.enterState(Window.STATE_LEVEL, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
					break;
				case "Menu":
					gameLevel.clear();
					game.enterState(Window.STATE_MENU);
					break;
				case "Quit":
					Window.quit();
					break;
				}
			}
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
