package fr.lsmbo.msda.recover.gui;

import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;

public class SpectrumChartExtendXYChart extends XYChart<Float, Float> {

	public SpectrumChartExtendXYChart(Axis<Float> xAxis, Axis<Float> yAxis) {
		super(xAxis, yAxis);
	}

	/*
	 * Called when a data item has been added to a series. This is where
	 * implementations of XYChart can create/add new nodes to getPlotChildren to
	 * represent this data item. They also may animate that data add with a fade
	 * in or similar if animated = true. Parameters: series - The series the
	 * data item was added to itemIndex - The index of the new item within the
	 * series item - The new data item that was added
	 */
	@Override
	protected void dataItemAdded(javafx.scene.chart.XYChart.Series<Float, Float> series, int itemIndex,
			javafx.scene.chart.XYChart.Data<Float, Float> item) {

	}

	/*
	 * Called when a data item has changed, ie its xValue, yValue or extraValue
	 * has changed. Parameters: item - The data item who was changed
	 */
	@Override
	protected void dataItemChanged(javafx.scene.chart.XYChart.Data<Float, Float> item) {

	}

	/*
	 * Called when a data item has been removed from data model but it is still
	 * visible on the chart. Its still visible so that you can handle animation
	 * for removing it in this method. After you are done animating the data
	 * item you must call removeDataItemFromDisplay() to remove the items node
	 * from being displayed on the chart. Parameters: item - The item that has
	 * been removed from the series series - The series the item was removed
	 * from
	 */
	@Override
	protected void dataItemRemoved(javafx.scene.chart.XYChart.Data<Float, Float> item,
			javafx.scene.chart.XYChart.Series<Float, Float> series) {

	}

	/*
	 * Called to update and layout the plot children. This should include all
	 * work to updates nodes representing the plot on top of the axis and grid
	 * lines etc. The origin is the top left of the plot area, the plot area
	 * with can be got by getting the width of the x axis and its height from
	 * the height of the y axis.
	 */
	@Override
	protected void layoutPlotChildren() {

	}

	/*
	 * A series has been added to the charts data model. This is where
	 * implementations of XYChart can create/add new nodes to getPlotChildren to
	 * represent this series. Also you have to handle adding any data items that
	 * are already in the series. You may simply call dataItemAdded() for each
	 * one or provide some different animation for a whole series being added.
	 * Parameters: series - The series that has been added seriesIndex - The
	 * index of the new series
	 */
	@Override
	protected void seriesAdded(javafx.scene.chart.XYChart.Series<Float, Float> series, int seriesIndex) {

	}

	/*
	 * A series has been removed from the data model but it is still visible on
	 * the chart. Its still visible so that you can handle animation for
	 * removing it in this method. After you are done animating the data item
	 * you must call removeSeriesFromDisplay() to remove the series from the
	 * display list. Parameters: series - The series that has been removed
	 */
	@Override
	protected void seriesRemoved(javafx.scene.chart.XYChart.Series<Float, Float> series) {

	}

	/*
	 * Other callable/overridable methods: public Axis<X> getXAxis() public
	 * Axis<Y> getYAxis() public final ObservableList<XYChart.Series<X,Y>>
	 * getData() public final void setData(ObservableList<XYChart.Series<X,Y>>
	 * value) public final ObjectProperty<ObservableList<XYChart.Series<X,Y>>>
	 * dataProperty() public final boolean getVerticalGridLinesVisible() public
	 * final void setVerticalGridLinesVisible(boolean value) public final
	 * BooleanProperty verticalGridLinesVisibleProperty() public final boolean
	 * isHorizontalGridLinesVisible() public final void
	 * setHorizontalGridLinesVisible(boolean value) public final BooleanProperty
	 * horizontalGridLinesVisibleProperty() public final boolean
	 * isAlternativeColumnFillVisible() public final void
	 * setAlternativeColumnFillVisible(boolean value) public final
	 * BooleanProperty alternativeColumnFillVisibleProperty() public final
	 * boolean isAlternativeRowFillVisible() public final void
	 * setAlternativeRowFillVisible(boolean value) public final BooleanProperty
	 * alternativeRowFillVisibleProperty() public final boolean
	 * isVerticalZeroLineVisible() public final void
	 * setVerticalZeroLineVisible(boolean value) public final BooleanProperty
	 * verticalZeroLineVisibleProperty() public final boolean
	 * isHorizontalZeroLineVisible() public final void
	 * setHorizontalZeroLineVisible(boolean value) public final BooleanProperty
	 * horizontalZeroLineVisibleProperty() protected ObservableList<Node>
	 * getPlotChildren() protected void updateLegend() protected void
	 * seriesChanged(ListChangeListener.Change<? extends XYChart.Series> c)
	 * protected void updateAxisRange() protected abstract void
	 * layoutPlotChildren() protected final void layoutChartChildren(double top,
	 * double left, double width, double height) protected final void
	 * removeSeriesFromDisplay(XYChart.Series<X,Y> series) protected final
	 * java.util.Iterator<XYChart.Series<X,Y>> getDisplayedSeriesIterator()
	 * protected final X getCurrentDisplayedXValue(XYChart.Data<X,Y> item)
	 * protected final void setCurrentDisplayedXValue(XYChart.Data<X,Y> item, X
	 * value) protected final ObjectProperty<X>
	 * currentDisplayedXValueProperty(XYChart.Data<X,Y> item) protected final Y
	 * getCurrentDisplayedYValue(XYChart.Data<X,Y> item) protected final void
	 * setCurrentDisplayedYValue(XYChart.Data<X,Y> item, Y value) protected
	 * final ObjectProperty<Y> currentDisplayedYValueProperty(XYChart.Data<X,Y>
	 * item) protected final java.lang.Object
	 * getCurrentDisplayedExtraValue(XYChart.Data<X,Y> item) protected final
	 * void setCurrentDisplayedExtraValue(XYChart.Data<X,Y> item,
	 * java.lang.Object value) protected final ObjectProperty<java.lang.Object>
	 * currentDisplayedExtraValueProperty(XYChart.Data<X,Y> item) protected
	 * final java.util.Iterator<XYChart.Data<X,Y>>
	 * getDisplayedDataIterator(XYChart.Series<X,Y> series) protected final void
	 * removeDataItemFromDisplay(XYChart.Series<X,Y> series, XYChart.Data<X,Y>
	 * item)
	 * 
	 * Source:
	 * https://docs.oracle.com/javafx/2/api/javafx/scene/chart/XYChart.html#
	 * dataItemAdded%28javafx.scene.chart.XYChart.Series,%20int,%20javafx.scene.
	 * chart.XYChart.Data%29
	 */
}
