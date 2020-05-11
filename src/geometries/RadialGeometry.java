package geometries;

import primitives.Color;

/**
 * The abstract class RadialGeometry is basic class to all geometries that has
 * radius
 * 
 * @author E&Y
 *
 */
public abstract class RadialGeometry extends Geometry {
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
	 * ctor with two params
	 * 
	 * @param r     for radius
	 * @param color for emission - call to super()
	 */
	public RadialGeometry(double r, Color color) {
		super(color);
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
	 * radius getter
	 * 
	 * @return the Radius
	 */
	public double getRadius() {
		return radius;
	}
}
