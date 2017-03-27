package fr.lsmbo.msda.recover.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.WrongChargeFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.IonReporter;

public class FiltersReader {

	public static void load(File loadFile) {

		try (BufferedReader reader = new BufferedReader(new FileReader(loadFile))) {

			String line = reader.readLine();
			
			//get back the index of {} to save only the information about filters in a new string
			int firstIndex = line.indexOf("{");
			int lastIndex = line.indexOf("}");
			String allFilters = line.substring(firstIndex + 1, lastIndex);
			int end = allFilters.length();

			//get back index of different filter to save a specific filter in a specific string
			int indexHIT = allFilters.indexOf("\"filterHIT\"");
			int indexLIT = allFilters.indexOf("\"filterLIT\"");
			int indexFI = allFilters.indexOf("\"filterFI\"");
			int indexWC = allFilters.indexOf("\"filterWC\"");
			int indexIS = allFilters.indexOf("\"filterIS\"");
			int indexIR = allFilters.indexOf("\"filterIR\"");

			String settingsHIT = allFilters.substring(indexHIT, indexLIT - 2);
//			System.out.println(settingsHIT);
			String settingsLIT = allFilters.substring(indexLIT, indexFI - 2);
//			System.out.println(settingsLIT);
			String settingsFI = allFilters.substring(indexFI, indexWC - 2);
//			System.out.println(settingsFI);
			String settingsWC = allFilters.substring(indexWC, indexIS - 2);
//			System.out.println(settingsWC);
			String settingsIS = allFilters.substring(indexIS, indexIR - 2);
//			System.out.println(settingsIS);
			String settingsIR = allFilters.substring(indexIR, end);
//			System.out.println(settingsIR);

			
			if (settingsHIT.contains("false")) {
				Filters.add("HIT", null);
			} else {
				
				//find the position of dualpoint and comma to get back the parameter wanted
				int dualPointMost = settingsHIT.indexOf(":", settingsHIT.indexOf("most"));
				int commaMost = settingsHIT.indexOf(",", dualPointMost);
				
				int dualPointPercentage = settingsHIT.indexOf(":", settingsHIT.indexOf("percentage"));
				int commaPercentage = settingsHIT.indexOf(",", dualPointPercentage);
				
				int dualPointMax = settingsHIT.indexOf(":", settingsHIT.indexOf("max"));

				int mostIntensePeaksToConsider = Integer.parseInt(settingsHIT.substring(dualPointMost + 1, commaMost));
				float percentageOfTopLine = Float
						.parseFloat(settingsHIT.substring(dualPointPercentage + 1, commaPercentage));
				int maxNbPeaks = Integer.parseInt(settingsHIT.substring(dualPointMax + 1));

				//instanciate the filter with its parameters
				HighIntensityThreasholdFilter filterHIT = new HighIntensityThreasholdFilter();
				filterHIT.setParameters(mostIntensePeaksToConsider, percentageOfTopLine, maxNbPeaks);
				Filters.add("HIT", filterHIT);

			}

			if (settingsLIT.contains("false")) {
				Filters.add("LIT", null);
			} else {
				int dualPointEmergence = settingsLIT.indexOf(":", settingsLIT.indexOf("emergence"));
				int commaEmergence = settingsLIT.indexOf(",", dualPointEmergence);
				int dualPointMinUPN = settingsLIT.indexOf(":", settingsLIT.indexOf("minUPN"));
				int commaMinUPN = settingsLIT.indexOf(",", dualPointMinUPN);
				int dualPointMaxUPN = settingsLIT.indexOf(":", settingsLIT.indexOf("maxUPN"));
				int commaMaxUPN = settingsLIT.indexOf(",", dualPointMaxUPN);
				int dualPointMode = settingsLIT.indexOf(":", settingsLIT.indexOf("mode"));

				float emergence = Float.parseFloat(settingsLIT.substring(dualPointEmergence + 1, commaEmergence));
				int minUPN = Integer.parseInt(settingsLIT.substring(dualPointMinUPN + 1, commaMinUPN));
				int maxUPN = Integer.parseInt(settingsLIT.substring(dualPointMaxUPN + 1, commaMaxUPN));
				ComputationTypes mode = ComputationTypes.valueOf(settingsLIT.substring(dualPointMode + 1));

				LowIntensityThreasholdFilter filterLIT = new LowIntensityThreasholdFilter();
				filterLIT.setParameters(emergence, minUPN, maxUPN, mode);
				Filters.add("LIT", filterLIT);
			}

			if (settingsFI.contains("false")) {
				Filters.add("FI", null);
			} else {
				int dualPointIntensity = settingsFI.indexOf(":", settingsFI.indexOf("intensity"));
				int commaIntensity = settingsFI.indexOf(",", dualPointIntensity);
				int dualPointComparator = settingsFI.indexOf(":", settingsFI.indexOf("comparator"));

				int intensity = Integer.parseInt(settingsFI.substring(dualPointIntensity + 1, commaIntensity));
				ComparisonTypes comparator = ComparisonTypes.valueOf(settingsFI.substring(dualPointComparator + 1));

				FragmentIntensityFilter filterFI = new FragmentIntensityFilter();
				filterFI.setParameters(intensity, comparator);
				Filters.add("FI", filterFI);
			}

			if (settingsWC.contains("false")) {
				Filters.add("WC", null);
			} else {
				WrongChargeFilter filterWC = new WrongChargeFilter();
				Filters.add("WC", filterWC);
			}

			if (settingsIS.length()<20) {
				Filters.add("IS", null);
			} else {
				int dualPointIdentified = settingsIS.indexOf(":", settingsIS.indexOf("RecoverIdentified"));
				int commaIdentified = settingsIS.indexOf(",", dualPointIdentified);
				int dualPointNonIdentified = settingsIS.indexOf(":", settingsIS.indexOf("NonIdentified"));
				
				Boolean checkRecoverIdentified = Boolean.parseBoolean(settingsIS.substring(dualPointIdentified + 1, commaIdentified));
				Boolean checkRecoverNonIdentified = Boolean.parseBoolean(settingsIS.substring(dualPointNonIdentified + 1));
				
				IdentifiedSpectraFilter filterIS = new IdentifiedSpectraFilter();
				filterIS.setParameters(checkRecoverIdentified, checkRecoverNonIdentified);
				Filters.add("IS", filterIS);
			}

			if (settingsIR.contains("false")) {
				Filters.add("IR", null);
			} else {
				String[] ionReporter = ionReporterAsAnArray(settingsIR);
				for(String string: ionReporter){
					int dualPointName = string.indexOf(":", string.indexOf("Ion reporter"));
					int commaName = string.indexOf(",", dualPointName);
					int dualPointMoz = string.indexOf(":", string.indexOf("m/z"));
					int commaMoz = string.indexOf(",", dualPointMoz);
					int dualPointTolerance = string.indexOf(":", string.indexOf("tolerance"));
					
					String name = string.substring(dualPointName + 1, commaName);
					float moz = Float.parseFloat(string.substring(dualPointMoz + 1, commaMoz));
					float tolerance = Float.parseFloat(string.substring(dualPointTolerance + 1));
					
					IonReporters.add(new IonReporter(name,moz,tolerance));
					IonReporterFilter filterIR = new IonReporterFilter();
					Filters.add("IR", filterIR);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int countNbIon(String string){
		int nbIonReporter=0 ;
		for(int i=0; i < string.length(); i++){
			if(string.charAt(i)==']'){
				nbIonReporter++;
			}
		}
		return nbIonReporter;
	}
	
	public static String[] ionReporterAsAnArray(String string){
		int nbIonReporter = countNbIon(string);
		String[] array = new String[nbIonReporter];
		int lastInstanceClosingBracket = 0;
		
		for(int i=0; i < nbIonReporter;i++){
			if(i==0){
				int firstOpeningBracket = string.indexOf("[");
				int firstClosingBracket = string.indexOf("]");
				lastInstanceClosingBracket = firstClosingBracket + 1;
				String firstIonReporter = string.substring(firstOpeningBracket+1, firstClosingBracket);
				array[i]=firstIonReporter;
			}
			if(i>0){
				int nextOpeningBracket = string.indexOf("[",lastInstanceClosingBracket);
				int nextClosingBracket = string.indexOf("]",lastInstanceClosingBracket);
				lastInstanceClosingBracket = nextClosingBracket + 1;
				String nextIonReporter = string.substring(nextOpeningBracket+1, nextClosingBracket);
				array[i]=nextIonReporter;
			}
		}
		return array;
	}
	
}