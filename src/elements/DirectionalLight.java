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
	 * @param intensity
	 * @param direction
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
}
