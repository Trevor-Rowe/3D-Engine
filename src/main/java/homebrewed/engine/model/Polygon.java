package homebrewed.engine.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import homebrewed.engine.Camera;
import homebrewed.engine.math.CartesianAxis;
import homebrewed.engine.math.Projection;
import homebrewed.engine.math.Rotation;
import homebrewed.engine.math.Vector;

public class Polygon implements Drawable, Comparable<Polygon> {
	
	private Vector[] vertices;
	private Vector[] primedVerts;
	private int meshSize;
	private Vector location;
	
	//Testing only 
	private Vector light; 
	
	public Polygon(Vector[] vertices, int meshSize, Vector location) {
			
		this.vertices = vertices;
		this.primedVerts = new Vector[vertices.length];
		this.meshSize = meshSize;
		this.location = location;
		this.light = new Vector(new double[] { 1, 1, 1, 0 });
		
		for(int index = 0; index < this.vertices.length; index++) {
			
			Vector vertex = this.vertices[index]; 
			this.vertices[index].setEntry(0, vertex.getEntry(0) - location.getEntry(0));
			this.vertices[index].setEntry(1, vertex.getEntry(1) - location.getEntry(1));
			this.vertices[index].setEntry(2, vertex.getEntry(2) - location.getEntry(2));
			this.primedVerts[index] = this.vertices[index].clone();
			
		}
		
		
	}
	
	public Vector[] getVertices() {
			
		return this.vertices; 
		
	}
	
	public Vector[] getTransformedVertices() {
			
		return this.primedVerts; 
		
	}
	
	public void transform(Camera camera) {
		
		Projection projection  = camera.getCurrentProjection();
		Rotation rotationY = camera.getCurrentYRotation(); 
		Rotation rotationX = camera.getCurrentXRotation();
		Vector cameraLocation  = camera.getLocation();
		Vector[] primedVerts = new Vector[this.vertices.length];
		for(int index = 0; index < this.vertices.length; index++) {
			
			primedVerts[index] = this.vertices[index].clone();
			//Move before?
			primedVerts[index].setEntry(0, primedVerts[index].getEntry(0) - cameraLocation.getEntry(0));
			primedVerts[index].setEntry(1, primedVerts[index].getEntry(1) - cameraLocation.getEntry(1));
			primedVerts[index].setEntry(2, primedVerts[index].getEntry(2) - cameraLocation.getEntry(2));
			
			primedVerts[index] = 
					projection.transform(
					rotationY.transform(
					rotationX.transform(primedVerts[index])
			));		
			//System.out.println("After: " + primedVerts[index] + "\n");
			
			
			double z = primedVerts[index].getEntry(3);
			if(z != 0) {
				
				primedVerts[index].setEntry(0, (primedVerts[index].getEntry(0) / z));
				primedVerts[index].setEntry(1, (primedVerts[index].getEntry(1) / z));
				//primedVerts[index].setEntry(2, (primedVerts[index].getEntry(2) / z));
				
			}
			
			primedVerts[index].setEntry(0, (primedVerts[index].getEntry(0) * this.meshSize) 
					+ Camera.ORIGIN_X);
			primedVerts[index].setEntry(1, (primedVerts[index].getEntry(1) * this.meshSize)
					+ Camera.ORIGIN_Y);
			primedVerts[index].setEntry(2, primedVerts[index].getEntry(2) * this.meshSize);
			
		}
		this.primedVerts = primedVerts;
	
	}
	
	@Override
	public void draw(BufferedImage image, Camera camera) {
		
		Graphics g = image.getGraphics();
		if(camera.canSee(primedVerts)) {
			
			//System.out.println("Drawing!");
			Vector v1v2 = primedVerts[0].add(primedVerts[1].scale(-1));
			Vector v1v3 = primedVerts[0].add(primedVerts[2].scale(-1));
			Vector cross = v1v2.cross(v1v3);
			double lighting = this.light.dot(cross); 
			g.setColor(Color.CYAN);
			
			if(lighting < 0) { g.setColor(Color.CYAN); }
			else { g.setColor(Color.DARK_GRAY); }
			
			int[] xVals = Vector.getPointsOnAxis(primedVerts, CartesianAxis.X_AXIS);
			int[] yVals = Vector.getPointsOnAxis(primedVerts, CartesianAxis.Y_AXIS); 
			g.fillPolygon(xVals, yVals, this.vertices.length);
			//drawFrame(primedVerts, g);
			
		}

	}
	
	private void drawFrame(Vector[] primedVerts, Graphics g) {
		
		g.setColor(Color.BLACK);
		for(int index = 0; index < primedVerts.length - 1; index++) {
			 
			g.drawLine(
			(int) primedVerts[index].getEntry(0),	
			(int) primedVerts[index].getEntry(1),
			(int) primedVerts[index + 1].getEntry(0),
			(int) primedVerts[index + 1].getEntry(1)
			);
				
		}
		g.drawLine(
		(int) primedVerts[primedVerts.length - 1].getEntry(0),	
		(int) primedVerts[primedVerts.length - 1].getEntry(1),
		(int) primedVerts[0].getEntry(0),
		(int) primedVerts[0].getEntry(1)
		);
		
	}

	@Override
	public int compareTo(Polygon other) {
		
		double averageDepth = 0;
		double averageDepthOther = 0;
		Vector[] otherPrimedVerts = other.getTransformedVertices(); 
		for(int index = 0; index < this.primedVerts.length; index++) {
			
			averageDepth += this.primedVerts[index].magnitude();
			averageDepthOther += otherPrimedVerts[index].magnitude(); 
			
		}
		averageDepth = averageDepth / this.primedVerts.length; 
		averageDepthOther = averageDepthOther / otherPrimedVerts.length;
		//System.out.println((averageDepth - averageDepthOther));
		return (int) (averageDepthOther - averageDepth);
		
	}
	
}
