package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

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
}
