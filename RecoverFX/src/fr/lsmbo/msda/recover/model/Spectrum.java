package fr.lsmbo.msda.recover.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lsmbo.msda.recover.Config;
import fr.lsmbo.msda.recover.Session;

public class Spectrum {

	private Integer id = 0;
	private float mz = 0; // maybe we should use BigDecimal instead for
							// precision ?
	private float intensity = 0; // maybe we should use BigDecimal instead for
									// precision ?
	private Integer charge = -1;
	private float retentionTime = 0; // retention time in seconds
	private String title = "";
	private Integer nbFragments = 0;
	private Integer indexOfMostIntenseFragment = 0;
	private float fragmentMaxMoz = 0;
	private float fragmentMaxIntensity = 0;
	private Integer lineStart = 0;
	private Integer lineStop = 0;
	private float medianFragmentsIntensities = 0;
	private float averageFragmentsIntensities = 0;
	private Integer upn = -1;
	private Boolean isIdentified = false;
	private Boolean isRecover = false;
	private float highIntensityThreshold = Session.HIGH_INTENSITY_THRESHOLD;
	private float lowIntensityThreshold = Session.LOW_INTENSITY_THRESHOLD;
	private Boolean isRecoverHIT = false;
	private Boolean isRecoverLIT = false;
	private Boolean isRecoverFI = false;
	private Boolean isRecoverWC = false;
	private Boolean isRecoverIS = false;
	private Boolean isRecoverIR = false;

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>(); // may be
																		// empty
																		// if
																		// file
																		// is
																		// too
																		// big
	private ArrayList<Fragment> sortedFragments = new ArrayList<Fragment>(); // may
																				// be
																				// empty
																				// if
																				// file
																				// is
																				// too
																				// big

	public String toString() {
		return "Spectrum id:" + id + " moz:" + mz + " intensity:" + intensity + " charge:" + charge + " title:'" + title
				+ "' nbFragments:" + nbFragments + " indexOfBest:" + indexOfMostIntenseFragment + " lineStart:"
				+ lineStart + " lineStop:" + lineStop + " Recover:" + isRecover;
	}

	public Spectrum() {
		super();
	}

	// public Spectrum(Integer id, float mz, float intensity, Integer charge,
	// float retentionTime, String title, Integer nbFragments, Integer
	// lineStart, Integer lineStop) {
	// super();
	// this.id = id;
	// this.mz = mz;
	// this.intensity = intensity;
	// this.charge = charge;
	// this.retentionTime = retentionTime;
	// this.title = title;
	// this.nbFragments = nbFragments;
	// this.lineStart = lineStart;
	// this.lineStop = lineStop;
	// this.upn = nbFragments;
	// }

	public Integer getIndexOfMostIntenseFragment() {
		computeFragmentValues();
		return this.indexOfMostIntenseFragment;
	}

	public void setIndexOfMostIntenseFragment(Integer indexOfMostIntenseFragment) {
		this.indexOfMostIntenseFragment = indexOfMostIntenseFragment;
	}

	public float getFragmentMaxMoz() {
		computeFragmentValues();
		return fragmentMaxMoz;
	}

	public void setFragmentMaxMoz(float fragmentMaxMoz) {
		this.fragmentMaxMoz = fragmentMaxMoz;
	}

	public float getFragmentMaxIntensity() {
		computeFragmentValues();
		return fragmentMaxIntensity;
	}

