package com.jajeem.filemanager.design;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.jajeem.util.CustomPanel;
import com.jajeem.util.WindowResizeAdapter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class FileManagerBase extends JFrame{
	private CustomFileButton customFileCloseButton;
	private CustomPanel panel;
	int posX,posY;
	JFrame mainFrame;
	private JPanel panelContent;
	public FileManagerBase() {
		WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
		WindowResizeAdapter.install(this, SwingConstants.SOUTH);
		WindowResizeAdapter.install(this, SwingConstants.EAST);
		mainFrame = this;
		setUndecorated(true);
		setBackground(new Color(0,0,0,0));
		
		panel = new CustomPanel("/icons/noa_en/fileMainPanel.png");
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
				.addComponent(panel_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
		);
		
		panelContent = new JPanel();
		panelContent.setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelContent, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelContent, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		customFileCloseButton = new CustomFileButton("/icons/noa_en/fileclosebutton.png");
		customFileCloseButton.setUndecorated(true);
		customFileCloseButton.setText("");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(357, Short.MAX_VALUE)
					.addComponent(customFileCloseButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(40))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(customFileCloseButton, GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
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
	
	public JButton getCloseButton(){
		return customFileCloseButton;
	}
}
