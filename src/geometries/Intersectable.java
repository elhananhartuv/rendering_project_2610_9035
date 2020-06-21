package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 * 
 * Intersectable is a common interface for all geometries that intersect the
 * geometries shapes.
 * 
 * @author E&Y
 */
public abstract class Intersectable {
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

	double minX = Double.POSITIVE_INFINITY;
	double maxX = Double.NEGATIVE_INFINITY;
	double minY = Double.POSITIVE_INFINITY;
	double maxY = Double.NEGATIVE_INFINITY;
	double minZ = Double.POSITIVE_INFINITY;
	double maxZ = Double.NEGATIVE_INFINITY;
	public boolean infiniteObject = false;

	/**
	 * The function find all the intersection that the ray intersect geometric
	 * shape.
	 * 
	 * @param ray The ray that intersect the geometric shape
	 * @return list of intersections geoPoints with the geometric shape. if there is
	 *         no points return null.
	 */
	public abstract List<GeoPoint> findIntersections(Ray ray);

	/**
	 * 
	 * @param Ray to intersect with the Geometry, only if ray intersect the bounding
	 *            box.
	 * @return List of point the are the intersection between the Ray parm and the
	 *         geometry form.
	 */
	public List<GeoPoint> findIntersectionsBoundingBox(Ray ray) {
		if (isRayIntersectBox(ray))
			return findIntersections(ray);
		return null;
	}

	// ***************** Operations ******************** //
	/**
	 * check if ray intersect the box
	 * 
	 * @param ray- the specific ray to check if intersect the box.
	 * @return true if ray intersect otherwise false.
	 */
	protected boolean isRayIntersectBox(Ray ray) {
		Vector v = ray.getDirection();
		double dirX = v.getHead().getX().get();
		double dirY = v.getHead().getY().get();
		double dirZ = v.getHead().getZ().get();
		Point3D p0 = ray.getP0();
		double startX = p0.getX().get();
		double startY = p0.getY().get();
		double startZ = p0.getZ().get();
		double tXmin = (minX - startX) / dirX;
		double tXmax = (maxX - startX) / dirX;

		if (tXmin > tXmax) {
			double temp = tXmin;
			tXmin = tXmax;
			tXmax = temp;
		}

		double tYmin = (minY - startY) / dirY;
		double tYmax = (maxY - startY) / dirY;

		if (tYmin > tYmax) {
			double temp = tYmin;
			tYmin = tYmax;
			tYmax = temp;
		}

		if ((tXmin > tYmax) || (tYmin > tXmax))
			return false;

		if (tYmin > tXmin)
			tXmin = tYmin;

		if (tYmax < tXmax)
			tXmax = tYmax;

		double tZmin = (minZ - startZ) / dirZ;
		double tZmax = (maxZ - startZ) / dirZ;

		if (tZmin > tZmax) {
			double temp = tZmin;
			tZmin = tZmax;
			tZmax = temp;
		}

		if ((tXmin > tZmax) || (tZmin > tXmax))
			return false;

		if (tZmin > tXmin)
			tXmin = tZmin;

		if (tZmax < tXmax)
			tXmax = tZmax;
		return true;
	}

	/**
	 * The function create the box and update if needed.
	 */
	protected abstract void createBox();

	// ***************** get&set ******************** //
	/**
	 * get min x
	 * 
	 * @return minX
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * get man x
	 * 
	 * @return maxX
	 */
	public double getMaxX() {
		return maxX;
	}

	/**
	 * get man y
	 * 
	 * @return maxY
	 */
	public double getMaxY() {
		return maxY;
	}

	/**
	 * get min y
	 * 
	 * @return minY
	 */
	public double getMinY() {
		return minY;
	}

	/**
	 * get man z
	 * 
	 * @return maxZ
	 */
	public double getMaxZ() {
		return maxZ;
	}

	/**
	 * get min z
	 * 
	 * @return minZ
	 */
	public double getMinZ() {
		return minZ;
	}

	/**
	 * get middle Point of box
	 * 
	 * @return Point3D
	 */
	public Point3D getCenterBox() {
		return new Point3D((minX + maxX) / 2, (minY + maxY - minY) / 2, (minZ + maxZ) / 2);
	}
}
