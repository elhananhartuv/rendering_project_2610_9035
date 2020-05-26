package unittests;

import org.junit.Test;
import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import elements.SpotLight;
import geometries.Cylinder;
import geometries.Plane;
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

public class CreateImage {

	@Test
	public void createImageTest() {
		Scene scene = new Scene("createImageTest");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Triangle(new Color(java.awt.Color.black), new Point3D(0, -20, 0), new Point3D(20, 10, 0),
						new Point3D(-20, 10, 0)),
				new Triangle(new Color(java.awt.Color.black), new Point3D(0, 20, 0), new Point3D(20, -10, 0),
						new Point3D(-20, -10, 0)),
				new Sphere(new Point3D(0, 0, 0), 50, new Color(java.awt.Color.PINK),
						new Material(0.4, 0.3, 100, 0.3, 0)));

		scene.addLights(new DirectionalLight(new Color(300, 500, 0), new Vector(1, -1, 1)));

		ImageWriter imageWriter = new ImageWriter("createImageTest", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void ourImage() {
		Scene scene = new Scene("createImage");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Sphere(new Point3D(0, -50, 0), 10, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-50, -25, 5), 10, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(50, -25, 5), 10, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(-50, 25, 5), 10, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(50, 25, 5), 10, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, 50, 0), 10, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),

				new Cylinder(new Ray(new Point3D(0, -50, 0), new Vector(-50, 75, 5)), 2, 100, new Color(182, 207, 189),
						new Material(0.4, 0.3, 100, 0, 0)),
				new Cylinder(new Ray(new Point3D(0, -50, 0), new Vector(50, 75, 5)), 2, 100, new Color(182, 207, 189),
						new Material(0.4, 0.3, 100, 0, 0)),
				new Cylinder(new Ray(new Point3D(-50, -25, 10), new Vector(100, 0, 0)), 2, 100,
						new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0, 0)),
				new Cylinder(new Ray(new Point3D(-50, 25, 10), new Vector(100, 0, 0)), 2, 100, new Color(182, 207, 189),
						new Material(0.4, 0.3, 100, 0, 0)),
				new Cylinder(new Ray(new Point3D(0, 50, 0), new Vector(-50, -75, 5)), 2, 100,
						new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0, 0)),
				new Cylinder(new Ray(new Point3D(0, 50, 0), new Vector(50, -75, 5)), 2, 100,
						new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0, 0)),
				new Cylinder(new Ray(new Point3D(0, -150, 0), new Vector(0, 1, 0)), 2, 50,
						new Color(java.awt.Color.WHITE), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, -110, 0), 2, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0, 0)),
				new Sphere(new Point3D(0, -100, 0), 5, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 1, 0)),
				new Sphere(new Point3D(0, -100, 0), 9, new Color(127, 127, 127), new Material(0.4, 0.3, 100, 0.3, 0)),

				new Triangle(new Color(116, 122, 99), new Material(0.8, 0.2, 300, 0, 0.3), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.8, 0.2, 300, 0, 0.1), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)));

		scene.addLights(new DirectionalLight(new Color(48, 170, 176), new Vector(0, -1, 2)),
				new PointLight(new Color(103, 110, 13), new Point3D(0, -100, 0), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("magenDavid", 250, 250, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
}
