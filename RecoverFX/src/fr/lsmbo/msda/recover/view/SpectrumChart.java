package fr.lsmbo.msda.recover.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.filters.HighIntensityThresholdFilter;
import fr.lsmbo.msda.recover.filters.LowIntensityThresholdFilter;
import fr.lsmbo.msda.recover.lists.Filters;
import fr.lsmbo.msda.recover.model.ComparisonSpectra;
import fr.lsmbo.msda.recover.model.ConstantComparisonSpectra;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;

public class SpectrumChart {

	private JFreeChart chart;
	private Spectrum spectrum;
	private ArrayList<Fragment> testReferenceFragment = new ArrayList<>();
	private ArrayList<Fragment> testMatchedFragment = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public SpectrumChart(Spectrum referenceSpectrum, Spectrum matchedSpectrum) {
		XYSeries series2 = new XYSeries("Fragments of the reference spectrum");
		XYSeries series1 = new XYSeries("Fragments of the matched spectrum");
		XYSeries series3 = new XYSeries("Fragments equals between reference spectrum and matched spectrum");
		
		testReferenceFragment = (ArrayList<Fragment>) referenceSpectrum.getFragments().clone();
		testMatchedFragment = (ArrayList<Fragment>) matchedSpectrum.getFragments().clone();
		
		ArrayList<Fragment> fragmentEquals = extractFragmentEquals(testReferenceFragment, testMatchedFragment);
		
		deleteFragmentEqualsFromFragmentList(testReferenceFragment, fragmentEquals);
		deleteFragmentEqualsFromFragmentList(testMatchedFragment, fragmentEquals);

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		//add fragments of the first ArrayList (without fragment equals) in a serie
		for (Fragment f : testReferenceFragment) {
			float mzRS = f.getMz();
			float intensityRS = f.getIntensity();

			series2.add(mzRS, intensityRS);
		}
		
		//add fragments of the second ArrayList (without fragment equals) in a serie
		for (Fragment f : testMatchedFragment) {
			float mzMS = f.getMz();
			float intensityMS = f.getIntensity();

			series1.add(mzMS, intensityMS);
		}
		
		//add fragments equals between the two ArrayList
		for (Fragment f : fragmentEquals){
			float mzFE = f.getMz();
			float intensityFE = f.getIntensity();
			
			series3.add(mzFE, intensityFE);
		}


		

		
		
		// create the plot
		chart = ChartFactory.createXYBarChart(matchedSpectrum.getTitle() + " overlapped with " + referenceSpectrum.getTitle(), "M/z", false, "Intensity", dataset);

		// set default axis ranges
		// changeAxisRange();

		// make it look gorgeous
		// sticks display
		XYStickRenderer renderer = new XYStickRenderer();
		renderer.setBaseStroke(new BasicStroke(0.8f));
		renderer.setSeriesPaint(0, new Color(50, 50, 255)
		/**
		 * Color . blue
		 */
		);
		renderer.setSeriesPaint(1, new Color(255, 50, 50) /*
															 * Color . red
															 */);
		// renderer.setSeriesPaint(2, new Color(100, 200, 70) /*
		// * Color red
		// */);

		// hide grid lines
		chart.getXYPlot().setRangeGridlinesVisible(false);
		chart.getXYPlot().setDomainGridlinesVisible(false);
		// set background color
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
		// chart.getXYPlot().addDomainMarker(
		// createMarker(spectrum.getMz(), spectrum.getMz(), "Precursor M/z", new
		// Color(150, 150, 255)),
		// Layer.BACKGROUND);
		changeAxisRange(referenceSpectrum, matchedSpectrum);

		// display an annotation above the fragment equals used in the algorithm
		for (Fragment f : matchedSpectrum.getFragmentEqualsToChart()) {
			XYPointerAnnotation pointer = new XYPointerAnnotation("", f.getMz(), f.getIntensity(), -1.57);
			pointer.setLabelOffset(20);
			chart.getXYPlot().addAnnotation(pointer);
		}

	}

