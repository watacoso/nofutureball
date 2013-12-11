package statesPackage;

import java.awt.Font;

import mainPackage.Window;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameOver extends BasicGameState {

	private int ID=Window.STATE_GAMEOVER;
	private int nOps=3;
	private int opID=0;
	private StateBasedGame game;
	private TrueTypeFont font;
	GameLevel gameLevel;
	
	public GameOver(GameLevel g){
		super();
		gameLevel=g;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame game)
			throws SlickException {
		font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 30), false);
		this.game=game;
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g.setColor(Color.white);

		font.drawString( 80,Window.HEIGHT/2,(opID==0?"-":"")+ "Restart", Color.white);
		font.drawString( 80,Window.HEIGHT/2+30,(opID==1?"-":"")+ "Menu", Color.white);
		font.drawString( 80,Window.HEIGHT/2+60,(opID==2?"-":"")+ "Quit", Color.white);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int key, char c){
		switch(key){
		case(Input.KEY_DOWN):
			if(opID<nOps-1)
				opID++;
			break;
		case(Input.KEY_UP):
			if(opID>0)
				opID--;
			break;
		case(Input.KEY_ENTER):
			executeOption(opID);
		break;
		}
	}
	
	private void executeOption(int opID){
		switch(opID){
		case 0:
			gameLevel.restart();
			game.enterState(Window.STATE_LEVEL, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
			break;
		case 1:
			gameLevel.clear();
			game.enterState(Window.STATE_MENU);
			break;
		case 2:
			Window.quit();
			break;
	}	
		opID=0;
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
