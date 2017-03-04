package fr.lsmbo.msda.recover.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Filters {
	private static HashMap<String, Object> filters = initializeFilters();

	
	public static void add(String nameFilter, Object filter){
		filters.put(nameFilter, filter);
	}
	
	private static HashMap<String, Object> initializeFilters(){
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("HIT",null);
		hashMap.put("LIT",null);
		hashMap.put("FI",null);
		hashMap.put("WC",null);
		hashMap.put("IS", null);
		hashMap.put("IR",null);
		
		return hashMap;
	}
	
	public static HashMap<String, Object> getFilters(){
		return filters;
	}
	
	public static Integer nbFilterUsed(){
		Integer nb = 0;
		for (Map.Entry<String, Object> filtersEntry : filters.entrySet()){
			if (filtersEntry.getValue() != null){
				nb++;
			}
		}
		return nb;
	}
	
	public static void resetHashMap(){
		for (Map.Entry<String, Object> filtersEntry : filters.entrySet()){
			filtersEntry.setValue(null);
		}
	}
	
	public static ArrayList<Integer> getFilterAsAnArray(){
		ArrayList<Integer> arrayFilter = new ArrayList<Integer>();
		if (filters.get("HIT")!=null)
			arrayFilter.add(0);
		if (filters.get("LIT")!=null)
			arrayFilter.add(1);
		if (filters.get("FI")!=null)
			arrayFilter.add(2);
		if (filters.get("WC")!=null)
			arrayFilter.add(3);
		if (filters.get("IS")!=null)
			arrayFilter.add(4);
		if (filters.get("IR")!=null)
			arrayFilter.add(5);
		
		return arrayFilter;
		
	}
}
