package net.arkevorkhat.music_downloader.controller;

import java.util.List;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.arkevorkhat.music_downloader.entity.RequestedAlbum;
import net.arkevorkhat.music_downloader.services.interfaces.IRequestedAlbumService;

@RestController
public class InfoController {
	//private static final Logger logger = LoggerFactory.getLogger(InfoController.class);
	@Autowired
	private IRequestedAlbumService albumService;
	@GetMapping("/data/downloadedAlbums")
	public List<RequestedAlbum> downloadedAlbums() {
		return albumService.getRequestedAlbums();
	}
}
