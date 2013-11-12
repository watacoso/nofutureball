package oppression;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	public static Container gameContainer;
	public static Container mapContainer;
	public static Container entities;
	public static Container ui;
	public static Container menu;
	public static Status status=Status.LOADING;

	
	public LevelManager lManager;
	
	public Game(String gamename) {
		super(gamename);
		gameContainer = new Container();
		entities = new Container();
		mapContainer = new Container();
		ui = new Container();
		
		gameContainer.add(mapContainer);
		gameContainer.add(entities);	
		gameContainer.add(ui);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_CLAMP, GL11.GL_NEAREST);
		gc.setVSync(true);
		gc.setShowFPS(false);
		Sprite.init();
		Window.setGameContainer(gc);
		SoundManager.load();
		SoundManager.mixedSound("Pickup");
		
		start();
		//UserInterface.init(this);
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
		lManager=new LevelManager();
		lManager.initLevel();
		status=Status.LEVEL;
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		Input input = Window.getGameContainer().getInput();
		if(input.isKeyDown(Input.KEY_ESCAPE))
			gc.exit();
		
		switch (status){
		case LOADING:
			
			break;
		case MENU:
			
			break;
		case LEVEL:
			gameContainer.update(this);
			lManager.update(i);
			Debugging.update();
			break;
		case PAUSE:
	
			break;
		case GAMEOVER:
			gameContainer.update(this);
			lManager.update(i);
			Debugging.update();
			
			if(input.isKeyDown(Input.KEY_R))
				restart();
			break;
		case CREDITS:
			
			break;
		}

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setBackground(Color.decode("#0C060E"));
		entities.sort();
		gameContainer.render(LevelManager.cam);		
	}
	
	
	public static enum Status {
		LOADING(), LEVEL(), PAUSE(),GAMEOVER(),MENU(),CREDITS();
	}

}
