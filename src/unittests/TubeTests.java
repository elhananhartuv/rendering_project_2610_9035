/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;
import primitives.*;
import geometries.*;

/**
 * Unit tests for primitives.Tube class
 * 
 * @author Elhanan & Yedidya
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {

		Ray ray = new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0));// P0=(1,0,0) V=(0,1,0)
		Tube tube = new Tube(ray, 1);
		// Tube equation is (x-1)^2+z^2=1

		// ============ Equivalence Partitions Tests ==============

		// TC01:Regular point on the tube
		// the normal expected is (0,0,1).
		assertEquals("Wrong normal to Tube", new Vector(0, 0, 1), tube.getNormal(new Point3D(1, 20, 1)));

		// =============== Boundary Values Tests ==================

		// TC11:the point is against p0 and (p-p0) orthogonal to v.
		assertEquals("Wrong normal to Tube", new Vector(1, 0, 0), tube.getNormal(new Point3D(2, 0, 0)));
	}

	/**
	 * Test method for {@link geometries.Tube#FindIntersections(primitives.Point3D)}.
	 */
	@Test
	public void testFindIntersections() {
		Tube tube = new Tube(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)), 1d);

		// ============ Equivalence Partitions Tests ==============

		// TC01: The ray intersect the Tube at two points
		List<Point3D> result = tube.findIntersections(new Ray(new Point3D(5, 0.8, 4), new Vector(-1, 0, 3)));
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getX().get() > result.get(1).getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("should be two points of intersect",
				List.of(new Point3D(0.4, 0.8, 17.8), new Point3D(1.6, 0.8, 14.2)), result);
		// TC02 ray intersect tube at one point (1 point)
		result = tube.findIntersections(new Ray(new Point3D(1.5, 0, 4), new Vector(2.5, 0, -2)));
		assertEquals("should be one point of intersect", List.of(new Point3D(2, 0, 3.6)), result);
		// TC03 ray doesnt intersect the tube(0 points)
		result = tube.findIntersections(new Ray(new Point3D(8, 0, 4), new Vector(2.5, 0, -2)));
		assertNull("should be null", result);

		// ================== Boundary Values Tests ==================

		// **** Group: Ray orthogonal to the tube Cases
		// TC 11: ray start before and going through the tube(2 points)
		result = tube.findIntersections(new Ray(new Point3D(3, 0.8, 5), new Vector(-1, 0, 0)));
		if (result.get(0).getX().get() > result.get(1).getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses tube", List.of(new Point3D(0.4, 0.8, 5), new Point3D(1.6, 0.8, 5)), result);
		// TC 12: ray start before and going through axis the tube(2 points)
		result = tube.findIntersections(new Ray(new Point3D(3, 0, 5), new Vector(-1, 0, 0)));
		if (result.get(0).getX().get() > result.get(1).getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 5), new Point3D(2, 0, 5)), result);
		// TC 13: ray start before and going through the center point of tube(2 point)
		result = tube.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(-1, 0, 0)));
		if (result.get(0).getX().get() > result.get(1).getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 0), new Point3D(2, 0, 0)), result);
		// TC 14:The ray start inside the tube(1 point)
		result = tube.findIntersections(new Ray(new Point3D(1.5, 0.8, 5), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0.4, 0.8, 5)), result);
		// TC 15:The ray start inside the tube and going through axis the tube(1
		// point)
		result = tube.findIntersections(new Ray(new Point3D(1.5, 0, 5), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 5)), result);
		// TC 16:The ray start inside the tube and going through center point of the
		// tube(1
		// point)
		result = tube.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 0)), result);
		// TC 17 :The ray start at the tube and go inside (1
		// point)
		result = tube.findIntersections(new Ray(new Point3D(1.6, 0.8, 5), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0.4, 0.8, 5)), result);
		// TC 18 :The ray start at the tube and go inside through center point of the
		// tube(1
		// point)
		result = tube.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 0)), result);
		// TC 19 :The ray start at the tube and go inside through axis of the tube (1
		// point)
		result = tube.findIntersections(new Ray(new Point3D(2, 0, 5), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 5)), result);
		// TC 20:The ray start after the tube and orthogonal to tube (null)
		result = tube.findIntersections(new Ray(new Point3D(-3, 0, 5), new Vector(-1, 0, 0)));
		assertNull("should be null", result);
		// TC 21:The ray start at the tube and go outside (null)
		result = tube.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-1, 0, 0)));
		assertNull("should be null", result);
		// TC 22:The ray start at the tube center point and orthogonal to tube (1 point)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 0)), result);
		// TC 23: The ray start at the at tube axis vector and orthogonal to tube (1
		// point)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 80), new Vector(-1, 0, 0)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 80)), result);
		// TC 24:The Ray orthogonal and tangent the tube
		result = tube.findIntersections(new Ray(new Point3D(2, 3, 5), new Vector(0, 1, 0)));
		assertNull("should be null", result);
		// TC 25:The Ray orthogonal and the ray not tangent the tube
		result = tube.findIntersections(new Ray(new Point3D(3, 3, 5), new Vector(0, 1, 0)));
		assertNull("should be null", result);

		// **** Group: Ray Parallel to the tube Cases (all cases return null)
		// TC 26:The ray start outside the tube and Parallel to tube (null)
		result = tube.findIntersections(new Ray(new Point3D(-3, 0, 5), new Vector(0, 0, 1)));
		assertNull("should be null", result);
		// TC 27:The ray start inside the tube and Parallel to tube (null)
		result = tube.findIntersections(new Ray(new Point3D(1.5, 0, 5), new Vector(0, 0, 1)));
		assertNull("should be null", result);
		// TC 28:The ray with same direction of the tube and start at the tube center
		// point(null)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
		// TC 29:The ray with oppsite direction of the tube and start at the tube center
		// point(null)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, -1)));
		assertNull("should be null", result);
		// TC 30:The tube contain the ray (null)
		result = tube.findIntersections(new Ray(new Point3D(2, 0, 4), new Vector(0, 0, 1)));
		assertNull("should be null", result);
		// TC 31:The ray start tube axis vector and they have same direction(null)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 69), new Vector(0, 0, 1)));
		assertNull("should be null", result);
		// TC 31:The ray start tube axis vector and go in oppsite direction(null)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 69), new Vector(0, 0, -1)));
		assertNull("should be null", result);
		// **** Group: Ray not Parallel neither orthogonal the tube Cases
		// 31: The Ray start at tube and go outside (null)
		result = tube.findIntersections(new Ray(new Point3D(2, 0, 4), new Vector(1, 0, 1)));
		assertNull("should be null", result);
		// TC32: The Ray start at tube and go inside (1 point)
		result = tube.findIntersections(new Ray(new Point3D(2, 0, 4), new Vector(-2, 0, 1)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 5)), result);
		// TC33: The Ray start at tube and go inside through the Tube point (1 point)
		result = tube.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(-1, 0, -1)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, -1)), result);
		// 34: The Ray start at tube center point (1 point)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(-1, 0, 1)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 1)), result);
		// TC35: The Ray start at tube axis vector (1 point)
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 80), new Vector(-1, 0, 1)));
		assertEquals("Ray crosses tube", List.of(new Point3D(0, 0, 81)), result);
		// TC 36:The Ray tangent to the Tube
		result = tube.findIntersections(new Ray(new Point3D(2, 3, 5), new Vector(0, 1, 3)));
		assertNull("should be null", result);
		// TC 37: The Ray tangent to the Tube
		result = tube.findIntersections(new Ray(new Point3D(2, 3, 5), new Vector(1, 1, 3)));
		assertNull("should be null", result);
		// TC 38: The Ray almost in the same tube direction
		result = tube.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0.001, 0, 1)));
		assertEquals("Ray crosses tube", List.of(new Point3D(2, 0, 1000)), result);
	}
}
