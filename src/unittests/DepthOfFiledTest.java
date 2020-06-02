package unittests;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Unit tests for Depth of filed attribute.
 * 
 * @author E&Y
 *
 */
public class DepthOfFiledTest {

	@Test
	public void DofTest1() {
		Scene scene = new Scene("DOF");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1), 600, 5, 100));
		scene.setDistance(400);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Sphere(new Point3D(-550, 50, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-200, -50, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-150, -90, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, 0, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(200, -150, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(300, -300, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(1000, -350, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(500, -400, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)));

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("DepthOfFiled", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void DofTest2() {
		Scene scene = new Scene("DOF");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1), 600, 5, 100));
		scene.setDistance(400);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Sphere(new Point3D(-550, 50, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-200, -50, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-150, -90, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, 0, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(200, -150, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(300, -300, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(1000, -350, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(500, -400, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-600, 80, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(2500, -400, -400), 100, new Color(127, 127, 127),
						new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, -50, -100), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0))
		// new Plane(new Point3D(0,0,-20), new Vector(0,0,1),new
		// Color(java.awt.Color.yellow), new Material(0.4, 0.3, 100, 0, 0))
		);

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("DepthOfFiled2", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
}
