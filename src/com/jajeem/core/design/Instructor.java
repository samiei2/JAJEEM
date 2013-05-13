package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;

import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.command.model.StartUpCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;

public class Instructor implements SwingConstants {

	private WebFrame frmJajeemProject;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructor window = new Instructor();
					// window.frmJajeemProject.setUndecorated(true);
					// window.frmJajeemProject.pack();
					window.frmJajeemProject.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public Instructor() throws NumberFormatException, Exception {
		try {
			// Setting up WebLookAndFeel style
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			new Config();
			PropertyConfigurator.configure("conf/log4j.conf");

		} catch (Throwable e) {
		}

		initialize();
		networkSetup();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {
		WebLookAndFeel.setDecorateFrames(true);
		frmJajeemProject = new WebFrame();
		frmJajeemProject.setRound(0);
		frmJajeemProject.setTitle("JaJeem Project");
		frmJajeemProject.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Instructor.class.getResource("/menubar/jajeem.jpg")));
		frmJajeemProject.setBounds(200, 100, 850, 600);
		frmJajeemProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WebPanel panel = new WebPanel();
		panel = createPanel();
		frmJajeemProject.getContentPane().add(panel);
		// frmJajeemProject.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void networkSetup() throws NumberFormatException, Exception {
		int port = Integer.parseInt(Config.getParam("port"));
		String broadcastingIp = Config.getParam("broadcastingIp");
		ServerService serverService = new ServerService();
		StartUpCommand cmd = new StartUpCommand(broadcastingIp, port,
				InetAddress.getLocalHost().getHostAddress());
		serverService.send(cmd);

		ClientService clientService = new ClientService(
				Config.getParam("broadcastingIp"), Integer.parseInt(Config
						.getParam("serverPort")));
		clientService.start();
	}

	private WebPanel createPanel() throws Exception {

		WebPanel panel = new WebPanel();
		panel.setUndecorated(false);
		panel.setLayout(new TableLayout(new double[][] {
				{ TableLayout.PREFERRED, TableLayout.FILL,
						TableLayout.PREFERRED },
				{ TableLayout.PREFERRED, TableLayout.FILL,
						TableLayout.PREFERRED } }));
		panel.setWebColored(false);

		WebPanel northPanel = new WebPanel();
		northPanel.setDrawSides(false, false, true, true);
		northPanel.setRound(0);
		setupPanel(northPanel, NORTH);
		panel.add(northPanel, "0,0,1,0");

		WebPanel southPanel = new WebPanel();
		southPanel.setDrawSides(true, true, false, false);
		southPanel.setRound(0);
		setupPanel(southPanel, SOUTH);
		panel.add(southPanel, "1,2,2,2");

		/*
		 * West panel for now not shown.
		 * 
		 * WebPanel leadingPanel = new WebPanel();
		 * leadingPanel.setDrawSides(true, false, false, true);
		 * leadingPanel.setRound(0); setupPanel(leadingPanel, WEST);
		 * panel.add(leadingPanel, "0,1,0,2");
		 */

		WebPanel trailingPanel = new WebPanel();
		trailingPanel.setDrawSides(false, true, true, false);
		trailingPanel.setRound(0);
		setupPanel(trailingPanel, EAST);
		WebScrollPane webScrollPane = new WebScrollPane(trailingPanel, false);
		webScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		final WebCollapsiblePane rPanel = new WebCollapsiblePane("",
				webScrollPane);
		rPanel.setCollapseIcon(new ImageIcon("icons/angle-right.png"));
		rPanel.setExpandIcon(new ImageIcon("icons/angle-left.png"));
		rPanel.setRotateStateIcon(false);

		rPanel.setExpanded(true);
		rPanel.setTitlePanePostion(SwingConstants.LEFT);
		panel.add(rPanel, "2,0,2,1");

		WebPanel centerPanel = new WebPanel();
		setupPanel(centerPanel, CENTER);
		WebScrollPane webScrollPaneCenter = new WebScrollPane(centerPanel, false);
		webScrollPaneCenter
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(webScrollPaneCenter, "1,1");

		return panel;
	}

	private void setupPanel(WebPanel panel, int location) throws Exception {

		panel.setUndecorated(false);
		panel.setMargin(new Insets(1, 1, 1, 1));
		panel.setRound(0);

		switch (location) {
		case NORTH: {
			panel = InstructorTop.createPanel(panel);

			break;
		}

		case SOUTH: {
			
			ImageIcon imgToolTip = new ImageIcon("icons/menubar/tooltip.png");
			TooltipManager.setDefaultDelay(1000);
			
			BufferedImage myPicture = ImageIO
					.read(new File("icons/buttom.jpg"));
			WebLabel picLabel = new WebLabel(new ImageIcon(myPicture));
			TooltipManager.setTooltip(picLabel, imgToolTip,
					"Jajeem is an Iranian handicraft, mainly made by nomads in rural areas of Iran.", TooltipWay.up);
			picLabel.setOpaque(false);
			panel.add(picLabel);

			break;
		}

		case WEST: {
			// panel.add(new WebVerticalLabel("West panel", WebLabel.CENTER,
			// false));

			break;
		}

		case EAST: {
			InstructorRight.iconsPath = "icons/applications_style1";
			panel = InstructorRight.createPanel(panel);

			break;
		}

		case CENTER: {
			panel = InstructorCenter.createPanel(panel);

			break;
		}
		}
	}
}
