package mainPackage;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import statesPackage.Menu;
import controlPackage.LevelManager;
import controlPackage.SoundManager;
import entityPackage.Container;
import entityPackage.Sprite;
/**
 * slick2D functions, (init, update, render) are called on this class. The class builds the Containers tree that
 * is then passed to the update and render functions.
 * it's also the namespace of all the game attributes of public domain
 * @author watacoso
 *
 */
public class Game extends BasicGame {

    public static Container gameContainer;
    public static Container mapContainer;
    public static Container entities;
    public static Container ui;
    public static Status status=Status.LOADING;
    public Menu menu;
    
	public Game(String title) {
		super(title);
	}
	
	public static LevelManager lManager;

	/**
	 * Slick2d init function. It builds the tree of containers. the order of the containers on the tree dictates their rendering order
	 * Sprite and SoundManager classes are initialized.
	 *
	 */
	
	public void init(GameContainer gc) throws SlickException {
		gc.setVSync(true);
		gc.setShowFPS(false);
		
		gameContainer = new Container();
		entities = new Container();
		mapContainer = new Container();
		ui = new Container();
		
		gameContainer.add(mapContainer);
		gameContainer.add(entities);	
		gameContainer.add(ui);
		
		Sprite.init();
		menu= new Menu();
		Window.setGameContainer(gc);
		SoundManager.load();
		SoundManager.mixedSound("Pickup");
		status=Status.MENU;
	}
	
	
	public static void clear(){
		entities.list.clear();
		mapContainer.list.clear();
		ui.list.clear();
	}
	
	public static void restart(){
		clear();
		start();
	}
	
	public static void start(){
		lManager=new LevelManager();
		lManager.initLevel();
		status=Status.LEVEL;
	}

	/**
	 * Slick2D update function. depending on the status of the game, it executes the update function of other objects in the game.
	 * 
	 * @todo find a better solution to the switch (status) system to manage different stages of the game.
	 */
	public void update(GameContainer gc, int i) throws SlickException {
		Input input = Window.getGameContainer().getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE))
			gc.exit();
		
		switch (status){
		case LOADING:
			menu.update();
			break;
		case MENU:
			menu.update();
			break;
		case LEVEL:
			gameContainer.update(this);
			lManager.update(i);
			Debugging.update();
			break;
		case PAUSE:
			menu.update();
			break;
		case GAMEOVER:
			
			//gameContainer.update(this);
			//lManager.update(i);
			//Debugging.update();
			
			menu.update();

			break;
		case CREDITS:
			menu.update();
			break;
		}
		
		

	}

	/**
	 * Slick2D render function. 
	 */
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setBackground(Color.decode("#0C060E"));
		entities.sort();
		gameContainer.render(LevelManager.cam);		
		menu.render();
	}
	
	
	public static enum Status {
		LOADING(), LEVEL(), PAUSE(),GAMEOVER(),MENU(),CREDITS();
	}

}
