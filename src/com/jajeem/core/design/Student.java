package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jitsi.examples.AVReceive2;
import org.jitsi.service.libjitsi.LibJitsi;

//import org.apache.log4j.PropertyConfigurator;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.util.Config;

public class Student {

	private JFrame frmJajeemProject;

	private static List<Chat> chatList = new ArrayList<Chat>();

	public static AVReceive2 receiver;

	private static boolean black;

	public static boolean isBlack() {
		return black;
	}

	public static void setBlack(boolean black) {
		Student.black = black;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student window = new Student();
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
	public Student() throws NumberFormatException, Exception {
		try {
			// Setting up WebLookAndFeel style
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			new Config();
			
			setReceiver(new AVReceive2("10000", "", "5000"));

		} catch (Throwable e) {
			// Something went wrong
		}

		initialize();
		networkSetup();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		WebLookAndFeel.setDecorateFrames(true);
		frmJajeemProject = new WebFrame();
		frmJajeemProject.setResizable(false);
		frmJajeemProject.setUndecorated(true);
		frmJajeemProject.setAlwaysOnTop(true);
		frmJajeemProject.setTitle("iCalabo");
		frmJajeemProject.setIconImage(Toolkit.getDefaultToolkit().getImage(
				Student.class.getResource("/icons/menubar/jajeem.jpg")));
		frmJajeemProject.setBounds(0, 400, 280, 400);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - frmJajeemProject.getWidth();
		int y = (int) ((rect.getMaxY() - frmJajeemProject.getHeight()));
		frmJajeemProject.setLocation(x, y - 200);
		WebPanel panel = new WebPanel();
		panel = createPanel();
		frmJajeemProject.getContentPane().add(panel);

		frmJajeemProject.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	private void networkSetup() throws NumberFormatException, Exception {

	}

	private WebPanel createPanel() throws IOException {

		new Config();

		WebPanel panel = new WebPanel();
		panel.setUndecorated(false);
		panel.setWebColored(false);

		WebPanel trailingPanel = new WebPanel();
		trailingPanel.setDrawSides(false, true, true, false);

		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);

		ImageIcon imgToolTip = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/menubar/tooltip.png"))));
		TooltipManager.setDefaultDelay(1000);

		ImageIcon imgMessage = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/applications_style1/message_text.png"))));
		WebButton messageButton = new WebButton(imgMessage);
		TooltipManager.setTooltip(messageButton, imgToolTip,
				"Send a message to your instructor.", TooltipWay.down);
		panel.add(messageButton);

		WebPanel panel2 = new WebPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(panel, BorderLayout.NORTH);

		WebPanel southPanel = new WebPanel();
		southPanel.setDrawSides(true, true, false, false);

		BufferedImage myPicture = ImageIO.read(Instructor.class
				.getResourceAsStream(("/icons/buttom.jpg")));
		WebLabel picLabel = new WebLabel(new ImageIcon(myPicture));
		TooltipManager
				.setTooltip(
						picLabel,
						imgToolTip,
						"Jajeem is an Iranian handicraft, mainly made by nomads in rural areas of Iran.",
						TooltipWay.up);
		southPanel.add(picLabel);
		panel2.add(southPanel, BorderLayout.SOUTH);

		messageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MessageSend.main(new String[] { StudentLogin.getServerIp(),
							Config.getParam("serverPort") });
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return panel2;
	}

	public static List<Chat> getChatList() {
		return chatList;
	}

	public static void setChatList(List<Chat> chatList) {
		Student.chatList = chatList;
	}

	public static AVReceive2 getReceiver() {
		if(receiver == null){
			try {
				receiver = new AVReceive2("10000", "", "5000");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return receiver;
	}

	public void setReceiver(AVReceive2 receiver) {
		this.receiver = receiver;
	}
}
