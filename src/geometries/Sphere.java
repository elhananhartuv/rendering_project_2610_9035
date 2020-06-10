package geometries;

import java.util.List;

import primitives.Color;
import primitives.Material;
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

	// ***************** Constructors ********************** //

	/**
	 * ctor for Sphere that get the center point and the radius
	 * 
	 * @param p0 the center point of the sphere.
	 * @param r  radius of the sphere.
	 */
	public Sphere(Point3D p0, double r) {
		this(p0, r, Color.BLACK);
	}

	/**
	 * ctor that get in addition color.
	 * 
	 * @param p0    the center point of the sphere
	 * @param r     the radius of the sphere
	 * @param color emmission color.
	 */
	public Sphere(Point3D p0, double r, Color emmission) {
		this(p0, r, emmission, new Material(0, 0, 0));
	}

	/**
	 * ctor that get in addition material.
	 * 
	 * @param p0
	 * @param r
	 * @param emmission
	 * @param material  attenuation parameters
	 */
	public Sphere(Point3D p0, double r, Color emmission, Material material) {
		super(r, emmission, material);
		center = new Point3D(p0);
		createBox();
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * center getter
	 * 
	 * @return the center point of the sphere.
	 */
	public Point3D getCenter() {
		return center;
	}

	// ***************** Operations ******************** //
	@Override
	public void createBox() {
		minX = center.getX().get() - radius;
		minY = center.getY().get() - radius;
		minZ = center.getZ().get() - radius;
		maxX = center.getX().get() + radius;
		maxY = center.getY().get() + radius;
		maxZ = center.getZ().get() + radius;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return point.subtract(center).normalize();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		Vector u;
		try {
			u = center.subtract(ray.getP0());// o-p0
		} catch (IllegalArgumentException e) {
			// the ray start in the center of the sphere
			return List.of(new GeoPoint(this, ray.getPoint(radius)));
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
			return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));

		if (t2 > 0)// there is only one point, need to take the positive t2
			return List.of(new GeoPoint(this, ray.getPoint(t2)));
		// there is only one point, need to take the positive t1
		else
			return List.of(new GeoPoint(this, ray.getPoint(t1)));

	}
}
