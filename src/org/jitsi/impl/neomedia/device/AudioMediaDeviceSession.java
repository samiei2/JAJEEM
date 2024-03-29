/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.neomedia.device;

import javax.media.Codec;
import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerEvent;
import javax.media.Player;
import javax.media.Processor;
import javax.media.Renderer;
import javax.media.UnsupportedPlugInException;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;

import org.jitsi.impl.neomedia.audiolevel.AudioLevelEffect;
import org.jitsi.impl.neomedia.jmfext.media.renderer.audio.AbstractAudioRenderer;
import org.jitsi.service.neomedia.VolumeControl;
import org.jitsi.service.neomedia.event.SimpleAudioLevelListener;
import org.jitsi.util.Logger;

/**
 * Extends <tt>MediaDeviceSession</tt> to add audio-specific functionality.
 * 
 * @author Emil Ivov
 * @author Damian Minkov
 * @author Lyubomir Marinov
 */
public class AudioMediaDeviceSession extends MediaDeviceSession {
	/**
	 * The <tt>Logger</tt> used by the <tt>AudioMediaDeviceSession</tt> class
	 * and its instances for logging output.
	 */
	private static final Logger logger = Logger
			.getLogger(AudioMediaDeviceSession.class);

	/**
	 * The <tt>Effect</tt> that we will register with our <tt>DataSource</tt> in
	 * order to measure the audio levels of the local user.
	 */
	private final AudioLevelEffect localUserAudioLevelEffect = new AudioLevelEffect();

	/**
	 * The <tt>VolumeControl</tt> which is to control the volume (level) of the
	 * audio (to be) played back by this instance.
	 */
	private VolumeControl outputVolumeControl;

	/**
	 * The effect that we will register with our stream in order to measure
	 * audio levels of the remote user audio.
	 */
	private final AudioLevelEffect streamAudioLevelEffect = new AudioLevelEffect();

	/**
	 * Initializes a new <tt>MediaDeviceSession</tt> instance which is to
	 * represent the use of a specific <tt>MediaDevice</tt> by a
	 * <tt>MediaStream</tt>.
	 * 
	 * @param device
	 *            the <tt>MediaDevice</tt> the use of which by a
	 *            <tt>MediaStream</tt> is to be represented by the new instance
	 */
	protected AudioMediaDeviceSession(AbstractMediaDevice device) {
		super(device);
	}