	public SpectrumChart(Spectrum spectrum) {

		this.spectrum = spectrum;

		// add data
		XYSeries series1 = new XYSeries("Fragments below low intensity threshold");
		XYSeries series2 = new XYSeries("Fragments above low intensity threshold");
		XYSeries series3 = new XYSeries("Fragments above high intensity threshold");

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		for (Fragment f : spectrum.getFragments()) {
			float mz = f.getMz();
			float intensity = f.getIntensity();

			// Any filter applied => all fragment in blue
			if (Filters.getFilters().get("HIT") == null && Filters.getFilters().get("LIT") == null) {
				series2.add(mz, intensity);
			}

			if (Filters.getFilters().get("HIT") != null && Filters.getFilters().get("LIT") == null) {
				if (intensity > (Session.HIGH_INTENSITY_THRESHOLD = spectrum.getHighIntensityThreshold())) {
					series3.add(mz, intensity);
				} else
					series2.add(mz, intensity);
			}

			if (Filters.getFilters().get("LIT") != null && Filters.getFilters().get("HIT") == null) {
				if (intensity < (Session.LOW_INTENSITY_THRESHOLD = spectrum.getLowIntensityThreshold())) {
					series1.add(mz, intensity);
				} else
					series2.add(mz, intensity);
			}

			if (Filters.getFilters().get("HIT") != null && Filters.getFilters().get("LIT") != null) {
				if (intensity > (Session.HIGH_INTENSITY_THRESHOLD = spectrum.getHighIntensityThreshold())) {
					series3.add(mz, intensity);
				} else if (intensity < (Session.LOW_INTENSITY_THRESHOLD = spectrum.getLowIntensityThreshold())) {
					series1.add(mz, intensity);
				} else
					series2.add(mz, intensity);
			}
		}

		// create the plot
		chart = ChartFactory.createXYBarChart(spectrum.getTitle(), "M/z", false, "Intensity", dataset);

		// set default axis ranges
		changeAxisRange();

		// add extra information
		addExtraInformation();

		// make it look gorgeous
		// sticks display
		XYStickRenderer renderer = new XYStickRenderer();
		renderer.setBaseStroke(new BasicStroke(0.8f));
		renderer.setSeriesPaint(0, new Color(50, 50, 255)/*
															 * Color . blue
															 */);
		renderer.setSeriesPaint(1, new Color(255, 50, 50) /*
															 * Color . red
															 */);
		renderer.setSeriesPaint(2, new Color(100, 200, 70) /*
															 * Color . red
															 */);
		// hide grid lines
		chart.getXYPlot().setRangeGridlinesVisible(false);
		chart.getXYPlot().setDomainGridlinesVisible(false);
		// set background color
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
	}

