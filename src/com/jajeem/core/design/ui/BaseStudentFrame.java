package com.jajeem.core.design.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.jajeem.util.CustomPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class BaseStudentFrame extends JFrame {

	int posX = 0, posY = 0;
	JFrame mainFrame;
	CustomMainPanel panelMain;
	JPanel panelContent;
	StudentTrayIcon trayIcon;
	private CustomPanel panelLogo;
	private JPanel panelCnt;
	private CustomPanel panelClose;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaseStudentFrame frame = new BaseStudentFrame();
					frame.setSize(400, 500);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BaseStudentFrame() {
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		mainFrame = this;
		
		JPanel panelMainTop = new JPanel();
		panelMainTop.setOpaque(false);
		
		JPanel panelMainBottom = new JPanel();
		panelMainBottom.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelMainBottom, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addComponent(panelMainTop, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelMainTop, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addComponent(panelMainBottom, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
		);
		GridBagLayout gbl_panelMainBottom = new GridBagLayout();
		gbl_panelMainBottom.columnWidths = new int[]{0};
		gbl_panelMainBottom.rowHeights = new int[]{50,150};
		gbl_panelMainBottom.columnWeights = new double[]{1.0};
		gbl_panelMainBottom.rowWeights = new double[]{0.25,0.75};
		panelMainBottom.setLayout(gbl_panelMainBottom);
		
		panelLogo = new CustomPanel("/icons/noa_en/studentheader.png");
		GridBagConstraints gbc_panelLogo = new GridBagConstraints();
		gbc_panelLogo.insets = new Insets(0, 0, 0, 0);
		gbc_panelLogo.fill = GridBagConstraints.BOTH;
		gbc_panelLogo.gridx = 0;
		gbc_panelLogo.gridy = 0;
		panelMainBottom.add(panelLogo, gbc_panelLogo);
		
		CustomPanel panelContentParent = new CustomPanel("/icons/noa_en/studentback.png");
		GridBagConstraints gbc_panelContentParent = new GridBagConstraints();
		gbc_panelContentParent.fill = GridBagConstraints.BOTH;
		gbc_panelContentParent.gridx = 0;
		gbc_panelContentParent.gridy = 1;
		panelMainBottom.add(panelContentParent, gbc_panelContentParent);
		
		GridBagLayout gbl_panelContentParent = new GridBagLayout();
		gbl_panelContentParent.columnWidths = new int[]{5,0,5};
		gbl_panelContentParent.rowHeights = new int[]{0,10};
		gbl_panelContentParent.columnWeights = new double[]{0.05,0.9,0.05};
		gbl_panelContentParent.rowWeights = new double[]{0.95,0.05};
		panelContentParent.setLayout(gbl_panelContentParent);
		
		panelCnt = new JPanel();
		panelCnt.setOpaque(false);
		GridBagConstraints gbc_panelCnt = new GridBagConstraints();
		gbc_panelCnt.fill = GridBagConstraints.BOTH;
		gbc_panelCnt.gridx = 1;
		gbc_panelCnt.gridy = 0;
		panelContentParent.add(panelCnt, gbc_panelCnt);
		
		panelClose = new CustomPanel("/icons/noa_en/closepanel.png");
		GroupLayout gl_panelMainTop = new GroupLayout(panelMainTop);
		gl_panelMainTop.setHorizontalGroup(
			gl_panelMainTop.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMainTop.createSequentialGroup()
					.addContainerGap(50, Short.MAX_VALUE)
					.addComponent(panelClose, 74, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(50))
		);
		gl_panelMainTop.setVerticalGroup(
			gl_panelMainTop.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMainTop.createSequentialGroup()
					.addContainerGap(2, Short.MAX_VALUE)
					.addComponent(panelClose, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
		);
		panelMainTop.setLayout(gl_panelMainTop);
		getContentPane().setLayout(groupLayout);
	
		initEvents();
	}
	
	public Container getLogoPanel(){
		return panelLogo;
	}
	
	public Container getContentPanel(){
		return panelCnt;
	}
	
	public Container getClosePanel(){
		return panelClose;
	}

	private void initEvents() {
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
}
