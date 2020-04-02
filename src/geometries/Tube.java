package geometries;
import primitives.Ray;
import primitives.Util;
import primitives.Point3D;
import primitives.Vector;

/**
 * Tube class are extends RadialGeometry has a Ray and Radius
 * @author USER
 *
 */
public class Tube extends RadialGeometry {

	Ray axisRay;
	public Tube(Ray ray, double r) {
		super(r);//call to base class radial geometry
		this.axisRay=ray;
	}

	@Override
	public Vector getNormal(Point3D point) {
		//t is the projection of point onto cylinder's ray
		Vector vector=point.subtract(axisRay.getP0());
		double t=Util.alignZero(axisRay.getDirection().dotProduct(vector));
		 if(t==0) {//the meaning is that the vector v orthogonal to the vector (p-p0)
			 return vector.normalize();//so the normal is (P-p0)
		 }
		Point3D o=axisRay.getP0().add(axisRay.getDirection().scale(t));
		return point.subtract(o).normalized();
	}
}
