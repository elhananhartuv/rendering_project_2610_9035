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
	private int _threads = 1;
	private final int SPARE_THREADS = 2;
	private boolean _print = false;

	/**
	 * max deep of recursion in calcColor function
	 */
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

	/**
	 * Pixel is an internal helper class whose objects are associated with a Render
	 * object that they are generated in scope of. It is used for multithreading in
	 * the Renderer and for follow up its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each
	 * thread.
	 * 
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long _maxRows = 0;
		private long _maxCols = 0;
		private long _pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long _counter = 0;
		private int _percents = 0;
		private long _nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * 
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			_maxRows = maxRows;
			_maxCols = maxCols;
			_pixels = maxRows * maxCols;
			_nextCounter = _pixels / 100;
			if (Render.this._print)
				System.out.printf("\r %02d%%", _percents);
		}

		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {
		}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object
		 * - this function is critical section for all the threads, and main Pixel
		 * object data is the shared data of this critical section.<br/>
		 * The function provides next pixel number each call.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return the progress percentage for follow up: if it is 0 - nothing to print,
		 *         if it is -1 - the task is finished, any other value - the progress
		 *         percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++_counter;
			if (col < _maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			++row;
			if (row < _maxRows) {
				col = 0;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percents = nextP(target);
			if (percents > 0)
				if (Render.this._print)
					System.out.printf("\r %02d%%", percents);
			if (percents >= 0)
				return true;
			if (Render.this._print)
				System.out.printf("\r %02d%%", 100);
			return false;
		}
	}

	// ***************** Getters/Setters ******************** //

	/**
	 * imageWriter getter
	 * 
	 * @return imageWriter
	 */
	public ImageWriter getImageWriter() {
		return imageWriter;
	}

	/**
	 * scene getter
	 * 
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}

	// ***************** Operations ******************** //

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 */
	public void renderImage() {
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		final double dist = scene.getDistance();
		final double width = imageWriter.getWidth();
		final double height = imageWriter.getHeight();
		final Camera camera = scene.getCamera();
		final Color backgroundColor = scene.getBackground();
		final Pixel thePixel = new Pixel(nY, nX);

		// Generate threads
		Thread[] threads = new Thread[_threads];
		for (int i = _threads - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel)) {
					List<Ray> rays = camera.constructBeamOfRaysThroughPixels(nX, nY, pixel.col, pixel.row, //
							dist, width, height);
					Color sumColor = Color.BLACK;// initialized to RGB vector (0,0,0)
					for (Ray ray : rays) {
						GeoPoint closestPoint = findClosestIntersection(ray);
						Color color = closestPoint == null ? backgroundColor : calcColor(closestPoint, ray);
						sumColor = sumColor.add(color);
					}

					if (rays.size() > 1)
						sumColor = sumColor.reduce(rays.size());
					imageWriter.writePixel(pixel.col, pixel.row, sumColor.getColor());
				}
			});
		}

		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Wait for all threads to finish
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}
		if (_print)
			System.out.printf("\r100%%\n");
	}

	/**
	 * Set multithreading <br>
	 * - if the parameter is 0 - number of coress less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
		if (threads != 0)
			_threads = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			if (cores <= 2)
				_threads = 1;
			else
				_threads = cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render setDebugPrint() {
		_print = true;
		return this;
	}

	//////////////////////////////////
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
	 * calculate the specular component
	 * 
	 * @param kS       attenuation factor
	 * @param l        the vector from the light source to lighted point.
	 * @param n        the normal vector in the light point
	 * @param v        the vector from the point to the camera
	 * @param nL       dot product between n and l
	 * @param nShinies attenuation parameter for specular
	 * @param light    light source
	 * 
	 * @return specular light
	 */
	private Color calcSpecular(double kS, Vector l, Vector n, Vector v, double nL, int nShinies, Color light) {
		Vector r = l.subtract(n.scale(2 * nL)); // nl must not be zero!
		double vR = -alignZero(r.dotProduct(v));
		if (vR <= 0)
			return Color.BLACK;
		return light.scale(kS * Math.pow(vR, nShinies));
	}

	/**
	 * calculate the color intensity in the specific point. considered the shadow
	 * and transparency and reflection
	 * 
	 * @param geoPoint - the specific point we want to calculate
	 * @param inRay    - the direction from point to another geometry / camera
	 * @param level    - deep of recursion
	 * @param k        - parameter
	 * @return - the color in point considered the shadow and transparency and
	 *         reflection
	 */
	private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
		Color color = geoPoint.geometry.getEmission();
		// vector from point to camera
		Vector v = geoPoint.point.subtract(scene.getCamera().getP0()).normalize();
		Vector n = geoPoint.geometry.getNormal(geoPoint.point);// normal vector at point
		// get the attenuation parameter
		Material material = geoPoint.geometry.getMatrial();
		int nShininess = material.getNShininess();
		double kD = material.getKd();
		double kS = material.getKs();
		// normal DotProduct v
		double nV = alignZero(n.dotProduct(v));
		Color iP; // intensity that point get from the light source
		double ktr;
		Vector l;
		double nL;
		for (LightSource lightSource : scene.getLights()) {
			// vector from light source to point
			l = lightSource.getL(geoPoint.point);
			nL = alignZero(n.dotProduct(l));
			if (nL > 0 && nV > 0 || nL < 0 && nV < 0) {// same sign(case zero not relevant)
				ktr = transparency(lightSource, l, n, geoPoint);
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
				// call in recursion
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
	 * the func generate reflected ray from geometry
	 * 
	 * @param n     normal
	 * @param point specific point
	 * @param inRay ray from light source
	 * @return Ray new Reflected ray/ (use the costructor of Ray)
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector r;
		Vector v = inRay.getDirection();
		double nV = alignZero(n.dotProduct(v));
		try {
			r = v.subtract(n.scale(nV * 2));
		} catch (IllegalArgumentException ex) {
			return null;
		}
		return new Ray(point, r, n);
	}

	/**
	 * 
	 * calculate the secondary rays to refracted
	 * 
	 * @param point
	 * @param n     normal
	 * @param inRay - ray from light source
	 * @return new refracted ray/ (use the costructor of Ray)
	 */
	private Ray constructRefractedRay(Point3D point, Vector n, Ray inRay) {
		return new Ray(point, inRay.getDirection(), n);
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

	/**
	 * check if point should be shaded and reflected
	 * 
	 * @param light - the light source
	 * @param l     - the ray from the light source
	 * @param n     - the normal
	 * @param gp    - the point on the geometry
	 * @return number between 0 to 1 that meaning the level of transparency and
	 *         shadow
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector lightDirection = l.scale(-1);// from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersection = scene.getGeometries().findIntersections(lightRay);
		double ktr = 1.0;
		if (intersection == null)
			return ktr;
		double lightDistance = light.getDistance(gp.point);
		for (GeoPoint gPoint : intersection) {
			if (alignZero(gPoint.point.distance(gp.point) - lightDistance) <= 0) {
				ktr *= gPoint.geometry.getMatrial().getKt();
				if (ktr < MIN_CALC_COLOR_K)
					return 0;
			}
		}
		return ktr;
	}

	/**
	 * This function check if the point is without shadow
	 * 
	 * 
	 * @param l  vector from light to the point
	 * @param n  normal vector of the geopoint
	 * @param gp the point
	 * @return true if its unshaded
	 */
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.getGeometries().findIntersections(lightRay);
		if (intersections != null) {
			double dis = light.getDistance(gp.point);
			for (GeoPoint geoPoint : intersections) {
				if (dis > gp.point.distance(geoPoint.point) && geoPoint.geometry.getMatrial().getKt() == 0)
					return false;
			}
		}
		return true;
	}
}
