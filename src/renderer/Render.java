package renderer;

import java.util.List;
import primitives.*;
import elements.*;
import geometries.*;
import scene.*;

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
		// the background color in case that the ray miss the geometries.
		java.awt.Color background = scene.getBackground().getColor();

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
				ray = camera.constructRayThroughPixel(Nx, Ny, i, j, scene.getDistance(), width, height);
				// get the intersection points with the geometries shape.
				List<Point3D> intersectionPoints = geometries.findIntersections(ray);

				if (intersectionPoints == null) {
					// if there is no intersection point write to pixel the background color.
					imageWriter.writePixel(j, i, background);

				} else {
					// there is intersections points-calculate the closest point to camera and the
					// color of the point and write to pixel.
					Point3D closestPoint = getClosestPoint(intersectionPoints);
					imageWriter.writePixel(j - 1, i - 1, calcColor(closestPoint).getColor());
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
	private Color calcColor(Point3D point) {
		return scene.getAmbientLight().GetIntensity();
	}

	/**
	 * calculate the closest point to the camera. if the list that given are empty
	 * return null.
	 * 
	 * @param points List of points
	 * @return the closest point.
	 */
	private Point3D getClosestPoint(List<Point3D> points) {
		Point3D result = null;// if there is no points in the list the result will stay null.
		double minDistance = 100d;
		Point3D p0 = this.scene.getCamera().getP0();

		for (Point3D point : points) {
			// distance from the camera.
			double distance = p0.distance(point);
			if (distance < minDistance) {
				minDistance = distance;
				result = point;
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
}
