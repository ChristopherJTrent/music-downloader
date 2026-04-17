package net.arkevorkhat.music_downloader.services.interfaces;

import net.arkevorkhat.music_downloader.contracts.AlbumDownloadRequest;

public interface IAlbumDownloaderService {
	public void DownloadAsync(AlbumDownloadRequest... albums);

}
