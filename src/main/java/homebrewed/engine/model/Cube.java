package homebrewed.engine.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import homebrewed.engine.Camera;
import homebrewed.engine.math.Vector;

public class Cube extends Mesh implements Drawable {

	
	public Cube(int size, Vector location) {
		
		super(new Polygon[] {
				
				new Polygon(new Vector[] { // SOUTH
						
						new Vector(new double[] {-.5, -.5, -.5, 1}),
						new Vector(new double[] {.5, -.5, -.5, 1}),
						new Vector(new double[] {.5, .5, -.5, 1}),
						new Vector(new double[] {-.5, .5, -.5, 1})
						
				}, size, location),
				new Polygon(new Vector[] { // EAST
						
						new Vector(new double[] {.5, -.5, -.5, 1}),
						new Vector(new double[] {.5, -.5, .5, 1}),
						new Vector(new double[] {.5, .5, .5, 1}),
						new Vector(new double[] {.5, .5, -.5, 1})
						
				}, size, location),
				new Polygon(new Vector[] { // NORTH
						
						new Vector(new double[] {.5, -.5, .5, 1}),
						new Vector(new double[] {-.5, -.5, .5, 1}),
						new Vector(new double[] {-.5, .5, .5, 1}),
						new Vector(new double[] {.5, .5, .5, 1})
						
				}, size, location),
				new Polygon(new Vector[] { // WEST
						
						new Vector(new double[] {-.5, -.5, .5, 1}),
						new Vector(new double[] {-.5, -.5, -.5, 1}),
						new Vector(new double[] {-.5, .5, -.5, 1}),
						new Vector(new double[] {-.5, .5, .5, 1})
						
				}, size, location),
				new Polygon(new Vector[] { // Bottom
						
						new Vector(new double[] {-.5, -.5, -.5, 1}),
						new Vector(new double[] {-.5, -.5, .5, 1}),
						new Vector(new double[] {.5, -.5, .5, 1}),
						new Vector(new double[] {.5, -.5, -.5, 1})
						
				}, size, location),
				new Polygon(new Vector[] { // Top
						
						new Vector(new double[] {-.5, .5, -.5, 1}),
						new Vector(new double[] {.5, .5, -.5, 1}),
						new Vector(new double[] {.5, .5, .5, 1}),
						new Vector(new double[] {-.5, .5, .5, 1})
						
				}, size, location)}, size, location);
	}
	
	public Vector getLocation() {
		
		return this.getLocation().clone();
		
	}
	
	@Override
	public void draw(BufferedImage image, Camera camera) {
		//System.out.println("Building cube!"); 
		super.draw(image, camera);
	} 

}
