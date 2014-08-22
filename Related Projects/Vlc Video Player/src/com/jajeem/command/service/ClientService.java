package com.jajeem.command.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


import com.jajeem.command.handler.StartVideoCommandHandler;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.StartMoviePlayerCommand;

public class ClientService implements IConnectorSevice, Runnable {

	protected MulticastSocket socket;
	protected InetAddress group;
	protected int port;
	protected Thread thread;

	private boolean stopped;

//	static Logger logger = Logger.getLogger("ClientService.class");

	public ClientService(String group, int port) throws NumberFormatException,
			Exception {

		stopped = false;

		this.port = port;
		this.group = InetAddress.getByName(group);

		/* setup the multicast control channel */
		socket = new MulticastSocket(port);
		System.out
				.println("Client listening on port: " + socket.getLocalPort());

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

				Command cmd = (Command) o;

				System.out.println("Receiving ----> Command: " + cmd.getClass() + " from: " + cmd.getFrom());
				
//				logger.info("Receiving: Message type: " + cmd.getClass()
//						+ ", from: " + cmd.getTo());

				if(cmd instanceof StartMoviePlayerCommand){
					StartVideoCommandHandler videoCommandHandler = new StartVideoCommandHandler();
					videoCommandHandler.run(cmd);
				}
				
			} catch (Exception ex) {
				System.err.println("Unknown message:" + ex.toString());
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public boolean isStopped() {
		return stopped;
	}

	@Override
	public void send(Command cmd) {
		// TODO Auto-generated method stub

	}

}
