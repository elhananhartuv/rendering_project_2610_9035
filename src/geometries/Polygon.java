package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> _vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane _plane;

	// ***************** Constructors ********************** //

	/**
	 * /** Polygon constructor get in addition emmission Color based on vertices
	 * list. The list must be ordered by edge path. The polygon must be convex.
	 * 
	 * @param emmission color emmission
	 * @param material  attenuation parameter
	 * @param vertices  list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex></li>
	 *                                  </ul>
	 */
	public Polygon(Color emmission, Material material, Point3D... vertices) {
		super(emmission, material);
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		_vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		_plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = _plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
		 createBox();
	}

	/**
	 * 
	 * /** Polygon constructor based on vertices list. The list must be ordered by
	 * edge path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex></li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		this(Color.BLACK, vertices);
	}

	/**
	 * ctor for polygon that get in addition emmission Color
	 * 
	 * @param emmission
	 * @param vertices
	 */
	public Polygon(Color emmission, Point3D... vertices) {
		this(emmission, new Material(0, 0, 0), vertices);
	}

	// ***************** Operations ******************** //

	@Override
	protected void createBox() {
		minX = _vertices.get(0).getX().get();
		minY = _vertices.get(0).getY().get();
		minZ = _vertices.get(0).getZ().get();
		maxX = minX;
		maxY = minY;
		maxZ = minZ;
		for (Point3D point : _vertices) {
			if (point.getX().get() < minX)
				minX = point.getX().get();
			if (point.getX().get() > maxX)
				maxX = point.getX().get();
			if (point.getY().get() < minY)
				minY = point.getY().get();
			if (point.getY().get() > maxY)
				maxY = point.getY().get();
			if (point.getZ().get() < minZ)
				minZ = point.getZ().get();
			if (point.getZ().get() > maxZ)
				maxZ = point.getZ().get();
		}
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> intersections = _plane.findIntersections(ray);

		if (intersections == null)
			return null;
		// if there is point in the intersections list need to check if it is on the
		// polygon
		Point3D p0 = ray.getP0();
		Vector v = ray.getDirection();
		int size = _vertices.size();

		Vector v1 = _vertices.get(0).subtract(p0);
		Vector vn = _vertices.get(size - 1).subtract(p0);

		double check = v.dotProduct(vn.crossProduct(v1).normalize());
		if (isZero(check))
			return null;
		// if it is positive all checks should be positive and if it negative all checks
		// should be too.
		boolean flag = false;// the meaning negative
		if (check > 0)
			flag = true;// the meaning positive
		Vector v2;
		for (int i = 0; i < size - 1; ++i) {
			v1 = _vertices.get(i).subtract(p0);
			v2 = _vertices.get(i + 1).subtract(p0);// promote next vertex
			check = alignZero(v.dotProduct(v1.crossProduct(v2).normalize()));
			// if check is different from other that mean positive and other negative(or
			// negative and other positive) or check==0 -there is no points on the polygon.
			if ((flag && (check < 0)) || (!flag && check > 0) || isZero(check))
				return null;
		}
		intersections.get(0).geometry = this;
		return intersections;
	}

	@Override
	public Vector getNormal(Point3D point) {
		return _plane.getNormal();
	}
}
