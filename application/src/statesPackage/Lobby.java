package statesPackage;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import mainPackage.OptionsList;
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

import com.google.gson.Gson;

import controlPackage.LevelManager;


/**
 * Stage precedent to the actual level stage. It's where the matchmaking will be done (player selection, game type, difficulty, map, etc..)
 * As of now, it provides means to change low level options of the level generation of interest of the developers.
 * @author watacoso
 *
 */

public class Lobby extends BasicGameState {

	public  OptionsList ol;
	private int ID=Window.STATE_LOBBY;
	private StateBasedGame game;
	private TrueTypeFont font;
	GameLevel gameLevel;
	
	/**
	 * Calls the default BasicGameState constructors, but also links the Lobby instance with the GameLevel instance)
	 * @param g
	 */
	
	public Lobby(GameLevel g){
		super();
		gameLevel=g;
	}
	
	/**
	 * Loads an OptionList instance with the level settings (throug importFromOriginal()). It also initializes the Font instance.
	 */
	public void init(GameContainer arg0, StateBasedGame game)
			throws SlickException {
		
		
		font=new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 20), false);
		ol=new OptionsList();
		
		importFromOriginal();
		
		this.game=game;
		// TODO Auto-generated method stub

	}
	
	/**
	 * Builds the list of options. It's called on initialization and on settings reset.
	 * 
	 * @TODO write the hardcoded max and min values on the gameSettings json file.
	 */
	
	private void importFromOriginal(){
		ol.clear();
		
		ol.add("Play");
		ol.add("nPlayers",LevelManager.gameSettings.nPlayers,4,1);
		ol.add("nCompounds",LevelManager.gameSettings.nCompounds,100,1);
		ol.add("maxNEnemies",LevelManager.gameSettings.maxNEnemies,100,0);
		ol.add("minSpawnRate",LevelManager.gameSettings.minSpawnRate,10000,100);
		ol.add("maxSpawnRate",LevelManager.gameSettings.maxSpawnRate,1000000,100);
		ol.add("spawnRateIncrementer",LevelManager.gameSettings.spawnRateIncrementer,1000,0);
		ol.add("incrementRate",LevelManager.gameSettings.incrementRate,100,1);
		
		ol.add("Save");
		ol.add("Reset");
		ol.add("Back",Window.STATE_MENU);
		
	}

	
	/**
	 * Saves the new settings on the static GameSettings object contained on LevelManager. it's a temporary saving method,
	 * since it doesn't change the content of the json file. resetting is enough to return to the default settings. it's called when
	 * accessing the level without saving the new settings.
	 */
	
	private void saveOnCollection(){
		LevelManager.gameSettings.nPlayers=ol.getInt(1);
		LevelManager.gameSettings.nCompounds=ol.getInt(2);
		LevelManager.gameSettings.maxNEnemies=ol.getInt(3);
		LevelManager.gameSettings.minSpawnRate =ol.getInt(4);
		LevelManager.gameSettings.maxSpawnRate =ol.getInt(5);
		LevelManager.gameSettings.spawnRateIncrementer=ol.getInt(6);
		LevelManager.gameSettings.incrementRate=ol.getInt(7);
	}
	
	/**
	 * Saves the settings on a json file. the saving process is definitive.
	 */
	
	private void saveOnJson(){
		Gson gson = new Gson();
		
		OutputStreamWriter writer = null;
		
		try {
			File file= new File("assets/json/gameSettings.json");
			file.createNewFile();
			writer =new OutputStreamWriter(new FileOutputStream(file));
			writer.append(gson.toJson(LevelManager.gameSettings));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Simple rendering function.
	 */
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {

		g.setColor(Color.black);
		g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g.setColor(Color.white);
		
		for(int i=0;i<ol.size();i++){
			font.drawString( 80,Window.HEIGHT/4+i*30, ol.getName(i),ol.getIndex()==i?Color.red: Color.white);
			font.drawString( 350,Window.HEIGHT/4+i*30, ol.getValue(i), ol.getIndex()==i?Color.red: Color.white);
		}

	}

	/**
	 * It manages the key input to move through the options list. The functional options are managed through a switch.
	 */
	
	public void keyReleased(int key, char c){
		switch(key){
		case(Input.KEY_DOWN):
			ol.goDown();
			break;
		case(Input.KEY_UP):
			ol.goUp();
			break;
		case(Input.KEY_LEFT):
			ol.goLeft();
			break;
		case(Input.KEY_RIGHT):
			ol.goRight();
			break;
		case(Input.KEY_ENTER):
			if(ol.isFunctional(ol.getIndex())){
				switch (ol.getName(ol.getIndex())){
				case "Play":
					saveOnCollection();
					gameLevel.start();
					game.enterState(Window.STATE_LEVEL, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
					break;
				case "Reset":
					importFromOriginal();
					break;
				case "Save":
						saveOnCollection();
						saveOnJson();
					break;
				}
			}
			else
				ol.select(game);
			break;
		}
	}
	
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
