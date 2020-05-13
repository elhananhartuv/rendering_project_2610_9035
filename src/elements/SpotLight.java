package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import static primitives.Util.*;

/**
 * SpotLight class is kind of Light has position and direction,and the lighted
 * point effected by distance and direction.
 * 
 * @author E&Y
 *
 */
public class SpotLight extends PointLight {
	private Vector direction;

	/**
	 * ctor for SpotLight call to PointLight ctor with position and attenuation
	 * parameters.
	 * 
	 * @param direction the direction of the light source
	 * @param intensity the intensity of the light source
	 * @param position  the position of the light source
	 * @param kC        attenuation parameter
	 * @param kL        attenuation parameter
	 * @param kQ        attenuation parameter
	 */
	public SpotLight(Vector direction, Color intensity, Point3D position, double kC, double kL, double kQ) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalized();
	}

	@Override
	public Color getIntensity(Point3D p) {
		double cosineAngle;
		try {
			cosineAngle = alignZero(direction.dotProduct(getL(p)));
		} catch (IllegalArgumentException ex) {
			return Color.BLACK;
		}
		if (cosineAngle < 0)
			return Color.BLACK;
		return super.getIntensity(p).scale(cosineAngle);
	}
}
