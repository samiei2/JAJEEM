package org.jitsi.examples;

import java.net.DatagramSocket;

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

public class ConfCall {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MediaService service = LibJitsi.getMediaService();
		MediaDevice audioDevice = service.createMixer(service.getDefaultDevice(MediaType.AUDIO, MediaUseCase.CALL));
		StreamConnector connector = new DefaultStreamConnector(
				new DatagramSocket(config.getLocalPort()),
		        new DatagramSocket(config.getLocalPort() + 1));
		
		for (Target target:config.getTargets()) {    //IP and Port of targets is known, Make a stream for each target
			MediaStream stream =
					service.createMediaStream(connector, audioDevice);
		    stream.setTarget(new
		    MediaStreamTarget(target.getDataSocket(),
		    		target.getControlSocket()));
		    stream.setDirection(MediaDirection.SENDRECV);
		    streams[i] = stream;
		}
		            
		for (MediaStream stream:streams) {
			stream.start();
		}
	}

}
