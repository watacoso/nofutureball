package com.github.nofutureball.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.github.nofutureball.states.*;



/**
 * Here is set window size, fps, fullscreen status
 * @todo make game resolution and fullscreen  configurable from a settings menu
 * @todo auto configuration on TV (for OUYA Deployment)
 * @author watacoso
 *
 */

public class Window extends StateBasedGame{

	//private static GameContainer gameContainer;

	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	public final static int FPS = 60;
	private static AppGameContainer appgc;
	
	public Window() {
		super("Oppression");
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {

		try {
			appgc = new AppGameContainer(new Window());
			
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(FPS);
			appgc.setVSync(true);
			appgc.setShowFPS(false);
			appgc.setMinimumLogicUpdateInterval(16);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}
	
	public static void quit(){
		appgc.exit();
	}

	/**
	 * Some states directly get the GameLevel instance, so that they can use it's personal methods;
	 */
	
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
		GameLevel gameLevel=new GameLevel();
		
		addState(new Menu(gameLevel));
		addState(gameLevel);
		addState(new Pause(gameLevel));
		addState(new Settings());
		addState(new GameOver(gameLevel));
	}
}

