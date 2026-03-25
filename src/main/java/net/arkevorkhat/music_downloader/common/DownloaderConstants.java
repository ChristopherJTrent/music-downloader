package net.arkevorkhat.music_downloader.common;

public interface DownloaderConstants {
	final String MediaDirectoryBase = "/media";
	final String TempDirectory = String.format("%s/downloads", MediaDirectoryBase);
	final String MusicDirectoryBase = String.format("%s/music", MediaDirectoryBase);
}
