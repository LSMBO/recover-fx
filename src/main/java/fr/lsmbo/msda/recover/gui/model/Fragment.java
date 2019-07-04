
package fr.lsmbo.msda.recover.gui.model;

/**
 * This class implements the functionality for a fragment.
 * 
 * @author Aromdhani
 *
 */
public class Fragment {

	private Integer id = 0;
	// TODO maybe we should use BigDecimal instead for precision?
	private float mz = 0;
	// TODO maybe we should use BigDecimal instead for precision?
	private float intensity = 0;
	private Integer charge = 1;

	/**
	 * Default constructor
	 */
	public Fragment() {
		super();
	}

	/**
	 * 
	 * @param id
	 *            the fragment id
	 * @param mz
	 *            the fragment mz
	 * @param intensity
	 *            the fragment intensity
	 * @param charge
	 *            the fragment charge
	 */
	public Fragment(Integer id, float mz, float intensity, Integer charge) {
		super();
		this.id = id;
		this.mz = mz;
		this.intensity = intensity;
		this.charge = charge;
	}

	/**
	 * 
	 * @return the fragment charge
	 */
	public Integer getCharge() {
		return charge;
	}

	/**
	 * @return the fragement id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @return the fragment intensity
	 */
	public float getIntensity() {
		return intensity;
	}

	/**
	 * @return the fragment Mz
	 */
	public float getMz() {
		return mz;
	}

	/**
	 * 
	 * @param charge
	 *            the fragment charge to set
	 */
	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	/**
	 * @param id
	 *            teh fragment id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @param intensity
	 *            the fragment intensity to set
	 */
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	/**
	 * 
	 * @param mz
	 *            the fragment mz to set
	 */
	public void setMz(float mz) {
		this.mz = mz;
	}

	@Override
	public String toString() {
		StringBuilder strBldr = new StringBuilder();
		strBldr.append("###Fragment id:").append(id).append(" moz:").append(mz).append(" intensity:").append(intensity)
				.append(" charge:").append(charge);
		return strBldr.toString();
	}

}
