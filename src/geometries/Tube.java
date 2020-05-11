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
	 * ctor for Tube that get 3 parameters ray,radius and color.
	 * 
	 * @param ray       axis ray for tube.
	 * @param r         radius of tube.
	 * @param emmission the color emmission.
	 */
	public Tube(Ray ray, double r, Color emmission) {
		super(r, emmission);
		this.axisRay = ray;
	}

	/**
	 * ctor with 4 parameters ray,radius,emission and material
	 * 
	 * @param ray       axis ray for tube.
	 * @param r         radius of tube.
	 * @param emmission the color emmission.
	 * @param material
	 */
	public Tube(Ray ray, double r, Color emmission, Material material) {
		super(r, emmission, material);
		this.axisRay = ray;
	}

	/**
	 * axisRay getter
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
	public List<GeoPoint> findIntersections(Ray ray) {
		Vector v = ray.getDirection();
		Point3D p = ray.getP0();
		Vector va = axisRay.getDirection();
		Point3D pa = axisRay.getP0();
		double radius = super.radius;
		Vector deltaP = null;// p-pa equal to deltaP.
		Vector temp;// v-(v,va)*va Auxiliary calculation.
		double a;
		double b;
		double c;
		double desc;
		double t1, t2;
		try {
			temp = v.subtract(va.scale(v.dotProduct(va)));
			a = alignZero(temp.lengthSquared());
		} catch (IllegalArgumentException ex) {
			if (isZero(v.dotProduct(va))) {
				a = alignZero(v.lengthSquared());
				temp = v;
			} else {
				// there is no intersection the ray is Parallel or contained in the tube.
				return null;
			}
		}
		try {
			deltaP = p.subtract(pa);
		} catch (IllegalArgumentException ex) {
			// If vectors in the same or opposite direction-there is no points.
			if (v.equals(va) || v.equals(va.scale(-1))) {
				return null;
			}
			b = 0;
		}
		if (deltaP == null)
			b = 0;
		else {
			try {
				b = alignZero(deltaP.subtract(va.scale(deltaP.dotProduct(va))).dotProduct(temp) * 2);
			} catch (IllegalArgumentException ex) {
				b = alignZero(deltaP.dotProduct(temp) * 2);
			}
		}
		if (deltaP == null)
			c = alignZero(-radius * radius);
		else {
			try {
				c = alignZero(deltaP.subtract(va.scale(deltaP.dotProduct(va))).lengthSquared() - radius * radius);
			} catch (IllegalArgumentException ex) {
				if (isZero(deltaP.dotProduct(va))) {
					c = alignZero(deltaP.lengthSquared() - radius * radius);
				} else {
					c = alignZero(-radius * radius);
				}
			}
		}

		desc = alignZero(b * b - 4 * a * c);
		// there is no intersection points.
		if (desc < 0)
			return null;
		desc = alignZero(Math.sqrt(desc));

		if (isZero(desc)) {
			// if the desc is zero it must be that the start point is inside the tube.
			t1 = alignZero(-b / (2d * a));
			// if the start point of the ray is outside the tube,can't be solution.
			if (p.distance(pa) > radius)
				return null;
			return t1 > 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) : null;
		}

		t1 = alignZero(-b + desc) / (2d * a);
		t2 = alignZero(-b - desc) / (2d * a);

		if (t1 > 0 & t2 > 0) {
			return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
		} else if (t1 > 0) {
			return List.of(new GeoPoint(this, ray.getPoint(t1)));
		} else if (t2 > 0) {
			return List.of(new GeoPoint(this, ray.getPoint(t2)));
		}
		return null;
	}
}
