package com.jajeem.command.handler;

import java.awt.Toolkit;

import javax.swing.JFrame;

import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.util.MouseBlocker;

//import com.jajeem.util.KeyboardBlocker;

public class SetBlackoutCommandHandler implements ICommandHandler {

	private static MouseBlocker mouseBlocker = new MouseBlocker();
	// private static KeyboardBlocker keyboardBlocker = new KeyboardBlocker();
	private static JFrame frame = new JFrame();

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (Student.isBlack()) {
			mouseBlocker.stop();
			// keyboardBlocker.stop();
			frame.setVisible(false);
			frame.dispose();
			Student.setBlack(false);

		} else {
			frame = new JFrame("");
			Toolkit tk = Toolkit.getDefaultToolkit();
			int xSize = ((int) tk.getScreenSize().getWidth());
			int ySize = ((int) tk.getScreenSize().getHeight());
			frame.setSize(xSize, ySize);
			frame.setUndecorated(true);
			frame.setVisible(true);
			mouseBlocker.start();
			// keyboardBlocker.start();
			Student.setBlack(true);
		}
	}
}
