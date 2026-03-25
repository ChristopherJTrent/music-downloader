package net.arkevorkhat.music_downloader.controller;

import org.springframework.web.bind.annotation.RestController;

import net.arkevorkhat.music_downloader.common.DownloaderConstants;
import net.arkevorkhat.music_downloader.common.Pair;
import net.arkevorkhat.music_downloader.common.YTDLPCommandBuilder;
import net.arkevorkhat.music_downloader.contracts.AlbumDownloadRequest;
import net.arkevorkhat.music_downloader.contracts.SimpleDownloadRequest;
import net.arkevorkhat.music_downloader.entity.RequestedAlbum;
import net.arkevorkhat.music_downloader.repository.RequestedAlbumRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DownloadController {
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	private static RequestedAlbumRepository albumRepo;
	private static YTDLPCommandBuilder createBuilderFromAlbumDownloadRequest(AlbumDownloadRequest request) {
		var builder = new YTDLPCommandBuilder();
		builder.withUrl(request.URL());
		var artist = request.Artist();
		if(artist != null) {
			var album = request.Album();
			if(album != null) {
				builder.withOutputDirectory(String.format("%s/%s/%s", 
					DownloaderConstants.MusicDirectoryBase,
					artist,
					album
				));
			} else {
				builder.withOutputDirectory(String.format("%s/%s", 
					DownloaderConstants.MusicDirectoryBase,
					artist
				));
			}
		} else {
			builder.withOutputDirectory(DownloaderConstants.MusicDirectoryBase);
		}
		return builder;
	}
	@PostMapping("/download")
	public String simpleDownload(@RequestBody SimpleDownloadRequest entity) {
		return new YTDLPCommandBuilder().withUrl(entity.URL()).createCommand();
	}
	@PostMapping("/download/album")
	public String albumDownload(
			@RequestBody AlbumDownloadRequest entity, 
			@RequestParam(required = false) String dryrun,
			@RequestParam(defaultValue = "false") boolean redownload
	) {
		var builder = createBuilderFromAlbumDownloadRequest(entity);
		var album = albumRepo.findByUrl(entity.URL());
		if(dryrun != null) {
			return String.join(" ",builder.build().command());
		} else if ( album != null && !redownload ) {
			return String.format("%s has already been downloaded as %s by %s", album.getUrl(), album.getAlbum(), album.getArtist());
		} else if ( album == null && redownload) {
			return "the redownload switch can only be used for an album that has already been downloaded.";
		} else {
			try {
				albumRepo.save(new RequestedAlbum(entity.URL(), entity.Artist(), entity.Album()));
				builder.start().getInputStream();
				return "Download Started.";
			} catch (Exception e) {
				return e.getLocalizedMessage();
			}
		}
	}
	@PostMapping("/download/albums")
	public String albumsDownload(
			@RequestBody List<AlbumDownloadRequest> entity, 
			@RequestParam(required = false) String dryrun, 
			@RequestParam(defaultValue = "false") boolean redownload
	) {
		if (entity.size() <= 1) {
			return "to download a single album, use /download/album";
		}
		var builders = entity.stream()
			.map((e) -> new Pair<>(
				DownloadController.createBuilderFromAlbumDownloadRequest(e), 
				new RequestedAlbum(e.URL(), e.Artist(), e.Album())
			))
			.toList();
		if(dryrun != null) {
			return builders.stream()
				.map((v) -> v.a.createCommand())
				.reduce("", (a, v) -> a.concat("\n").concat(v));
		} else {
			var worker = new Thread() {
				public void run() {
					for (var container : builders) {
						var builder = container.a;
						var existing = albumRepo.findByUrl(builder.getURL());
						if ((existing != null && !redownload) || (existing == null && redownload)) {
							continue;
						}
						try {
							albumRepo.save(container.b);
							builder.start().onExit().wait();
						} catch (Exception e) {
							logger.warn("Error in worker thread while downloading an album. Skipping...");
						}
					}
				}
			};
			worker.start();
			return "Download Started.";
		}
	}
}
