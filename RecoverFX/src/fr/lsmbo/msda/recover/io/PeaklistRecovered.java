package fr.lsmbo.msda.recover.io;


import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.filters.HighIntensityThreasholdFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;

public class PeaklistRecovered {
	
	public static void save(File file){
		Date actualDate = Calendar.getInstance().getTime();
		String line;
		String title;
		Spectrum spectrum = null;
		Integer lineNumber = 0;
		ArrayList<String> arrayLine = new ArrayList<String>();
		
		try{
			BufferedWriter writerNewPeaklist = new BufferedWriter(new FileWriter(file));
			BufferedReader reader = new BufferedReader(new FileReader(Session.CURRENT_FILE));
			
			writerNewPeaklist.write("###File created with RECOVER on " + actualDate
									+"\n### " + Spectra.getNbRecover() +" spectrum have been saved"
									+"\n###________________________________________________________");
			writerNewPeaklist.newLine();
			if ((Filters.getFilters().get("HIT"))!=null){
				HighIntensityThreasholdFilter filterHIT = (HighIntensityThreasholdFilter) Filters.getFilters().get("HIT");
				writerNewPeaklist.write(filterHIT.getFullDescription());
			}
			
			//this loop scan the input file and store just one spectrum in an array (until END IONS will be find)
			while((line = reader.readLine()) != null) {
				arrayLine.add(line);
				
				//Write parameters of this peaklist in the new file 
				if (line.matches("^###\\s.*")){
					writerNewPeaklist.write(line + "\n");
				}
				
				//extract the title of the spectrum and recover the corresponding spectrum
				if (line.startsWith("TITLE")){
						title = line.replaceFirst("TITLE.\\s+", "");
						spectrum = Spectra.getSpectrumWithTitle(title);
				}
				
				//check if the spectrum is recover. If yes, write since the array line corresponding to this spectrum (index - 1 because need to write BEGIN IONS
				//before the title.
				if (spectrum != null && spectrum.getIsRecover()){
					writerNewPeaklist.write(arrayLine.get(lineNumber - 1) + "\n");
				}
				
				//if spectrum is recover, write previous line (index - 1) plus "END IONS"
				//set spectrum null and clear the array to store the next spectrum.
				if (line.startsWith("END IONS")){
					if (spectrum != null && spectrum.getIsRecover()){
					writerNewPeaklist.write(arrayLine.get(lineNumber - 1) + "\n" + arrayLine.get(lineNumber) + "\n");
					writerNewPeaklist.newLine();
					}
					spectrum = null;
					arrayLine.clear();
					lineNumber = -1;
			}
				lineNumber++;
			}
			
			writerNewPeaklist.close();
			reader.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		}
}
 