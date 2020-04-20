package primitives;

/**
 * class Point representing point in 3 Dimension
 * 
 * @author USER
 *
 */
public class Point3D {
	public final static Point3D ZERO = new Point3D(0, 0, 0);
	private Coordinate x;
	private Coordinate y;
	private Coordinate z;

	/**
	 * get the Coordinate x
	 * 
	 * @return x
	 */
	public Coordinate getX() {
		return x;
	}

	/**
	 * get the Coordinate y
	 * 
	 * @return y
	 */
	public Coordinate getY() {
		return y;
	}

	/**
	 * get the Coordinate z
	 * 
	 * @return z
	 */
	public Coordinate getZ() {
		return z;
	}

	/**
	 * ctor that get 3 double numbers
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(double x, double y, double z) {
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}

	/**
	 * ctor that get 3 Coordinate
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);
	}

	/**
	 * copy ctor that get point
	 * 
	 * @param point
	 */
	public Point3D(Point3D point) {
		this.x = new Coordinate(point.x);
		this.y = new Coordinate(point.y);
		this.z = new Coordinate(point.z);
	}

	/**
	 * The function receives two points, subtracts vectors, and returns the
	 * resulting vector
	 * 
	 * @param point
	 * @return
	 */
	public Vector subtract(Point3D point) {
		return new Vector(this.x._coord - point.x._coord, this.y._coord - point.y._coord,
				this.z._coord - point.z._coord);
	}

	/**
	 * The function receives a vector, and add the vector to the point. The function
	 * returns the new point
	 * 
	 * @param vec to add to point
	 * @return new point
	 */
	public Point3D add(Vector vec) {
		return new Point3D(x._coord + vec.getHead().x._coord, y._coord + vec.getHead().y._coord,
				z._coord + vec.getHead().z._coord);
	}

	/**
	 * the function return the distanceSquared
	 * 
	 * @param point
	 * @return distanceSquared
	 */
	public double distanceSquared(Point3D point) {
		return (point.x._coord - this.x._coord) * (point.x._coord - this.x._coord)
				+ (point.y._coord - this.y._coord) * (point.y._coord - this.y._coord)
				+ (point.z._coord - this.z._coord) * (point.z._coord - this.z._coord);
	}

	/**
	 * return the distance between tow points
	 * 
	 * @param point
	 * @return the distance
	 */
	public double distance(Point3D point) {
		return Math.sqrt(distanceSquared(point));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D temp = (Point3D) obj;
		return this.x.equals(temp.x) && this.y.equals(temp.y) && this.z.equals(temp.z);
	}

	@Override
	public String toString() {
		return "(" + x.toString() + "," + y.toString() + "," + z.toString() + ")";
	}
}
