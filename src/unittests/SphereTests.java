/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;

/**
 * Unit tests for primitives.Sphere class
 * 
 * @author Elhanan & Yedidya
 *
 */
public class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);
		// the sphere equation is (x-1)^2+y^2+z^2=1
		// the Point (2,0,0) on the sphere
		// now we need to check if the function get normal return the right result

		// ============ Equivalence Partitions Tests ==============
		// TC01: regular point on the sphere.
		assertEquals("Wrong normal to sphere", new Vector(1, 0, 0), sphere.getNormal(new Point3D(2, 0, 0)));
	}

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray's line is outside the sphere (0 points)
		assertEquals("Ray's line out of sphere", null,
				sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

		// TC02: Ray starts before and crosses the sphere (2 points)
		Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
		Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
		List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).point.getX().get() > result.get(1).point.getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses sphere", List.of(new GeoPoint(sphere,p1), new GeoPoint(sphere,p2)), result);

		// TC03: Ray starts inside the sphere (1 point)
		// the intersection point that intersection is (2/3,2/3,2/3)
		assertEquals("Ray crosses sphere ", List.of(new GeoPoint(sphere,new Point3D(2 / 3d, 2 / 3d, 2 / 3d))),
				sphere.findIntersections(new Ray(new Point3D(1 / 2d, 1 / 2d, 1 / 2d),
						new Vector(1 / Math.sqrt(3), 1 / Math.sqrt(3), 1 / Math.sqrt(3)))));

		// TC04: Ray starts after the sphere (0 points)
		// check if the result list is null
		assertNull("Ray starts after the sphere",
				sphere.findIntersections(new Ray(new Point3D(2, 2, 2), new Vector(1, 0, 0))));

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		assertEquals("ray from sphere goes inside", List.of(new GeoPoint(sphere,new Point3D(2, 0, 0))),
				sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(1, -1, 0))));
		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull("Ray from sphere goes outside",
				sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, -1, 0))));

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		result = sphere.findIntersections(new Ray(new Point3D(1, 3, 0), new Vector(0, -1, 0)));
		// check if there is more then 2 points or less.
		assertEquals("Wrong number of points", 2, result.size());
		// Sort the points according to y, that we can equal between the points
		// correctly.
		if (result.get(0).point.getY().get() > result.get(1).point.getY().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray starts before the sphere through the center",
				List.of(new GeoPoint(sphere,new Point3D(1, -1, 0)), new GeoPoint(sphere,new Point3D(1, 1, 0))), result);

		// TC14: Ray starts at sphere and goes inside (1 points)
		assertEquals("Ray starts at sphere and goes inside through the center", List.of(new GeoPoint(sphere,new Point3D(1, -1, 0))),
				sphere.findIntersections(new Ray(new Point3D(1, 1, 0), new Vector(0, -1, 0))));

		// TC15: Ray starts inside (1 points)
		assertEquals("Ray starts inside through the center", List.of(new GeoPoint(sphere,new Point3D(1, -1, 0))),
				sphere.findIntersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0, -1, 0))));

		// TC16: Ray starts at the center (1 points)
		assertEquals("Ray starts at the center", List.of(new GeoPoint(sphere,new Point3D(1, 1, 0))),
				sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))));

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull("Ray from sphere goes outside",
				sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(0, -1, 0))));
		// TC18: Ray starts after sphere (0 points)
		assertNull("Ray after sphere goes outside",
				sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(0, -1, 0))));

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull("Ray starts before the tangent point ",
				sphere.findIntersections(new Ray(new Point3D(0, -1, 0), new Vector(1, 0, 0))));

		// TC20: Ray starts at the tangent point
		assertNull("Ray starts  at the tangent point ",
				sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 0, 0))));
		// TC21: Ray starts after the tangent point
		assertNull("Ray starts  after the tangent point ",
				sphere.findIntersections(new Ray(new Point3D(2, -1, 0), new Vector(1, 0, 0))));

		// **** Group: Special cases
		// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull("ray is orthogonal to ray start to sphere's center line",
				sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1))));
	}
}
