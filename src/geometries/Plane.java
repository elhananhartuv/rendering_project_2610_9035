package geometries;
import primitives.*;
/**
 * class plane represent plane in in 3 Dimension
 * the plane has point and normal to plane.
 * @author USER
 *
 */
public class Plane implements Geometry {

	Point3D point;
	Vector normal;
	/**
	 * Constructor that receives point and direction and initializes accordingly 
	 * @param point
	 * @param vector
	 */
	public Plane(Point3D point,Vector vector) {
		this.point=new Point3D(point);
		this.normal=new Vector(vector);	
	}
	/**
	 * A constructor that gets three points and calculates the normal plane  and initializes.
	 * @param p0
	 * @param p1
	 * @param p2
	 */
	public Plane(Point3D p0,Point3D p1,Point3D p2) {
		point=new Point3D(p0);
		Vector v1=p0.subtract(p1);//make one direction
		Vector v2=p0.subtract(p2);// make the second direction
		normal=v1.crossProduct(v2);//calculate the normal to plane
		normal.normalize();
	}
	
	@Override
	public Vector getNormal (Point3D point) {
		return normal;//in each point on the plane the normal will be the same.
	}
	public Vector getNormal() {
		return normal;
	}
	@Override
	public String toString() { 
		return "the normal of the plane is: "+normal.toString()+" and the point is:"+point.toString();
	}
	
}