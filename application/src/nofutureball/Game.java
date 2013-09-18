package nofutureball;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	public Container gameContainer = null;
	public Container mapContainer = null;
	public Container entities = null;
	public Container ui = null;
	public Container players = null;
	public LevelGen levelGen;
	
	public LevelManager lManager;
	
	public Game(String gamename) {
		super(gamename);
		gameContainer = new Container();
		entities = new Container();
		mapContainer = new Container();
		players = new Container();
		ui = new Container();
		
		gameContainer.add(mapContainer);
		gameContainer.add(entities);	
		gameContainer.add(ui);
		
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_CLAMP, GL11.GL_NEAREST);
		gc.setVSync(true);
		Sprite.init();
		lManager=new LevelManager(this);
		AnimationSource.init();
		NoFutureBall.setGameContainer(gc);
		
		lManager.initLevel();
		SoundManager.load();
		SoundManager.mixedSound("Pickup");
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gameContainer.update(this);
		lManager.update();
		Debugging.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//g.setAntiAlias(false);
		g.setBackground(Color.decode("#0C060E"));
		entities.sort();
		gameContainer.render(lManager.cam);		
	}
	
	


}
