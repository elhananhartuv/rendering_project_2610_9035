
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

/**
 * Unit tests for geometrries.Geometries class
 * 
 * @author E&Y
 *
 */
public class GeometriesTest {
	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		// the sphere equation is (x-3)^2+y^2+z^2=6.25
		Sphere sphere = new Sphere(new Point3D(3, 0, 0), 2.5);
		//// the plane equation is x+y+z=3
		Plane plane = new Plane(new Point3D(3, 0, 0), new Point3D(0, 3, 0), new Point3D(0, 0, 3));
		Triangle triangle = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
		Geometries geometries = new Geometries(sphere, plane, triangle);

		// ============ Equivalence Partitions Tests ==============

		// **** Group: intersect the ray in some geometry but not at all.
		// TC01: Ray intersect triangle and plane.
		// The ray is (0.25,0.25,0.25)+t(0,0,1) intersect the plane and the triangle but
		// not the sphere.
		assertEquals("wrong number of intersections with plane and triangle", 2,
				geometries.findIntersections(new Ray(new Point3D(0.25, 0.25, 0.25), new Vector(0, 0, 1))).size());
		// TC02: ray intersect sphere and plane
		// The ray intersect the sphere and the plane but not the triangle.
		assertEquals("wrong number of intersections with sphere and plane", 3,
				geometries.findIntersections(new Ray(new Point3D(2, 0, -36), new Vector(0, 0, 1))).size());

		// =============== Boundary Values Tests ==================
		// **** Group: ray intersect only one shape.
		// TC11: ray intersect sphere
		// the ray intersect only the sphere in one point the ray start in(4,1,0)
		assertEquals("intersect only the sphere", 1,
				geometries.findIntersections(new Ray(new Point3D(4, 1, 0), new Vector(0, 0, 1))).size());

		// TC11: ray intersect plane
		assertEquals("intersect only the plane", 1,
				geometries.findIntersections(new Ray(new Point3D(0.4, 0.4, 1.1), new Vector(0, 0, 1))).size());

		// TC11: ray intersect triangle
		assertEquals("intersect only the plane", 1,
				geometries.findIntersections(new Ray(new Point3D(0.4, 0.4, 1.1), new Vector(0, 0, -1))).size());

		// TC12: there is no intersection with the shapes.
		assertNull("no intersection", geometries.findIntersections(new Ray(new Point3D(4, 4, 0), new Vector(0, 0, 1))));

		// TC13:ray intersect all the shapes
		assertEquals("intersect all the shapes", 4,
				geometries.findIntersections(new Ray(new Point3D(0.7, 0.1, -6), new Vector(0, 0, 1))).size());

		// TC14: empty geometries group.
		// an empty group of geometries should return null.
		geometries = new Geometries();
		assertNull("empty geometries group.",
				geometries.findIntersections(new Ray(new Point3D(1, 2, 5), new Vector(1, 5, 4))));
	}

}
