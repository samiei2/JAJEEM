package com.jajeem.command.handler;

import javax.swing.JOptionPane;

import com.jajeem.command.model.Command;
import com.jajeem.command.model.SendFileAssignmentCommand;
import com.jajeem.core.design.student.Student;
import com.jajeem.filemanager.client.AssignmentTimer;
import com.jajeem.util.Threading.ThreadManager;

public class SendFileAssignmentCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		SendFileAssignmentCommand command = (SendFileAssignmentCommand) cmd;
		final String file = command.getFile();
		final String time = command.getTime();
		ThreadManager.getInstance().runTemp(new Runnable() {

			@Override
			public void run() {
				JOptionPane.showMessageDialog(null, "Please answer " + file
						+ " in your inbox in " + time
						+ " minutes and then put it in your outbox.");
			}
		});
		ThreadManager.getInstance().runTemp(new Runnable() {

			@Override
			public void run() {
				AssignmentTimer timer = new AssignmentTimer();
				timer.start(time);
				timer.setLocationRelativeTo(Student.getFrmJajeemProject());
				timer.setVisible(true);

			}
		});

	}
}
