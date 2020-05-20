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
	private double kT;
	private double kR;

	/**
	 * ctor for Material class get 3 parameters. initialized by full ctor(use this)
	 * 
	 * @param kD         attenuation parameter for diffusive
	 * @param kS         attenuation parameter for specular
	 * @param nShininess attenuation for the pow.
	 */
	public Material(double kD, double kS, int nShininess) {
		this(kD, kS, nShininess, 0, 0);
	}

	/**
	 * ctor for Material class get 5 parameters.
	 * 
	 * @param kD         attenuation parameter for diffusive
	 * @param kS         attenuation parameter for specular
	 * @param nShininess attenuation for the pow.
	 * @param kT         attenuation parameter for transparency
	 * @param kR         attenuation parameter for reflection
	 */
	public Material(double kD, double kS, int nShininess, double kT, double kR) {
		this.kD = kD;
		this.kS = kS;
		this.nShininess = nShininess;
		this.kT = kT;
		this.kR = kR;
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

	/**
	 * kR getter reflection parameter
	 * 
	 * @return kR
	 */
	public double getkR() {
		return kR;
	}

	/**
	 * kT getter transparency parameter
	 * 
	 * @return kT
	 */
	public double getkT() {
		return kT;
	}
}
