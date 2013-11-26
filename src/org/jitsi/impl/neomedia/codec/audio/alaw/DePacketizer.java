/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.neomedia.codec.audio.alaw;

import javax.media.Buffer;
import javax.media.Format;
import javax.media.format.AudioFormat;

import org.jitsi.service.neomedia.codec.Constants;

import com.sun.media.codec.audio.AudioCodec;

/**
 * DePacketizer for ALAW codec
 * 
 * @author Damian Minkov
 */
public class DePacketizer extends AudioCodec {
	/**
	 * Creates DePacketizer
	 */
	public DePacketizer() {
		inputFormats = new Format[] { new AudioFormat(Constants.ALAW_RTP) };
	}

	/**
	 * Returns the name of the DePacketizer
	 * 
	 * @return String
	 */
	@Override
	public String getName() {
		return "ALAW DePacketizer";
	}

	/**
	 * Returns the supported output formats
	 * 
	 * @param in
	 *            Format
	 * @return Format[]
	 */
	@Override
	public Format[] getSupportedOutputFormats(Format in) {

		if (in == null) {
			return new Format[] { new AudioFormat(AudioFormat.ALAW) };
		}

		if (matches(in, inputFormats) == null) {
			return new Format[1];
		}

		if (!(in instanceof AudioFormat)) {
			return new Format[] { new AudioFormat(AudioFormat.ALAW) };
		}

		AudioFormat af = (AudioFormat) in;
		return new Format[] { new AudioFormat(AudioFormat.ALAW,
				af.getSampleRate(), af.getSampleSizeInBits(), af.getChannels()) };
	}

	/**
	 * Initializes the codec.
	 */
	@Override
	public void open() {
	}

	/**
	 * Clean up
	 */
	@Override
	public void close() {
	}

	/**
	 * decode the buffer
	 * 
	 * @param inputBuffer
	 *            Buffer
	 * @param outputBuffer
	 *            Buffer
	 * @return int
	 */
	@Override
	public int process(Buffer inputBuffer, Buffer outputBuffer) {

		if (!checkInputBuffer(inputBuffer)) {
			return BUFFER_PROCESSED_FAILED;
		}

		if (isEOM(inputBuffer)) {
			propagateEOM(outputBuffer);
			return BUFFER_PROCESSED_OK;
		}

		Object outData = outputBuffer.getData();
		outputBuffer.setData(inputBuffer.getData());
		inputBuffer.setData(outData);
		outputBuffer.setLength(inputBuffer.getLength());
		outputBuffer.setFormat(outputFormat);
		outputBuffer.setOffset(inputBuffer.getOffset());
		return BUFFER_PROCESSED_OK;
	}
}
