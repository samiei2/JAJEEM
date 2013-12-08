package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.jajeem.util.CustomButton;
import com.jajeem.util.CustomPanel;

public class BaseQuizFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panelTop;
	CustomPanel panelContent;
	JFrame mainFrame;
	int posX, posY;

	public BaseQuizFrame() {
		mainFrame = this;
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));

		CustomPanel panel = new CustomPanel("/icons/noa_en/quizmain.png");
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);

		panelTop = new JPanel();
		panelTop.setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panel_1,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														480, Short.MAX_VALUE)
												.addComponent(
														panelTop,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														480, Short.MAX_VALUE))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelTop, GroupLayout.PREFERRED_SIZE, 52,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 303,
								Short.MAX_VALUE).addContainerGap()));

		panelContent = new CustomPanel("/icons/noa_en/quizdesignpanel.png");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								444, Short.MAX_VALUE).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								258, Short.MAX_VALUE).addContainerGap()));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent evt) {
				// sets frame position when mouse dragged
				if (mainFrame.getCursor().getType() == Cursor.DEFAULT_CURSOR) {
					setLocation(evt.getXOnScreen() - posX, evt.getYOnScreen()
							- posY);
				}
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

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				posX = e.getX();
				posY = e.getY();
			}
		});
	}

	public Container getMainContentPane() {
		return panelContent;
	}

	public Container getTopPane() {
		return panelTop;
	}
}