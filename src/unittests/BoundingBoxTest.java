package unittests;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Cylinder;
import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

/**
 * test for Bounding Volume Hierarchy
 * 
 * @author E&Y
 *
 */
public class BoundingBoxTest {

	@Test
	public void createSpheresHierarchy() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
		scene.setDistance(500);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		Random rand = new Random();
		final int NUM = 1000, MOVE = 50;
		Geometries a = new Geometries();
		Geometries b = new Geometries();
		Geometries c = new Geometries();
		Geometries d = new Geometries();
		Geometries left = new Geometries();
		Geometries right = new Geometries();
		Geometries root = new Geometries();
		for (int i = 1; i <= NUM; ++i) {
			a.add(new Sphere(
					new Point3D(i * 100 - 300, -MOVE * i, MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100, 0, 0)));
		}

		for (int i = NUM ; i > 1; i--) {
			b.add(new Sphere(
					new Point3D(i * 100, MOVE * i, MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.7, 0.3, 45)));
		}
		for (int i = 1; i <= NUM; ++i) {
			c.add(new Sphere(
					new Point3D(i * 100 - 300, -MOVE * i, -MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100, 0, 0)));
		}
		for (int i = NUM; i > 1; i--) {
			d.add(new Sphere(
					new Point3D(i * 100, MOVE * i, -MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.7, 0.3, 45)));
		}

		right.add(a, c);
		left.add(b, d);
		root.add(left, right);
		scene.addGeometries(root);
		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
		ImageWriter imageWriter = new ImageWriter("our_Bounding_Volume_Hierarchy", 500, 500, 1000, 1000);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBoundigBox(true);
		render.renderImage();
		render.writeToImage();
	}

   @Test
	public void createSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
		scene.setDistance(500);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		Random rand = new Random();
		final int NUM = 1000, MOVE = 50;

		Geometries geometries = new Geometries();

		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Sphere(
					new Point3D(i * 100 - 200, -MOVE * i, MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100, 0, 0)));
		}

		for (int i = NUM; i > 1; i--) {
			geometries.add(new Sphere(
					new Point3D(i * 100, MOVE * i, MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.7, 0.3, 45)));
		}
		for (int i = 1; i <= NUM; ++i) {
			geometries.add(new Sphere(
					new Point3D(i * 100 - 200, -MOVE * i, -MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.4, 0.7, 100, 0, 0)));
		}
		for (int i = NUM; i > 1; i--) {
			geometries.add(new Sphere(
					new Point3D(i * 100, MOVE * i, -MOVE * i / 2), 50, new Color(Math.abs(rand.nextInt() % 255),
							Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
					new Material(0.7, 0.3, 45)));
		}

		scene.addGeometries(geometries);
		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
	  
		ImageWriter imageWriter = new ImageWriter("withoutHierarchy", 500, 500, 1000, 1000);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBoundigBox(true);
		scene.makeTree();
		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void boomBoomBoom() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(-1000, 0, 0), new Vector(1, 0, 0), new Vector(0, 0, 1)));
		scene.setDistance(500);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		Random rand = new Random();
		final int NUM = 1000, MOVE = 50;
		Geometries geometries = new Geometries();
		Polygon polygone1 = new Polygon(new Color(java.awt.Color.green), new Material(0.7, 0.3, 45),
				new Point3D(-1100, 100, -50), new Point3D(-1100, 500, -50), new Point3D(10000, 500, 50),
				new Point3D(10000, 100, 50));
		Polygon polygone2 = new Polygon(new Color(java.awt.Color.green), new Material(0.7, 0.3, 45),
				new Point3D(-1100, -100, -50), new Point3D(-1100, -500, -50), new Point3D(10000, -500, 50),
				new Point3D(10000, -100, 50));
		geometries.add(polygone1, polygone2);

		for (int i = -1100, j = -50; i <= 1100 && j < 50; i += 50, j += 2) {
			geometries
					.add(new Polygon(new Color(java.awt.Color.white), new Material(0.7, 0.3, 45), new Point3D(i, 3, j),
							new Point3D(i, -3, j), new Point3D(i + 35, -3, j + 1), new Point3D(i + 35, 3, j + 1)));

		}

		for (int i = -1100, j = -50; i <= 10000 && j < 50; i += 100, j += 1.3) {
			geometries.add(
					new Cylinder(new Ray(new Point3D(i, 115, j), new Vector(0, 0, 1)), 5, 45, new Color(92, 63, 9),
							new Material(0.7, 0.3, 45)),
					new Cylinder(new Ray(new Point3D(i, -115, j), new Vector(0, 0, 1)), 5, 45, new Color(92, 63, 9),
							new Material(0.7, 0.3, 45))
			);
		}
		scene.addGeometries(geometries);
		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 0)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));
		ImageWriter imageWriter = new ImageWriter("boomBoomBoom", 500, 500, 500, 500);
		scene.makeTree();
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3).setBoundigBox(true);
		render.renderImage();
		render.writeToImage();
	}
}