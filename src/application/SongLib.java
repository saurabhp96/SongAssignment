package application;
	
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
	@Override
	public void start(Stage primaryStage) {
		
		try {

			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Song.fxml"));
			GridPane root=(GridPane)loader.load();
			primaryStage.setResizable(false);
			Scene scene = new Scene(root,500,500);
			Controller controller=loader.getController();
			controller.createList(primaryStage);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Song Library");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
