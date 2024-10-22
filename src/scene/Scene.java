package scene;

import elements.AmbientLight;
import primitives.*;
import geometries.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static java.util.Collections.sort;
import static java.lang.Double.compare;
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
	private boolean boundingBox = false;

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
	 * set bounding box
	 * 
	 * @param flag boolean variable
	 * @return this object
	 */
	public Scene setBoundingBox(boolean flag) {
		boundingBox = flag;
		return this;
	}

	/**
	 * get bounding box
	 * 
	 * @param flag boolean variable
	 * @return boundingBox
	 */
	public boolean getBoundingBox() {
		return boundingBox;
	}

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
	 * get lights function-return all the lights sources in the scene.
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
	 * the function add geometry shape to scene.
	 * 
	 * @param geometries shape to add.
	 */
	public void addGeometries(Geometries geometries) {
		if (geometries == null) {
			return;
		}
		List<Intersectable> list = geometries.getGeometries();
		for (Intersectable geo : list) {
			this.geometries.add(geo);
		}
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

	/**
	 * The function create Hierarchy tree.
	 */
	public void setHierarchyTree() {
		List<Intersectable> regularObjects = new LinkedList();
		List<Intersectable> infiniteObjects = new LinkedList();
		for (Intersectable geo : geometries.getGeometries()) {
			if (geo.infiniteObject)
				infiniteObjects.add(geo);
			else
				regularObjects.add(geo);
		}
		geometries = buildTree(regularObjects);
		// add the infinite objects to scene
		for (Intersectable geo : infiniteObjects) {
			geometries.add(geo);
		}
	}

	/**
	 * tail recursive function that build Hierarchy tree (binary tree). the tree is
	 * building from bottom to top. each two geometries that closed each other will
	 * be in one node
	 * 
	 * @param geoWithBox-all the geometries in our scene
	 * @return Geometries - all the geometries in our scene sorted in tree Hierarchy
	 */
	private Geometries buildTree(List<Intersectable> geoWithBox) {
		// stop condition -the element in the list is the root of the tree.
		if (geoWithBox.size() == 1) {
			return (Geometries) geoWithBox.get(0);
		}
		Geometries res = new Geometries();
		Geometries temp = new Geometries();
		Intersectable geo, g;
		int size = geoWithBox.size() - 1;
		while (size > 0) {
			g = geoWithBox.get(size);
			geoWithBox.remove(g);
			geo = closestGeometry(g, geoWithBox);
			geoWithBox.remove(geo);
			temp.add(g, geo);
			res.add(temp);
			temp = new Geometries();
			size -= 2;// we delete two elements from the list
		}
		// if there is more objects in list in case that list is odd.
		if (geoWithBox.size() != 0)
			res.add(geoWithBox.get(0));
		return buildTree(res.getGeometries());// recursive call.
	}

	/**
	 * The function calculate the closest geometry to the shape. if there is one
	 * shape in the list geometries, return the one shape that left
	 * 
	 * @param g          the geometry that we search the closest geometry
	 * @param geometries all the geometries shape
	 * @return closest geometry to g.
	 */
	private Intersectable closestGeometry(Intersectable g, List<Intersectable> geometries) {
		if (geometries.size() == 0) {
			return g;
		}
		double mindistance = Double.POSITIVE_INFINITY;
		double curentDistance;
		Intersectable closeGeo = new Geometries();
		for (Intersectable geo : geometries) {
			curentDistance = g.getCenterBox().distanceSquared(geo.getCenterBox());
			if (curentDistance < mindistance) {
				mindistance = curentDistance;
				closeGeo = geo;
			}
		}
		return closeGeo;
	}
}
