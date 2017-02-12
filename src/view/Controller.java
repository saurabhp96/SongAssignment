package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
	
	public void processButton(ActionEvent e)  throws FileNotFoundException {
		Button b = (Button) e.getSource();
		
		if (b == addSongButton)
		{
			String title = titleInput.getText();
			String artist = artistInput.getText();
			
			//check if title and/or artist are empty
			if (title.isEmpty() || artist.isEmpty())
			{
				//dialog
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Can't add this song.");
				alert.setContentText("You need to fill in the artist and title of the song!");
				alert.showAndWait();
				return;
			}
			
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
			
			//check to make sure this song doesn't already exist
			for (Song s:obsList)
			{
				if (song.equals(s))
				{
					// dialog
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error in song addition");
					alert.setHeaderText("Can't add this song.");
					alert.setContentText("You already have a song with this title and artist!");
					alert.showAndWait();
					return;
				}
			}
			
			//if the song doesn't already exist, ask if user is sure they want to add it
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Song Addition");
			//alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you sure you want to add this song?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
				//just continue with the code
				
			} else {
			    // ... user chose CANCEL or closed the dialog
				return;
			}
			
			
			//update songlist, sort the songlist
			obsList.add(song);
			Collections.sort(obsList);
			
			//update song display
			listView.setItems(obsList);
			
			//make the inserted song selected
			//listView.getSelectionModel.select(what do i put here);
			
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
			//to edit a song, select a song from the list
			//the fields should populate with its existing information
			//make your changes, then click edit and the changes should save
			
			int index=listView.getSelectionModel().getSelectedIndex();
			if (index == -1)
				return;
			
			Song selectedSong=obsList.get(index);
			
			//get values of input fields
			String title = titleInput.getText();
			String artist = artistInput.getText();
			
			//check that the artist and/or title are not empty
			if (title.isEmpty() || artist.isEmpty())
			{
				//dialog
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Can't edit this song.");
				alert.setContentText("You need to fill in the artist and title of the song!");
				alert.showAndWait();
				return;
			}
			
			//check that the artist and/or title don't already exist
			for (Song s:obsList)
			{
				if (selectedSong.equals(s))
				{
					// dialog
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error in song addition");
					alert.setHeaderText("Can't add this song.");
					alert.setContentText("You already have a song with this title and artist!");
					alert.showAndWait();
					return;
				}
			}
			
			String album = albumInput.getText();
			String yearString = yearInput.getText();
			int year = -1;
			if (! yearString.isEmpty())
			{
				year = Integer.valueOf(yearString);
			}
			
			//ask if user is sure they want to make these changes
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Song Edit");
			//alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you sure you want to edit this song?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
				//just continue with the code
				
			} else {
			    // ... user chose CANCEL or closed the dialog
				return;
			}
			
			//save input fields to Song
			selectedSong.setTitle(title);
			selectedSong.setArtist(artist);
			selectedSong.setAlbum(album);
			selectedSong.setYear(year);
			
			//sort the song list
			Collections.sort(obsList);
			
			//update song display
			listView.setItems(obsList);
			
			
		}
		else if (b == deleteSongButton)
		{
			//only do anything if a song is selected from the list
			//then, pop up a confirm dialog
			//on confirm, delete the song from the file, and from the song display list
			
			int index=listView.getSelectionModel().getSelectedIndex();
			if (index == -1)
				return;
			
			Song selectedSong=obsList.get(index);
			
			//ask if user is sure they want to delete this song
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Song Deletion");
			//alert.setHeaderText("Look, a Confirmation Dialog");
			alert.setContentText("Are you sure you want to delete this song?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
				obsList.remove(index);
				
			} else {
			    // ... user chose CANCEL or closed the dialog
				return;
			}
			
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
