package com.jajeem.core.design.teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jscroll.JScrollDesktopPane;

import com.alee.laf.WebLookAndFeel;

public class tests extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final tests frame = new tests();
					frame.setVisible(true);
					
					final String[] pathes = new String[]{
							"D:\\wall\\1024x crap\\iranload.co (249).jpg",
							"D:\\wall\\1024x crap\\iranload.co (270).jpg",
							"D:\\wall\\1024x crap\\iranload.co (285).jpg",
							"D:\\wall\\1024x crap\\iranload.co (295).jpg",
							"D:\\wall\\1024x crap\\iranload.co (154).jpg",
							"D:\\wall\\1024x crap\\iranload.co (382).jpg",
							"D:\\wall\\1024x crap\\iranload.co (385).jpg",
							};
					UIManager.setLookAndFeel(WebLookAndFeel.class
					.getCanonicalName());
					final JInternalFrame inf = new JInternalFrame();
					inf.setSize(120, 120);
					JLabel lbl = new JLabel();
					lbl.setIcon(new ImageIcon(pathes[0]));
					inf.setVisible(true);
					inf.add(lbl);
					frame.scrollDesktopPane.add(inf);
					
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							for (int i = 0; i < pathes.length; i++) {
								inf.getContentPane().removeAll();
								JLabel lbl = new JLabel();
								lbl.setIcon(new ImageIcon(pathes[i]));
								inf.add(lbl);
								inf.revalidate();
								inf.repaint();
								
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});
					t.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	JPanel panel;
	private JScrollDesktopPane scrollDesktopPane;
	public tests() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		contentPane.add(panel, BorderLayout.CENTER);
		
		scrollDesktopPane = new JScrollDesktopPane();
		panel.add(scrollDesktopPane, BorderLayout.CENTER);
	}
}

class TestPanel extends JPanel{
	private BufferedImage screenImage = null;
	private static BufferedImage dummyImage = null;
	
	public TestPanel(BufferedImage screenImage2) {
		screenImage = screenImage2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(screenImage!=null)
			System.out.println(screenImage);
		else
			System.out.println("Null");
		Graphics g2d = g.create();
		if(screenImage!=null)
			g2d.drawImage(screenImage, 0, 0, 120, 120, this);
		g2d.dispose();
		super.paintComponent(g);
	}
}