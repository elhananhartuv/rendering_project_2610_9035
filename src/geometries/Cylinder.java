package geometries;

import static primitives.Util.*;

import java.util.List;

import primitives.*;

/**
 * Cylinder class represents Cylinder in 3D coordinate extena Tube class
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
		return super.findIntersections(ray);
	}
}
