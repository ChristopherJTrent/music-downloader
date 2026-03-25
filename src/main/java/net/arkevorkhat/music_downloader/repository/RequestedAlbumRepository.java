package net.arkevorkhat.music_downloader.repository;

import org.springframework.data.repository.CrudRepository;

import net.arkevorkhat.music_downloader.entity.RequestedAlbum;

public interface RequestedAlbumRepository extends CrudRepository<RequestedAlbum, Long> {
	RequestedAlbum findByUrl(String URL);
}
