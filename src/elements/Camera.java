package elements;

import primitives.*;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
	private double focalDistance=10;
	private double aperture;
	private int numOfRays;
	private static final Random RAND = new Random();

	/**
	 * ctor for camera get the location and make the right vector.
	 * 
	 * @param p0  Camera location
	 * @param vTo The direction to the view plane
	 * @param vUp direction to up
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		this(p0, vTo, vUp, 0, 0, 1);
	}

	/**
	 * ctor for camera get the location and make the right vector.
	 * 
	 * @param p0            Camera location
	 * @param vTo           The direction to the view plane
	 * @param vUp           direction to up
	 * @param focalDistance
	 * @param aperture
	 * @param numOfRays
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp, double focalDistance, double aperture, int numOfRays) {
		// checking if vTo orthogonal to vUp
		if (vUp.dotProduct(vTo) != 0)
			throw new IllegalArgumentException("The vTo and vUp must be orthogonal to each other");
		this.p0 = new Point3D(p0);
		this.vTo = vTo.normalized();
		this.vUp = vUp.normalized();
		this.vRight = this.vTo.crossProduct(this.vUp);
		this.focalDistance = focalDistance;
		this.aperture = aperture;
		this.numOfRays = numOfRays;
	}

	public List<Ray> constructbeamOfRaysThroughPixels(int nX, int nY, int j, int i, double screenDistance,
			double screenWidth, double screenHeight) {
		List<Ray> result = new LinkedList<Ray>();
		Point3D pij = getViewPlanePoint(nX, nY, j, i, screenDistance*0.7, screenWidth*0.7, screenHeight*0.7);
		Vector vToFocal = pij.subtract(p0).normalize();
		// add to list the ray through pixel i,j from view plane
		result.add(new Ray(pij, vToFocal));
		if (numOfRays <= 1 || Util.isZero(aperture))
			return result;
		// to create the focal plane "plane" and not circular we divide in the cosine
		// angle (dotProfuct) to increase the distance.
		Point3D focalPoint = pij.add(vToFocal.scale(focalDistance / vTo.dotProduct(vToFocal)));
		for (int k = 1; k < numOfRays; k++) {
			Point3D randPointVp = randomPoint(pij);
			result.add(new Ray(randPointVp, focalPoint.subtract(randPointVp)));
		}
		return result;
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
	private Point3D getViewPlanePoint(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {
		Point3D viewPlanePoint = p0.add(vTo.scale(screenDistance));
		double rY = screenHeight / nY;
		double rX = screenWidth / nX;

		double yi = ((i - nY / 2d) * rY + rY / 2d);
		double xj = ((j - nX / 2d) * rX + rX / 2d);

		return movePoint(xj, yi, viewPlanePoint);
	}

	/**
	 * 
	 * @param pij
	 * @return
	 */
	private Point3D randomPoint(Point3D pij) {
		double x = (RAND.nextDouble() - 0.5) * aperture;
		double y = (RAND.nextDouble() - 0.5) * aperture;
		return movePoint(x, y, pij);
	}

	/**
	 * move the point by i,j indices.
	 * 
	 * @param j how much to move in vRight direction
	 * @param i how much to move in vUp direction
	 * @param p the point to move
	 * @return the new point after moved i,j.
	 */
	private Point3D movePoint(Double j, Double i, Point3D p) {
		if (!Util.isZero(j)) {
			p = p.add(vRight.scale(j));
		}
		if (!Util.isZero(i)) {
			p = p.add(vUp.scale(-i));
		}
		return p;
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
