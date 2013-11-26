package com.jajeem.recorder.design;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebDialog;
import com.jajeem.util.CustomPanel;

public class CustomRecorderDialog extends WebDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int posX = 0, posY = 0;
	JPanel contentPanel;

	public CustomRecorderDialog() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
		setBackground(new Color(0, 255, 0, 0));
		Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new Dimension(231, 333));

		CustomPanel panel_1 = new CustomPanel("/icons/noa_en/recorderpanel.png");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel_1,
				GroupLayout.PREFERRED_SIZE, 231, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel_1, Alignment.TRAILING,
				GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE));

		contentPanel = new JPanel();
		contentPanel.setOpaque(false);

		WebButton webButton_4 = new WebButton();
		webButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		webButton_4.setUndecorated(true);
		webButton_4.setBackground(new Color(0, 255, 0, 0));
		webButton_4.setIcon(new ImageIcon(CustomRecorderDialog.class
				.getResource("/icons/noa_en/recorderclose.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				contentPanel,
																				GroupLayout.DEFAULT_SIZE,
																				211,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																Alignment.TRAILING,
																gl_panel_1
																		.createSequentialGroup()
																		.addComponent(
																				webButton_4,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(54)))));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webButton_4, GroupLayout.PREFERRED_SIZE,
								32, GroupLayout.PREFERRED_SIZE)
						.addGap(34)
						.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE,
								245, Short.MAX_VALUE).addContainerGap()));
		panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);
		// setContentPane(new CustomPanel("/icons/noa_en/recorder.png"));

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
			}

			@Override
			public void keyReleased(KeyEvent e) {
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

	public Container getMainContentPane() {
		return contentPanel;
	}

	public static void main(String[] args) {
		CustomRecorderDialog frm = new CustomRecorderDialog();
		frm.setVisible(true);
	}
}

class CustomRecordButton extends WebButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage originalImage;

	public CustomRecordButton() {
		init();
	}

	public CustomRecordButton(String label) {
		super(label);
		init();
	}

	public void init() {
		try {
			originalImage = ImageIO.read(CustomPanel.class
					.getResource("/icons/noa_en/recorderbutton.png"));
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
		super.paintComponent(g);
	}
}
