package net.arkevorkhat.music_downloader.controller;

import org.springframework.web.bind.annotation.RestController;

import net.arkevorkhat.music_downloader.common.YTDLPCommandBuilder;
import net.arkevorkhat.music_downloader.common.YTDLPCommandBuilderFactory;
import net.arkevorkhat.music_downloader.contracts.AlbumDownloadRequest;
import net.arkevorkhat.music_downloader.contracts.SimpleDownloadRequest;
import net.arkevorkhat.music_downloader.services.interfaces.IAlbumDownloaderService;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DownloadController {
	@Autowired
	public IAlbumDownloaderService downloadService;
	@PostMapping("/download")
	public String simpleDownload(@RequestBody SimpleDownloadRequest entity) {
		try {	
			new YTDLPCommandBuilder().withUrl(entity.URL()).start();
		} catch(Exception e) {
			
		}
		return "Download started...";
	}
	@PostMapping("/download/album")
	public String albumDownload(
			@RequestBody AlbumDownloadRequest entity, 
			@RequestParam(required = false) String dryrun
	) {
		var builder = YTDLPCommandBuilderFactory.createFromAlbumDownloadRequest(entity);
		if(dryrun != null) {
			return String.join(" ",builder.build().command());
		} else {
			downloadService.DownloadAsync(entity);
			return "download started...";
		}
	}
	@PostMapping("/download/albums")
	public String albumsDownload(
			@RequestBody AlbumDownloadRequest[] entity, 
			@RequestParam(required = false) String dryrun, 
			@RequestParam(defaultValue = "false") boolean redownload
	) {
		if (entity.length <= 1) {
			return "to download a single album, use /download/album";
		}
		if(dryrun != null) {
			return Stream.of(entity)
				.map(YTDLPCommandBuilderFactory::createFromAlbumDownloadRequest)
				.map((v) -> v.createCommand())
				.reduce("", (a, v) -> a.concat("\n").concat(v));
		} else {
			downloadService.DownloadAsync(entity);
			return "started downloading albums";
		}
	}
}
