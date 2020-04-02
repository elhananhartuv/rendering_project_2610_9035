/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import geometries.*;
import primitives.Point3D;
import primitives.Vector;
/**
 * Unit tests for primitives.Sphere class
 * @author USER
 *
 */
public class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		Point3D center=new Point3D(0,0,0);
		Sphere sphere=new Sphere(center, 2.0);
		//the sphere equation is x^2+y^2+z^2=4
		//the Point (2,0,0) on the sphere
		//now we need to check if the function get normal return the right result
		// ============ Equivalence Partitions Tests ==============
		assertEquals("Wrong normal to sphere", new Vector(1,0,0),sphere.getNormal(new Point3D(2,0,0)));//check if the real normal are equal to the function getNormal
	
		
	}
}
