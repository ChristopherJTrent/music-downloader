package net.arkevorkhat.music_downloader.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.arkevorkhat.music_downloader.entity.RequestedAlbum;
import net.arkevorkhat.music_downloader.repository.RequestedAlbumRepository;
import net.arkevorkhat.music_downloader.services.interfaces.IRequestedAlbumService;
@Service
public class RequestedAlbumService implements IRequestedAlbumService{
	@Autowired
	private RequestedAlbumRepository albumRepo;

	@Override
	public RequestedAlbum saveRequestedAlbum(RequestedAlbum album) {
		return albumRepo.save(album);
	}

	@Override
	public List<RequestedAlbum> getRequestedAlbums() {
		return (List<RequestedAlbum>) albumRepo.findAll();
	}

	@Override
	public void deleteRequestedAlbumByURL(String URL) {
		var entity = albumRepo.findByUrl(URL);
		albumRepo.delete(entity);
	}

	@Override
	public RequestedAlbum getByUrl(String URL) {
		return albumRepo.findByUrl(URL);
	}
}
