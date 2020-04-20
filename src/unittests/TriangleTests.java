/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for primitives.Triangle class
 * 
 * @author Elhanan & Yedidya
 *
 */
public class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Point3D p0 = new Point3D(1, 0, 0);
		Point3D p1 = new Point3D(0, 1, 0);
		Point3D p2 = new Point3D(0, 0, 1);
		Triangle triangle = new Triangle(p0, p1, p2);

		// ============ Equivalence Partitions Tests ==============

		// check if the real normal are equal to the function getNormal
		// the normal expected is (1,1,1) in each point the normal will be same.
		assertEquals("Wrong normal to triangle", new Vector(1, 1, 1).normalize(), triangle.getNormal(p1));
	}

	@Test
	public void testFindIntersections() {
		// The plane containing the triangle is x+y+z=1;
		Triangle triangle = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));

		// ============ Equivalence Partitions Tests ==============

		// TC01: The point is inside the triangle.
		// the intersection point is (1/3,1/3,1/3) the ray is (0.2,0.2,0.2)+t*(1,1,1)
		assertEquals("point is inside", List.of(new Point3D(1 / 3d, 1 / 3d, 1 / 3d)),
				triangle.findIntersections(new Ray(new Point3D(0.2, 0.2, 0.2), new Vector(1, 1, 1))));
		// TC02:The point is outside against edge from (1,0,0) to (0,0,1).
		// the intersection point with the plane is (-0.5,0.5,1) the point is outside
		// the triangle and against the edge
		assertNull("Outside against edge",
				triangle.findIntersections(new Ray(new Point3D(-0.5, 0.5, 0), new Vector(0, 0, 1))));
		// TC03:The point is Outside against vertex.
		// The intersection point with the plane is (2,0,-1) the point is against the
		// vertex (1,0,0) and outside the triangle.
		assertNull("Outside against vertex",
				triangle.findIntersections(new Ray(new Point3D(2, 0, -4), new Vector(0, 0, 1))));

		// =============== Boundary Values Tests ==================

		// TC11:The intersection point is on edge.
		// The intersection point is (0.5,0,0.5) that found on the edge (1,0,0) to
		// (0,0,1).
		assertNull("The intersection point is on edge",
				triangle.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(0, 0, 1))));

		// TC12:The intersection point is on vertex.
		// The intersection point is (1,0,0) that found in one of the vertex of our
		// triangle, should return null.
		assertNull("The intersection point is on vertex",
				triangle.findIntersections(new Ray(new Point3D(1, 0, -10), new Vector(0, 0, 1))));

		// TC13:The intersection point is on edg's continuation.
		// The intersection point is (-1,2,0) is on edg's continuation from (1,0,0) to
		// (0,1,0)
		assertNull("The intersection point is on edg's continuation",
				triangle.findIntersections(new Ray(new Point3D(-1, 2, -3), new Vector(0, 0, 1))));

	}

}
