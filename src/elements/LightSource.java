package elements;

import primitives.*;

/**
 * LightSource is the interface to all the light source that has position and
 * the light shines differently.
 * 
 * @author E&Y
 *
 */
public interface LightSource {
	/**
	 * The function calculate the lighted point intensity .
	 * 
	 * @param p lighted point
	 * @return intensity light.
	 */
	public Color getIntensity(Point3D p);

	/**
	 * The function return normalize vector from the lighted source to the lighted
	 * point.if the point that got is the lighted source position return null.
	 * 
	 * @param p lighted point
	 * @return vector from light source to lighted point.
	 */
	public Vector getL(Point3D p);
	
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * @param point
	 * @return
	 */
	double getDistance(Point3D point);

}
