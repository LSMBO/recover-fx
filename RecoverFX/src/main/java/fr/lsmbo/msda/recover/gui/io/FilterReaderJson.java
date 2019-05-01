/*
 * 
 */
package fr.lsmbo.msda.recover.gui.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

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
				// Boolean operator
				JsonToken token = parser.nextToken();
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Flag") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					BooleanOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "False") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.FALSE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "True") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.TRUE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.NONE, null);
						}
					}
					filters.add((BooleanOperator) filter);
					ColumnFilters.add("Flag", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Ion Reporter") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					BooleanOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "False") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.FALSE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "True") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.TRUE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.NONE, null);
						}
					}
					filters.add((BooleanOperator) filter);
					ColumnFilters.add("Ion Reporter", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Wrong charge") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					BooleanOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "False") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.FALSE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "True") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.TRUE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.NONE, null);
						}
					}
					filters.add((BooleanOperator) filter);
					ColumnFilters.add("Wrong charge", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Identified") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					BooleanOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "False") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.FALSE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "True") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.TRUE, parser.getValueAsBoolean());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new BooleanOperator(BooleanOperator.Type.NONE, null);
						}
					}
					filters.add((BooleanOperator) filter);
					ColumnFilters.add("Identified", filters);
				}
				// String operator
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Title") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					StringOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new StringOperator(StringOperator.Type.EQUALS, parser.getValueAsString());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new StringOperator(StringOperator.Type.NOTEQUALS, parser.getValueAsString());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Contains") {
							token = parser.nextToken();
							filter = new StringOperator(StringOperator.Type.CONTAINS, parser.getValueAsString());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Starts With") {
							token = parser.nextToken();
							filter = new StringOperator(StringOperator.Type.STARTSWITH, parser.getValueAsString());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Ends With") {
							token = parser.nextToken();
							filter = new StringOperator(StringOperator.Type.ENDSWITH, parser.getValueAsString());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new StringOperator(StringOperator.Type.NONE, null);
						}
					}
					filters.add((StringOperator) filter);
					ColumnFilters.add("Title", filters);
				}
				// Number operator
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Id") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Id", filters);
				}

				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Mz") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Mz", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Intensity") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Intensity", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Charge") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Charge", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Retention Time") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Retention Time", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Fragment number") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Fragment number", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Max fragment intensity") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("Max fragment intensity", filters);
				}
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "UPN") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.EQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.GREATERTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.LESSTHANEQUALS, parser.getValueAsInt());
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator(NumberOperator.Type.NONE, null);
						}
					}
					filters.add((NumberOperator) filter);
					ColumnFilters.add("UPN", filters);
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
