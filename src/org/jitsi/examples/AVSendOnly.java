/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.examples;

import java.awt.BorderLayout;
import java.awt.Component;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

import com.jajeem.exception.VideoConnectionException;

/**
 * Implements an example application in the fashion of JMF's AVTransmit2 example
 * which demonstrates the use of the <tt>libjitsi</tt> library for the purposes
 * of transmitting audio and video via RTP means.
 * 
 * @author Lyubomir Marinov
 */
public class AVSendOnly {
	JDialog frame = null;

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
	public AVSendOnly(String localPortBase, String remoteHost,
			String remotePortBase) throws Exception {
		this.localPortBase = (localPortBase == null) ? -1 : Integer.valueOf(
				localPortBase).intValue();
		this.setRemoteAddr(InetAddress.getByName(remoteHost));
		this.remotePortBase = Integer.valueOf(remotePortBase).intValue();
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
			mediaStream.setDirection(MediaDirection.SENDONLY);

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

		setTransmitting(true);

		// trying to add a local view of webcam, most of the time webcam has not
		// started yet, so we wait for it...
		for (MediaStream mediaStream : mediaStreams) {
			if (mediaStream != null) {
				if (type.equals("both")) {
					if (mediaStream.getName().equals("video")) {
						Component cmp = ((VideoMediaStream) mediaStream)
								.getLocalVisualComponent();
						int i = 0;
						while (cmp == null) {
							cmp = ((VideoMediaStream) mediaStream)
									.getLocalVisualComponent();
							Thread.sleep(1000);
							if (i == 10) {
								break;
							}
							i++;
						}
						try {
							if (cmp != null) {
								if (frame == null) {
									frame = new JDialog();
									frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
									frame.setLayout(new BorderLayout(0, 0));
									frame.add(cmp);
									frame.setSize(400, 400);
									frame.setVisible(true);
								}
							} else {
								JOptionPane
										.showMessageDialog(null,
												"Could not start webchat,the other system is probably is not responding!");
								throw new VideoConnectionException(
										"Video Error");
							}
						} catch (Exception e) {
							if (e instanceof VideoConnectionException) {
								throw new VideoConnectionException(
										"Video Error");
							} else {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

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

			if (frame != null) {
				frame.setVisible(false);
				frame = null;
			}
		}
	}

	/**
	 * The name of the command-line argument which specifies the port from which
	 * the media is to be transmitted. The command-line argument value will be
	 * used as the port to transmit the audio RTP from, the next port after it
	 * will be to transmit the audio RTCP from. Respectively, the subsequent
	 * ports will be used to transmit the video RTP and RTCP from."
	 */
	private static final String LOCAL_PORT_BASE_ARG_NAME = "--local-port-base=";

	/**
	 * The name of the command-line argument which specifies the name of the
	 * host to which the media is to be transmitted.
	 */
	private static final String REMOTE_HOST_ARG_NAME = "--remote-host=";

	/**
	 * The name of the command-line argument which specifies the port to which
	 * the media is to be transmitted. The command-line argument value will be
	 * used as the port to transmit the audio RTP to, the next port after it
	 * will be to transmit the audio RTCP to. Respectively, the subsequent ports
	 * will be used to transmit the video RTP and RTCP to."
	 */
	private static final String REMOTE_PORT_BASE_ARG_NAME = "--remote-port-base=";

	public static void main(String[] args) throws Exception {
		// We need two parameters to do the transmission. For example,
		// ant run-example -Drun.example.name=AVTransmit2
		// -Drun.example.arg.line="--remote-host=127.0.0.1 --remote-port-base=10000"
		// if (args.length < 2)
		// {
		// prUsage();
		// }
		// else
		// {
		// Map<String, String> argMap = parseCommandLineArgs(args);

		LibJitsi.start();
		try {
			// Create a audio transmit object with the specified params.
			AVSendOnly at = new AVSendOnly("5000", "192.168.0.101", "10000");
			// Start the transmission
			String result = at.start("both");

			// result will be non-null if there was an error. The return
			// value is a String describing the possible error. Print it.
			if (result == null) {
				System.err.println("Start transmission for 60 seconds...");

				// Transmit for 60 seconds and then close the processor
				// This is a safeguard when using a capture data source
				// so that the capture device will be properly released
				// before quitting.
				// The right thing to do would be to have a GUI with a
				// "Stop" button that would call stop on AVTransmit2
				try {
					Thread.sleep(600000);
				} catch (InterruptedException ie) {
				}

				// Stop the transmission
				at.stop();

				System.err.println("...transmission ended.");
			} else {
				System.err.println("Error : " + result);
			}
		} finally {
			LibJitsi.stop();
		}
		// }
	}

	/**
	 * Parses the arguments specified to the <tt>AVTransmit2</tt> application on
	 * the command line.
	 * 
	 * @param args
	 *            the arguments specified to the <tt>AVTransmit2</tt>
	 *            application on the command line
	 * @return a <tt>Map</tt> containing the arguments specified to the
	 *         <tt>AVTransmit2</tt> application on the command line in the form
	 *         of name-value associations
	 */
	static Map<String, String> parseCommandLineArgs(String[] args) {
		Map<String, String> argMap = new HashMap<String, String>();

		for (String arg : args) {
			int keyEndIndex = arg.indexOf('=');
			String key;
			String value;

			if (keyEndIndex == -1) {
				key = arg;
				value = null;
			} else {
				key = arg.substring(0, keyEndIndex + 1);
				value = arg.substring(keyEndIndex + 1);
			}
			argMap.put(key, value);
		}
		return argMap;
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
}
