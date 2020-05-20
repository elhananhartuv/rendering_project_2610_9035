package renderer;

import java.util.List;
import primitives.*;
import elements.*;
import geometries.*;
import scene.*;
import static geometries.Intersectable.GeoPoint;
import static primitives.Util.*;

/**
 * Renderer class is responsible to create the buffer image from the geometries
 * in the scene, and Prepare the photo to write it on jpeg picture.
 * 
 * @author E&Y
 *
 */
public class Render {
	private ImageWriter imageWriter;
	private Scene scene;

	/**
	 * constant for moving rays size for shading rays, transparency and reflection
	 */
	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;

	// ***************** Constructor ******************** //

	/**
	 * ctor for render class. get two parameters imageWriter and scene.
	 * 
	 * @param imageWriter to create the image.
	 * @param scene       create the image from the scene.
	 */
	public Render(ImageWriter imageWriter, Scene scene) {
		this.imageWriter = imageWriter;
		this.scene = scene;
	}

	// ***************** Getters/Setters ******************** //

	/**
	 * get function
	 * 
	 * @return imageWriter
	 */
	public ImageWriter getImageWriter() {
		return imageWriter;
	}

	/**
	 * get function
	 * 
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}

	// ***************** Operations ******************** //

	/**
	 * create the buffer image by the geometries in the scene.
	 * 
	 */
	public void renderImage() {
		// java.awt.Color background = scene.getBackground().getColor();
		Color background = scene.getBackground();
		Camera camera = scene.getCamera();
		Intersectable geometries = scene.getGeometries();

		// the width and height of the view plane.
		int width = (int) imageWriter.getWidth();
		int height = (int) imageWriter.getHeight();

		// the number of pixels in the image
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy();
		// ray from camera through the pixel.
		Ray ray;
		for (int i = 0; i < Ny; i++) {
			for (int j = 0; j < Nx; j++) {
				// get the ray from camera through the pixel i,j in the view plane.
				ray = camera.constructRayThroughPixel(Nx, Ny, j, i, scene.getDistance(), width, height);
				// get the intersection points with the geometries shape.
				List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);

				if (intersectionPoints == null) {
					// if there is no intersection point write to pixel the background color.
					imageWriter.writePixel(j, i, background.getColor());

				} else {
					// there is intersections points-calculate the closest point to camera and the
					// color of the point and write to pixel.
					GeoPoint closestPoint = getClosestPoint(intersectionPoints);
					imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
				}
			}
		}
	}

	/**
	 * calculate the color intensity in the specific point that function get.
	 * 
	 * @param gPoint the point to calculate the color.
	 * @return the color of the point.
	 */
	private Color calcColor(GeoPoint gPoint) {
		Color color = scene.getAmbientLight().getIntensity();
		color = color.add(gPoint.geometry.getEmission());

		List<LightSource> lights = scene.getLights();// all the lights sources in our scene.
		// the vector from the point to camera -v
		Vector v = gPoint.point.subtract(scene.getCamera().getP0()).normalize();
		Vector n = gPoint.geometry.getNormal(gPoint.point);// the normal to the point.
		// Attenuation factors
		Material material = gPoint.geometry.getMatrial();
		int nShininess = material.getnShininess();
		double kD = material.getkD();
		double kS = material.getkS();
		Vector l;// the vector from the camera to the point.
		double nL;// dot product between the normal vector and l vector.
		Color iP; // the lighted source can be pointLight,directionLight,spotLight.
		double nV;
		if (lights != null) {
			for (LightSource lightSource : lights) {
				l = lightSource.getL(gPoint.point);
				nL = alignZero(n.dotProduct(l));
				nV = alignZero(n.dotProduct(v));
				if (nL > 0 && nV > 0 || nL < 0 && nV < 0) {
					if (unshaded(lightSource, l, n, gPoint)) {
						iP = lightSource.getIntensity(gPoint.point);
						color = color.add(calcDiffuse(kD, nL, iP), calcSpecular(kS, l, n, v, nL, nShininess, iP));
					}
				}
			}
		}
		return color;
	}

	/**
	 * calculate the Diffusive component
	 * 
	 * @param kd attenuation factor
	 * @param nl n.dotPrudct(l) n- normal vector, l- vector from the light source to
	 *           lighted point.
	 * @param ip is the light intensity in the point
	 * @return the color with diffuse
	 */
	private Color calcDiffuse(double kd, double nl, Color ip) {
		if (nl < 0)
			nl = -nl;// instead abs function
		return ip.scale(nl * kd);
	}

	/**
	 * calculate the Specular component
	 * 
	 * @param kS       attenuation factor
	 * @param l        the vector from the light source to lighted point.
	 * @param n        the normal vector in the light point
	 * @param v        the vector from the point to the camera
	 * @param nL
	 * @param nShinies
	 * @param light
	 * @return
	 */
	private Color calcSpecular(double kS, Vector l, Vector n, Vector v, double nL, int nShinies, Color light) {
		Vector r = l.subtract(n.scale(2 * nL)); // nl must not be zero!
		double vR = -alignZero(r.dotProduct(v));
		if (vR <= 0)
			return Color.BLACK;
		return light.scale(kS * Math.pow(vR, nShinies));
	}

	/**
	 * calculate the closest point to the camera. if the list that given are empty
	 * return null.
	 * 
	 * @param points List of points
	 * @return the closest point.
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> points) {
		GeoPoint result = null;// if there is no points in the list the result will stay null.
		double minDistance = Double.MAX_VALUE;
		Point3D p0 = this.scene.getCamera().getP0();
		double distance;
		for (GeoPoint geo : points) {
			// distance from the camera.
			distance = p0.distance(geo.point);
			if (distance < minDistance) {
				minDistance = distance;
				result = geo;
			}
		}
		return result;// closest point.
	}

	/**
	 * add a grid over the image but not overwrite the image
	 * 
	 * @param interval Interval of the grid.
	 * @param color    the color of the grid.
	 */
	public void printGrid(int interval, java.awt.Color color) {
		double Ny = imageWriter.getNx();
		double Nx = imageWriter.getNy();
		// make the grid by interval.
		for (int j = 0; j < Nx; j++)
			for (int i = 0; i < Ny; i++)
				if (j % interval == 0 || i % interval == 0)
					imageWriter.writePixel(i, j, color);
	}

	/**
	 * the function call to writeToImage in imageWriter. Function writeToImage
	 * produces unoptimized jpeg file of the image according to pixel color matrix
	 * in the directory of the project
	 */
	public void writeToImage() {
		this.imageWriter.writeToImage();
	}

	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param l
	 * @param n
	 * @param gp
	 * @return
	 */
	private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector lightDirection = l.scale(-1);// from point to light source
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Point3D point = gp.point.add(delta);
		Ray lighRay = new Ray(point, lightDirection);
		List<GeoPoint> intersection = scene.getGeometries().findIntersections(lighRay);
		if (intersection == null)
			return true;
		double lightDistance = light.getDistance(gp.point);
		for (GeoPoint gPoint : intersection) {
			if (alignZero(gPoint.point.distance(gp.point) - lightDistance) <= 0)
				return false;
		}
		return true;
	}
}
