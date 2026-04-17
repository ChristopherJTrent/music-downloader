package net.arkevorkhat.music_downloader.services.implementation;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.arkevorkhat.music_downloader.common.Pair;
import net.arkevorkhat.music_downloader.common.YTDLPCommandBuilderFactory;
import net.arkevorkhat.music_downloader.contracts.AlbumDownloadRequest;
import net.arkevorkhat.music_downloader.entity.RequestedAlbum;
import net.arkevorkhat.music_downloader.services.interfaces.IAlbumDownloaderService;
import net.arkevorkhat.music_downloader.services.interfaces.IRequestedAlbumService;

@Service
public class AlbumDownloaderService implements IAlbumDownloaderService {
	private static ReentrantLock lock = new ReentrantLock();
	private static final Logger logger = LoggerFactory.getLogger(AlbumDownloaderService.class);
	@Autowired IRequestedAlbumService albumService;

	@Override
	public void DownloadAsync(AlbumDownloadRequest... albums) {
		var pairs = Stream.of(albums)
			.map((v) -> new Pair<>(
				YTDLPCommandBuilderFactory.createFromAlbumDownloadRequest(v),
				new RequestedAlbum(v.URL(), v.Artist(), v.Album())
			))
			.toList();
		new Thread() {
			public void run() {
				lock.lock();
				try {
					for (var container : pairs) {
						var builder = container.a;
						var found = albumService.getByUrl(container.b.getUrl());
						if (found != null) {
							logger.info(String.format("Skipped download of album: %s - %s", found.getArtist(), found.getAlbum()));
							continue;
						}
						try {
							albumService.saveRequestedAlbum(container.b);
							builder.start().onExit().wait();
						} catch (Exception e) {
							logger.warn(e.getLocalizedMessage());
						}
					}
				} finally {
					lock.unlock();
				}
			}
		}.start();
	}
	
}
