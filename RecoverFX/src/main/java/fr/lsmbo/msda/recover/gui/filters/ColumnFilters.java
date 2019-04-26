package fr.lsmbo.msda.recover.gui.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

/***
 * Save column filters in HashMap. keep in memory all the column filters(until
 * reset). It initialize all the filters , add, remove a column filter.
 * 
 * @author Aromdhani
 *
 */
public class ColumnFilters {

	private static HashMap<String, List<Object>> ColumnFilters = new HashMap<String, List<Object>>();

	@SuppressWarnings("unused")
	private static ArrayList<Integer> arrayFilter = new ArrayList<Integer>();

	/**
	 * Initialize the HashMap. It remove all the applied filters.
	 * 
	 * @return An empty HashMap.
	 */
	@SuppressWarnings({ "unused" })
	private static HashMap<String, List<Object>> initialize() {
		ColumnFilters.clear();
		return ColumnFilters;
	}

	/***
	 * Get the applied column filters
	 * 
	 * @return The HashMap of column filters
	 */
	public static HashMap<String, List<Object>> getApplied() {
		return ColumnFilters;
	}

	/**
	 * Add a column filter
	 * 
	 * @param The
	 *            Column name with which the specified list of filters is to be
	 *            associated
	 * @param The
	 *            list of filters to be associated with the specified column
	 */
	public static void add(String colmunName, List<Object> filter) {
		ColumnFilters.put(colmunName, filter);
	}

	/**
	 * Remove all applied filters for all columns
	 * 
	 * @return true if the hashMap that hold all columns and list filters is
	 *         empty
	 */
	public static boolean resetAll() {
		if (!ColumnFilters.isEmpty())
			ColumnFilters.clear();
		return ColumnFilters.isEmpty();
	}

	/**
	 * Remove all applied filters for one column
	 * 
	 * @return true if list that hold all filters is empty
	 */
	public static boolean resetOne(String columnName) {
		if (ColumnFilters.containsKey(columnName)) {
			ColumnFilters.put(columnName, new ArrayList<>());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove a filter from the applied filters.
	 * 
	 * @return true if list that hold all filters is empty
	 */
	public static void remove(String columnName) {
		ColumnFilters.remove(columnName);
	}

	/***
	 * Return the description of all applied filters
	 * 
	 * @return the full description of the applied filters .
	 */
	@SuppressWarnings("rawtypes")
	public static String getFullDescription() {
		StringBuilder strBuilder = new StringBuilder();
		ColumnFilters.forEach((k, v) -> {
			int n = 1;
			strBuilder.append("\n").append("###Filters applied on column: ").append(k).append("\n");
			for (Object filter : v) {
				if (filter instanceof NumberOperator<?>) {
					strBuilder.append(n++).append(") ").append("Type:").append(((NumberOperator<?>) filter).getType())
							.append(" ; ").append("value: ").append(((NumberOperator<?>) filter).getValue())
							.append("\n");
				}
				if (filter instanceof StringOperator) {
					strBuilder.append(n++).append(") ").append("Type:").append(((StringOperator) filter).getType())
							.append(" ; ").append("value: ").append(((StringOperator) filter).getValue()).append("\n");
				}
				if (filter instanceof BooleanOperator) {
					strBuilder.append(n++).append(") ").append("Type:").append(((BooleanOperator) filter).getType())
							.append(" ; ").append("value: ").append(((BooleanOperator) filter).getValue()).append("\n");
				}
				if (filter instanceof LowIntensityThresholdFilter) {
					strBuilder.append(n++).append(") ").append("Type: ")
							.append(((LowIntensityThresholdFilter) filter).getType()).append(" ; ").append("value: ")
							.append(((LowIntensityThresholdFilter) filter).getFullDescription()).append("\n");
				}
				if (filter instanceof IdentifiedSpectraFilter) {
					strBuilder.append(n++).append(") ").append("Type: ")
							.append(((IdentifiedSpectraFilter) filter).getType()).append(" ; ").append("value: ")
							.append(((IdentifiedSpectraFilter) filter).getFullDescription()).append("\n");
				}
				if (filter instanceof IonReporterFilter) {
					strBuilder.append(n++).append(") ").append("Type: ").append(((IonReporterFilter) filter).getType())
							.append(" ; ").append("value: ").append(((IonReporterFilter) filter).getFullDescription())
							.append("\n");
				}
			}
		});
		return strBuilder.toString();
	}

}
