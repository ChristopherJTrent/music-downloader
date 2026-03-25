package net.arkevorkhat.music_downloader.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RequestedAlbum {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String Url;
	private String Artist;
	private String Album;
	public RequestedAlbum() {}
	
	public RequestedAlbum(String uRL, String artist, String album) {
		Url = uRL;
		Artist = artist;
		Album = album;
	}

	public String getUrl() {
		return Url;
	}
	public void setUrl(String uRL) {
		Url = uRL;
	}
	public String getArtist() {
		return Artist;
	}
	public void setArtist(String artist) {
		Artist = artist;
	}
	public String getAlbum() {
		return Album;
	}
	public void setAlbum(String album) {
		Album = album;
	}

}
