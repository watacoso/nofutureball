package nofuture;

import java.awt.image.BufferedImage;

import essential.game.Drawable;

public class Game implements Drawable {
	
	final public static int WIDTH = 300;
	final public static int HEIGHT = 300;
	
	
	public Game()
	{
		
	}

////////// MODEL COLLECTIONS (those are the game objects) { \\\\\\\\\\\
	//public ModelList<SomeGameObject> gameObjects = new ModelList<SomeGameObject>();
	
	
///////////// Update and rendering \\\\\\\\\\\\\\\
	@Override
	public void update() {
		// gameObjects.update();
	}
	
	@Override
	public void renderTo(BufferedImage image, double xOffset, double yOffset) {
		// gameObjects.renderTo(image, xOffset, yOffset);
	}
	
}
