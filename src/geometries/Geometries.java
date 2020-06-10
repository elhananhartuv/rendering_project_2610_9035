package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * geometries class is a package of geometric shapes.
 * 
 * @author E&Y
 *
 */
public class Geometries extends Intersectable {
	private List<Intersectable> _geometries;
	private Intersectable lastGeometry;

	// ***************** Constructors ********************** //

	/**
	 * a default ctor that Initialize the _geometries list with empty ArrayList.
	 */
	public Geometries() {
		_geometries = new LinkedList<Intersectable>();
	}

	/**
	 * ctor that get Intersectable parameters
	 * 
	 * @param _geometries add geometries shapes to list
	 */
	public Geometries(Intersectable... _geometries) {
		this._geometries = new LinkedList<Intersectable>();
		add(_geometries);
	}

	// ***************** Operations ******************** //

	@Override
	protected void createBox() {
		if (lastGeometry.minX < minX)
			minX = lastGeometry.minX;
		if (lastGeometry.maxX > maxX)
			maxX = lastGeometry.maxX;
		if (lastGeometry.minY < minY)
			minY = lastGeometry.minY;
		if (lastGeometry.maxY > maxY)
			maxY = lastGeometry.maxY;
		if (lastGeometry.minZ < minZ)
			minZ = lastGeometry.minZ;
		if (lastGeometry.maxZ > maxZ)
			maxZ = lastGeometry.maxZ;
	}

	/**
	 * The function get some Intersectable parameters and add to the _geometries
	 * list
	 * 
	 * @param geometries parameters to add to list.
	 */
	public void add(Intersectable... parm) {
		for (Intersectable geometry : parm) {
			_geometries.add(geometry);
			lastGeometry = geometry;
			createBox();
		}
	}

	/**
	 * find intersection only if intersect box
	 * 
	 * @param parm ray to intersect
	 * @return list of intersection points
	 */
	@Override
	public List<GeoPoint> findIntersectionsBoundingBox(Ray parm) {
		List<GeoPoint> allIntersectPoints = new LinkedList<GeoPoint>();
		List<GeoPoint> check=new LinkedList<GeoPoint>(); 
		for (Intersectable geometry : _geometries) {
			if (geometry.isRayIntersectBox(parm))
				check=geometry.findIntersectionsBoundingBox(parm);
			if(check!=null)
				allIntersectPoints.addAll(check);
		}
		return allIntersectPoints;
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> intersections = new LinkedList<GeoPoint>();
		for (int i = 0; i < _geometries.size(); i++) {
			// need to check if the there is no intersection with the geometry shape.
			List<GeoPoint> check = _geometries.get(i).findIntersections(ray);
			if (check != null)
				intersections.addAll(check);
		}
		// if there is no intersections point the size of 'intersections' is 0
		if (isZero(intersections.size()))
			return null;
		return intersections;
	}
}
