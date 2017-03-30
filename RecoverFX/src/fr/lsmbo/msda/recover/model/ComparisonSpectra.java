package fr.lsmbo.msda.recover.model;

import java.util.ArrayList;

import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.lists.Spectra;

public class ComparisonSpectra {
	private static Spectra secondSpectra = ListOfSpectra.getSecondSpectra();
	private static Spectra subListSecondSpectra = new Spectra();
	private static final float deltaMoz = 0.007F;
	private static final int deltaRT = 90;
	private static final int nbPeaksMin = 4;
	

	public static void recoverNearSpectrum(Spectrum sp){
		float referenceSpectrumMoz = sp.getMz();
		int referenceSpectrumRTSec = (int)(sp.getRetentionTime() * 60);
		int nbSpectra = secondSpectra.getSpectraAsObservable().size();
		
		for(int i = 0; i < nbSpectra; i++){
			Spectrum testedSpectrum = secondSpectra.getSpectraAsObservable().get(i);
			if( testedSpectrum.getMz() >= (referenceSpectrumMoz-deltaMoz) && testedSpectrum.getMz() <= (referenceSpectrumMoz+deltaMoz) ){
				if( (testedSpectrum.getRetentionTime() *60) >= (referenceSpectrumRTSec - deltaRT) && (testedSpectrum.getRetentionTime() *60) <= (referenceSpectrumRTSec + deltaRT) ){
					subListSecondSpectra.add(testedSpectrum);
				}
			}
		}
		
		if (getSubListSecondSpectra().getSpectraAsObservable().size()!=0){
			testNbPeak(sp);
		}
	}
	
	public static Spectra getSubListSecondSpectra(){
		return subListSecondSpectra;
	}
	
	public static void testNbPeak(Spectrum sp){
		Spectra subList = getSubListSecondSpectra();
		int nbSpectraSublist = subList.getSpectraAsObservable().size();
		int nbPeaks = 0;
		ArrayList<Fragment> fragmentSubList = new ArrayList<>();
		
		//scan 8 most intense peaks of sp
		for(int i=0; i<8;i++){
			//get the ith fragment 
			Fragment fragment = sp.getEightIntensePeaks()[i];
//			System.out.println("Fragment spectrum selected : " + fragment);
			//scan all the spectrum in the sublist
			
			for(int j=0; j<nbSpectraSublist;j++){
				//get the its spectrum in the sublist
				Spectrum spectrumSubList = subList.getSpectraAsObservable().get(j);
//				System.out.println("Spectrum in the subList : " + spectrumSubList);
				//get the nb of fragment of the spectrum
				int nbFragmentSubList = spectrumSubList.getNbFragments();
				//scan all the fragment in the spectrum of sublist
				for(int k=0; k < nbFragmentSubList;k++){
					Fragment fragmentSpectrumSubList = spectrumSubList.getFragments().get(k);
					System.out.println("Fragment in the spectrumSubList : " + fragmentSpectrumSubList);
					if(fragmentSpectrumSubList.getMz() >= (fragment.getMz() - deltaMoz) && fragmentSpectrumSubList.getMz() <= (fragment.getMz() + deltaMoz)){
//						System.out.println("Fragment match with spectrum selected : " + fragment.getMz() + " moz fragmentsublist : " + fragmentSpectrumSubList);
						fragmentSubList.add(fragmentSpectrumSubList);
					}
				}
			}
		}
		
		

		
	}
}
