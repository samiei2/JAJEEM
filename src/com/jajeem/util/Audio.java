package com.jajeem.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Audio {

	static Process process;
	private String path;

	// /Play Sound
	private final static int BUFFER_SIZE = 128000;
	private static File soundFile;
	private static AudioInputStream audioStream;
	private static AudioFormat audioFormat;
	private static SourceDataLine sourceLine;

	public Audio() {
		if (System.getProperty("os.arch").contains("64")) {

			// path =
			// Audio.class.getResource("/com/jajeem/extra/nircmdc_64.exe").getPath();
			path = "util/nircmdc_64.exe";
		} else {
			// path =
			// Audio.class.getResource("/com/jajeem/extra/nircmdc_32.exe").getPath();
			path = "util/nircmdc_32.exe";
		}

	}

	public void mute() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"mutesysvolume", "1");
		processBuilder.start();
	}

	public void unMute() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"mutesysvolume", "0");
		processBuilder.start();
	}

	public void switchMute() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"mutesysvolume", "2");
		processBuilder.start();
	}

	public void increaseVol() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"changesysvolume", "2000");
		processBuilder.start();
	}

	public void decreaseVol() throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"changesysvolume", "-5000");
		processBuilder.start();
	}

	public void setVol(int vol) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(path,
				"setsysvolume", Integer.toString(vol));
		processBuilder.start();
	}

	public static void playSound(String filename) {

		String strFilename = filename;

		try {
			soundFile = new File(strFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		audioFormat = audioStream.getFormat();

		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		sourceLine.start();

		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				@SuppressWarnings("unused")
				int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
			}
		}

		sourceLine.drain();
		sourceLine.close();
	}
}
