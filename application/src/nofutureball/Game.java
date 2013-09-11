package nofutureball;

import java.util.ArrayList;

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
		Player p2 = new Sharpshooter(level.roomsPool.get(30),128, 120, KeySet.TWO);
		players.add(p2);
		Player p3 = new Sharpshooter(level.roomsPool.get(50),128, 128, KeySet.THREE);
		players.add(p3);
		
		for(int i=0;i<50;i++){
			Enemy e = new Enemy(level.roomsPool.get(50), 256, 256,p1);
			entities.add(e);
		}
		

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
		
		g.setBackground(Color.decode("#0C060E"));
		entities.sort();
		gameContainer.render(cam);
		cam.update();
	}
	
	


}
