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
	 * 
	 * @param nX             number of pixels in axis x
	 * @param nY             number of pixels in axis y
	 * @param j 
	 * @param i
	 * @param screenDistance the distance of the view plane from camera
	 * @param screenWidth    width screen
	 * @param screenHeight   height screen
	 * @return
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {
		if (isZero(screenDistance))
			throw new IllegalArgumentException("distance cannot be 0");

		Point3D Pc = p0.add(vTo.scale(screenDistance));
		double Ry = screenHeight / nY;
		double Rx = screenWidth / nX;

		double yi = ((i - nY / 2d) * Ry + Ry / 2d);

		double xj = ((j - nX / 2d) * Rx + Rx / 2d);

		Point3D Pij = Pc;

		if (!isZero(xj))
			Pij = Pij.add(vRight.scale(xj));

		if (!isZero(yi))
			Pij = Pij.add(vUp.scale(-1 * yi)); // Pij.add(_vUp.scale(-yi))

		Vector Vij = Pij.subtract(p0);
		return new Ray(p0, Vij);
	}

	/**
	 * get function
	 * 
	 * @return Camera location.
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * get function
	 * 
	 * @return The direction to the view plane
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * get function
	 * 
	 * @return direction to up.
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * get function
	 * 
	 * @return direction to right.
	 */
	public Vector getVRight() {
		return vRight;
	}
}
