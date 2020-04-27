package geometries;

import primitives.Ray;
import primitives.*;
import java.util.List;
import static primitives.Util.*;
import primitives.Point3D;
import primitives.Vector;

/**
 * Tube class are extends RadialGeometry has a Ray and Radius
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
		return null;
	}

}
