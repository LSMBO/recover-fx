package fr.lsmbo.msda.recover.model;


import java.util.ArrayList;

import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;

public class ComparisonSpectra {
	//Spectra to make comparison
	private static Spectra secondSpectra;
	//A sub list of spectra 
	private static Spectra subListSecondSpectra = new Spectra();
	//Spectra which succeed all the step
	private static Spectra validSpectra = new Spectra();
	//Spectrum reference
	private static Spectrum sp;
	
	//number of peaks equals between reference spectrum (RS) and tested spectrum(TS) (same MOZ and same RT)
	private static int nbPeaksEquals;
	
	private static Integer nbPeaks = ConstantComparisonSpectra.getNbPeaks();
	
	private static Double cosTheta;

	//Arrays which contain at the same index the same peaks (same moz +/- deltaRT) 
	private static float[] peaksRS = new float[nbPeaks];
	private static float[] peaksTS = new float[nbPeaks];

	//Arraylist to display annotation on the graph
	private static ArrayList<Fragment> fragmentEquals = new ArrayList<Fragment>();

	
	//Constant
	private static Float deltaMoz;
	private static Integer deltaRT;
	private static Integer nbPeaksMin;
	private static Integer thetaMin;
	private static double cosThetaMin;

	// reset the value of sublist and the valid spectra every time the algorithm was used (for different reference spectrum) and initialize second peaklist
	private static void initialize() {
		secondSpectra = ListOfSpectra.getSecondSpectra();
		subListSecondSpectra.initialize();
		validSpectra.initialize();
		deltaMoz = ConstantComparisonSpectra.getDeltaMoz();
		deltaRT = ConstantComparisonSpectra.getDeltaRT();
		nbPeaksMin = ConstantComparisonSpectra.getNbPeaksMin();
		thetaMin = ConstantComparisonSpectra.getThetaMin();
		cosThetaMin = Math.cos(Math.toRadians(thetaMin));
		fragmentEquals.clear();

//		System.out.println("deltaMoz: " + deltaMoz + " deltaRT: " + deltaRT + " nbPeaksMin: " + nbPeaksMin + " thetaMin: " + thetaMin + " cosThetaMin: " + cosThetaMin);
	}

	//Reset the array
	private static void resetPeaks() {
		peaksRS = new float[nbPeaks];
		peaksTS = new float[nbPeaks];
	}

