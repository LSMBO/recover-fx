/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.lsmbo.msda.recover.gui.Config;
import fr.lsmbo.msda.recover.gui.Session;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * This class implements the functionality for a spectrum.
 * 
 * @author Aromdhani
 * @author BL
 * 
 */
public class Spectrum {
	// Spectrum id
	private Integer id = 0;
	// Maybe we should use BigDecimal instead for precision
	private float mz = 0;
	// Maybe we should use BigDecimal instead for precision
	private float intensity = 0;
	private Integer charge = -1;
	// Retention time in seconds
	private float retentionTime = 0;
	// Spectrum title
	private String title = "";
	// Spectrum fragments number
	private Integer nbFragments = 0;
	// The index of the most intense fragment.
	private Integer indexOfMostIntenseFragment = 0;
	// The maximum of Moz
	private float fragmentMaxMoz = 0;
	// The fragment maximum intensity
	private float fragmentMaxIntensity = 0;
	// Median of fragment intensity
	private float medianFragmentsIntensities = 0;
	// The average of fragment intensity
	private float averageFragmentsIntensities = 0;
	// The index of the line start in file of the spectrum
	private Integer lineStart = 0;
	// The index of the line stop in file of the spectrum
	private Integer lineStop = 0;
	// The useful peaks number : it's used to filter
	private Integer upn = -1;
	// TODO More about the reference spectrum
	private double cosTheta = 0D;
	private float deltaMozWithReferenceSpectrum = 0F;
	private int deltaRetentionTimeWithReferenceSpectrum = 0;
	private int nbPeaksIdenticalWithReferenceSpectrum = 0;
	private String titleReferenceSpectrum = "";
	// TODO To remove this property
	// The number of fragment that match
	private Integer nbMatch = 0;
	// The number of peaks
	private int nbPeaks;
	// TODO To remove this property.
	// Determines whether the spectrum is recovered
	private final BooleanProperty recovered = new SimpleBooleanProperty(false);
	// Determines whether the spectrum is identified
	private final BooleanProperty identified = new SimpleBooleanProperty(false);
	// Determines whether the spectrum is flagged
	private final BooleanProperty isFlagged = new SimpleBooleanProperty(false);
	// Determines whether the spectrum has a wrong charge
	private BooleanProperty wrongCharge = new SimpleBooleanProperty(false);
	// Determines whether the spectrum has an ion reporter
	private BooleanProperty ionReporter = new SimpleBooleanProperty(false);

	private float highIntensityThreshold = Session.HIGH_INTENSITY_THRESHOLD;
	private float lowIntensityThreshold = Session.LOW_INTENSITY_THRESHOLD;
	private float topLine = Session.TOP_LINE;

	private int nbFragmentAboveHIT = 0;

	private StatusFilterType isRecoverHIT = StatusFilterType.NOT_USED;
	private StatusFilterType isRecoverLIT = StatusFilterType.NOT_USED;
	private StatusFilterType isRecoverFI = StatusFilterType.NOT_USED;
	private StatusFilterType isRecoverWC = StatusFilterType.NOT_USED;
	private StatusFilterType isRecoverIS = StatusFilterType.NOT_USED;
	private StatusFilterType isRecoverIR = StatusFilterType.NOT_USED;
	// May be empty if file is too big
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	// May be empty if file is too big
	private ArrayList<Fragment> sortedFragments = new ArrayList<Fragment>();
	private ArrayList<Fragment> fragmentEqualsToChart = new ArrayList<Fragment>();

	private Fragment[] nbIntensePeaks;
	private Double[] squareRootnbIntensePeaks;

	@Override
	public String toString() {
		StringBuilder spectrumStr = new StringBuilder();
		spectrumStr.append("##Spectrum id: ").append(id).append(" moz:").append(mz).append(" intensity:")
				.append(intensity).append(" charge:").append(charge).append(" title:'").append(title)
				.append("' nbFragments:").append(nbFragments).append(" indexOfBest:").append(indexOfMostIntenseFragment)
				.append(" lineStart:").append(lineStart).append(" lineStop:").append(lineStop).append(" Recover:")
				.append(recovered.getValue()).append(" Flagged:").append(isFlagged.getValue());
		return spectrumStr.toString();
	}

	/***
	 * Default constructor
	 */
	public Spectrum() {
		super();
	}

	/**
	 * @return the index of the most intense fragment
	 */
	public Integer getIndexOfMostIntenseFragment() {
		computeFragmentValues();
		return this.indexOfMostIntenseFragment;
	}

