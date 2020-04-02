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
		//t is the projection of point onto cylinder's ray 
		double t=super.axisRay.getDirection().dotProduct(point.subtract(axisRay.getP0()));//t=v*(p-p0)
		// t is the projection on ray and if t equal to zero the meaning is, that the point is on the lower base 
		if(t==0) {
			return super.axisRay.getDirection().scale(-1);	//The point is on the lower base so the normal is -v
		}
		// t is the projection on ray and if t equal to height the meaning is, that the point is on the top base 
		if(t==height) {
			return super.axisRay.getDirection();//The point is on the top base so the normal is v.
		}
		//else if the point is regular point on the cylinder calculate like tube.
		 Point3D o=axisRay.getP0().add(axisRay.getDirection().scale(t));//O=p0+t*v
		 return point.subtract(o).normalized();
	}
}
