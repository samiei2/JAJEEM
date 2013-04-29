package com.jajeem.command.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import com.jajeem.command.handler.*;
import com.jajeem.command.model.*;

public class ClientService implements IConnectorSevice, Runnable {

	protected MulticastSocket socket;
	protected InetAddress group;
	protected int port;
	protected Thread thread;
	
	private boolean stopped;

	public ClientService(String group, int port) throws UnknownHostException,
			IOException {

		stopped = false;
		
		this.port = port;
		this.group = InetAddress.getByName(group);

		/* setup the multicast control channel */
		socket = new MulticastSocket(port);
		System.out.println("Port: " + socket.getLocalPort());

		// socket.connect(this.group,port);
		socket.joinGroup(this.group);

		thread = new Thread(this);

	}

	@Override
	public void start() {
		thread.start();
	}

	@Override
	public void stop() {
		stopped = true;
	}

	@Override
	public void send(int destination) {
	}

	@Override
	public void broadcast() {
	}

	private byte[] getDatagram() throws IOException {

		int size = socket.getReceiveBufferSize();
		byte[] buffer = new byte[size];

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);

		return packet.getData();

	}

	@Override
	public void run() {
		while (true) {
			try {
				byte[] packet = getDatagram();

				ByteArrayInputStream b = new ByteArrayInputStream(packet);
				DataInputStream d = new DataInputStream(b);

				process(d);

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public void process(DataInputStream d) {
		try {

			ObjectInputStream oi = new ObjectInputStream(d);

			try {

				Object o = oi.readObject();

				if (!(o instanceof Command))
					throw new ClassNotFoundException("Object is not a message");

				Command msg = (Command) o;
				System.out.println(msg.getType());
				switch (msg.getType()) {
					case "power":
						SetPowerCommandHandler powerHandler = new SetPowerCommandHandler();
						powerHandler.run(msg.getType());
						break;
					case "startVNC":
						StartCaptureCommandHandler captureHandler = new StartCaptureCommandHandler();
						captureHandler.run(msg.getType());
						break;
					default:
						System.out.println("Unknown message, message is: " + msg.getType());
				}

			} catch (ClassNotFoundException ex) {
				System.err.println("Unknown message:" + ex.toString());
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	
	public boolean isStopped() {
		return stopped;
	}

}
