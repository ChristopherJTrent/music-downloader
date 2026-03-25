package net.arkevorkhat.music_downloader.common;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class YTDLPCommandBuilder {
	private String URL;
	private String outputDirectory = "/media/music";
	private String tempDirectory = "/media/downloads";

	public YTDLPCommandBuilder() {

	}
	public YTDLPCommandBuilder withUrl(String url) {
		this.URL = url;
		return this;
	}
	public YTDLPCommandBuilder withOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
		return this;
	}
	public YTDLPCommandBuilder withTempDirectory(String tempDirectory) {
		this.tempDirectory = tempDirectory;
		return this;
	}

	public List<String> getCommandParts() {
		return Arrays.asList(
			"/bin/yt-dlp", 
			"-P",
			String.format("temp:%s", this.tempDirectory),
			"-P",
			String.format("home:%s", this.outputDirectory),
			"-x",
			"--audio-format",
			"mp3",
			"--sponsorblock-remove=all",
			String.format("%s", URL)
		);
	}

	public String getURL() {
		return URL;
	}
	public String getOutputDirectory() {
		return outputDirectory;
	}
	public String getTempDirectory() {
		return tempDirectory;
	}
	public String createCommand() {
		return String.join(" ", 
			getCommandParts()
		);
	}

	public ProcessBuilder build() {
		return new ProcessBuilder(
			getCommandParts()
		);
	}
	public Process start() throws IOException, UnsupportedOperationException {
		return build().inheritIO().start();
	}
}
