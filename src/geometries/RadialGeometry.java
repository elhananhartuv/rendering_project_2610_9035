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

	// ***************** Constructors ********************** //

	/**
	 * ctor for RadialGeometry that got only rasius
	 * 
	 * @param r radius
	 */
	public RadialGeometry(double r) {
		this(r, Color.BLACK);
	}

	/**
	 * ctor with two parameters radius and emmission color.
	 * 
	 * @param r     for radius
	 * @param color for emission
	 */
	public RadialGeometry(double r, Color emmission) {
		this(r, emmission, new Material(0, 0, 0));
	}

	/**
	 * ctor with 3 parameters radius, emmission color and material.
	 * 
	 * @param r         radius
	 * @param emmission emission color.
	 * @param material  attenuation parameter
	 */
	public RadialGeometry(double r, Color emmission, Material material) {
		super(emmission, material);
		this.radius = r;
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
