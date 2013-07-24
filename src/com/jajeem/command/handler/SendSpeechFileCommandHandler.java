package com.jajeem.command.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JOptionPane;

import com.alee.extended.filechooser.FileListCellEditor;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendFileAssignmentCommand;
import com.jajeem.command.model.SendFileCollectCommand;
import com.jajeem.command.model.SendSpeechFileCommand;
import com.jajeem.core.design.Student;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.AssignmentTimer;
import com.jajeem.util.Config;

public class SendSpeechFileCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendSpeechFileCommand command = (SendSpeechFileCommand)cmd;
		String filePath = null;
		try {

			File receivedFile = new File(new File("Inbox"),command.getFile());
			if(receivedFile.exists()){
				filePath = receivedFile.getAbsolutePath();
			}
			
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			byte[] sendData = new byte[1024];
			if(filePath==null)
				return;
			String sentence = filePath;
			sendData = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3030);
			clientSocket.send(sendPacket);
			clientSocket.close();
		} catch (Exception e) {
			
		}
		
	}
}