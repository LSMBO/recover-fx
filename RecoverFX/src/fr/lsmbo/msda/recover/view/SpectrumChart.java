package fr.lsmbo.msda.recover.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.model.Fragment;
import fr.lsmbo.msda.recover.model.Spectrum;

public class SpectrumChart {

	private JFreeChart chart;
	private Spectrum spectrum;

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
		
        for(Fragment f: spectrum.getFragments()) {
        	float mz = f.getMz();
        	float intensity = f.getIntensity();
        	if(intensity < Session.LOW_INTENSITY_THRESHOLD) {
        		series1.add(mz, intensity);
        	} else if(intensity > Session.HIGH_INTENSITY_THRESHOLD) {
        		series3.add(mz, intensity);
        	} else {
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
		renderer.setSeriesPaint(0, new Color(50,50,255)/* Color.blue*/);
		renderer.setSeriesPaint(1, new Color(255,50,50) /*Color.red*/);
		renderer.setSeriesPaint(2, new Color(100,200,70) /*Color.red*/);
		// hide grid lines
		chart.getXYPlot().setRangeGridlinesVisible(false);
		chart.getXYPlot().setDomainGridlinesVisible(false);
		// set background color
		chart.getXYPlot().setBackgroundPaint(Color.WHITE);
	}
	
	public JFreeChart getChart() {
		return chart;
	}
	
	public void changeAxisRange() {
        NumberAxis domain = (NumberAxis) chart.getXYPlot().getDomainAxis();
        domain.setRange(0.00, Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_MZ : spectrum.getFragmentMaxMoz() * 1.2);
//        domain.setTickUnit(new NumberTickUnit(0.1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) chart.getXYPlot().getRangeAxis();
        range.setRange(0.00, Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_INTENSITY : spectrum.getFragmentMaxIntensity() * 1.2);
//        range.setTickUnit(new NumberTickUnit(0.1));
	}
	
	private void addExtraInformation() {
        // precursor m/z
		chart.getXYPlot().addDomainMarker(createMarker(spectrum.getMz(), spectrum.getMz(), "Precursor M/z", new Color(150, 150, 255)), Layer.BACKGROUND);
        // raw baseline if chosen
	    addThresholdMarker(0F, Session.CALCULATED_NOISE_VALUE = spectrum.getMedianFragmentsIntensities(), "Raw baseline", new Color(255 - 30, 201 - 30, 87 - 30, 220));
        // low intensity threshold if chosen
	    addThresholdMarker(0F, Session.LOW_INTENSITY_THRESHOLD = spectrum.getLowIntensityThreshold(), "Low intensity threshold", new Color(255, 183, 87, 220));
        // high intensity threshold if chosen
	    addThresholdMarker(Session.HIGH_INTENSITY_THRESHOLD = spectrum.getHighIntensityThreshold(), (float)chart.getXYPlot().getRangeAxis().getUpperBound(), "High intensity threshold", new Color(255, 183, 87, 220));
	    // top line
		// status ?
	}
	
	private void addThresholdMarker(Float startValue, Float endValue, String label, Color color) {
		if(startValue >= 0 && endValue >= 0) {
		    chart.getXYPlot().addRangeMarker(createMarker(startValue, endValue, label, color), Layer.BACKGROUND);
		}
	}
	
	private IntervalMarker createMarker(Float startValue, Float endValue, String label, Color color) {
		IntervalMarker marker = new IntervalMarker(startValue, endValue);
		marker.setLabel(label);
		marker.setLabelFont(new Font("SansSerif", Font.ITALIC, 11)); // TODO set generic font style
		marker.setLabelAnchor(RectangleAnchor.TOP_LEFT); // TODO may be different for each marker
		marker.setLabelTextAnchor(TextAnchor.TOP_LEFT); // TODO may be different for each marker
		marker.setPaint(color);
		return marker;
	}
}
