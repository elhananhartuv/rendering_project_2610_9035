package geometries;
import primitives.*;
public class Plane implements Geometry {

	Point3D point;
	Vector normal;
	public Plane(Point3D point,Vector vector) {
		this.point=new Point3D(point);
		this.normal=new Vector(vector);	
	}
	public Plane(Point3D p0,Point3D p1,Point3D p2) {
		point=new Point3D(p0);
		Vector v1=p0.subtract(p1);
		Vector v2=p0.subtract(p2);
		normal=v1.crossProduct(v2);
		normal.normalize();
	}
	
	@Override
	public Vector getNormal (Point3D point) {
		return normal;
	}
	public Vector getNormal() {
		return normal;
	}
	@Override
	public String toString() {
		return "the normal of the plane is: "+normal.toString()+" and the point is:"+point.toString();
	}
	
}