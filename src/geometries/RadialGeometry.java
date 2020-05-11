package geometries;

import primitives.Color;
import primitives.Material;

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
	 * ctor for RadialGeometry that got only rasius
	 * 
	 * @param r radius
	 */
	public RadialGeometry(double r) {
		radius = r;
	}

	/**
	 * ctor with two parameters radius and emmission color.
	 * 
	 * @param r     for radius
	 * @param color for emission
	 */
	public RadialGeometry(double r, Color emmission) {
		super(emmission);
		radius = r;
	}

	/**
	 * ctor with 3 parameters radius, emmission color and material.
	 * 
	 * @param r         radius
	 * @param emmission emission color.
	 * @param material
	 */
	public RadialGeometry(double r, Color emmission, Material material) {
		this(r, emmission);
		this.material = material;
	}

	/**
	 * cpoy ctor
	 * 
	 * @param other radialGeometry to copy.
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
