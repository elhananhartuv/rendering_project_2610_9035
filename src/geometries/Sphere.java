package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry {

	Point3D center;
	public Sphere(Point3D p0,double r) {
		super(r);
		center=new Point3D(p0);
	}
	
	@Override
	public Vector getNormal(Point3D point) {
		// TODO Auto-generated method stub
		return null;
	}

}
