package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.*;

import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.WrongChargeFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.model.IonReporter;

public class FilterWriterJson {

	public static void saveFilter(File file) throws IOException {

		Date actualDate = Calendar.getInstance().getTime();
		String nom = System.getProperty("user.name");

		// get back all the filter
		HighIntensityThreasholdFilter filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters().get("HIT");
		LowIntensityThreasholdFilter filterLIT = (LowIntensityThreasholdFilter) Filters.getFilters().get("LIT");
		FragmentIntensityFilter filterFI = (FragmentIntensityFilter) Filters.getFilters().get("FI");
		WrongChargeFilter filterWC = (WrongChargeFilter) Filters.getFilters().get("WC");
		IdentifiedSpectraFilter filterIS = (IdentifiedSpectraFilter) Filters.getFilters().get("IS");
		IonReporterFilter filterIR = (IonReporterFilter) Filters.getFilters().get("IR");

		JsonFactory factory = new JsonFactory();
		JsonGenerator generator = factory.createGenerator(file, JsonEncoding.UTF8);

		generator.writeStartObject();
		generator.useDefaultPrettyPrinter();

		// FilterHIT generator
		if (filterHIT != null) {
			generator.writeObjectFieldStart("filterHIT");

			generator.writeNumberField("nbMostIntensePeaksToConsider", filterHIT.getNbMostIntensePeaksToConsider());
			generator.writeNumberField("percentageOfTopLine", filterHIT.getPercentageOfTopLine());
			generator.writeNumberField("maxNbPeaks", filterHIT.getMaxNbPeaks());
			generator.writeEndObject();
		}
		// FilterLIT generator

		if (filterLIT != null) {
			generator.writeObjectFieldStart("filterLIT");
			generator.writeNumberField("emergence", filterLIT.getEmergence());
			generator.writeNumberField("minUPN", filterLIT.getMinUPN());
			generator.writeNumberField("maxUPN", filterLIT.getMaxUPN());
			generator.writeStringField("mode", filterLIT.getMode().name());
			generator.writeEndObject();
		}

		if (filterFI != null) {
			generator.writeObjectFieldStart("filterFI");
			generator.writeNumberField("intensity", filterFI.getIntensityFragment());
			generator.writeStringField("comparator", filterFI.getComparator().name());
			generator.writeEndObject();
		}

		if (filterWC != null) {
			generator.writeObjectFieldStart("filterWC");
			generator.writeEndObject();
		}

		if (filterIS != null) {
			generator.writeObjectFieldStart("filterIS");
			generator.writeBooleanField("checkRecoverIdentified", filterIS.getRecoverSpectrumIdentified());
			generator.writeBooleanField("checkRecoverNonIdentified", filterIS.getRecoverSpectrumNonIdentified());
			generator.writeEndObject();
		}
		if (filterIR != null) {
			generator.writeObjectFieldStart("filterIR");
			generator.writeArrayFieldStart("ionReporter");
			Integer nbIon = IonReporters.getIonReporters().size();
			for (int k = 0; k < nbIon; k++) {
				IonReporter ionReporter = IonReporters.getIonReporters().get(k);
				generator.writeStartObject();
				generator.writeStringField("name", ionReporter.getName());
				generator.writeNumberField("moz", ionReporter.getMoz());
				generator.writeNumberField("tolerance", ionReporter.getTolerance());
				generator.writeEndObject();
			}
			generator.writeEndArray();
			generator.writeEndObject();
		}

		generator.writeEndObject();

		generator.close();
	}

}
