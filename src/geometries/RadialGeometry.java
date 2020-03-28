package geometries;

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
