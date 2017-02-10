package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
	private Stage stage;
	
	public void processButton(ActionEvent e){
		Button b = (Button) e.getSource();
		if (b == addSongButton)
		{
			String title = titleInput.getText();
			String artist = artistInput.getText();
			String album = albumInput.getText();
			if (album.isEmpty())
			{
				album = " ";
			}
			int year = -1;
			String yearString = yearInput.getText();
			if (! yearString.isEmpty())
			{
				year = Integer.valueOf(yearString);
			}
			
			Song song = new Song(title, artist, album, year);
			
			//update file
			
			//update song display
			
			//make the inserted song selected
			
			//display its details
			TitleOutput.setText(title);
			ArtistOutput.setText(artist);
			if (!album.isEmpty())
			{
				AlbumOutput.setText(album);
			}
			if (!yearString.isEmpty())
			{
				YearOutput.setText(String.format("d", yearString));
				//test this
			}
			
		}
		else if (b == editSongButton)
		{
			//only do anything if a song is selected from the list
			
			//then, populate edit text fields with song info
			
		}
		else if (b == deleteSongButton)
		{
			//only do anything if a song is selected from the list
			//then, pop up a confirm dialog
			//on confirm, delete the song from the file, and from the song display list
			
		}
		
	}
	
	public void createList() throws FileNotFoundException{
		
		obsList=FXCollections.observableArrayList();
		getSongsFromFile(obsList);
		Collections.sort(obsList);
		listView.setItems(obsList);
		
		if (!obsList.isEmpty())
			listView.getSelectionModel().select(0);
		listView.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> showSongDetails());
	}

	public void showSongDetails() {
		// TODO Auto-generated method stub
		int index=listView.getSelectionModel().getSelectedIndex();
		Song selectedSong=obsList.get(index);
		TitleOutput.setText(selectedSong.getTitle());
		ArtistOutput.setText(selectedSong.getArtist());
		AlbumOutput.setText(selectedSong.getAlbum());
		YearOutput.setText(selectedSong.getYear()!=-1?selectedSong.getYear()+"":"");
	}

	/**
	 * 
	 * @param list The list to populate the songs into
	 * @throws FileNotFoundException
	 */
	private void getSongsFromFile(ObservableList<Song> list) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file= new File("SongList.txt");
		Scanner sc=new Scanner(file);
		while(sc.hasNextLine()){
			String line=sc.nextLine();
			Scanner lineScan=new Scanner(line);
			lineScan.useDelimiter(";");
			String songTitle=lineScan.next();
			String songArtist=lineScan.next();
			String songAlbum=lineScan.next();
			int songYear=Integer.parseInt(lineScan.next());
			
			Song s=new Song(songTitle,songArtist,songAlbum,songYear);
			
			list.add(s);
		}
		
	}
	
	public void writeSongsIntoFile() throws FileNotFoundException{
		File file=new File("SongList.txt");
		
		PrintWriter writer=new PrintWriter(file);
		
		for(Song s:obsList){
			writer.println(s.getTitle()+";"+s.getArtist()+";"+s.getAlbum()+";"+s.getYear()+";");
		}
		
		writer.close();
		
	}

	public void setStage(Stage primaryStage) {
		// TODO Auto-generated method stub
		stage=primaryStage;
	}
	

}
