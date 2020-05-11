package geometries;

import java.util.List;

import primitives.*;

/**
 * 
 * Intersectable is a common interface for all geometries that intersect the
 * geometries shapes.
 * 
 * @author E&Y
 */
public interface Intersectable {

	/**
	 * The function find all the intersection that the ray intersect geometric
	 * shape.
	 * 
	 * @param ray The ray that intersect the geometric shape
	 * @return list of intersections geoPoints with the geometric shape. if there is
	 *         no points return null.
	 */
	List<GeoPoint> findIntersections(Ray ray);

	/**
	 * Geo point is help class when we intersect geometry shapes to know which kind
	 * of geometry the point intersect. contain Geometry, and Point3D.
	 * 
	 * @author E&Y
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		/**
		 * ctor with two parameters
		 * 
		 * @param geometry the shape that the point is on the him.
		 * @param point3d  the point.
		 */
		public GeoPoint(Geometry geometry, Point3D point3d) {
			this.geometry = geometry;
			this.point = point3d;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			GeoPoint temp = (GeoPoint) obj;
			return this.geometry == temp.geometry && this.point.equals(temp.point);
		}
	}
}
