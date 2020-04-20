/**
 * 
 */
package unittests;

import static primitives.Util.isZero;
import static org.junit.Assert.*;

import org.junit.Test;
import primitives.*;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Elhanan & Yedidya
 */
public class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		// test subtract
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);

		// ============ Equivalence Partitions Tests ==============a
		assertEquals("subtract returns wrong value", new Vector(3, 6, 9), v1.subtract(v2));
		// =============== Boundary Values Tests ==================
		try {
			v1.subtract(v1);
			fail("doesn't throw an exception for zero vector");
		} catch (Exception e) {

		}
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);

		// ============ Equivalence Partitions Tests ==============a
		assertEquals("add returns wrong value", new Vector(-1, -2, -3), v1.add(v2));

		// =============== Boundary Values Tests ==================
		try {
			v1.add(new Vector(-1, -2, -3));
			fail("doesn't throw an exception for zero vector");
		} catch (Exception e) {

		}

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		// test multiply vector by scalar
		Vector vec = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		assertEquals("Scale Vecotr return wrong Vector", vec.scale(2), new Vector(2, 4, 6));

		// =============== Boundary Values Tests ==================

		try {
			vec.scale(0);
			fail("doesn't throw an exception for zero vector");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		// test Dot-Product
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, -3, 2);

		// ============ Equivalence Partitions Tests ==============
		assertTrue("ERROR: dotProduct for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
		assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v3);

		// ============ Equivalence Partitions Tests ==============

		assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

		// Test cross-product result orthogonality to its operands
		assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
		assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

		// =============== Boundary Values Tests ==================
		// test zero vector from cross-product.
		try {
			v1.crossProduct(v2);
			fail("crossProduct() for parallel vectors does not throw an exception");
		} catch (Exception e) {
		}

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		// test length squared
		Vector v1 = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		assertTrue("Wrong length sqaured", isZero(v1.lengthSquared() - 14));
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		// test length squared
		Vector v1 = new Vector(0, 3, 4);

		// ============ Equivalence Partitions Tests ==============
		assertTrue("Wrong length sqaured", isZero(v1.length() - 5));
	}

	@Test
	public void testNormalize() {
		// test normalize vector
		Vector v = new Vector(1, 2, 3);
		Vector vCopy = new Vector(v);
		Vector vCopyNormalize = vCopy.normalize();

		// ============ Equivalence Partitions Tests ==============

		assertTrue("normalize() function creates a new vector", vCopy == vCopyNormalize);
		// TC02 check if normalize really normalize the vector
		assertEquals("Vactor not Normalized", 1, vCopyNormalize.length(), 0);

	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		// test normalized vector
		Vector v = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		Vector normalizedVector = v.normalized();
		assertFalse("normalizated() function does not create a new vector", v == normalizedVector);

	}

}
