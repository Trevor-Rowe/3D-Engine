package homebrewed.engine.math;

public class Translation implements MatrixTransformation {
	
	private Matrix matrix; 
	
	public Translation(double displacementX, double displacementY, double displacementZ) {

			
		this.matrix = new Matrix(new double[][] {
				
				{ 1, 0, 0, displacementX },
				{ 0, 1, 0, displacementY },
				{ 0, 0, 1, displacementZ },
				{ 0, 0, 0, 1 }
				
		});
	
	}

	@Override
	public Vector transform(Vector input) {
		
		return this.matrix.multiply(input.getMatrix()).getColumnVectors()[0]; 
		
	} 

}
