package unittests;

import java.util.Random;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Unit tests for Depth of filed effect.
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
				new Sphere(new Point3D(-200, -50, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(-150, -90, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(0, 0, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(200, -150, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(300, -300, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.7, 100, 0, 0)),
				new Sphere(new Point3D(1000, -350, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(500, -400, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.7, 100, 0, 0)));

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("DepthOfFiled", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);

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
				new Sphere(new Point3D(-200, -50, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(-150, -90, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(0, 0, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(200, -150, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(300, -300, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(1000, -350, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(500, -400, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(-600, 80, 0), 30, new Color(127, 127, 127), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(3000, -400, -400), 100, new Color(127, 127, 127),
						new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, -50, -100), 30, new Color(127, 127, 127), new Material(0.4, 0.7, 100, 0, 0)));

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("DepthOfFiled2", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBoundigBox(true);
		scene.makeTree();
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void DofTest3() {
		Scene scene = new Scene("DOF");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1), 600, 5, 5));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Cylinder(new Ray(new Point3D(0, 50, 0), new Vector(0, 0, 1)), 5, 25,
						new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 1)),
				new Cylinder(new Ray(new Point3D(25, 50, 0), new Vector(0, 0, 1)), 5, 25,
						new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 1)),
				new Cylinder(new Ray(new Point3D(50, 50, 0), new Vector(0, 0, 1)), 5, 25,
						new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 1)));

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("DepthOfFiled3", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void DofTest4() {
		Scene scene = new Scene("DOF");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1), 600, 5, 100));
		scene.setDistance(400);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Sphere(new Point3D(-550, 50, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(-200, -50, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(-150, -90, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(0, 0, 0), 30, new Color(java.awt.Color.BLUE), new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(200, -150, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(300, -300, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(1000, -350, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(500, -400, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.9, 100, 0, 0)),
				new Sphere(new Point3D(-600, 80, 0), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.7, 100, 0, 0)),
				new Sphere(new Point3D(3000, -400, -400), 100, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.7, 100, 0, 0)),
				new Sphere(new Point3D(0, -50, -100), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.7, 100, 0, 0)),
				new Sphere(new Point3D(200, -150, 200), 30, new Color(java.awt.Color.BLUE),
						new Material(0.4, 0.7, 100, 0, 0)));

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("DepthOfFiled4___", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBoundigBox(true);
		//scene.makeTree();
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void DofTest5() {
		Scene scene = new Scene("DOF");
		Random rand = new Random();
		final int NUM = 10, MOVE = 2;
		scene.setCamera(new Camera(new Point3D(1000, 1000, -3000), new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.setDistance(400);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));
		Geometries geometries = new Geometries();
		for (int i = 1; i <= NUM; ++i)
			for (int j = 1; j <= NUM; ++j)
				for (int k = 1; k <= NUM; ++k) {
					if (rand.nextInt() % 2 == 0)
						geometries.add(new Sphere(
								new Point3D(i * MOVE, j * MOVE, k * MOVE), 1, new Color(Math.abs(rand.nextInt() % 255),
										Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
								new Material(0.7, 0.3, 45)));
				}
		scene.addGeometries(geometries);

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("TRY", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBoundigBox(true);

		render.renderImage();
		render.writeToImage();
	}
}
