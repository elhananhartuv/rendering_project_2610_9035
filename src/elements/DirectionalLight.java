package elements;

import primitives.Color;
import primitives.*;

/**
 * DirectionalLight class is light source with direction.
 * 
 * @author E&Y
 */
public class DirectionalLight extends Light implements LightSource {
	private Vector direction;

	/**
	 * ctor for DirectionalLight get 2 parameters, intensity and direction.
	 * 
	 * @param intensity the intensity of light source
	 * @param direction the direction of light source
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		return intensity;
	}

	@Override
	public Vector getL(Point3D p) {
		return direction;
	}
	
	@Override
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}
}
