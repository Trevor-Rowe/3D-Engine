package homebrewed.engine.math;

public class Matrix {
	
	private Vector[] rows; 
	private Vector[] columns;
	
	public Matrix(double[][] entries) {
		
		this.rows = new Vector[entries.length];
		this.columns = new Vector[entries[0].length];

		//Go down through rows
		for(int row = 0; row < this.rows.length; row++) {
			
			//Copy current row into vectors
			this.rows[row] = new Vector(entries[row].clone());
			
		}
		for(int col = 0; col < this.columns.length; col++) {
			
			Vector tempCol = new Vector(this.rows.length);
			for(int row = 0; row < this.rows.length; row++) {
				
				Vector currentRow = this.rows[row]; 
				tempCol.setEntry(row, currentRow.getEntry(col));
				
			}
			this.columns[col] = tempCol; 
			
		}
		
	}
	
	public Matrix(int rows, int cols) {
		
		this.rows = new Vector[rows]; 
		this.columns = new Vector[cols];
		for(int i = 0; i < this.rows.length; i++) {
			
			this.rows[i] = new Vector(this.columns.length); 
			for(int j = 0; j < this.columns.length; j++) {
			
				this.columns[j] = new Vector(this.rows.length);
			}
		} 
		
	}
	
	public Matrix(Vector column) {
			
		this.columns = new Vector[] { column };
		this.rows = new Vector[column.size()];
		for(int i = 0; i < this.rows.length; i++) { this.rows[i] = new Vector(new double[] { column.getEntry(i) }); }
		
	}
	
	public int getNumberOfRows() {
		
		return this.rows.length; 
		
	}
	
	public int getNumberOfColumns() {
		
		return this.columns.length; 
		
	}
	
	public Vector[] getColumnVectors() {
			
		return this.columns; 
		
	}
	
	public Vector[] getRowVectors() {
		
		return this.rows; 
		
	}
	
	public void transpose() {
				
		Vector[] temp = this.columns; 
		this.columns = this.rows; 
		this.rows = temp; 
		
		
	}

	public Matrix multiply(Matrix other) {
		
		//System.out.println(other); 
		if(this.getNumberOfColumns() != other.getNumberOfRows()) { throw new IllegalArgumentException(); }
		//System.out.println("New matrix will have: " + this.getNumberOfRows() + " rows  and " + other.getNumberOfColumns() + " columns."); 
		double[][] result = new double[this.getNumberOfRows()][other.getNumberOfColumns()];
		Vector[] otherCols = other.getColumnVectors();
		for(int i = 0; i < this.rows.length; i++) {
			for(int j = 0; j < otherCols.length; j++) {
				result[i][j] = this.rows[i].dot(otherCols[j]);
			} 
		}
		return new Matrix(result);
		
	}

	@Override
	public String toString() {
			
		String result = "";
		for(Vector vector : rows) {
				
			result += vector.toString() + "\n"; 
			
		}
		return result;
		
	}
	
	public static Matrix getIdentityMatrix(int size) {
		
		double[][] result = new double[size][size];
		for(int i = 0; i < size; i++) {
			result[i][i] = 1; 
		}
		return new Matrix(result);  
		
	}

}
