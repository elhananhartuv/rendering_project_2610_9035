package geometries;
import primitives.*;
/**
 * interface geometry is base to all geometries shapes.
 * has function getNormal.
 * @author E&Y
 *
 */
public interface Geometry extends Intersectable  {
	/**
	 * return the normal vector in the point
	 * @param point
	 * @return
	 */
    Vector getNormal(Point3D point);
}
