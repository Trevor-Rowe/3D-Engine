package homebrewed.engine.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import homebrewed.engine.Camera;

public interface Drawable {
	
	public void draw(BufferedImage image, Camera camera);

}