	public SpectrumChart(Spectrum spectrum, String file) {

		this.spectrum = spectrum;

		// add data
		XYSeries series1 = new XYSeries("Fragments below low intensity threshold");
		XYSeries series2 = new XYSeries("Fragments above low intensity threshold");
		XYSeries series3 = new XYSeries("Fragments above high intensity threshold");

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		dataset.addSeries(series3);

		for (Fragment f : spectrum.getFragments()) {
			float mz = f.getMz();
			float intensity = f.getIntensity();
			series2.add(mz, intensity);
		}

		// create the plot
		chart = ChartFactory.createXYBarChart(spectrum.getTitle(), "M/z", false, "Intensity", dataset);

		// set default axis ranges
		changeAxisRange();

		// make it look gorgeous
		// sticks display
		XYStickRenderer renderer = new XYStickRenderer();
		renderer.setBaseStroke(new BasicStroke(0.8f));
		renderer.setSeriesPaint(0, new Color(50, 50, 255)
		/**
		 * Color . blue
		 */
		);
		renderer.setSeriesPaint(1, new Color(255, 50, 50) /*
															 * Color . red
															 */);
		renderer.setSeriesPaint(2, new Color(100, 200, 70) /*
															 * Color red
															 */);

		// hide grid lines
		chart.getXYPlot().setRangeGridlinesVisible(false);
		chart.getXYPlot().setDomainGridlinesVisible(false);
		// set background color
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
		chart.getXYPlot().addDomainMarker(
				createMarker(spectrum.getMz(), spectrum.getMz(), "Precursor M/z", new Color(150, 150, 255)),
				Layer.BACKGROUND);
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void changeAxisRange() {
		NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();
		domain.setRange(0.00,
				Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_MZ : spectrum.getFragmentMaxMoz() * 1.2);
		// domain.setTickUnit(new NumberTickUnit(0.1));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) chart.getXYPlot().getRangeAxis();
		range.setRange(0.00,
				Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_INTENSITY : spectrum.getFragmentMaxIntensity() * 1.2);
		// range.setTickUnit(new NumberTickUnit(0.1));
	}

	public void changeAxisRange(Spectrum referenceSpectrum, Spectrum testedSpectrum) {
		float maxMoz;
		float maxIntensity;

		if (referenceSpectrum.getFragmentMaxMoz() > testedSpectrum.getFragmentMaxMoz()) {
			maxMoz = referenceSpectrum.getFragmentMaxMoz();
		} else {
			maxMoz = testedSpectrum.getFragmentMaxMoz();
		}

		if (referenceSpectrum.getFragmentMaxIntensity() > testedSpectrum.getFragmentMaxIntensity()) {
			maxIntensity = referenceSpectrum.getFragmentMaxIntensity();
		} else {
			maxIntensity = testedSpectrum.getFragmentMaxIntensity();
		}

		NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();
		domain.setRange(0.00, maxMoz * 1.2);
		// domain.setTickUnit(new NumberTickUnit(0.1));
		domain.setVerticalTickLabels(true);
		NumberAxis range = (NumberAxis) chart.getXYPlot().getRangeAxis();
		range.setRange(0.00, maxIntensity * 1.2);

	}

	private void addExtraInformation() {
		// precursor m/z
		chart.getXYPlot().addDomainMarker(
				createMarker(spectrum.getMz(), spectrum.getMz(), "Precursor M/z", new Color(150, 150, 255)),
				Layer.BACKGROUND);
		if (Filters.getFilters().get("LIT") != null) {
			LowIntensityThresholdFilter filterLIT = (LowIntensityThresholdFilter) Filters.getFilters().get("LIT");
			float emergence = filterLIT.getEmergence();
			String mode = filterLIT.getMode().toString();
			// raw baseline if chosen
			if (mode == "MEDIAN")
				addThresholdMarker(0F, Session.CALCULATED_NOISE_VALUE = spectrum.getMedianFragmentsIntensities(),
						"Raw baseline : " + mode + "of all peaks", new Color(255 - 30, 201 - 30, 87 - 30, 220));
			else
				addThresholdMarker(0F, Session.CALCULATED_NOISE_VALUE = spectrum.getAverageFragmentsIntensities(),
						"Raw baseline : " + mode + "  of all peaks", new Color(255 - 30, 201 - 30, 87 - 30, 220));
			// low intensity threshold if chosen
			addThresholdMarker(0F, Session.LOW_INTENSITY_THRESHOLD = spectrum.getLowIntensityThreshold(),
					"Low intensity threshold : " + emergence + " * raw baseline", new Color(255, 183, 87, 220));
		}
		if (Filters.getFilters().get("HIT") != null) {
			HighIntensityThresholdFilter filterHIT = (HighIntensityThresholdFilter) Filters.getFilters().get("HIT");
			int nbMostIntensePeaks = filterHIT.getNbMostIntensePeaksToConsider();
			int percentageTopLine = (int) (filterHIT.getPercentageOfTopLine() * 100);
			// high intensity threshold if chosen
			addThresholdMarker(Session.HIGH_INTENSITY_THRESHOLD = spectrum.getHighIntensityThreshold(),
					Session.TOP_LINE = spectrum.getTopLine(),
					"High intensity threshold : Top Line - " + percentageTopLine + "%", new Color(255, 183, 87, 220));
			addThresholdMarker(Session.TOP_LINE = spectrum.getTopLine(),
					(float) chart.getXYPlot().getRangeAxis().getUpperBound(),
					"Top Line : Average of the " + nbMostIntensePeaks + " most intense peaks",
					new Color(255, 183, 87, 220));
		}
		// top line
		// status ?
	}

	private void addThresholdMarker(Float startValue, Float endValue, String label, Color color) {
		if (startValue >= 0 && endValue >= 0) {
			chart.getXYPlot().addRangeMarker(createMarker(startValue, endValue, label, color), Layer.BACKGROUND);
		}
	}

	private IntervalMarker createMarker(Float startValue, Float endValue, String label, Color color) {
		IntervalMarker marker = new IntervalMarker(startValue, endValue);
		marker.setLabel(label);
		marker.setLabelFont(new Font("SansSerif", Font.ITALIC, 11)); // TODO set
																		// generic
																		// font
																		// style
		marker.setLabelAnchor(RectangleAnchor.TOP_LEFT); // TODO may be
															// different for
															// each marker
		marker.setLabelTextAnchor(TextAnchor.TOP_LEFT); // TODO may be different
														// for each marker
		marker.setPaint(color);
		return marker;
	}

	//Compare two ArrayList of fragment and return a new ArrayList only with Fragment equals between the two ArrayList (+/- delta m/z)
	private ArrayList<Fragment> extractFragmentEquals(ArrayList<Fragment> referenceFragments, ArrayList<Fragment> matchedFragments) {
		ArrayList<Fragment> fragmentEquals = new ArrayList<Fragment>();
		float deltaMoz = ConstantComparisonSpectra.getDeltaMoz();

		//Loop over the first ArrayList
		for (int i = 0; i < referenceFragments.size(); i++) {
			Fragment referencefragment = referenceFragments.get(i);
			float referenceFragmentMoz = referencefragment.getMz();

			//Loop over the second ArrayList
			for (int j = 0; j < matchedFragments.size(); j++) {
				float matchedFragmentMoz = matchedFragments.get(j).getMz();

				//break the second loop if the m/z fragment of the first array is lower than m/z fragment of the second array
				//no need to continue the loop because arrayList was sorted by m/z
				if (referenceFragmentMoz < matchedFragmentMoz - deltaMoz + 0.001) {
					break;
				}

				//check if m/z fragment of the first array is equals (according to delta m/z) to a m/z fragment of the second array
				if (referenceFragmentMoz > (matchedFragmentMoz - deltaMoz) && referenceFragmentMoz < (matchedFragmentMoz + deltaMoz)) {
					//check if the fragment is not already present in the list
					if (!fragmentEquals.contains(referencefragment)) {
						//then add this fragment into the array
						fragmentEquals.add(referencefragment);
					}
				}
			}
		}
		
		return fragmentEquals;
	}
	
	
	private void deleteFragmentEqualsFromFragmentList(ArrayList<Fragment> fragment, ArrayList<Fragment> fragmentEquals){
		
		float deltaMoz = ConstantComparisonSpectra.getDeltaMoz();
		
		//loop over the list of fragment equals
		for(int i = 0; i < fragmentEquals.size();i++){
			float fragmentEqualsMoz = fragmentEquals.get(i).getMz();
			
			//loop over the list of fragment
			for(int j = 0; j < fragment.size(); j++){
				float fragmentMoz = fragment.get(j).getMz();
				Fragment currentFragment = fragment.get(j);
				
				//check if m/z are the same (according to delta m/z) and then remove this fragment from the array list of fragment
				if(fragmentMoz > fragmentEqualsMoz - deltaMoz && fragmentMoz < fragmentEqualsMoz + deltaMoz){
					fragment.remove(currentFragment);
				}
			}
		}
			
	}
}
