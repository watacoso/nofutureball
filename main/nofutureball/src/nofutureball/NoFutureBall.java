package nofutureball;

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

	private Graphics ball;
	private int[]ballPosition;
	
	public NoFutureBall(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ball=new Graphics();
		ball.setColor(Color.red);
		ballPosition=new int[2];
		ballPosition[0]=200;
		ballPosition[1]=200;
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		
		Input input=gc.getInput();
		
		if (input.isKeyDown(Input.KEY_UP)){
			ballPosition[1]--;
        }
        else if (input.isKeyDown(Input.KEY_DOWN)){
        	ballPosition[1]++;
        }
        else if (input.isKeyDown(Input.KEY_LEFT)){
        	ballPosition[0]--;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)){
        	ballPosition[0]++;
        }
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		
		ball.fillOval(ballPosition[0], ballPosition[1], 80, 80);
		g.drawString("Howdy!", 100, 100);
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
}