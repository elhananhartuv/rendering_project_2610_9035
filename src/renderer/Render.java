package renderer;

import java.util.List;
import primitives.*;
import elements.*;
import geometries.*;
import scene.*;
import static geometries.Intersectable.GeoPoint;

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
	 * calculate the color in the specific point that function get.
	 * 
	 * @param point the point to calculate the color.
	 * @return the color of the point.
	 */
	private Color calcColor(GeoPoint point) {
		Color color = scene.getAmbientLight().getIntensity();
		// add emmision to ambient light
		color = color.add(point.geometry.getEmission());
		return color;
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
}
