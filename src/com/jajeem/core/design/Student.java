package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jitsi.examples.AVTransmit2;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.TooltipWay;
import com.jajeem.command.model.IntercomRequestCommand;
import com.jajeem.command.model.RequestCourseListCommand;
import com.jajeem.command.model.StopIntercomCommand;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.ClientFileManagerMain;
import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.recorder.design.Recorder;
import com.jajeem.recorder.design.RecorderStudent;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.i18n;
import com.sun.awt.AWTUtilities;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Student {

	private static WebDialog frmJajeemProject;
	private static WebDialog mainFram;
	private static CustomButton intercomButton;

	private static List<Chat> chatList = new ArrayList<Chat>();

	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static AVTransmit2 transmitter;

	private static VNCCaptureService vncViewer;

	private static com.jajeem.core.model.Student studentModel;
	private static boolean granted = false;

	Font font = new Font("Arial", Font.BOLD, 20);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student window = new Student();
					window.getFrmJajeemProject().setVisible(true);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
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
			new i18n();

			setTransmitter(new AVTransmit2("10000", "", "5000"));
			setReceiverOnly(new AVReceiveOnly("10010", "", "5010"));

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
		setFrmJajeemProject(new WebDialog());
		
		mainFram = getFrmJajeemProject();
		mainFram.setResizable(false);
		mainFram.setAlwaysOnTop(true);
		mainFram.setTitle(i18n.getParam("Classmate"));
//		mainFram.setUndecorated(true);
//		System.setProperty("sun.java2d.noddraw", "true");
//	    AWTUtilities.setWindowOpaque(mainFram, false);
		// frmJajeemProject.setIconImage(Toolkit.getDefaultToolkit().getImage(
		// Student.class.getResource("/icons/menubar/jajeem.jpg")));
		getFrmJajeemProject().setBounds(0, 400, 311, 460);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - getFrmJajeemProject().getWidth();
		int y = (int) ((rect.getMaxY() - getFrmJajeemProject().getHeight()));
		getFrmJajeemProject().setLocation(x, y - 200);
		WebPanel panel = new WebPanel();
		panel = createPanel();
		getFrmJajeemProject().getContentPane().add(panel);

		getFrmJajeemProject().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	private void networkSetup() throws NumberFormatException, Exception {

	}

	private WebPanel createPanel() throws Exception {

		WebPanel panel = new WebPanel();
		panel.setUndecorated(false);
		panel.setWebColored(false);

		WebPanel trailingPanel = new WebPanel();
		trailingPanel.setDrawSides(false, true, true, false);

		ImageIcon imgToolTip = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/menubar/tooltip.png"))));
		TooltipManager.setDefaultDelay(500);

		ImageIcon imgMessage = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/noa_en/chat.png"))));
		CustomButton messageButton = new CustomButton(imgMessage);
		messageButton.setUndecorated(true);
		messageButton.setHorizontalAlignment(SwingConstants.LEADING);
		messageButton.setIconTextGap(20);
		messageButton.setFont(font);
		messageButton.setForeground(Color.WHITE);
		messageButton.setText("Call Instructor");
		TooltipManager.setTooltip(messageButton, imgToolTip,
				i18n.getParam("Send a message to your instructor."),
				TooltipWay.down);

		ImageIcon imgFile = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/filesharing.png"))));
		CustomButton fileButton = new CustomButton(imgFile);
		fileButton.setUndecorated(true);
		fileButton.setHorizontalAlignment(SwingConstants.LEADING);
		fileButton.setIconTextGap(20);
		fileButton.setFont(font);
		fileButton.setForeground(Color.WHITE);
		fileButton.setText("File Manager");
		TooltipManager.setTooltip(fileButton, imgToolTip,
				i18n.getParam("Send a file to your instructor."),
				TooltipWay.down);

		ImageIcon videoFile = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/movieplayer.png"))));
		CustomButton videoButton = new CustomButton(videoFile);
		videoButton.setUndecorated(true);
		videoButton.setHorizontalAlignment(SwingConstants.LEADING);
		videoButton.setIconTextGap(20);
		videoButton.setFont(font);
		videoButton.setForeground(Color.WHITE);
		videoButton.setText("Movie Player");
		TooltipManager.setTooltip(videoButton, imgToolTip,
				i18n.getParam("Video Player"), TooltipWay.down);

		ImageIcon imgIntercom = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/noa_en/chat.png"))));
		intercomButton = new CustomButton(imgIntercom);
		intercomButton.setUndecorated(true);
		intercomButton.setHorizontalAlignment(SwingConstants.LEADING);
		intercomButton.setIconTextGap(20);
		intercomButton.setFont(font);
		intercomButton.setForeground(Color.WHITE);
		intercomButton.setText("Chat");
		TooltipManager.setTooltip(intercomButton, imgToolTip,
				i18n.getParam("Call Instructor."), TooltipWay.down);

		ImageIcon recordIntercom = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/noa_en/recording.png"))));
		CustomButton recordButton = new CustomButton(recordIntercom);
		recordButton.setUndecorated(true);
		recordButton.setHorizontalAlignment(SwingConstants.LEADING);
		recordButton.setIconTextGap(20);
		recordButton.setFont(font);
		recordButton.setForeground(Color.WHITE);
		recordButton.setText("Recording");
		TooltipManager.setTooltip(recordButton, imgToolTip,
				i18n.getParam("Recorder"), TooltipWay.down);

		ImageIcon accountImage = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/account.png"))));
		CustomButton accountButton = new CustomButton(accountImage);
		accountButton.setUndecorated(true);
		accountButton.setHorizontalAlignment(SwingConstants.LEADING);
		accountButton.setIconTextGap(20);
		accountButton.setFont(font);
		accountButton.setForeground(Color.WHITE);
		accountButton.setText("Account Manager");
		TooltipManager.setTooltip(accountButton, imgToolTip,
				i18n.getParam("My Account"), TooltipWay.down);

		WebPanel panel2 = new WebPanel();
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel2.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 305, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel2.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(messageButton, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addComponent(videoButton, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addComponent(intercomButton, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addComponent(recordButton, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addComponent(accountButton, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(messageButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(videoButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(intercomButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(recordButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(accountButton, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addGap(28))
		);
		panel.setLayout(gl_panel);
		panel2.setLayout(gl_panel2);

		messageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MessageSend.main(new String[] { StudentLogin.getServerIp(),
							Config.getParam("serverPort") });
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
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
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});

		videoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final Process proc;
					System.out.println(new File("util/", "videoplayer.jar")
							.exists());
					proc = Runtime.getRuntime().exec(
							"java -jar videoplayer.jar \"\" true", null,
							new File("util/"));
					// Then retrieve the process output
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								BufferedReader in = new BufferedReader(
										new InputStreamReader(proc
												.getInputStream()));
								String line = null;
								while ((line = in.readLine()) != null) {
									System.out.println(line);
								}
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
							}
						}
					}).start();
				} catch (IOException ex) {
					JajeemExcetionHandler.logError(ex);
					ex.printStackTrace();
				}
			}
		});

		intercomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (Student.getTransmitter().isTransmitting()) {
						intercomButton.setIcon(new ImageIcon(
								ImageIO.read(Student.class
										.getResourceAsStream(("/icons/noa/right_panel/mic_student.png")))));
						getTransmitter().stop();

						StopIntercomCommand sic = new StopIntercomCommand(
								InetAddress.getLocalHost().getHostAddress(),
								StudentLogin.getServerIp(),
								Integer.parseInt(Config.getParam("serverPort")));
						StudentLogin.getServerService().send(sic);
					} else {
						IntercomRequestCommand irc = new IntercomRequestCommand(
								InetAddress.getLocalHost().getHostAddress(),
								StudentLogin.getServerIp(),
								Integer.parseInt(Config.getParam("serverPort")),
								studentModel);
						StudentLogin.getServerService().send(irc);
					}
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});

		recordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Enabling dialog decoration
				boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
				WebLookAndFeel.setDecorateDialogs(true);

				RecorderStudent recorder = new RecorderStudent(new ArrayList<String>(),
						false, false);
				recorder.setLocationRelativeTo(getFrmJajeemProject());
				recorder.setVisible(true);

				// Restoring frame decoration option
				WebLookAndFeel.setDecorateDialogs(decorateFrames);
			}
		});

		accountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RequestCourseListCommand gcc;
				try {
					gcc = new RequestCourseListCommand(InetAddress
							.getLocalHost().getHostAddress(), StudentLogin
							.getServerIp(), Integer.parseInt(Config
							.getParam("serverPort")), studentModel);
					StudentLogin.getServerService().send(gcc);
				} catch (Exception e1) {
					JajeemExcetionHandler.logError(e1);
					e1.printStackTrace();
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
		if (transmitter == null) {
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

	public static WebDialog getMainFram() {
		return mainFram;
	}

	public void setMainFram(WebDialog frmJajeemProject) {
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

	public static void setIntercomButtonText(String text) {
		intercomButton.setText(text);
	}

	public static void setIntercomButtonStop() throws IOException {
		intercomButton
				.setIcon(new ImageIcon(
						ImageIO.read(Student.class
								.getResourceAsStream(("/icons/noa/right_panel/stop_recording.png")))));
		intercomButton.setEnabled(false);

	}
	
	public static void setIntercomButtonStart() throws IOException {
		intercomButton
				.setIcon(new ImageIcon(
						ImageIO.read(Student.class
								.getResourceAsStream(("/icons/applications_style1/call_teacher.png")))));
		intercomButton.setEnabled(true);
	}

	public static WebDialog getFrmJajeemProject() {
		return frmJajeemProject;
	}

	public static void setFrmJajeemProject(WebDialog frmJajeemProject) {
		Student.frmJajeemProject = frmJajeemProject;
	}

	public static com.jajeem.core.model.Student getStudentModel() {
		return studentModel;
	}

	public static void setStudentModel(
			com.jajeem.core.model.Student studentModel) {
		Student.studentModel = studentModel;
	}

	public static boolean isGranted() {
		return granted;
	}

	public static void setGranted(boolean granted) {
		Student.granted = granted;
	}
}
