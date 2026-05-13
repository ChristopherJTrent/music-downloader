package net.arkevorkhat.music_downloader.services.interfaces;

import net.arkevorkhat.music_downloader.contracts.MusicDownloadRequest;

public interface IAlbumDownloaderService {
	public void DownloadAsync(MusicDownloadRequest... albums);

}
