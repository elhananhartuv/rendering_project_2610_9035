package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Sphere class represent sphere in 3D. extends RadialGeometry has a center
 * point and Radius.
 * 
 * @author E&Y
 *
 */
public class Sphere extends RadialGeometry {

	private Point3D center;

	/**
	 * ctor for Sphere that get the center point and the radius
	 * 
	 * @param p0 the center point of the sphere.
	 * @param r radius of the sphere.
	 */
	public Sphere(Point3D p0, double r) {
		super(r);
		center = new Point3D(p0);
	}

	/**
	 * 
	 * @return the center point of the sphere.
	 */
	public Point3D getCenter() {
		return center;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return point.subtract(center).normalize();
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		Vector u;
		try {
			u = center.subtract(ray.getP0());// o-p0
		} catch (IllegalArgumentException e) {
			// the ray start in the center of the sphere
			return List.of(ray.getPoint(radius));
		}
		double tm = alignZero(ray.getDirection().dotProduct(u));
		double d = alignZero(u.lengthSquared() - tm * tm);
		if (d >= 0)
			d = alignZero(Math.sqrt(d));
		else
			return null;
		// if d>r the ray is outside from the sphere there is no intersection
		if (d > radius || isZero(d - radius))
			return null;
		double th = alignZero(radius * radius - d * d);
		// if th is 0 that mean that d=radius so this is a tangent point.
		// if th smaller than zero this is error and cant make sqrt() function.
		if (th <= 0)
			return null;
		// now we Know that we can use sqrt function()
		th = alignZero(Math.sqrt(th));

		double t1 = alignZero(tm + th);
		double t2 = alignZero(tm - th);

		// there is no intersections.
		if (t1 <= 0 && t2 <= 0)
			return null;

		if (t1 > 0 && t2 > 0)// there is 2 intersections point
			return List.of(ray.getPoint(t1), ray.getPoint(t2));

		if (t2 > 0)// there is only one point, need to take the positive t2
			return List.of(ray.getPoint(t2));
		// there is only one point, need to take the positive t1
		else
			return List.of(ray.getPoint(t1));

	}
}
