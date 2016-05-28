package com.jajeem.core.design.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;
import com.jajeem.util.CustomButton;
import com.jajeem.util.WindowResizeAdapter;
import com.jajeem.util.test;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CustomTeacherFrame2 extends JFrame {
	private static final long serialVersionUID = 1L;
	int posX = 0, posY = 0;
	JFrame mainFrame;
	private int oldHeight;
	private int oldWidth;
	private JPanel panel_6;
	private JPanel panel_5;

	public CustomTeacherFrame2() {

		mainFrame = this;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		setSize(winSize.width, winSize.height);
		WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
//		 setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(panel, 1024, GroupLayout.PREFERRED_SIZE,
								Short.MAX_VALUE).addGap(0)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel, 560,
				GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE));

		JPanel panelClose = new JPanel();
		panelClose.setOpaque(false);
		JPanel panelMain = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panelMain,
														Alignment.LEADING,
														1024,
														GroupLayout.PREFERRED_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelClose,
														Alignment.LEADING,
														1024,
														GroupLayout.PREFERRED_SIZE,
														Short.MAX_VALUE))
								.addGap(1)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(panelClose, 38,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(panelMain, 522,
								GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));
		
		JPanel panel_1 = new CustomMainPanel("/icons/noa_en/new/teacherMainTop.png");
		
		JPanel panel_2 = new CustomMainPanel("/icons/noa_en/new/teacherMainBottom.png");
		GroupLayout gl_panelMain = new GroupLayout(panelMain);
		gl_panelMain.setHorizontalGroup(
			gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1599, Short.MAX_VALUE)
				.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 1599, Short.MAX_VALUE)
		);
		gl_panelMain.setVerticalGroup(
			gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMain.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE))
		);
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 1004, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		panel_6 = new JPanel();
		panel_6.setOpaque(false);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
		);
		panel_4.setLayout(gl_panel_4);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 1579, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
		);
		
		panel_5 = new JPanel();
		panel_5.setOpaque(false);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 1559, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		panel_1.setLayout(gl_panel_1);
		panelMain.setLayout(gl_panelMain);

		CustomMainPanel panelCloseButton = new CustomMainPanel(
				"/icons/noa_en/new/closePanel.png");
		GroupLayout gl_panelClose = new GroupLayout(panelClose);
		gl_panelClose.setHorizontalGroup(gl_panelClose.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelClose
						.createSequentialGroup()
						.addContainerGap(797, Short.MAX_VALUE)
						.addComponent(panelCloseButton,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(65)));
		gl_panelClose.setVerticalGroup(gl_panelClose.createParallelGroup(
				Alignment.LEADING).addComponent(panelCloseButton, 38,
				GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));

		CustomMainButton webButtonMin = new CustomMainButton(
				"/icons/noa_en/new/minbutton", 23, 23, 0, 0);
		webButtonMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setExtendedState(Frame.ICONIFIED);
			}
		});

		CustomMainButton webButtonClose = new CustomMainButton(
				"/icons/noa_en/new/closebutton", 23, 23, 0, 0);
		webButtonClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		CustomMainButton webButtonMax = new CustomMainButton(
				"/icons/noa_en/new/maxbutton", 23, 23, 0, 0);
		webButtonMax.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (mainFrame.getExtendedState() == Frame.MAXIMIZED_BOTH) {
					mainFrame.setSize(oldWidth, oldHeight);
				} else {
					oldWidth = mainFrame.getWidth();
					oldHeight = mainFrame.getHeight();
					mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
				}
			}
		});
		GroupLayout gl_panelCloseButton = new GroupLayout(panelCloseButton);
		gl_panelCloseButton
				.setHorizontalGroup(gl_panelCloseButton.createParallelGroup(
						Alignment.TRAILING).addGroup(
						gl_panelCloseButton
								.createSequentialGroup()
								.addGap(21)
								.addComponent(webButtonMin, 23,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(26)
								.addComponent(webButtonMax, 23,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(26)
								.addComponent(webButtonClose, 23,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.PREFERRED_SIZE).addGap(19)));
		gl_panelCloseButton
				.setVerticalGroup(gl_panelCloseButton
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelCloseButton
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelCloseButton
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webButtonMax,
																23,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webButtonMin,
																23,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webButtonClose,
																23,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panelCloseButton.setLayout(gl_panelCloseButton);
		panelClose.setLayout(gl_panelClose);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

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

		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				mainFrame.toFront();
			}
		});
	}

	public static void main(String[] args) {
		CustomTeacherFrame2 frm = new CustomTeacherFrame2();
		frm.setVisible(true);
	}

	public Container getTopContentPane() {
		return panel_5;
	}

	public Container getMainContentPane() {
		return panel_6;
	}
}