package com.jajeem.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jajeem.core.design.InstructorNoa;

public class test extends JFrame {
	test() {
		setContentPane(new ContentPanel());
		// setSize(500, 300);
		setSize(2000, 1000);
	}

	public static void main(String[] args) {
		test jrframe = new test();
		jrframe.setUndecorated(true);
		jrframe.setVisible(true);
	}
}

class ContentPanel extends JPanel {
	Image bgimage = null;

	ContentPanel() {
		MediaTracker mt = new MediaTracker(this);
		bgimage = Toolkit.getDefaultToolkit().getImage(
				test.class.getResource("/icons/noa_en/background.png"));
		mt.addImage(bgimage, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int imwidth = bgimage.getWidth(null);
		int imheight = bgimage.getHeight(null);
		this.setSize(imwidth, imheight);
		g.drawImage(bgimage, 1, 1, null);
	}
}