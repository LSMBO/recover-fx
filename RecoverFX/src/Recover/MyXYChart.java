package Recover;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.shape.Line;

public class MyXYChart<X, Y> extends XYChart<X, Y> {
	
	public static int OPTION_DISABLED = -1;
	private float lowIntensityThreshold = MyXYChart.OPTION_DISABLED;
	private float highIntensityThreshold = MyXYChart.OPTION_DISABLED;
	private float rawBaseline = MyXYChart.OPTION_DISABLED;
	
//	public MyXYChart<X, Y>(Axis<X> xAxis, Axis<Y> yAxis) {
//		super(xAxis, yAxis);
//	}
	public MyXYChart(Axis<X> xAxis, Axis<Y> yAxis) {
		this(xAxis, yAxis, FXCollections.<Series<X, Y>>observableArrayList());
	}
	public MyXYChart(Axis<X> xAxis, Axis<Y> yAxis, ObservableList<Series<X, Y>> data) {
		super(xAxis, yAxis);
		setData(data);
	}

	public float getLowIntensityThreshold() {
		return lowIntensityThreshold;
	}

	public void setLowIntensityThreshold(float lowIntensityThreshold) {
		this.lowIntensityThreshold = lowIntensityThreshold;
	}

	public float getHighIntensityThreshold() {
		return highIntensityThreshold;
	}

	public void setHighIntensityThreshold(float highIntensityThreshold) {
		this.highIntensityThreshold = highIntensityThreshold;
	}

	public float getRawBaseline() {
		return rawBaseline;
	}

	public void setRawBaseline(float rawBaseline) {
		this.rawBaseline = rawBaseline;
	}

	/*
	 * Called when a data item has been added to a series. 
	 * This is where implementations of XYChart can create/add new nodes to getPlotChildren to represent this data item. 
	 * They also may animate that data add with a fade in or similar if animated = true.
	 * Parameters:
	 * 	series - The series the data item was added to
	 * 	itemIndex - The index of the new item within the series
	 * 	item - The new data item that was added
	 */
	@Override
	protected void dataItemAdded(javafx.scene.chart.XYChart.Series<X, Y> series, int itemIndex, javafx.scene.chart.XYChart.Data<X, Y> item) {
		System.out.println("dataItemAdded: "+item.getXValue()+" "+item.getYValue());
		Node bar = item.getNode();
		getPlotChildren().add(bar);
	}

	/*
	 * Called when a data item has changed, ie its xValue, yValue or extraValue has changed.
	 * Parameters:
	 * 	item - The data item who was changed
	 */
	@Override
	protected void dataItemChanged(javafx.scene.chart.XYChart.Data<X, Y> item) {
		System.out.println("dataItemChanged: "+item.getXValue()+" "+item.getYValue());
		
	}

	/*
	 * Called when a data item has been removed from data model but it is still visible on the chart. 
	 * Its still visible so that you can handle animation for removing it in this method. 
	 * After you are done animating the data item you must call removeDataItemFromDisplay() to remove the items node from being displayed on the chart.
	 * Parameters:
	 *	item - The item that has been removed from the series
	 *	series - The series the item was removed from
	 */
	@Override
	protected void dataItemRemoved(javafx.scene.chart.XYChart.Data<X, Y> item, javafx.scene.chart.XYChart.Series<X, Y> series) {
		System.out.println("dataItemRemoved: "+item.getXValue()+" "+item.getYValue());
		Node bar = item.getNode();
		getPlotChildren().remove(bar);
		removeDataItemFromDisplay(series, item);
//		updateMap(series, item);
	}

	/*
	 * Called to update and layout the plot children. 
	 * This should include all work to updates nodes representing the plot on top of the axis and grid lines etc. 
	 * The origin is the top left of the plot area, the plot area with can be got by getting the width of the x axis and its height from the height of the y axis.
	 */
	@Override
	protected void layoutPlotChildren() {
		// is this where i have to create the sticks ?
		System.out.println("layoutPlotChildren: "+getData().size());
		for(int i = 0; i < getData().size(); i++) {
			for(int j = 0; j < getData().get(i).getData().size(); j++) {
				Data<X, Y> d = getData().get(i).getData().get(j);
				System.out.println("PlotChildren: "+d.getXValue()+" "+d.getYValue());
//				d.setNode(new Line((double)d.getXValue(), 0d, (double)d.getXValue(), (double)d.getYValue()));
//				getPlotChildren().add(new Line((double)d.getXValue(), 0d, (double)d.getXValue(), (double)d.getYValue()));
			}
		}
	}