	/**
	 * 
	 * @param indexOfMostIntenseFragment
	 *            the index of the most intense fragment
	 */
	public void setIndexOfMostIntenseFragment(Integer indexOfMostIntenseFragment) {
		this.indexOfMostIntenseFragment = indexOfMostIntenseFragment;
	}

	/**
	 * 
	 * @return return the fragment max Moz
	 */
	public float getFragmentMaxMoz() {
		computeFragmentValues();
		return fragmentMaxMoz;
	}

	/**
	 * 
	 * @param fragmentMaxMoz
	 *            the fragment max Moz to set
	 */
	public void setFragmentMaxMoz(float fragmentMaxMoz) {
		this.fragmentMaxMoz = fragmentMaxMoz;
	}

	/**
	 * 
	 * @return the fragment max intensity
	 */
	public float getFragmentMaxIntensity() {
		computeFragmentValues();
		return fragmentMaxIntensity;
	}

	/**
	 * 
	 * @param fragmentMaxIntensity
	 *            the fragment max intensity to set
	 */
	public void setFragmentMaxIntensity(float fragmentMaxIntensity) {
		this.fragmentMaxIntensity = fragmentMaxIntensity;
	}

	/**
	 * 
	 * @return the spectrum id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            the spectrum id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the Mz
	 */
	public float getMz() {
		return mz;
	}

	/**
	 * 
	 * @param mz
	 *            the mz to set
	 */
	public void setMz(float mz) {
		this.mz = mz;
	}

	/**
	 * 
	 * @return the spectrum intensity(Precursor intensity)
	 */
	public float getIntensity() {
		return intensity;
	}

	/**
	 * 
	 * @param intensity
	 *            the intensity to set
	 */
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	/**
	 * 
	 * @return the spectrum charge
	 */
	public Integer getCharge() {
		return charge;
	}

	/**
	 * 
	 * @param charge
	 *            the charge to set
	 */
	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	/**
	 * 
	 * @return The retention time
	 */
	public float getRetentionTime() {
		return retentionTime;
	}

	/**
	 * 
	 * @param retentionTime
	 *            the retention time to set
	 */
	public void setRetentionTime(float retentionTime) {
		this.retentionTime = retentionTime;
	}

	/**
	 * Set the retention time from title
	 */
	public void setRetentionTimeFromTitle() {
		if (!title.isEmpty() && !Session.CURRENT_REGEX_RT.isEmpty() && Config.get(Session.CURRENT_REGEX_RT) != null) {
			setRetentionTimeFromTitle(Config.get(Session.CURRENT_REGEX_RT));
		}
	}

	/**
	 * Set the retention from title
	 * 
	 * @param regex
	 *            the used regex to retrieve the retention time from title
	 */
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

	/**
	 * @return return the spectrum title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            The title to set
	 */
	public void setTitle(String title) {
		this.title = title;
		this.setRetentionTimeFromTitle();
	}

	/**
	 * 
	 * @return the fragment number
	 */
	public Integer getNbFragments() {
		computeFragmentValues();
		return nbFragments;
	}

	/**
	 * 
	 * @param nbFragments
	 *            the fragment number to set
	 */
	public void setNbFragments(Integer nbFragments) {
		this.nbFragments = nbFragments;
	}

	/**
	 * 
	 * @return the line start in the file
	 */
	public Integer getLineStart() {
		return lineStart;
	}

	/**
	 * @deprecated
	 * @param lineStart
	 *            the line start in the file to set
	 */
	public void setLineStart(Integer lineStart) {
		this.lineStart = lineStart;
	}

	/**
	 * 
	 * @return the line stop in the file
	 */
	public Integer getLineStop() {
		return lineStop;
	}

	/**
	 * @param lineStop
	 *            the line stop to set
	 */
	public void setLineStop(Integer lineStop) {
		this.lineStop = lineStop;
	}

