package fr.lsmbo.msda.recover.model;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;

public class ComparisonSpectra {
	private static Spectra secondSpectra = ListOfSpectra.getSecondSpectra();
	private static Spectra subListSecondSpectra = new Spectra();
	private static int nbPeaks;
	private static Spectrum sp;
	private static float[] peaksSR = new float[Session.NB_PEAKS];
	private static float[] peaksST = new float[Session.NB_PEAKS];

	private static final float deltaMoz = 0.007F;
	private static final int deltaRT = 90;
	private static final int nbPeaksMin = 4;

	//initialize value  
	private static void initialize(){
		subListSecondSpectra.initialize();
		peaksSR = new float[Session.NB_PEAKS];
		peaksST = new float[Session.NB_PEAKS];
		nbPeaks = 0;
	}
	
	
	//Method to get a subList of spectra with spectra near to reference spectrum (same moz +/- deltamoz and same RT +/- deltaRT)
	private static void computeSubListSecondSpectra() {
		float referenceSpectrumMoz = sp.getMz();
		//RT of reference spectrum in sec.
		int referenceSpectrumRTSec = (int) (sp.getRetentionTime() * 60);
		
		int nbSpectra = secondSpectra.getSpectraAsObservable().size();

		
		for (int i = 0; i < nbSpectra; i++) {
			
			Spectrum testedSpectrum = secondSpectra.getSpectraAsObservable().get(i);
			//check if the moz precursor of the tested spectrum is equals to moz precursor of reference spectrum (+/- deltamoz)
			if (testedSpectrum.getMz() >= (referenceSpectrumMoz - deltaMoz) && testedSpectrum.getMz() <= (referenceSpectrumMoz + deltaMoz)) {
				//check if the RT of the tested spectrum is equals to RT of reference spectrum (+/- deltaRT)
				if ((testedSpectrum.getRetentionTime() * 60) >= (referenceSpectrumRTSec - deltaRT) && (testedSpectrum.getRetentionTime() * 60) <= (referenceSpectrumRTSec + deltaRT)) {
					//when this two condition was successful, the tested spectrum is added to the sublist;
					subListSecondSpectra.add(testedSpectrum);
				}
			}
		}
	}

	//Method to check if the nb peaks of the reference spectrum can have their equivalent in a spectrum of sublist (same moz +/- deltaMoz)
	private static void findFragment(Spectrum spectrumSubList) {
		//Recover the nbpeaks most intense of the reference spectrum
		Fragment[] nbIntensePeaks = sp.getnBIntensePeaks();
		
		int nbFragmentsubList = spectrumSubList.getNbFragments();
		
		// get fragment of the reference spectrum
		for (int i = 0; i < nbIntensePeaks.length; i++) {
			Fragment fragmentReferenceSpectrum = nbIntensePeaks[i];
			
			//set the range of moz
			float minMozFragmentReferenceSpectrum = fragmentReferenceSpectrum.getMz() - deltaMoz;
			float maxMozFragmentReferenceSpectrum = fragmentReferenceSpectrum.getMz() + deltaMoz;

			//get fragment of the tested spectrum
			for (int j = 0; j < nbFragmentsubList; j++) {
				Fragment fragmentSubListSpectrum = spectrumSubList.getFragments().get(j);
				float mozFragmentSubListSpectrum = fragmentSubListSpectrum.getMz();
				
				//check if fragments have the same moz (+/- deltaMoz)
				if (mozFragmentSubListSpectrum >= minMozFragmentReferenceSpectrum && mozFragmentSubListSpectrum <= maxMozFragmentReferenceSpectrum) {
					addPeaksSR(fragmentReferenceSpectrum.getIntensity(), i);
					addPeaksST(fragmentSubListSpectrum.getIntensity(), i);
				}
			}
		}
	}

	private static void countNbPeak() {
		for (float f : peaksST) {
			if (f != 0) {
				nbPeaks++;
			}
		}
	}

	private static Double[] squareRootPeaksST() {
		Double[] squareRootPeaksST = new Double[nbPeaks];
		int j = 0;
		for (int i = 0; i < peaksST.length; i++) {
			if (peaksST[i] != 0) {
				squareRootPeaksST[j] = Math.sqrt(peaksST[i]);
				j++;
			}
		}
		return squareRootPeaksST;
	}
	
	private static Double[] squareRootPeaksSR(){
		Double [] squareRootPeaksSR = new Double[nbPeaks];
		int j = 0;
		for(int i = 0; i < peaksSR.length;i++){
			if(peaksSR[i]!=0){
				squareRootPeaksSR[j] = sp.getSquareRootnBIntensePeaks()[i];
				j++;
			}
		}
		return squareRootPeaksSR;
	}


	private static void addPeaksSR(float intensityFragmentReferenceSpectrum, int index) {
		peaksSR[index] = intensityFragmentReferenceSpectrum;
	}

	private static void addPeaksST(float intensityFragmentSubListSpectrum, int index) {
		if (peaksST[index] == 0) {
			peaksST[index] = intensityFragmentSubListSpectrum;
		} else {
			if (peaksST[index] < intensityFragmentSubListSpectrum) {
				peaksST[index] = intensityFragmentSubListSpectrum;
			}
		}
	}

	private static double computeCosTheta() {
		Double cosTheta = 0D;
		
		Double numeratorCosTheta = 0D;
		
		Double leftDenominator = 0D;
		Double rightDenominator = 0D;
		
		Double sumIntensitySR = 0D;
		Double sumIntensityST = 0D;
		
		for (int i = 0; i < nbPeaks; i++) {
			Double squareRootRefSpec = squareRootPeaksSR()[i];
			Double squareRootTestSpec = squareRootPeaksST()[i];
			numeratorCosTheta += (squareRootRefSpec * squareRootTestSpec);
		}

		for (int i = 0; i < peaksSR.length; i++){
			if(peaksSR[i]!=0){
				sumIntensitySR += peaksSR[i];
			}
		}
		leftDenominator = Math.sqrt(sumIntensitySR);
		
		for (int i = 0; i < peaksST.length; i++){
			if(peaksST[i]!=0){
				sumIntensityST += peaksST[i];
			}
		}
		rightDenominator = Math.sqrt(sumIntensityST);
		
		cosTheta = numeratorCosTheta / (leftDenominator*rightDenominator);
		return cosTheta;
	}

	public static void test(Spectrum spectrumRef) {
		sp = spectrumRef;
		initialize();
		computeSubListSecondSpectra();
		if (subListSecondSpectra.getSpectraAsObservable().size() != 0) {
			findFragment(subListSecondSpectra.getSpectraAsObservable().get(0));
			countNbPeak();
			for (float f : peaksSR) {
				System.out.println("intensité correspondante : " + f);
			}
			System.out.println("\n");
			for (float f : peaksST) {
				System.out.println("intensité correspondante : " + f);
			}
			System.out.println(nbPeaks);
			for(Double d: squareRootPeaksSR()){
				System.out.println("Square root ref : " + d);
			}
			for(Double dou: squareRootPeaksST()){
				System.out.println("Square root test : " + dou);
			}
			System.out.println(computeCosTheta());
		}
	}
}
