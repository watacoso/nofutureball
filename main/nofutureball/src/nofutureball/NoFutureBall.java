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
	
	public final static int WIDTH = 640;
	public final static int HEIGHT = 480;
	public final static int FPS = 60;
	
	public Container entitiesContainer;
	
	private Player p1;
	private Room r1;
	private Camera cam;
	
	public NoFutureBall(String gamename) {
		super(gamename);
		
	}

	@Override
	public void init(GameContainer gc) throws SlickException {


		AnimationSource.init();
		gameContainer = gc;
		entitiesContainer=new Container();
		p1 = new Player(200, 200);
		r1 = new Room(100, 100, 400, 300);
		entitiesContainer.add(r1);
		entitiesContainer.add(p1);
		cam = new Camera(entitiesContainer, p1);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		entitiesContainer.update();
		cam.update();
		Debugging.update();
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		entitiesContainer.render();
	}

	public static void main(String[] args){

		System.out.println(Player.STATS.get("Maxhealth"));
		 
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new NoFutureBall("Simple Slick Game"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(FPS);
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