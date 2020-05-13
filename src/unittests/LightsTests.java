package unittests;

import org.junit.Test;
import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering abasic image
 * 
 * @author Dan
 */
public class LightsTests {

	/**
	 * Produce a picture of a sphere lighted by a directional light
	 */
	@Test
	public void sphereDirectional() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Point3D(0, 0, 50), 50, new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

		scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));

		ImageWriter imageWriter = new ImageWriter("sphereDirectional", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a point light
	 */
	@Test
	public void spherePoint() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Point3D(0, 0, 50), 50, new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

		scene.addLights(new PointLight(new Color(500, 300, 0), new Point3D(-50, 50, -50), 1, 0.00001, 0.000001));

		ImageWriter imageWriter = new ImageWriter("spherePoint", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void sphereSpot() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Point3D(0, 0, 50), 50, new Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 100)));

		scene.addLights(new SpotLight(new Vector(1, -1, 2), new Color(500, 300, 0), new Point3D(-50, 50, -50), 1,
				0.00001, 0.00000001));

		ImageWriter imageWriter = new ImageWriter("sphereSpot", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a directional light
	 */
	@Test
	public void trianglesDirectional() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.8, 0.2, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.8, 0.2, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, 1)));

		ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a point light
	 */
	@Test
	public void trianglesPoint() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new PointLight(new Color(500, 250, 250), new Point3D(10, 10, 130), 1, 0.0005, 0.0005));

		ImageWriter imageWriter = new ImageWriter("trianglesPoint", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light
	 */
	@Test
	public void trianglesSpot() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));

		scene.addLights(new SpotLight(new Vector(-2, 2, 1), new Color(500, 250, 250), new Point3D(10, 10, 130), 1,
				0.0001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("trianglesSpot", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by all lights
	 */
	@Test
	public void sphereAllLights() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));
		scene.addGeometries(
				new Sphere(new Point3D(0, 0, 50), 50, new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 100)));
		scene.addLights(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)),
				new PointLight(new Color(500, 250, 250), new Point3D(55, 53, 100), 1, 0.0005, 0.0005),
				new SpotLight(new Vector(-2, 2, 1), new Color(500, 250, 250), new Point3D(10, 10, 130), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("sphereAllLights", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by all lights
	 */
	@Test
	public void trianglesAllLights() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0.8));
		scene.addGeometries(
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(150, 150, 150), new Point3D(75, -75, 150)),
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 300), new Point3D(-150, 150, 150),
						new Point3D(-70, -70, 50), new Point3D(75, -75, 150)));
		scene.addLights(new DirectionalLight(new Color(350, 175, 175), new Vector(0, -1, 1)),
				new PointLight(new Color(255, 100, 100), new Point3D(-100, 100, 70), 1, 0.0001, 0.0001),
				new SpotLight(new Vector(2, -2, 1), new Color(500, 250, 250), new Point3D(10, 10, 130), 1, 0, 0));

		ImageWriter imageWriter = new ImageWriter("trianglesAllLights", 200, 200, 500, 500);
		Render render = new Render(imageWriter, scene);
		render.renderImage();
		render.writeToImage();
	}
}
