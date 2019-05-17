
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import fr.lsmbo.msda.recover.gui.filters.Filters;
import fr.lsmbo.msda.recover.gui.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.gui.filters.LowIntensityThresholdFilter;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.model.IonReporter;

/**
 * Saves and writes filters parameters in JSON file.
 * 
 * @author Aromdhani
 *
 */
public class FilterWriterJson {
	private static final Logger logger = LogManager.getLogger(FilterWriterJson.class);

	/**
	 * Save and write filter parameters in a JSON file
	 * 
	 * @param file
	 *            The file to save filters
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void saveFilter(File file) throws IOException {
		Date actualDate = Calendar.getInstance().getTime();
		String userName = System.getProperty("user.name");
		JsonFactory factory = new JsonFactory();
		JsonGenerator generator = factory.createGenerator(file, JsonEncoding.UTF8);
		generator.writeStartObject();
		generator.useDefaultPrettyPrinter();
		generator.writeStringField("Date", actualDate.toString());
		generator.writeStringField("User", userName);
		// Get and write filters
		generator.writeObjectFieldStart("Filters");
		Filters.getAll().forEach((name, appliedFilters) -> {
			try {
				switch (name) {
				// Apply filter on flag column
				case "Flag": {
					generator.writeObjectFieldStart("Flag");
					for (Object filter : appliedFilters) {
						generator.writeBooleanField(((BooleanOperator) filter).getType().toString(),
								((BooleanOperator) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Id column
				case "Id": {
					generator.writeObjectFieldStart("Id");
					for (Object filter : appliedFilters) {
						generator.writeNumberField(((NumberOperator<Integer>) filter).getType().toString(),
								((NumberOperator<Integer>) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Title column
				case "Title": {
					generator.writeObjectFieldStart("Title");
					for (Object filter : appliedFilters) {
						generator.writeStringField(((StringOperator) filter).getType().toString(),
								((StringOperator) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Mz column
				case "Mz": {
					generator.writeObjectFieldStart("Mz");
					for (Object filter : appliedFilters) {
						String filedName = ((NumberOperator<?>) filter).getType().toString();
						Float value = ((NumberOperator<?>) filter).getValue().floatValue();
						generator.writeNumberField(filedName, value);
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Intensity column
				case "Intensity": {
					generator.writeObjectFieldStart("Intensity");
					for (Object filter : appliedFilters) {
						String filedName = ((NumberOperator<?>) filter).getType().toString();
						Integer value = ((NumberOperator<?>) filter).getValue().intValue();
						generator.writeNumberField(filedName, value);
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Charge column
				case "Charge": {
					generator.writeObjectFieldStart("Charge");
					for (Object filter : appliedFilters) {
						generator.writeNumberField(((NumberOperator<Integer>) filter).getType().toString(),
								((NumberOperator<Integer>) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Fragment number column
				case "Fragment number": {

					generator.writeObjectFieldStart("Fragment number");
					for (Object filter : appliedFilters) {
						generator.writeNumberField(((NumberOperator<Integer>) filter).getType().toString(),
								((NumberOperator<Integer>) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Max fragment intensity column
				case "Max fragment intensity": {

					generator.writeObjectFieldStart("Max fragment intensity");
					for (Object filter : appliedFilters) {
						generator.writeNumberField(((NumberOperator<Integer>) filter).getType().toString(),
								((NumberOperator<Integer>) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on UPN column
				case "UPN": {
					generator.writeObjectFieldStart("UPN");
					for (Object filter : appliedFilters) {
						generator.writeNumberField(((NumberOperator<Integer>) filter).getType().toString(),
								((NumberOperator<Integer>) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Identified column
				case "Identified": {
					generator.writeObjectFieldStart("Identified");
					for (Object filter : appliedFilters) {
						generator.writeBooleanField(((BooleanOperator) filter).getType().toString(),
								((BooleanOperator) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Wrong charge column
				case "Ion Reporter": {
					generator.writeObjectFieldStart("Ion Reporter");
					for (Object filter : appliedFilters) {
						generator.writeBooleanField(((BooleanOperator) filter).getType().toString(),
								((BooleanOperator) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter on Wrong charge column
				case "Wrong charge": {
					generator.writeObjectFieldStart("Wrong charge");
					for (Object filter : appliedFilters) {
						generator.writeBooleanField(((BooleanOperator) filter).getType().toString(),
								((BooleanOperator) filter).getValue());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter IdentifiedSpectraFilter
//				case "IS": {
//					generator.writeObjectFieldStart("IS");
//					for (Object filter : appliedFilters) {
//						generator.writeStringField("file",
//								((IdentifiedSpectraFilter) filter).getFileParams().getFilePath());
//						generator.writeStringField("sheet",
//								((IdentifiedSpectraFilter) filter).getFileParams().getCurrentSheetName());
//						generator.writeStringField("column",
//								((IdentifiedSpectraFilter) filter).getFileParams().getColumn());
//						generator.writeNumberField("row",
//								((IdentifiedSpectraFilter) filter).getFileParams().getRowNumber());
//					}
//					generator.writeEndObject();
//					break;
//				}
				// Apply filter LowIntensityThresholdFilter
				case "LIT": {
					generator.writeObjectFieldStart("LIT");
					for (Object filter : appliedFilters) {
						generator.writeNumberField("emergence", ((LowIntensityThresholdFilter) filter).getEmergence());
						generator.writeStringField("mode", ((LowIntensityThresholdFilter) filter).getMode().name());
					}
					generator.writeEndObject();
					break;
				}
				// Apply filter IonReporterFilter
				case "IR": {
					generator.writeObjectFieldStart("IR");
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
					break;
				}
				// Default
				default:
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Error while trying to write filters parameters!", e);
			}
		});
		generator.writeEndObject();
		generator.close();
	}

}
