/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.neomedia;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.media.CaptureDeviceInfo;
import javax.media.Codec;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NotConfiguredError;
import javax.media.Player;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;
import javax.media.UnsupportedPlugInException;
import javax.media.control.TrackControl;
import javax.media.format.RGBFormat;
import javax.media.protocol.DataSource;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.jitsi.impl.neomedia.codec.EncodingConfigurationConfigImpl;
import org.jitsi.impl.neomedia.codec.EncodingConfigurationImpl;
import org.jitsi.impl.neomedia.codec.FFmpeg;
import org.jitsi.impl.neomedia.codec.FMJPlugInConfiguration;
import org.jitsi.impl.neomedia.codec.video.AVFrameFormat;
import org.jitsi.impl.neomedia.codec.video.HFlip;
import org.jitsi.impl.neomedia.codec.video.SwScale;
import org.jitsi.impl.neomedia.device.AudioMediaDeviceImpl;
import org.jitsi.impl.neomedia.device.AudioMixerMediaDevice;
import org.jitsi.impl.neomedia.device.AudioSystem;
import org.jitsi.impl.neomedia.device.DeviceConfiguration;
import org.jitsi.impl.neomedia.device.DeviceSystem;
import org.jitsi.impl.neomedia.device.MediaDeviceImpl;
import org.jitsi.impl.neomedia.device.ScreenDeviceImpl;
import org.jitsi.impl.neomedia.device.VideoTranslatorMediaDevice;
import org.jitsi.impl.neomedia.format.MediaFormatFactoryImpl;
import org.jitsi.impl.neomedia.format.MediaFormatImpl;
import org.jitsi.impl.neomedia.format.VideoMediaFormatImpl;
import org.jitsi.impl.neomedia.transform.sdes.SDesControlImpl;
import org.jitsi.service.configuration.ConfigurationService;
import org.jitsi.service.libjitsi.LibJitsi;
import org.jitsi.service.neomedia.BasicVolumeControl;
import org.jitsi.service.neomedia.MediaService;
import org.jitsi.service.neomedia.MediaStream;
import org.jitsi.service.neomedia.MediaType;
import org.jitsi.service.neomedia.MediaUseCase;
import org.jitsi.service.neomedia.RTPTranslator;
import org.jitsi.service.neomedia.Recorder;
import org.jitsi.service.neomedia.SDesControl;
import org.jitsi.service.neomedia.SrtpControl;
import org.jitsi.service.neomedia.StreamConnector;
import org.jitsi.service.neomedia.VolumeControl;
import org.jitsi.service.neomedia.ZrtpControl;
import org.jitsi.service.neomedia.codec.EncodingConfiguration;
import org.jitsi.service.neomedia.device.MediaDevice;
import org.jitsi.service.neomedia.device.ScreenDevice;
import org.jitsi.service.neomedia.format.MediaFormat;
import org.jitsi.service.neomedia.format.MediaFormatFactory;
import org.jitsi.service.resources.ResourceManagementService;
import org.jitsi.util.Logger;
import org.jitsi.util.OSUtils;
import org.jitsi.util.event.PropertyChangeNotifier;
import org.jitsi.util.swing.VideoContainer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.sun.media.util.Registry;

/**
 * Implements <tt>MediaService</tt> for JMF.
 * 
 * @author Lyubomir Marinov
 * @author Dmitri Melnikov
 */
