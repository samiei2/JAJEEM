package com.jajeem.command.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.*;

import com.jajeem.command.model.*;
import com.jajeem.util.Config;

public class ServerService extends TimerTask implements IConnectorSevice {

	protected MulticastSocket socket;
	protected Timer timer;
	protected InetAddress group;
	protected int port;
	protected int ttl;
	protected int interval;
	protected String host;
	protected String type;
	
	private boolean stopped;
	
	static Logger logger = Logger.getLogger("ServerService.class");
	
	public ServerService(String group, int ttl, int interval,
			String type) throws NumberFormatException, Exception {

		PropertyConfigurator.configure("conf/log4j.conf");
			
		stopped = false;
		
		this.port = Integer.parseInt(Config.getParam("port"));
		this.group = InetAddress.getByName(group);
		this.host = group;
		this.ttl = ttl;
		this.type = type;
		this.interval = interval;

		socket = new MulticastSocket();
		socket.setTimeToLive(ttl);
		timer = new Timer();
	}

	@Override
	public void start() {
		timer.schedule(this, 0, interval);
	}

	@Override
	public void stop() {
		timer.cancel();
		timer.purge();
		stopped = true;
	}

	@Override
	public void send(int destination) {
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
	
	public boolean isStopped() {
		return stopped;
	}
}