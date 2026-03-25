package net.arkevorkhat.music_downloader.contracts;

import java.util.List;

public record MultiAlbumDownloadRequest(List<AlbumDownloadRequest> albums) {
	
}
