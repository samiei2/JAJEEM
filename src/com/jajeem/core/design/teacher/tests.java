package com.jajeem.core.design.teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println(new File("vlc-win64",
				"vlc.exe").exists());
		Runtime.getRuntime().exec(
				Paths.get(".").toAbsolutePath().normalize().toString()+"/vlc-win64/vlc.exe");
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