package unittests;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class BoundingBoxTest {
	@Test
	public void create1000Spheres()  {
		Random rand = new Random();
		final int NUM = 10, MOVE = 50;
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(1000, 1000, -3000), new Vector(0, -1, 0), new Vector(0, 0, 1)));
		scene.setBackground(new Color(0, 0, 0));
		scene.setAmbientLight(new AmbientLight(new Color(15, 15, 15), 1.0));
		Geometries geometries = new Geometries();

		for (int i = 1; i <= NUM; ++i)
			for (int j = 1; j <= NUM; ++j)
				for (int k = 1; k <= NUM; ++k) {
					if (rand.nextInt() % 2 == 0)
						geometries.add(new Sphere(
								new Point3D(i * MOVE, j * MOVE, k * MOVE), 25, new Color(Math.abs(rand.nextInt() % 255),
										Math.abs(rand.nextInt() % 255), Math.abs(rand.nextInt() % 255)),
								new Material(0.7, 0.3, 45)));
				}
		scene.addGeometries(geometries);
		ImageWriter imageWriter = new ImageWriter("Spheres", 500, 500, 500, 500);
		Render render = new Render(imageWriter, scene).setDebugPrint().setMultithreading(3);
		render.renderImage();
		render.writeToImage();
	}

}