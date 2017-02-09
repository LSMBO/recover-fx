package Recover;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class TestChart extends Application {
	
//	private ScatterChart<Number, Number> chart;
	private BubbleChart<Number, Number> chart;
	
	@Override
	public void start(Stage primaryStage) {
		try {
	        primaryStage.setTitle("JavaFX Welcome");
	        
	        BorderPane bp = new BorderPane();
	
	        Scene scene = new Scene(bp, 300, 275);
	        primaryStage.setScene(scene);
	        	        
	        Button btn = new Button("Do something");
	        HBox hb = new HBox();
	        hb.getChildren().add(btn);
	        hb.setAlignment(Pos.CENTER);
	        bp.setBottom(hb);
	        
//	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis xAxis = new NumberAxis(0, 1000, 100);
	        final NumberAxis yAxis = new NumberAxis(0, 1000, 100);
//	        final BubbleChart<Number, Number> chart = new BubbleChart<Number, Number>(xAxis,yAxis);
//	        final ScatterChart<Number, Number> chart = new ScatterChart<Number, Number>(xAxis,yAxis);
//	        chart = new ScatterChart<Number, Number>(xAxis,yAxis);
	        chart = new BubbleChart<Number, Number>(xAxis,yAxis);
	        chart.setTitle("Summary");
	        chart.setAnimated(false);
	        xAxis.setAutoRanging(false);
	        xAxis.setAnimated(false);
	        yAxis.setAutoRanging(false);
	        yAxis.setAnimated(false);
	        xAxis.setLabel("M/z");
	        yAxis.setLabel("Intensity");
	        
	        XYChart.Series<Number, Number> series1 = new XYChart.Series<Number, Number>();
	        series1.setName("Fragments");
	        series1.getData().add(get(1000, 500, true));
	        series1.getData().add(get(100, 200, true));
	        series1.getData().add(get(1000, 1000, false));
	        chart.getData().add(series1);
//	        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();
//	        series2.setName("");
//	        series2.getData().add(get(1000, 1000, false));
//	        chart.getData().add(series2);
	        
//	        XYChart.Series series1 = new XYChart.Series();
//	        series1.setName("Good fragments");
//	        series1.getData().add(get(431.26114, 320.0));
//	        series1.getData().add(get(544.34561, 339.0));
//	        series1.getData().add(get(551.25496, 217.0));
//	        series1.getData().add(get(878.43755, 570.0));
//	        
//	        XYChart.Series series2 = new XYChart.Series();
//	        series2.setName("intensity < 200");
//	        series2.getData().add(get(663.38341, 111.0));
//	        series2.getData().add(get(664.33969, 177.0));
//	        series2.getData().add(get(666.28351, 198.0)); 
//	        
//	        XYChart.Series series3 = new XYChart.Series();
//	        series3.setName("intensity > 600");
//	        series3.getData().add(get(779.36642, 730.0));
//	        
//	        chart.getData().addAll(series1, series2, series3);
	        
//	        xAxis.setLowerBound(0);
//			xAxis.setUpperBound(1000);
//			xAxis.setTickUnit(100);
//			yAxis.setLowerBound(0);
//			yAxis.setUpperBound(1000);
//			yAxis.setTickUnit(100);
	        
	        
	        bp.setCenter(chart);
	        
	        btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
//	                actiontarget.setFill(Color.FIREBRICK);
//	                actiontarget.setText("Sign in button pressed");
					xAxis.setLowerBound(0);
					xAxis.setUpperBound(1000);
					xAxis.setTickUnit(100);
					yAxis.setLowerBound(0);
					yAxis.setUpperBound(1000);
					yAxis.setTickUnit(100);
				}
			});
	        
	        primaryStage.setWidth(800);
	        primaryStage.setHeight(600);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private XYChart.Data<Number, Number> get(double x, double y, Boolean asLine) {
//		System.out.println(x+" "+y);
		XYChart.Data<Number, Number> d;
		if(asLine) {
			d = new XYChart.Data<Number, Number>(x, y);
//			d = new XYChart.Data<Number, Number>(x, 0);
//			d.setNode(new Line(x, 0, x, y));
		} else {
			d = new XYChart.Data<Number, Number>(x, y);
//			d.setNode(new Line(0, 0, 0, 0));
//			d.getNode().setOpacity(0);
		}
		System.out.println(x+" "+y+" => "+d.getXValue().doubleValue()+" "+d.getYValue().doubleValue());
		return d;
	}
}
