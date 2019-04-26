/*
 * 
 */
package fr.lsmbo.msda.recover.gui.model;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;

/**
 * This class provides a comparison of spectra.
 * 
 * @author Aromdhani
 * @author ABU
 * @author BL
 *
 */
public class ComparisonSpectra {
	// Spectra to make comparison
	private static Spectra secondSpectra;
	// A sub list of spectra
	private static Spectra subListSecondSpectra = new Spectra();
	// Spectra which succeed all the step
	private static Spectra validSpectra = new Spectra();
	// Spectrum reference
	private static Spectrum referenceSpectrum;

	// Number of peaks equals between reference spectrum (RS) and tested
	// spectrum(TS) (same MOZ and same RT)
	private static int nbPeaksEquals;

	private static Integer nbPeaks;

	private static Double cosTheta;

	// Arrays which contain at the same index the same peaks (same moz +/-
	// deltaRT)
	private static float[] listPeaksReferenceSpectrum;
	private static float[] listPeaksTestedSpectrum;

	private static Double[] listSquareRootpeaksTestedSpectrum;
	private static Double[] listSquareRootpeaksReferenceSpectrum;

	// Constant
	private static Float deltaMoz;
	private static Integer deltaRT;
	private static Integer nbPeaksMin;
	private static Integer thetaMin;
	private static double cosThetaMin;

	/**
	 * Reset the value of sublist and the valid spectra every time the algorithm
	 * was used (for different reference spectrum) and initialize second peak
	 * list
	 */
	private static void initialize() {
		secondSpectra = ListOfSpectra.getSecondSpectra();
		subListSecondSpectra.initialize();
		validSpectra.initialize();
		deltaMoz = ConstantComparisonSpectra.getDeltaMoz();
		deltaRT = ConstantComparisonSpectra.getDeltaRT();
		nbPeaksMin = ConstantComparisonSpectra.getNbPeaksMin();
		thetaMin = ConstantComparisonSpectra.getThetaMin();
		cosThetaMin = Math.cos(Math.toRadians(thetaMin));
		nbPeaks = ConstantComparisonSpectra.getNbPeaks();
	}

	/** Reset the array */
	private static void resetPeaks() {
		listPeaksReferenceSpectrum = new float[nbPeaks];
		listPeaksTestedSpectrum = new float[nbPeaks];
	}

	/**
	 * Compute subList of spectra with spectra near to reference spectrum (same
	 * moz +/- deltamoz and same RT +/- deltaRT)
	 */
	private static void computeSubListSecondSpectra() {

		float referenceSpectrumMoz = referenceSpectrum.getMz();
		// RT of reference spectrum in sec.
		// TODO think if RT was already in seconds
		int referenceSpectrumRTSec = (int) (referenceSpectrum.getRetentionTime() * 60);

		int nbSpectra = secondSpectra.getSpectraAsObservable().size();

		for (int i = 0; i < nbSpectra; i++) {

			Spectrum testedSpectrum = secondSpectra.getSpectraAsObservable().get(i);
			// check if the moz precursor of the tested spectrum is equals to
			// moz precursor of reference spectrum (+/- deltamoz)
			if (testedSpectrum.getMz() >= (referenceSpectrumMoz - deltaMoz)
					&& testedSpectrum.getMz() <= (referenceSpectrumMoz + deltaMoz)) {
				// check if the RT of the tested spectrum is equals to RT of
				// reference spectrum (+/- deltaRT)
				if ((testedSpectrum.getRetentionTime() * 60) >= (referenceSpectrumRTSec - deltaRT)
						&& (testedSpectrum.getRetentionTime() * 60) <= (referenceSpectrumRTSec + deltaRT)) {
					// when this two condition was successful, the tested
					// spectrum is added to the sublist;
					subListSecondSpectra.addSpectrum(testedSpectrum);
				}
			}
		}
	}

