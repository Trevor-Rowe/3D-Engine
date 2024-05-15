package homebrewed.engine.model;

import homebrewed.engine.math.Matrix;
import homebrewed.engine.math.Vector;

public interface Transformable {
	
	public Vector[] applyTransformation(Matrix transformation);  

}
