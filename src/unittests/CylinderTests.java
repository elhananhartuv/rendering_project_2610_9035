package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.Cylinder;
import primitives.*;
/**
 * Unit tests for primitives.Cylinder class
 * @author USER
 *
 */
public class CylinderTests {

	@Test
	public void testGetNormal() {
		Ray ray=new Ray(new Point3D(0,0,0), new Vector(0,0,1));
		Cylinder cylinder=new Cylinder(ray,4, 4);//the height is 4 and the radius is 4
		//the cylinder equation is x^2+y^2=16
		Point3D point1=new Point3D(2,0,3);//regular point on the cylinder
		Point3D point2=new Point3D(4,0,0);//The point is on the lower base
		Point3D point3=new Point3D(4,0,4);//The point is on the top base
		Vector normalExp1=new Vector(1,0,0);
		Vector normalExp2=new Vector(0,0,-1);
		Vector normalExp3=new Vector(0,0,1);
		// ============ Equivalence Partitions Tests ==============
		 // =============== Boundary Values Tests ==================
		assertEquals("Wrong normal to cylinder",normalExp1,cylinder.getNormal(point1));
		// ============ Equivalence Partitions Tests ==============
		assertEquals("Wrong normal to cylinder",normalExp2,cylinder.getNormal(point2));
		// ============ Equivalence Partitions Tests ==============
		assertEquals("Wrong normal to cylinder",normalExp3,cylinder.getNormal(point3));
	}

}
