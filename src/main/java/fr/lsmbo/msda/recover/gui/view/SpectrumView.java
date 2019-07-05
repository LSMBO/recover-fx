/*
 * 
 */
package fr.lsmbo.msda.recover.gui.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.compomics.util.Export;
import com.compomics.util.enumeration.ImageType;
import com.compomics.util.general.IsotopicDistributionSpectrum;
import com.compomics.util.gui.interfaces.SpectrumAnnotation;
import com.compomics.util.gui.spectrum.DefaultSpectrumAnnotation;
import com.compomics.util.gui.spectrum.GraphicsPanel;
import com.compomics.util.gui.spectrum.ReferenceArea;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import com.compomics.util.interfaces.SpectrumFile;
import com.compomics.util.io.filefilters.PdfFileFilter;

import fr.lsmbo.msda.recover.gui.model.Fragment;
import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Create and displays a spectrum view that enables the plotting of a spectrum
 * with specific annotations .
 * 
 * @author Aromdhani
 *
 */
public class SpectrumView {

	/**
	 * The spectrum to plot
	 */
	private Spectrum spectrum;
	/**
	 * A list of the peaks to plot
	 */
	private ArrayList<Fragment> fragments;
	/**
	 * The panel that can be embedded in the interface
	 */
	private SpectrumPanel panel;
	/**
	 * The reference area
	 */
	private ReferenceArea refArea;

	/**
	 * 
	 * @param spectrumToPlot
	 *            the spectrum to plot
	 */
	public SpectrumView(Spectrum spectrumToPlot) {
		this.spectrum = spectrumToPlot;
		this.fragments = spectrumToPlot.getFragments();
		generateSpectrumPanel(true, true); // Called in the constructor to
											// speed the plot on screen
	}

	/**
	 * Creates the panel that can be embedded in the interface
	 * 
	 * @param pt
	 *            The type of polymer that generated this spectrum
	 * @param withAnnotations
	 *            Choose to include annotation on the spectrum or not.
	 * @param beautify
	 *            Choose to center the spectrum in the spectrum view.
	 */
	private void generateSpectrumPanel(boolean withAnnotations, boolean beautify) {
		// Gather values to plot the spectrum
		HashMap<Double, Double> peaks = new HashMap<>();
		fragments.forEach(fragment -> {
			peaks.put((double) fragment.getMz(), (double) fragment.getIntensity());
		});

		// Create the Compomics SpectrumFile to be plotted
		SpectrumFile sf = new IsotopicDistributionSpectrum();
		sf.setPrecursorMZ(spectrum.getMz());
		sf.setCharge(spectrum.getCharge());
		sf.setFilename(spectrum.getTitle());
		sf.setPeaks(peaks);

		// Create the panel that will hold the spectrum

		panel = new SpectrumPanel(sf, // Spectrum file
				GraphicsPanel.DrawingStyle.LINES, // Lines or dots
				true, // Enable interaction
				Color.blue, // Color of the filename of the spectrum
				50, // Max padding
				false, // Show fileName
				true, // Show precursor details (Mass and charge)
				true, // Show resolution
				0, // MS-level
				false); // Profile mode
		panel.setScientificYAxis(true);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
		panel.showAnnotatedPeaksOnly(true); // Noise in light grey
		panel.setToolTipText("Select 2 peaks to show sequence and left click + ctrl to cancel."
				+ " Select an area to zoom and right click to cancel zoom.");
		if (withAnnotations) {
			List<SpectrumAnnotation> annotations = new ArrayList<>();
			DefaultSpectrumAnnotation precursorAnnotation = new DefaultSpectrumAnnotation(spectrum.getMz(), 0.0,
					Color.BLUE, "Precursor");
			annotations.add(precursorAnnotation);
			/*
			 * Annotate the other peaks
			 */
			spectrum.getFragments() // All the fragments
					.stream() // As a stream
					.filter(d -> d.getMz() != spectrum.getMz()).forEach(f -> {
						DefaultSpectrumAnnotation codingFragmentsAnnotation = new DefaultSpectrumAnnotation(f.getMz(),
								0, Color.LIGHT_GRAY, "•");
						annotations.add(codingFragmentsAnnotation);
					});
			panel.setAnnotations(annotations);
		}
		if (beautify) {
			panel.setAnnotateHighestPeak(true);
			panel.setFilenameColor(Color.BLUE);
			panel.setDataPointAndLineColor(Color.pink, 500);
			panel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			addReferenceArea();
		}
	}

	

	public SpectrumPanel getSpectrumPanel() {
		return panel;
	}

	/**
	 * Add the threshold value
	 * 
	 * @param threshold
	 *            the threshold to set
	 */
	private void addReferenceArea() {
		ReferenceArea refArea = new ReferenceArea("Threshold", "Baseline X Emergence", 0,
				(double) this.spectrum.getLowIntensityThreshold(), Color.GREEN, 0.2f, true, true, true);
		this.refArea = refArea;
		this.panel.addReferenceAreaYAxis(refArea);

	}

}