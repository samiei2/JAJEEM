package com.jajeem.command.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.*;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.command.model.*;

public class ServerService extends TimerTask implements IConnectorSevice {

	protected MulticastSocket socket;
	protected Timer timer;
	protected InetAddress group;
	protected int port;
	protected int ttl;
	protected int interval;
	protected String host;
	protected String type;
	
	static Logger logger = Logger.getLogger("ServerService.class");
	
	public ServerService(String group, int port, int ttl, int interval,
			String type) throws UnknownHostException, IOException {

		this.port = port;
		this.group = InetAddress.getByName(group);
		this.host = group;
		this.ttl = ttl;
		this.type = type;
		this.interval = interval;

		socket = new MulticastSocket();
		socket.setTimeToLive(ttl);
		timer = new Timer();
		PropertyConfigurator.configure("conf/log4j.conf");
		logger.info("First LOG!");
	}

	@Override
	public void start() {
		timer.schedule(this, 0, interval);
	}

	@Override
	public void stop() {
		timer.cancel();
		timer.purge();
	}

	@Override
	public void send() {
		// TODO Auto-generated method stub

	}

	@Override
	public void broadcast() {
		// TODO Auto-generated method stub

	}

	public void run() {
		byte[] b = null;

		b = constructMessage();
		DatagramPacket packet = new DatagramPacket(b, b.length, group, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			System.err.println(e);
			try {
				System.out.println("Message Size: " + b.length);
				System.out.println("SendBufferSize: "
						+ socket.getSendBufferSize());
			} catch (SocketException se) {
				System.err.println(se);
			}
		}
		
		System.out.println("Server started...");
	}

	private byte[] constructMessage() {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);

			Command msg = new Command(host, port, type);

			o.writeObject(msg);
			o.flush();
			b.flush();

			return b.toByteArray();

		} catch (IOException ex) {
			System.out.println("Error while sending an announce message");
			return null;
		}
	}

	@Override
	public void process(DataInputStream d) {
		// TODO Auto-generated method stub

	}
}