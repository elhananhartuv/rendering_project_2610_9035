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
	 * 
	 * @author E&Y
	 *
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;
		
		 /**
		  * ctor with two params
		  * 
		  * @param geometry
		  * @param point3d
		  */
		public GeoPoint(Geometry geometry, Point3D point3d) {
			this.geometry = geometry;
			this.point = point3d;
		}
	}

	/**
	 * The function find all the intersection that the ray intersect geometric
	 * shape.
	 * 
	 * @param ray The ray that intersect the geometric shape
	 * @return list of intersections geoPoints with the geometric shape. if there is no
	 *         points return null.
	 */
	List<GeoPoint> findIntersections(Ray ray);
}
