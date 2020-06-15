package geometries;

import primitives.*;

/**
 * class geometry is base to all geometries shapes. has abstract function
 * getNormal.
 * 
 * @author E&Y
 *
 */
public abstract class Geometry extends Intersectable {
	protected Color emmission;
	protected Material material;

	// ***************** Constructors ********************** //

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
		this(Color.BLACK, new Material(0, 0, 0));
	}

	/**
	 * constructor initialize emmission with Color param ,initialize material to
	 * (0,0,0)
	 * 
	 * @param color for emmission
	 */
	public Geometry(Color emmission) {
		this(emmission, new Material(0, 0, 0));
	}

	// ***************** Getters/Setters ********************** //

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

	// ***************** Operations ******************** //
	/**
	 * create bounded box
	 */
	@Override
	protected void createBox() {
		minX = Double.POSITIVE_INFINITY;
		maxX = Double.NEGATIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		minZ = Double.POSITIVE_INFINITY;
		maxZ = Double.NEGATIVE_INFINITY;
	}

	/**
	 * The function calculate the normal vector in the specific point
	 * 
	 * @param point - to calculate the normal
	 * @return the normal vector
	 */
	public abstract Vector getNormal(Point3D point);

}
