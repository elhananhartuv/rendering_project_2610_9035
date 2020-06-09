package elements;

import primitives.*;

/**
 * 
 * @author E&Y
 *
 */
public class Box {
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;

	// ***************** Constructors ********************** //
	/**
	 * 
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @param minZ
	 * @param maxZ
	 */
	public Box(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
	}

	/**
	 * 
	 */
	public Box() {
		this.minX = 0;
		this.maxX = 0;
		this.minY = 0;
		this.maxY = 0;
		this.minZ = 0;
		this.maxZ = 0;
	}

	// ***************** Operations ******************** //
	/**
	 * check if ray intersect the box
	 * 
	 * @param ray- the specific ray to check if intersect the box.
	 * @return true if ray intersect otherwise false.
	 */
	public boolean isRayIntersectBox(Ray ray) {
		return true;
	}
/**
 * 
 * @param mi
 */
	public void updateBoxMInSize() {

	}
/**
 * 
 * @param mi
 */
	public void updateBoxMaxSize() {

	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean isPointInBox(double x, double y, double z) {
		return (x <= maxX && x >= minX && y <= maxY && y >= minY && z <= maxZ && z >= minZ);
	}

	// ***************** get&set ******************** //

	/**
	 * get min x
	 * 
	 * @return minX
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * get man x
	 * 
	 * @return maxX
	 */
	public double getMaxX() {
		return maxX;
	}

	/**
	 * get man y
	 * 
	 * @return maxY
	 */
	public double getMaxY() {
		return maxY;
	}

	/**
	 * get min y
	 * 
	 * @return minY
	 */
	public double getMinY() {
		return minY;
	}

	/**
	 * get man z
	 * 
	 * @return maxZ
	 */
	public double getMaxZ() {
		return maxZ;
	}

	/**
	 * get min z
	 * 
	 * @return minZ
	 */
	public double getMinZ() {
		return minZ;
	}

}