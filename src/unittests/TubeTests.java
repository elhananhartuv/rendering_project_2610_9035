/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;
import geometries.*;
/**
 *  Unit tests for primitives.Tube class
 * @author USER
 *
 */
public class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	public void testGetNormal() {

		Ray ray=new Ray(new Point3D(0,0,0), new Vector(0,1,0));//P0=(0,0,0) V=(0,1,0)
		Tube tube=new Tube(ray, 2.0);
		
		//the Point (2,8,0) on the tube the Tube equation is x^2+z^2=4
		Point3D point=new Point3D(2,8,0);
		Vector normalExp=new Vector(1,0,0);
	

		 // ============ Equivalence Partitions Tests ==============
	    assertEquals("Wrong normal to Tube", normalExp,tube.getNormal(point));
	    // =============== Boundary Values Tests ==================
	    assertEquals("Wrong normal to Tube", new Vector(1,0,0),tube.getNormal(new Point3D(1,0,0)));
	}

}
