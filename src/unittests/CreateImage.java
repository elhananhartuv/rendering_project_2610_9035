package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.PointLight;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Tube;
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
	public void createImage() {
		Scene scene = new Scene("createImageTest");
		scene.setCamera(new Camera(new Point3D(25, 25, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(132, 124, 65), 0));

		scene.addGeometries(
				new Sphere(new Point3D(0, 0, 0), 15, new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)),
				new Sphere(new Point3D(50, 0, 0), 15, new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)),
				new Sphere(new Point3D(25, 50, 0), 15, new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)),
				new Tube(new Ray(new Point3D(0, 0, 0), new Vector(new Point3D(-50, 0, 0))), 5,
						new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100)),
				new Tube(new Ray(new Point3D(0, 0, 0), new Vector(new Point3D(-25, -50, 0))), 5,
						new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100)),
				new Tube(new Ray(new Point3D(50, 0, 0), new Vector(new Point3D(-25, 50, 0))), 5,
						new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100)));

		scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

		ImageWriter imageWriter = new ImageWriter("ourImage", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

}