	/**
	 * 
	 * @return the list of the fragment
	 */
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}

	/**
	 * @param fragments
	 *            the list of the fragment to set
	 */
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}

	/**
	 * @return A sorted list of fragment. The sort is based on fragment
	 *         intensities.
	 */
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

	/**
	 * Compute fragment values . It computes the average and the median of
	 * fragment intensities.
	 */
	private void computeFragmentValues() {
		// TODO Compute here the sortedFragments array, order by intensity
		// ascendant
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

	/**
	 * Determines whether is a wrong charge
	 * 
	 * @return <code>false</code> if the m/z precursor is greater than the last
	 *         fragment (max m/z).
	 */
	public Boolean computeWrongCharge() {
		if ((this.getMz() * this.getCharge() > this.getFragmentMaxMoz()))
			return false;
		else
			return true;
	}

	/**
	 * @param fragment
	 *            the fragment to set
	 */
	public void addFragment(Fragment fragment) {
		this.fragments.add(fragment);
	}

	/**
	 * @param fragments
	 *            the list of fragment to set
	 */
	public void addFragments(ArrayList<Fragment> fragments) {
		this.fragments.addAll(fragments);
	}

	/**
	 * Unload fragments. Set the list of fragment of the spectrum to null.
	 */
	public void unloadFragments() {
		this.fragments = null;
	}

	/**
	 * @return the median of the fragment intensities.
	 */
	public float getMedianFragmentsIntensities() {
		computeFragmentValues();
		return medianFragmentsIntensities;
	}

	/**
	 * @param medianFragmentsIntensities
	 *            the median of the fragment intensities to set
	 */
	public void setMedianFragmentsIntensities(float medianFragmentsIntensities) {
		this.medianFragmentsIntensities = medianFragmentsIntensities;
	}

	/**
	 * @return the average of fragment intensities
	 */
	public float getAverageFragmentsIntensities() {
		computeFragmentValues();
		return averageFragmentsIntensities;
	}

	/**
	 * @param averageFragmentsIntensities
	 *            the average of fragment intensities to set
	 */
	public void setAverageFragmentsIntensities(float averageFragmentsIntensities) {
		this.averageFragmentsIntensities = averageFragmentsIntensities;
	}

	/**
	 * Compute the number of intense peaks. It get the nbPeak last fragment of
	 * the spectrum ( sorted by intensity) and put them in an array. they are
	 * the most intense peaks of the spectrum
	 */
	private void computeNbIntensePeaks() {
		nbPeaks = ConstantComparisonSpectra.getNbPeaks();
		nbIntensePeaks = new Fragment[nbPeaks];
		if (getNbFragments() >= nbPeaks) {
			int firstValue = getNbFragments() - 1;
			int lastValue = firstValue - nbPeaks;

			for (int i = firstValue; i > lastValue; i--) {
				Fragment fragment = getSortedFragments().get(i);
				nbIntensePeaks[firstValue - i] = fragment;
			}
		}
	}

	/**
	 * @return the number of the intense peaks
	 */
	public Fragment[] getNbIntensePeaks() {
		computeNbIntensePeaks();
		return nbIntensePeaks;
	}

	/**
	 * Compute the square root for the nbIntensePeaks compute in
	 * computeNbIntensePeaks()
	 */
	private void computeListSquareRootNbIntensePeaks() {
		nbPeaks = ConstantComparisonSpectra.getNbPeaks();
		squareRootnbIntensePeaks = new Double[nbPeaks];

		for (int i = 0; i < nbPeaks; i++) {
			Fragment fragment = nbIntensePeaks[i];
			float intensity = fragment.getIntensity();
			squareRootnbIntensePeaks[i] = Math.sqrt(intensity);
		}
	}

	/**
	 * @return the list of the square root of the intense peaks.
	 */
	public Double[] getListSquareRootNbIntensePeaks() {
		computeListSquareRootNbIntensePeaks();
		return squareRootnbIntensePeaks;
	}

	/**
	 * @return identified property
	 */
	public BooleanProperty identifiedProperty() {
		return identified;
	}

	/**
	 * @return the value of the identified property
	 */
	public Boolean getIsIdentified() {
		return identified.get();
	}

	/**
	 * @param isIdentified
	 *            the identified value to set
	 */
	public void setIsIdentified(Boolean isIdentified) {
		// this.isIdentified = isIdentified;
		this.identified.set(isIdentified);
	}

	/**
	 * @return the recover property
	 */
	public BooleanProperty recoveredProperty() {
		return recovered;
	}

	/**
	 * 
	 * @return recover property value
	 */
	public Boolean getIsRecovered() {
		return recovered.get();
	}

	/**
	 * 
	 * @param isRecover
	 *            the recover value to set in property
	 */
	public void setIsRecovered(Boolean isRecover) {
		// this.isRecover = isRecover;
		this.recovered.set(isRecover);
	}

	/**
	 * @return the wrong charge property
	 */
	public BooleanProperty getWrongCharge() {
		setWrongCharge(computeWrongCharge());
		return wrongCharge;
	}

	/**
	 * @param wrongCharge
	 *            the wrong charge property to set
	 */
	public void setWrongCharge(BooleanProperty wrongCharge) {
		this.wrongCharge = wrongCharge;
	}

	/**
	 * @param isWrongCharge
	 *            the wrong charge value to set in property
	 */
	public void setWrongCharge(Boolean isWrongCharge) {
		this.wrongCharge.setValue(isWrongCharge);
	}

	/**
	 * @return the ion reporter property
	 */
	public BooleanProperty getIonReporter() {
		// setIonReporter();
		return ionReporter;
	}

	/**
	 * @param ionReporter
	 *            the ion reporter property to set
	 */
	public void setIonReporter(BooleanProperty ionReporter) {
		this.ionReporter = ionReporter;
	}

	/**
	 * @param isIonReporter
	 *            the ion reporter value to set in property
	 */
	public void setIonReporter(Boolean isIonReporter) {
		this.ionReporter.setValue(isIonReporter);
	}

	/**
	 * @return the useful peaks number. The value -1 means that it's not
	 *         initialized.
	 */
	public Integer getUpn() {
		return upn;
	}

	/**
	 * @param upn
	 *            the value of the useful peaks number to set. Its value depends
	 *            on the of user preferences.
	 */
	public void setUpn(Integer upn) {
		this.upn = upn;
	}

	@Deprecated
	public float getHighIntensityThreshold() {
		return highIntensityThreshold;
	}

	@Deprecated
	public void setHighIntensityThreshold(float highIntensityThreshold) {
		this.highIntensityThreshold = highIntensityThreshold;
	}

	/**
	 * 
	 * @return the low intensity threshold
	 */
	public float getLowIntensityThreshold() {
		return lowIntensityThreshold;
	}

	/**
	 * 
	 * @param lowIntensityThreshold
	 *            the lower intensity threshold to set
	 */
	public void setLowIntensityThreshold(float lowIntensityThreshold) {
		this.lowIntensityThreshold = lowIntensityThreshold;
	}

	/**
	 * @return the top line
	 */
	public float getTopLine() {
		return topLine;
	}

	/**
	 * @param _topline
	 *            the top line to set
	 */
	public void setTopLine(float _topline) {
		this.topLine = _topline;
	}

	/**
	 * TODO to remove
	 * 
	 * @return
	 */
	public StatusFilterType getIsRecoverHIT() {
		return isRecoverHIT;
	}

	/**
	 * TODO to remove
	 * 
	 * @param isRecoverHIT
	 */
	public void setIsRecoverHIT(StatusFilterType isRecoverHIT) {
		this.isRecoverHIT = isRecoverHIT;
	}

	/**
	 * 
	 * @return the status of the filter low intensity threshold
	 */
	public StatusFilterType getIsRecoverLIT() {
		return isRecoverLIT;
	}

	/**
	 * 
	 * @param isRecoverLIT
	 *            return the low intensity threshold status
	 */
	public void setIsRecoverLIT(StatusFilterType isRecoverLIT) {
		this.isRecoverLIT = isRecoverLIT;
	}

	/**
	 * TODO to remove this filter
	 * 
	 * @return
	 */
	public StatusFilterType getIsRecoverFI() {
		return isRecoverFI;
	}

	/**
	 * TODO to remove this filter
	 * 
	 * @param isRecoverFI
	 */
	public void setIsRecoverFI(StatusFilterType isRecoverFI) {
		this.isRecoverFI = isRecoverFI;
	}

	/**
	 * @return the status of the wrong charge state.
	 */
	public StatusFilterType getIsRecoverWC() {
		return isRecoverWC;
	}

	/**
	 * @param isRecoverWC
	 *            the wrong charge filter state.
	 */
	public void setIsRecoverWC(StatusFilterType isRecoverWC) {
		this.isRecoverWC = isRecoverWC;
	}

	/**
	 * TODO to remove this filter. this filter has been moved to column filter.
	 * 
	 * @return the state of identified spectra filter
	 */
	public StatusFilterType getIsRecoverIS() {
		return isRecoverIS;
	}

	/**
	 * TODO to remove this filter. this filter has been moved to column filter.
	 * 
	 * @param isRecoverIS
	 *            the state of identified spectra filter to set
	 */
	public void setIsRecoverIS(StatusFilterType isRecoverIS) {
		this.isRecoverIS = isRecoverIS;
	}

	/**
	 * TODO to remove this filter. this filter has been moved to column filter.
	 * 
	 * @return the state of ion reporter filter
	 */
	public StatusFilterType getIsRecoverIR() {
		return isRecoverIR;
	}

	/**
	 * TODO to remove this filter. this filter has been moved to column filter.
	 * 
	 * @param isRecoverIR
	 *            the state of the ion reporter filter to set.
	 */
	public void setIsRecoverIR(StatusFilterType isRecoverIR) {
		this.isRecoverIR = isRecoverIR;
	}

	/**
	 * 
	 * @return the number of fragment over the high intensity threshold.
	 */
	public int getNbFragmentAboveHIT() {
		return nbFragmentAboveHIT;
	}

	/**
	 * TODO to be removed
	 * 
	 * @param nb
	 *            set the number of fragment above the the high intensity
	 *            threshold
	 */
	public void setNbFragmentAboveHIT(int nb) {
		this.nbFragmentAboveHIT = nb;
	}

	/**
	 * 
	 * @return the is flagged proprty
	 */
	public BooleanProperty getIsFlaggedProperty() {
		return isFlagged;
	}

	/**
	 * Determines whether the spectrum is flagged.
	 * 
	 * @return the flagged property value.
	 */
	public Boolean getIsFlagged() {
		return isFlagged.getValue();
	}

	/**
	 * @param isFlagged
	 *            the flag value to set.
	 */
	public void setIsFlagged(Boolean isFlagged) {
		this.isFlagged.setValue(isFlagged);
	}

	/**
	 * @return the cost Theta
	 */
	public double getCosTheta() {
		return cosTheta;
	}

	/**
	 * @param _cosTheta
	 *            the cosTheta value to set.
	 */
	public void setCosThetha(double _cosTheta) {
		this.cosTheta = _cosTheta;
	}

	/**
	 * 
	 * @return Delta Moz with reference spectrum
	 */
	public float getDeltaMozWithReferenceSpectrum() {
		return deltaMozWithReferenceSpectrum;
	}

	/**
	 * 
	 * @param deltaMoz
	 *            the delta Moz to set
	 */
	public void setDeltaMozWithReferenceSpectrum(float deltaMoz) {
		this.deltaMozWithReferenceSpectrum = deltaMoz;
	}

	/**
	 * 
	 * @return the delta retention time with reference spectrum
	 */
	public int getDeltaRetentionTimeWithReferenceSpectrum() {
		return deltaRetentionTimeWithReferenceSpectrum;
	}

	/**
	 * 
	 * @param deltaRT
	 *            the delta retention time to set.
	 */
	public void setDeltaRetentionTimeWithReferenceSpectrum(Integer deltaRT) {
		this.deltaRetentionTimeWithReferenceSpectrum = deltaRT;
	}

	/**
	 * 
	 * @return the number of peaks identical with reference spectrum
	 */
	public int getNbPeaksIdenticalWithReferenceSpectrum() {
		return nbPeaksIdenticalWithReferenceSpectrum;
	}

	/**
	 * 
	 * @param nbPeaksIdentical
	 *            the number of peaks identical with reference spectrum to set
	 */
	public void setNbPeaksIdenticalWithReferenceSpectrum(Integer nbPeaksIdentical) {
		this.nbPeaksIdenticalWithReferenceSpectrum = nbPeaksIdentical;
	}

	/**
	 * 
	 * @return the title of reference spectrum
	 */
	public String getTitleReferenceSpectrum() {
		return titleReferenceSpectrum;
	}

	/**
	 * 
	 * @param titleReferenceSpectrum
	 *            the title of reference spectrum to set
	 */
	public void setTitleReferenceSpectrum(String titleReferenceSpectrum) {
		this.titleReferenceSpectrum = titleReferenceSpectrum;
	}

	/**
	 * 
	 * @return the list of fragment that equals to chart
	 */
	public ArrayList<Fragment> getFragmentEqualsToChart() {
		return fragmentEqualsToChart;
	}

	/**
	 * 
	 * @return the number of spectrum that match. It computes the number of
	 *         spectrum that match using this spectrum as a reference. See
	 *         <code>ComparisonSpectra.main</code>
	 */
	public Integer getNbMatch() {
		computeNbMatch();
		return nbMatch;
	}

	/**
	 * Compute the number of spectrum that match.It computes the number of
	 * spectrum that match using this spectrum as a reference. See
	 * <code>ComparisonSpectra.main</code>
	 */
	private void computeNbMatch() {
		if (this.getNbFragments() >= ConstantComparisonSpectra.getNbPeaks()) {
			ComparisonSpectra.main(this);
			if (ComparisonSpectra.getValidSpectrum().getSpectraAsObservable().size() != 0) {
				nbMatch = ComparisonSpectra.getValidSpectrum().getSpectraAsObservable().size();
			} else {
				nbMatch = 0;
			}
		} else {
			nbMatch = 0;
		}
	}

}
