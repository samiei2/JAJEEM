package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
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
import com.jajeem.command.model.StudentLogoutCommand;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.filemanager.client.ClientFileManagerMain;
import com.jajeem.message.design.Chat;
import com.jajeem.message.design.MessageSend;
import com.jajeem.recorder.design.Recorder;
import com.jajeem.recorder.design.RecorderStudent;
import com.jajeem.share.service.VNCCaptureService;
import com.jajeem.util.Config;
import com.jajeem.util.CustomButton;
import com.jajeem.util.CustomButtonStudent;
import com.jajeem.util.CustomStudentFrame;
import com.jajeem.util.i18n;
import com.sun.awt.AWTUtilities;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import com.alee.laf.button.WebButton;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Student {

	private static JFrame frmJajeemProject;
	private static JFrame mainFram;
	private static CustomButtonStudent intercomButton;

	private static List<Chat> chatList = new ArrayList<Chat>();

	private static AVReceiveOnly receiverOnly;
	private static AVSendOnly sendOnly;
	private static AVTransmit2 transmitter;

	private static VNCCaptureService vncViewer;

	private static com.jajeem.core.model.Student studentModel;
	private static boolean granted = false;

	Font font = new Font("Arial", Font.BOLD, 20);
	private static CustomButtonStudent recordButtonStatic;
	private Timer timer;
	private static int countdown = 30000;

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

		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				countdown -= 1000;
				if (countdown <= 0) {
					Student.getFrmJajeemProject().setVisible(false);
				}
			}
		});
		timer.setInitialDelay(0);
		timer.start();

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {
		WebLookAndFeel.setDecorateFrames(true);
		JFrame customStudentFrame = new JFrame();
		setFrmJajeemProject(customStudentFrame);

		mainFram = getFrmJajeemProject();
		mainFram.setResizable(false);
		mainFram.setAlwaysOnTop(true);
		mainFram.setTitle(i18n.getParam("Classmate"));
		// mainFram.setUndecorated(true);
		// System.setProperty("sun.java2d.noddraw", "true");
		// AWTUtilities.setWindowOpaque(mainFram, false);
		// frmJajeemProject.setIconImage(Toolkit.getDefaultToolkit().getImage(
		// Student.class.getResource("/icons/menubar/jajeem.jpg")));
		getFrmJajeemProject().setSize(340, 420);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = (int) rect.getMaxX() - getFrmJajeemProject().getWidth();
		int y = (int) ((rect.getMaxY() - getFrmJajeemProject().getHeight()));
		getFrmJajeemProject().setLocation(x, y);
		
		WebPanel panel = new WebPanel();
		panel.setBackground(Color.RED);
		panel.setUndecorated(true);
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(
				customStudentFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 336, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		customStudentFrame.getContentPane().setLayout(groupLayout);

		

		WebPanel trailingPanel = new WebPanel();
		trailingPanel.setDrawSides(false, true, true, false);

		ImageIcon imgToolTip = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/menubar/tooltip.png"))));
		TooltipManager.setDefaultDelay(500);

		ImageIcon imgMessage = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/chat.png"))));

		ImageIcon imgFile = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/filesharing.png"))));

		ImageIcon videoFile = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/movieplayer.png"))));

		ImageIcon imgIntercom = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/chat.png"))));

		ImageIcon recordIntercom = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/recording.png"))));

		ImageIcon accountImage = new ImageIcon(ImageIO.read(Student.class
				.getResourceAsStream(("/icons/noa_en/account.png"))));

		WebPanel panel_1 = new WebPanel();
		panel_1.setOpaque(false);
		panel_1.setUndecorated(true);
		panel_1.setWebColored(false);
		CustomButtonStudent fileButton = new CustomButtonStudent(imgFile);
		fileButton.setUndecorated(true);
		fileButton.setHorizontalAlignment(SwingConstants.LEADING);
		fileButton.setIconTextGap(20);
		fileButton.setFont(font);
		fileButton.setForeground(Color.WHITE);
		fileButton.setText("File Manager");
		TooltipManager.setTooltip(fileButton, imgToolTip,
				i18n.getParam("Send a file to your instructor."),
				TooltipWay.down);
		CustomButtonStudent videoButton = new CustomButtonStudent(videoFile);
		videoButton.setUndecorated(true);
		videoButton.setHorizontalAlignment(SwingConstants.LEADING);
		videoButton.setIconTextGap(20);
		videoButton.setFont(font);
		videoButton.setForeground(Color.WHITE);
		videoButton.setText("Movie Player");
		TooltipManager.setTooltip(videoButton, imgToolTip,
				i18n.getParam("Video Player"), TooltipWay.down);
		intercomButton = new CustomButtonStudent(imgIntercom);
		intercomButton.setUndecorated(true);
		intercomButton.setHorizontalAlignment(SwingConstants.LEADING);
		intercomButton.setIconTextGap(20);
		intercomButton.setFont(font);
		intercomButton.setForeground(Color.WHITE);
		intercomButton.setText("Contact Instructor");
		TooltipManager.setTooltip(intercomButton, imgToolTip,
				i18n.getParam("Call Instructor."), TooltipWay.down);
		final CustomButtonStudent recordButton = new CustomButtonStudent(
				recordIntercom);
		recordButton.setUndecorated(true);
		recordButton.setHorizontalAlignment(SwingConstants.LEADING);
		recordButton.setIconTextGap(20);
		recordButton.setFont(font);
		recordButton.setForeground(Color.WHITE);
		recordButton.setText("Recording");
		TooltipManager.setTooltip(recordButton, imgToolTip,
				i18n.getParam("Recorder"), TooltipWay.down);
		setRecordButtonStatic(recordButton);
		CustomButtonStudent accountButton = new CustomButtonStudent(
				accountImage);
		accountButton.setUndecorated(true);
		accountButton.setHorizontalAlignment(SwingConstants.LEADING);
		accountButton.setIconTextGap(20);
		accountButton.setFont(font);
		accountButton.setForeground(Color.WHITE);
		accountButton.setText("Account Manager");
		TooltipManager.setTooltip(accountButton, imgToolTip,
				i18n.getParam("My Account"), TooltipWay.down);
		
		JLabel lblNoavaran = new JLabel("Noavaran Co.");
		lblNoavaran.setForeground(Color.GRAY);
		
		JLabel lblWwwnoavaranengcom = new JLabel("www.noavaran-eng.com");
		lblWwwnoavaranengcom.setForeground(Color.GRAY);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 326, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNoavaran)
							.addPreferredGap(ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
							.addComponent(lblWwwnoavaranengcom)
							.addGap(10))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNoavaran)
						.addComponent(lblWwwnoavaranengcom))
					.addContainerGap())
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(videoButton, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)
					.addGap(20))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(recordButton, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(accountButton, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(intercomButton, GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(23)
					.addComponent(intercomButton, GroupLayout.PREFERRED_SIZE, 54, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fileButton, GroupLayout.PREFERRED_SIZE, 54, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(videoButton, GroupLayout.PREFERRED_SIZE, 54, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(recordButton, GroupLayout.PREFERRED_SIZE, 54, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(accountButton, GroupLayout.PREFERRED_SIZE, 54, Short.MAX_VALUE)
					.addGap(50))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

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
					intercomButton.setEnabled(false);
					intercomButton.setText("Wait for teacher...");
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

				RecorderStudent recorder = new RecorderStudent(
						new ArrayList<String>(), false, false);
				recorder.setLocationRelativeTo(getFrmJajeemProject());
				recorder.setVisible(true);
				recordButton.setEnabled(false);
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

		// getFrmJajeemProject().getContentPane().add(panel);

		getFrmJajeemProject().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		getFrmJajeemProject().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				countdown = 30000;
				super.windowClosed(e);
			}
		});

		getFrmJajeemProject().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				countdown = 30000;
				try {
					StudentLogoutCommand cmd = new StudentLogoutCommand(
							InetAddress.getLocalHost().getHostAddress(),
							StudentLogin.getServerIp(), Integer.parseInt(Config
									.getParam("serverPort")));
					cmd.setSenderName(System.getProperty("user.name"));
					StudentLogin.getServerService().send(cmd);
				} catch (Exception ex) {
				}
				super.componentHidden(e);
			}
		});
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

	public static CustomButtonStudent getRecordButtonStatic() {
		return recordButtonStatic;
	}

	public static void setRecordButtonStatic(
			CustomButtonStudent recordButtonStatic) {
		Student.recordButtonStatic = recordButtonStatic;
	}

	public static int getCountdown() {
		return countdown;
	}

	public static void setCountdown(int countdown) {
		Student.countdown = countdown;
	}
}
