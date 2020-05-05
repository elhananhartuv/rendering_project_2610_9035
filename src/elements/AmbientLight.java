package elements;

import primitives.Color;

/**
 * ambient light represents a fixed-intensity and fixed color light source that
 * affects all objects in the scene equally.
 * 
 * @author E&Y
 */
public class AmbientLight {
	private Color intensity;

	/**
	 * ctor for AmbientLight class.
	 * 
	 * @param ia
	 * @param ka
	 */
	public AmbientLight(Color ia, double ka) {
		this.intensity = ia.scale(ka);
	}

	/**
	 * get function.
	 * 
	 * @return the intensity.
	 */
	public Color GetIntensity() {
		return intensity;
	}
}
