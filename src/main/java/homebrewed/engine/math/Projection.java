package homebrewed.engine.math;

public class Projection implements MatrixTransformation {
	
	private double a; 
	private double fAngle; 
	private double rMin; 
	private double rMax; 
	private Vector rUnit; 
	
	public Projection(double a, double fAngle, double rMin, double rMax, Vector rUnit) {
		
		this.a = a;
		this.fAngle = fAngle; 
		this.rMin = rMin;
		this.rMax = rMax; 
		this.rUnit = rUnit; 
		
	}

	@Override
	public Vector transform(Vector input) {
		
		double f = 1 / (Math.tan(fAngle / 2));
		double q = rMax / (rMax - rMin); 
		double r = input.dot(rUnit);
		double c = q * (r - rMin); 
		
		
		Matrix result = new Matrix( new double[][] {
			
			{(c * rUnit.getEntry(0)) + (a * f), 0, 0, 0},
			{0, (c * rUnit.getEntry(1)) + f, 0, 0},
			{0, 0, (c * rUnit.getEntry(2)), 0},
			{0, 0, 0, r}
			
		});
		
		result = new Matrix( new double[][] {
			
			{(a * f), 0, 0, 0},
			{0,  f, 0, 0},
			{0, 0, q, -rMin * q},
			{0, 0, 1, 0}
			
		});
		
		return result.multiply(input.getMatrix()).getColumnVectors()[0];
		
	}

}