	/*
	 * A series has been added to the charts data model. 
	 * This is where implementations of XYChart can create/add new nodes to getPlotChildren to represent this series. 
	 * Also you have to handle adding any data items that are already in the series. 
	 * You may simply call dataItemAdded() for each one or provide some different animation for a whole series being added.
	 * Parameters:
	 * 	series - The series that has been added
	 * 	seriesIndex - The index of the new series
	 */
	@Override
	protected void seriesAdded(javafx.scene.chart.XYChart.Series<X, Y> series, int seriesIndex) {
		System.out.println("seriesAdded: "+series.getName()+" "+series.getData().size());

		for(int j = 0; j < series.getData().size(); j++) {
			Data<X, Y> d = series.getData().get(j);
//			System.out.println("PlotChildren: "+d.getXValue()+" "+d.getYValue());
//			d.setNode(new Line((double)d.getXValue(), 0d, (double)d.getXValue(), (double)d.getYValue()));
//			getPlotChildren().add(new Line((double)d.getXValue(), 0d, (double)d.getXValue(), (double)d.getYValue()));
//			System.out.println("Line: "+(double)d.getXValue()+" "+getYAxis().getHeight()+" "+(double)d.getXValue()+" "+(getYAxis().getZeroPosition() - (double)d.getYValue()));
//			Line l = new Line((double)d.getXValue(), getYAxis().getZeroPosition(), (double)d.getXValue(), getYAxis().getDisplayPosition(d.getYValue()));
			Line l = new Line((double)d.getXValue(), 0d, (double)d.getXValue(), (double)d.getYValue());
			System.out.println("Line: "+l.toString());
			getPlotChildren().add(l);
		}
	}

	/*
	 * A series has been removed from the data model but it is still visible on the chart. 
	 * Its still visible so that you can handle animation for removing it in this method. 
	 * After you are done animating the data item you must call removeSeriesFromDisplay() to remove the series from the display list.
	 * Parameters:
	 * 	series - The series that has been removed
	 */
	@Override
	protected void seriesRemoved(javafx.scene.chart.XYChart.Series<X, Y> series) {
		System.out.println("seriesRemoved: "+series.getName());
		
	}

	/*
	 * Other callable/overridable methods:
	 * 	public Axis<X> getXAxis()
	 * 	public Axis<Y> getYAxis()
	 * 	public final ObservableList<XYChart.Series<X,Y>> getData()
	 * 	public final void setData(ObservableList<XYChart.Series<X,Y>> value)
	 * 	public final ObjectProperty<ObservableList<XYChart.Series<X,Y>>> dataProperty()
	 * 	public final boolean getVerticalGridLinesVisible()
	 * 	public final void setVerticalGridLinesVisible(boolean value)
	 * 	public final BooleanProperty verticalGridLinesVisibleProperty()
	 * 	public final boolean isHorizontalGridLinesVisible()
	 * 	public final void setHorizontalGridLinesVisible(boolean value)
	 * 	public final BooleanProperty horizontalGridLinesVisibleProperty()
	 * 	public final boolean isAlternativeColumnFillVisible()
	 * 	public final void setAlternativeColumnFillVisible(boolean value)
	 * 	public final BooleanProperty alternativeColumnFillVisibleProperty()
	 * 	public final boolean isAlternativeRowFillVisible()
	 * 	public final void setAlternativeRowFillVisible(boolean value)
	 * 	public final BooleanProperty alternativeRowFillVisibleProperty()
	 * 	public final boolean isVerticalZeroLineVisible()
	 * 	public final void setVerticalZeroLineVisible(boolean value)
	 * 	public final BooleanProperty verticalZeroLineVisibleProperty()
	 * 	public final boolean isHorizontalZeroLineVisible()
	 * 	public final void setHorizontalZeroLineVisible(boolean value)
	 * 	public final BooleanProperty horizontalZeroLineVisibleProperty()
	 * 	protected ObservableList<Node> getPlotChildren()
	 * 	protected void updateLegend()
	 * 	protected void seriesChanged(ListChangeListener.Change<? extends XYChart.Series> c)
	 * 	protected void updateAxisRange()
	 * 	protected abstract void layoutPlotChildren()
	 * 	protected final void layoutChartChildren(double top, double left, double width, double height)
	 * 	protected final void removeSeriesFromDisplay(XYChart.Series<X,Y> series)
	 * 	protected final java.util.Iterator<XYChart.Series<X,Y>> getDisplayedSeriesIterator()
	 * 	protected final X getCurrentDisplayedXValue(XYChart.Data<X,Y> item)
	 * 	protected final void setCurrentDisplayedXValue(XYChart.Data<X,Y> item, X value)
	 * 	protected final ObjectProperty<X> currentDisplayedXValueProperty(XYChart.Data<X,Y> item)
	 * 	protected final Y getCurrentDisplayedYValue(XYChart.Data<X,Y> item)
	 * 	protected final void setCurrentDisplayedYValue(XYChart.Data<X,Y> item, Y value)
	 * 	protected final ObjectProperty<Y> currentDisplayedYValueProperty(XYChart.Data<X,Y> item)
	 * 	protected final java.lang.Object getCurrentDisplayedExtraValue(XYChart.Data<X,Y> item)
	 * 	protected final void setCurrentDisplayedExtraValue(XYChart.Data<X,Y> item, java.lang.Object value)
	 * 	protected final ObjectProperty<java.lang.Object> currentDisplayedExtraValueProperty(XYChart.Data<X,Y> item)
	 * 	protected final java.util.Iterator<XYChart.Data<X,Y>> getDisplayedDataIterator(XYChart.Series<X,Y> series)
	 * 	protected final void removeDataItemFromDisplay(XYChart.Series<X,Y> series, XYChart.Data<X,Y> item)
	 * 
	 * Source: https://docs.oracle.com/javafx/2/api/javafx/scene/chart/XYChart.html#dataItemAdded%28javafx.scene.chart.XYChart.Series,%20int,%20javafx.scene.chart.XYChart.Data%29
	 */
}