	/**
	 * Copies the playback part of a specific <tt>MediaDeviceSession</tt> into
	 * this instance.
	 * 
	 * @param deviceSession
	 *            the <tt>MediaDeviceSession</tt> to copy the playback part of
	 *            into this instance
	 */
	@Override
	public void copyPlayback(MediaDeviceSession deviceSession) {
		AudioMediaDeviceSession amds = (AudioMediaDeviceSession) deviceSession;

		setStreamAudioLevelListener(amds.streamAudioLevelEffect
				.getAudioLevelListener());
		setLocalUserAudioLevelListener(amds.localUserAudioLevelEffect
				.getAudioLevelListener());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Overrides the super implementation in order to configure the
	 * <tt>VolumeControl</tt> of the returned <tt>Renderer</tt> for the purposes
	 * of having call/telephony conference-specific volume (levels).
	 */
	@Override
	protected Renderer createRenderer(Player player, TrackControl trackControl) {
		Renderer renderer = super.createRenderer(player, trackControl);

		if (renderer != null) {
			setVolumeControl(renderer, outputVolumeControl);
		}

		return renderer;
	}

	/**
	 * Returns the last audio level that was measured by this device session for
	 * the specified <tt>ssrc</tt>.
	 * 
	 * @param ssrc
	 *            the SSRC ID whose last measured audio level we'd like to
	 *            retrieve.
	 * 
	 * @return the audio level that was last measured for the specified
	 *         <tt>ssrc</tt> or <tt>-1</tt> if no level has been cached for that
	 *         ID.
	 */
	public int getLastMeasuredAudioLevel(long ssrc) {
		return -1;
	}

	/**
	 * Returns the last audio level that was measured by the underlying mixer
	 * for local user.
	 * 
	 * @return the audio level that was last measured for the local user.
	 */
	public int getLastMeasuredLocalUserAudioLevel() {
		return -1;
	}

	/**
	 * Called by
	 * {@link MediaDeviceSession#playerControllerUpdate(ControllerEvent event)}
	 * when the player associated with this session's <tt>ReceiveStream</tt>
	 * moves enters the <tt>Configured</tt> state, so we use the occasion to add
	 * our audio level effect.
	 * 
	 * @param player
	 *            the <tt>Player</tt> which is the source of a
	 *            <tt>ConfigureCompleteEvent</tt>
	 * @see MediaDeviceSession#playerConfigureComplete(Processor)
	 */
	@Override
	protected void playerConfigureComplete(Processor player) {
		super.playerConfigureComplete(player);

		TrackControl tcs[] = player.getTrackControls();

		if (tcs != null) {
			for (TrackControl tc : tcs) {
				if (tc.getFormat() instanceof AudioFormat) {
					// Assume there is only one audio track.
					try {
						registerStreamAudioLevelJMFEffect(tc);
					} catch (UnsupportedPlugInException upie) {
						logger.error(
								"Failed to register stream audio level Effect",
								upie);
					}
					break;
				}
			}
		}
	}

	/**
	 * Gets notified about <tt>ControllerEvent</tt>s generated by the processor
	 * reading our capture data source, calls the corresponding method from the
	 * parent class so that it would initialize the processor and then adds the
	 * level effect for the local user audio levels.
	 * 
	 * @param event
	 *            the <tt>ControllerEvent</tt> specifying the
	 *            <tt>Controller</tt> which is the source of the event and the
	 *            very type of the event
	 */
	@Override
	protected void processorControllerUpdate(ControllerEvent event) {
		super.processorControllerUpdate(event);

		if (event instanceof ConfigureCompleteEvent) {
			Processor processor = (Processor) event.getSourceController();

			if (processor != null) {
				registerLocalUserAudioLevelEffect(processor);
			}
		}
	}

	/**
	 * Creates an audio level effect and add its to the codec chain of the
	 * <tt>TrackControl</tt> assuming that it only contains a single track.
	 * 
	 * @param processor
	 *            the processor on which track control we need to register a
	 *            level effect with.
	 */
	protected void registerLocalUserAudioLevelEffect(Processor processor) {
		// we register the effect regardless of whether or not we have any
		// listeners at this point because we won't get a second chance.
		// however the effect would do next to nothing unless we register a
		// first listener with it.
		//
		// XXX: i am assuming that a single effect could be reused multiple
		// times
		// if that turns out not to be the case we need to create a new instance
		// here.

		// here we add sound level indicator for captured media
		// from the microphone if there are interested listeners
		try {
			TrackControl tcs[] = processor.getTrackControls();

			if (tcs != null) {
				for (TrackControl tc : tcs) {
					if (tc.getFormat() instanceof AudioFormat) {
						// we assume a single track
						tc.setCodecChain(new Codec[] { localUserAudioLevelEffect });
						break;
					}
				}
			}
		} catch (UnsupportedPlugInException ex) {
			logger.error("Effects are not supported by the datasource.", ex);
		}
	}

	/**
	 * Adds an audio level effect to the tracks of the specified
	 * <tt>trackControl</tt> and so that we would notify interested listeners of
	 * audio level changes.
	 * 
	 * @param trackControl
	 *            the <tt>TrackControl</tt> where we need to register a level
	 *            effect that would measure the audio levels of the
	 *            <tt>ReceiveStream</tt> associated with this class.
	 * 
	 * @throws UnsupportedPlugInException
	 *             if we fail to add our sound level effect to the track control
	 *             of <tt>mediaStream</tt>'s processor.
	 */
	private void registerStreamAudioLevelJMFEffect(TrackControl trackControl)
			throws UnsupportedPlugInException {
		// we register the effect regardless of whether or not we have any
		// listeners at this point because we won't get a second chance.
		// however the effect would do next to nothing unless we register a
		// first listener with it.
		// Assume there is only one audio track
		trackControl.setCodecChain(new Codec[] { streamAudioLevelEffect });
	}

	/**
	 * Sets the <tt>SimpleAudioLevelListener</tt> that this session should be
	 * notifying about changes in local audio level related information. This
	 * class only supports a single listener for audio changes per source (i.e.
	 * stream or data source). Audio changes are generally quite time intensive
	 * (~ 50 per second) so we are doing this in order to reduce the number of
	 * objects associated with the process (such as event instances listener
	 * list iterators and sync copies).
	 * 
	 * @param listener
	 *            the <tt>SimpleAudioLevelListener</tt> to add
	 */
	public void setLocalUserAudioLevelListener(SimpleAudioLevelListener listener) {
		localUserAudioLevelEffect.setAudioLevelListener(listener);
	}

	/**
	 * Sets the <tt>VolumeControl</tt> which is to control the volume (level) of
	 * the audio (to be) played back by this instance.
	 * 
	 * @param outputVolumeControl
	 *            the <tt>VolumeControl</tt> which is to be control the volume
	 *            (level) of the audio (to be) played back by this instance
	 */
	public void setOutputVolumeControl(VolumeControl outputVolumeControl) {
		this.outputVolumeControl = outputVolumeControl;
	}

	/**
	 * Sets <tt>listener</tt> as the <tt>SimpleAudioLevelListener</tt> that we
	 * are going to notify every time a change occurs in the audio level of the
	 * media that this device session is receiving from the remote party. This
	 * class only supports a single listener for audio changes per source (i.e.
	 * stream or data source). Audio changes are generally quite time intensive
	 * (~ 50 per second) so we are doing this in order to reduce the number of
	 * objects associated with the process (such as event instances listener
	 * list iterators and sync copies).
	 * 
	 * @param listener
	 *            the <tt>SimpleAudioLevelListener</tt> that we want notified
	 *            for audio level changes in the remote participant's media.
	 */
	public void setStreamAudioLevelListener(SimpleAudioLevelListener listener) {
		streamAudioLevelEffect.setAudioLevelListener(listener);
	}

	/**
	 * Implements a utility which facilitates setting a specific
	 * <tt>VolumeControl</tt> on a specific <tt>Renderer</tt> for the purposes
	 * of control over the volume (level) of the audio (to be) played back by
	 * the specified <tt>Renderer</tt>.
	 * 
	 * @param renderer
	 *            the <tt>Renderer</tt> on which the specified
	 *            <tt>volumeControl</tt> is to be set
	 * @param volumeControl
	 *            the <tt>VolumeControl</tt> to be set on the specified
	 *            <tt>renderer</tt>
	 */
	public static void setVolumeControl(Renderer renderer,
			VolumeControl volumeControl) {
		if (renderer instanceof AbstractAudioRenderer) {
			AbstractAudioRenderer<?> abstractAudioRenderer = (AbstractAudioRenderer<?>) renderer;

			abstractAudioRenderer.setVolumeControl(volumeControl);
		}
	}
}
