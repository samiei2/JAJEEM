package org.jitsi.examples;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

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
	private MediaStream[] streams;
	ArrayList<Target> targetlist = new ArrayList<>();
	
	public void StartConfCall(){
		MediaService service = LibJitsi.getMediaService();
		MediaDevice audioDevice = service.createMixer(service.getDefaultDevice(MediaType.AUDIO, MediaUseCase.CALL));
		StreamConnector connector = null;
		try {
			connector = new DefaultStreamConnector(
					new DatagramSocket(8000),
			        new DatagramSocket(8001));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		int i = 0;
		for (Target target:targetlist) {    //IP and Port of targets is known, Make a stream for each target
			MediaStream stream =
					service.createMediaStream(connector, audioDevice);
		    stream.setTarget(new
		    MediaStreamTarget(target.getDataSocket(),
		    		target.getControlSocket()));
		    stream.setDirection(MediaDirection.SENDRECV);
		    streams[i] = stream;
		    i++;
		}
		            
		for (MediaStream stream:streams) {
			stream.start();
		}
	}
	
	public static void main(String[] args) {
		
	}
	
	public void setTargetList(ArrayList<Target> list){
		targetlist = list;
	}
	
	public ArrayList<Target> getTargetList() {
		return targetlist;
	}
	
	public class Target{

		private InetSocketAddress dataaddress;
		private InetSocketAddress controladdress;

		public void setDataSocket(InetSocketAddress addr){
			this.dataaddress = addr;
		}
		
		public InetSocketAddress getDataSocket() {
			return dataaddress;
		}

		public void setControlSocket(InetSocketAddress addr){
			this.controladdress = addr;
		}
		
		public InetSocketAddress getControlSocket() {
			return controladdress;
		}
	}

}


