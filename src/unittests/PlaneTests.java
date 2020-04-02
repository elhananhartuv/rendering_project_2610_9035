/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Plane;
import primitives. *;

/**
 * Unit tests for primitives.Plane class
 * @author USER
 *
 */
public class PlaneTests {

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormalPoint3D() {
		//create 3 Point and make the Polygon
		Point3D p0=new Point3D(1,0,0);
		Point3D p1=new Point3D(0,1,0);
		Point3D p2=new Point3D(0,0,1);
		Plane plane=new Plane(p0,p1,p2);	
		// ============ Equivalence Partitions Tests ==============
		assertEquals("Wrong normal to plane", new Vector(1,1,1).normalize(),plane.getNormal(p1));//check if the real normal are equal to the function getNormal
	}
}
