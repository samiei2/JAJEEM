package com.jajeem.core.design.ui;

import static java.lang.Thread.sleep;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.jajeem.util.CustomCloseButton;

public class CustomStudentFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	int posX = 0, posY = 0;
	JFrame mainFrame;
	CustomMainPanel panelMain;
	JPanel panelContent;
	StudentTrayIcon trayIcon;

	public CustomStudentFrame() {
		mainFrame = this;
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		// setSize(300, 550);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
				Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE,
				Short.MAX_VALUE));

		JPanel panelClose = new JPanel();
		panelClose.setOpaque(false);
		panelMain = new CustomMainPanel("/icons/noa_en/studentback.png");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panelClose, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addGroup(
						gl_panel.createSequentialGroup().addComponent(
								panelMain, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(panelClose, GroupLayout.PREFERRED_SIZE,
								31, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelMain, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		GridBagLayout gbl_panelMain = new GridBagLayout();
		gbl_panelMain.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelMain.columnWeights = new double[] { 0.1, 0.8, 0.1 };
		gbl_panelMain.rowWeights = new double[] { 0.84, 0.02, 0.14 };
		panelMain.setLayout(gbl_panelMain);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		// gbc_panel_1.gridwidth = 9;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		// gbc_panel_1.weighty = 0.24;
		panelMain.add(panel_1, gbc_panel_1);

		panelContent = new JPanel();
		panelContent.setOpaque(false);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 1;
		// gbc_panel_2.weighty = 0.72;
		panelMain.add(panelContent, gbc_panel_2);
		GroupLayout gl_panelContent = new GroupLayout(panelContent);
		gl_panelContent.setHorizontalGroup(gl_panelContent.createParallelGroup(
				Alignment.LEADING).addGap(0, 253, Short.MAX_VALUE));
		gl_panelContent.setVerticalGroup(gl_panelContent.createParallelGroup(
				Alignment.LEADING).addGap(0, 358, Short.MAX_VALUE));
		panelContent.setLayout(gl_panelContent);

		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		panelMain.add(panel_3, gbc_panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 0, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 2;
		gbc_panel_4.gridy = 1;
		panelMain.add(panel_4, gbc_panel_4);

		JPanel panel_5 = new JPanel();
		panel_5.setOpaque(false);
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 0, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 2;
		// gbc_panel_5.weighty = 0.04;
		panelMain.add(panel_5, gbc_panel_5);

		CustomMainPanel panelCloseButton = new CustomMainPanel(
				"/icons/noa_en/closePanel.png");
		GroupLayout gl_panelClose = new GroupLayout(panelClose);
		gl_panelClose.setHorizontalGroup(gl_panelClose.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_panelClose
						.createSequentialGroup()
						.addContainerGap(258, Short.MAX_VALUE)
						.addComponent(panelCloseButton,
								GroupLayout.PREFERRED_SIZE, 103,
								GroupLayout.PREFERRED_SIZE).addGap(89)));
		gl_panelClose.setVerticalGroup(gl_panelClose.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelClose
						.createSequentialGroup()
						.addComponent(panelCloseButton,
								GroupLayout.PREFERRED_SIZE, 31,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		CustomMainButton webButtonMin = new CustomMainButton(
				"/icons/noa_en/min", 20, 10, 10, 15);
		webButtonMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setExtendedState(Frame.ICONIFIED);
				// trayIcon.add();
				// mainFrame.setVisible(flse);
			}
		});

		CustomCloseButton webButtonClose = new CustomCloseButton(
				"/icons/noa_en/close.png");
		webButtonClose.setUndecorated(true);
		webButtonClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setVisible(false);
			}
		});
		GroupLayout gl_panelCloseButton = new GroupLayout(panelCloseButton);
		gl_panelCloseButton.setHorizontalGroup(gl_panelCloseButton
				.createParallelGroup(Alignment.LEADING).addGroup(
						Alignment.TRAILING,
						gl_panelCloseButton
								.createSequentialGroup()
								.addContainerGap(58, Short.MAX_VALUE)
								.addComponent(webButtonMin,
										GroupLayout.PREFERRED_SIZE, 40,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(webButtonClose,
										GroupLayout.PREFERRED_SIZE, 40,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panelCloseButton
				.setVerticalGroup(gl_panelCloseButton
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelCloseButton
										.createSequentialGroup()
										.addGroup(
												gl_panelCloseButton
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																webButtonClose,
																GroupLayout.PREFERRED_SIZE,
																32,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webButtonMin,
																GroupLayout.PREFERRED_SIZE,
																32,
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

		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				mainFrame.toFront();
			}
		});

		// trayIcon = new StudentTrayIcon(mainFrame);
	}

	public static void main(String[] args) {
		CustomStudentFrame frm = new CustomStudentFrame();
		frm.setVisible(true);
	}

	public Container getMainContentPane() {
		return panelContent;
	}
}

class StudentTrayIcon {
	TrayIcon icon;
	JFrame mainFrame;
	Thread _notificationThread;
	SystemTray tray;

	public StudentTrayIcon() {

	}

	public void add() {
		try {
			tray.add(icon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		_notificationThread.start();
	}

	public StudentTrayIcon(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		try {
			SetupTrayIcon();
		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
		}
		tray = SystemTray.getSystemTray();
	}

	private void SetupTrayIcon() throws AWTException, InterruptedException {
		icon = new TrayIcon(getImage(), "Noavaran Co. ClassMate",
				createPopupMenu());

		icon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible(true);
				_notificationThread.interrupt();
				tray.remove(icon);
			}

		});

		_notificationThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					icon.displayMessage("Warning", "Click me! =)",
							TrayIcon.MessageType.WARNING);
				}
			}
		});
		// _notificationThread.start();
	}

	private static Image getImage() throws HeadlessException {
		ImageIcon originalImage = null;
		try {
			originalImage = new ImageIcon(
					StudentTrayIcon.class.getResource("/icons/noa_en/logo.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Image img = new BufferedImage(originalImage.getIconWidth(),
				originalImage.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);

		originalImage.paintIcon(new Panel(), img.getGraphics(), 0, 0);

		return img;
	}

	private static PopupMenu createPopupMenu() throws HeadlessException {
		PopupMenu menu = new PopupMenu();
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menu.add(exit);
		return menu;
	}
}