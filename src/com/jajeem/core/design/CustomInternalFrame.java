package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class CustomInternalFrame {
	public CustomInternalFrame() {
		JFrame frame = new JFrame();

		JLabel lblNewLabel = new JLabel("New label");
		ImageIcon icon = new ImageIcon(
				CustomInternalFrame.class
						.getResource("/icons/noa_en/internalframe.png"));
		ImageIcon monitorIconScaled = new ImageIcon(icon.getImage()
				.getScaledInstance(350, 250, Image.SCALE_SMOOTH));
		lblNewLabel.setIcon(monitorIconScaled);
		frame.getContentPane().add(lblNewLabel, BorderLayout.CENTER);

	}
}
