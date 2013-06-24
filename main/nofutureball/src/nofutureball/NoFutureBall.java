package nofutureball;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class NoFutureBall extends BasicGame {



	private static GameContainer gameContainer;
	
	public ArrayList<Entity> entities;
	
	private Player p1;
	
	public NoFutureBall(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		
		gameContainer=gc;
		entities=new ArrayList<Entity>();
		p1=new Player(200,200);
		entities.add(p1);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		p1.update();
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Howdy!", 100, 100);
		p1.render();
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