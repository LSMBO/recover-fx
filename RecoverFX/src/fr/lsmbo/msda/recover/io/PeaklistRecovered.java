package fr.lsmbo.msda.recover.io;


import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.lists.Spectra;
import fr.lsmbo.msda.recover.model.Spectrum;

public class PeaklistRecovered {
	
	public static void save(File file){
		Date actualDate = Calendar.getInstance().getTime();
		String line;
		Integer nb = Spectra.getSpectraAsObservable().size();
		ArrayList<String> arrayLine = new ArrayList<String>();
		
		try{
			BufferedWriter writerNewPeaklist = new BufferedWriter(new FileWriter(file));
			BufferedReader reader = new BufferedReader(new FileReader(Session.CURRENT_FILE));
			
			writerNewPeaklist.write("###File created with RECOVER on " + actualDate
									+"\n### " + Spectra.getNbRecover() +" spectrum have been saved"
									+"\n###________________________________________________________");
			writerNewPeaklist.newLine();
			
			//Initialize an array with all the line of the current peaklist and write into the new file the beginning of this current peaklist.
			while((line = reader.readLine()) != null) {
				arrayLine.add(line);
				
				if (line.matches("^###\\s.*"))
					writerNewPeaklist.write(line + "\n");
			}
			
			System.out.println(arrayLine.get(0));
			
			//verify if a spectrum is recovered. if yes write lines of the desired spectrum
			for(int i=0; i<nb; i++){
				Spectrum spectrum = Spectra.getSpectraAsObservable().get(i);
				if (spectrum.getIsRecover()){
					for(int j = spectrum.getLineStart()-1; j < spectrum.getLineStop(); j++){
						writerNewPeaklist.write(arrayLine.get(j) +"\n");
					}
					writerNewPeaklist.newLine();
				}
			}
			
			writerNewPeaklist.close();
			reader.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		}
}
 