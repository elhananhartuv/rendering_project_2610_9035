/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Vector;

/**
 * Unit tests for primitives.Triangle class
 * @author USER
 *
 */
public class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		
		//create 3 Point and make the Triangle
	    Point3D p0=new Point3D(1,0,0);
	    Point3D p1=new Point3D(0,1,0);
		Point3D p2=new Point3D(0,0,1);
		Triangle triangle=new Triangle(p0, p1, p2);
		// ============ Equivalence Partitions Tests ==============
		assertEquals("Wrong normal to triangle", new Vector(1,1,1).normalize(),triangle.getNormal(p1));//check if the real normal are equal to the function getNormal
	}

}
