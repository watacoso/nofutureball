package com.github.nofutureball.states;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.github.nofutureball.json.GeneralSettings;
import com.github.nofutureball.main.OptionsList;
import com.github.nofutureball.main.Window;
import com.google.gson.Gson;

public class Settings extends BasicGameState {

	public static GeneralSettings generalSettings;
	
	public  OptionsList ol;
	private int ID=Window.STATE_SETTINGS;
	private StateBasedGame game;
	private TrueTypeFont font;
	
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame game)
			throws SlickException {

		font=new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 30), false);
		ol=new OptionsList();
		
		importFromOriginal();
		
		this.game=game;
	}
	
	private void importFromOriginal(){
		ol.clear();
		
		ol.add("Music",generalSettings.music,100);
		ol.add("Sound",generalSettings.sound,100);
		ol.add("Fullscreen",generalSettings.fullScreen);
		ol.add("VSync",generalSettings.vSync);
		
		ol.add("Apply");
		ol.add("Reset");
		ol.add("Back",Window.STATE_MENU);
		
	}
	
	private void exportToOriginal(){
		
		Gson gson = new Gson();
		generalSettings.music=ol.getInt(0);
		generalSettings.sound=ol.getInt(1);
		generalSettings.fullScreen=ol.getBoolean(2);
		generalSettings.vSync=ol.getBoolean(3);
		OutputStreamWriter writer = null;
			
		try {
			File file= new File("assets/json/generalSettings.json");
			file.createNewFile();
			writer =new OutputStreamWriter(new FileOutputStream(file));
			writer.append(gson.toJson(generalSettings));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		g.setColor(Color.black);
		g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
		g.setColor(Color.white);
		
		for(int i=0;i<ol.size();i++){
			font.drawString( 80,Window.HEIGHT/4+i*30, ol.getName(i),ol.getIndex()==i?Color.red: Color.white);
			font.drawString( 300,Window.HEIGHT/4+i*30, ol.getValue(i), ol.getIndex()==i?Color.red: Color.white);
		}
	}
	
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
				case "Reset":
					importFromOriginal();
					break;
				case "Apply":
					exportToOriginal();
					try {
						Window.loadSettings(generalSettings);
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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

class Op{
	
	private Integer value,min,max;
	private Boolean bValue;
	public boolean isB;
	public String name;
	
	public Op(String name, int value, int min, int max){
		this.name=name;
		this.min=min;
		this.max=max;
		this.value=value<min?min:value>max?max:value;
		isB=false;
	}
	
	public Op(String name, int value, int max){
		this.name=name;
		this.min=0;
		this.max=max;
		this.value=value<min?min:value>max?max:value;
		isB=false;
	}
	
	public Op(String name,boolean value){
		this.name=name;
		bValue=value;
		isB=true;
	}
	
	public void setValue(int value){
		if(isB) return;
		this.value=value<min?min:value>max?max:value;
	}
	
	public void add(int value){
		if(isB) return;
		int v=this.value+value;
		this.value=v<min?min:v>max?max:v;
	}
	
	public void setValue(boolean value){
		if(!isB) return;
		bValue=value;
	}
	
	public void switchValue(){
		if(!isB) return;
		bValue=!bValue;
	}
	
	public String getStringValue(){
		if(isB)
			return bValue.toString();
		else
			return value.toString();
	}
	
}
