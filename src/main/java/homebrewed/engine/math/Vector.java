package homebrewed.engine.math;

public class Vector {
	
	private double[] values; 
	
	public Vector(double[] values) {
			
		this.values = values;
		
	}
	
	public Vector(int size) {
			
		this.values = new double[size];
		for(int i = 0; i < values.length; i++) { this.values[i] = 0; }
		
	}
	
	public double dot(Vector vectorToDot) {
			
		double dotProduct = 0; 
		for(int index = 0; index < values.length; index++) {
				
			dotProduct += vectorToDot.values[index] * values[index]; 
			
		}
		return dotProduct;	
		
	}
	
	//WARN: Assume 3D 
	public Vector cross(Vector other) {
		
		Vector cross = new Vector(this.size());
		double a = this.getEntry(1) * other.getEntry(2) - this.getEntry(2) * other.getEntry(1);
		cross.setEntry(0, a);
		double b = -1 * (this.getEntry(0) * other.getEntry(2) - this.getEntry(2) * other.getEntry(0));
		cross.setEntry(1, b);
		double c = this.getEntry(0) * other.getEntry(1) - this.getEntry(1) * other.getEntry(0);
		cross.setEntry(2, c);
		return cross; 
		
	}
	
	public double magnitude() {
		
		int sumOfSqaures = 0;
		for(int i = 0; i < this.values.length; i++) { sumOfSqaures += (this.getEntry(i) * this.getEntry(i)); }
		return Math.sqrt(sumOfSqaures);
		
	}
	
	public Vector normalize() {
		
		Vector result = new Vector(this.values.clone());
		double magnitude = result.magnitude(); 
		return result.scale(1 / magnitude);
		
	}
	
	public Vector scale(double scalar) {
			
		double[] temp = new double[values.length];
		for(int index = 0; index < values.length; index++) {
				
			temp[index] = scalar * values[index]; 
			
		}
		return new Vector(temp);	
		
	}
	
	public Vector add(Vector vectorToAdd) {
		
		double[] temp = new double[values.length];
		for(int index = 0; index < values.length; index++) {
				
			temp[index] = vectorToAdd.values[index] + values[index]; 
			
		}
		return new Vector(temp);	
		
	}
	
	public Vector buffer(int quantity) {
			
		double[] bufferedValues = new double[this.values.length + quantity];
		for(int i = 0; i < this.values.length; i++) { bufferedValues[i] = this.values[i]; }
		return new Vector(bufferedValues);
		
	}
	
	public Vector clone() {
		
		return new Vector(this.values.clone()); 
		
	}
	
	public static int[] getPointsOnAxis(Vector[] points, CartesianAxis axis) {
		
		int[] result = new int[points.length];
		for(int index = 0; index < result.length; index++) {
			
			Vector point = points[index];
			switch(axis) {
			
				case X_AXIS:
				
					result[index] = (int) point.getEntry(0);
					break;
				
				case Y_AXIS:

					result[index] = (int) point.getEntry(1);
					break;
				
				case Z_AXIS:
				
					result[index] = (int) point.getEntry(2);
					break;
				
				default:
				
					result[index] = (int) point.getEntry(0);
					break;

			}
			
		}
		return result;
		
	}
	
	public Matrix getMatrix() {
			
		return new Matrix(this);
		
	}
	
	public int size() {
		
		return this.values.length; 
			
	}
	
	public double getEntry(int index) {
		
		return this.values[index];
				
	}
	
	public double[] getValues() {
		
		return this.values.clone(); 
		
	}

	
	public boolean setEntry(int index, double value) {
		
		this.values[index] = value; 
		return true;
		
	}
	
	@Override
	public String toString() {
			
		String result = "[";
		for(int index = 0; index < values.length; index++) {
			
			if(index == 0) { 
				
				result += values[index]; 
				
			} 
			else {
					
				result += ", " + values[index];
				
			}
			
		}
		return result + "]"; 
		
	}

}
