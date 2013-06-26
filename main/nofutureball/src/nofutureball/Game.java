package nofutureball;

import java.util.ArrayList;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	public Game(String gamename) {
		super(gamename);
		entities = new Container();
		entities.add(r1);
		cam = new Camera(entities);
		
	}

	public Container entities = null;

	
	private Room r1 = new Room(200, 200, 200, 200);
	private Camera cam = null;

	@Override
	public void init(GameContainer gc) throws SlickException {

		AnimationSource.init();
		NoFutureBall.setGameContainer(gc);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(0, 0, KeySet.ONE));
		players.add(new Player(100, 0, KeySet.TWO));
		players.add(new Player(100, 100, KeySet.THREE));
		players.add(new Player(0, 100, KeySet.FOUR));
		startNewGame(players);
	}
	public void startNewGame(ArrayList<Player> players)
	{
		for (Player player : players) {
			entities.add(player);
			cam.addTarget(player);
		}
	}
	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		entities.update();
		cam.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		entities.render();
	}

}
