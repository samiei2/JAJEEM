package com.jajeem.command.handler;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendSpeechFileCommand;
import com.jajeem.util.FileUtil;

public class SendSpeechFileCommandHandler implements ICommandHandler {

	@SuppressWarnings("resource")
	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendSpeechFileCommand command = (SendSpeechFileCommand)cmd;
		String filePath = null;
		try {
			String inbox = FileUtil.getInboxPath();
			File inb = new File(inbox);
			if(!inb.exists())
				inb.mkdirs();
			File receivedFile = new File(inb,command.getFile());
			if(receivedFile.exists()){
				filePath = receivedFile.getAbsolutePath().trim();
			}
			
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] sendData = new byte[1024];
			if(filePath==null) {
				return;
			}
			String sentence = filePath;
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3030);
			clientSocket.send(sendPacket);
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
