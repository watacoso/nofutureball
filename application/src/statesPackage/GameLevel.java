package statesPackage;

import mainPackage.Debugging;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controlPackage.LevelManager;
import entityPackage.Container;

public class GameLevel extends BasicGameState {

	public static Container gameContainer;
	public static Container mapContainer;
	public static Container entities;
	public static Container ui;
	private LevelManager levelManager;
	
	private int ID=2;
	private StateBasedGame game;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame game)
			throws SlickException {
		gameContainer = new Container();
		entities = new Container();
		mapContainer = new Container();
		ui = new Container();
		
		gameContainer.add(mapContainer);
		gameContainer.add(entities);	
		gameContainer.add(ui);
		
		this.game=game;
		
	}
	
	public void clear(){
		entities.list.clear();
		mapContainer.list.clear();
		ui.list.clear();
	}
	
	public void restart(){
		clear();
		start();
	}
	
	public void start(){
		levelManager=new LevelManager(game);
		levelManager.initLevel();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setBackground(Color.decode("#0C060E"));
		entities.sort();
		gameContainer.render(LevelManager.cam);		

		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException {
		gameContainer.update(gc);
		levelManager.update(i);
		Debugging.update();
		// TODO Auto-generated method stub

	}
	
	public void keyReleased(int key, char c){
		switch(key){
		case(Input.KEY_ESCAPE):
			game.enterState(3);	
		break;
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
