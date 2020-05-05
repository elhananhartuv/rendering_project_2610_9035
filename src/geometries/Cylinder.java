package geometries;

import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;
import primitives.*;

/**
 * Cylinder class represents Cylinder in 3D coordinate extend Tube class
 * 
 * @author Elhanan &Yedidya
 *
 */
public class Cylinder extends Tube {
	private double height;

	/**
	 * ctor of Cylinder get 3 parameters ray,radius and height and call to Tube ctor
	 * with(ray,r)
	 * 
	 * @param ray    of the cylinder
	 * @param r      radius of cylinder
	 * @param height of cylinder.
	 */
	public Cylinder(Ray ray, double r, double height) {
		super(ray, r);
		this.height = height;
	}

	/**
	 * get function
	 * 
	 * @return height of the Cylinder
	 */
	public double getHeight() {
		return height;
	}

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
	public List<Point3D> findIntersections(Ray ray) {
		List<Point3D> intersectionsWithTube = super.findIntersections(ray);
		Vector axisDirection = axisRay.getDirection();
		// the point is at the center of the circle on the top base.
		Point3D pTop = axisRay.getPoint(height);

		// create the plane that the top base and lower base are Contained on it
		Plane topBase = new Plane(pTop, axisRay.getDirection());
		Plane lowerBase = new Plane(axisRay.getP0(), axisRay.getDirection().scale(-1));
		List<Point3D> intersectionsWithTopPlane = null;
		List<Point3D> intersectionsWithLowPlane = null;

		// check if the point that on the tube,if intersect the top base and the lower
		// base that mean that the point is on the cylinder. if isn't, remove from
		// the list .
		List<Point3D> intersectionsWithCylinder = new ArrayList<Point3D>();
		if (intersectionsWithTube != null) {
			for (int i = 0; i < intersectionsWithTube.size(); i++) {
				intersectionsWithTopPlane = topBase
						.findIntersections(new Ray(intersectionsWithTube.get(i), axisDirection));
				intersectionsWithLowPlane = lowerBase
						.findIntersections(new Ray(intersectionsWithTube.get(i), axisDirection.scale(-1)));
				// if one list is null -the point is not on cylinder.
				if (intersectionsWithTopPlane != null && intersectionsWithLowPlane != null) {
					intersectionsWithCylinder.add(intersectionsWithTube.get(i));
				}
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
			Point3D intersectPointWIthPlane = intersectionsWithTopPlane.get(0);
			double distance = alignZero(intersectPointWIthPlane.distance(pTop));
			if (distance < radius)
				intersectionsWithCylinder.add(intersectPointWIthPlane);
		}
		if (intersectionsWithLowPlane != null) {
			Point3D intersectPointWIthPlane = intersectionsWithLowPlane.get(0);
			double distance = alignZero(intersectPointWIthPlane.distance(axisRay.getP0()));
			if (distance < radius)
				intersectionsWithCylinder.add(intersectPointWIthPlane);
		}
		// check if there is intersection if not return null
		return intersectionsWithCylinder.size() > 0 ? intersectionsWithCylinder : null;

	}
}
