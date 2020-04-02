package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * Sphere class are extends RadialGeometry has a center point and Radius.
 * 
 * @author USER
 *
 */
public class Sphere extends RadialGeometry {

	Point3D center;
	public Sphere(Point3D p0,double r) {
		super(r);
		center=new Point3D(p0);
	}
	
	@Override
	public Vector getNormal(Point3D point) {
		return point.subtract(center).normalize();
	}

}
