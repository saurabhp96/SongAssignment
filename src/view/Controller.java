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

		if (b == addSongButton) {
			String title = titleInput.getText().trim();
			String artist = artistInput.getText().trim();

			// check if title and/or artist are empty
			if (title.isEmpty() || artist.isEmpty()) {
				// dialog
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Can't add this song.");
				alert.setContentText("You need to fill in the title and artist of the song!");
				alert.showAndWait();
				return;
			}

			String album = albumInput.getText().trim();
			if (album.isEmpty()) {
				album = " ";
			}
			int year = -1;
			String yearString = yearInput.getText().trim();
			if (!yearString.isEmpty()) {
				year = Integer.valueOf(yearString);
			}

			Song song = new Song(title, artist, album, year);

			// check to make sure this song doesn't already exist
			if (obsList.contains(song)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(stage);
				alert.setTitle("Error in song addition");
				alert.setHeaderText("Can't add this song.");
				alert.setContentText("You already have a song with this title and artist!");
				alert.showAndWait();
				return;
			}

			// if the song doesn't already exist, ask if user is sure they want
			// to add it
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(stage);
			alert.setTitle("Confirm Song Addition");
			alert.setContentText("Are you sure you want to add this song?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK) {
				return;
			}

			int index = 0;

			while (index < obsList.size() && obsList.get(index).compareTo(song) < 0)
				index++;

			obsList.add(index, song);

			listView.getSelectionModel().select(index);
			this.showSongDetails();
			
		}
		else if (b == editSongButton)
		{
			
			int index=listView.getSelectionModel().getSelectedIndex();
			if (index == -1){
				Alert alert=new Alert(AlertType.ERROR);
				alert.initOwner(stage);
				alert.setTitle("Error");
				alert.setHeaderText("Can't edit song.");
				alert.setContentText("No song is selected");
				alert.showAndWait();
				return;
			}
			
			Song selectedSong=obsList.get(index);
			//get values of input fields
			String title = titleInput.getText().trim();
			String artist = artistInput.getText().trim();
			String album=albumInput.getText().trim();
			String year=yearInput.getText().trim();
			
			//check that the artist and/or title are not empty
			if (title.isEmpty() || artist.isEmpty())
			{
				//dialog
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Can't edit this song.");
				alert.setContentText("You need to fill in the artist and title of the song!");
				alert.showAndWait();
				return;
			}
			
			Song edittedSong=new Song(title,artist);
			
			if(obsList.contains(edittedSong)&&!edittedSong.equals(selectedSong)){
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(stage);
				alert.setTitle("Error in song edit");
				alert.setHeaderText("Can't edit this song.");
				alert.setContentText("You already have a song with this title and artist!");
				alert.showAndWait();
				return;
			}
			
			if(!year.isEmpty()){
				try {
					Integer.parseInt(year);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					Alert alert2 = new Alert(AlertType.ERROR);
					alert2.initOwner(stage);
					alert2.setTitle("Error in song edit");
					alert2.setHeaderText("Can't edit this song.");
					alert2.setContentText("Not a number in year");
					alert2.showAndWait();
					return;
				}
			}
			//confirmation
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(stage);
			alert.setTitle("Confirm Song Edit");
			alert.setContentText("Are you sure you want to edit this song?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != ButtonType.OK) {
				return;
			}
			
			//edit song
			Song s=obsList.remove(index);
			s.setTitle(title);
			s.setArtist(artist);
			s.setAlbum(album);
			s.setYear(Integer.parseInt(year));
			
			//insert editted song into list
			int i = 0;

			while (i < obsList.size() && obsList.get(i).compareTo(s) < 0)
				i++;

			obsList.add(i, s);

			listView.getSelectionModel().select(i);
			this.showSongDetails();
			
		}
		else if (b == deleteSongButton)
		{
			//only do anything if a song is selected from the list
			//then, pop up a confirm dialog
			//on confirm, delete the song from the file, and from the song display list
			
			int index=listView.getSelectionModel().getSelectedIndex();
			if (index == -1){
				Alert alert=new Alert(AlertType.ERROR);
				alert.initOwner(stage);
				alert.setTitle("Error");
				alert.setHeaderText("Can't delete song.");
				alert.setContentText("No song is selected");
				alert.showAndWait();
				return;
			}
			
			Song selectedSong=obsList.get(index);
			
			// ask if user is sure they want to delete this song
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(stage);
			alert.setTitle("Confirm Song Deletion");
			alert.setContentText("Are you sure you want to delete this song?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				// ... user chose OK
				obsList.remove(index);
				if (obsList.isEmpty()) {
					TitleOutput.setText("");
					ArtistOutput.setText("");
					AlbumOutput.setText("");
					YearOutput.setText("");
				} else if (index >= obsList.size()) {
					listView.getSelectionModel().select(index - 1);
					this.showSongDetails();
				} else {
					listView.getSelectionModel().select(index);
					this.showSongDetails();
				}

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
		
		
		listView.getSelectionModel().selectedIndexProperty()
				.addListener((obs, oldVal, newVal) -> showSongDetails());
		if (!obsList.isEmpty()) {
			listView.getSelectionModel().select(0);
			this.showSongDetails();
		}
	}

	public void showSongDetails() {
		// TODO Auto-generated method stub
		int index=listView.getSelectionModel().getSelectedIndex();
		if (index != -1) {
			Song selectedSong = obsList.get(index);
			TitleOutput.setText(selectedSong.getTitle());
			ArtistOutput.setText(selectedSong.getArtist());
			AlbumOutput.setText(selectedSong.getAlbum());
			YearOutput.setText(selectedSong.getYear() != -1 ? selectedSong.getYear() + "" : "");

			// populate edit text fields when a song is selected
			titleInput.setText(selectedSong.getTitle());
			artistInput.setText(selectedSong.getArtist());
			albumInput.setText(selectedSong.getAlbum());
			yearInput.setText(selectedSong.getYear() != -1 ? selectedSong.getYear() + "" : "");
		}
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
