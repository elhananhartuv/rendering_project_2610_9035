package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;
/**
 * geometries class is a package of geometric shapes.
 * 
 * @author E&Y
 *
 */

public class Geometries implements Intersectable {
	private List<Intersectable> _geometries;

	/**
	 * a default ctor that Initialize the _geometries list with empty ArrayList.
	 */
	public Geometries() {
		_geometries = new ArrayList<Intersectable>();
	}

	/**
	 * ctor that get Intersectable parameters
	 * 
	 * @param _geometries add geometries shapes to list
	 */
	public Geometries(Intersectable... _geometries) {
		this._geometries = new ArrayList<Intersectable>();
		add(_geometries);
	}

	/**
	 * The function get some Intersectable parameters and add to the _geometries
	 * list
	 * 
	 * @param geometries parameters to add to list.
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable shape : geometries) {
			_geometries.add(shape);
		}
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		List<Point3D> intersections = new ArrayList<Point3D>();
		for (int i = 0; i < _geometries.size(); i++) {
			List<Point3D> check = _geometries.get(i).findIntersections(ray);
			if (check != null)
				intersections.addAll(check);
		}
		// if there is no intersections point the size of 'intersections' is 0
		if (isZero(intersections.size()))
			return null;
		return intersections;
	}
}
