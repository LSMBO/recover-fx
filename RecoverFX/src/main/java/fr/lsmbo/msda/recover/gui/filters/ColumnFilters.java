package fr.lsmbo.msda.recover.gui.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.google.jhsheets.filtered.operators.BooleanOperator;
import org.google.jhsheets.filtered.operators.NumberOperator;
import org.google.jhsheets.filtered.operators.StringOperator;

import fr.lsmbo.msda.recover.gui.lists.IdentifiedSpectra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/***
 * Save column filters in HashMap. keep in memory all the column filters(until
 * reset). It initialize, put all the filters , add, remove a column filter.
 * 
 * @author Aromdhani
 *
 */
public class ColumnFilters {

	private static HashMap<String, ObservableList<Object>> filtersByNameMap = new HashMap<String, ObservableList<Object>>();

	@SuppressWarnings("unused")
	private static ArrayList<Integer> arrayFilter = new ArrayList<Integer>();

	/**
	 * Initialize the HashMap. It remove all the applied filters.
	 * 
	 * @return An empty HashMap.
	 */
	@SuppressWarnings({ "unused" })
	private static HashMap<String, ObservableList<Object>> initialize() {
		filtersByNameMap.clear();
		return filtersByNameMap;
	}

	/***
	 * Return the applied column filters
	 * 
	 * @return HashMap of column filters
	 */
	public static HashMap<String, ObservableList<Object>> getAll() {
		return filtersByNameMap;
	}

	/**
	 * Add a column filter
	 * 
	 * @param The Column name with which the specified list of filters is to be
	 *            associated
	 * @param The list of filters to be associated with the specified column
	 */
	public static void add(String colmunName, ObservableList<Object> filters) {
		filtersByNameMap.put(colmunName, filters);
	}

	/**
	 * Remove all applied filters for all columns
	 * 
	 * @return true if the hashMap that hold all columns and list filters is empty
	 */
	public static boolean resetAll() {
		if (!filtersByNameMap.isEmpty())
			filtersByNameMap.clear();
		return filtersByNameMap.isEmpty();
	}

	/**
	 * Remove all applied filters for one column
	 * 
	 * @return true if list that hold all filters is empty
	 */
	public static boolean resetOne(String columnName) {
		if (filtersByNameMap.containsKey(columnName)) {
			filtersByNameMap.put(columnName, FXCollections.observableArrayList());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove a filter from the applied filters.
	 * 
	 * @return true if the list that hold all filters is empty
	 */
	public static void remove(String columnName) {
		filtersByNameMap.remove(columnName);
	}

	/***
	 * Copies all of the mappings from the specified map of filters to this map of
	 * stored filters. These mappings will replace any mappings that this map had
	 * for any of the keys currently in the specified map.
	 * 
	 * @param filtersListMap the map of filters to add
	 * 
	 */
	public static void addAll(Map<String, ObservableList<Object>> filtersListMap) {
		filtersByNameMap.putAll(filtersListMap);
	}

	/***
	 * Return the description of all applied filters
	 * 
	 * @return the full description of the applied filters .
	 */
	public static String getFullDescription() {
		StringBuilder strBuilder = new StringBuilder();
		TreeMap<String, ObservableList<Object>> filtersByNameTreeMap = new TreeMap<>();
		filtersByNameTreeMap.clear();
		filtersByNameTreeMap.putAll(ColumnFilters.getAll());
		filtersByNameTreeMap.forEach((name, filters) -> {
			int n = 1;
			strBuilder.append("\n").append("###Filters applied on column: ").append(name).append("\n");
			for (Object filter : filters) {
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
				if (filter instanceof IdentifiedSpectra) {
					strBuilder.append(n++).append(") ").append("Type: ").append(((IdentifiedSpectra) filter).getType())
							.append(" ; ").append("value: ").append(((IdentifiedSpectra) filter).getFullDescription())
							.append("\n");
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
