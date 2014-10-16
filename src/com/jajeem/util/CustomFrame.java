package com.jajeem.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CustomFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int posX = 0, posY = 0;

	public CustomFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim);
		setContentPane(new CustomPanel("/icons/noa_en/background.png"));

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen()
						- posY);

			}
		});

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					int i = JOptionPane.showConfirmDialog(getContentPane(),
							"Are you sure you want to close this window?");
					if (i == 0) {
						dispose();
					} else if (i == 1) {
						return;
					} else {
						return;
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		CustomFrame frm = new CustomFrame();
		frm.setVisible(true);
	}
}