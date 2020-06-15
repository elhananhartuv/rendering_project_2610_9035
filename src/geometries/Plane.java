package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * class plane represent plane in in 3 Dimension the plane has point and normal
 * to plane.
 * 
 * @author E&Y
 *
 */
public class Plane extends Geometry {
	private Point3D point;
	private Vector normal;

	// ***************** Constructors ********************** //

	/**
	 * ctor that get 3 point to create the plane. in addition get material and
	 * emmisiom.
	 * 
	 * @param material for the attenuation parameters
	 * @param emmision color emmision
	 * @param p0       first vertex
	 * @param p1       second vertex
	 * @param p2       third vertex
	 */
	public Plane(Point3D p0, Point3D p1, Point3D p2, Color emmision, Material material) {
		super(emmision, material);
		point = new Point3D(p0);
		Vector v1 = p0.subtract(p1);// make one direction
		Vector v2 = p0.subtract(p2);// make the second direction
		// calculate the normal to plane and make sure that the vector is normalized
		normal = v1.crossProduct(v2).normalize();
		this.infiniteObject=true;
	}

	/**
	 * ctor that get 3 point and additionally color. the ctor get the 3 point and
	 * create the plane.
	 * 
	 * @param emmision color
	 * @param p0       first vertex
	 * @param p1       second vertex
	 * @param p2       third vertex
	 */
	public Plane(Point3D p0, Point3D p1, Point3D p2, Color emmision) {
		this(p0, p1, p2, emmision, new Material(0, 0, 0));
	}

	/**
	 * A constructor that gets three points and calculates the normal plane and
	 * initializes.
	 * 
	 * @param p0 first vertex
	 * @param p1 second vertex
	 * @param p2 third vertex
	 */
	public Plane(Point3D p0, Point3D p1, Point3D p2) {
		this(p0, p1, p2, Color.BLACK);
	}

	/**
	 * ctor that get point and normal vector and additionally material and color.
	 * 
	 * @param material for the attenuation parameters
	 * @param emmision color.
	 * @param point    random point on the plane
	 * @param vector   normal vector to the plane.
	 */
	public Plane(Point3D point, Vector vector, Color emmision, Material material) {
		super(emmision, material);
		this.point = new Point3D(point);
		this.normal = vector.normalize();// make sure that the vector normalized
		this.infiniteObject=true;
	}

	/**
	 * ctor that get point and normal and additionally color. the ctor get point and
	 * normal and create plane.
	 * 
	 * @param emmision color
	 * @param point    random point on the plane
	 * @param vector   the normal vector to the plane.
	 */
	public Plane(Point3D point, Vector vector, Color emmision) {
		this(point, vector, emmision, new Material(0, 0, 0));
	}

	/**
	 * Constructor that receives point and direction and initializes accordingly
	 * 
	 * @param point
	 * @param vector
	 */
	public Plane(Point3D point, Vector vector) {
		this(point, vector, Color.BLACK);
	}

	// ***************** Getters/Setters ********************** //

	/**
	 * normal getter
	 * 
	 * @return the normal to plane
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * point getter
	 * 
	 * @return the point that representing the plane
	 */
	public Point3D getPoint() {
		return point;
	}

	// ***************** Operations ******************** //

	@Override
	public Vector getNormal(Point3D point) {
		return normal;// in each point on the plane the normal will be the same.
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		Vector qp0;// Q-P0
		try {
			// check if the ray start in the plane
			qp0 = point.subtract(ray.getP0());
		} catch (IllegalArgumentException ex) {
			return null;// The ray start in the plane
		}
		double numerator = alignZero(normal.dotProduct(qp0));
		double denominator = alignZero(normal.dotProduct(ray.getDirection()));
		// if the ray start at the plane or parallel to the plane or composed in the
		// plane there is no intersection point
		if (isZero(numerator) || isZero(denominator))
			return null;
		double t = alignZero(numerator / denominator);
		if (t > 0)
			return List.of(new GeoPoint(this, ray.getPoint(t)));
		return null;
	}

	@Override
	public String toString() {
		return "the normal of the plane is: " + normal.toString() + " and the point is:" + point.toString();
	}
}