	// Method to get a subList of spectra with spectra near to reference
	// spectrum (same moz +/- deltamoz and same RT +/- deltaRT)
	private static void computeSubListSecondSpectra() {
		
		float referenceSpectrumMoz = sp.getMz();
		// RT of reference spectrum in sec.
		int referenceSpectrumRTSec = (int) (sp.getRetentionTime() * 60);

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
					subListSecondSpectra.add(testedSpectrum);
				}
			}
		}
	}

	// Method to check if the nb peaks of the reference spectrum can have their
	// equivalent in a spectrum of sublist (same moz +/- deltaMoz)
	private static void findFragment(Spectrum spectrumSubList) {
		// Recover the nbpeaks most intense of the reference spectrum
		Fragment[] nbIntensePeaks = sp.getnBIntensePeaks();
		
		//reset value of arrays 
		resetPeaks();
		
		//
		int nbFragmentsubList = spectrumSubList.getNbFragments();

		// get fragment of the reference spectrum
		for (int i = 0; i < nbIntensePeaks.length; i++) {
			Fragment fragmentReferenceSpectrum = nbIntensePeaks[i];

			// set the range of moz
			float minMozFragmentReferenceSpectrum = fragmentReferenceSpectrum.getMz() - deltaMoz;
			float maxMozFragmentReferenceSpectrum = fragmentReferenceSpectrum.getMz() + deltaMoz;

			// get fragment of the tested spectrum
			for (int j = 0; j < nbFragmentsubList; j++) {
				Fragment fragmentSubListSpectrum = spectrumSubList.getFragments().get(j);
				float mozFragmentSubListSpectrum = fragmentSubListSpectrum.getMz();

				// check if fragments have the same moz (+/- deltaMoz)
				if (mozFragmentSubListSpectrum >= minMozFragmentReferenceSpectrum
						&& mozFragmentSubListSpectrum <= maxMozFragmentReferenceSpectrum) {
					//if the condition was respected, save the value of the intensity of the fragment in the array (for RS and TS) at the same index
					addpeaksRS(fragmentReferenceSpectrum.getIntensity(), i);
					addpeaksTS(fragmentSubListSpectrum.getIntensity(), i);
					
					//add in the ArrayList of fragmentEquals the most intense fragment (between RS and TS)
					if(fragmentReferenceSpectrum.getIntensity() > fragmentSubListSpectrum.getIntensity()){
						fragmentEquals.add(fragmentReferenceSpectrum);
					} else{
						fragmentEquals.add(fragmentSubListSpectrum);
					}
				}
			}
		}
	}

	//count the number of peak which matched between RS and TS 
	private static void countNbPeak() {
		nbPeaksEquals = 0;
		for (float f : peaksTS) {
			if (f != 0) {
				nbPeaksEquals++;
			}
		}
	}

	//find the non 0 values in the array of tested spectrum, compute the square root of this value and return a new array (size equals to number of peaks identical between TS and RS)
	private static Double[] squareRootpeaksTS() {
		Double[] squareRootpeaksTS = new Double[nbPeaksEquals];
		int j = 0;
		for (int i = 0; i < peaksTS.length; i++) {
			if (peaksTS[i] != 0) {
				squareRootpeaksTS[j] = Math.sqrt(peaksTS[i]);
				j++;
			}
		}
		return squareRootpeaksTS;
	}

	//find the non 0 values in the array of reference spectrum, get back the square root of this value and return a new array (size equals to number of peaks identical between TS and RS)
	private static Double[] squareRootpeaksRS() {
		Double[] squareRootpeaksRS = new Double[nbPeaksEquals];
		int j = 0;
		for (int i = 0; i < peaksRS.length; i++) {
			if (peaksRS[i] != 0) {
				//
				squareRootpeaksRS[j] = sp.getSquareRootnBIntensePeaks()[i];
				j++;
			}
		}
		return squareRootpeaksRS;
	}

	//add a new value in the array
	private static void addpeaksRS(float intensityFragmentReferenceSpectrum, int index) {
		peaksRS[index] = intensityFragmentReferenceSpectrum;
	}

	//add a new value in the array of TS, if a value is present for the same peak of RS, keep the most intense value of intensity
	private static void addpeaksTS(float intensityFragmentSubListSpectrum, int index) {
		if (peaksTS[index] == 0) {
			peaksTS[index] = intensityFragmentSubListSpectrum;
		} else {
			if (peaksTS[index] < intensityFragmentSubListSpectrum) {
				peaksTS[index] = intensityFragmentSubListSpectrum;
			}
		}
	}

	//Compute value of cos theta. RS.peak = intensity of peaks RS ; TS.peak = intensity of peaks TS
	// Cos theta = ∑NB_PEAKS(√RS.peak * √TS.peak)/(√(∑NB_PEAKS(RS.peak))*√(∑NB_PEAKS(TS.peak)))
	private static double computeCosTheta() {
		cosTheta = 0D;
		Double numeratorCosTheta = 0D;

		Double leftDenominator = 0D; 
		Double rightDenominator = 0D;

		Double sumIntensityRS = 0D;
		Double sumIntensityTS = 0D;

		//Compute the numerator of the equation (find the corresponding square root, multiply them and sum)
		for (int i = 0; i < nbPeaksEquals; i++) {
			Double squareRootRefSpec = squareRootpeaksRS()[i];
			Double squareRootTestSpec = squareRootpeaksTS()[i];
			numeratorCosTheta += (squareRootRefSpec * squareRootTestSpec);
		}

		//Compute the square root of the sum of intensity reference Spectrum
		for (int i = 0; i < peaksRS.length; i++) {
			if (peaksRS[i] != 0) {
				sumIntensityRS += peaksRS[i];
			}
		}
		leftDenominator = Math.sqrt(sumIntensityRS);

		//Compute the square root of the sum of intensity tested Spectrum
		for (int i = 0; i < peaksTS.length; i++) {
			if (peaksTS[i] != 0) {
				sumIntensityTS += peaksTS[i];
			}
		}
		rightDenominator = Math.sqrt(sumIntensityTS);

		
		cosTheta = numeratorCosTheta / (leftDenominator * rightDenominator);
		System.out.println(cosTheta);
		return cosTheta;
	}

	public static void test(Spectrum spectrumRef) {
		sp = spectrumRef;
		setReferenceSpectrum(sp);
		initialize();
		computeSubListSecondSpectra();
		if (subListSecondSpectra.getSpectraAsObservable().size() != 0) {
			for (int i = 0; i < subListSecondSpectra.getSpectraAsObservable().size(); i++) {
				Spectrum testedSpectrum = subListSecondSpectra.getSpectraAsObservable().get(i);
				testedSpectrum.setDeltaMozWithRS(  testedSpectrum.getMz() - sp.getMz());
				testedSpectrum.setDeltaRetentionTimeWithRS( (int) ( (testedSpectrum.getRetentionTime() * 60) - (sp.getRetentionTime()*60) ) );
				findFragment(testedSpectrum);
				countNbPeak();
				testedSpectrum.setNbPeaksIdenticalWithRS(nbPeaksEquals);
				//
				if (nbPeaksEquals >= nbPeaksMin) {
					computeCosTheta();
					if (cosTheta >= cosThetaMin) {
						testedSpectrum.setCosThetha(cosTheta);
						validSpectra.add(testedSpectrum);
					}
				}
			}
		}
	}

	public static Spectra getValidSpectrum() {
		return validSpectra;
	}
	
	public static void setReferenceSpectrum(Spectrum referenceSpectrum){
		sp = referenceSpectrum;
	}
	
	public static Spectrum getReferenceSpectrum(){
		return sp;
	}
	
	public static ArrayList<Fragment> getFragmentEquals(){
		return fragmentEquals;
	}
	
	
}
