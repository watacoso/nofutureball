package nofuture;

import java.awt.Color;

import javax.swing.JFrame;


import essential.game.GameLoop;
import essential.game.Monitor;

public class NoFuture extends GameLoop {
	
	
	
	Monitor display = new Monitor(Game.WIDTH, Game.HEIGHT);
	
	public NoFuture()
	{
		JFrame window = new JFrame("No future game");
		window.add(display);
		window.setSize(Game.WIDTH + 6, Game.HEIGHT + 27);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	protected void update() {
		display.clear(Color.BLACK);
		display.render();
	}
	
	
	
	public static NoFuture gameloop = new NoFuture();
	public static void main(String[] args)
	{
		gameloop.run();
	}
	
}
