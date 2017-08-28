package fr.lsmbo.msda.recover.model;

import java.util.ArrayList;

public class ConvertorArrayToArrayList {

	public static ArrayList<String> arrayToArrayListString(String[] string){
		ArrayList<String> arrayList = new ArrayList<>();
		for(String st : string){
			arrayList.add(st);
		}
		return arrayList;
	}
}
