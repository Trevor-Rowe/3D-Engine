package homebrewed.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import homebrewed.engine.math.CartesianAxis;
import homebrewed.engine.math.Projection;
import homebrewed.engine.math.Rotation;
import homebrewed.engine.math.Vector;
import homebrewed.engine.model.Cube;
import homebrewed.engine.model.Mesh;
import homebrewed.engine.model.Polygon;


//start with static camera.
public class Camera {
	
	public static final int WIDTH = 1600; 
	public static final int HEIGHT = 1600; 
	public static final int ORIGIN_X = WIDTH / 2;
	public static final int ORIGIN_Y = HEIGHT / 2; 
	public static final int ORIGIN_Z = 1;
	
	//Orientation information
	private double rMin; 
	private double rMax;
	Vector rUnit;
	Vector rTemp; 
	Vector location;
	//Aspect ratio and field of view
	private double a; 
	private double fAngle;
	
	//Temporary for testing.
	private double thetaX;
	private double thetaY;
	private double thetaZ;
	private double dTheta; 
	
	ArrayList<Mesh> cubes;
	private Cube cube; 
	private Mesh teapot; 
	
	//lets start with single cube
	public Camera() {
			
		this.rMin = 1; 
		this.rMax = 1000; 
		this.a = WIDTH / HEIGHT; 
		this.fAngle = 0.5 * Math.PI;
		this.dTheta = 0.2;
		this.thetaX = 0; this.thetaY = 0; this.thetaZ = 0;
		
		this.rUnit = new Vector(new double[] { 0, 0, 1, 0});
		this.rTemp = rUnit.clone(); 
		this.location = new Vector(new double[] { 0, 0, 0, 0 });
		
		this.teapot = loadTeapot(new Vector(new double[] {0, 0, 0, 0}));
		this.cube = new Cube(100, new Vector(new double[] { 0, 0, 0, 0 }));
		
 	}
	
	public Mesh loadTeapot(Vector location) {
		
		File file = new File("C:\\Users\\Trevor\\Desktop\\Teapot.obj");
		ArrayList<Polygon> polygons = new ArrayList<Polygon>(); 
		ArrayList<Vector> vertices = new ArrayList<Vector>(); 
		try {
			
			Scanner sc = new Scanner(file);
			while(sc.hasNext()) {
				
				String line = sc.nextLine();
				//System.out.println(line); 
				String[] data = line.split(" ");
				if(data[0].equalsIgnoreCase("v")) {
					
					vertices.add(new Vector(new double[] {
							
							Double.parseDouble(data[1]),
							Double.parseDouble(data[2]),
							Double.parseDouble(data[3]),
							1
							
					}));
					
				}
				else if(data[0].equalsIgnoreCase("f")) {
					
					Polygon polygon; 
					Vector[] verts = new Vector[3]; 
					verts[0] = vertices.get(Integer.parseInt(data[1]) - 1);
					verts[1] = vertices.get(Integer.parseInt(data[2]) - 1);
					verts[2] = vertices.get(Integer.parseInt(data[3]) - 1);
					polygon = new Polygon(verts, 400, location); 
					polygons.add(polygon);
					
				}
				
			}
			sc.close();
			Object[] sortedPolys = polygons.toArray();
			Polygon[] results = new Polygon[sortedPolys.length]; 
			for(int index = 0; index < sortedPolys.length; index++) {
				
				Object obj = sortedPolys[index]; 
				Polygon polygon = (Polygon) obj;
				results[index] = polygon; 
				
			}
			return new Mesh(results, 400, location); 
			
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		} 
		return null; 
		
	}
	
	public Vector getLocation() {
		
		return this.location;
		
	}

	public Projection getCurrentProjection() {
			
		return new Projection(a, fAngle, rMin, rMax, rUnit); 
		
	}
	
	public Rotation getCurrentXRotation() {
		
		return new Rotation(thetaX, CartesianAxis.X_AXIS);
		
	}
	
	public Rotation getCurrentYRotation() {
		
		return new Rotation(thetaY, CartesianAxis.Y_AXIS);
		
	}
	
	public Rotation getCurrentZRotation() {
		
		return new Rotation(thetaZ, CartesianAxis.Z_AXIS);
		
	}
	
	public void rotateCamera(CartesianAxis axis, double scale) {
		
		switch(axis) {
		
		case X_AXIS:
			
			this.thetaX = (thetaX + (dTheta * scale)) % (2 * Math.PI); 
			break;
			
		case Y_AXIS:
			
			this.thetaY = (thetaY + (dTheta * scale)) % (2 * Math.PI);
			break;
			
		case Z_AXIS:
			
			this.thetaZ = (thetaZ + (dTheta * scale)) % (2 * Math.PI); 
			break;
			
		default:
			break;
		
		}
		
	}

	public void moveCamera(double displacement, CartesianAxis axis) {
		
		switch(axis) {
		
		case X_AXIS:
			
			this.location.setEntry(0, this.location.getEntry(0) + displacement); 
			break;
		
		case Y_AXIS:
			
			this.location.setEntry(1, this.location.getEntry(1) + displacement); 
			break;
		
		case Z_AXIS:
			
			this.location.setEntry(2, this.location.getEntry(2) + displacement); 
			break;
			
		}
		
		
	}
	
	public boolean canSee(Vector[] vertices) {
		
		for(Vector vertex: vertices) {
			
			if(vertex.add(this.location.scale(-1)).dot(rUnit) > 0) { return false; }
			
		} 
		Vector v1v2 = vertices[0].add(vertices[1].scale(-1));
		Vector v1v3 = vertices[0].add(vertices[2].scale(-1));
		Vector cross = v1v2.cross(v1v3);
		return cross.dot(rUnit) < 0; 
		
	}
	
	public void draw(BufferedImage image) {
		
		Graphics g = image.getGraphics(); 
		Font f = new Font("Temp", Font.BOLD, 32); 
		g.setFont(f);
		g.setColor(Color.RED);
		g.drawString(this.location.toString(), 100, 100);
		g.drawString(rTemp.toString(), 100, 150);
		g.drawString(thetaY + "", 100, 200);
		//thetaX += 0.05;
		//thetaZ += 0.05; 
		this.teapot.transform(this);
		teapot.draw(image, this);
		//this.cube.transform(this);
		//cube.draw(image, this);

		
	}

}
