package com.jajeem.survey.design.alt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.alee.laf.rootpane.WebFrame;
import com.jajeem.util.CustomPanel;
import com.jajeem.util.WindowResizeAdapter;
import com.jajeem.util.test;
import com.sun.awt.AWTUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javaQuery.core.keyType;

public class BaseSurveyFrame extends WebFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panelTop;
	CustomSurveyPanel panelContent;
	JFrame mainFrame;
	int posX, posY;
	private CustomSurveyButton customQuizButton;

	public BaseSurveyFrame() {
		WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame = this;
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
//		System.setProperty("sun.java2d.noddraw", "true");
//		AWTUtilities.setWindowOpaque(this, false);

		CustomSurveyPanel panel = new CustomSurveyPanel("/icons/noa_en/accountmainpanel.png");

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
														GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
												.addComponent(
														panelTop,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelTop, GroupLayout.PREFERRED_SIZE, 52,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
								Short.MAX_VALUE).addContainerGap()));

		panelContent = new CustomSurveyPanel("/icons/noa_en/quizdesignpanel.png");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE).addContainerGap()));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		customQuizButton = new CustomSurveyButton("/icons/noa_en/fileclosebutton.png");
		customQuizButton.setUndecorated(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(0)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(customQuizButton, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
							.addGap(38))
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(customQuizButton, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);

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
				if(e.getModifiersEx() == InputEvent.ALT_DOWN_MASK)
					if(e.getKeyCode() == KeyEvent.VK_F4)
						dispose();
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
	
	public Container getCloseButton(){
		return customQuizButton;
	}
	
	public static void main(String[] args) {
		BaseSurveyFrame frame = new BaseSurveyFrame();
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
}

class CustomSurveyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;

	CustomSurveyPanel(String imageURI) {
		try {
			originalImage = ImageIO.read(test.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), null);
		g2.dispose();
	}

	@Override
	public int getWidth() {
		int w = super.getWidth();
		return w;
	}

	@Override
	public int getHeight() {
		int h = super.getHeight();
		return h;
	}
}