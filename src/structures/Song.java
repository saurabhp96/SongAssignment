package structures;

/**
 * 
 * @author Saurabh Prasad, Rohith Sivakumar
 *
 */
public class Song implements Comparable<Song> {
	
	private String title;
	private String artist;
	private String album;
	private int year;
	
	/**
	 * 
	 * @param title Title of the song
	 * @param artist Artist of the song
	 */
	public Song(String title, String artist){
		this.title=title;
		this.artist=artist;
	}
	
	

	/**
	 * 
	 * @param title Title of the song
	 * @param artist Artist of the song
	 * @param album Album of the song
	 * @param year The year the song was released
	 */
	public Song(String title, String artist, String album, int year) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}


	public boolean equals(Object o){
		
		if (o == null || !(o instanceof Song))
			return false;

		Song other = (Song) o;

		return this.title.equals(other.title) && this.artist.equals(other.artist);

	}


	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		return this.title.compareTo(o.title);
	}



	@Override
	public String toString() {
		return title;
	}
	
	
	

}
