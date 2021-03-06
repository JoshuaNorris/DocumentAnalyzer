package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage; 
public class Main extends Application{

	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("DAController.fxml"));
        primaryStage.setTitle("Document Analyzer");
        primaryStage.setScene(new Scene(root, 800, 575));
        primaryStage.show();
	}
}
