package net.arkevorkhat.music_downloader.common;

import net.arkevorkhat.music_downloader.contracts.AlbumDownloadRequest;

public class YTDLPCommandBuilderFactory {
	public static YTDLPCommandBuilder createFromAlbumDownloadRequest(AlbumDownloadRequest request) {		
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
}
