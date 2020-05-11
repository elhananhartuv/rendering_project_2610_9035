package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * 
 * @author USER
 *
 */
public class SpotLight extends PointLight {
	private Vector direction;

	/**
	 * 
	 * @param direction
	 * @param intensity
	 * @param position
	 * @param kC
	 * @param kL
	 * @param kQ
	 */
	public SpotLight(Vector direction, Color intensity, Point3D position, double kC, double kL, double kQ) {
		super(intensity, position, kC, kL, kQ);
		this.direction = direction.normalized();
	}

	@Override

	public Color getIntensity(Point3D p) {
		double CosineAngle = direction.dotProduct(getL(p));
		double factor = Math.max(0, CosineAngle);
		if (CosineAngle < 0)
			return Color.BLACK;
		return super.getIntensity(p).scale(factor);
	}

}
