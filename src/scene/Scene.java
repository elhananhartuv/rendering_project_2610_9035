package scene;

import elements.AmbientLight;
import primitives.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import elements.*;

/**
 * The class scene represent scene in 3d, has geometries shapes light and camera
 * and connect between all these things to renderer.the class responsible to
 * background color and the ambient light.
 * 
 * @author E&Y
 */
public class Scene {
	private String name;
	private Color background;
	private AmbientLight ambientLight;
	private Geometries geometries;
	private Camera camera;
	private double distance;
	private List<LightSource> lights = new LinkedList<LightSource>();

	/**
	 * ctor for Scene that get only the name.
	 * 
	 * @param name the name of scene.
	 */
	public Scene(String name) {
		this.name = name;
		geometries = new Geometries();
	}

	// ***************** Getters/Setters ********************** //
	/**
	 * name getter
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * background getter
	 * 
	 * @return the background
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * ambientLight getter
	 * 
	 * @return the ambientLight
	 */
	public AmbientLight getAmbientLight() {
		return ambientLight;
	}

	/**
	 * geometries getter
	 * 
	 * @return the geometries
	 */
	public Geometries getGeometries() {
		return geometries;
	}

	/**
	 * camera getter
	 * 
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * distance getter
	 * 
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * background setter
	 * 
	 * @param color to update.
	 */
	public void setBackground(Color color) {
		this.background = color;
	}

	/**
	 * ambientLight setter
	 * 
	 * @param ambientLight to update.
	 */
	public void setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
	}

	/**
	 * camera setter
	 * 
	 * @param camera to update.
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	/**
	 * distance setter
	 * 
	 * @param distance to update.
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * get lights function
	 * 
	 * @return lights-list of lights
	 */
	public List<LightSource> getLights() {
		return lights;
	}

	// ***************** Operations ******************** //
	/**
	 * the function add geometry shape to scene.
	 * 
	 * @param geometries shape to add.
	 */
	public void addGeometries(Intersectable... geometries) {
		this.geometries.add(geometries);
	}

	/**
	 * the function add some lights sources to the scene.
	 * 
	 * @param lights list of lights to add.
	 */
	public void addLights(LightSource... lights) {
		for (LightSource light : lights) 
			this.lights.add(light);	
	}
}
