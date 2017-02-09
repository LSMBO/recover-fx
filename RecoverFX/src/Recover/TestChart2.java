package Recover;
	
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class TestChart2 extends Application {

	private JFreeChart chart;
	
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

//			XYSeries series1 = new XYSeries("peaks over Low Intensity Threshold");
//			XYSeries series2 = new XYSeries("peaks under Low Intensity Threshold");
//			XYSeries series3 = new XYSeries("peaks above High Intensity Threshold");
//			
//			series1.add(1000, 500);
//			series1.add(100, 200);
//			series1.add(1000, 1000);

			XYSeries series1 = new XYSeries("peaks over Low Intensity Threshold");
	        series1.add(431.26114, 320.0);
	        series1.add(544.34561, 339.0);
	        series1.add(551.25496, 217.0);
	        series1.add(878.43755, 570.0);
	        
			XYSeries series2 = new XYSeries("peaks under Low Intensity Threshold");
	        series2.add(663.38341, 111.0);
	        series2.add(664.33969, 177.0);
	        series2.add(666.28351, 198.0); 
	        
			XYSeries series3 = new XYSeries("peaks above High Intensity Threshold");
	        series3.add(779.36642, 730.0);
			
	        XYSeriesCollection dataset = new XYSeriesCollection(series1);
			dataset.addSeries(series2);
			dataset.addSeries(series3);

	        
	        chart = ChartFactory.createXYBarChart("title", "M/z", false, "Intensity", dataset);

	        ChartPanel chartPanel = new ChartPanel(chart);
	        SwingNode sn = new SwingNode();
	        SwingUtilities.invokeLater(new Runnable() {
	             @Override
	             public void run() {
	                 sn.setContent(chartPanel);
	             }
	         });
	        bp.setCenter(sn);
	        
	        btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
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
}
