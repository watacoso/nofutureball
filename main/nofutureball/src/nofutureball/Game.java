package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	public Game(String gamename) {
		super(gamename);
		gameContainer = new Container();
		entities = new Container();
		mapContainer = new Container();
		ui = new Container();
		
		gameContainer.add(mapContainer);
		gameContainer.add(entities);		
	}

	public Container gameContainer = null;
	public Container mapContainer = null;
	public Container entities = null;
	public Container ui = null;

	private Room r1;
	private Room r2;
	private Room r3;
	private Room r4;
	private Room r5;
	private Camera cam = null;

	@Override
	public void init(GameContainer gc) throws SlickException {

		r1 = new Room(0,-120, 10, 10);
		r2 = new Room(615, 0, 10, 10);
		r3 = new Room(1230, 60, 10, 10);
		r4 = new Room(495, 315, 10, 10);
		r5 = new Room(615, 630, 10, 10);
		
		r1.addWalls(entities);
		r2.addWalls(entities);
		r3.addWalls(entities);
		r4.addWalls(entities);
		r5.addWalls(entities);
		
		mapContainer.add(r2);
		mapContainer.add(r1);
		mapContainer.add(r3);
		mapContainer.add(r4);
		mapContainer.add(r5);
		
		cam = new Camera(gameContainer);

		AnimationSource.init();
		NoFutureBall.setGameContainer(gc);
		ArrayList<Player> players = new ArrayList<Player>();
		//players.add(new Player(r1,100, 40, KeySet.ONE));
		players.add(new Player(r4, 40, 40, KeySet.TWO));
		// players.add(new Player(100, 100, KeySet.THREE));
		// players.add(new Player(0, 100, KeySet.FOUR));
		startNewGame(players);
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
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		entities.sort();
		gameContainer.render();
	}

}
