//package Recover;
//	
//import javax.swing.SwingUtilities;
//
//import de.erichseifert.gral.data.DataTable;
//import de.erichseifert.gral.graphics.Drawable;
//import de.erichseifert.gral.plots.BarPlot;
//import de.erichseifert.gral.plots.XYPlot;
//import de.erichseifert.gral.ui.DrawablePanel;
//import de.erichseifert.gral.ui.InteractivePanel;
//import javafx.application.Application;
//import javafx.embed.swing.SwingNode;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//
//
//public class TestChart3 extends Application {
//	
////	private XYBarChart<Number, Number> chart;
//	
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//	        primaryStage.setTitle("JavaFX Welcome");
//	        
//	        BorderPane bp = new BorderPane();
//	
//	        Scene scene = new Scene(bp, 300, 275);
//	        primaryStage.setScene(scene);
//	        	        
//	        Button btn = new Button("Do something");
//	        HBox hb = new HBox();
//	        hb.getChildren().add(btn);
//	        hb.setAlignment(Pos.CENTER);
//	        bp.setBottom(hb);
//	        
//	        DataTable data = new DataTable(Double.class, Double.class);
//	        data.add(1000d, 500d);
//	        data.add(100d, 200d);
//	        data.add(448.22137, 153d);
//	        BarPlot plot = new BarPlot(data);
//	        SwingNode sn = new SwingNode();
//	        SwingUtilities.invokeLater(new Runnable() {
//	             @Override
//	             public void run() {
////	            	 DrawablePanel dp = new DrawablePanel(plot);
////	            	 sn.setContent(dp);
//	            	 InteractivePanel ip = new InteractivePanel(plot);
//	                 sn.setContent(ip);
//	             }
//	         });
//	        bp.setCenter(sn);
//	        
//	        btn.setOnAction(new EventHandler<ActionEvent>() {
//				@Override
//				public void handle(ActionEvent event) {
//				}
//			});
//	        
//	        primaryStage.setWidth(800);
//	        primaryStage.setHeight(600);
//	        primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//    }
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//}
