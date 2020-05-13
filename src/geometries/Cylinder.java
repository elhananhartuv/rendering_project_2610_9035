package geometries;

import static primitives.Util.*;
import java.util.LinkedList;
import java.util.List;
import primitives.*;

/**
 * Cylinder class represents Cylinder in 3D coordinate extend Tube class
 * 
 * @author Elhanan & Yedidya
 *
 */
public class Cylinder extends Tube {
	private double height;

	// ***************** Constructors ********************** //

	/**
	 * ctor of Cylinder get 3 parameters ray,radius and height and call to Tube ctor
	 * with(ray,r)
	 * 
	 * @param ray    of the cylinder
	 * @param r      radius of cylinder
	 * @param height of cylinder.
	 */
	public Cylinder(Ray ray, double r, double height) {
		this(ray, r, height, Color.BLACK, new Material(0, 0, 0));

	}

	/**
	 * ctor that get in addition color.
	 * 
	 * @param ray       axis ray
	 * @param r         radius
	 * @param height    the height of the cylinder
	 * @param emmission color emmission
	 */
	public Cylinder(Ray ray, double r, double height, Color emmission) {
		this(ray, r, height, emmission, new Material(0, 0, 0));
	}

	/**
	 * ctor that get 4 parameters material in addition .
	 * 
	 * @param ray
	 * @param r
	 * @param height
	 * @param emmission
	 * @param material
	 */
	public Cylinder(Ray ray, double r, double height, Color emmission, Material material) {
		super(ray, r, emmission, material);
		this.height = height;
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * height getter
	 * 
	 * @return height of the Cylinder
	 */
	public double getHeight() {
		return height;
	}

	// ***************** Operations ******************** //

	@Override
	public Vector getNormal(Point3D point) {
		// t is the projection of point onto cylinder's ray
		// t=v*(p-p0)
		Vector v = axisRay.getDirection();
		double t;
		try {
			t = alignZero(v.dotProduct(point.subtract(axisRay.getP0())));// p-p0
		} catch (IllegalArgumentException e) {
			return v.scale(-1);// if p is p0 and the point on the lower base
		}
		// t is the projection on ray and if t equal to zero the meaning is, that the
		// point is on the lower base and are not equal to p0.
		if (isZero(t)) {
			return v.scale(-1); // The point is on the lower base so the normal is v or -v
		}
		// t is the projection on ray and if t equal to height the meaning is, that the
		// point is on the top base
		if (isZero(height - t)) {
			return v;// The point is on the top base so the normal is v.
		}
		// else if the point is regular point on the cylinder calculate like tube.
		Point3D o = axisRay.getPoint(t);// O=p0+t*v
		return point.subtract(o).normalized();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> intersectionsWithTube = super.findIntersections(ray);
		Vector axisDirection = axisRay.getDirection();
		// the point is at the center of the circle on the top base.
		Point3D pTop = axisRay.getPoint(height);
		Point3D p0 = axisRay.getP0();

		// create the plane that the top base and lower base are Contained on it
		Plane topBase = new Plane(pTop, axisDirection);
		Plane lowerBase = new Plane(p0, axisRay.getDirection().scale(-1));
		List<GeoPoint> intersectionsWithTopPlane = null;
		List<GeoPoint> intersectionsWithLowPlane = null;

		// check if the point that on the tube is on the cylinder.
		// calculate the projection on the axis if it bigger than height or smallest
		// than 0-no intersection point.
		List<GeoPoint> intersectionsWithCylinder = new LinkedList<GeoPoint>();
		if (intersectionsWithTube != null) {
			for (int i = 0; i < intersectionsWithTube.size(); i++) {
				double projection = alignZero(
						intersectionsWithTube.get(i).point.subtract(p0).dotProduct(axisDirection));
				if (projection < height && projection > 0)
					intersectionsWithCylinder.add(intersectionsWithTube.get(i));
			}
		}

		intersectionsWithTopPlane = topBase.findIntersections(ray);
		intersectionsWithLowPlane = lowerBase.findIntersections(ray);
		// if there is no more intersection.
		if (intersectionsWithTopPlane == null && intersectionsWithLowPlane == null)
			return intersectionsWithCylinder.size() > 0 ? intersectionsWithCylinder : null;
		// there is more intersection with the plane, need to check if it is on the top
		// base or lower base.
		if (intersectionsWithTopPlane != null) {
			Point3D intersectPointWIthPlane = intersectionsWithTopPlane.get(0).point;
			double distance = alignZero(intersectPointWIthPlane.distance(pTop));
			if (distance < radius)
				intersectionsWithCylinder.add(new GeoPoint(this, intersectPointWIthPlane));
		}
		if (intersectionsWithLowPlane != null) {
			Point3D intersectPointWIthPlane = intersectionsWithLowPlane.get(0).point;
			double distance = alignZero(intersectPointWIthPlane.distance(axisRay.getP0()));
			if (distance < radius)
				intersectionsWithCylinder.add(new GeoPoint(this, intersectPointWIthPlane));
		}
		// check if there is intersection if not return null
		return intersectionsWithCylinder.size() > 0 ? intersectionsWithCylinder : null;
	}
}
