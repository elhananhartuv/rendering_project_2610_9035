/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
