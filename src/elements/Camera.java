package elements;

import primitives.*;
import static primitives.Util.*;

/**
 * A camera class represent camera in 3D.
 * 
 * @author E&Y
 *
 */
public class Camera {
	private Point3D p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;

	/**
	 * ctor for camera get the location and make the right vector.
	 * 
	 * @param p0  Camera location
	 * @param vTo The direction to the view plane
	 * @param vUp direction to up
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		// checking if vTo orthogonal to vUp
		if (vUp.dotProduct(vTo) != 0)
			throw new IllegalArgumentException("The vTo and vUp must be orthogonal to each other");
		this.p0 = new Point3D(p0);
		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();
		this.vRight = this.vTo.crossProduct(this.vUp);
	}

	/**
	 * The function calculate the ray that start at the camera and intersect the
	 * view plane in pixel j,i.
	 * 
	 * 
	 * @param nX             number of pixels in axis x.
	 * @param nY             number of pixels in axis y.
	 * @param j              the pixel in y axis.
	 * @param i              the pixel in x axis.
	 * @param screenDistance the distance of the view plane from camera.
	 * @param screenWidth    the width of screen.
	 * @param screenHeight   the height of screen.
	 * @return ray that intersect the view plane at pixel j,i.
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {

		Point3D pointCenter = p0.add(vTo.scale(screenDistance));// the center point in view plane.

		// calculate Ratio
		double rY = screenHeight / nY;
		double rX = screenWidth / nX;

		double yi = ((i - nY / 2d) * rY + rY / 2d);
		double xj = ((j - nX / 2d) * rX + rX / 2d);

		// in case that xj and yi both are 0
		Point3D pij = pointCenter;

		if (!isZero(xj))
			pij = pij.add(vRight.scale(xj));

		if (!isZero(yi))
			pij = pij.add(vUp.scale(-1 * yi));

		return new Ray(p0, pij.subtract(p0));
	}

	/**
	 * p0 getter
	 * 
	 * @return Camera location.
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * vTo getter
	 * 
	 * @return The direction to the view plane
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * vUp getter
	 * 
	 * @return direction to up.
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * vRight getter
	 * 
	 * @return direction to right.
	 */
	public Vector getVRight() {
		return vRight;
	}
}
