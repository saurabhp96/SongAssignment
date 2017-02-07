package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import structures.Song;

/**
 * 
 * @author Saurabh Prasad, Rohith Sivakumar
 *
 */
public class Controller {
	
	@FXML ListView<Song> listView;
	@FXML Button addSongButton;
	@FXML Button editSongButton;
	@FXML Button deleteSongButton;
	@FXML TextField titleInput;
	@FXML TextField artistInput;
	@FXML TextField albumInput;
	@FXML TextField yearInput;
	@FXML Text TitleOutput;
	@FXML Text ArtistOutput;
	@FXML Text AlbumOutput;
	@FXML Text YearOutput;
	
	private ObservableList<Song> obsList;
	private ArrayList<Song> songs=new ArrayList<Song>();
	
	public void processButton(ActionEvent e){
		
	}
	
	public void createList(Stage stage) throws FileNotFoundException{
		
		getSongsFromFile(songs);
		obsList=FXCollections.observableArrayList(songs);
		listView.setItems(obsList);
		
		if (!obsList.isEmpty())
			listView.getSelectionModel().select(0);
		listView.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> showSongDetails(stage));
	}

	public void showSongDetails(Stage stage) {
		// TODO Auto-generated method stub
		
	}

	private void getSongsFromFile(ArrayList<Song> songs) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file= new File("SongList.txt");
		Scanner sc=new Scanner(file);
		
		while(sc.hasNextLine()){
			String line=sc.nextLine();
			Scanner lineScan=new Scanner(line);
			String songTitle=lineScan.next();
			String songArtist=lineScan.next();
			String songAlbum=lineScan.next();
			int songYear=Integer.parseInt(lineScan.next());
			
			Song s=new Song(songTitle,songArtist);
			if(!songAlbum.equals(" "))
				s.setAlbum(songAlbum);
			if(songYear!=-1){
				s.setYear(songYear);
			}
			
			songs.add(s);
		}
		
	}

}
