package elements;

import primitives.Color;

/**
 * Light id abstract class that provide intensity color for each kind of light
 * 
 * @author E&Y
 */
abstract class Light {
	protected Color intensity;

	/**
	 * ctor for abstract class Light that get intensity.
	 * 
	 * @param intensity the intensity of the light.
	 */
	public Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * get intensity function
	 * 
	 * @return intensity
	 */
	public Color getIntensity() {
		return intensity;
	}
}
