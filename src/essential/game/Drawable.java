package essential.game;

import java.awt.image.BufferedImage;

public interface Drawable {
	public abstract void update();
	public abstract void renderTo(BufferedImage image, double xOffset, double yOffset);
}
