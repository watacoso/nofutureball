package nofutureball;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class NoFutureBall extends BasicGame {



	private static GameContainer gameContainer;
	
	public Container entitiesContainer;
	
	private Player p1;
	private Room r1;
	private Camera cam;
	
	public NoFutureBall(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		
		gameContainer=gc;
		entitiesContainer=new Container();
		p1 = new Player(200, 200);
		r1 = new Room(100, 100, 400, 300);
		entitiesContainer.add(r1);
		entitiesContainer.add(p1);
		cam = new Camera(entitiesContainer,p1.position.x-320,p1.position.y-240);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//p1.update();
		//r1.update();
		entitiesContainer.update();
		cam.setPosition(p1.position.x - 320, p1.position.y - 240);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Howdy!", 100, 100);
		//p1.render();
		//r1.render();
		entitiesContainer.render();
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new NoFutureBall("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(NoFutureBall.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	public static GameContainer getGameContainer() {
		return gameContainer;
	}

	public static void setGameContainer(GameContainer gc) {
		gameContainer = gc;
	}
}