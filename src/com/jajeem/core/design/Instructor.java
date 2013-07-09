package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVTransmit2;
import org.jitsi.service.libjitsi.LibJitsi;

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
import com.jajeem.command.service.ServerServiceTimer;
import com.jajeem.core.design.InstructorCenter;
import com.jajeem.core.design.InstructorRight;
import com.jajeem.core.design.InstructorTop;
import com.jajeem.message.design.Chat;
import com.jajeem.util.Config;

public class Instructor implements SwingConstants {

	private WebFrame frmJajeemProject;

	private static ServerServiceTimer serverServiceTimer;

	private static AVTransmit2 transmitter;
	private static AVReceiveOnly receiver;

	private static List<Chat> chatList = new ArrayList<Chat>();

	static Logger logger = Logger.getLogger("Instructor.class");

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public static void main(final String[] args) throws NumberFormatException,
			Exception {
//		networkSetup();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructor window = new Instructor();
					window.getFrmJajeemProject().setVisible(true);

					window.getFrmJajeemProject().setTitle(
							"iCalabo - Prof." + (String) args[0] + " - "
									+ (String) args[1]);

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

//			new Config();

			// Start LibJitsi for first time
			LibJitsi.start();

			setTransmitter(new AVTransmit2("5000", "", "10000"));
			setReceiver(new AVReceiveOnly("10010", "", "5010"));

		} catch (Throwable e) {
		}

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {
		WebLookAndFeel.setDecorateFrames(true);
		setFrmJajeemProject(new WebFrame());
		getFrmJajeemProject().setRound(0);
		getFrmJajeemProject().setIconImage(
				Toolkit.getDefaultToolkit().getImage(
						Instructor.class
								.getResource("/icons/menubar/jajeem.jpg")));

		getFrmJajeemProject().setBounds(200, 0, 1000, 755);
		getFrmJajeemProject().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WebPanel panel = new WebPanel();
		panel = createPanel();
		getFrmJajeemProject().getContentPane().add(panel);
		// frmJajeemProject.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * Initializes network, broadcasting an start up command to network
	 */
	private static void networkSetup() throws NumberFormatException, Exception {
		serverServiceTimer = new ServerServiceTimer();
		int port = Integer.parseInt(Config.getParam("startUpPort"));
		String broadcastingIp = Config.getParam("broadcastingIp");

		StartUpCommand cmd = new StartUpCommand(InetAddress.getLocalHost()
				.getHostAddress(), broadcastingIp, port, InetAddress
				.getLocalHost().getHostAddress(),
				System.getProperty("user.name"));
		serverServiceTimer.setCmd(cmd);
		serverServiceTimer.setInterval(5000);
		serverServiceTimer.start();

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

		final WebCollapsiblePane rPanel = new WebCollapsiblePane("",
				trailingPanel);

		// rPanel.setCollapseIcon(new ImageIcon(ImageIO.read(Instructor.class
		// .getResourceAsStream("/icons/menubar/angle-right.png"))));
		//
		// rPanel.setExpandIcon(new ImageIcon(ImageIO.read(Instructor.class
		// .getResourceAsStream("/icons/menubar/angle-left.png"))));
		rPanel.setRotateStateIcon(false);

		rPanel.setExpanded(true);
		rPanel.setTitlePanePostion(SwingConstants.LEFT);
		panel.add(rPanel, "2,0,2,1");

		WebPanel centerPanel = new WebPanel();
		setupPanel(centerPanel, CENTER);
		WebScrollPane webScrollPaneCenter = new WebScrollPane(centerPanel,
				false);
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

			ImageIcon imgToolTip = new ImageIcon(ImageIO.read(Instructor.class
					.getResourceAsStream(("/icons/menubar/tooltip.png"))));
			TooltipManager.setDefaultDelay(1000);

			BufferedImage myPicture = ImageIO.read(Instructor.class
					.getResourceAsStream(("/icons/buttom.jpg")));
			WebLabel picLabel = new WebLabel(new ImageIcon(myPicture));
			TooltipManager
					.setTooltip(
							picLabel,
							imgToolTip,
							"Jajeem is an Iranian handicraft, mainly made by nomads in rural areas of Iran.",
							TooltipWay.up);
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

			InstructorRight.iconsPath = Instructor.class.getResource(
					"/icons/applications_style1/").getPath();
			panel = InstructorRight.createPanel(panel);

			break;
		}

		case CENTER: {
			panel = InstructorCenter.createPanel(panel);

			break;
		}
		}
	}

	public WebFrame getFrmJajeemProject() {
		return frmJajeemProject;
	}

	public void setFrmJajeemProject(WebFrame frmJajeemProject) {
		this.frmJajeemProject = frmJajeemProject;
	}

	public static List<Chat> getChatList() {
		return chatList;
	}

	public static void setChatList(List<Chat> chatList) {
		Instructor.chatList = chatList;
	}

	public static AVReceiveOnly getReceiver() {
		return receiver;
	}

	public static void setReceiver(AVReceiveOnly receiver) {
		Instructor.receiver = receiver;
	}

	public static AVTransmit2 getTransmitter() {
		return transmitter;
	}

	public static void setTransmitter(AVTransmit2 transmitter) {
		Instructor.transmitter = transmitter;
	}

}
