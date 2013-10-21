package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jitsi.examples.AVReceiveOnly;
import org.jitsi.examples.AVSendOnly;
import org.jitsi.examples.AVTransmit2;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
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
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;
import com.jajeem.util.i18n;

public class Student {

	private static JFrame frmJajeemProject;
	private static JFrame mainFram;
	private static WebButton intercomButton;

	private static List<Chat> chatList = new ArrayList<Chat>();

	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static AVTransmit2 transmitter;

	private static VNCCaptureService vncViewer;

	private static com.jajeem.core.model.Student studentModel;
	private static boolean granted = false;

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
		setFrmJajeemProject(new WebFrame());
		mainFram = getFrmJajeemProject();
		mainFram.setResizable(false);
		mainFram.setAlwaysOnTop(true);
		mainFram.setTitle(i18n.getParam("Classmate"));
		// frmJajeemProject.setIconImage(Toolkit.getDefaultToolkit().getImage(
		// Student.class.getResource("/icons/menubar/jajeem.jpg")));
		getFrmJajeemProject().setBounds(0, 400, 280, 550);

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

		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);

		ImageIcon imgToolTip = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/menubar/tooltip.png"))));
		TooltipManager.setDefaultDelay(500);

		ImageIcon imgMessage = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/applications_style1/message_text.png"))));
		WebButton messageButton = new WebButton(imgMessage);
		TooltipManager.setTooltip(messageButton, imgToolTip,
				i18n.getParam("Send a message to your instructor."),
				TooltipWay.down);
		panel.add(messageButton);

		ImageIcon imgFile = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/com/jajeem/images/file_upload.png"))));
		WebButton fileButton = new WebButton(imgFile);
		TooltipManager.setTooltip(fileButton, imgToolTip,
				i18n.getParam("Send a file to your instructor."),
				TooltipWay.down);
		panel.add(fileButton);

		ImageIcon videoFile = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/com/jajeem/images/MPlayer.png"))));
		WebButton videoButton = new WebButton(videoFile);
		TooltipManager.setTooltip(videoButton, imgToolTip,
				i18n.getParam("Video Player"), TooltipWay.down);
		panel.add(videoButton);

		ImageIcon imgIntercom = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/applications_style1/call_teacher.png"))));
		intercomButton = new WebButton(imgIntercom);

		TooltipManager.setTooltip(intercomButton, imgToolTip,
				i18n.getParam("Call Instructor."), TooltipWay.down);
		panel.add(intercomButton);

		ImageIcon recordIntercom = new ImageIcon(
				ImageIO.read(Student.class
						.getResourceAsStream(("/icons/noa/right_panel/mic_student.png"))));
		WebButton recordButton = new WebButton(recordIntercom);
		TooltipManager.setTooltip(recordButton, imgToolTip,
				i18n.getParam("Recorder"), TooltipWay.down);
		panel.add(recordButton);

		ImageIcon accountImage = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa/right_panel/accounts.png"))));
		WebButton accountButton = new WebButton(accountImage);
		TooltipManager.setTooltip(accountButton, imgToolTip,
				i18n.getParam("My Account"), TooltipWay.down);
		panel.add(accountButton);

		WebPanel panel2 = new WebPanel();
		panel2.setLayout(new BorderLayout());
		panel2.add(panel, BorderLayout.NORTH);

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

				Recorder recorder = new Recorder(new ArrayList<String>(),
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

	public static JFrame getFrmJajeemProject() {
		return frmJajeemProject;
	}

	public static void setFrmJajeemProject(JFrame frmJajeemProject) {
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
