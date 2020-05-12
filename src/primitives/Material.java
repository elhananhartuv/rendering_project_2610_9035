package primitives;

/**
 * Material class provide the attenuation for diffusive and specularity and
 * shininess.
 * 
 * @author E&Y
 *
 */
public class Material {
	private double kD;
	private double kS;
	private int nShininess;

	/**
	 * ctor for Material class get 3 parameters.
	 * 
	 * @param kD         attenuation parameter for diffusive
	 * @param kS         attenuation parameter for specular
	 * @param nShininess attenuation for the pow.
	 */
	public Material(double kD, double kS, int nShininess) {
		this.kD = kD;
		this.kS = kS;
		this.nShininess = nShininess;
	}

	/**
	 * get kD function
	 * 
	 * @return kD
	 */
	public double getkD() {
		return kD;
	}

	/**
	 * get kS function
	 * 
	 * @return kS
	 */
	public double getkS() {
		return kS;
	}

	/**
	 * get nShininess function
	 * 
	 * @return nShininess
	 */
	public int getnShininess() {
		return nShininess;
	}
}
