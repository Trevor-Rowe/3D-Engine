package homebrewed.engine.model;

import java.awt.image.BufferedImage;

import homebrewed.engine.Camera;
import homebrewed.engine.math.Vector;

public class Mesh implements Drawable {
	
	
	private Polygon[] polygons;
	private int size;
	private Vector location; 
	
	public Mesh(Polygon[] polygons, int size, Vector location) {
		
		this.polygons = polygons;
		this.size = size; 
		this.location = location; 
		
	}
	
	public int size() {
			
		return this.size; 
		
	}
	
	public Vector getLocation() {
		
		return this.location;
		
	}
	
	public Polygon[] getPolygons() {
		
		return this.polygons; 
		
	}
	
	public void transform(Camera camera) {
		
		for(Polygon poly : this.polygons) {
			
			poly.transform(camera);
			
		} 
		
	}
	
	@Override
	public void draw(BufferedImage image, Camera camera) {
		
		for(Polygon Polygon: polygons) {
			
			Polygon.draw(image, camera);
			
		}
		
	} 
	
}
