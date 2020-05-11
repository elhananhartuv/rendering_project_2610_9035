package geometries;

import primitives.*;

/**
 * interface geometry is base to all geometries shapes. has function getNormal.
 * 
 * @author E&Y
 *
 */
public abstract class Geometry implements Intersectable {

	protected Color emmission;

	/**
	 * emmission getter
	 * 
	 * @return emmission
	 */
	public Color getEmission() {
		return emmission;
	}
	
	/**
	 * default constructor initialize emission with Black color
	 */
	public Geometry() {
		this(Color.BLACK);
	}
	
	/**
	 * constructor initialize emmission with Color param
	 * 
	 * @param color for emmission
	 */
	public Geometry(Color color) {
		this.emmission = color;
	}

	/**
	 * function calculate the normal vector in the specific point
	 * 
	 * @param point - to calculate the normal
	 * @return the normal vector
	 */
	public abstract Vector getNormal(Point3D point);
}
