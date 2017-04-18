package fr.lsmbo.msda.recover.io;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import jdk.nashorn.internal.parser.JSONParser;

public class FilterReaderJson {

	
	public static void load(File loadFile) throws JsonParseException, IOException {
		JsonFactory factory = new JsonFactory();
		@SuppressWarnings("deprecation")
		JsonParser parser = factory.createJsonParser(loadFile);
		
		JsonToken current;
		
		current = parser.nextToken();
		
		while(parser.nextToken() != JsonToken.END_OBJECT){
			String fieldName = parser.getCurrentName();
			if(fieldName.equals("nbMostIntensePeaksToConsider")){
			
			}

			}
		}
		
		
		
		
		
	}
