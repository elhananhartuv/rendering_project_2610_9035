package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Vector;

/**
 * Tube class representing Infinite cylinder, has a axis Ray and Radius.
 * 
 * @author E&Y
 *
 */
public class Tube extends RadialGeometry {
	protected Ray axisRay;

	/**
	 * ctor for Tube Class get Ray and the radius of the tube.
	 * 
	 * @param ray the The axis of symmetry of the tube.
	 * @param r   the Radius of the tube.
	 */
	public Tube(Ray ray, double r) {
		super(r);// call to base class radial geometry
		this.axisRay = ray;
	}

	/**
	 * get function
	 * 
	 * @return the Ray of the tube
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	@Override
	public Vector getNormal(Point3D point) {
		// t is the projection of point onto cylinder's ray
		Vector vector = point.subtract(axisRay.getP0());// p-p0
		double t = alignZero(axisRay.getDirection().dotProduct(vector));
		if (isZero(t)) {// the meaning is that the vector v orthogonal to the vector (p-p0)
			return vector.normalize();// so the normal is (P-p0)
		}
		Point3D o = axisRay.getPoint(t);// p0+t*v
		return point.subtract(o).normalized();
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		Vector v = ray.getDirection();
		Point3D p = ray.getP0();
		Vector va = axisRay.getDirection();
		Point3D Pa = axisRay.getP0();
		double radius = super.radius;
		Vector deltaP = null;// p-pa equal to deltaP.
		Vector temp;// v-(v,va)*va Auxiliary calculation.
		double A;
		double B;
		double C;
		double desc;
		double t1, t2;
		try {
			temp = v.subtract(va.scale(v.dotProduct(va)));
			A = alignZero(temp.lengthSquared());
		} catch (IllegalArgumentException ex) {
			if (isZero(v.dotProduct(va))) {
				A = alignZero(v.lengthSquared());
				temp = v;
			} else {
				// there is no intersection the ray is Parallel or contained in the tube.
				return null;
			}
		}
		try {
			deltaP = p.subtract(Pa);
		} catch (IllegalArgumentException ex) {
			// If vectors in the same or opposite direction-there is no points.
			if (v.equals(va) || v.equals(va.scale(-1))) {
				return null;
			}
			B = 0;
		}
		if (deltaP == null)
			B = 0;
		else {
			try {
				B = alignZero(deltaP.subtract(va.scale(deltaP.dotProduct(va))).dotProduct(temp) * 2);
			} catch (IllegalArgumentException ex) {
				B = alignZero(deltaP.dotProduct(temp) * 2);
			}
		}
		if (deltaP == null)
			C = alignZero(-radius * radius);
		else {
			try {
				C = alignZero(deltaP.subtract(va.scale(deltaP.dotProduct(va))).lengthSquared() - radius * radius);
			} catch (IllegalArgumentException ex) {
				if (isZero(deltaP.dotProduct(va))) {
					C = alignZero(deltaP.lengthSquared() - radius * radius);
				} else {
					C = alignZero(-radius * radius);
				}
			}
		}

		desc = alignZero(B * B - 4 * A * C);
		// there is no intersection points.
		if (desc < 0)
			return null;
		desc = alignZero(Math.sqrt(desc));

		if (isZero(desc)) {
			// if the desc is zero it must be that the start point is inside the tube.
			t1 = alignZero(-B / (2d * A));
			// if the start point of the ray is outside the tube,can't be solution.
			if (p.distance(Pa) > radius)
				return null;
			return t1 > 0 ? List.of(ray.getPoint(t1)) : null;
		}

		t1 = alignZero(-B + desc) / (2d * A);
		t2 = alignZero(-B - desc) / (2d * A);

		if (t1 > 0 & t2 > 0) {
			return List.of(ray.getPoint(t1), ray.getPoint(t2));
		} else if (t1 > 0) {
			return List.of(ray.getPoint(t1));
		} else if (t2 > 0) {
			return List.of(ray.getPoint(t2));
		}
		return null;
	}
}
