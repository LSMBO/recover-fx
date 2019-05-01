/*
 * 
 */
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.IFilterOperator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import fr.lsmbo.msda.recover.gui.filters.ColumnFilters;
import fr.lsmbo.msda.recover.gui.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.gui.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.gui.filters.LowIntensityThresholdFilter;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.model.ComputationTypes;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FilterReaderJson {

	/**
	 * Parse a file at format JSON to set and parameter filters
	 * 
	 * @param file File at format JSON which contains parameters of filters
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public static void load(File file) throws JsonParseException, IOException {

		// Reset all filters before any treatment in case some filter are used.
		// Reset ions reporter
		ColumnFilters.resetAll();
		IonReporters.getIonReporters().clear();
		try {
			JsonFactory factory = new JsonFactory();
			@SuppressWarnings("deprecation")
			JsonParser parser = factory.createJsonParser(file);
			while (!parser.isClosed()) {
				JsonToken token = parser.nextToken();
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Flag") {
					// get values in the object filterLIT
					ObservableList<Object> filters = FXCollections.observableArrayList();
					BooleanOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						System.out.println(token);
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "False") {
							System.out.println(parser.getCurrentName());
							System.out.println(token);
							System.out.println(JsonToken.FIELD_NAME);
							System.out.println(parser.getValueAsBoolean());
							
							filter = new BooleanOperator(BooleanOperator.Type.FALSE, parser.getValueAsBoolean());
						} else {
							filter = new BooleanOperator(BooleanOperator.Type.TRUE, parser.getValueAsBoolean());
						}
					}
					filters.add((BooleanOperator) filter);
					ColumnFilters.add("Flag", filters);
				}
				// Check if filteFrLIT is present then initialize parameters for this filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "LIT") {
					LowIntensityThresholdFilter filterLIT = new LowIntensityThresholdFilter();
					float emergence = 0;
					int minUPN = 0;
					int maxUPN = 0;
					ComputationTypes mode = null;

					// get values in the object filterLIT
					while (!JsonToken.END_OBJECT.equals(token)) {

						token = parser.nextToken();

						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "emergence") {
							token = parser.nextToken();
							emergence = (float) parser.getValueAsDouble();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "minUPN") {
							token = parser.nextToken();
							minUPN = parser.getValueAsInt();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "maxUPN") {
							token = parser.nextToken();
							maxUPN = parser.getValueAsInt();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "mode") {
							token = parser.nextToken();
							mode = ComputationTypes.valueOf(parser.getValueAsString());
						}
					}
					filterLIT.setParameters(emergence, minUPN, maxUPN, mode);

				}

				// FILTER IS
				// Check if filterIS is present then initialize parameters for this filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterIS") {
					IdentifiedSpectraFilter filterIS = new IdentifiedSpectraFilter();
					Boolean checkRecoverIdentified = null;
					Boolean checkRecoverNonIdentified = null;

					// get values in the object filterIS
					while (!JsonToken.END_OBJECT.equals(token)) {

						token = parser.nextToken();

						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "checkRecoverIdentified") {
							token = parser.nextToken();
							checkRecoverIdentified = parser.getValueAsBoolean();
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "checkRecoverNonIdentified") {
							token = parser.nextToken();
							checkRecoverNonIdentified = parser.getValueAsBoolean();
						}
					}
					filterIS.setParameters(checkRecoverIdentified, checkRecoverNonIdentified);

				}

				// FILTER IR
				// Check if filterIR is present then initialize parameters for this filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterIR") {
					IonReporterFilter filterIR = new IonReporterFilter();
					String name = "";
					float moz = 0;
					float tolerance = 0;

					// Just after the token FIELD_NAME is the token START_OBJECT and after is again
					// a token FIELD_NAME.
					// need to go two step further to get the good token
					token = parser.nextToken();
					token = parser.nextToken();

					if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "ionReporter") {

						// loop until end of array. Format are always the same for an ion reporter name,
						// moz, tolerance.
						while (!JsonToken.END_ARRAY.equals(token)) {
							token = parser.nextToken();

							if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "name") {
								token = parser.nextToken();
								name = parser.getValueAsString();
							} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "moz") {
								token = parser.nextToken();
								moz = (float) parser.getValueAsDouble();
							} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "tolerance") {
								token = parser.nextToken();
								tolerance = (float) parser.getValueAsDouble();
							}

							if (JsonToken.END_OBJECT.equals(token)) {
								IonReporters.addIonReporter(new IonReporter(name, moz, tolerance));
							}
						}

					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
