package common.math;

/**
 * Representation of free vector.
 */
public class Vector implements Cloneable {

	/** Stores data about coordinates of vector. */
	public final Point data;

	/**
	 * Creates zero vector with {@code size} dimensions.
	 * @param size Amount of dimensions.
	 * @see Point#Point(int)
	 */
	public Vector(int size){
		this.data = new Point(size);
	}

	/**
	 * Creates a vector and fills it with given data.
	 * @param data Data represents the coordinates of a vector.
	 * @see Point#Point(double[])
	 */
	public Vector(double... data){
		this.data = new Point(data);
	}

	/**
	 * Creates a vector made from two points
	 * @param p1
	 * @param p2
	 */
	public Vector(Point p1, Point p2){
		p1.checkDimensions(p2);
		this.data = new Point(p1.coordinates.length);
		for(int i = 0; i < p1.coordinates.length; i++)
			this.data.coordinates[i] = p2.coordinates[i] - p1.coordinates[i];
	}

	public double getLength(){
		var result = 0d;
		for (double n : this.data.coordinates)
			result += n * n;
		return Math.sqrt(result);
	}

	public Vector add(Vector v){
		this.data.checkDimensions(v.data);
		var result = this.clone();
		for (int i = 0; i < v.data.coordinates.length; i++)
			result.data.coordinates[i] += v.data.coordinates[i];
		return result;
	}

	public Vector multiple(double n){
		var result = this.clone();
		for(int i = 0; i < result.data.coordinates.length; i++)
			result.data.coordinates[i] *= n;
		return result;
	}

	public double dotProduct(Vector v){
		this.data.checkDimensions(v.data);
		var result = 0d;
		for(int i = 0; i < this.data.coordinates.length; i++)
			result += this.data.coordinates[i] * v.data.coordinates[i];
		return result;
	}

	public Vector crossProduct(Vector v){
		this.data.checkDimensions(v.data);
		if(this.data.coordinates.length != 3)
			throw new IllegalArgumentException("Cross product defined only for three-dimensional vectors. Supplied " + this.data.coordinates.length + "-nth dimensional vectors");
		var a = this.data.coordinates;
		var b = v.data.coordinates;
		return new Vector(
			a[1] * b[2] - a[2] * b[1],
			a[2] * b[0] - a[0] * b[2],
			a[0] * b[1] - a[1] * b[0]
		);
	}

	public Vector getUnit(){
		double l = this.getLength();
		var result = this.clone();
		for(int i = 0; i < result.data.coordinates.length; i++)
			result.data.coordinates[i] /= l;
		return result;
	}

	public double getAngle(Vector v){
		double cos = this.multiple(v) / (this.getLength() * v.getLength());
		return Math.acos(cos);
	}
	
	public boolean isCollinear(Vector v){
		if(this.isZero())
			return true;
		return this.getAngle(v) % Math.PI == 0;
	}

	// TODO Complanarity
	// public boolean isComplanar(Vector v){}

	public boolean hasSameDirection(Vector v){
		return this.isCollinear(v) && this.multiple(v) > 0;
	}

	public boolean isUnit(){
		return this.getLength() == 1d;
	}

	public boolean isZero(){
		return this.getLength() == 0d;
	}

	public Vector clone(){
		return new Vector(this.data.coordinates);
	}

	@Override
	public int hashCode(){
		return this.data.hashCode();
	}

	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(o instanceof Vector){
			var v = (Vector) o;
			var dimensionsAreMatch = this.data.coordinates.length == v.data.coordinates.length;
			if(!dimensionsAreMatch)
				return false;
			var lengthsAreEqual = this.getLength() == v.getLength();
			var directionIsSame = this.hasSameDirection(v);
			return lengthsAreEqual && directionIsSame;
		}
		return false;
	}

	@Override
	public String toString(){
		return this.data.toString();
	}
}
