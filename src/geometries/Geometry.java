package geometries;

import primitives.*;

/**
 * class geometry is base to all geometries shapes. has abstract function
 * getNormal.
 * 
 * @author E&Y
 *
 */
public abstract class Geometry implements Intersectable {
	protected Color emmission;
	protected Material material;

	/**
	 * emmission getter
	 * 
	 * @return emmission
	 */
	public Color getEmission() {
		return emmission;
	}

	/**
	 * get material function
	 * 
	 * @return material
	 */
	public Material getMatrial() {
		return material;
	}

	/**
	 * ctor that get Color and Material.
	 * 
	 * @param emmission color emmission
	 * @param material  material.
	 */
	public Geometry(Color emmission, Material material) {
		this.emmission = emmission;
		this.material = material;
	}

	/**
	 * default constructor initialize emmission with Black color
	 */
	public Geometry() {
		this(Color.BLACK);// call to ctor
		material = new Material(0, 0, 0);
	}

	/**
	 * constructor initialize emmission with Color param
	 * 
	 * @param color for emmission
	 */
	public Geometry(Color emmission) {
		this.emmission = emmission;
		material = new Material(0, 0, 0);
	}

	/**
	 * The function calculate the normal vector in the specific point
	 * 
	 * @param point - to calculate the normal
	 * @return the normal vector
	 */
	public abstract Vector getNormal(Point3D point);
}
