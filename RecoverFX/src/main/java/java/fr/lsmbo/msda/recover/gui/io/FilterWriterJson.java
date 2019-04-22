/*
 * 
 */
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.*;

import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;
import fr.lsmbo.msda.recover.gui.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.gui.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.gui.filters.LowIntensityThresholdFilter;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.model.IonReporter;

public class FilterWriterJson {

	/**
	 * Save parameters of filters in a file at format JSON
	 * @param file
	 * 		The file to save filters
	 * @throws IOException
	 */
	public static void saveFilter(File file) throws IOException {

		//TODO Actually not used maybe incorporate it in the file and display somewhere in the filter window.
		Date actualDate = Calendar.getInstance().getTime();
		String nom = System.getProperty("user.name");

		
		LowIntensityThresholdFilter filterLIT =(LowIntensityThresholdFilter) ColumnFilters.getApplied().get("LIT").get(0);
		IdentifiedSpectraFilter filterIS =(IdentifiedSpectraFilter) ColumnFilters.getApplied().get("IS").get(0);
		IonReporterFilter filterIR = (IonReporterFilter) ColumnFilters.getApplied().get("IR").get(0);

		JsonFactory factory = new JsonFactory();
		JsonGenerator generator = factory.createGenerator(file, JsonEncoding.UTF8);

		generator.writeStartObject();
		generator.useDefaultPrettyPrinter();

		// FilterLIT generator

		if (filterLIT != null) {
			generator.writeObjectFieldStart("filterLIT");
			generator.writeNumberField("emergence", filterLIT.getEmergence());
			generator.writeNumberField("minUPN", filterLIT.getMinUPN());
			generator.writeNumberField("maxUPN", filterLIT.getMaxUPN());
			generator.writeStringField("mode", filterLIT.getMode().name());
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
			
			//Scan all the ion contains in ionReporters and save it as an object in the array
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
