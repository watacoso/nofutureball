package com.github.nofutureball.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import com.github.nofutureball.entity.Container;
import com.github.nofutureball.entity.enemy.RoboRifler;
import com.github.nofutureball.entity.player.Player;
import com.github.nofutureball.entity.player.Sharpshooter;
import com.github.nofutureball.json.GameSettings;
import com.github.nofutureball.main.Camera;
import com.github.nofutureball.main.KeySet;
import com.github.nofutureball.main.UserInterface;
import com.github.nofutureball.map.Room;
import com.github.nofutureball.states.GameLevel;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;



/**
 * Manages the Levels
 * Number of Players, and so forth.
 * 
 * Takes care of enemy spawning
 * 
 * @author watacoso
 */

public class LevelManager {

	public static GameSettings gameSettings;
	public LevelGen levelGen;
	private  int level;
	private Container entities;
	public static ArrayList<Player> players;
	public static int nEnemies;
	private int spawnNumber=0;
	private static int timer;
	private int spawnTimestamp;
	public static Camera cam;
	private static StateBasedGame game;
	
	
	
	/**
     * Constructor
     * @param the StateBasedGame instance
     */
	public LevelManager(StateBasedGame g){
		game=g;
		this.entities=GameLevel.entities;
		players=new ArrayList<Player>();
		levelGen=new LevelGen(GameLevel.entities,GameLevel.mapContainer);
		cam=new Camera(GameLevel.gameContainer);
		timer=0;
		nEnemies=0;
		spawnTimestamp=0;
	}
	
	/**
	 * static functions called on application startup. it loads the option values from a json file named "gameSettings.json
	 */
	
	public static void loadGameSettings(){
		JsonReader reader = null;
		try {
			reader = new JsonReader(new InputStreamReader(new FileInputStream(
					"assets/json/gameSettings.json")));
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		gameSettings= gson.fromJson(reader, GameSettings.class);
	}
	
	/**
     * Returns current time of the timer
     * @return int timer
     */
    public static int getTime(){
        return timer;
    }
    
    /**
     * Sets Number of Players
     * @param nPlayers number of players wanted
     */
    public void setNumPlayers(){
    	System.out.println(gameSettings.nPlayers);
        players.clear();
        if(gameSettings.nPlayers>=1){
            Player p = new Sharpshooter(levelGen.getStartRoom(),300, 300, KeySet.ONE);
            players.add(p);
            UserInterface.addProfile(0);
        }
        if(gameSettings.nPlayers>=2){
            Player p = new Sharpshooter(levelGen.getStartRoom(),300, 600, KeySet.TWO);
            players.add(p);
            UserInterface.addProfile(1);
        }
        //if(gameSettings.nPlayers>=3){
        //  Player p = new Sharpshooter(levelGen.getRandomRoom(),600, 300, KeySet.THREE);
        //  players.add(p);
        //UserInterface.addProfile(2);
        //}
        //if(gameSettings.nPlayers==4){
        //  Player p = new Sharpshooter(levelGen.getRandomRoom(),600, 600, KeySet.FOUR);
        //  players.add(p);
        //UserInterface.addProfile(3);
        //}

    }
    
    public void newGame(){
        
    }
    
    public void nextLevel(){
        
    }
    
    /**
     * Initialises a level
     */
    public void initLevel(){
        levelGen.generateMap(level);
        setNumPlayers();
        for (int i=0;i<players.size();i++) {
            entities.add(players.get(i));
            cam.addTarget(players.get(i));
        }
        
    }
    
    public void pause(boolean value){
        
    }
    
    /**
     * Switches into the Game Over State
     */
    public static void gameOver(){
        game.enterState(4 ,new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
    }

    /**
     * Spawns a player
     * @param p Player
     * @param r Room
     * @param x x-Position
     * @param y y-Position
     */
    private void spawnPlayer(Player p,Room r, int x, int y){
        entities.remove(p);
        p.room=r;
        p.position.set(r.position.x+x,r.position.y+y);
        p.dead=false;
        p.health=p.maxHealth();
        entities.add(p);
        cam.addTarget(p);
    }
    
    /**
     * Slick Update function
     * Spawns Enemies
     * Respawns Players
     * @param delta
     */
    public void update(int delta){
        timer+=delta;
        if(getTime()-spawnTimestamp>3000){
            spawnTimestamp=getTime();
            spawnNumber+=5;
            for(int i=0;i<1+Math.ceil(spawnNumber/10);i++){
                //System.out.println(nEnemies);
                if(nEnemies<gameSettings.maxNEnemies){
                    RoboRifler e = new RoboRifler(levelGen.getRandomRoom(), 256, 256);
                    entities.add(e);
                }
            }
            
        }
        
        
        //player respawn
        for(int i=0;i<players.size();i++){
            if(players.get(i).dead){
                if(getTime()>players.get(i).deathTime+players.get(i).cooldown*1000){
                    for(int j=0;j<players.size();j++)
                        if(!players.get(j).dead){
                            spawnPlayer(players.get(i),players.get(j).room,200,200);
                            SoundManager.mixedSound("respawn");
                        }
                }
            }
        }
        
        
       //invisible wall
       // Player.midPoint.set(LevelManager.players.get(0).box.getPosition());
       // if(players.size()>1){
            
       // for(int i=1;i<LevelManager.players.size();i++){
       //     Vector2f t=new Vector2f(LevelManager.players.get(i).box.getPosition().copy().sub(Player.midPoint));
       //     t.scale(0.5f);
       //     Player.midPoint.add(t);
       // }
            //System.out.println(Player.midPoint);
       // }
        cam.update();
    }
}
