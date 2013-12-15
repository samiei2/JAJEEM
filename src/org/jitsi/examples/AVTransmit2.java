/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.examples;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

import org.jitsi.service.libjitsi.LibJitsi;
import org.jitsi.service.neomedia.DefaultStreamConnector;
import org.jitsi.service.neomedia.MediaDirection;
import org.jitsi.service.neomedia.MediaService;
import org.jitsi.service.neomedia.MediaStream;
import org.jitsi.service.neomedia.MediaStreamTarget;
import org.jitsi.service.neomedia.MediaType;
import org.jitsi.service.neomedia.MediaUseCase;
import org.jitsi.service.neomedia.StreamConnector;
import org.jitsi.service.neomedia.device.MediaDevice;
import org.jitsi.service.neomedia.format.MediaFormat;
import org.jitsi.service.neomedia.format.MediaFormatFactory;

/**
 * Implements an example application in the fashion of JMF's AVTransmit2 example
 * which demonstrates the use of the <tt>libjitsi</tt> library for the purposes
 * of transmitting audio and video via RTP means.
 * 
 * @author Lyubomir Marinov
 */
public class AVTransmit2 {
	private boolean transmitting = false;
	/**
	 * The port which is the source of the transmission i.e. from which the
	 * media is to be transmitted.
	 * 
	 * @see #LOCAL_PORT_BASE_ARG_NAME
	 */
	private int localPortBase;

	/**
	 * The <tt>MediaStream</tt> instances initialized by this instance indexed
	 * by their respective <tt>MediaType</tt> ordinal.
	 */
	private MediaStream[] mediaStreams;

	/**
	 * The <tt>InetAddress</tt> of the host which is the target of the
	 * transmission i.e. to which the media is to be transmitted.
	 * 
	 * @see #REMOTE_HOST_ARG_NAME
	 */
	private InetAddress remoteAddr;

	/**
	 * The port which is the target of the transmission i.e. to which the media
	 * is to be transmitted.
	 * 
	 * @see #REMOTE_PORT_BASE_ARG_NAME
	 */
	private int remotePortBase;

	/**
	 * Initializes a new <tt>AVTransmit2</tt> instance which is to transmit
	 * audio and video to a specific host and a specific port.
	 * 
	 * @param localPortBase
	 *            the port which is the source of the transmission i.e. from
	 *            which the media is to be transmitted
	 * @param remoteHost
	 *            the name of the host which is the target of the transmission
	 *            i.e. to which the media is to be transmitted
	 * @param remotePortBase
	 *            the port which is the target of the transmission i.e. to which
	 *            the media is to be transmitted
	 * @throws Exception
	 *             if any error arises during the parsing of the specified
	 *             <tt>localPortBase</tt>, <tt>remoteHost</tt> and
	 *             <tt>remotePortBase</tt>
	 */
	public AVTransmit2(String localPortBase, String remoteHost,
			String remotePortBase) throws Exception {
		this.setLocalPortBase((localPortBase == null) ? -1 : Integer.valueOf(
				localPortBase).intValue());
		this.setRemoteAddr(InetAddress.getByName(remoteHost));
		this.setRemotePortBase(Integer.valueOf(remotePortBase).intValue());
	}

