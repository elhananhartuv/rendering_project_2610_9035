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
	private double focalDistance;
	private double aperture;
	private int numOfRays;
	private static final Random RAND = new Random();

	/**
	 * ctor for camera get the location of the camera, get the toward vector(vTo)
	 * and vUp and create the vRight vector.there is no Depth of filed effect if we
	 * call to this ctor .
	 * 
	 * @param p0  Camera location
	 * @param vTo The direction to the view plane
	 * @param vUp direction to up
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {
		this(p0, vTo, vUp, 0, 0, 1);
	}

	/**
	 * ctor for camera get the location of the camera, get the toward vector(vTo)
	 * and vUp and create the vRight vector.and get the parameter to Depth of filed
	 * effect.
	 * 
	 * @param p0            Camera location
	 * @param vTo           The direction to the view plane
	 * @param vUp           direction to up
	 * @param focalDistance distance of focal plane from the view plane
	 * @param aperture      the size of aperture (zamzam in Hebrew)
	 * @param numOfRays     to generate beam of rays
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

	/**
	 * generate a beam of rays in each pixel,all the rays are from the pixel in view
	 * plane to focal point.
	 * 
	 * @param nX             number of pixels in axis x.
	 * @param nY             number of pixels in axis y.
	 * @param j              the pixel in y axis.
	 * @param i              the pixel in x axis.
	 * @param screenDistance the distance of the view plane from camera.
	 * @param screenWidth    the width of screen.
	 * @param screenHeight   the height of screen.
	 * 
	 * @return list of rays that start at the view plane to the focal points.
	 */
	public List<Ray> constructBeamOfRaysThroughPixels(int nX, int nY, int j, int i, double screenDistance,
			double screenWidth, double screenHeight) {
		List<Ray> result = new LinkedList<Ray>();
		Point3D pij;// the point in pixel i,j in the view plane.
		if (numOfRays == 1 || Util.isZero(aperture)) { 
			// there is no DOF effect so we want the rays start before view plane.
			pij = getViewPlanePoint(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
			result.add(new Ray(p0, pij.subtract(p0)));
			return result;
		}
		// there is DOF.
		pij = getViewPlanePoint(nX, nY, j, i, screenDistance * 0.5, screenWidth * 0.5, screenHeight * 0.5);
		// the direction from camera to pixel i,j.
		Vector vToFocal = pij.subtract(p0).normalize();
		// add to list the ray through pixel i,j from view plane
		// this is the central ray throw the pixel that start at view plane.
		result.add(new Ray(pij, vToFocal));
		// to create plane focal and not domed, we divide in the cosine
		// angle (dotProfuct) to increase the distance.
		Point3D focalPoint = pij.add(vToFocal.scale(focalDistance / vTo.dotProduct(vToFocal)));
		// k start from 1. the first ray is already concluded
		for (int k = 1; k < numOfRays; k++) {
			Point3D randPointVp = randomPoint(pij);// random point on the view plane
			result.add(new Ray(randPointVp, focalPoint.subtract(randPointVp)));
		}
		return result;
	}

	/**
	 * The function calculate the point on the view plane at pixel j,i.
	 * 
	 * @param nX             number of pixels in axis x.
	 * @param nY             number of pixels in axis y.
	 * @param j              the pixel in y axis.
	 * @param i              the pixel in x axis.
	 * @param screenDistance the distance of the view plane from camera.
	 * @param screenWidth    the width of screen.
	 * @param screenHeight   the height of screen.
	 * @return
	 */
	private Point3D getViewPlanePoint(int nX, int nY, int j, int i, double screenDistance, double screenWidth,
			double screenHeight) {
		Point3D viewPlanePoint = p0.add(vTo.scale(screenDistance));// the central point on the view plane.
		double rY = screenHeight / nY;// the height of each pixel.
		double rX = screenWidth / nX;// the width of each pixel.

		double yi = ((i - nY / 2d) * rY + rY / 2d);// how much to move.
		double xj = ((j - nX / 2d) * rX + rX / 2d);

		return changeLocationPoint(xj, yi, viewPlanePoint);
	}

	/**
	 * scatter randomly the rays we created, in interval of aperture.
	 * 
	 * @param pij center of pixel
	 * @return randomly point in the pixel
	 */
	private Point3D randomPoint(Point3D pij) {
		// random number in interval of the aperture.[-aperture/2,aperture/2].
		double x = (RAND.nextDouble() - 0.5) * aperture;
		double y = (RAND.nextDouble() - 0.5) * aperture;
		return changeLocationPoint(x, y, pij);
	}

	/**
	 * move the point by i,j indices.
	 * 
	 * @param j how much to move in vRight direction
	 * @param i how much to move in vUp direction
	 * @param p the point to move
	 * @return the new point after moved i,j.
	 */
	private Point3D changeLocationPoint(Double j, Double i, Point3D p) {
		if (!Util.isZero(j)) {
			p = p.add(vRight.scale(j));
		}
		if (!Util.isZero(i)) {
			p = p.add(vUp.scale(-i));
		}
		return p;
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
