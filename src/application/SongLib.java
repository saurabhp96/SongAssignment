package application;
	
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import structures.Song;
import view.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * test message
 * @author Saurabh Prasad, Rohith Sivakumar
 *
 */
public class SongLib extends Application {
	Controller controller;
	@Override
	public void start(Stage primaryStage) {
		
		try {

			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Song.fxml"));
			GridPane root=(GridPane)loader.load();
			primaryStage.setResizable(false);
			Scene scene = new Scene(root,500,500);
			controller=loader.getController();
			controller.createList();
			controller.setStage(primaryStage);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Song Library");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		try {
			controller.writeSongsIntoFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
