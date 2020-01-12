package common.math;

import java.util.StringJoiner;

/**
 * Basic class that represents point in n-dimensional space.
 * Objects of this class are mutable.
 */
public class Point {

	/** Stores data about coordinates in space. */
	public final double[] coordinates;

	/**
	 * Creates a point located at zero coordinates.
	 * @param size Number of dimensions. Usually 2 or 3.
	 */
	public Point(int size){
		this.coordinates = new double[size];
	}

	/**
	 * Creates a point located at {@code data} coordinates.
	 * Number of dimensions is calculated automatically from
	 * amount of coordinates.
	 * @param data Coordinates that the point has.
	 */
	public Point(double ...data){
		this(data.length);
		System.arraycopy(data, 0, this.coordinates, 0, data.length);
	}

	/**
	 * Calculate distance between this point and given one.
	 * @param p A point to which the distance is calculated.
	 * @return Distance between points.
	 */
	public double distanceTo(Point p){
		this.checkDimensions(p);
		var result = 0d;
		for(int i = 0; i < this.coordinates.length; i++)
			result += Math.pow(p.coordinates[i] - this.coordinates[i], 2);
		return Math.sqrt(result);
	}

	@Override
	public int hashCode() {
		int n = 31;
		int result = 17;
		result = n * result + this.coordinates.length;
		for(double f : this.coordinates){
			var tmp = Double.doubleToLongBits(f);
			result = n * result + (int) (tmp ^ (tmp >>> 32));
		}
		return result;
	}

	@Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(o == this)
			return true;
		if(o instanceof Point){
			var casted = (Point) o;
			if(this.coordinates.length != casted.coordinates.length)
				return false;
			for(int i = 0; i < this.coordinates.length; i++)
				if(this.coordinates[i] != casted.coordinates[i])
					return false;
			return true;
		}
		return false;
	}

	@Override
	public String toString(){
		var result = new StringJoiner("; ", "(", ")");
		for(double n : this.coordinates){
			var longRepr = (long) n;
			result.add(n == longRepr ? Long.toString(longRepr) : Double.toString(n));
		}
		return result.toString();
	}

	/**
	 * Checks if two points has the same amount of dimensions.
	 * @param p A point against which number of dimensions is checked.
	 * @throws IllegalArgumentException If number of dimensions in {@code p} point differs from this point.
	 */
	protected void checkDimensions(Point p) throws IllegalArgumentException {
		if(this.coordinates.length != p.coordinates.length)
			throw new IllegalArgumentException(
				String.format(
					"Given point %1$s has different amount of coordinates (%2$d) instead of expected (%3$d).",
					p, p.coordinates.length, this.coordinates.length
				)
			);
	}
}
