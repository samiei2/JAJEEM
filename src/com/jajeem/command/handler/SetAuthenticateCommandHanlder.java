package com.jajeem.command.handler;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JInternalFrame;

import com.jajeem.command.model.AuthenticateCommand;
import com.jajeem.command.model.Command;
import com.jajeem.command.model.DuplicateLoginCommand;
import com.jajeem.command.model.GrantCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.design.InstructorNoaUtil;
import com.jajeem.core.model.Student;
import com.jajeem.core.service.StudentService;
import com.jajeem.util.Config;
import com.jajeem.util.Session;

public class SetAuthenticateCommandHanlder implements ICommandHandler {

	private static Object authenticateLock = new Object();

	@Override
	public void run(Command cmd) throws NumberFormatException,
			UnknownHostException, Exception {
		new Config();
		ServerService serverService = new ServerService();
		boolean grant = false;
		GrantCommand grantCommand = new GrantCommand(InetAddress.getLocalHost()
				.getHostAddress(), ((AuthenticateCommand) cmd).getFrom(),
				Integer.parseInt(Config.getParam("port")), false, null);

		StudentService studentService = new StudentService();
		grant = studentService.authenticate(
				((AuthenticateCommand) cmd).getUsername(),
				((AuthenticateCommand) cmd).getPassword());

		synchronized (authenticateLock) {
			if (Session.getLoggedInStudents().containsValue(
					((AuthenticateCommand) cmd).getUsername())) {
				DuplicateLoginCommand dup = new DuplicateLoginCommand(
						InetAddress.getLocalHost().getHostAddress(),
						((AuthenticateCommand) cmd).getFrom(),
						Integer.parseInt(Config.getParam("port")));
				serverService.send(dup);
				return;
			} else {
				if (grant) {
					Session.getLoggedInStudents().put(
							((AuthenticateCommand) cmd).getFrom(),
							((AuthenticateCommand) cmd).getUsername());
				}
			}
		}

		grantCommand.setGranted(grant);

		if (grant) {

			Student student = new Student();
			student = studentService.get(((AuthenticateCommand) cmd)
					.getUsername());
			grantCommand.setStudent(student);

			InstructorNoa.studentList.put(cmd.getFrom(), student);

			JInternalFrame[] frames = InstructorNoa.getDesktopPane()
					.getAllFrames();
			for (JInternalFrame frame : frames) {
				if (cmd.getFrom().compareTo(
						(String) frame.getClientProperty("ip")) == 0) {
					try {
						frame.dispose();
					} catch (Exception e) {
					}
					try {
						InstructorNoaUtil.createFrame(
								InstructorNoa.getDesktopPane(),
								((AuthenticateCommand) cmd).getFrom(),
								((AuthenticateCommand) cmd).getUsername());
					} catch (Exception e) {
					}
					// frame.putClientProperty("username",
					// ((AuthenticateCommand) cmd).getUsername());
					// System.out.println(frame.getTitle());
					// frame.setTitle(((AuthenticateCommand)
					// cmd).getUsername());
					// ((WebInternalFrame)frame).close();
					// ((WebInternalFrame)frame).setTitle("arminiak");
					// ((WebInternalFrame)frame).open();

					break;
				}
			}
		}

		serverService.send(grantCommand);
	}

	@SuppressWarnings("unused")
	private void printInternalFields(Object frame) {
		for (Field field : frame.getClass().getFields()) {
			field.setAccessible(true); // if you want to modify private fields
			if (!field.getClass().isPrimitive()) {
				printInternalFields(field);
			}
			try {
				System.out.println(field.getName() + " - " + field.getType()
						+ " - " + field.get(frame));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
