package geometries;
import primitives.Ray;
import primitives.Point3D;
import primitives.Vector;

public class Tube extends RadialGeometry {

	Ray axisRay;
	public Tube(Ray ray, double r) {
		super(r);
		axisRay=new Ray(ray.getP0(),ray.getDirection());
	}

	@Override
	public Vector getNormal(Point3D point) {
		
		return null;
	}

}
