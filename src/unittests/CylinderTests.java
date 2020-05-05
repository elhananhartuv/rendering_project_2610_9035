package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;
import geometries.Cylinder;
import primitives.*;

/**
 * Unit tests for primitives.Cylinder class
 * 
 * @author USER
 *
 */
public class CylinderTests {

	@Test
	public void testGetNormal() {
		Ray ray = new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1));
		// The height is 4 and the radius is 1
		Cylinder cylinder = new Cylinder(ray, 1, 4);
		// the cylinder equation is (x-1)^2+y^2=1

		// ============ Equivalence Partitions Tests ==============
		// TC01: regular point on the cylinder.
		assertEquals("Wrong normal to cylinder", new Vector(1, 0, 0), cylinder.getNormal(new Point3D(2, 0, 1)));
		// TC02:second test of regular point.
		assertEquals("Wrong normal to cylinder", new Vector(1, 1, 0).normalized(),
				cylinder.getNormal(new Point3D(2, 1, 1)));

		// =============== Boundary Values Tests ==================

		// TC011: The point is on the lower base and the cylinder.
		assertEquals("Wrong normal to cylinder", new Vector(0, 0, -1), cylinder.getNormal(new Point3D(2, 0, 0)));
		// TC12: The point is on the top base and on the cylinder.
		assertEquals("Wrong normal to cylinder", new Vector(0, 0, 1), cylinder.getNormal(new Point3D(2, 0, 4)));
		// TC13:The point is only on the base
		assertEquals("The point is only on the base.", new Vector(0, 0, 1),
				cylinder.getNormal(new Point3D(1.5, 1.25, 4)));
		// TC14: The point is on the top base and on the ray.
		assertEquals("The point is on the top base and on the ray", cylinder.getNormal(new Point3D(1, 0, 4)),
				new Vector(0, 0, 1));
		// TC15: The point is on the lower and equal to p0(p==p0).
		assertEquals("The point is on the lower and equal to p0", new Vector(0, 0, -1),
				cylinder.getNormal(new Point3D(1, 0, 0)));
	}

	/**
	 * Test method for
	 * {@link geometries.Cylinder#FindIntersections(primitives.Point3D)}.
	 */
	@Test
	public void testFindIntersections() {
		Cylinder cylinder = new Cylinder(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1)), 1d, 10);

		// ============ Equivalence Partitions Tests ==============

		// TC01: The ray start under the cylinder and going through 2 bases(2 points).
		Ray ray = new Ray(new Point3D(1.75, 0, -1), new Vector(0, 0, 1));
		List<Point3D> result = cylinder.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getZ().get() > result.get(1).getZ().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Worng points", List.of(new Point3D(1.75, 0, 0), new Point3D(1.75, 0, 10)), result);
		// TC02: The ray start before the cylinder and intersect (2 points)
		ray = new Ray(new Point3D(0, 0, 2), new Vector(1, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getZ().get() > result.get(1).getZ().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Worng points", List.of(new Point3D(1, 0, 3), new Point3D(3, 0, 5)), result);
		// TC03: The ray start before the cylinder and going through the top (2
		// points)
		ray = new Ray(new Point3D(0, 0, 8), new Vector(1, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getZ().get() > result.get(1).getZ().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Worng points", List.of(new Point3D(1, 0, 9), new Point3D(2, 0, 10)), result);
		// TC04: The ray start after the cylinder (0 points)
		ray = new Ray(new Point3D(4, 0, 5), new Vector(1, 0, 1));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);
		// TC05: The ray start on the top base of the cylinder (0 points)
		ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 0, 1));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);
		// TC06: Ray's starts within cylinder and going through the top (1 point)
		ray = new Ray(new Point3D(1.5, 0, 3), new Vector(-0.05, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(1.15, 0, 10)), result);

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line is parallel to the axis, but not equal to axis.
		// TC11: Ray's starts under cylinder and going through the bases(2 point)
		ray = new Ray(new Point3D(2.5, 0, -10), new Vector(0, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getZ().get() > result.get(1).getZ().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Worng points", List.of(new Point3D(2.5, 0, 0), new Point3D(2.5, 0, 10)), result);
		// TC12: Ray's starts within cylinder and going through the top (1 point)
		ray = new Ray(new Point3D(2.5, 0, 3), new Vector(0, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2.5, 0, 10)), result);
		// TC13: Ray's starts within cylinder and going through the lower base (1 point)
		ray = new Ray(new Point3D(2.5, 0, 5), new Vector(0, 0, -1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2.5, 0, 0)), result);
		// TC14: Ray's starts at cylinder lower base and going through the top (1 point)
		ray = new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(1.5, 0, 10)), result);
		// TC15: Ray's starts at cylinder lower base and going outside (0 points)
		ray = new Ray(new Point3D(1.5, 0, 0), new Vector(0, 0, -1));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);
		// TC16: Ray's starts at cylinder center and going through the top (1 point)
		ray = new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2, 0, 10)), result);
		// TC17: Ray's starts at cylinder center and going outside (0 points)
		ray = new Ray(new Point3D(2, 0, 0), new Vector(0, 0, -1));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);

		// **** Group: Ray's line is equal to axis.
		// TC21:ray start at the lower base (1 point).
		ray = new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2, 0, 10)), result);
		// TC22:ray start at the top base.
		ray = new Ray(new Point3D(2, 0, 10), new Vector(0, 0, -1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2, 0, 0)), result);
		// TC23: Ray's line is equal to axis but start in the middle and intersect the
		// top base.
		ray = new Ray(new Point3D(2, 0, 5), new Vector(0, 0, 1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2, 0, 10)), result);
		// TC24: Ray's line is equal to axis but start in the middle and intersect the
		// lower base.
		ray = new Ray(new Point3D(2, 0, 5), new Vector(0, 0, -1));
		result = cylinder.findIntersections(ray);
		assertEquals("Worng point", List.of(new Point3D(2, 0, 0)), result);

		// **** Group: Ray is orthogonal to the axis
		// TC31: Ray's starts before cylinder and intersect (2 point)
		ray = new Ray(new Point3D(0, 0, 5), new Vector(1, 0, 0));
		result = cylinder.findIntersections(ray);
		assertEquals("Wrong number of points", 2, result.size());
		if (result.get(0).getX().get() > result.get(1).getX().get())
			result = List.of(result.get(1), result.get(0));
		assertEquals("Worng points", List.of(new Point3D(1, 0, 5), new Point3D(3, 0, 5)), result);
		// TC32 Ray's starts before and above cylinder (0 points)
		ray = new Ray(new Point3D(0, 0, 11), new Vector(1, 0, 0));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);
		// TC33: Ray's starts before and going through the top (0 points)
		ray = new Ray(new Point3D(0, 0, 10), new Vector(1, 0, 0));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);
		// TC34: Ray's starts at the cylinder center (0 points)
		ray = new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);
		// TC35: Ray's starts at the cylinder center (0 points)
		ray = new Ray(new Point3D(2, 0, 0), new Vector(0, 1, 0));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);

		// **** Group: special cases
		// TC41: Ray's tangent to cylinder top (0 points)
		ray = new Ray(new Point3D(1, 4, 10), new Vector(0, -1, 0));
		result = cylinder.findIntersections(ray);
		assertNull("should be null", result);

	}
}
