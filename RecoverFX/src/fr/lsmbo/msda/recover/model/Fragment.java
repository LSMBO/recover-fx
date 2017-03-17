package fr.lsmbo.msda.recover.model;

public class Fragment {

	private Integer id = 0;
	private float mz = 0; // maybe we should use BigDecimal instead for
							// precision ?
	private float intensity = 0; // maybe we should use BigDecimal instead for
									// precision ?
	private Integer charge = 1;

	public String toString() {
		return "Fragment id:" + id + " moz:" + mz + " intensity:" + intensity + " charge:" + charge;
	}

	public Fragment() {
		super();
	}

	public Fragment(Integer id, float mz, float intensity, Integer charge) {
		super();
		this.id = id;
		this.mz = mz;
		this.intensity = intensity;
		this.charge = charge;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getMz() {
		return mz;
	}

	public void setMz(float mz) {
		this.mz = mz;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	// public int compareByIntensity(Fragment f1, Fragment f2) {
	// if(f1.getIntensity() > f2.getIntensity()) return 1;
	// if(f1.getIntensity() < f2.getIntensity()) return -1;
	// return 0;
	// }
	//
	// @Override
	// public int compareTo(Object f) {
	// float _intensity = ((Fragment)f).getIntensity();
	// if(intensity > _intensity) return 1;
	// if(intensity < _intensity) return -1;
	// return 0;
	// }
}
