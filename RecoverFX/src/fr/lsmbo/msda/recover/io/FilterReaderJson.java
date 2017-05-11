package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lsmbo.msda.recover.filters.FragmentIntensityFilter;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.IdentifiedSpectraFilter;
import fr.lsmbo.msda.recover.filters.IonReporterFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.filters.WrongChargeFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.IonReporters;
import fr.lsmbo.msda.recover.model.ComparisonTypes;
import fr.lsmbo.msda.recover.model.ComputationTypes;
import fr.lsmbo.msda.recover.model.IonReporter;
import jdk.nashorn.internal.parser.JSONParser;

public class FilterReaderJson {

	public static void load(File loadFile) throws JsonParseException, IOException {
		//Reset filter before any treatment
		Filters.resetHashMap();
		IonReporters.getIonReporters().clear();
		
		try {
			JsonFactory factory = new JsonFactory();
			@SuppressWarnings("deprecation")
			JsonParser parser = factory.createJsonParser(loadFile);

			while (!parser.isClosed()) {
				JsonToken token = parser.nextToken();

				//FILTER HIT
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterHIT") {
					HighIntensityThreasholdFilter filterHIT = new HighIntensityThreasholdFilter();
					int nbMostIntensePeaksToConsider = 0;
					float percentageOfTopLine = 0;
					int maxNbPeaks = 0;

					while (!JsonToken.END_OBJECT.equals(token)) {

						token = parser.nextToken();

						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "nbMostIntensePeaksToConsider") {
							token = parser.nextToken();
							nbMostIntensePeaksToConsider = parser.getValueAsInt();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "percentageOfTopLine") {
							token = parser.nextToken();
							percentageOfTopLine = (float) parser.getValueAsDouble();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "maxNbPeaks") {
							token = parser.nextToken();
							maxNbPeaks = parser.getValueAsInt();
						}
					}
					filterHIT.setParameters(nbMostIntensePeaksToConsider, percentageOfTopLine, maxNbPeaks);
					Filters.add("HIT", filterHIT);
				}
				
				//FILTER LIT
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterLIT") {
					LowIntensityThreasholdFilter filterLIT = new LowIntensityThreasholdFilter();
					float emergence = 0 ;
					int minUPN = 0;
					int maxUPN = 0;
					ComputationTypes mode = null;
					
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
					filterLIT.setParameters(emergence,minUPN,maxUPN,mode);
					Filters.add("LIT", filterLIT);
				}
				
				//FILTER FI
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterFI") {
					FragmentIntensityFilter filterFI = new FragmentIntensityFilter();
					int intensity = 0;
					ComparisonTypes comparator = null;
					
					while (!JsonToken.END_OBJECT.equals(token)) {

						token = parser.nextToken();

						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "intensity") {
							token = parser.nextToken();
							intensity = parser.getValueAsInt();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "comparator") {
							token = parser.nextToken();
							comparator = ComparisonTypes.valueOf(parser.getValueAsString());
						}
					}
					filterFI.setParameters(intensity,comparator);
					Filters.add("FI", filterFI);
				}
				
				//FILTER WC
				if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterWC") {
					WrongChargeFilter filterWC = new WrongChargeFilter();
					Filters.add("WC", filterWC);
				}
				
				//FILTER IS
				if(JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterIS") {
					IdentifiedSpectraFilter filterIS = new IdentifiedSpectraFilter();
					Boolean checkRecoverIdentified = null ;
					Boolean checkRecoverNonIdentified = null;
					
					while (!JsonToken.END_OBJECT.equals(token)) {

						token = parser.nextToken();

						if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "checkRecoverIdentified") {
							token = parser.nextToken();
							checkRecoverIdentified = parser.getValueAsBoolean();
						} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "checkRecoverNonIdentified") {
							token = parser.nextToken();
							checkRecoverNonIdentified = parser.getValueAsBoolean();
						}
					}
					filterIS.setParameters(checkRecoverIdentified,checkRecoverNonIdentified);
					Filters.add("IS", filterIS);
				}
				
				//FILTER IR 
				if(JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "filterIR") {
					IonReporterFilter filterIR = new IonReporterFilter();
					String name = "";
					float moz = 0;
					float tolerance = 0;
					
					token = parser.nextToken();
					token = parser.nextToken();
					
					if(JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "ionReporter"){
						
						while(!JsonToken.END_ARRAY.equals(token)) {
							token = parser.nextToken();
							
							if(JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "name") {
								token = parser.nextToken();
								name = parser.getValueAsString();
							} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "moz"){
								token = parser.nextToken();
								moz = (float) parser.getValueAsDouble();
							} else if (JsonToken.FIELD_NAME.equals(token) && parser.getCurrentName() == "tolerance"){
								token = parser.nextToken();
								tolerance = (float) parser.getValueAsDouble();
							}
							
							if(JsonToken.END_OBJECT.equals(token)){
								IonReporters.addIonReporter(new IonReporter(name,moz,tolerance));
							}
						}
						Filters.add("IR", filterIR);	
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

}
