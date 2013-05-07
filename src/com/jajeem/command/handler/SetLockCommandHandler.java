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

public class SetLockCommandHandler implements ICommandHandler {

	@Override
	public void run(Command cmd) throws NumberFormatException, Exception {
		JFrame frame = new JFrame();
		WebLabel label = new WebLabel("LOCKED");
		label.setFont(new Font("Serif", Font.PLAIN, 50));
		WebPanel panel = new WebPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        frame.add(panel);
		Toolkit tk = Toolkit.getDefaultToolkit();
		frame.setBounds(new Rectangle(new Point(0, 0), tk.getScreenSize()));
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}

}