public class MediaServiceImpl extends PropertyChangeNotifier implements
		MediaService {
	/**
	 * The <tt>Logger</tt> used by the <tt>MediaServiceImpl</tt> class and its
	 * instances for logging output.
	 */
	private static final Logger logger = Logger
			.getLogger(MediaServiceImpl.class);

	/**
	 * The name of the <tt>boolean</tt> <tt>ConfigurationService</tt> property
	 * which indicates whether the detection of audio <tt>CaptureDevice</tt>s is
	 * to be disabled. The default value is <tt>false</tt> i.e. the audio
	 * <tt>CaptureDevice</tt>s are detected.
	 */
	public static final String DISABLE_AUDIO_SUPPORT_PNAME = "net.java.sip.communicator.service.media.DISABLE_AUDIO_SUPPORT";

	/**
	 * The name of the <tt>boolean</tt> <tt>ConfigurationService</tt> property
	 * which indicates whether the method
	 * {@link DeviceConfiguration#setAudioSystem(AudioSystem, boolean)} is to be
	 * considered disabled for the user i.e. the user is not presented with user
	 * interface which allows selecting a particular <tt>AudioSystem</tt>.
	 */
	public static final String DISABLE_SET_AUDIO_SYSTEM_PNAME = "net.java.sip.communicator.impl.neomedia.audiosystem.DISABLED";

	/**
	 * The name of the <tt>boolean</tt> <tt>ConfigurationService</tt> property
	 * which indicates whether the detection of video <tt>CaptureDevice</tt>s is
	 * to be disabled. The default value is <tt>false</tt> i.e. the video
	 * <tt>CaptureDevice</tt>s are detected.
	 */
	public static final String DISABLE_VIDEO_SUPPORT_PNAME = "net.java.sip.communicator.service.media.DISABLE_VIDEO_SUPPORT";

	/**
	 * The prefix of the property names the values of which specify the dynamic
	 * payload type preferences.
	 */
	private static final String DYNAMIC_PAYLOAD_TYPE_PREFERENCES_PNAME_PREFIX = "net.java.sip.communicator.impl.neomedia.dynamicPayloadTypePreferences";

	/**
	 * The value of the <tt>devices</tt> property of <tt>MediaServiceImpl</tt>
	 * when no <tt>MediaDevice</tt>s are available. Explicitly defined in order
	 * to reduce unnecessary allocations.
	 */
	private static final List<MediaDevice> EMPTY_DEVICES = Collections
			.emptyList();

	/**
	 * The name of the <tt>System</tt> boolean property which specifies whether
	 * the loading of the JMF/FMJ <tt>Registry</tt> is to be disabled.
	 */
	private static final String JMF_REGISTRY_DISABLE_LOAD = "net.sf.fmj.utility.JmfRegistry.disableLoad";

	/**
	 * The indicator which determines whether the loading of the JMF/FMJ
	 * <tt>Registry</tt> is disabled.
	 */
	private static boolean jmfRegistryDisableLoad;

	/**
	 * The indicator which determined whether
	 * {@link #postInitializeOnce(MediaServiceImpl)} has been executed in order
	 * to perform one-time initialization after initializing the first instance
	 * of <tt>MediaServiceImpl</tt>.
	 */
	private static boolean postInitializeOnce;

	/**
	 * The prefix that is used to store configuration for encodings preference.
	 */
	private static final String ENCODING_CONFIG_PROP_PREFIX = "net.java.sip.communicator.impl.neomedia.codec.EncodingConfiguration";

	/**
	 * The <tt>CaptureDevice</tt> user choices such as the default audio and
	 * video capture devices.
	 */
	private final DeviceConfiguration deviceConfiguration = new DeviceConfiguration();

	/**
	 * The <tt>PropertyChangeListener</tt> which listens to
	 * {@link #deviceConfiguration}.
	 */
	private final PropertyChangeListener deviceConfigurationPropertyChangeListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			deviceConfigurationPropertyChange(event);
		}
	};

	/**
	 * The list of audio <tt>MediaDevice</tt>s reported by this instance when
	 * its {@link MediaService#getDevices(MediaType, MediaUseCase)} method is
	 * called with an argument {@link MediaType#AUDIO}.
	 */
	private final List<MediaDeviceImpl> audioDevices = new ArrayList<MediaDeviceImpl>();

	/**
	 * The {@link EncodingConfiguration} instance that holds the current
	 * (global) list of formats and their preference.
	 */
	private final EncodingConfiguration currentEncodingConfiguration;

	/**
	 * The <tt>MediaFormatFactory</tt> through which <tt>MediaFormat</tt>
	 * instances may be created for the purposes of working with the
	 * <tt>MediaStream</tt>s created by this <tt>MediaService</tt>.
	 */
	private MediaFormatFactory formatFactory;

	/**
	 * The one and only <tt>MediaDevice</tt> instance with
	 * <tt>MediaDirection</tt> not allowing sending and <tt>MediaType</tt> equal
	 * to <tt>AUDIO</tt>.
	 */
	private MediaDevice nonSendAudioDevice;

	/**
	 * The one and only <tt>MediaDevice</tt> instance with
	 * <tt>MediaDirection</tt> not allowing sending and <tt>MediaType</tt> equal
	 * to <tt>VIDEO</tt>.
	 */
	private MediaDevice nonSendVideoDevice;

	/**
	 * The list of video <tt>MediaDevice</tt>s reported by this instance when
	 * its {@link MediaService#getDevices(MediaType, MediaUseCase)} method is
	 * called with an argument {@link MediaType#VIDEO}.
	 */
	private final List<MediaDeviceImpl> videoDevices = new ArrayList<MediaDeviceImpl>();

	/**
	 * A {@link Map} that binds indicates whatever preferences this media
	 * service implementation may have for the RTP payload type numbers that get
	 * dynamically assigned to {@link MediaFormat}s with no static payload type.
	 * The method is useful for formats such as "telephone-event" for example
	 * that is statically assigned the 101 payload type by some legacy systems.
	 * Signalling protocol implementations such as SIP and XMPP should make sure
	 * that, whenever this is possible, they assign to formats the dynamic
	 * payload type returned in this {@link Map}.
	 */
	private static Map<MediaFormat, Byte> dynamicPayloadTypePreferences;

	/**
	 * The volume control of the media service playback.
	 */
	private static VolumeControl outputVolumeControl;

	/**
	 * The volume control of the media service capture.
	 */
	private static VolumeControl inputVolumeControl;

	/**
	 * Listeners interested in Recorder events without the need to have access
	 * to their instances.
	 */
	private final List<Recorder.Listener> recorderListeners = new ArrayList<Recorder.Listener>();

	static {
		setupFMJ();
	}

	/**
	 * Initializes a new <tt>MediaServiceImpl</tt> instance.
	 */
	public MediaServiceImpl() {
		/*
		 * XXX The deviceConfiguration is initialized and referenced by this
		 * instance so adding deviceConfigurationPropertyChangeListener does not
		 * need a matching removal.
		 */
		deviceConfiguration
				.addPropertyChangeListener(deviceConfigurationPropertyChangeListener);

		currentEncodingConfiguration = new EncodingConfigurationConfigImpl(
				ENCODING_CONFIG_PROP_PREFIX);

		/*
		 * Perform one-time initialization after initializing the first instance
		 * of MediaServiceImpl.
		 */
		synchronized (MediaServiceImpl.class) {
			if (!postInitializeOnce) {
				postInitializeOnce = true;
				postInitializeOnce(this);
			}
		}
	}

	/**
	 * Create a <tt>MediaStream</tt> which will use a specific
	 * <tt>MediaDevice</tt> for capture and playback of media. The new instance
	 * will not have a <tt>StreamConnector</tt> at the time of its construction
	 * and a <tt>StreamConnector</tt> will be specified later on in order to
	 * enable the new instance to send and receive media.
	 * 
	 * @param device
	 *            the <tt>MediaDevice</tt> to be used by the new instance for
	 *            capture and playback of media
	 * @return a newly-created <tt>MediaStream</tt> which will use the specified
	 *         <tt>device</tt> for capture and playback of media
	 * @see MediaService#createMediaStream(MediaDevice)
	 */
	@Override
	public MediaStream createMediaStream(MediaDevice device) {
		return createMediaStream(null, device);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Implements {@link MediaService#createMediaStream(MediaType)}. Initializes
	 * a new <tt>AudioMediaStreamImpl</tt> or <tt>VideoMediaStreamImpl</tt> in
	 * accord with <tt>mediaType</tt>
	 */
	@Override
	public MediaStream createMediaStream(MediaType mediaType) {
		return createMediaStream(mediaType, null, null, null);
	}

	/**
	 * Creates a new <tt>MediaStream</tt> instance which will use the specified
	 * <tt>MediaDevice</tt> for both capture and playback of media exchanged via
	 * the specified <tt>StreamConnector</tt>.
	 * 
	 * @param connector
	 *            the <tt>StreamConnector</tt> that the new <tt>MediaStream</tt>
	 *            instance is to use for sending and receiving media
	 * @param device
	 *            the <tt>MediaDevice</tt> that the new <tt>MediaStream</tt>
	 *            instance is to use for both capture and playback of media
	 *            exchanged via the specified <tt>connector</tt>
	 * @return a new <tt>MediaStream</tt> instance
	 * @see MediaService#createMediaStream(StreamConnector, MediaDevice)
	 */
	@Override
	public MediaStream createMediaStream(StreamConnector connector,
			MediaDevice device) {
		return createMediaStream(connector, device, null);
	}

	/**
	 * Creates a new <tt>MediaStream</tt> instance which will use the specified
	 * <tt>MediaDevice</tt> for both capture and playback of media exchanged via
	 * the specified <tt>StreamConnector</tt>.
	 * 
	 * @param connector
	 *            the <tt>StreamConnector</tt> that the new <tt>MediaStream</tt>
	 *            instance is to use for sending and receiving media
	 * @param device
	 *            the <tt>MediaDevice</tt> that the new <tt>MediaStream</tt>
	 *            instance is to use for both capture and playback of media
	 *            exchanged via the specified <tt>connector</tt>
	 * @param srtpControl
	 *            a control which is already created, used to control the SRTP
	 *            operations.
	 * 
	 * @return a new <tt>MediaStream</tt> instance
	 * @see MediaService#createMediaStream(StreamConnector, MediaDevice)
	 */
	@Override
	public MediaStream createMediaStream(StreamConnector connector,
			MediaDevice device, SrtpControl srtpControl) {
		return createMediaStream(null, connector, device, srtpControl);
	}

	/**
	 * Initializes a new <tt>MediaStream</tt> instance. The method is the actual
	 * implementation to which the public <tt>createMediaStream</tt> methods of
	 * <tt>MediaServiceImpl</tt> delegate.
	 * 
	 * @param mediaType
	 *            the <tt>MediaType</tt> of the new <tt>MediaStream</tt>
	 *            instance to be initialized. If <tt>null</tt>, <tt>device</tt>
	 *            must be non-<tt>null</tt> and its
	 *            {@link MediaDevice#getMediaType()} will be used to determine
	 *            the <tt>MediaType</tt> of the new instance. If non-
	 *            <tt>null</tt>, <tt>device</tt> may be <tt>null</tt>. If non-
	 *            <tt>null</tt> and <tt>device</tt> is non-<tt>null</tt>, the
	 *            <tt>MediaType</tt> of <tt>device</tt> must be (equal to)
	 *            <tt>mediaType</tt>.
	 * @param connector
	 *            the <tt>StreamConnector</tt> to be used by the new instance if
	 *            non-<tt>null</tt>
	 * @param device
	 *            the <tt>MediaDevice</tt> to be used by the instance if non-
	 *            <tt>null</tt>
	 * @param srtpControl
	 *            the <tt>SrtpControl</tt> to be used by the new instance if
	 *            non-<tt>null</tt>
	 * @return a new <tt>MediaStream</tt> instance
	 */
	private MediaStream createMediaStream(MediaType mediaType,
			StreamConnector connector, MediaDevice device,
			SrtpControl srtpControl) {
		// Make sure that mediaType and device are in accord.
		if (mediaType == null) {
			if (device == null) {
				throw new NullPointerException("device");
			} else {
				mediaType = device.getMediaType();
			}
		} else if ((device != null) && !mediaType.equals(device.getMediaType())) {
			throw new IllegalArgumentException("device");
		}

		switch (mediaType) {
		case AUDIO:
			return new AudioMediaStreamImpl(connector, device, srtpControl);
		case VIDEO:
			return new VideoMediaStreamImpl(connector, device, srtpControl);
		default:
			return null;
		}
	}

	/**
	 * Creates a new <tt>MediaDevice</tt> which uses a specific
	 * <tt>MediaDevice</tt> to capture and play back media and performs mixing
	 * of the captured media and the media played back by any other users of the
	 * returned <tt>MediaDevice</tt>. For the <tt>AUDIO</tt> <tt>MediaType</tt>,
	 * the returned device is commonly referred to as an audio mixer. The
	 * <tt>MediaType</tt> of the returned <tt>MediaDevice</tt> is the same as
	 * the <tt>MediaType</tt> of the specified <tt>device</tt>.
	 * 
	 * @param device
	 *            the <tt>MediaDevice</tt> which is to be used by the returned
	 *            <tt>MediaDevice</tt> to actually capture and play back media
	 * @return a new <tt>MediaDevice</tt> instance which uses <tt>device</tt> to
	 *         capture and play back media and performs mixing of the captured
	 *         media and the media played back by any other users of the
	 *         returned <tt>MediaDevice</tt> instance
	 * @see MediaService#createMixer(MediaDevice)
	 */
	@Override
	public MediaDevice createMixer(MediaDevice device) {
		switch (device.getMediaType()) {
		case AUDIO:
			return new AudioMixerMediaDevice((AudioMediaDeviceImpl) device);
		case VIDEO:
			return new VideoTranslatorMediaDevice((MediaDeviceImpl) device);
		default:
			/*
			 * TODO If we do not support mixing, should we return null or rather
			 * a MediaDevice with INACTIVE MediaDirection?
			 */
			return null;
		}
	}

	/**
	 * Gets the default <tt>MediaDevice</tt> for the specified
	 * <tt>MediaType</tt>.
	 * 
	 * @param mediaType
	 *            a <tt>MediaType</tt> value indicating the type of media to be
	 *            handled by the <tt>MediaDevice</tt> to be obtained
	 * @param useCase
	 *            the <tt>MediaUseCase</tt> to obtain the <tt>MediaDevice</tt>
	 *            list for
	 * @return the default <tt>MediaDevice</tt> for the specified
	 *         <tt>mediaType</tt> if such a <tt>MediaDevice</tt> exists;
	 *         otherwise, <tt>null</tt>
	 * @see MediaService#getDefaultDevice(MediaType, MediaUseCase)
	 */
	@Override
	public MediaDevice getDefaultDevice(MediaType mediaType,
			MediaUseCase useCase) {
		CaptureDeviceInfo captureDeviceInfo;

		switch (mediaType) {
		case AUDIO:
			captureDeviceInfo = getDeviceConfiguration()
					.getAudioCaptureDevice();
			break;
		case VIDEO:
			captureDeviceInfo = getDeviceConfiguration().getVideoCaptureDevice(
					useCase);
			break;
		default:
			captureDeviceInfo = null;
			break;
		}

		MediaDevice defaultDevice = null;

		if (captureDeviceInfo != null) {
			for (MediaDevice device : getDevices(mediaType, useCase)) {
				if ((device instanceof MediaDeviceImpl)
						&& captureDeviceInfo.equals(((MediaDeviceImpl) device)
								.getCaptureDeviceInfo())) {
					defaultDevice = device;
					break;
				}
			}
		}
		if (defaultDevice == null) {
			switch (mediaType) {
			case AUDIO:
				defaultDevice = getNonSendAudioDevice();
				break;
			case VIDEO:
				defaultDevice = getNonSendVideoDevice();
				break;
			default:
				/*
				 * There is no MediaDevice with direction which does not allow
				 * sending and mediaType other than AUDIO and VIDEO.
				 */
				break;
			}
		}

		return defaultDevice;
	}

	/**
	 * Gets the <tt>CaptureDevice</tt> user choices such as the default audio
	 * and video capture devices.
	 * 
	 * @return the <tt>CaptureDevice</tt> user choices such as the default audio
	 *         and video capture devices.
	 */
	public DeviceConfiguration getDeviceConfiguration() {
		return deviceConfiguration;
	}

	/**
	 * Gets a list of the <tt>MediaDevice</tt>s known to this
	 * <tt>MediaService</tt> and handling the specified <tt>MediaType</tt>.
	 * 
	 * @param mediaType
	 *            the <tt>MediaType</tt> to obtain the <tt>MediaDevice</tt> list
	 *            for
	 * @param useCase
	 *            the <tt>MediaUseCase</tt> to obtain the <tt>MediaDevice</tt>
	 *            list for
	 * @return a new <tt>List</tt> of <tt>MediaDevice</tt>s known to this
	 *         <tt>MediaService</tt> and handling the specified
	 *         <tt>MediaType</tt>. The returned <tt>List</tt> is a copy of the
	 *         internal storage and, consequently, modifications to it do not
	 *         affect this instance. Despite the fact that a new <tt>List</tt>
	 *         instance is returned by each call to this method, the
	 *         <tt>MediaDevice</tt> instances are the same if they are still
	 *         known to this <tt>MediaService</tt> to be available.
	 * @see MediaService#getDevices(MediaType, MediaUseCase)
	 */
	@Override
	public List<MediaDevice> getDevices(MediaType mediaType,
			MediaUseCase useCase) {
		List<? extends CaptureDeviceInfo> cdis;
		List<MediaDeviceImpl> privateDevices;

		if (MediaType.VIDEO.equals(mediaType)) {
			/*
			 * In case a video capture device has been added to or removed from
			 * system (i.e. webcam, monitor, etc.), rescan the video capture
			 * devices.
			 */
			DeviceSystem.initializeDeviceSystems(MediaType.VIDEO);
		}

		switch (mediaType) {
		case AUDIO:
			cdis = getDeviceConfiguration().getAvailableAudioCaptureDevices();
			privateDevices = audioDevices;
			break;
		case VIDEO:
			cdis = getDeviceConfiguration().getAvailableVideoCaptureDevices(
					useCase);
			privateDevices = videoDevices;
			break;
		default:
			/*
			 * MediaService does not understand MediaTypes other than AUDIO and
			 * VIDEO.
			 */
			return EMPTY_DEVICES;
		}

		List<MediaDevice> publicDevices;

		synchronized (privateDevices) {
			if ((cdis == null) || (cdis.size() <= 0)) {
				privateDevices.clear();
			} else {
				Iterator<MediaDeviceImpl> deviceIter = privateDevices
						.iterator();

				while (deviceIter.hasNext()) {
					Iterator<? extends CaptureDeviceInfo> cdiIter = cdis
							.iterator();
					CaptureDeviceInfo captureDeviceInfo = deviceIter.next()
							.getCaptureDeviceInfo();
					boolean deviceIsFound = false;

					while (cdiIter.hasNext()) {
						if (captureDeviceInfo.equals(cdiIter.next())) {
							deviceIsFound = true;
							cdiIter.remove();
							break;
						}
					}
					if (!deviceIsFound) {
						deviceIter.remove();
					}
				}

				for (CaptureDeviceInfo cdi : cdis) {
					if (cdi == null) {
						continue;
					}

					MediaDeviceImpl device;

					switch (mediaType) {
					case AUDIO:
						device = new AudioMediaDeviceImpl(cdi);
						break;
					case VIDEO:
						device = new MediaDeviceImpl(cdi, mediaType);
						break;
					default:
						device = null;
						break;
					}
					if (device != null) {
						privateDevices.add(device);
					}
				}
			}

			publicDevices = new ArrayList<MediaDevice>(privateDevices);
		}

		/*
		 * If there are no MediaDevice instances of the specified mediaType,
		 * make sure that there is at least one MediaDevice which does not allow
		 * sending.
		 */
		if (publicDevices.isEmpty()) {
			MediaDevice nonSendDevice;

			switch (mediaType) {
			case AUDIO:
				nonSendDevice = getNonSendAudioDevice();
				break;
			case VIDEO:
				nonSendDevice = getNonSendVideoDevice();
				break;
			default:
				/*
				 * There is no MediaDevice with direction not allowing sending
				 * and mediaType other than AUDIO and VIDEO.
				 */
				nonSendDevice = null;
				break;
			}
			if (nonSendDevice != null) {
				publicDevices.add(nonSendDevice);
			}
		}

		return publicDevices;
	}

	/**
	 * Returns the current encoding configuration -- the instance that contains
	 * the global settings. Note that any changes made to this instance will
	 * have immediate effect on the configuration.
	 * 
	 * @return the current encoding configuration -- the instance that contains
	 *         the global settings.
	 */
	@Override
	public EncodingConfiguration getCurrentEncodingConfiguration() {
		return currentEncodingConfiguration;
	}

	/**
	 * Gets the <tt>MediaFormatFactory</tt> through which <tt>MediaFormat</tt>
	 * instances may be created for the purposes of working with the
	 * <tt>MediaStream</tt>s created by this <tt>MediaService</tt>.
	 * 
	 * @return the <tt>MediaFormatFactory</tt> through which
	 *         <tt>MediaFormat</tt> instances may be created for the purposes of
	 *         working with the <tt>MediaStream</tt>s created by this
	 *         <tt>MediaService</tt>
	 * @see MediaService#getFormatFactory()
	 */
	@Override
	public MediaFormatFactory getFormatFactory() {
		if (formatFactory == null) {
			formatFactory = new MediaFormatFactoryImpl();
		}
		return formatFactory;
	}

	/**
	 * Gets the one and only <tt>MediaDevice</tt> instance with
	 * <tt>MediaDirection</tt> not allowing sending and <tt>MediaType</tt> equal
	 * to <tt>AUDIO</tt>.
	 * 
	 * @return the one and only <tt>MediaDevice</tt> instance with
	 *         <tt>MediaDirection</tt> not allowing sending and
	 *         <tt>MediaType</tt> equal to <tt>AUDIO</tt>
	 */
	private MediaDevice getNonSendAudioDevice() {
		if (nonSendAudioDevice == null) {
			nonSendAudioDevice = new AudioMediaDeviceImpl();
		}
		return nonSendAudioDevice;
	}

	/**
	 * Gets the one and only <tt>MediaDevice</tt> instance with
	 * <tt>MediaDirection</tt> not allowing sending and <tt>MediaType</tt> equal
	 * to <tt>VIDEO</tt>.
	 * 
	 * @return the one and only <tt>MediaDevice</tt> instance with
	 *         <tt>MediaDirection</tt> not allowing sending and
	 *         <tt>MediaType</tt> equal to <tt>VIDEO</tt>
	 */
	private MediaDevice getNonSendVideoDevice() {
		if (nonSendVideoDevice == null) {
			nonSendVideoDevice = new MediaDeviceImpl(MediaType.VIDEO);
		}
		return nonSendVideoDevice;
	}

	/**
	 * Initializes a new <tt>ZrtpControl</tt> instance which is to control all
	 * ZRTP options.
	 * 
	 * @return a new <tt>ZrtpControl</tt> instance which is to control all ZRTP
	 *         options
	 */
	@Override
	public ZrtpControl createZrtpControl() {
		return new ZrtpControlImpl();
	}

	/**
	 * Initializes a new <tt>SDesControl</tt> instance which is to control all
	 * SDes options.
	 * 
	 * @return a new <tt>SDesControl</tt> instance which is to control all SDes
	 *         options
	 */
	@Override
	public SDesControl createSDesControl() {
		return new SDesControlImpl();
	}

	/**
	 * Gets the <tt>VolumeControl</tt> which controls the volume level of audio
	 * output/playback.
	 * 
	 * @return the <tt>VolumeControl</tt> which controls the volume level of
	 *         audio output/playback
	 * @see MediaService#getOutputVolumeControl()
	 */
	@Override
	public VolumeControl getOutputVolumeControl() {
		if (outputVolumeControl == null) {
			outputVolumeControl = new BasicVolumeControl(
					VolumeControl.PLAYBACK_VOLUME_LEVEL_PROPERTY_NAME);
		}
		return outputVolumeControl;
	}

	/**
	 * Gets the <tt>VolumeControl</tt> which controls the volume level of audio
	 * input/capture.
	 * 
	 * @return the <tt>VolumeControl</tt> which controls the volume level of
	 *         audio input/capture
	 * @see MediaService#getInputVolumeControl()
	 */
	@Override
	public VolumeControl getInputVolumeControl() {
		if (inputVolumeControl == null) {
			// If available, use hardware.
			try {
				inputVolumeControl = new HardwareVolumeControl(this,
						VolumeControl.CAPTURE_VOLUME_LEVEL_PROPERTY_NAME);
			} catch (Throwable t) {
				if (t instanceof ThreadDeath) {
					throw (ThreadDeath) t;
				} else if (t instanceof InterruptedException) {
					Thread.currentThread().interrupt();
				}
			}
			// Otherwise, use software.
			if (inputVolumeControl == null) {
				inputVolumeControl = new BasicVolumeControl(
						VolumeControl.CAPTURE_VOLUME_LEVEL_PROPERTY_NAME);
			}
		}
		return inputVolumeControl;
	}

	/**
	 * Get available screens.
	 * 
	 * @return screens
	 */
	@Override
	public List<ScreenDevice> getAvailableScreenDevices() {
		ScreenDevice[] screens = ScreenDeviceImpl.getAvailableScreenDevices();
		List<ScreenDevice> screenList;

		if ((screens != null) && (screens.length != 0)) {
			screenList = new ArrayList<ScreenDevice>(Arrays.asList(screens));
		} else {
			screenList = Collections.emptyList();
		}
		return screenList;
	}

	/**
	 * Get default screen device.
	 * 
	 * @return default screen device
	 */
	@Override
	public ScreenDevice getDefaultScreenDevice() {
		return ScreenDeviceImpl.getDefaultScreenDevice();
	}

	/**
	 * Creates a new <tt>Recorder</tt> instance that can be used to record a
	 * call which captures and plays back media using a specific
	 * <tt>MediaDevice</tt>.
	 * 
	 * @param device
	 *            the <tt>MediaDevice</tt> which is used for media capture and
	 *            playback by the call to be recorded
	 * @return a new <tt>Recorder</tt> instance that can be used to record a
	 *         call which captures and plays back media using the specified
	 *         <tt>MediaDevice</tt>
	 * @see MediaService#createRecorder(MediaDevice)
	 */
	@Override
	public Recorder createRecorder(MediaDevice device) {
		if (device instanceof AudioMixerMediaDevice) {
			return new RecorderImpl((AudioMixerMediaDevice) device);
		} else {
			return null;
		}
	}

	/**
	 * Returns a {@link Map} that binds indicates whatever preferences this
	 * media service implementation may have for the RTP payload type numbers
	 * that get dynamically assigned to {@link MediaFormat}s with no static
	 * payload type. The method is useful for formats such as "telephone-event"
	 * for example that is statically assigned the 101 payload type by some
	 * legacy systems. Signaling protocol implementations such as SIP and XMPP
	 * should make sure that, whenever this is possible, they assign to formats
	 * the dynamic payload type returned in this {@link Map}.
	 * 
	 * @return a {@link Map} binding some formats to a preferred dynamic RTP
	 *         payload type number.
	 */
	@Override
	public Map<MediaFormat, Byte> getDynamicPayloadTypePreferences() {
		if (dynamicPayloadTypePreferences == null) {
			dynamicPayloadTypePreferences = new HashMap<MediaFormat, Byte>();

			/*
			 * Set the dynamicPayloadTypePreferences to their default values. If
			 * the user chooses to override them through the
			 * ConfigurationService, they will be overwritten later on.
			 */
			MediaFormat telephoneEvent = MediaUtils.getMediaFormat(
					"telephone-event", 8000);
			if (telephoneEvent != null) {
				dynamicPayloadTypePreferences.put(telephoneEvent, (byte) 101);
			}

			MediaFormat h264 = MediaUtils.getMediaFormat("H264",
					VideoMediaFormatImpl.DEFAULT_CLOCK_RATE);
			if (h264 != null) {
				dynamicPayloadTypePreferences.put(h264, (byte) 99);
			}

			/*
			 * Try to load dynamicPayloadTypePreferences from the
			 * ConfigurationService.
			 */
			ConfigurationService cfg = LibJitsi.getConfigurationService();

			if (cfg != null) {
				String prefix = DYNAMIC_PAYLOAD_TYPE_PREFERENCES_PNAME_PREFIX;
				List<String> propertyNames = cfg.getPropertyNamesByPrefix(
						prefix, true);

				for (String propertyName : propertyNames) {
					/*
					 * The dynamic payload type is the name of the property name
					 * and the format which prefers it is the property value.
					 */
					byte dynamicPayloadTypePreference = 0;
					Throwable exception = null;

					try {
						dynamicPayloadTypePreference = Byte
								.parseByte(propertyName.substring(prefix
										.length() + 1));
					} catch (IndexOutOfBoundsException ioobe) {
						exception = ioobe;
					} catch (NumberFormatException nfe) {
						exception = nfe;
					}
					if (exception != null) {
						logger.warn(
								"Ignoring dynamic payload type preference"
										+ " which could not be parsed: "
										+ propertyName, exception);
						continue;
					}

					String source = cfg.getString(propertyName);

					if ((source != null) && (source.length() != 0)) {
						try {
							JSONObject json = (JSONObject) JSONValue
									.parseWithException(source);
							String encoding = (String) json
									.get(MediaFormatImpl.ENCODING_PNAME);
							long clockRate = (Long) json
									.get(MediaFormatImpl.CLOCK_RATE_PNAME);
							Map<String, String> fmtps = new HashMap<String, String>();

							if (json.containsKey(MediaFormatImpl.FORMAT_PARAMETERS_PNAME)) {
								JSONObject jsonFmtps = (JSONObject) json
										.get(MediaFormatImpl.FORMAT_PARAMETERS_PNAME);
								Iterator<?> jsonFmtpsIter = jsonFmtps.keySet()
										.iterator();

								while (jsonFmtpsIter.hasNext()) {
									String key = jsonFmtpsIter.next()
											.toString();
									String value = (String) jsonFmtps.get(key);

									fmtps.put(key, value);
								}
							}

							MediaFormat mediaFormat = MediaUtils
									.getMediaFormat(encoding, clockRate, fmtps);

							if (mediaFormat != null) {
								dynamicPayloadTypePreferences.put(mediaFormat,
										dynamicPayloadTypePreference);
							}
						} catch (Throwable jsone) {
							logger.warn(
									"Ignoring dynamic payload type preference"
											+ " which could not be parsed: "
											+ source, jsone);
						}
					}
				}
			}
		}
		return dynamicPayloadTypePreferences;
	}

	/**
	 * Creates a preview component for the specified device(video device) used
	 * to show video preview from that device.
	 * 
	 * @param device
	 *            the video device
	 * @param preferredWidth
	 *            the width we prefer for the component
	 * @param preferredHeight
	 *            the height we prefer for the component
	 * @return the preview component.
	 */
	@Override
	public Object getVideoPreviewComponent(MediaDevice device,
			int preferredWidth, int preferredHeight) {
		ResourceManagementService resources = LibJitsi
				.getResourceManagementService();
		String noPreviewText = (resources == null) ? "" : resources
				.getI18NString("impl.media.configform.NO_PREVIEW");
		JLabel noPreview = new JLabel(noPreviewText);

		noPreview.setHorizontalAlignment(SwingConstants.CENTER);
		noPreview.setVerticalAlignment(SwingConstants.CENTER);

		final JComponent videoContainer = new VideoContainer(noPreview, false);

		if ((preferredWidth > 0) && (preferredHeight > 0)) {
			videoContainer.setPreferredSize(new Dimension(preferredWidth,
					preferredHeight));
		}

		try {
			CaptureDeviceInfo captureDeviceInfo;

			if ((device != null)
					&& ((captureDeviceInfo = ((MediaDeviceImpl) device)
							.getCaptureDeviceInfo()) != null)) {
				DataSource dataSource = Manager
						.createDataSource(captureDeviceInfo.getLocator());

				/*
				 * Don't let the size be uselessly small just because the
				 * videoContainer has too small a preferred size.
				 */
				if ((preferredWidth < 128) || (preferredHeight < 96)) {
					preferredWidth = 128;
					preferredHeight = 96;
				}
				VideoMediaStreamImpl.selectVideoSize(dataSource,
						preferredWidth, preferredHeight);

				// A Player is documented to be created on a connected
				// DataSource.
				dataSource.connect();

				Processor player = Manager.createProcessor(dataSource);
				final VideoContainerHierarchyListener listener = new VideoContainerHierarchyListener(
						videoContainer, player);
				videoContainer.addHierarchyListener(listener);
				final MediaLocator locator = dataSource.getLocator();

				player.addControllerListener(new ControllerListener() {
					@Override
					public void controllerUpdate(ControllerEvent event) {
						controllerUpdateForPreview(event, videoContainer,
								locator, listener);
					}
				});
				player.configure();
			}
		} catch (Throwable t) {
			if (t instanceof ThreadDeath) {
				throw (ThreadDeath) t;
			} else {
				logger.error("Failed to create video preview", t);
			}
		}

		return videoContainer;
	}

	/**
	 * Listens and shows the video in the video container when needed.
	 * 
	 * @param event
	 *            the event when player has ready visual component.
	 * @param videoContainer
	 *            the container.
	 * @param locator
	 *            input DataSource locator
	 * @param listener
	 *            the hierarchy listener we created for the video container.
	 */
	private static void controllerUpdateForPreview(ControllerEvent event,
			JComponent videoContainer, MediaLocator locator,
			VideoContainerHierarchyListener listener) {
		if (event instanceof ConfigureCompleteEvent) {
			Processor player = (Processor) event.getSourceController();

			/*
			 * Use SwScale for the scaling since it produces an image with
			 * better quality and add the "flip" effect to the video.
			 */
			TrackControl[] trackControls = player.getTrackControls();

			if ((trackControls != null) && (trackControls.length != 0)) {
				try {
					for (TrackControl trackControl : trackControls) {
						Codec codecs[] = null;
						SwScale scaler = new SwScale();

						// do not flip desktop
						if (DeviceSystem.LOCATOR_PROTOCOL_IMGSTREAMING
								.equals(locator.getProtocol())) {
							codecs = new Codec[] { scaler };
						} else {
							codecs = new Codec[] { new HFlip(), scaler };
						}

						trackControl.setCodecChain(codecs);
						break;
					}
				} catch (UnsupportedPlugInException upiex) {
					logger.warn("Failed to add SwScale/VideoFlipEffect to "
							+ "codec chain", upiex);
				}
			}

			// Turn the Processor into a Player.
			try {
				player.setContentDescriptor(null);
			} catch (NotConfiguredError nce) {
				logger.error("Failed to set ContentDescriptor of Processor",
						nce);
			}

			player.realize();
		} else if (event instanceof RealizeCompleteEvent) {
			Player player = (Player) event.getSourceController();
			Component video = player.getVisualComponent();

			// sets the preview to the listener
			listener.setPreview(video);
			showPreview(videoContainer, video, player);
		}
	}

	/**
	 * Shows the preview panel.
	 * 
	 * @param previewContainer
	 *            the container
	 * @param preview
	 *            the preview component.
	 * @param player
	 *            the player.
	 */
	private static void showPreview(final JComponent previewContainer,
			final Component preview, final Player player) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					showPreview(previewContainer, preview, player);
				}
			});
			return;
		}

		previewContainer.removeAll();

		if (preview != null) {
			previewContainer.add(preview);
			player.start();

			if (previewContainer.isDisplayable()) {
				previewContainer.revalidate();
				previewContainer.repaint();
			} else {
				previewContainer.doLayout();
			}
		} else {
			disposePlayer(player);
		}
	}

	/**
	 * Dispose the player used for the preview.
	 * 
	 * @param player
	 *            the player.
	 */
	private static void disposePlayer(final Player player) {
		// launch disposing preview player in separate thread
		// will lock renderer and can produce lock if user has quickly
		// requested preview component and can lock ui thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				player.stop();
				player.deallocate();
				player.close();
			}
		}).start();
	}

	/**
	 * Get a <tt>MediaDevice</tt> for a part of desktop streaming/sharing.
	 * 
	 * @param width
	 *            width of the part
	 * @param height
	 *            height of the part
	 * @param x
	 *            origin of the x coordinate (relative to the full desktop)
	 * @param y
	 *            origin of the y coordinate (relative to the full desktop)
	 * @return <tt>MediaDevice</tt> representing the part of desktop or null if
	 *         problem
	 */
	@Override
	public MediaDevice getMediaDeviceForPartialDesktopStreaming(int width,
			int height, int x, int y) {
		MediaDevice device = null;
		String name = "Partial desktop streaming";
		Dimension size = null;
		int multiple = 0;
		Point p = new Point((x < 0) ? 0 : x, (y < 0) ? 0 : y);
		ScreenDevice dev = getScreenForPoint(p);
		int display = -1;

		if (dev != null) {
			display = dev.getIndex();
		} else {
			return null;
		}

		/* on Mac OS X, width have to be a multiple of 16 */
		if (OSUtils.IS_MAC) {
			multiple = Math.round(width / 16f);
			width = multiple * 16;
		} else {
			/* JMF filter graph seems to not like odd width */
			multiple = Math.round(width / 2f);
			width = multiple * 2;
		}

		/* JMF filter graph seems to not like odd height */
		multiple = Math.round(height / 2f);
		height = multiple * 2;

		size = new Dimension(width, height);

		Format formats[] = new Format[] {
				new AVFrameFormat(size, Format.NOT_SPECIFIED,
						FFmpeg.PIX_FMT_ARGB, Format.NOT_SPECIFIED),
				new RGBFormat(size, // size
						Format.NOT_SPECIFIED, // maxDataLength
						Format.byteArray, // dataType
						Format.NOT_SPECIFIED, // frameRate
						32, // bitsPerPixel
						2 /* red */, 3 /* green */, 4 /* blue */) };

		Rectangle bounds = ((ScreenDeviceImpl) dev).getBounds();
		x -= bounds.x;
		y -= bounds.y;

		CaptureDeviceInfo devInfo = new CaptureDeviceInfo(name + " " + display,
				new MediaLocator(DeviceSystem.LOCATOR_PROTOCOL_IMGSTREAMING
						+ ":" + display + "," + x + "," + y), formats);

		device = new MediaDeviceImpl(devInfo, MediaType.VIDEO);
		return device;
	}

	/**
	 * If the <tt>MediaDevice</tt> corresponds to partial desktop streaming
	 * device.
	 * 
	 * @param mediaDevice
	 *            <tt>MediaDevice</tt>
	 * @return true if <tt>MediaDevice</tt> is a partial desktop streaming
	 *         device, false otherwise
	 */
	@Override
	public boolean isPartialStreaming(MediaDevice mediaDevice) {
		if (mediaDevice == null) {
			return false;
		}

		MediaDeviceImpl dev = (MediaDeviceImpl) mediaDevice;
		CaptureDeviceInfo cdi = dev.getCaptureDeviceInfo();

		return (cdi != null)
				&& cdi.getName().startsWith("Partial desktop streaming");
	}

	/**
	 * Find the screen device that contains specified point.
	 * 
	 * @param p
	 *            point coordinates
	 * @return screen device that contains point
	 */
	public ScreenDevice getScreenForPoint(Point p) {
		for (ScreenDevice dev : getAvailableScreenDevices()) {
			if (dev.containsPoint(p)) {
				return dev;
			}
		}
		return null;
	}

	/**
	 * Gets the origin of a specific desktop streaming device.
	 * 
	 * @param mediaDevice
	 *            the desktop streaming device to get the origin on
	 * @return the origin of the specified desktop streaming device
	 */
	@Override
	public Point getOriginForDesktopStreamingDevice(MediaDevice mediaDevice) {
		MediaDeviceImpl dev = (MediaDeviceImpl) mediaDevice;
		CaptureDeviceInfo cdi = dev.getCaptureDeviceInfo();

		if (cdi == null) {
			return null;
		}

		MediaLocator locator = cdi.getLocator();

		if (!DeviceSystem.LOCATOR_PROTOCOL_IMGSTREAMING.equals(locator
				.getProtocol())) {
			return null;
		}

		String remainder = locator.getRemainder();
		String split[] = remainder.split(",");
		int index = Integer
				.parseInt(((split != null) && (split.length > 1)) ? split[0]
						: remainder);

		List<ScreenDevice> devs = getAvailableScreenDevices();

		if (devs.size() - 1 >= index) {
			Rectangle r = ((ScreenDeviceImpl) devs.get(index)).getBounds();

			return new Point(r.x, r.y);
		}

		return null;
	}

	/**
	 * Those interested in Recorder events add listener through MediaService.
	 * This way they don't need to have access to the Recorder instance. Adds a
	 * new <tt>Recorder.Listener</tt> to the list of listeners interested in
	 * notifications from a <tt>Recorder</tt>.
	 * 
	 * @param listener
	 *            the new <tt>Recorder.Listener</tt> to be added to the list of
	 *            listeners interested in notifications from <tt>Recorder</tt>s.
	 */
	@Override
	public void addRecorderListener(Recorder.Listener listener) {
		synchronized (recorderListeners) {
			if (!recorderListeners.contains(listener)) {
				recorderListeners.add(listener);
			}
		}
	}

	/**
	 * Removes an existing <tt>Recorder.Listener</tt> from the list of listeners
	 * interested in notifications from <tt>Recorder</tt>s.
	 * 
	 * @param listener
	 *            the existing <tt>Listener</tt> to be removed from the list of
	 *            listeners interested in notifications from <tt>Recorder</tt>s
	 */
	@Override
	public void removeRecorderListener(Recorder.Listener listener) {
		synchronized (recorderListeners) {
			recorderListeners.remove(listener);
		}
	}

	/**
	 * Gives access to currently registered <tt>Recorder.Listener</tt>s.
	 * 
	 * @return currently registered <tt>Recorder.Listener</tt>s.
	 */
	@Override
	public Iterator<Recorder.Listener> getRecorderListeners() {
		return recorderListeners.iterator();
	}

	/**
	 * Notifies this instance that the value of a property of
	 * {@link #deviceConfiguration} has changed.
	 * 
	 * @param event
	 *            a <tt>PropertyChangeEvent</tt> which specifies the name of the
	 *            property which had its value changed and the old and the new
	 *            values of that property
	 */
	private void deviceConfigurationPropertyChange(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();

		/*
		 * While AUDIO_CAPTURE_DEVICE is sure to affect the DEFAULT_DEVICE,
		 * AUDIO_PLAYBACK_DEVICE is not. Anyway, MediaDevice is supposed to
		 * represent the device to be used for capture AND playback (though its
		 * current implementation MediaDeviceImpl may be incomplete with respect
		 * to the playback representation). Since it is not clear at this point
		 * of the execution whether AUDIO_PLAYBACK_DEVICE really affects the
		 * DEFAULT_DEVICE and for the sake of completeness, throw in the changes
		 * to the AUDIO_NOTIFY_DEVICE as well.
		 */
		if (DeviceConfiguration.AUDIO_CAPTURE_DEVICE.equals(propertyName)
				|| DeviceConfiguration.AUDIO_NOTIFY_DEVICE.equals(propertyName)
				|| DeviceConfiguration.AUDIO_PLAYBACK_DEVICE
						.equals(propertyName)
				|| DeviceConfiguration.VIDEO_CAPTURE_DEVICE
						.equals(propertyName)) {
			/*
			 * We do not know the old value of the property at the time of this
			 * writing. We cannot report the new value either because we do not
			 * know the MediaType and the MediaUseCase.
			 */
			firePropertyChange(DEFAULT_DEVICE, null, null);
		}
	}

	/**
	 * Initializes a new <tt>RTPTranslator</tt> which is to forward RTP and RTCP
	 * traffic between multiple <tt>MediaStream</tt>s.
	 * 
	 * @return a new <tt>RTPTranslator</tt> which is to forward RTP and RTCP
	 *         traffic between multiple <tt>MediaStream</tt>s
	 * @see MediaService#createRTPTranslator()
	 */
	@Override
	public RTPTranslator createRTPTranslator() {
		return new RTPTranslatorImpl();
	}

	/**
	 * Gets the indicator which determines whether the loading of the JMF/FMJ
	 * <tt>Registry</tt> has been disabled.
	 * 
	 * @return <tt>true</tt> if the loading of the JMF/FMJ <tt>Registry</tt> has
	 *         been disabled; otherwise, <tt>false</tt>
	 */
	public static boolean isJmfRegistryDisableLoad() {
		return jmfRegistryDisableLoad;
	}

	/**
	 * Performs one-time initialization after initializing the first instance of
	 * <tt>MediaServiceImpl</tt>.
	 * 
	 * @param mediaServiceImpl
	 *            the <tt>MediaServiceImpl</tt> instance which has caused the
	 *            need to perform the one-time initialization
	 */
	private static void postInitializeOnce(MediaServiceImpl mediaServiceImpl) {
		new ZrtpFortunaEntropyGatherer(
				mediaServiceImpl.getDeviceConfiguration()).setEntropy();
	}

	/**
	 * Sets up FMJ for execution. For example, sets properties which instruct
	 * FMJ whether it is to create a log, where the log is to be created.
	 */
	private static void setupFMJ() {
		/*
		 * FMJ now uses java.util.logging.Logger, but only logs if
		 * "allowLogging" is set in it's registry. Since the levels can be
		 * configured through properties for the net.sf.fmj.media.Log class, we
		 * always enable this (as opposed to only enabling it when
		 * <tt>this.logger</tt> has debug enabled).
		 */
		Registry.set("allowLogging", true);

		/*
		 * Disable the loading of .fmj.registry because Kertesz Laszlo has
		 * reported that audio input devices duplicate after restarting Jitsi.
		 * Besides, Jitsi does not really need .fmj.registry on startup.
		 */
		if (System.getProperty(JMF_REGISTRY_DISABLE_LOAD) == null) {
			System.setProperty(JMF_REGISTRY_DISABLE_LOAD, "true");
		}
		jmfRegistryDisableLoad = "true".equalsIgnoreCase(System
				.getProperty(JMF_REGISTRY_DISABLE_LOAD));

		String scHomeDirLocation = System
				.getProperty(ConfigurationService.PNAME_SC_HOME_DIR_LOCATION);

		if (scHomeDirLocation != null) {
			String scHomeDirName = System
					.getProperty(ConfigurationService.PNAME_SC_HOME_DIR_NAME);

			if (scHomeDirName != null) {
				File scHomeDir = new File(scHomeDirLocation, scHomeDirName);

				/* Write FMJ's log in Jitsi's log directory. */
				Registry.set("secure.logDir",
						new File(scHomeDir, "log").getPath());

				/* Write FMJ's registry in Jitsi's user data directory. */
				String jmfRegistryFilename = "net.sf.fmj.utility.JmfRegistry.filename";

				if (System.getProperty(jmfRegistryFilename) == null) {
					System.setProperty(jmfRegistryFilename, new File(scHomeDir,
							".fmj.registry").getAbsolutePath());
				}
			}
		}

		ConfigurationService cfg = LibJitsi.getConfigurationService();
		for (String prop : cfg.getPropertyNamesByPrefix("net.java.sip."
				+ "communicator.impl.neomedia.adaptive_jitter_buffer", true)) {
			String suffix = prop.substring(prop.lastIndexOf(".") + 1,
					prop.length());
			Registry.set("adaptive_jitter_buffer_" + suffix,
					cfg.getString(prop));
		}

		FMJPlugInConfiguration.registerCustomPackages();
		FMJPlugInConfiguration.registerCustomCodecs();
	}

	/**
	 * Returns a new {@link EncodingConfiguration} instance that can be used by
	 * other bundles.
	 * 
	 * @return a new {@link EncodingConfiguration} instance.
	 */
	@Override
	public EncodingConfiguration createEmptyEncodingConfiguration() {
		return new EncodingConfigurationImpl();
	}

	/**
	 * The listener which will be notified for changes in the video container.
	 * Whether the container is displayable or not we will stop the player or
	 * start it.
	 */
	private class VideoContainerHierarchyListener implements HierarchyListener {
		/**
		 * The parent window.
		 */
		private Window window;

		/**
		 * The listener for the parent window. Used to dispose player on close.
		 */
		private WindowListener windowListener;

		/**
		 * The parent container of our preview.
		 */
		private JComponent container;

		/**
		 * The player showing the video preview.
		 */
		private Player player;

		/**
		 * The preview component of the player, must be set once the player has
		 * been realized.
		 */
		private Component preview = null;

		/**
		 * Creates VideoContainerHierarchyListener.
		 * 
		 * @param container
		 *            the video container.
		 * @param player
		 *            the player.
		 */
		VideoContainerHierarchyListener(JComponent container, Player player) {
			this.container = container;
			this.player = player;
		}

		/**
		 * After the player has been realized the preview can be obtained and
		 * supplied to this listener. Normally done on player
		 * RealizeCompleteEvent.
		 * 
		 * @param preview
		 *            the preview.
		 */
		void setPreview(Component preview) {
			this.preview = preview;
		}

		/**
		 * Disposes player and cleans listeners as we will no longer need them.
		 */
		public void dispose() {
			if (windowListener != null) {
				if (window != null) {
					window.removeWindowListener(windowListener);
					window = null;
				}
				windowListener = null;
			}
			container.removeHierarchyListener(this);

			disposePlayer(player);

			/*
			 * We've just disposed the player which created the preview
			 * component so the preview component is of no use regardless of
			 * whether the Media configuration form will be redisplayed or not.
			 * And since the preview component appears to be a huge object even
			 * after its player is disposed, make sure to not reference it.
			 */
			if (preview != null) {
				container.remove(preview);
			}
		}

		/**
		 * Change in container.
		 * 
		 * @param event
		 *            the event for the chnage.
		 */
		@Override
		public void hierarchyChanged(HierarchyEvent event) {
			if ((event.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) == 0) {
				return;
			}

			if (!container.isDisplayable()) {
				dispose();
				return;
			} else {
				// if this is just a change in the video container
				// and preview has not been created yet, do nothing
				// otherwise start the player which will show in preview
				if (preview != null) {
					player.start();
				}
			}

			if (windowListener == null) {
				window = SwingUtilities.windowForComponent(container);
				if (window != null) {
					windowListener = new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent event) {
							dispose();
						}
					};
					window.addWindowListener(windowListener);
				}
			}
		}
	}
}
