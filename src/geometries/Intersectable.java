package geometries;

import java.util.List;

/**
 * 
 * Intersectable is a common interface for all geometries
 *  that intersect the  geometries shapes.
 *  @author E&Y
 */
import primitives.*;

public interface Intersectable {
	/**
	 * The function find all the intersection that the ray intersect geometric
	 * shape.
	 * 
	 * @param ray The ray that intersect the geometric shape
	 * @return list of intersections points with the geometric shape. if there is no
	 *         points return null.
	 */
	List<Point3D> findIntersections(Ray ray);
}
