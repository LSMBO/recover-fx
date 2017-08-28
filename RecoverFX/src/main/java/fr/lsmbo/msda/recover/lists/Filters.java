package fr.lsmbo.msda.recover.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Save information of filters used into an hashmap. Keep parameters in memory for all filters
 * (until reset). Method to count number of filter used, to reset filter used in the hashmap and to
 * return filter used as an array (filter was described as an index, 0 for HIT etc..)
 * 
 * @author BL
 *
 */
public class Filters {
	private static HashMap<String, Object> filters = initializeFilters();
	private static ArrayList<Integer> arrayFilter = new ArrayList<Integer>();

	public static void add(String nameFilter, Object filter) {
		filters.put(nameFilter, filter);
	}

	// Initialize the hashmap which will be receive the different filter.
	private static HashMap<String, Object> initializeFilters() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("HIT", null);
		hashMap.put("LIT", null);
		hashMap.put("FI", null);
		hashMap.put("WC", null);
		hashMap.put("IS", null);
		hashMap.put("IR", null);

		return hashMap;
	}

	public static HashMap<String, Object> getFilters() {
		return filters;
	}

	// Count the number of filter used(scan the hashmap and increment the number
	// every time the value was not null)
	public static Integer nbFilterUsed() {
		Integer nb = 0;
		for (Map.Entry<String, Object> filtersEntry : filters.entrySet()) {
			if (filtersEntry.getValue() != null) {
				nb++;
			}
		}
		return nb;
	}

	// set the value to null for all filters
	public static void resetHashMap() {
		for (Map.Entry<String, Object> filtersEntry : filters.entrySet()) {
			filtersEntry.setValue(null);
		}
	}

	// return an array of filter describe as an index. If a filter x was used,
	// put in the array the corresponding index
	public static void computeFilterAsAnArray() {
		arrayFilter.clear();

		if (filters.get("HIT") != null)
			arrayFilter.add(0);
		if (filters.get("LIT") != null)
			arrayFilter.add(1);
		if (filters.get("FI") != null)
			arrayFilter.add(2);
		if (filters.get("WC") != null)
			arrayFilter.add(3);
		if (filters.get("IS") != null)
			arrayFilter.add(4);
		if (filters.get("IR") != null)
			arrayFilter.add(5);
	}

	public static ArrayList<Integer> getFilterAsAnArray() {
		return arrayFilter;
	}
}
