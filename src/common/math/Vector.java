package common.math;

/**
 * Representation of free vector.
 * All the methods that take a vector as argument require
 * the amount of dimensions of an instance and the argument to be equal.
 * In other case, {@code IllegalArgumentException} is thrown ({@link Point#checkDimensions(Point)}).
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
	 * Creates a vector made of two points.
	 * @param p1 A point to be substracted from second.
	 * @param p2 A point from which first is substracted.
	 */
	public Vector(Point p1, Point p2){
		p1.checkDimensions(p2);
		this.data = new Point(p1.coordinates.length);
		for(int i = 0; i < p1.coordinates.length; i++)
			this.data.coordinates[i] = p2.coordinates[i] - p1.coordinates[i];
	}

	/**
	 * Calculate length of a vector.
	 * @return Length of a vector.
	 */
	public double getLength(){
		var result = 0d;
		for (double n : this.data.coordinates)
			result += n * n;
		return Math.sqrt(result);
	}

	/**
	 * Adds two vectors and produces the third one.
	 * @param v A vector to which the instance adds.
	 * @return A vector that is result of sum of initial vectors.
	 */
	public Vector add(Vector v){
		this.data.checkDimensions(v.data);
		var result = this.clone();
		for (int i = 0; i < v.data.coordinates.length; i++)
			result.data.coordinates[i] += v.data.coordinates[i];
		return result;
	}

	/**
	 * Multiplies the coordinates of instance by {@code n}.
	 * @param n A number by which coordinates of instance is multiplied.
	 * @return The new vector.
	 */
	public Vector multiple(double n){
		var result = this.clone();
		for(int i = 0; i < result.data.coordinates.length; i++)
			result.data.coordinates[i] *= n;
		return result;
	}

	/**
	 * Produces a dot product of two vectors.
	 * @param v A vector by which the instance is producted.
	 * @return A number that represents the multiplication of two vectors.
	 */
	public double dotProduct(Vector v){
		this.data.checkDimensions(v.data);
		var result = 0d;
		for(int i = 0; i < this.data.coordinates.length; i++)
			result += this.data.coordinates[i] * v.data.coordinates[i];
		return result;
	}

	/**
	 * Produces a cross product (<a href="https://en.wikipedia.org/wiki/Cross_product">vector product</a>) of two vectors.
	 * The operation is possible only on three-dimensional vectors.
	 * @param v A vector by which the operation performs.
	 * @return New vector that is a result of operation.
	 */
	// TODO 7-dimensional multiplication
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

	/**
	 * Returns a unit vector from instance.
	 * @return Unit vector.
	 */
	public Vector getUnit(){
		double l = this.getLength();
		var result = this.clone();
		for(int i = 0; i < result.data.coordinates.length; i++)
			result.data.coordinates[i] /= l;
		return result;
	}

	/**
	 * Returns angle between two vectors in radians.
	 * @param v A vector to which angle is calculated.
	 * @return Angle in radians.
	 */
	public double getAngle(Vector v){
		double cos = this.dotProduct(v) / (this.getLength() * v.getLength());
		return Math.acos(cos);
	}
	
	/**
	 * Returns {@code true} if instance is collinear to {@code v} vector.
	 * Zero vectors is collinear to any vector.
	 * @param v A vector to which collinearity is calculated.
	 * @return {@code} true if vectors are collinear to each other.
	 */
	public boolean isCollinear(Vector v){
		if(this.isZero())
			return true;
		return this.getAngle(v) % Math.PI == 0;
	}

	// TODO Complanarity
	// public boolean isComplanar(Vector v){}

	/**
	 * Returns {@code true} if instance is collinear to {@code v}
	 * and has the same direction as {@code v}.
	 * @param v A vector to which direction compares.
	 * @return {@code true} if vectors have same direction.
	 */
	public boolean hasSameDirection(Vector v){
		return this.isCollinear(v) && this.dotProduct(v) > 0;
	}

	/**
	 * Returns {@code true} if instance is unit vector (length is 1).
	 * @return {@code true} if length of instance is 1.
	 */
	public boolean isUnit(){
		return this.getLength() == 1d;
	}

	/**
	 * Returns {@code true} if instance is zero vector (length is 0).
	 * @return {@code true} if length of instance is 0.
	 */
	public boolean isZero(){
		return this.getLength() == 0d;
	}

	@Override
	public Vector clone(){
		return new Vector(this.data.coordinates);
	}

	@Override
	public int hashCode(){
		return this.data.hashCode();
	}

	/**
	 * Returns {@code true} if instance and {@code o} are equals.
	 * This means that both vectors are collinear, have same direction
	 * and have equal length. Equality of coordinates does not consider.
	 * @param o A vector to which the instance compares.
	 * @return {@code true} if mentioned conditions are met.
	 */
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
