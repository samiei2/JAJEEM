package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.service.ClientService;
import com.jajeem.util.Config;

public class Student {

	private JFrame frmJajeemProject;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student window = new Student();
					window.frmJajeemProject.setVisible(true);
					window.frmJajeemProject.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public Student() throws NumberFormatException, Exception {
		try {
			// Setting up WebLookAndFeel style
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());
			
			new Config();
			PropertyConfigurator.configure("conf/log4j.conf");
			
		} catch (Throwable e) {
			// Something went wrong
		}
		
		initialize();
		networkSetup();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJajeemProject = new JFrame();
		frmJajeemProject.setTitle("Jajeem Project");
		frmJajeemProject.setIconImage(Toolkit.getDefaultToolkit().getImage(Student.class.getResource("/menubar/class.png")));
		frmJajeemProject.setBounds(0, 400, 150, 400);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - frmJajeemProject.getWidth();
		int y = (int) ((rect.getMaxY() - frmJajeemProject.getHeight()));
		frmJajeemProject.setLocation(x, y-200);
		
		WebPanel panel = new WebPanel();
		panel = createPanel();
		frmJajeemProject.getContentPane().add(panel);
		
		frmJajeemProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void networkSetup() throws NumberFormatException, Exception {
		ClientService clientService = new ClientService(Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
		clientService.start();
	}

	private WebPanel createPanel() {
		
		new Config();
		
		WebPanel panel = new WebPanel();
		panel.setUndecorated(false);
		panel.setWebColored(false);

		WebPanel trailingPanel = new WebPanel();
		trailingPanel.setDrawSides(false, true, true, false);
		

		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);

		ImageIcon imgMonitor = new ImageIcon("icons/applications/monitor.png");
		WebButton monitorButton = new WebButton("Monitor", new ImageIcon(imgMonitor.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		panel.add(monitorButton);
		
		ImageIcon imgIntercom = new ImageIcon("icons/applications/intercom.png");
		WebButton intercomButton = new WebButton("Intercom", new ImageIcon(imgIntercom.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		panel.add(intercomButton);
		
		ImageIcon imgMessage = new ImageIcon("icons/applications/message.png");
		WebButton messageButton = new WebButton("Message", new ImageIcon(imgMessage.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		panel.add(messageButton);

		
		WebPanel panel2 = new WebPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(panel, BorderLayout.NORTH);
		
		
		WebPanel southPanel = new WebPanel();
		southPanel.setDrawSides(true, true, false, false);
		WebLabel webLabel = new WebLabel(
				"Teanab Institute: English Advance 4, Prof. Samiei, Tuesdays 17-19");
		webLabel.setToolTipText("Teanab Institute: English Advance 4, Prof. Samiei, Tuesdays 17-19");
		webLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		webLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		southPanel.add(webLabel);
		panel2.add(southPanel);

		return panel2;
	}

}
