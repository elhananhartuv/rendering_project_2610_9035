/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;

import geometries.Plane;
import primitives.*;

/**
 * Unit tests for primitives.Plane class
 * 
 * @author Elhanan & Yedidya
 *
 */
public class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		Point3D p0 = new Point3D(1, 0, 0);
		Point3D p1 = new Point3D(0, 1, 0);
		Point3D p2 = new Point3D(0, 0, 1);
		Plane plane = new Plane(p0, p1, p2);

		// ============ Equivalence Partitions Tests ==============
		// check if the real normal are equal to the function getNormal
		assertEquals("Wrong normal to plane", new Vector(1, 1, 1).normalize(), plane.getNormal(p1));
	}

	/**
	 * Test method for {@link geometries.Plane#FindIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		// The plane equation is x+y+z=1 or (1,0,0)+t(1,-1,0)+p(1,0,-1).
		// The tow directions is {(1,-1,0),(1,0,-1)} and the normal is (1,1,1).
		Plane plane = new Plane(new Point3D(1, 0, 0), new Vector(1, 1, 1));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Ray intersects the plane
		// The ray is (0,0,0.5)+t(0,0,1),
		// the intersection point that expected is (0,0,1).
		assertEquals("Ray intersects the plane",
				plane.findIntersections(new Ray(new Point3D(0, 0, 0.5), new Vector(0, 0, 1))),
				List.of(new Point3D(0, 0, 1)));

		// TC02: Ray does not intersects the plane
		// The ray is (0,0,0.5)+t(0,0,-1) there is no intersection point that expected
		assertNull("Ray does not intersects the plane",
				plane.findIntersections(new Ray(new Point3D(4, 4, 4), new Vector(0, 0, 1))));

		// =============== Boundary Values Tests ==================

		// **** Group: Ray is parallel to the plane but not orthogonal
		// TC11: The ray included in the plane
		// if the ray included in the plane the expected result is null
		assertNull("The ray included in the plane",
				plane.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 0, -1))));

		// TC12: The ray not included in the plane
		assertNull("The ray not included in the plane",
				plane.findIntersections(new Ray(new Point3D(5, 4, 4), new Vector(1, 0, -1))));

		// **** Group: Ray is orthogonal to the plane.
		// TC21:Ray start before the plane
		assertEquals("Ray start before the plane",
				plane.findIntersections(new Ray(new Point3D(0.1, 0.1, 0.1), new Vector(2, 2, 2))),
				List.of(new Point3D(1 / 3d, 1 / 3d, 1 / 3d)));
		// TC22: Ray start in the plane.
		assertNull("Ray start in the plane",
				plane.findIntersections(new Ray(new Point3D(1 / 3d, 1 / 3d, 1 / 3d), new Vector(1, 1, 1))));

		// TC23: Ray start after the plane.
		assertNull("Ray start after the plane",
				plane.findIntersections(new Ray(new Point3D(4, 4, 4), new Vector(1, 1, 1))));

		// **** Group:Ray is neither orthogonal nor parallel to and begins at the plane.
		// TC31:P0 is in the plane.
		assertNull("P0 is in the plane.",
				plane.findIntersections(new Ray(new Point3D(1 / 3d, 1 / 3d, 1 / 3d), new Vector(2, 2, 1))));

		// TC32:Ray begins in the same point that appears in the plane.
		assertNull("P0 is in the plane.", plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(2, 2, 1))));

	}
}
