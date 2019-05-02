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

/**
 * Parse and load filters parameters from JSON file.
 * 
 * @author Aromdhani
 * @author Burel
 *
 */
public class FilterReaderJson {

	/**
	 * Parse and load filters parameters from JSON file.
	 * 
	 * @param file
	 *            File at format JSON which contains parameters of filters.
	 * @throws JsonParseException
	 * @throws IOException
	 */
	public static void load(File file) throws JsonParseException, IOException {

		// Reset all filters before any treatment.
		ColumnFilters.resetAll();
		// Reset ions reporter
		IonReporters.getIonReporters().clear();
		try {
			JsonFactory factory = new JsonFactory();
			@SuppressWarnings("deprecation")
			JsonParser parser = factory.createJsonParser(file);
			while (!parser.isClosed()) {
				/**
				 * Boolean operator
				 **/
				// Read Flag filter
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
				// Read Ion Reporter filter
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
				// Read Wrong charge filter
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
				// Read identified filter
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
				/**
				 * String operator
				 */
				// Read Title filter
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
				/**
				 * Number operator
				 */
				// Read Id filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Id") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Id", filters);
				}
				// Read Mz filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Mz") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Mz", filters);
				}
				// Read Intensity filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Intensity") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Intensity", filters);
				}
				// Read Charge filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Charge") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Charge", filters);
				}
				// Read Retention time filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Retention Time") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Retention Time", filters);
				}
				// Read Fragment number filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Fragment number") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Fragment number", filters);
				}
				// Read max fragment intensity filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Max fragment intensity") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("Max fragment intensity", filters);
				}
				// Read useful peaks number filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "UPN") {
					ObservableList<Object> filters = FXCollections.observableArrayList();
					NumberOperator<Integer> filter = null;
					while (!JsonToken.END_OBJECT.equals(token)) {
						token = parser.nextToken();
						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.EQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Not Equals") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NOTEQUALS, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHAN,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Greater Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.GREATERTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHAN, parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token)
								&& parser.getCurrentName() == "Equals/Less Than") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.LESSTHANEQUALS,
									parser.getValueAsInt());
							filters.add((NumberOperator<Integer>) filter);
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "No Filter") {
							token = parser.nextToken();
							filter = new NumberOperator<Integer>(NumberOperator.Type.NONE, null);
							filters.add((NumberOperator<Integer>) filter);
						}
					}

					ColumnFilters.add("UPN", filters);
				}
				// Check if filteFrLIT is present then initialize parameters for
				// this filter
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
				// Check if filterIS is present then initialize parameters for
				// this filter
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
				// Check if filterIR is present then initialize parameters for
				// this filter
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterIR") {
					IonReporterFilter filterIR = new IonReporterFilter();
					String name = "";
					float moz = 0;
					float tolerance = 0;

					// Just after the token FIELD_NAME is the token START_OBJECT
					// and after is again
					// a token FIELD_NAME.
					// need to go two step further to get the good token
					token = parser.nextToken();
					token = parser.nextToken();

					if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "ionReporter") {

						// loop until end of array. Format are always the same
						// for an ion reporter name,
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
