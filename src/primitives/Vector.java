package primitives;

/**
 * Class Vector is the basic class representing a vector in 3D. The class is
 * based on Point to representing the head vector.
 * 
 * @author Elhanan & Yedidya Shmueli
 *
 */
public class Vector {

	private Point3D head;

	/**
	 * head geter
	 * 
	 * @return the head point that representing vector
	 */
	public Point3D getHead() {
		return head;
	}

	
	// ***************** Constructors ********************** //
	/**
	 * ctor that get 3 Coordinates. throw exception for the zero vector
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		// if is equal to zero throw exception
		if (new Point3D(x, y, z).equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error cannot accept the zero vector");
		head = new Point3D(x, y, z);
	}

	/**
	 * ctor that get 3 double numbers throw exception if is equal to zero vector
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x, double y, double z) {
		// if is equal to zero throw exception
		if (new Point3D(x, y, z).equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error cannot accept the zero vector");
		head = new Point3D(x, y, z);
	}

	/**
	 * ctor that get point3D
	 * 
	 * @param point
	 */
	public Vector(Point3D point) {
		if (point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error cannot accept the zero vector");
		this.head = new Point3D(point);
	}

	/**
	 * copy ctor
	 * 
	 * @param vector
	 */
	public Vector(Vector vector) {
		this.head = new Point3D(vector.head);
	}

	// ***************** Operations ********************** //
	/**
	 * add tow vectors
	 * 
	 * @param vector
	 * @return the new vector
	 */
	public Vector add(Vector vector) {
		return new Vector(this.getHead().getX()._coord + vector.getHead().getX()._coord,
				this.getHead().getY()._coord + vector.getHead().getY()._coord,
				this.getHead().getZ()._coord + vector.getHead().getZ()._coord);
	}

	/**
	 * subtract between tow vectors
	 * 
	 * @param vector
	 * @return new vector
	 */
	public Vector subtract(Vector vector) {
		return new Vector(this.getHead().getX()._coord - vector.getHead().getX()._coord,
				this.getHead().getY()._coord - vector.getHead().getY()._coord,
				this.getHead().getZ()._coord - vector.getHead().getZ()._coord);
	}

	/**
	 * Scalar multiplication in vector
	 * 
	 * @param num
	 * @return
	 */
	public Vector scale(double num) {
		return new Vector(num * this.getHead().getX()._coord, num * this.getHead().getY()._coord,
				num * this.getHead().getZ()._coord);
	}

	/**
	 * the function calculate dot product
	 * 
	 * @param vector
	 * @return
	 */
	public double dotProduct(Vector vector) {
		return (this.getHead().getX()._coord * vector.getHead().getX()._coord
				+ this.getHead().getY()._coord * vector.getHead().getY()._coord
				+ this.getHead().getZ()._coord * vector.getHead().getZ()._coord);
	}

	/**
	 * calculate the cross product between tow vectors
	 * 
	 * @param vec
	 * @return cross product
	 */
	public Vector crossProduct(Vector vec) {
		double x = this.getHead().getY()._coord * vec.getHead().getZ()._coord
				- this.getHead().getZ()._coord * vec.getHead().getY()._coord;
		double y = this.getHead().getZ()._coord * vec.getHead().getX()._coord
				- this.getHead().getX()._coord * vec.getHead().getZ()._coord;
		double z = this.getHead().getX()._coord * vec.getHead().getY()._coord
				- this.getHead().getY()._coord * vec.getHead().getX()._coord;
		return new Vector(x, y, z);
	}

	/**
	 * return the lengthSquared of the vector
	 * 
	 * @return
	 */
	public double lengthSquared() {
		return getHead().getX()._coord * getHead().getX()._coord + getHead().getY()._coord * getHead().getY()._coord
				+ getHead().getZ()._coord * getHead().getZ()._coord;
	}

	/**
	 * return the length of the vector
	 * 
	 * @return
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * The function normalizes and modifies! the vector
	 * 
	 * @return this
	 */
	public Vector normalize() {
		this.head = scale(1 / this.length()).head;
		return this;
	}

	/**
	 * The function returns a vector with the same direction and normalized
	 * 
	 * @return another vector with some direction but normalized
	 */
	public Vector normalized() {
		return new Vector(this).normalize();
	}

	//*************** Admin *****************//
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector temp = (Vector) obj;
		return this.head.equals(temp.head);
	}

	@Override
	public String toString() {
		return "head: " + head.toString();
	}
}
