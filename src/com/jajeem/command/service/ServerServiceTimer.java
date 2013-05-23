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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.jajeem.command.model.Command;
import com.jajeem.util.Config;

public class ServerServiceTimer extends TimerTask implements IConnectorSevice {

	protected MulticastSocket socket;
	protected Timer timer;
	protected InetAddress group;
	protected int port;
	protected int ttl;
	private int interval;
	protected String host;
	protected String type;
	private Command cmd;

	private boolean stopped;
	
	static Logger logger = Logger.getLogger("ServerServiceTimer.class");
	
	public ServerServiceTimer() throws NumberFormatException, Exception {

		PropertyConfigurator.configure("conf/log4j.conf");
			
		stopped = false;
		this.ttl = Integer.parseInt(Config.getParam("ttl"));;
		this.setInterval(Integer.parseInt(Config.getParam("interval")));;

		socket = new MulticastSocket();
		socket.setTimeToLive(ttl);
		timer = new Timer();
		
	}
	
	public ServerServiceTimer(String group, int ttl, int interval,
			String type) throws NumberFormatException, Exception {

		PropertyConfigurator.configure("conf/log4j.conf");
			
		stopped = false;
		
		this.group = InetAddress.getByName(group);
		this.host = group;
		this.ttl = Integer.parseInt(Config.getParam("ttl"));;
		this.type = type;
		this.setInterval(Integer.parseInt(Config.getParam("interval")));;

		socket = new MulticastSocket();
		socket.setTimeToLive(ttl);
		timer = new Timer();
	}

	@Override
	public void start() {
		timer.schedule(this, 0, getInterval());
		logger.info("Server started, sending startUpCommand every: " + getInterval());
	}

	@Override
	public void stop() {
		timer.cancel();
		timer.purge();
		stopped = true;
	}

	@Override
	public void send(Command cmd) {
		byte[] b = null;
		InetAddress group = null;
		
		try {
			group = InetAddress.getByName(cmd.getTo());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		b = constructMessage(cmd);
		
		DatagramPacket packet = new DatagramPacket(b, b.length, group, cmd.getPort());
		
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
	
	@Override
	public void run() {
		byte[] b = null;
		
		try {
			group = InetAddress.getByName(cmd.getTo());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		b = constructMessage(cmd);
		DatagramPacket packet = new DatagramPacket(b, b.length, group, cmd.getPort());
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
	

	/**
	 * Gets an command object and convert it to an stream of bytes
	 * @param command Command
	 * @return stream of bytes
	 */
	private byte[] constructMessage(Command cmd) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o = new ObjectOutputStream(b);

			o.writeObject(cmd);
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

	public Command getCmd() {
		return cmd;
	}

	public void setCmd(Command cmd) {
		this.cmd = cmd;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
}