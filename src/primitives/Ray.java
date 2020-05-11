package primitives;



/**
 * class Ray representing ray with starting point and direction
 * 
 * @author E&Y
 *
 */
public class Ray {
	private Point3D p0;
	private Vector direction;

	/**
	 * ctor that get Point3D and Vector to make the ray
	 * 
	 * @param p0
	 * @param direction
	 */
	public Ray(Point3D p0, Vector direction) {
		this.p0 = new Point3D(p0);
		// make sure that the vector are normalize
		this.direction = new Vector(direction.normalized());
	}

	/**
	 * 
	 * @return starting point
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * 
	 * @return the direction of the ray
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * get double number and return the target point: (p0+t*v)
	 * 
	 * @param t the scaler that need to mult the direction vector
	 * @return the new target point on the ray: p0+t*v
	 */
	public Point3D getPoint(double t) {
		return this.p0.add(direction.scale(t));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ray))
			return false;
		Ray temp = (Ray) obj;
		return p0.equals(temp.p0) && direction.equals(temp.direction);
	}

	@Override
	public String toString() {
		return "point: " + p0.toString() + " direction: " + direction.toString();
	}

}
