package homebrewed.engine.math;

public class Rotation implements MatrixTransformation {
	
	private Matrix matrix; 
	
	public Rotation(double theta, CartesianAxis axis) {
			
		switch(axis) {
		
		case X_AXIS: 
			
			this.matrix = new Matrix(new double[][] {
				
				{1, 0, 0, 0},
				{0, Math.cos(theta), -1 * Math.sin(theta), 0},
				{0, Math.sin(theta), Math.cos(theta), 0},
				{0, 0, 0, 1}
				
			});
			break;
			
		case Y_AXIS:
			
			this.matrix = new Matrix(new double[][] {
				
				{Math.cos(theta), 0, Math.sin(theta), 0},
				{0, 1, 0, 0},
				{-1 * Math.sin(theta), 0, Math.cos(theta), 0},
				{0, 0, 0, 1}
				
			});
			break; 
			
		case Z_AXIS: 
			
			this.matrix = new Matrix(new double[][] {
				
				{Math.cos(theta), -1 * Math.sin(theta), 0, 0},
				{Math.sin(theta), Math.cos(theta), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
				
			});
			break; 
		
			default:
				
				Matrix.getIdentityMatrix(4);
				break;
		
		} 
		
	}

	@Override
	public Vector transform(Vector input) {

		return this.matrix.multiply(input.getMatrix()).getColumnVectors()[0];
		
	}

}
