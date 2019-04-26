/*
 * 
 */
package fr.lsmbo.msda.recover.gui.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.lsmbo.msda.recover.gui.Session;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;
import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Save a new file with only spectra recovered.
 * 
 * @author BL
 *
 */
public class PeaklistWriter {

	private static File fileReader;

	/**
	 * Write a new file of peaklist based on the origin file (Copy from the
	 * origin file spectra recovered)
	 * 
	 * @param file
	 *            the file to write
	 */
	public static void save(File file) {
		Date actualDate = Calendar.getInstance().getTime();
		String line;
		String title = "";
		Spectrum spectrum = null;
		Integer lineNumber = 0;
		ArrayList<String> arrayLine = new ArrayList<String>();
		Spectra spectra;

		// Recover the good spectra according to utilization (export or
		// export_batch)
		if (!ExportBatch.useBatchSpectra) {
			spectra = ListOfSpectra.getFirstSpectra();
			fileReader = Session.CURRENT_FILE;
		} else {
			spectra = ListOfSpectra.getBatchSpectra();
		}
		try {
			BufferedWriter writerNewPeaklist = new BufferedWriter(new FileWriter(file), 32768);
			BufferedReader reader = new BufferedReader(new FileReader(fileReader));

			// header
			writerNewPeaklist.write("###File created with RECOVER on " + actualDate + "\n### " + spectra.getNbRecover()
					+ " spectra recovered" + "\n### " + spectra.getNbIdentified() + " spectra identified"
					+ "\n###________________________________________________________");
			writerNewPeaklist.newLine();

			// this loop scan the input file and store just one spectrum in an
			// array (until END IONS will be found)
			while ((line = reader.readLine()) != null) {
				arrayLine.add(line);

				// Write parameters of this peak list in the new file
				if (line.matches("^###\\s.*")) {
					writerNewPeaklist.write(line + "\n");
				}

				// extract the title of the spectrum and recover the
				// corresponding spectrum
				if (line.startsWith("TITLE")) {
					String titleTest = line.replaceFirst("TITLE.\\s+", "");

					if (!titleTest.contains("TITLE=")) {
						title = line.replaceFirst("TITLE.\\s+", "");
					} else if (titleTest.contains("TITLE=")) {
						title = line.replaceFirst("TITLE=", "");
					}
					spectrum = spectra.getSpectrumWithTitle(title);
				}

				// check if the spectrum is recover. If yes, write since the
				// array line corresponding to this spectrum (index - 1 because
				// need to write BEGIN IONS
				// before the title.
				if (spectrum != null && spectrum.getIsRecovered()) {
					writerNewPeaklist.write(arrayLine.get(lineNumber - 1) + "\n");
				}
				// else if (spectrum != null && spectrum.getIsIdentified()) {
				// writerNewPeaklist.write(arrayLine.get(lineNumber - 1) +
				// "\n");
				// }

				// if spectrum is recover, write previous line (index - 1) plus
				// "END IONS"
				// set spectrum null and clear the array to store the next
				// spectrum.
				if (line.startsWith("END IONS")) {
					if (spectrum != null && spectrum.getIsRecovered()) {
						writerNewPeaklist
								.write(arrayLine.get(lineNumber - 1) + "\n" + arrayLine.get(lineNumber) + "\n");
						writerNewPeaklist.newLine();
					}
					// else if (spectrum != null && spectrum.getIsIdentified())
					// {
					// writerNewPeaklist.write(arrayLine.get(lineNumber - 1) +
					// "\n" + arrayLine.get(lineNumber) + "\n");
					// writerNewPeaklist.newLine();
					// }

					// Reset parameters for the next spectrum
					spectrum = null;
					arrayLine.clear();
					// -1 to start with lineNumber = 0 ( increment just after)
					lineNumber = -1;
				}
				lineNumber++;
			}

			writerNewPeaklist.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setFileReader(File file) {
		fileReader = file;
	}

}
