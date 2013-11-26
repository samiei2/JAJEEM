package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.rootpane.WebFrame;
import com.jajeem.util.CustomPanel;
import com.sun.awt.AWTUtilities;

public class CustomAccountFrame extends WebFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomPanel panelTop;
	CustomPanel panelContent;
	CustomPanel panelClose;
	private int posX;
	private int posY;

	public CustomAccountFrame() {
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		System.setProperty("sun.java2d.noddraw", "true");
		AWTUtilities.setWindowOpaque(this, false);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);

		panelContent = new CustomPanel("/icons/noa_en/accountmainpanel.png");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 753,
						Short.MAX_VALUE)
				.addComponent(panelContent, GroupLayout.DEFAULT_SIZE, 753,
						Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 55,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								320, Short.MAX_VALUE)));

		panelClose = new CustomPanel("/icons/noa_en/accountclosepanel.png");

		panelTop = new CustomPanel("/icons/noa_en/accounttoppanel.png");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addGap(28)
						.addComponent(panelTop, GroupLayout.PREFERRED_SIZE,
								237, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 117,
								Short.MAX_VALUE)
						.addComponent(panelClose, GroupLayout.PREFERRED_SIZE,
								173, GroupLayout.PREFERRED_SIZE).addGap(29)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(panelTop,
												GroupLayout.PREFERRED_SIZE, 55,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(panelClose,
												GroupLayout.PREFERRED_SIZE, 55,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panelClose.setLayout(null);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

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

		this.addKeyListener(new KeyAdapter() {
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

	public Container getMainContentPane() {
		return panelContent;
	}

	public Container getTopContentPane() {
		return panelTop;
	}

	public Container getCloseContentPane() {
		return panelClose;
	}
}

class CustomAccountCheckBox extends JCheckBox {
	/**
	 * 
	 */
	private BufferedImage originalImage;
	boolean isSelected = false;

	public CustomAccountCheckBox() {
		try {
			originalImage = ImageIO.read(CustomPanel.class
					.getResource("/icons/noa_en/accountButtonSelected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		getModel().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (model.isSelected()) {
					isSelected = true;
				} else {
					isSelected = false;
				}
			}
		});
		setOpaque(false);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		Graphics g2 = g.create();
		if (isSelected) {
			g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
		}
		String text = getText();
		g2.drawString(text, 13, 25);
		g2.dispose();
	}
}