package nofuture;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import essential.game.Drawable;

public class ModelList<T extends Drawable> implements Drawable {
	
	ArrayList<T> list = new ArrayList<T>();
	
	public ModelList()
	{
		
	}
	
	@Override
	public void renderTo(BufferedImage image, double xOffset, double yOffset)
	{
		for (int i = 0; i < list.size(); i ++)
		{
			list.get(i).renderTo(image, xOffset, yOffset);
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < list.size(); i ++)
		{
			list.get(i).update();
		}
	}
}
