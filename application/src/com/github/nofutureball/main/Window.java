package com.github.nofutureball.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import json.GeneralSettings;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

<<<<<<< HEAD:application/src/mainPackage/Window.java
import statesPackage.GameLevel;
import statesPackage.GameOver;
import statesPackage.Lobby;
import statesPackage.Menu;
import statesPackage.Pause;
import statesPackage.Settings;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
=======
import com.github.nofutureball.states.*;
>>>>>>> e3edaf5a433642cdc9dc036ebdd8a3569314761f:application/src/com/github/nofutureball/main/Window.java



/**
 * Here is set window size, fps, fullscreen status
 * @todo make game resolution and fullscreen  configurable from a settings menu
 * @todo auto configuration on TV (for OUYA Deployment)
 * @author watacoso
 *
 */

public class Window extends StateBasedGame{

	
	public final static int STATE_INTRO=0;
	public final static int STATE_MENU=1;
	public final static int STATE_LOBBY=2;
	public final static int STATE_LEVEL=3;
	public final static int STATE_PAUSE=4;
	public final static int STATE_SETTINGS=5;
	public final static int STATE_GAMEOVER=6;
	public final static int STATE_CREDITS=7;
	
	public static int WIDTH;
	public static int HEIGHT;
	public final static int FPS = 60;
	private static AppGameContainer appgc;
	
	

	
	
	public Window() {
		super("Oppression");
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {

		
			
			
			JsonReader reader = null;
			try {
				reader = new JsonReader(new InputStreamReader(new FileInputStream(
						"assets/json/generalSettings.json")));
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Gson gson = new Gson();

			
			try {
				appgc = new AppGameContainer(new Window());
				loadSettings((GeneralSettings) gson.fromJson(reader, GeneralSettings.class));
				appgc.setTargetFrameRate(FPS);
				appgc.setShowFPS(false);
				appgc.setMinimumLogicUpdateInterval(16);
				appgc.start();
			} 
			catch (SlickException ex) {
				Logger.getLogger(Window.class.getName()).log(Level.SEVERE,null, ex);
			}
			
			

	}
	
	public static void loadSettings(GeneralSettings gs) throws SlickException{
		WIDTH=gs.width;
		HEIGHT=gs.height;
		appgc.setDisplayMode(gs.width,gs.height, gs.fullScreen);
		appgc.setVSync(gs.vSync);
		Settings.generalSettings=gs;
		
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
		addState(new Lobby(gameLevel));
		addState(gameLevel);
		addState(new Pause(gameLevel));
		addState(new Settings());
		addState(new GameOver(gameLevel));
	}
	
	
	
}

