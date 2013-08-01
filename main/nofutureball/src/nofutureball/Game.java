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
	
	public Level level;
	

	private Camera cam = null;
	
	
	public Game(String gamename) {
		super(gamename);
		gameContainer = new Container();
		entities = new Container();
		mapContainer = new Container();
		ui = new Container();
		
		gameContainer.add(mapContainer);
		gameContainer.add(entities);	
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {


		level=new Level(entities,mapContainer);
		level.generateMap();
		cam = new Camera(gameContainer);

		AnimationSource.init();
		NoFutureBall.setGameContainer(gc);
		ArrayList<Player> players = new ArrayList<Player>();
		//players.add(new Player(level.getStartRoom(),100, 40, KeySet.ONE));
		Player p=new Player(level.getStartRoom(), 200, 40, KeySet.TWO);
		Enemy e=new Enemy(level.getStartRoom(), 200, 200,p);
		players.add(p);
		entities.add(e);
		//players.add(new Player(level.getStartRoom(), 100, 100, KeySet.THREE));
		//players.add(new Player(level.getStartRoom(), 300, 100, KeySet.FOUR));
		startNewGame(players);
		SoundManager.load();
		SoundManager.mixedSound("Pickup");
	}

	public void startNewGame(ArrayList<Player> players) {
		for (Player player : players) {
			entities.add(player);
			cam.addTarget(player);
		}
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		gameContainer.update();
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
