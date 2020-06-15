package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.*;
import renderer.Render;

import static primitives.Util.*;

/**
 * geometries class is a package of geometric shapes.
 * 
 * @author E&Y
 *
 */
public class Geometries extends Intersectable {
	private List<Intersectable> _geometries;
	/**
	 * the last geometry that added to our geometries it's needed to check if we
	 * need to update the size box.
	 */
	private Intersectable lastGeometryAdded;

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
		if (lastGeometryAdded.minX < minX)
			minX = lastGeometryAdded.minX;
		if (lastGeometryAdded.maxX > maxX)
			maxX = lastGeometryAdded.maxX;
		if (lastGeometryAdded.minY < minY)
			minY = lastGeometryAdded.minY;
		if (lastGeometryAdded.maxY > maxY)
			maxY = lastGeometryAdded.maxY;
		if (lastGeometryAdded.minZ < minZ)
			minZ = lastGeometryAdded.minZ;
		if (lastGeometryAdded.maxZ > maxZ)
			maxZ = lastGeometryAdded.maxZ;
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
			lastGeometryAdded = geometry;
			if (Render.boundingBox)
				this.createBox();
		}
	}

	/**
	 * find intersection only if intersect box
	 * 
	 * @param ray ray to intersect
	 * @return list of intersection points
	 */
	@Override
	public List<GeoPoint> findIntersectionsBoundingBox(Ray ray) {
		List<GeoPoint> allIntersectPoints = new LinkedList<GeoPoint>();
		List<GeoPoint> check = new LinkedList<GeoPoint>();
		for (Intersectable geometry : _geometries) {
			if (geometry.isRayIntersectBox(ray))
				check = geometry.findIntersectionsBoundingBox(ray);
			if (check != null)
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

	/**
	 * get geometries
	 * 
	 * @return list<intersectable) all of geometries.
	 */
	public List<Intersectable> getGeometries() {
		return _geometries;
	}
}
