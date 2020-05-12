package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import static primitives.Util.*;

/**
 * PointLight class is kind of Light has position and effected by distance.
 * 
 * @author E&Y
 *
 */
public class PointLight extends Light implements LightSource {
	private Point3D position;
	// attenuation parameters
	private double kC;
	private double kL;
	private double kQ;

	/**
	 * ctor for point light. PointLight has the same attributes as a Light node,
	 * with the addition of location and factors for attenuation with distance
	 * 
	 * 
	 * @param intensity the intensity of the color
	 * @param position  the position of the source light. point3d.
	 * @param kC        factors for attenuation with distance
	 * @param kL        factors for attenuation with distance
	 * @param kQ        factors for attenuation with distance
	 */
	public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
		super(intensity);
		this.position = new Point3D(position);
		this.kC = kC;
		this.kL = kL;
		this.kQ = kQ;
	}

	@Override
	public Color getIntensity(Point3D p) {
		double dSquared = alignZero(p.distanceSquared(position));
		double distance = alignZero(Math.sqrt(dSquared));
		return intensity.reduce(kC + kL * distance + kQ * dSquared);
	}

	@Override
	public Vector getL(Point3D p) {
		try {
			return p.subtract(position).normalized();
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}
}
