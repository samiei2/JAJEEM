package com.jajeem.command.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.command.handler.*;
import com.jajeem.command.model.*;

public class ClientService implements IConnectorSevice, Runnable {

	protected MulticastSocket socket;
	protected InetAddress group;
	protected int port;
	protected Thread thread;

	private boolean stopped;

	static Logger logger = Logger.getLogger("ServerService.class");

	public ClientService(String group, int port) throws NumberFormatException,
			Exception {

		stopped = false;

		PropertyConfigurator.configure("conf/log4j.conf");

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

				if (cmd instanceof StartCaptureCommand) {
					StartCaptureCommandHandler startCaptureHandler = new StartCaptureCommandHandler();
					startCaptureHandler.run(cmd);
				} else if (cmd instanceof StopCaptureCommand) {
					StopCaptureCommandHandler stopCaptureHandler = new StopCaptureCommandHandler();
					stopCaptureHandler.run(cmd);
				} else if (cmd instanceof StartViewerCommand) {
					StartViewerCommandHandler startViewerHandler = new StartViewerCommandHandler();
					startViewerHandler.run(cmd);
				} else if (cmd instanceof StartUpCommand) {
					StartUpCommandHandler startUpHandler = new StartUpCommandHandler();
					startUpHandler.run(cmd);
				} else if (cmd instanceof StartQuizCommand) {
					StartQuizCommandHandler startQuizHandler = new StartQuizCommandHandler();
					startQuizHandler.run(cmd);
				} else if (cmd instanceof SendQuizResponseCommand) {
					SendQuizResponseCommandHandler sendquizresponse = new SendQuizResponseCommandHandler();
					sendquizresponse.run(cmd);
				} else if (cmd instanceof WhiteBlackAppCommand) {
					SetWhiteBlackAppCommandHandler setWhiteBlackAppCommandHandler = new SetWhiteBlackAppCommandHandler();
					setWhiteBlackAppCommandHandler.run(cmd);
				} else if (cmd instanceof BlackoutCommand) {
					SetBlackoutCommandHandler setBlackoutCommandHandler = new SetBlackoutCommandHandler();
					setBlackoutCommandHandler.run(cmd);
				} else if (cmd instanceof InternetCommand) {
					SetInternetCommandHandler setInternetCommandHandler = new SetInternetCommandHandler();
					setInternetCommandHandler.run(cmd);
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
