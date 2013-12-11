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

public class CustomTeacherFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	int posX = 0, posY = 0;
	JFrame mainFrame;
	CustomMainPanel panelMain;
	JPanel panel1;
	private int oldHeight;
	private int oldWidth;
	private JPanel panel_1;
	private JPanel panel_2;

	public CustomTeacherFrame() {

		mainFrame = this;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		setSize(winSize.width, winSize.height);
		 WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);

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
		panelMain = new CustomMainPanel("/icons/noa_en/new/teacherMain.png");
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

		panel1 = new JPanel();
		panel1.setOpaque(false);
		GroupLayout gl_panelMain = new GroupLayout(panelMain);
		gl_panelMain.setHorizontalGroup(gl_panelMain.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelMain.createSequentialGroup().addGap(24)
						.addComponent(panel1, 904, 1549, Short.MAX_VALUE)
						.addGap(26)));
		gl_panelMain.setVerticalGroup(gl_panelMain.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panelMain
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel1, GroupLayout.DEFAULT_SIZE, 781,
								Short.MAX_VALUE).addGap(23)));
		GridBagLayout gbl_panel1 = new GridBagLayout();
		gbl_panel1.columnWidths = new int[] { 0 };
		gbl_panel1.rowHeights = new int[] { 0, 0 };
		gbl_panel1.columnWeights = new double[] { 1.0 };
		gbl_panel1.rowWeights = new double[] { 0.2, 0.8 };
		panel1.setLayout(gbl_panel1);

		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel1.add(panel_1, gbc_panel_1);

		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel1.add(panel_2, gbc_panel_2);
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
		CustomTeacherFrame frm = new CustomTeacherFrame();
		frm.setVisible(true);
	}

	public Container getTopContentPane() {
		return panel_1;
	}

	public Container getMainContentPane() {
		return panel_2;
	}
}