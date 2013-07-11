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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jitsi.examples.AVTransmit2;

//import org.apache.log4j.PropertyConfigurator;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.filemanager.client.ClientFileManagerMain;
import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;

public class Student {

	private JFrame frmJajeemProject;
	private static JFrame mainFram;

	private static List<Chat> chatList = new ArrayList<Chat>();

	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static AVTransmit2 transmitter;
	
	private static boolean black;
	
	private static VNCCaptureService vncViewer;

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
			
			// initiate Libjitsi for intercom
//			LibJitsi.start();
			setTransmitter(new AVTransmit2("10000", "", "5000"));
//			setReceiver(new AVReceive2("10000", "", "5000"));

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
		mainFram = frmJajeemProject;
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

		frmJajeemProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		ImageIcon imgFile = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/com/jajeem/images/file_upload.png"))));
		WebButton fileButton = new WebButton(imgFile);
		TooltipManager.setTooltip(fileButton, imgToolTip,
				"Send a file to your instructor.", TooltipWay.down);
		panel.add(fileButton);
		
		ImageIcon videoFile = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/com/jajeem/images/MPlayer.png"))));
		WebButton videoButton = new WebButton(videoFile);
		TooltipManager.setTooltip(videoButton, imgToolTip,
				"Video Player", TooltipWay.down);
		panel.add(videoButton);

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
					e.printStackTrace();
				}
			}
		});
		
		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ClientFileManagerMain main = new ClientFileManagerMain();
					main.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		videoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final Process proc;
					System.out.println(new File("util/",
							"videoplayer.jar").exists());
					proc = Runtime.getRuntime().exec(
							"java -jar videoplayer.jar", null,
							new File("util/"));
					// Then retrieve the process output
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								BufferedReader in = new BufferedReader(  
			                            new InputStreamReader(proc.getInputStream()));  
							        String line = null;  
							        while ((line = in.readLine()) != null) {  
							            System.out.println(line);  
							        }  
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					}).start();
				} catch (IOException ex) {
					ex.printStackTrace();
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

	public static AVTransmit2 getTransmitter() throws Exception {
		if(transmitter== null){
			transmitter = new AVTransmit2("10000", "", "5000");
		}
		return transmitter;
	}

	public static void setTransmitter(AVTransmit2 transmitter) throws Exception {
		Student.transmitter = transmitter;
	}

	public static VNCCaptureService getVncViewer() {
		return vncViewer;
	}

	public static void setVncViewer(VNCCaptureService vncViewer) {
		Student.vncViewer = vncViewer;
	}

	public static JFrame getMainFram() {
		return mainFram;
	}

	public void setMainFram(JFrame frmJajeemProject) {
		this.mainFram = frmJajeemProject;
	}

	public static AVReceiveOnly getReceiverOnly() {
		return receiverOnly;
	}

	public static void setReceiverOnly(AVReceiveOnly receiverOnly) {
		Student.receiverOnly = receiverOnly;
	}

	public static AVSendOnly getSendOnly() {
		return sendOnly;
	}

	public static void setSendOnly(AVSendOnly sendOnly) {
		Student.sendOnly = sendOnly;
	}
}
