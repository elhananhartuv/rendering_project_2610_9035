//trying to push 
package unittests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.List;
import elements.Camera;
import primitives.*;
import geometries.*;

/**
 * unit tests for integration between geometries and camera rays.
 * 
 * @author E&Y
 */
public class IntegrationTests {
	/**
	 * Test function for integration with sphere. check the intersection points with
	 * the rays that goes through the view plane.
	 */
	@Test
	public void sphereTest() {
		Camera camera;// the position of the camera will be change according to the cases.
		Sphere sphere;// the position of the sphere will be change according to the cases.

		// TC01: only the ray through the central point of the view plane intersect the
		// sphere.
		camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		// the center point is(0,0,3) the radius is 1.
		sphere = new Sphere(new Point3D(0, 0, 3), 1d);

		// how much intersection point we got with the sphere check the pixels one by
		// one
		int result = rayIntegrationGeometry(sphere, camera);
		assertEquals("wrong result for intersection with sphere", 2, result);

		// TC02: all rays intersect the sphere (18 points expected).
		camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
		sphere = new Sphere(new Point3D(0, 0, 2.5), 2.5);
		result = rayIntegrationGeometry(sphere, camera);
		assertEquals("all the rays hit the sphere", 18, result);

		// TC03: all the ray hit the sphere except the corners.(10 intersections
		// expected)
		sphere = new Sphere(new Point3D(0, 0, 2), 2);
		result = rayIntegrationGeometry(sphere, camera);
		assertEquals("without corners", 10, result);

		// TC04: the camera is inside the sphere. (9 points expected)
		sphere = new Sphere(new Point3D(0, 0, 1), 4);
		result = rayIntegrationGeometry(sphere, camera);
		assertEquals("camera inside", 9, result);

		// TC05: sphere behind the camera 0 points.
		sphere = new Sphere(new Point3D(0, 0, -1), 0.5);
		result = rayIntegrationGeometry(sphere, camera);
		assertEquals("camera inside", 0, result);
	}

	/**
	 * Test function for integration with plane
	 */
	@Test
	public void planeTest() {
		// The camera is common to all tests
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		Plane plane;// the position of the plane will change according to the cases.

		// TC01: the plane parallel to the view plane expected 9 points.
		plane = new Plane(new Point3D(0, 0, 5), new Vector(0, 0, 1));
		int result = rayIntegrationGeometry(plane, camera);
		assertEquals("the plane parallel to the view plane", 9, result);

		// TC02: the plane not parallel, but all the rays intersect the plane
		plane = new Plane(new Point3D(0, 0, 1), new Vector(0, 1, 2));
		result = rayIntegrationGeometry(plane, camera);
		assertEquals("not parallel but all the rays intersect the plane", 9, result);

		// TC03:the plane not parallel to the view plane. the lower rays not intersect
		// the plane.
		plane = new Plane(new Point3D(0, 0, 5), new Vector(1, 1, 1));
		result = rayIntegrationGeometry(plane, camera);
		assertEquals("lower rays not intersect the plane", 6, result);
	}

	/**
	 * Test function for integration with triangle
	 */
	@Test
	public void triangleTest() {
		// The camera is common to all tests
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		Triangle triangle;// the position of the triangle will change according to the cases.

		// TC01:only one intersection point.
		triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
		// call to the function that calculate the number of intersection points.
		int result = rayIntegrationGeometry(triangle, camera);
		assertEquals("one intersection", 1, result);

		// TC02: two intersection points with triangle.
		triangle = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
		result = rayIntegrationGeometry(triangle, camera);
		assertEquals("two intersection", 2, result);
	}

	/**
	 * The function calculate the intersections with the geometry. the function get
	 * specific geometry and camera and return the number of intersection that the
	 * rays from the camera intersect through the view plane the geometry.
	 * 
	 * @param geometry the shape that we check intersection with the ray through
	 *                 pixels.
	 * @param camera   the camera place, where the rays coming from.
	 * @return the number of intersection point with the geometry.
	 */
	private int rayIntegrationGeometry(Intersectable geometry, Camera camera) {
		List<Point3D> intersections;
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = geometry.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		return sum;
	}
}