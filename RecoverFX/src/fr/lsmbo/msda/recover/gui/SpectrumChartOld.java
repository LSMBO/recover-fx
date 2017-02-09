package fr.lsmbo.msda.recover.gui;
//package fr.lsmbo.msda.recover.gui;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import fr.lsmbo.msda.recover.Session;
//import fr.lsmbo.msda.recover.model.Fragment;
//import fr.lsmbo.msda.recover.model.Spectrum;
//
//public class SpectrumChart {
//	
////	public static BarPlot getPlot1(Spectrum spectrum) {
////		// add data
////		DataTable data1 = new DataTable(Float.class, Float.class);
////		DataTable data2 = new DataTable(Float.class, Float.class);
////		DataTable data3 = new DataTable(Float.class, Float.class);
////		DataTable data4 = new DataTable(Float.class, Float.class);
////        for(Fragment f: spectrum.getFragments()) {
////        	float mz = f.getMz();
////        	float intensity = f.getIntensity();
////        	if(intensity < Session.CALCULATED_NOISE_VALUE) {
////        		data1.add(mz, intensity);
////        	} else if(intensity < Session.LOW_INTENSITY_THRESHOLD) {
////        		data2.add(mz, intensity);
////        	} else if(intensity > Session.HIGH_INTENSITY_THRESHOLD) {
////        		data4.add(mz, intensity);
////        	} else {
////        		data3.add(mz, intensity);
////        	}
////        }
////        
////        // set up series
////    	DataSeries series1 = new DataSeries("Fragments below raw baseline", data1);
////    	DataSeries series2 = new DataSeries("Fragments below low intensity threshold", data2);
////    	DataSeries series3 = new DataSeries("Fragments", data3);
////    	DataSeries series4 = new DataSeries("Fragments above high intensity threshold", data4);
////        
////    	// create the plot
////        BarPlot plot = new BarPlot(series1, series2, series3, series4);
////        plot.getTitle().setText(spectrum.getTitle());
////        
////        // add some styling
////        plot.setInsets(new Insets2D.Double(20, 60, 60, 40));
////        plot.getPointRenderers(series1).get(0).setColor(Color.RED);
////        plot.getPointRenderers(series2).get(0).setColor(Color.GREEN);
////        plot.getPointRenderers(series3).get(0).setColor(Color.BLUE);
////        plot.getPointRenderers(series4).get(0).setColor(Color.YELLOW);
////        
//////        plot.setBounds(0, Session.HIGHEST_FRAGMENT_MZ, 0, Session.HIGHEST_FRAGMENT_INTENSITY);
////        
////        // return full plot
////        return plot;
////	}
//	
//	public static JFreeChart getPlot(Spectrum spectrum) {
//		// add data
//		XYSeries series1 = new XYSeries("Fragments below raw baseline");
//		XYSeries series2 = new XYSeries("Fragments below low intensity threshold");
//		XYSeries series3 = new XYSeries("Fragments");
//		XYSeries series4 = new XYSeries("Fragments above high intensity threshold");
//		
//        XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series1);
//		dataset.addSeries(series2);
//		dataset.addSeries(series3);
//		dataset.addSeries(series4);
//		
//        for(Fragment f: spectrum.getFragments()) {
//        	float mz = f.getMz();
//        	float intensity = f.getIntensity();
//        	if(intensity < Session.CALCULATED_NOISE_VALUE) {
//        		series1.add(mz, intensity);
//        	} else if(intensity < Session.LOW_INTENSITY_THRESHOLD) {
//        		series2.add(mz, intensity);
//        	} else if(intensity > Session.HIGH_INTENSITY_THRESHOLD) {
//        		series4.add(mz, intensity);
//        	} else {
//        		series3.add(mz, intensity);
//        	}
//        }
//        
//    	// create the plot
//        JFreeChart chart = ChartFactory.createXYBarChart(spectrum.getTitle(), "M/z", false, "Intensity", dataset);
//        
//        // add some styling
////        plot.setInsets(new Insets2D.Double(20, 60, 60, 40));
////        plot.getPointRenderers(series1).get(0).setColor(Color.RED);
////        plot.getPointRenderers(series2).get(0).setColor(Color.GREEN);
////        plot.getPointRenderers(series3).get(0).setColor(Color.BLUE);
////        plot.getPointRenderers(series4).get(0).setColor(Color.YELLOW);
//        
////        plot.setBounds(0, Session.HIGHEST_FRAGMENT_MZ, 0, Session.HIGHEST_FRAGMENT_INTENSITY);
////        XYPlot plot = (XYPlot)chart.getPlot();
////        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
////        domain.setRange(0.00, Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_MZ : spectrum.getFragmentMaxMoz());
//////        domain.setTickUnit(new NumberTickUnit(0.1));
////        domain.setVerticalTickLabels(true);
////        NumberAxis range = (NumberAxis) plot.getRangeAxis();
////        range.setRange(0.00, Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_INTENSITY : spectrum.getFragmentMaxIntensity());
//////        range.setTickUnit(new NumberTickUnit(0.1));
//        
//        // return full plot
//        return chart;
//	}
//	
//	public static void changeAxisRange(JFreeChart chart) {
////		XYPlot plot = (XYPlot)chart.getPlot();
////        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
////        domain.setRange(0.00, Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_MZ : spectrum.getFragmentMaxMoz());
//////        domain.setTickUnit(new NumberTickUnit(0.1));
////        domain.setVerticalTickLabels(true);
////        NumberAxis range = (NumberAxis) plot.getRangeAxis();
////        range.setRange(0.00, Session.USE_FIXED_AXIS ? Session.HIGHEST_FRAGMENT_INTENSITY : spectrum.getFragmentMaxIntensity());
//////        range.setTickUnit(new NumberTickUnit(0.1));
//	}
//	
//	public static double getUpperBound(float value, Boolean xAxis) {
//		float realValue = value;
//		if(Session.USE_FIXED_AXIS) {
//			realValue = xAxis ? Session.HIGHEST_FRAGMENT_MZ : Session.HIGHEST_FRAGMENT_INTENSITY;
//		}
//		int rounded = (int)realValue; // 2410.14553 => 2410
//		double divider = Math.pow(10, String.valueOf(rounded).length() - 1);
//		double n = realValue / divider; // 2410.14553 => 2.41014553
//		if((int)Math.round(n) == (int)Math.floor(n)) {
//			return ((int)n + 0.5) * divider; // case 1: value=2410.14553 => ((int)2.41014553 + 0.5) = 2 + 0.5 = 2.5
//		} else {
//			return ((int)n + 1) * divider; // case 2: value=775.657 => ((int)7.75657 + 1) = 7 + 1 = 8
//		}
//	}
//	
//	public static double getTickUnit(double value) {
//		return value * 0.1;
//	}
//}
