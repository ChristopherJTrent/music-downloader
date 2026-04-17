package net.arkevorkhat.music_downloader.services.interfaces;

import java.util.List;

import net.arkevorkhat.music_downloader.entity.RequestedAlbum;

public interface IRequestedAlbumService {
	RequestedAlbum saveRequestedAlbum(RequestedAlbum album);
	List<RequestedAlbum> getRequestedAlbums();
	void deleteRequestedAlbumByURL(String URL);
	RequestedAlbum getByUrl(String URL);
}
