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
		String file = command.getFile();
		String time = command.getTime();
		JOptionPane.showMessageDialog(null, "Please answer "+file+" in "+ time +" minutes and then put it in your outbox.");
		AssignmentTimer timer = new AssignmentTimer();
		timer.setVisible(true);
		timer.start(time);
		timer.setLocationRelativeTo(Student.getMainFram());
	}
}
