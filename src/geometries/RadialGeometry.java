package geometries;
/**
 * the abstract class RadialGeometry has radius and  implements the interface Geometry
 * @author USER
 *
 */
public abstract class RadialGeometry implements Geometry {
	double radius;
	public  RadialGeometry(double r) {
		radius=r;
	}

	public RadialGeometry(RadialGeometry other) {
		radius=other.radius;
	}
	
	public double getRadius() {
		return radius;
	}
}