	/**
	 * Check if the number of peaks of the reference spectrum can have their
	 * equivalent in a spectrum of sublist (same moz +/- deltaMoz)
	 */
	private static void findFragment(Spectrum spectrumSubList) {
		// Recover the nbpeaks most intense of the reference spectrum
		Fragment[] nbIntensePeaks = referenceSpectrum.getNbIntensePeaks();

		ArrayList<Fragment> fragmentEquals = spectrumSubList.getFragmentEqualsToChart();
		fragmentEquals.clear();

		// reset value of arrays
		resetPeaks();

		//
		int nbFragmentSpectrumSubList = spectrumSubList.getNbFragments();

		// get fragment of the reference spectrum
		for (int i = 0; i < nbIntensePeaks.length; i++) {
			Fragment fragmentReferenceSpectrum = nbIntensePeaks[i];

			// set the range of moz
			float minMozFragmentReferenceSpectrum = fragmentReferenceSpectrum.getMz() - deltaMoz;
			float maxMozFragmentReferenceSpectrum = fragmentReferenceSpectrum.getMz() + deltaMoz;

			// get fragment of the tested spectrum
			for (int j = 0; j < nbFragmentSpectrumSubList; j++) {
				Fragment fragmentSubListSpectrum = spectrumSubList.getFragments().get(j);
				float mozFragmentSubListSpectrum = fragmentSubListSpectrum.getMz();

				// check if fragments have the same moz (+/- deltaMoz)
				if (mozFragmentSubListSpectrum >= minMozFragmentReferenceSpectrum
						&& mozFragmentSubListSpectrum <= maxMozFragmentReferenceSpectrum) {
					// if the condition was respected, save the value of the
					// intensity of the fragment in the array (for RS and TS) at
					// the same index
					addPeakReferenceSpectrum(fragmentReferenceSpectrum.getIntensity(), i);
					addPeakTestedSpectrum(fragmentSubListSpectrum.getIntensity(), i);

					// add in the ArrayList of fragmentEquals the most intense
					// fragment (between RS and TS)
					if (fragmentReferenceSpectrum.getIntensity() > fragmentSubListSpectrum.getIntensity()) {

						fragmentEquals.add(fragmentReferenceSpectrum);
					} else {

						fragmentEquals.add(fragmentSubListSpectrum);
					}
				}
			}
		}
	}

	/** count the number of peak which matched between RS and TS */
	private static void countNbPeak() {
		nbPeaksEquals = 0;
		for (float f : listPeaksTestedSpectrum) {
			if (f != 0) {
				nbPeaksEquals++;
			}
		}
	}

	/**
	 * Find the non 0 values in the array of tested spectrum, compute the square
	 * root of this value and return a new array (size equals to number of peaks
	 * identical between TS and RS)
	 */
	private static void computeListSquareRootpeaksTestedSpectrum() {
		listSquareRootpeaksTestedSpectrum = new Double[nbPeaksEquals];
		int j = 0;
		for (int i = 0; i < listPeaksTestedSpectrum.length; i++) {
			if (listPeaksTestedSpectrum[i] != 0) {
				listSquareRootpeaksTestedSpectrum[j] = Math.sqrt(listPeaksTestedSpectrum[i]);
				j++;
			}
		}
	}

	/**
	 * Find the non 0 values in the array of reference spectrum, get back the
	 * square root of this value and return a new array (size equals to number
	 * of peaks identical between TS and RS)
	 */
	private static void computeListSquareRootpeaksReferenceSpectrum() {
		listSquareRootpeaksReferenceSpectrum = new Double[nbPeaksEquals];
		int j = 0;
		for (int i = 0; i < listPeaksReferenceSpectrum.length; i++) {
			if (listPeaksReferenceSpectrum[i] != 0) {
				//
				listSquareRootpeaksReferenceSpectrum[j] = referenceSpectrum.getListSquareRootNbIntensePeaks()[i];
				j++;
			}
		}
	}

	/**
	 * 
	 * @return the list of square root peaks of the tested spectrum
	 */
	private static Double[] getListSquareRootpeaksTestedSpectrum() {
		computeListSquareRootpeaksTestedSpectrum();
		return listSquareRootpeaksTestedSpectrum;
	}

	/**
	 * 
	 * @return the list of square root peaks of the reference spectrum
	 */
	private static Double[] getListSquareRootpeaksReferenceSpectrum() {
		computeListSquareRootpeaksReferenceSpectrum();
		return listSquareRootpeaksReferenceSpectrum;
	}

	/**
	 * 
	 * @param intensityFragmentReferenceSpectrum
	 * @param index
	 */
	// add a new value in the array
	private static void addPeakReferenceSpectrum(float intensityFragmentReferenceSpectrum, int index) {
		listPeaksReferenceSpectrum[index] = intensityFragmentReferenceSpectrum;
	}

	/**
	 * add a new value in the array of TS, if a value is present for the same
	 * peak of RS, keep the most intense value of intensity
	 **/
	private static void addPeakTestedSpectrum(float intensityFragmentSubListSpectrum, int index) {
		if (listPeaksTestedSpectrum[index] == 0) {
			listPeaksTestedSpectrum[index] = intensityFragmentSubListSpectrum;
		} else {
			if (listPeaksTestedSpectrum[index] < intensityFragmentSubListSpectrum) {
				listPeaksTestedSpectrum[index] = intensityFragmentSubListSpectrum;
			}
		}
	}

