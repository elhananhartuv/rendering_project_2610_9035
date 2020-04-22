package elements;

import primitives.*;

/**
 *  A  camera class represent camera in 3D.
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
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @param screenDistance
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {
		return null;
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
