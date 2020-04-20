package geometries;

import primitives.*;
import static primitives.Util.*;
import java.util.List;

/**
 * class Triangle represent triangle in 3 Dimension the class extends Polygon.
 * 
 * @author E&Y
 *
 */
public class Triangle extends Polygon {
	/**
	 * ctor that get 3 Point3D and make the triangle.
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		super(p1, p2, p3);
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (Point3D p : _vertices)
			toReturn += p.toString();
		return toReturn;
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		List<Point3D> intersections = _plane.findIntersections(ray);

		if (intersections == null)
			return null;// there is no intersection point
		// if the list isn't empty check if the point is on the triangle;
		Point3D p0 = ray.getP0();
		Vector v = ray.getDirection();
		Vector v1 = _vertices.get(0).subtract(p0);
		Vector v2 = _vertices.get(1).subtract(p0);
		Vector v3 = _vertices.get(2).subtract(p0);
		double t1 = v.dotProduct(v1.crossProduct(v2));
		if (isZero(t1))// if it is zero -no intersection and return.
			return null;
		double t2 = v.dotProduct(v2.crossProduct(v3));
		if (isZero(t2))
			return null;
		double t3 = v.dotProduct(v3.crossProduct(v1));
		// if all positive or negative so the point is on the triangle.
		if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0))
			return intersections;
		return null;// no intersection.
		// return super.findIntersections(ray);
	}

}
