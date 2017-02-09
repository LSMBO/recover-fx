package fr.lsmbo.msda.recover.gui;

import fr.lsmbo.msda.recover.model.Spectrum;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class SpectrumChart extends XYChart<Float, Float> {
	
	public SpectrumChart(Axis<Float> xAxis, Axis<Float> yAxis) {
		super(xAxis, yAxis);
	}

	@Override
	protected void dataItemAdded(javafx.scene.chart.XYChart.Series<Float, Float> series, int itemIndex,
			javafx.scene.chart.XYChart.Data<Float, Float> item) {
		
	}

	@Override
	protected void dataItemChanged(javafx.scene.chart.XYChart.Data<Float, Float> item) {
		
	}

	@Override
	protected void dataItemRemoved(javafx.scene.chart.XYChart.Data<Float, Float> item,
			javafx.scene.chart.XYChart.Series<Float, Float> series) {
		
	}

	@Override
	protected void layoutPlotChildren() {
		
	}

	@Override
	protected void seriesAdded(javafx.scene.chart.XYChart.Series<Float, Float> series, int seriesIndex) {
		
	}

	@Override
	protected void seriesRemoved(javafx.scene.chart.XYChart.Series<Float, Float> series) {
		
	}

}
