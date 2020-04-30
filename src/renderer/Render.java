package renderer;

import java.util.List;
import primitives.*;

/**
 *!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * 
 * @author E&Y
 */
import scene.Scene;

public class Render {
	private ImageWriter imageWriter;
	private Scene scene;

	// ***************** Constructor ******************** //

	/**
	 * ctor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param imageWriter
	 * @param scene
	 */
	public Render(ImageWriter imageWriter, Scene scene) {
		this.imageWriter = imageWriter;
		this.scene = scene;
	}
	
	// ***************** Getters/Setters ******************** //
	
	/**
	 * get function
	 * @return imageWriter
	 */
	public ImageWriter getImageWriter() {
		return imageWriter;
	}
	
	/**
	 * get function
	 * @return scene
	 */
	public Scene getScene() {
		return scene;
	}

	// ***************** Operations ******************** //

	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	public void renderImage() {

	}

	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param point
	 * @return
	 */
	private Color calcColor(Point3D point) {
		return scene.getAmbientLight().GetIntensity();
	}

	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param points
	 * @return
	 */
	private Point3D getClosestPoint(List<Point3D> points) {
		return null;
	}

	/**
	 * to add a grid over the image but not overwrite the image
	 * 
	 * @param interval
	 * @param color
	 */
	public void printGrid(int interval, java.awt.Color color) {

	}
}
