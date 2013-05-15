package com.jajeem.command.handler;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.model.Command;
import com.jajeem.core.design.Student;
import com.jajeem.util.KeyboardBlocker;
import com.jajeem.util.MouseBlocker;

public class SetBlackoutCommandHandler implements ICommandHandler {

	private static MouseBlocker mouseBlocker = new MouseBlocker();
//	private static KeyboardBlocker keyboardBlocker = new KeyboardBlocker();
	private static JFrame frame = new JFrame();

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		if (Student.isBlack()) {
			mouseBlocker.stop();
//			keyboardBlocker.stop();
			frame.setVisible(false);
			frame.dispose();
			Student.setBlack(false);

		} else {
				WebLabel label = new WebLabel("LOCKED");
				label.setFont(new Font("Serif", Font.PLAIN, 50));
				WebPanel panel = new WebPanel(new FlowLayout(FlowLayout.CENTER));
				panel.add(label);
				frame.add(panel);
				Toolkit tk = Toolkit.getDefaultToolkit();
				frame.setBounds(new Rectangle(new Point(0, 0), tk
						.getScreenSize()));
				frame.setUndecorated(true);
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);
				mouseBlocker.start();
//				keyboardBlocker.start();
				Student.setBlack(true);
			}
		}
	}
