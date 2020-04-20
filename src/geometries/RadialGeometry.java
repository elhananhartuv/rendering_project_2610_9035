package geometries;

/**
 * The abstract class RadialGeometry is basic class to all geometries that has
 * radius
 * 
 * @author E&Y
 *
 */
public abstract class RadialGeometry implements Geometry {
	protected double radius;

	/**
	 * ctor for Radial
	 * 
	 * @param r
	 */
	public RadialGeometry(double r) {
		radius = r;
	}

	/**
	 * cpoy ctor
	 * 
	 * @param other
	 */
	public RadialGeometry(RadialGeometry other) {
		radius = other.radius;
	}

	/**
	 * @return the Radius
	 */
	public double getRadius() {
		return radius;
	}
}
