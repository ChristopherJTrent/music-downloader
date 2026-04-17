package net.arkevorkhat.music_downloader.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RequestedAlbum {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String url;
	private String artist;
	private String album;
	public RequestedAlbum() {}
	
	public RequestedAlbum(String uRL, String artist, String album) {
		url = uRL;
		this.artist = artist;
		this.album = album;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String uRL) {
		url = uRL;
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

}