	/**
	 * Compute value of cos theta.
	 * 
	 * <pre>
	 * RS.peak = intensity of peaks RS
	 * TS.peak = intensity of peaks TS Cos theta = ∑NB_PEAKS(√RS.peak *
	 * √TS.peak)/(√(∑NB_PEAKS(RS.peak))*√(∑NB_PEAKS(TS.peak)))
	 * </pre>
	 */
	private static void computeCosTheta() {
		cosTheta = 0D;
		Double numeratorCosTheta = 0D;

		Double leftDenominator = 0D;
		Double rightDenominator = 0D;

		Double sumIntensityReferenceSpectrum = 0D;
		Double sumIntensityTestedSpectrum = 0D;

		Double[] arraySquareRootReferenceSpectrum = getListSquareRootpeaksReferenceSpectrum();
		Double[] arraySquareRootTestedSpectrum = getListSquareRootpeaksTestedSpectrum();
		// Compute the numerator of the equation (find the corresponding square
		// root, multiply them and sum)
		for (int i = 0; i < nbPeaksEquals; i++) {
			Double squareRootReferenceSpectrum = arraySquareRootReferenceSpectrum[i];
			Double squareRootTestedSpectrum = arraySquareRootTestedSpectrum[i];
			numeratorCosTheta += (squareRootReferenceSpectrum * squareRootTestedSpectrum);
		}

		// Compute the square root of the sum of intensity reference Spectrum
		for (int i = 0; i < listPeaksReferenceSpectrum.length; i++) {
			if (listPeaksReferenceSpectrum[i] != 0) {
				sumIntensityReferenceSpectrum += listPeaksReferenceSpectrum[i];
			}
		}
		leftDenominator = Math.sqrt(sumIntensityReferenceSpectrum);

		// Compute the square root of the sum of intensity tested Spectrum
		for (int i = 0; i < listPeaksTestedSpectrum.length; i++) {
			if (listPeaksTestedSpectrum[i] != 0) {
				sumIntensityTestedSpectrum += listPeaksTestedSpectrum[i];
			}
		}
		rightDenominator = Math.sqrt(sumIntensityTestedSpectrum);

		cosTheta = numeratorCosTheta / (leftDenominator * rightDenominator);
	}

	/**
	 * 
	 * @param spectrumRef
	 *            the spectrum to set as reference.
	 */
	public static void main(Spectrum spectrumRef) {

		referenceSpectrum = spectrumRef;
		setReferenceSpectrum(referenceSpectrum);
		initialize();
		computeSubListSecondSpectra();
		if (subListSecondSpectra.getSpectraAsObservable().size() != 0) {
			for (int i = 0; i < subListSecondSpectra.getSpectraAsObservable().size(); i++) {
				Spectrum testedSpectrum = subListSecondSpectra.getSpectraAsObservable().get(i);
				findFragment(testedSpectrum);
				countNbPeak();
				testedSpectrum.setNbPeaksIdenticalWithReferenceSpectrum(nbPeaksEquals);
				if (nbPeaksEquals >= nbPeaksMin) {
					computeCosTheta();

					if (cosTheta >= cosThetaMin) {
						testedSpectrum.setCosThetha(cosTheta);
						testedSpectrum.setTitleReferenceSpectrum(referenceSpectrum.getTitle());
						testedSpectrum
								.setDeltaMozWithReferenceSpectrum(testedSpectrum.getMz() - referenceSpectrum.getMz());
						testedSpectrum.setDeltaRetentionTimeWithReferenceSpectrum(
								(int) ((testedSpectrum.getRetentionTime() * 60)
										- (referenceSpectrum.getRetentionTime() * 60)));
						validSpectra.addSpectrum(testedSpectrum);

					}
				}
			}

		}
	}

	/**
	 * 
	 * @return spectra within the validated spectrum
	 */
	public static Spectra getValidSpectrum() {
		return validSpectra;
	}

	/**
	 * 
	 * @param _referenceSpectrum
	 *            the reference spectrum to set.
	 */
	public static void setReferenceSpectrum(Spectrum _referenceSpectrum) {
		referenceSpectrum = _referenceSpectrum;
	}

	/**
	 * 
	 * @return the reference spectrum
	 */
	public static Spectrum getReferenceSpectrum() {
		return referenceSpectrum;
	}

}
