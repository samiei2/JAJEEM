package com.jajeem.recorder.design;

import info.clearthought.layout.TableLayout;

import java.awt.EventQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JFrame;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.button.WebButton;
import com.alee.utils.SwingUtils;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Window.Type;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

public class Recorder extends WebDialog {
	
	private WebButton wbtnPlay;
	private WebButton wbtnRecord;
	
	private static boolean isRecording = false;
	
	private AudioInputStream audioInputStream;
	
	Capture capt = new Capture();
	
	Playback play = new Playback();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Recorder frame = new Recorder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Recorder() {
		//setAlwaysOnTop(true);
		setModal(true);
		setRound(0);
		
		setResizable(false);
		setBounds(100,100,349,110);
		getContentPane().setLayout(null);
		
		wbtnPlay = new WebButton();
		wbtnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(wbtnPlay.getText().equals("Stop")){
					play.stop();
					wbtnPlay.setText("Play");
				}
				else{
					play.start();
					wbtnPlay.setText("Stop");
				}
			}
		});
		wbtnPlay.setText("Play");
		wbtnPlay.setBounds(10, 11, 93, 29);
		getContentPane().add(wbtnPlay);
		
		wbtnRecord = new WebButton();
		wbtnRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(wbtnRecord.getText().equals("Record")){
					wbtnPlay.setEnabled(false);
					capt.start();
					wbtnRecord.setText("Stop");
				}
				else{
					wbtnPlay.setEnabled(true);
					wbtnRecord.setText("Record");
					capt.stop();
				}
			}
		});
		wbtnRecord.setText("Record");
		wbtnRecord.setBounds(113, 11, 93, 29);
		getContentPane().add(wbtnRecord);
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		SwingUtils.equalizeComponentsWidths(wbtnPlay,wbtnRecord);
		
		WebButton wbtnSave = new WebButton();
		wbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileOutputStream fileout = null;
				try {
					fileout = new FileOutputStream("record-"+System.currentTimeMillis()+".mp3");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				OutputStreamWriter writer = new OutputStreamWriter(fileout);
				
				int b;
				
				try {
					while((b = audioInputStream.read())!=-1){
						writer.write(b);
					}
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		wbtnSave.setText("Save");
		wbtnSave.setBounds(216, 11, 93, 29);
		getContentPane().add(wbtnSave);
//		pack();
		//setVisible(true);
	}
	
	public class Capture implements Runnable {

		TargetDataLine line;
		Thread thread;
		String errStr;
		double duration, seconds;

		public Capture() {

		}

		public void start() {
			errStr = null;
			thread = new Thread(this);
			thread.setName("Capture");
			thread.start();
		}

		public void stop() {
			thread = null;
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
			audioInputStream = new AudioInputStream(bais, format, audioBytes.length
					/ frameSizeInBytes);

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
	}

	public class Playback implements Runnable {

		SourceDataLine line;
		String errStr;
		Thread thread;
		final int bufSize = 16384;
		double duration, seconds;

		public void start() {
			errStr = null;
			thread = new Thread(this);
			thread.setName("Playback");
			thread.start();
		}

		public void stop() {
			thread = null;
		}

		private void shutDown(String message) {
			if ((errStr = message) != null) {
				System.err.println(errStr);
			}
			if (thread != null) {
				thread = null;
			}
		}

		public void run() {

			// make sure we have something to play
			if (audioInputStream == null) {
				shutDown("No loaded audio to play back");
				return;
			}
			// reset to the beginnning of the stream
			try {
				audioInputStream.reset();
			} catch (Exception e) {
				shutDown("Unable to reset the stream\n" + e);
				return;
			}

			// get an AudioInputStream of the desired format for playback

			AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
			float rate = 44100.0f;
			int channels = 2;
			int frameSize = 4;
			int sampleSize = 16;
			boolean bigEndian = true;

			AudioFormat format = new AudioFormat(encoding, rate, sampleSize,
					channels, (sampleSize / 8) * channels, rate, bigEndian);

			AudioInputStream playbackInputStream = AudioSystem.getAudioInputStream(
					format, audioInputStream);

			if (playbackInputStream == null) {
				shutDown("Unable to convert stream of format " + audioInputStream
						+ " to format " + format);
				return;
			}

			// define the required attributes for our line,
			// and make sure a compatible line is supported.

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				shutDown("Line matching " + info + " not supported.");
				return;
			}

			// get and open the source data line for playback.

			try {
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(format, bufSize);
			} catch (LineUnavailableException ex) {
				shutDown("Unable to open the line: " + ex);
				return;
			}

			// play back the captured audio data

			int frameSizeInBytes = format.getFrameSize();
			int bufferLengthInFrames = line.getBufferSize() / 8;
			int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
			byte[] data = new byte[bufferLengthInBytes];
			int numBytesRead = 0;

			// start the source data line
			line.start();

			while (thread != null) {
				try {
					if ((numBytesRead = playbackInputStream.read(data)) == -1) {
						break;
					}
					int numBytesRemaining = numBytesRead;
					while (numBytesRemaining > 0) {
						numBytesRemaining -= line.write(data, 0, numBytesRemaining);
					}
				} catch (Exception e) {
					shutDown("Error during playback: " + e);
					break;
				}
			}
			// we reached the end of the stream.
			// let the data play out, then
			// stop and close the line.
			if (thread != null) {
				line.drain();
			}
			line.stop();
			line.close();
			line = null;
			shutDown(null);
		}

		
	} // End class Playback
}
