package com.jajeem.recorder.design;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.jajeem.util.Config;

public class Capture implements Runnable {

	AudioInputStream audioInputStream;
	TargetDataLine line;
	Thread thread;
	String errStr;
	double duration, seconds;
//	static TransparentRecordingFrame overlayframe;

	public Capture() {
		
	}

	public void start() {
//		overlayframe = new TransparentRecordingFrame();
//		try {
//			if(Config.getParam("server").equals("1"))
//				overlayframe.setVisible(true);
//		} catch (Exception e) {
//			try {
//				if(Config.getParam("server").equals("1"))
//					overlayframe.setVisible(true);
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//		}
		errStr = null;
		thread = new Thread(this);
		thread.setName("Capture");
		thread.start();
	}

	public void stop() {
		thread = null;
//		try {
//			if(Config.getParam("server").equals("1"))
//				overlayframe.setVisible(false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private void shutDown(String message) {
		if ((errStr = message) != null && thread != null) {
			thread = null;
			System.err.println(errStr);
		}
	}

	public void run() {

		duration = 0;
		audioInputStream = null;

		// define the required attributes for our line,
		// and make sure a compatible line is supported.

		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
		float rate = 44100.0f;
		int channels = 2;
		int frameSize = 4;
		int sampleSize = 16;
		boolean bigEndian = true;

		AudioFormat format = new AudioFormat(encoding, rate, sampleSize,
				channels, (sampleSize / 8) * channels, rate, bigEndian);

		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)) {
			shutDown("Line matching " + info + " not supported.");
			return;
		}

		// get and open the target data line for capture.

		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format, line.getBufferSize());
		} catch (LineUnavailableException ex) {
			shutDown("Unable to open the line: " + ex);
			return;
		} catch (SecurityException ex) {
			shutDown(ex.toString());
			// JavaSound.showInfoDialog();
			return;
		} catch (Exception ex) {
			shutDown(ex.toString());
			return;
		}

		// play back the captured audio data
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int frameSizeInBytes = format.getFrameSize();
		int bufferLengthInFrames = line.getBufferSize() / 8;
		int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
		byte[] data = new byte[bufferLengthInBytes];
		int numBytesRead;

		line.start();

		while (thread != null) {
			if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1) {
				break;
			}
			out.write(data, 0, numBytesRead);
		}

		// we reached the end of the stream.
		// stop and close the line.
		line.stop();
		line.close();
		line = null;

		// stop and close the output stream
		try {
			out.flush();
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// load bytes into the audio input stream for playback

		byte audioBytes[] = out.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
		audioInputStream = new AudioInputStream(bais, format,
				audioBytes.length / frameSizeInBytes);

		long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format
				.getFrameRate());
		duration = milliseconds / 1000.0;

		try {
			audioInputStream.reset();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

	public AudioInputStream getAudioInputStream() {
		return audioInputStream;
	}

	public void setAudioInputStream(AudioInputStream audioInputStream) {
		this.audioInputStream = audioInputStream;
	}
}