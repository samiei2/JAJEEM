/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.examples;

import java.awt.Component;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

import javax.swing.JDialog;

import org.jitsi.service.libjitsi.LibJitsi;
import org.jitsi.service.neomedia.DefaultStreamConnector;
import org.jitsi.service.neomedia.MediaDirection;
import org.jitsi.service.neomedia.MediaService;
import org.jitsi.service.neomedia.MediaStream;
import org.jitsi.service.neomedia.MediaStreamTarget;
import org.jitsi.service.neomedia.MediaType;
import org.jitsi.service.neomedia.MediaUseCase;
import org.jitsi.service.neomedia.StreamConnector;
import org.jitsi.service.neomedia.VideoMediaStream;
import org.jitsi.service.neomedia.device.MediaDevice;
import org.jitsi.service.neomedia.format.MediaFormat;
import org.jitsi.service.neomedia.format.MediaFormatFactory;

/**
 * Implements an example application in the fashion of JMF's AVReceive2 example
 * which demonstrates the use of the <tt>libjitsi</tt> library for the purposes
 * of receiving audio and video via RTP means.
 * 
 * @author Lyubomir Marinov
 */
public class AVReceiveOnly {

	JDialog frame;
	/**
	 * The port which is the target of the transmission i.e. on which the media
	 * is to be received.
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
	 * The <tt>InetAddress</tt> of the host which is the target of the receipt
	 * i.e. from which the media is to be received.
	 * 
	 * @see #REMOTE_HOST_ARG_NAME
	 */
	private InetAddress remoteAddr;

	/**
	 * The port which is the target of the receipt i.e. from which the media is
	 * to be received.
	 * 
	 * @see #REMOTE_PORT_BASE_ARG_NAME
	 */
	private int remotePortBase;

	/**
	 * Initializes a new <tt>AVReceive2</tt> instance which is to receive audio
	 * and video from a specific host and a specific port.
	 * 
	 * @param localPortBase
	 *            the port on which the audio and video are to be received
	 * @param remoteHost
	 *            the name of the host from which the media is transmitted
	 * @param remotePortBase
	 *            the port from which the media is transmitted
	 * @throws Exception
	 *             if any error arises during the parsing of the specified
	 *             <tt>localPortBase</tt>, <tt>remoteHost</tt> and
	 *             <tt>remotePortBase</tt>
	 */
	public AVReceiveOnly(String localPortBase, String remoteHost,
			String remotePortBase) throws Exception {
		this.localPortBase = (localPortBase == null) ? -1 : Integer.valueOf(
				localPortBase).intValue();
		this.setRemoteAddr(InetAddress.getByName(remoteHost));
		this.remotePortBase = Integer.valueOf(remotePortBase).intValue();
	}

	/**
	 * Initializes the receipt of audio and video.
	 * 
	 * @return <tt>true</tt> if this instance has been successfully initialized
	 *         to receive audio and video
	 * @throws Exception
	 *             if anything goes wrong while initializing this instance for
	 *             the receipt of audio and video
	 */
	public boolean initialize(String type) throws Exception {
		/*
		 * Prepare for the start of the transmission i.e. initialize the
		 * MediaStream instances.
		 */
		MediaType[] mediaTypes = MediaType.values();
		MediaService mediaService = LibJitsi.getMediaService();
		int localPort = localPortBase;
		int remotePort = remotePortBase;

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
			mediaStream.setDirection(MediaDirection.RECVONLY);

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

			if (localPortBase == -1) {
				connector = new DefaultStreamConnector();
			} else {
				int localRTPPort = localPort++;
				int localRTCPPort = localPort++;

				connector = new DefaultStreamConnector(new MulticastSocket(
						localRTPPort), new MulticastSocket(localRTCPPort));
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

		for (MediaStream mediaStream : mediaStreams) {
			if (mediaStream != null) {
				if (type.equals("both")
						&& mediaStream.getName().equals("video")) {
					Component cmp = ((VideoMediaStream) mediaStream)
							.getVisualComponent();
					while (cmp == null) {
						cmp = ((VideoMediaStream) mediaStream)
								.getVisualComponent();
					}

					if (cmp != null) {

						if (frame == null) {
							frame = new JDialog();
							frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
							// frame.setLayout(new BorderLayout(0, 0));
							frame.getContentPane().add(cmp);
							frame.setSize(400, 400);
							frame.repaint();
							frame.setAlwaysOnTop(true);
							frame.setVisible(true);
						}
					}

				}
			}
		}
		return true;
	}

	/**
	 * Close the <tt>MediaStream</tt>s.
	 */
	public void close() {
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
			if(frame!=null){
				frame.setVisible(false);
				frame = null;
			}
		}
	}

	/**
	 * The name of the command-line argument which specifies the port on which
	 * the media is to be received. The command-line argument value will be used
	 * as the port to receive the audio RTP on, the next port after it will be
	 * used to receive the audio RTCP on. Respectively, the subsequent ports
	 * ports will be used to transmit the video RTP and RTCP on."
	 */
	private static final String LOCAL_PORT_BASE_ARG_NAME = "--local-port-base=";

	/**
	 * The name of the command-line argument which specifies the name of the
	 * host from which the media is to be received.
	 */
	private static final String REMOTE_HOST_ARG_NAME = "--remote-host=";

	/**
	 * The name of the command-line argument which specifies the port from which
	 * the media is to be received. The command-line argument value will be used
	 * as the port to receive the audio RTP from, the next port after it will be
	 * to receive the audio RTCP from. Respectively, the subsequent ports will
	 * be used to receive the video RTP and RTCP from."
	 */
	private static final String REMOTE_PORT_BASE_ARG_NAME = "--remote-port-base=";

	public static void main(String[] args) throws Exception {
		// We need three parameters to do the transmission. For example,
		// ant run-example -Drun.example.name=AVReceive2
		// -Drun.example.arg.line="--local-port-base=10000 --remote-host=129.130.131.132 --remote-port-base=5000"
		// if (args.length < 3)
		// {
		// prUsage();
		// }
		// else
		// {
		// Map<String, String> argMap = AVTransmit2.parseCommandLineArgs(args);

		LibJitsi.start();
		try {
			AVReceiveOnly avReceive = new AVReceiveOnly("10000",
					"194.168.0.119", "5000");

			if (avReceive.initialize("both")) {
				try {
					/*
					 * Wait for the media to be received and played back.
					 * AVTransmit2 transmits for 1 minute so AVReceive2 waits
					 * for 2 minutes to allow AVTransmit2 to start the
					 * tranmission with a bit of a delay (if necessary).
					 */
					long then = System.currentTimeMillis();
					long waitingPeriod = 2 * 600000;

					try {
						while ((System.currentTimeMillis() - then) < waitingPeriod) {
							Thread.sleep(1000);
						}
					} catch (InterruptedException ie) {
					}
				} finally {
					avReceive.close();
				}

				System.err.println("Exiting AVReceive2");
			} else {
				System.err.println("Failed to initialize the sessions.");
			}
		} finally {
			LibJitsi.stop();
		}
		// }
	}

	public InetAddress getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(InetAddress remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
}
