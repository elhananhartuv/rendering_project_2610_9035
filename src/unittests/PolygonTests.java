package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;
import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for
	 * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
			fail("Constructed a polygon with wrong order of vertices");
		} catch (IllegalArgumentException e) {
		}

		// TC03: Not in the same plane
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 2, 2));
			fail("Constructed a polygon with vertices that are not in the same plane");
		} catch (IllegalArgumentException e) {
		}

		// TC04: Concave quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
			fail("Constructed a concave polygon");
		} catch (IllegalArgumentException e) {
		}

		// =============== Boundary Values Tests ==================

		// TC10: Vertix on a side of a quadrangular
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
			fail("Constructed a polygon with vertix on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC11: Last point = first point
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}

		// TC12: Collocated points
		try {
			new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 1, 0));
			fail("Constructed a polygon with vertice on a side");
		} catch (IllegalArgumentException e) {
		}
	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
				new Point3D(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
	}

	/**
	 * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Polygon polygon = new Polygon(new Point3D(2, 0, 2), new Point3D(1, 0, 2), new Point3D(1, 1, 2),
				new Point3D(2, 2, 2));

		// ============ Equivalence Partitions Tests ==============

		// TC01: The point is inside the Polygon.
		assertEquals("point is inside", List.of(new Point3D(1.5, 1, 2)),
				polygon.findIntersections(new Ray(new Point3D(1.5, 1, 0), new Vector(0, 0, 1))));

		// TC02:The point is outside against edge from (1,0,0) to (0,0,1).
		assertNull("Outside against edge",
				polygon.findIntersections(new Ray(new Point3D(1.5, -1, 0), new Vector(0, 0, 1))));
		// TC03:The point is Outside against vertex .
		assertNull("Outside against Vertex",
				polygon.findIntersections(new Ray(new Point3D(0.8, -1, 0), new Vector(0, 0, 1))));
		// =============== Boundary Values Tests ==================

		// TC11:The intersection point is on edge.
		assertNull("The intersection point is on edge",
				polygon.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, 1))));

		// TC12:The intersection point is on vertex.
		assertNull("The intersection point is on vertex",
				polygon.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1))));

		// TC13:The intersection point is on edg's continuation.

		assertNull("The intersection point is on edg's continuation",
				polygon.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(0, 0, 1))));

	}
}
