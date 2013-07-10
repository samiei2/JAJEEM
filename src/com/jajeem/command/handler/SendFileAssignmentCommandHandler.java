package com.jajeem.command.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.swing.JOptionPane;

import com.alee.extended.filechooser.FileListCellEditor;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendFileAssignmentCommand;
import com.jajeem.command.model.SendFileCollectCommand;
import com.jajeem.core.design.Student;
import com.jajeem.events.FileTransferEvent;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.AssignmentTimer;
import com.jajeem.util.Config;

public class SendFileAssignmentCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendFileAssignmentCommand command = (SendFileAssignmentCommand)cmd;
		final String file = command.getFile();
		final String time = command.getTime();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "Please answer "+file+" in your inbox in "+ time +" minutes and then put it in your outbox.");
			}
		}).start(); 
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				AssignmentTimer timer = new AssignmentTimer();
				timer.start(time);
				timer.setLocationRelativeTo(Student.getMainFram());
				timer.setVisible(true);
			}
		}).start();
		
	}
}
