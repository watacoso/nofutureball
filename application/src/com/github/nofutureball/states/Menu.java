package com.github.nofutureball.states;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;

import com.github.nofutureball.main.Game;
import com.github.nofutureball.main.Window;

public class Menu {

	Graphics g;
	TrueTypeFont font;
	//private String[] mainVoices;
	public Menu(){
		 g=new Graphics();
		 font = new TrueTypeFont(new java.awt.Font("Verdana", Font.BOLD, 30), false);
	}
	
	public void update(){
		
		Input input = Window.getGameContainer().getInput();
		
		switch(Game.status){
		case MENU:
			if(input.isKeyDown(Input.KEY_ENTER))
				Game.start();
			break;
		case LOADING:
			break;
		case PAUSE:
			break;
		case GAMEOVER:
			if(input.isKeyDown(Input.KEY_R))
				Game.restart();
			break;
		case CREDITS:
			break;
		default:
			break;
		}
	}
	
	public void render(){
		
		
		
		switch(Game.status){
		case MENU:
			g.setColor(Color.black);
			g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
			font.drawString( 20,Window.HEIGHT/2, "PLAY", Color.white);
			break;
		case LOADING:
			g.setColor(Color.black);
			g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
			font.drawString( 20,Window.HEIGHT/2, "LOADING", Color.white);
			break;
		case PAUSE:
			g.setColor(Color.black);
			g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
			font.drawString( 20,Window.HEIGHT/2, "PAUSE", Color.white);
			break;
		case GAMEOVER:
			g.setColor(Color.black);
			g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
			font.drawString( 20,Window.HEIGHT/2, "GAMEOVER", Color.white);
			break;
		case CREDITS:
			g.setColor(Color.black);
			g.drawRect(0, 0, Window.WIDTH, Window.HEIGHT);
			font.drawString( 20,Window.HEIGHT/2, "BLAHHH", Color.white);
			break;
		default:
			break;
		}
	}
	
	
	
}
