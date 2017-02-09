package Recover;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class AbuAxis extends AnchorPane {

	public static int HORIZONTAL = 0;
	public static int VERTICAL = 1;

	private double minValue;
	private double maxValue;
	private int orientation;
	private double length;
	private Line axis;
	private ArrayList<Line> ticks;
	private ArrayList<Label> tickLabels;

	public AbuAxis(double min, double max, int orientation) {
		// add some margin to min and max
		// compute number of ticks
		// use current size
		
		this.minValue = min * 1.2; // adding 20%, but i should get a round value
		this.maxValue = max * 1.2; // adding 20%, but i should get a round value
		if(orientation == AbuAxis.VERTICAL)
			this.orientation = AbuAxis.VERTICAL;
		else
			this.orientation = AbuAxis.HORIZONTAL;
		redraw();
	}
	
	public void redraw() {
		// use current size to compute axis length and create the axis
		if(orientation == AbuAxis.HORIZONTAL) {
			length = this.getWidth();
			axis = new Line(0, 0, length, 0);
		} else {
			length = this.getHeight();
			axis = new Line(this.getWidth(), 0, this.getWidth(), length);
		}
		// TODO compute number of ticks
		Integer nbTicks = 100;
		// create ticks
		
		// add tick labels
		
//		axis = new Line(0, 0, 0, 0);
//		ticks = new ArrayList<Line>();
		
	}
	
	public float getPositionOfValue(float value) {
		// TODO use current size, min and max to compute the position
		return 0;
	}
}
