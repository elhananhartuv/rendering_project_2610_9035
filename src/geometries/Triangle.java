package geometries;

import primitives.*;
import java.util.List;

/**
 * class Triangle represent triangle in 3 Dimension, the class extends Polygon.
 * 
 * @author E&Y
 *
 */
public class Triangle extends Polygon {
	// ***************** Constructors ********************** //
	/**
	 * ctor that get 3 Vertex Point3D and create the triangle.
	 * 
	 * @param p1 first vertex.
	 * @param p2 second vertex.
	 * @param p3 third vertex.
	 */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		this(Color.BLACK, p1, p2, p3);
	}

	/**
	 * ctor that get 3 points to create the triangle additionally get color
	 * emmision.
	 * 
	 * @param emmision color.
	 * @param p1       first vertex.
	 * @param p2       second vertex.
	 * @param p3       third vertex.
	 */
	public Triangle(Color emmision, Point3D p1, Point3D p2, Point3D p3) {
		this(emmision, new Material(0, 0, 0), p1, p2, p3);
	}

	/**
	 * ctor that get 3 points to create the triangle additionally get material
	 * 
	 * @param material
	 * @param emmision color.
	 * @param p1       first vertex.
	 * @param p2       second vertex.
	 * @param p3       third vertex.
	 */
	public Triangle(Color emmision, Material material, Point3D p1, Point3D p2, Point3D p3) {
		super(emmision, material, p1, p2, p3);
	}

	// ***************** Operations ******************** //

	@Override
	public String toString() {
		String toReturn = "";
		for (Point3D p : _vertices)
			toReturn += p.toString();
		return toReturn;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		// call to find intersection in Polygon.
		List<GeoPoint> intersectionGeoPoints = super.findIntersections(ray);
		if (intersectionGeoPoints == null)
			return intersectionGeoPoints;
		intersectionGeoPoints.get(0).geometry = this;
		return intersectionGeoPoints;
	}
}
