package mainPackage;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * Here is set window size, fps, fullscreen status
 * @todo make game resolution and fullscreen  configurable from a settings menu
 * @todo auto configuration on TV (for OUYA Deployment)
 * @author watacoso
 *
 */

public class Window {

	private static GameContainer gameContainer;

	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;

	public final static int FPS = 60;
	
	/**
	 * Main program
	 * @param args Arguments are not used
	 */
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Simple Slick Game"));
			
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(FPS);
			// appgc.setMinimumLogicUpdateInterval(16);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Window.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
	
	/**
	 * Returns the GameContainer
	 * @return the active Game Container
	 */
	public static GameContainer getGameContainer() {
		return gameContainer;
	}
	
	/**
	 * Sets the GameContainer
	 * @param gc The new GameContainer
	 */
	public static void setGameContainer(GameContainer gc) {
		gameContainer = gc;
	}
}