package fr.lsmbo.msda.recover.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.sun.xml.internal.ws.addressing.W3CAddressingConstants;

import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.WrongChargeFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.model.IonReporter;

public class FiltersWriter {

	public static void saveFilter(File file) {
		Date actualDate = Calendar.getInstance().getTime();
		String nom = System.getProperty("user.name");
		HighIntensityThreasholdFilter filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters().get("HIT");
		LowIntensityThreasholdFilter filterLIT = (LowIntensityThreasholdFilter) Filters.getFilters().get("LIT");
		FragmentIntensityFilter filterFI = (FragmentIntensityFilter) Filters.getFilters().get("FI");
		WrongChargeFilter filterWC = (WrongChargeFilter) Filters.getFilters().get("WC");
		IdentifiedSpectraFilter filterIS = (IdentifiedSpectraFilter) Filters.getFilters().get("IS");
		IonReporterFilter filterIR = (IonReporterFilter) Filters.getFilters().get("IR");

		try {
			BufferedWriter filterSettings = new BufferedWriter(new FileWriter(file));
			filterSettings.write("User : " + nom + " Date : " + actualDate);

			if (filterHIT == null) {
				filterSettings.write(" #filterHIT : false ");
			} else {
				int mostIntensePeaksToConsider = filterHIT.getNbMostIntensePeaksToConsider();
				float percentageOfTopLine = filterHIT.getPercentageOfTopLine();
				int maxNbPeaks = filterHIT.getMaxNbPeaks();
				filterSettings.write(" #filterHIT : true, \"mostIntensePeaksToConsider\" :" + mostIntensePeaksToConsider
						+ ", \"percentageOfTopLine\" :" + percentageOfTopLine + ", \"maxNbPeaks\" :" + maxNbPeaks);
			}

			if (filterLIT == null) {
				filterSettings.write(" #filterLIT : false ");
			} else {
				int emergence = filterLIT.getEmergence();
				int minUPN = filterLIT.getMinUPN();
				int maxUPN = filterLIT.getMaxUPN();
				String mode = filterLIT.getMode().toString();
				filterSettings.write(" #filterLIT : true, \"emergence\" :" + emergence + ", \"minUPN\" :" + minUPN
						+ ", \"maxUPN\" :" + maxUPN + " \"mode\" :" + mode);
			}

			if (filterFI == null) {
				filterSettings.write(" #filterFI : false ");
			} else {
				int intensity = filterFI.getIntensityFragment();
				String comparator = filterFI.getComparator().toString();
				filterSettings
						.write(" #filterFI : true, \"intensity\" :" + intensity + ", \"comparator\" :" + comparator);

			}

			if (filterWC == null) {
				filterSettings.write(" #filterWC : false");
			} else {
				filterSettings.write(" #filterWC : true");
			}

			if (filterIS == null) {
				filterSettings.write(" #filterIS : false ");
			} else {
				Boolean checkRecoverIdentified = IdentifiedSpectraFilter.getCheckRecoverIdentified();
				Boolean checkRecoverNonIdentified = IdentifiedSpectraFilter.getCheckRecoverNonIdentified();
				filterSettings.write(" #filterIS : true, \"checkRecoverIdentified\" :" + checkRecoverIdentified
						+ ", \"checkRecoverNonIdentified\" :" + checkRecoverNonIdentified);
			}

			if (filterIR == null) {
				filterSettings.write(" #filterIR : false ");
			} else {
				Integer nbIon = IonReporters.getIonReporters().size();
				String ionReporterInfo = "";
				for (int k = 0; k < nbIon; k++) {
					IonReporter ionReporter = IonReporters.getIonReporters().get(k);
					ionReporterInfo += "[" + ionReporter.toString() + "] ";
				}
				filterSettings.write(" #filterIR : true, " + ionReporterInfo);
			}
			filterSettings.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
