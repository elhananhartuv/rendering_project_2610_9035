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
	 * create the tree.
	 */
	public void makeTree() {
//		List<Intersectable> ourGeo = ((Geometries) geometries.getGeometries().get(0)).getGeometries();
//		int size = ourGeo.size();
//		List<Intersectable> temp1 = new LinkedList();
//		List<Intersectable> temp2 = new LinkedList();
//		List<Intersectable> temp3 = new LinkedList();
//		List<Intersectable> temp4 = new LinkedList();
//		List<Intersectable> temp5 = new LinkedList();
//		List<Intersectable> temp6 = new LinkedList();
//		List<Intersectable> temp7 = new LinkedList();
//		List<Intersectable> temp8 = new LinkedList();
//
//		for (Intersectable geo : ourGeo) {
//			if (geo.getMinX() >= 0 && geo.getMinY() >= 0 && geo.getMinZ() >= 0) {
//				temp1.add(geo);
//			}
//			else if (geo.getMinX() >= 0 && geo.getMinY() >= 0 && geo.getMinZ() <= 0) {
//				temp2.add(geo);
//			}
//			else if (geo.getMinX() >= 0 && geo.getMinY() <= 0 && geo.getMinZ() >= 0) {
//				temp3.add(geo);
//			}
//			else if (geo.getMinX() >= 0 && geo.getMinY() <= 0 && geo.getMinZ() <= 0) {
//				temp4.add(geo);
//			}
//			else if (geo.getMinX() <= 0 && geo.getMinY() >=0 && geo.getMinZ() >= 0) {
//				temp5.add(geo);
//			}
//			else if (geo.getMinX() <= 0 && geo.getMinY() >= 0 && geo.getMinZ() <= 0) {
//				temp6.add(geo);
//			}
//			else if (geo.getMinX() <= 0 && geo.getMinY() <= 0 && geo.getMinZ() >= 0) {
//				temp7.add(geo);
//			}
//			else if (geo.getMinX() <= 0 && geo.getMinY() <= 0 && geo.getMinZ() <= 0) {
//				temp8.add(geo);
//			}
//		}
//
//		int size1 = temp1.size();
//		int size2 = temp2.size();
//		int size3 = temp3.size();
//		int size4 = temp4.size();
//		int size5 = temp5.size();
//		int size6 = temp6.size();
//		int size7 = temp7.size();
//		int size8 = temp8.size();
//		Geometries t1 = (Geometries) setTreeHirarcia(sortGeometries(temp1));
//		Geometries t2 = (Geometries) setTreeHirarcia(sortGeometries(temp2));
//		Geometries t3 = (Geometries) setTreeHirarcia(sortGeometries(temp3));
//		Geometries t4 = (Geometries) setTreeHirarcia(sortGeometries(temp4));
//		Geometries t5 = (Geometries) setTreeHirarcia(sortGeometries(temp5));
//		Geometries t6 = (Geometries) setTreeHirarcia(sortGeometries(temp6));
//		Geometries t7 = (Geometries) setTreeHirarcia(sortGeometries(temp7));
//		Geometries t8 = (Geometries) setTreeHirarcia(sortGeometries(temp8));
//
//		Geometries positiveXandY = new Geometries(t1, t2);
//		Geometries positiveX = new Geometries(t3, t4);
//		Geometries negativeX = new Geometries(t5, t6);
//		Geometries negativeXandY = new Geometries(t7, t8);
//		Geometries root = new Geometries(positiveXandY, positiveX, negativeX, negativeXandY);
//		geometries = root;

		// List<Intersectable> ourGeo = ((Geometries)
		// geometries.getGeometries().get(0)).getGeometries();
		List<Intersectable> ourGeo = geometries.getGeometries();
		int size = ourGeo.size();
		geometries = buildTree(ourGeo);
	}

	/**
	 * this function put the geometries in boxes (as a laef) and sort them by
	 * location parameter
	 * 
	 * @return List<Node> of boxes (as a laef) after sort
	 */
	private List<Intersectable> sortGeometries(List<Intersectable> listToSort) {
		List<Intersectable> list = new LinkedList<>();
		for (Intersectable geo : listToSort) {
			Intersectable tempNode = new Geometries(geo);
			tempNode.location = (tempNode.getMaxX()) * (tempNode.getMaxX())
					+ (tempNode.getMaxY()) * (tempNode.getMaxY()) + (tempNode.getMaxZ()) * (tempNode.getMaxZ());
			list.add(tempNode);
		}
		sort(list, (u1, u2) -> compare(u1.location, u2.location));
		return list;
	}


	/**
	 * Recursive function
	 * 
	 * this function receive List<Node> and make tree by hierarchy the func puts
	 * them in pairs and sends them collection again in recursive
	 * 
	 * @param sortedList the geometries in sort
	 * @return sortedList.get(0) ==> the head
	 */
	protected Intersectable setTreeHirarcia(List<Intersectable> sortedList) {
		// stop condition
		if (sortedList.size() == 1)
			return sortedList.get(0);

		// odd size so add 1 (null)
		List<Intersectable> list = new LinkedList<>();

		Iterator<Intersectable> it = sortedList.iterator();
		while (it.hasNext())
			list.add(new Geometries(it.next(), it.hasNext() ? it.next() : null));
		return setTreeHirarcia(list);
	}

	/**
	 * 
	 * @param geoWithBox
	 * @return
	 */
	private Geometries buildTree(List<Intersectable> geoWithBox) {
		if (geoWithBox.size() == 1) {
			return (Geometries) geoWithBox.get(0);
		}
		Geometries res = new Geometries();
		Geometries temp = new Geometries();
		Intersectable geo;
		Intersectable g;
		int size = geoWithBox.size() - 1;
		while (size > 0) {
			g = geoWithBox.get(size);
			geoWithBox.remove(g);
			geo = closestGeometry(g, geoWithBox);
			geoWithBox.remove(geo);
			temp.add(g, geo);
			res.add(temp);
			temp = new Geometries();
			size -= 2;
		}
		if (geoWithBox.size() != 0)
			res.add(geoWithBox.get(0));
		return buildTree(res.getGeometries());
	}

	/**
	 * the function calculate the closest geometry to the shape.
	 * 
	 * @param g          the geometry that we search the closest geometry
	 * @param geometries all the geometries shape
	 * @return closest geometry.
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