	public void setFragmentMaxIntensity(float fragmentMaxIntensity) {
		this.fragmentMaxIntensity = fragmentMaxIntensity;
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

	public float getRetentionTime() {
		return retentionTime;
	}

	public void setRetentionTime(float retentionTime) {
		this.retentionTime = retentionTime;
	}

	public void setRetentionTimeFromTitle() {
		if (!title.isEmpty() && !Session.CURRENT_REGEX_RT.isEmpty() && Config.get(Session.CURRENT_REGEX_RT) != null) {
			setRetentionTimeFromTitle(Config.get(Session.CURRENT_REGEX_RT));
		}
	}

	public void setRetentionTimeFromTitle(String regex) {
		try {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(this.title);
			Float rt = 0F;
			if (m.find())
				rt = new Float(m.group(1));
			this.retentionTime = rt;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.setRetentionTimeFromTitle();
	}

	public Integer getNbFragments() {
		computeFragmentValues();
		return nbFragments;
	}

	public void setNbFragments(Integer nbFragments) {
		this.nbFragments = nbFragments;
	}

	public Integer getLineStart() {
		return lineStart;
	}

	public void setLineStart(Integer lineStart) {
		this.lineStart = lineStart;
	}

	public Integer getLineStop() {
		return lineStop;
	}

	public void setLineStop(Integer lineStop) {
		this.lineStop = lineStop;
	}

	public ArrayList<Fragment> getFragments() {
		return fragments;
	}

	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
		// Float sum = 0F;
		// Float[] intensities = new Float[this.nbFragments];
		// for(Fragment fragment: fragments) {
		// sum += fragment.getIntensity();
		// intensities[fragment.getId()] = fragment.getIntensity();
		// if(fragment.getIntensity() > this.fragmentMaxIntensity) {
		// this.fragmentMaxIntensity = fragment.getIntensity();
		// this.indexOfMostIntenseFragment = fragment.getId();
		// }
		// }
		// this.averageFragmentsIntensities = sum / this.nbFragments;
		// Arrays.sort(intensities);
		// if (intensities.length % 2 == 0)
		// this.medianFragmentsIntensities =
		// ((Float)intensities[intensities.length/2] +
		// (Float)intensities[intensities.length/2 - 1])/2;
		// else
		// this.medianFragmentsIntensities = (Float)
		// intensities[intensities.length/2];
		// this.fragmentMaxMoz = fragments.get(this.nbFragments).getMz();
		// this.fragmentMaxMozl = fragments.get(this.nbFragments).getMzl();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Fragment> getSortedFragments() {
		if (sortedFragments.isEmpty()) {
			sortedFragments = (ArrayList<Fragment>) fragments.clone();
			sortedFragments.sort(new Comparator<Fragment>() {
				@Override
				public int compare(Fragment f1, Fragment f2) {
					if (f1.getIntensity() > f2.getIntensity())
						return 1;
					if (f1.getIntensity() < f2.getIntensity())
						return -1;
					return 0;
				}
			});
		}
		return sortedFragments;
	}

	private void computeFragmentValues() {
		// TODO compute here the sortedFragments array, order by intensity asc
		if (this.averageFragmentsIntensities == 0 && this.medianFragmentsIntensities == 0 && this.fragmentMaxMoz == 0
				&& this.fragmentMaxIntensity == 0) {
			// lazy computing
			this.nbFragments = this.fragments.size();
			if (this.nbFragments > 0) {
				Float sum = 0F;
				Float[] intensities = new Float[this.nbFragments];
				for (Integer i = 0; i < this.nbFragments; i++) {
					Fragment fragment = fragments.get(i);
					// for(Fragment fragment: fragments) {
					sum += fragment.getIntensity();
					// intensities[fragment.getId()-1] =
					// fragment.getIntensity();
					intensities[i] = fragment.getIntensity();
					if (fragment.getIntensity() > this.fragmentMaxIntensity) {
						this.fragmentMaxIntensity = fragment.getIntensity();
						// this.indexOfMostIntenseFragment = fragment.getId();
						this.indexOfMostIntenseFragment = i;
					}
				}
				this.averageFragmentsIntensities = sum / this.nbFragments;
				Arrays.sort(intensities);
				if (intensities.length % 2 == 0)
					this.medianFragmentsIntensities = ((Float) intensities[intensities.length / 2]
							+ (Float) intensities[intensities.length / 2 - 1]) / 2;
				else
					this.medianFragmentsIntensities = (Float) intensities[intensities.length / 2];
				this.fragmentMaxMoz = fragments.get(this.nbFragments - 1).getMz();
			}
		}
	}

	public void addFragment(Fragment fragment) {
		this.fragments.add(fragment);
		// this.nbFragments++;
	}

	public void addFragments(ArrayList<Fragment> fragments) {
		this.fragments.addAll(fragments);
		// this.nbFragments += fragments.size();
	}

	public void unloadFragments() {
		this.fragments = null;
	}

	public float getMedianFragmentsIntensities() {
		computeFragmentValues();
		return medianFragmentsIntensities;
	}

	public void setMedianFragmentsIntensities(float medianFragmentsIntensities) {
		this.medianFragmentsIntensities = medianFragmentsIntensities;
	}

	public float getAverageFragmentsIntensities() {
		computeFragmentValues();
		return averageFragmentsIntensities;
	}

	public void setAverageFragmentsIntensities(float averageFragmentsIntensities) {
		this.averageFragmentsIntensities = averageFragmentsIntensities;
	}

	// public Integer getUpn() {
	// if(upn == -1) // means not initialized
	// upn = getNbFragments();
	// return upn;
	// }
	//
	// public void setUpn(Integer upn) {
	// this.upn = upn;
	// }

	public Boolean getIsIdentified() {
		return isIdentified;
	}

	public void setIsIdentified(Boolean isIdentified) {
		this.isIdentified = isIdentified;
	}

	public Boolean getIsRecover() {
		return isRecover;
	}

	public void setIsRecover(Boolean isRecover) {
		this.isRecover = isRecover;
	}

	public Integer getUpn() {
		return upn;
	}

	public void setUpn(Integer upn) {
		this.upn = upn;
	}

	public float getHighIntensityThreshold() {
		return highIntensityThreshold;
	}

	public void setHighIntensityThreshold(float highIntensityThreshold) {
		this.highIntensityThreshold = highIntensityThreshold;
	}

	public float getLowIntensityThreshold() {
		return lowIntensityThreshold;
	}

	public void setLowIntensityThreshold(float lowIntensityThreshold) {
		this.lowIntensityThreshold = lowIntensityThreshold;
	}

	public Boolean getIsRecoverHIT() {
		return isRecoverHIT;
	}

	public void setIsRecoverHIT(Boolean isRecoverHIT) {
		this.isRecoverHIT = isRecoverHIT;
	}

	public Boolean getIsRecoverLIT() {
		return isRecoverLIT;
	}

	public void setIsRecoverLIT(Boolean isRecoverLIT) {
		this.isRecoverLIT = isRecoverLIT;
	}

	public Boolean getIsRecoverFI() {
		return isRecoverFI;
	}

	public void setIsRecoverFI(Boolean isRecoverFI) {
		this.isRecoverFI = isRecoverFI;
	}

	public Boolean getIsRecoverWC() {
		return isRecoverWC;
	}

	public void setIsRecoverWC(Boolean isRecoverWC) {
		this.isRecoverWC = isRecoverWC;
	}

	public Boolean getIsRecoverIS() {
		return isRecoverIS;
	}

	public void setIsRecoverIS(Boolean isRecoverIS) {
		this.isRecoverIS = isRecoverIS;
	}

	public Boolean getIsRecoverIR() {
		return isRecoverIR;
	}

	public void setIsRecoverIR(Boolean isRecoverIR) {
		this.isRecoverIR = isRecoverIR;
	}
}
