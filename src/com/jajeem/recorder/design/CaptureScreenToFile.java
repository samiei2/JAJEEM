/*
 * Copyright (c) 2008, 2009 by Xuggle Incorporated.  All rights reserved.
 *
 * This file is part of Xuggler.
 *
 * You can redistribute Xuggler and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Xuggler is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Xuggler.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.jajeem.recorder.design;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import com.xuggle.mediatool.IMediaCoder;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;

/**
 * Using {@link IMediaWriter}, takes snapshots of your desktop and encodes them
 * to video.
 * 
 * @author aclarke
 * 
 */

public class CaptureScreenToFile {
	private static IRational FRAME_RATE = IRational.make(30, 1);
	private static final int SECONDS_TO_RUN_FOR = 15;
	private static boolean running = false;

	/**
	 * Takes a screen shot of your entire screen and writes it to output.flv
	 * 
	 * @param args
	 */
	public CaptureScreenToFile(){
		File file = new File("Recordings");
		if(!file.exists())
			file.mkdir();
		else
			if(!file.isDirectory()){
				file.delete();
				file.mkdir();
			}
	}

	public static void main(String[] args) {
		StartCaputre("temp.mp4");
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StopCapture();
	}

	public static void StartCaputre(String file) {
		try {
			if(file == "")
				file = "temp.mp4";
			final String outFile;
			outFile = "Recordings/recording - "+System.currentTimeMillis()+".avi";
			running = true;
			// This is the robot for taking a snapshot of the
			// screen. It's part of Java AWT

			Thread recorder = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						final Robot robot = new Robot();
						final Toolkit toolkit = Toolkit.getDefaultToolkit();
						final Rectangle screenBounds = new Rectangle(toolkit
								.getScreenSize());

						// First, let's make a IMediaWriter to write the file.
						final IMediaWriter writer = ToolFactory
								.makeWriter(outFile);

						// We tell it we're going to add one video stream, with
						// id
						// 0,
						// at position 0, and that it will have a fixed frame
						// rate
						// of
						// FRAME_RATE.
						writer.addVideoStream(0, 0, FRAME_RATE,
								screenBounds.width, screenBounds.height);

						// Now, we're going to loop
						long startTime = System.nanoTime();
						int index = 0;
						while (running) {

							// take the screen shot
							BufferedImage screen = robot
									.createScreenCapture(screenBounds);

							// convert to the right image type
							BufferedImage bgrScreen = convertToType_2(screen,
									BufferedImage.TYPE_3BYTE_BGR);

							// encode the image
							writer.encodeVideo(0, bgrScreen, System.nanoTime()
									- startTime, TimeUnit.NANOSECONDS);

							System.out.println("encoded image: " + index++);

							// sleep for framerate milliseconds
							Thread.sleep((long) (1000 / FRAME_RATE.getDouble()));

						}
						// Finally we tell the writer to close and write the
						// trailer
						// if
						// needed
						writer.close();
					} catch (Exception e) {
						System.err.println("an error occurred: "
								+ e.getMessage());
					}
				}
			});
			recorder.start();

		} catch (Throwable e) {
			System.err.println("an error occurred: " + e.getMessage());
		}
	}
	
	public static void StartCaputreWithAudio(String file) {
		try {
			final String outFile;
			outFile = "Recordings/temp.mp4";
			running = true;
			// This is the robot for taking a snapshot of the
			// screen. It's part of Java AWT

			Thread recorder = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Capture capt = new Capture();
						capt.start();
						final Robot robot = new Robot();
						final Toolkit toolkit = Toolkit.getDefaultToolkit();
						final Rectangle screenBounds = new Rectangle(toolkit
								.getScreenSize());

						// First, let's make a IMediaWriter to write the file.
						final IMediaWriter writer = ToolFactory
								.makeWriter(outFile);

						// We tell it we're going to add one video stream, with
						// id
						// 0,
						// at position 0, and that it will have a fixed frame
						// rate
						// of
						// FRAME_RATE.
						writer.addVideoStream(0, 0, FRAME_RATE,
								screenBounds.width, screenBounds.height);

						// Now, we're going to loop
						long startTime = System.nanoTime();
						int index = 0;
						while (running) {

							// take the screen shot
							BufferedImage screen = robot
									.createScreenCapture(screenBounds);

							// convert to the right image type
							BufferedImage bgrScreen = convertToType_2(screen,
									BufferedImage.TYPE_3BYTE_BGR);

							// encode the image
							writer.encodeVideo(0, bgrScreen, System.nanoTime()
									- startTime, TimeUnit.NANOSECONDS);

							System.out.println("encoded image: " + index++);

							// sleep for framerate milliseconds
							Thread.sleep((long) (1000 / FRAME_RATE.getDouble()));

						}
						// Finally we tell the writer to close and write the
						// trailer
						// if
						// needed
						writer.close();
						capt.stop();
						Thread.sleep(1000);
						FileOutputStream out = new FileOutputStream("Recordings/temp.mp3");
						AudioSystem.write(capt.audioInputStream, AudioFileFormat.Type.AIFF, out);
						out.flush();
						out.close();
						Thread.sleep(1000);
						Synchornize();
					} catch (Exception e) {
						System.err.println("an error occurred: "
								+ e.getMessage());
					}
				}
			});
			recorder.start();
			

		} catch (Throwable e) {
			System.err.println("an error occurred: " + e.getMessage());
		}
	}

	public static void StopCapture() {
		running = false;
	}

	/**
	 * Convert a {@link BufferedImage} of any type, to {@link BufferedImage} of
	 * a specified type. If the source image is the same type as the target
	 * type, then original image is returned, otherwise new image of the correct
	 * type is created and the content of the source image is copied into the
	 * new image.
	 * 
	 * @param sourceImage
	 *            the image to be converted
	 * @param targetType
	 *            the desired BufferedImage type
	 * 
	 * @return a BufferedImage of the specifed target type.
	 * 
	 * @see BufferedImage
	 */

	public static BufferedImage convertToType(BufferedImage sourceImage,
			int targetType) {
		BufferedImage image;

		// if the source image is already the target type, return the source
		// image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

		// otherwise create a new image of the target type and draw the new
		// image

		else {
			image = new BufferedImage(sourceImage.getWidth(),
					sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		return image;
	}

	public static BufferedImage convertToType_2(BufferedImage sourceImage,
			int targetType) {
		BufferedImage image;

		// if the source image is already the target type, return the source
		// image

		if (sourceImage.getType() == targetType)
			image = sourceImage;

		// otherwise create a new image of the target type and draw the new
		// image

		else {
			image = new BufferedImage(sourceImage.getWidth(),
					sourceImage.getHeight(), targetType);
			image.getGraphics().drawImage(sourceImage, 0, 0, null);
		}

		Image cursor;
		cursor = Toolkit.getDefaultToolkit().getImage(
				CaptureScreenToFile.class.getResource("/com/jajeem/images/cursor.png"));

		int x = MouseInfo.getPointerInfo().getLocation().x;
		int y = MouseInfo.getPointerInfo().getLocation().y;

		Graphics2D graphics2D = image.createGraphics();
		graphics2D.drawImage(cursor, x, y, 16, 16, null); // cursor.gif is
															// 16x16 size.

		return image;
	}

	public static void Synchornize() {
		String filenamevideo = "Recordings/temp.mp4"; // this is the input
		// file for video.
		// you can change
		// extension
		String filenameaudio = "Recordings/temp.mp3"; // this is the input
		// file for audio.
		// you can change
		// extension

		IMediaWriter mWriter = ToolFactory
				.makeWriter("Recordings/recording - "+System.currentTimeMillis()+".mp4"); // output
		// file

		IContainer containerVideo = IContainer.make();
		IContainer containerAudio = IContainer.make();
		
		

		if (containerVideo.open(filenamevideo, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("Cant find " + filenamevideo);

		if (containerAudio.open(filenameaudio, IContainer.Type.READ, null) < 0)
			throw new IllegalArgumentException("Cant find " + filenameaudio);

		int numStreamVideo = containerVideo.getNumStreams();
		int numStreamAudio = containerAudio.getNumStreams();

		System.out.println("Number of video streams: " + numStreamVideo + "\n"
				+ "Number of audio streams: " + numStreamAudio);

		int videostreamt = -1; // this is the video stream id
		int audiostreamt = -1;

		IStreamCoder videocoder = null;

		for (int i = 0; i < numStreamVideo; i++) {
			IStream stream = containerVideo.getStream(i);
			IStreamCoder code = stream.getStreamCoder();

			if (code.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
				videostreamt = i;
				videocoder = code;
				break;
			}

		}

		for (int i = 0; i < numStreamAudio; i++) {
			IStream stream = containerAudio.getStream(i);
			IStreamCoder code = stream.getStreamCoder();

			if (code.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
				audiostreamt = i;
				break;
			}

		}

		if (videostreamt == -1)
			throw new RuntimeException("No video steam found");
		if (audiostreamt == -1)
			throw new RuntimeException("No audio steam found");

		if (videocoder.open() < 0)
			throw new RuntimeException("Cant open video coder");
		IPacket packetvideo = IPacket.make();

		IStreamCoder audioCoder = containerAudio.getStream(audiostreamt)
				.getStreamCoder();

		if (audioCoder.open() < 0)
			throw new RuntimeException("Cant open audio coder");
		mWriter.addAudioStream(1, 1, audioCoder.getChannels(),
				audioCoder.getSampleRate());

		mWriter.addVideoStream(0, 0, videocoder.getWidth(),
				videocoder.getHeight());

		IPacket packetaudio = IPacket.make();

		while (containerVideo.readNextPacket(packetvideo) >= 0
				|| containerAudio.readNextPacket(packetaudio) >= 0) {

			if (packetvideo.getStreamIndex() == videostreamt) {

				// video packet
				IVideoPicture picture = IVideoPicture.make(
						videocoder.getPixelType(), videocoder.getWidth(),
						videocoder.getHeight());
				int offset = 0;
				while (offset < packetvideo.getSize()) {
					int bytesDecoded = videocoder.decodeVideo(picture,
							packetvideo, offset);
					if (bytesDecoded < 0)
						throw new RuntimeException("bytesDecoded not working");
					offset += bytesDecoded;

					if (picture.isComplete()) {
						System.out.println(picture.getPixelType());
						mWriter.encodeVideo(0, picture);

					}
				}
			}

			if (packetaudio.getStreamIndex() == audiostreamt) {
				// audio packet

				IAudioSamples samples = IAudioSamples.make(512,
						audioCoder.getChannels(), IAudioSamples.Format.FMT_S32);
				int offset = 0;
				while (offset < packetaudio.getSize()) {
					int bytesDecodedaudio = audioCoder.decodeAudio(samples,
							packetaudio, offset);
					if (bytesDecodedaudio < 0)
						throw new RuntimeException("could not detect audio");
					offset += bytesDecodedaudio;

					if (samples.isComplete()) {
						mWriter.encodeAudio(1, samples);

					}
				}

			}
		}
		
		videocoder.close();
		audioCoder.close();
		containerAudio.close();
		containerVideo.close();
		
		mWriter.flush();
		mWriter.close();
		new File("Recordings/temp.mp4").delete();
		new File("Recordings/temp.mp3").delete();
	}
	
}
