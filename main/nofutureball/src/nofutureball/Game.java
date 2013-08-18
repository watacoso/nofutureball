package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	public Container gameContainer = null;
	public Container mapContainer = null;
	public Container entities = null;
	public Container ui = null;
	public Container players = null;
	public Level level;
	

	private Camera cam = null;
	
	
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

		ObjectAnimationList.init();
		level=new Level(entities,mapContainer);
		level.generateMap();
		cam = new Camera(gameContainer);

		AnimationSource.init();
		NoFutureBall.setGameContainer(gc);
		

		Player p1 = new Sharpshooter(level.getStartRoom(),100, 40, KeySet.ONE);
		players.add(p1);
		
		//Enemy e = new Enemy(level.getStartRoom(), 200, 200,p1);
		//Enemy e2 = new Enemy(level.getStartRoom(), 220, 230,p1);
		//Enemy e3 = new Enemy(level.getStartRoom(), 230, 220,p1);
		//Enemy e4 = new Enemy(level.getStartRoom(), 200, 240,p1);
		//Enemy e5 = new Enemy(level.getStartRoom(), 200, 250,p1);
		
		//entities.add(e);
		//entities.add(e2);
		//entities.add(e3);
		//entities.add(e4);
		//entities.add(e5);
		startNewGame(players);
		SoundManager.load();
		SoundManager.mixedSound("Pickup");
	}

	public void startNewGame(Container players) {
		for (int i=0;i<players.size();i++) {
			entities.add(players.get(i));
			cam.addTarget(players.get(i));
		}
	}
	

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gameContainer.update(this);
		cam.update();
		Debugging.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		entities.sort();
		gameContainer.render(cam);
		cam.update();
	}
	
	


}
