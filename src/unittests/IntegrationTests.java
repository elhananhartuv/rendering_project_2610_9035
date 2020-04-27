/**
 * mgbhmbhihuhojtvreytvryetcs thshtvrty
 */
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
		List<Point3D> intersections;
		int sum = 0;// how much intersection point we got with the sphere
		// check the pixels one by one
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("wrong result for intersection with sphere", 2, sum);

		// TC02: all rays intersect the sphere (18 points expected).
		camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
		sphere = new Sphere(new Point3D(0, 0, 2.5), 2.5);
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("all the rays hit the sphere", 18, sum);

		// TC03: all the ray hit the sphere except the corners.(10 intersections
		// expected)
		sphere = new Sphere(new Point3D(0, 0, 2), 2);
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("without corners", 10, sum);

		// TC04: the camera is inside the sphere. (9 points expected)
		sphere = new Sphere(new Point3D(0, 0, 1), 4);
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("camera inside", 9, sum);

		// TC05: sphere behind the camera 0 points.
		sphere = new Sphere(new Point3D(0, 0, -1), 0.5);
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = sphere.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("camera inside", 0, sum);
	}

	/**
	 * Test function for integration with plane
	 */
	@Test
	public void planeTest() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		Plane plane;// the position of the sphere will change according to the cases.

		// TC01: the plane parallel to the view plane expected 9 points.
		plane = new Plane(new Point3D(0, 0, 5), new Vector(0, 0, 1));

		List<Point3D> intersections;
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("the plane parallel to the view plane", 9, sum);

		// TC02: the plane not parallel, but all the rays intersect the plane
		plane = new Plane(new Point3D(0, 0, 1), new Vector(0, 1, 2));
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("not parallel but all the rays intersect the plane", 9, sum);

		// TC03:the plane not parallel to the view plane. the lower rays not intersect
		// the plane.
		plane = new Plane(new Point3D(0, 0, 5), new Vector(1, 1, 1));
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = plane.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("lower rays not intersect the plane", 6, sum);
	}

	/**
	 * Test function for integration with triangle
	 */
	@Test
	public void triangleTest() {
		Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		Triangle triangle;// the position of the triangle will change according to the cases.

		// TC01:only one intersection point.
		triangle = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
		List<Point3D> intersections;
		int sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = triangle.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("one intersection", 1, sum);

		// TC02: two intersection points with triangle.
		triangle = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
		sum = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				intersections = triangle.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3));
				if (intersections != null)
					sum += intersections.size();
			}
		}
		assertEquals("two intersection", 2, sum);
	}
}