	/**
	 * Starts the transmission. Returns null if transmission started ok.
	 * Otherwise it returns a string with the reason why the setup failed.
	 */
	public String start(String type) throws Exception {
		/*
		 * Prepare for the start of the transmission i.e. initialize the
		 * MediaStream instances.
		 */
		MediaType[] mediaTypes = MediaType.values();
		MediaService mediaService = LibJitsi.getMediaService();
		int localPort = getLocalPortBase();
		int remotePort = getRemotePortBase();

		mediaStreams = new MediaStream[mediaTypes.length];
		for (MediaType mediaType : mediaTypes) {
			/*
			 * The default MediaDevice (for a specific MediaType) is configured
			 * (by the user of the application via some sort of UI) into the
			 * ConfigurationService. If there is no ConfigurationService
			 * instance known to LibJitsi, the first available MediaDevice of
			 * the specified MediaType will be chosen by MediaService.
			 */
			MediaDevice device = mediaService.getDefaultDevice(mediaType,
					MediaUseCase.CALL);
			MediaStream mediaStream = mediaService.createMediaStream(device);

			// direction
			/*
			 * The AVTransmit2 example sends only and the AVReceive2 receives
			 * only. In a call, the MediaStream's direction will most commonly
			 * be set to SENDRECV.
			 */
			mediaStream.setDirection(MediaDirection.SENDRECV);

			// format
			String encoding;
			double clockRate;
			/*
			 * The AVTransmit2 and AVReceive2 examples use the H.264 video
			 * codec. Its RTP transmission has no static RTP payload type number
			 * assigned.
			 */
			byte dynamicRTPPayloadType;

			switch (device.getMediaType()) {
			case AUDIO:
				encoding = "PCMU";
				clockRate = 8000;
				/* PCMU has a static RTP payload type number assigned. */
				dynamicRTPPayloadType = -1;
				break;
			case VIDEO:
				encoding = "H264";
				clockRate = MediaFormatFactory.CLOCK_RATE_NOT_SPECIFIED;
				/*
				 * The dymanic RTP payload type numbers are usually negotiated
				 * in the signaling functionality.
				 */
				dynamicRTPPayloadType = 99;
				break;
			default:
				encoding = null;
				clockRate = MediaFormatFactory.CLOCK_RATE_NOT_SPECIFIED;
				dynamicRTPPayloadType = -1;
			}

			if (encoding != null) {
				MediaFormat format = mediaService.getFormatFactory()
						.createMediaFormat(encoding, clockRate);

				/*
				 * The MediaFormat instances which do not have a static RTP
				 * payload type number association must be explicitly assigned a
				 * dynamic RTP payload type number.
				 */
				if (dynamicRTPPayloadType != -1) {
					mediaStream.addDynamicRTPPayloadType(dynamicRTPPayloadType,
							format);
				}

				mediaStream.setFormat(format);
			}

			// connector
			StreamConnector connector;

			if (getLocalPortBase() == -1) {
				connector = new DefaultStreamConnector();
			} else {
				int localRTPPort = localPort++;
				int localRTCPPort = localPort++;

				MulticastSocket s1 = new MulticastSocket(localRTPPort);

				MulticastSocket s2 = new MulticastSocket(localRTCPPort);

				connector = new DefaultStreamConnector(s1, s2);
			}
			mediaStream.setConnector(connector);

			// target
			/*
			 * The AVTransmit2 and AVReceive2 examples follow the common
			 * practice that the RTCP port is right after the RTP port.
			 */
			int remoteRTPPort = remotePort++;
			int remoteRTCPPort = remotePort++;

			mediaStream.setTarget(new MediaStreamTarget(new InetSocketAddress(
					getRemoteAddr(), remoteRTPPort), new InetSocketAddress(
					getRemoteAddr(), remoteRTCPPort)));

			// name
			/*
			 * The name is completely optional and it is not being used by the
			 * MediaStream implementation at this time, it is just remembered so
			 * that it can be retrieved via MediaStream#getName(). It may be
			 * integrated with the signaling functionality if necessary.
			 */
			mediaStream.setName(mediaType.toString());

			mediaStreams[mediaType.ordinal()] = mediaStream;
		}

		/*
		 * Do start the transmission i.e. start the initialized MediaStream
		 * instances.
		 */
		for (MediaStream mediaStream : mediaStreams) {
			if (mediaStream != null) {
				if (type.equals("both")) {
					mediaStream.start();
				} else if (type.equals("audio")
						&& mediaStream.getName().equals("audio")) {
					mediaStream.start();
				} else {
					continue;
				}
			}
		}

		setTransmitting(true);

		return null;
	}

	/**
	 * Stops the transmission if already started
	 */
	public void stop() {
		if (mediaStreams != null) {
			for (int i = 0; i < mediaStreams.length; i++) {
				MediaStream mediaStream = mediaStreams[i];

				if (mediaStream != null) {
					try {
						mediaStream.stop();
					} finally {
						mediaStream.close();
						mediaStreams[i] = null;
					}
				}
			}

			mediaStreams = null;

			setTransmitting(false);
		}
	}

	public InetAddress getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(InetAddress remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public boolean isTransmitting() {
		return transmitting;
	}

	public void setTransmitting(boolean transmitting) {
		this.transmitting = transmitting;
	}

	public int getRemotePortBase() {
		return remotePortBase;
	}

	public void setRemotePortBase(int remotePortBase) {
		this.remotePortBase = remotePortBase;
	}

	public int getLocalPortBase() {
		return localPortBase;
	}

	public void setLocalPortBase(int localPortBase) {
		this.localPortBase = localPortBase;
	}
}
