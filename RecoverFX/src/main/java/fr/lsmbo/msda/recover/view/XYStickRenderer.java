package fr.lsmbo.msda.recover.view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.data.xy.XYDataset;

public class XYStickRenderer extends AbstractXYItemRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info,
			XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item,
			CrosshairState crosshairState, int pass) {

		float x = (float) dataset.getXValue(series, item);
		float y = (float) dataset.getYValue(series, item);
		if (!Float.isNaN(y)) {
			org.jfree.ui.RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
			org.jfree.ui.RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
			float transX = (float) domainAxis.valueToJava2D(x, dataArea, xAxisLocation);
			float transY = (float) rangeAxis.valueToJava2D(y, dataArea, yAxisLocation);
			float transOY = (float) rangeAxis.valueToJava2D(0, dataArea, yAxisLocation);
			g2.setPaint(getItemPaint(series, item));
			g2.setStroke(getBaseStroke());
			PlotOrientation orientation = plot.getOrientation();
			if (orientation == PlotOrientation.VERTICAL) {
				g2.drawLine((int) transX, (int) transOY, (int) transX, (int) transY);
			} else if (orientation == PlotOrientation.HORIZONTAL) {
				g2.drawLine((int) transOY, (int) transX, (int) transY, (int) transX);
			}
			int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
			int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
			updateCrosshairValues(crosshairState, x, y, domainAxisIndex, rangeAxisIndex, transX, transY, orientation);
		}
	}
}
