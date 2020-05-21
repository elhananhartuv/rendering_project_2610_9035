package renderer;

import java.util.List;
import primitives.*;
import elements.*;
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
				// there is intersections points-calculate the closest point to camera and the
				// color of the point and write to pixel.
				GeoPoint closestPoint = findClosestIntersection(ray);
				imageWriter.writePixel(j, i,
						closestPoint == null ? background.getColor() : calcColor(closestPoint, ray).getColor());
			}
		}
	}

	/**
	 * calculate the color intensity in the specific point that function get.
	 * 
	 * @param gPoint the point to calculate the color.
	 * @return the color of the point.
	 */
	private Color calcColor(GeoPoint geopoint, Ray inRay) {
		return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(scene.getAmbientLight().getIntensity());
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
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param geoPoint
	 * @param inRay
	 * @param level
	 * @param k
	 * @return
	 */
	private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
		Color color = geoPoint.geometry.getEmission();
		Vector v = geoPoint.point.subtract(scene.getCamera().getP0()).normalize();
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);
		Material material = geoPoint.geometry.getMatrial();
		int nShininess = material.getNShininess();
		double kD = material.getKd();
		double kS = material.getKs();
		double nV = alignZero(n.dotProduct(v));
		Color iP;
		for (LightSource lightSource : scene.getLights()) {
			Vector l = lightSource.getL(geoPoint.point);
			double nL = alignZero(n.dotProduct(l));
			if (nL > 0 && nV > 0 || nL < 0 && nV < 0) {// same sign(case zero not relevant)
				double ktr = transparency(lightSource, l, n, geoPoint);
				if (ktr * k > MIN_CALC_COLOR_K) {
					iP = lightSource.getIntensity(geoPoint.point).scale(ktr);
					color = color.add(calcDiffuse(kD, nL, iP), calcSpecular(kS, l, n, v, nL, nShininess, iP));
				}
			}
		}
		if (level == 1)
			return Color.BLACK;
		double kr = material.getKr(), kkr = k * kr;
		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(n, geoPoint.point, inRay);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null) {
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
			}
		}
		double kt = material.getKt(), kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(geoPoint.point, n, inRay);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null) {
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
			}
		}
		return color;
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
	 * check if point should be shaded
	 * 
	 * @param light - the light source
	 * @param l     - the ray from the light source
	 * @param n     - the normal
	 * @param gp    - the point on the geometry
	 * @return true if there no shadow, else false
	 */
	/*private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector lightDirection = l.scale(-1);// from point to light source
		// Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		// Point3D point = gp.point.add(delta);
		// Ray lighRay = new Ray(point, lightDirection);
		Ray lighRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersection = scene.getGeometries().findIntersections(lighRay);
		if (intersection == null)
			return true;
		double lightDistance = light.getDistance(gp.point);
		for (GeoPoint gPoint : intersection) {
			if (gPoint.geometry.getMatrial().getKt() == 0
					&& alignZero(gPoint.point.distance(gp.point) - lightDistance) <= 0)
				return false;
		}
		return true;
	}
*/
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param n
	 * @param point
	 * @param inRay
	 * @return
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector r;
		Vector v = inRay.getDirection();
		double nV = alignZero(n.dotProduct(v));
		Vector epsVector = n.scale(nV > 0 ? -DELTA : DELTA);
		try {
			r = v.subtract(n.scale(nV * 2));
		} catch (IllegalArgumentException ex) {
			return null;///////////////////
		}
		// return new Ray(point.add(epsVector), r);
		return new Ray(point, r, n.scale(-1));
	}

	/**
	 * 
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param point
	 * @param n
	 * @param inRay
	 * @return
	 */
	private Ray constructRefractedRay(Point3D point, Vector n, Ray inRay) {
		Vector v = inRay.getDirection();
		double nV = alignZero(n.dotProduct(v));
		Vector epsVector = n.scale(nV > 0 ? DELTA : -DELTA);
		return new Ray(point.add(epsVector), v);
		/// return new Ray
	}

	/**
	 * calculate the closest point to the head ray from the intersections points
	 * with the ray. if there is no intersection return null.
	 * 
	 * @param ray
	 * @return the closest point to the head ray.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		List<GeoPoint> intersections = scene.getGeometries().findIntersections(ray);
		if (intersections == null)
			return null;
		GeoPoint result = null;// if there is no points in the list the result will stay null.
		double minDistance = Double.MAX_VALUE;
		Point3D p0 = ray.getP0();
		double distance;
		for (GeoPoint geo : intersections) {
			// distance from the head ray.
			distance = p0.distance(geo.point);
			if (distance < minDistance) {
				minDistance = distance;
				result = geo;
			}
		}
		return result;// closest point.
	}

	private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector lightDirection = l.scale(-1);// from point to light source
		// Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		// Point3D point = gp.point.add(delta);
		// Ray lighRay = new Ray(point, lightDirection);
		Ray lighRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersection = scene.getGeometries().findIntersections(lighRay);
		if (intersection == null)
			return 1.0;
		double lightDistance = light.getDistance(gp.point);
		double ktr = 1.0;
		for (GeoPoint gPoint : intersection) {
			if (/*
				 * gPoint.geometry.getMatrial().getKt() == 0 &&
				 */ alignZero(gPoint.point.distance(gp.point) - lightDistance) <= 0) {
				ktr *= gPoint.geometry.getMatrial().getKt();
				if (ktr < MIN_CALC_COLOR_K)
					return 0;
			}
		}
		return ktr;
	}
}
