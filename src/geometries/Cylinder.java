package geometries;

import primitives.*;

public class Cylinder extends Tube {

	double height;
	public Cylinder(Ray ray, double r,double height) {
		super(ray, r);
		this.height=height;
	}
	@Override
	public Vector getNormal(Point3D point) {
		return null;
	}

}
