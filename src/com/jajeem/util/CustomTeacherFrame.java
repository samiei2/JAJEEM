package com.jajeem.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.laf.button.WebButton;

public class CustomTeacherFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	int posX = 0, posY = 0;
	JFrame mainFrame;
	CustomMainPanel panelMain;
	JPanel panelContent;

	public CustomTeacherFrame() {
		mainFrame = this;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		setSize(winSize.width, winSize.height);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE));

		JPanel panelClose = new JPanel();
		panelClose.setOpaque(false);
		panelMain = new CustomMainPanel("/icons/noa_en/new/teacherMain2.png");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panelMain, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panelClose, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(panelClose, GroupLayout.PREFERRED_SIZE,
								49, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelMain, GroupLayout.PREFERRED_SIZE,
								810, GroupLayout.PREFERRED_SIZE)));

		panelContent = new JPanel();
		panelContent.setOpaque(false);
		GroupLayout gl_panelMain = new GroupLayout(panelMain);
		gl_panelMain.setHorizontalGroup(gl_panelMain.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelMain
						.createSequentialGroup()
						.addGap(24)
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								1550, Short.MAX_VALUE).addGap(26)));
		gl_panelMain.setVerticalGroup(gl_panelMain.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelMain
						.createSequentialGroup()
						.addGap(22)
						.addComponent(panelContent, GroupLayout.DEFAULT_SIZE,
								824, Short.MAX_VALUE).addGap(23)));
		panelMain.setLayout(gl_panelMain);

		CustomMainPanel panelCloseButton = new CustomMainPanel(
				"/icons/noa_en/new/closePanel.png");
		GroupLayout gl_panelClose = new GroupLayout(panelClose);
		gl_panelClose.setHorizontalGroup(gl_panelClose.createParallelGroup(
				Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_panelClose
						.createSequentialGroup()
						.addGap(1351)
						.addComponent(panelCloseButton,
								GroupLayout.PREFERRED_SIZE, 162,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(87, Short.MAX_VALUE)));
		gl_panelClose.setVerticalGroup(gl_panelClose.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelClose
						.createSequentialGroup()
						.addComponent(panelCloseButton,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		CustomMainButton webButtonMin = new CustomMainButton(
				"/icons/noa_en/new/minbutton", 27, 27, 0, 0);
		webButtonMin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setExtendedState(Frame.ICONIFIED);
			}
		});

		CustomMainButton webButtonClose = new CustomMainButton(
				"/icons/noa_en/new/closebutton", 27, 27, 0, 0);
		webButtonClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}
		});

		CustomMainButton webButtonMax = new CustomMainButton(
				"/icons/noa_en/new/maxbutton", 27, 27, 0, 0);
		webButtonMax.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
			}
		});
		GroupLayout gl_panelCloseButton = new GroupLayout(panelCloseButton);
		gl_panelCloseButton
				.setHorizontalGroup(gl_panelCloseButton.createParallelGroup(
						Alignment.TRAILING).addGroup(
						gl_panelCloseButton
								.createSequentialGroup()
								.addGap(21)
								.addComponent(webButtonMin,
										GroupLayout.PREFERRED_SIZE, 27,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED,
										23, Short.MAX_VALUE)
								.addComponent(webButtonMax,
										GroupLayout.PREFERRED_SIZE, 27,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(webButtonClose,
										GroupLayout.PREFERRED_SIZE, 27,
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
																GroupLayout.PREFERRED_SIZE,
																27,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webButtonMin,
																GroupLayout.PREFERRED_SIZE,
																27,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webButtonClose,
																GroupLayout.PREFERRED_SIZE,
																27,
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
	}

	public static void main(String[] args) {
		CustomTeacherFrame frm = new CustomTeacherFrame();
		frm.setVisible(true);
	}

	public Container getMainContentPane() {
		return panelContent;
	}
}

class CustomMainPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;

	CustomMainPanel(String imageURI) {
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

class CustomMainButton extends WebButton {
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;
	int width, height = 20;
	int x = 10;
	int y = 5;
	BufferedImage background;
	BufferedImage rollover;
	BufferedImage selected;

	boolean isRollOver, isPressed;

	CustomMainButton(String imageURI) {
		try {
			originalImage = ImageIO.read(test.class.getResource(imageURI));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setUndecorated(false);
	}

	CustomMainButton(String imageURI, int w, int h) {

		{
			try {
				URL inp = CustomButton.class.getResource(imageURI + ".png");
				background = ImageIO.read(inp);
				inp = CustomButton.class.getResource(imageURI + "Hover.png");
				rollover = ImageIO.read(inp);
				inp = CustomButton.class.getResource(imageURI + "Pressed.png");
				selected = ImageIO.read(inp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			getModel().addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					ButtonModel model = (ButtonModel) e.getSource();
					if (model.isRollover()) {
						isRollOver = true;
					} else {
						isRollOver = false;
					}
					if (model.isPressed()) {
						isPressed = true;
					} else {
						isPressed = false;
					}
				}
			});
		}

		setOpaque(false);
		width = w;
		height = h;
	}

	CustomMainButton(String imageURI, int w, int h, int x, int y) {
		{
			try {
				URL inp = CustomButton.class.getResource(imageURI + ".png");
				background = ImageIO.read(inp);
				// inp = CustomButton.class.getResource(imageURI+"Hover.png");
				// rollover = ImageIO.read(inp);
				// inp = CustomButton.class.getResource(imageURI+"Pressed.png");
				// selected = ImageIO.read(inp);
			} catch (IOException e) {
				e.printStackTrace();
			}
			getModel().addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					ButtonModel model = (ButtonModel) e.getSource();
					if (model.isRollover()) {
						isRollOver = true;
					} else {
						isRollOver = false;
					}
					if (model.isPressed()) {
						isPressed = true;
					} else {
						isPressed = false;
					}
				}
			});
		}
		try {
			originalImage = ImageIO.read(test.class.getResource(imageURI
					+ ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setOpaque(false);
		width = w;
		height = h;
		this.x = x;
		this.y = y;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics g2 = g.create();
		if (background != null) {
			g2.drawImage(background, x, y, width, height, null);
		}
		g2.dispose();
	}
}