package elements;

import primitives.Color;

/**
 * ambient light represents a fixed-intensity and fixed color light source that
 * affects all objects in the scene equally.the class extend Class Light.
 * 
 * @author E&Y
 */
public class AmbientLight extends Light {

	/**
	 * ctor for AmbientLight class.calculate the intensity and call to Light ctor.
	 * 
	 * @param intensity
	 * @param kA        kA is a attenuation parameters
	 */
	public AmbientLight(Color intensity, double kA) {
		super(intensity.scale(kA));
	}

}
