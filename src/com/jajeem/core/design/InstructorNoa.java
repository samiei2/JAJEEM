package com.jajeem.core.design;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jitsi.examples.AVReceive2;
import org.jitsi.examples.AVTransmit2;
import org.jitsi.service.libjitsi.LibJitsi;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.jajeem.command.service.ServerService;
import com.jajeem.message.design.Chat;
import com.jajeem.util.Config;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

public class InstructorNoa {

	private WebFrame frame;
	private static WebDesktopPane desktopPane;

	private static AVTransmit2 transmitter;
	private static AVReceive2 receiver;
	private static List<Chat> chatList = new ArrayList<Chat>();
	private static ServerService serverService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructorNoa window = new InstructorNoa();
					window.frame.setVisible(true);

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
	 */
	public InstructorNoa() throws Exception {

		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName());

			new Config();

			// Start LibJitsi for first time
			LibJitsi.start();
			setTransmitter(new AVTransmit2("5000", "", "10000"));
			setReceiver(new AVReceive2("10010", "", "5010"));

			InstructorNoaUtil.networkSetup();

			initialize();

		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new WebFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				InstructorNoa.class.getResource("/icons/noa/teacher.png")));
		frame.setShowMinimizeButton(false);
		frame.setTitle("iCalabo");
		frame.setShowResizeCorner(false);
		frame.getContentPane().setBackground(new Color(56, 107, 170));
		frame.setBounds(200, 0, 1045, 718);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		WebPanel centerPanel = new WebPanel();
		centerPanel.setBackground(new Color(255, 255, 255));

		WebPanel rightButtonPanel = new WebPanel();
		rightButtonPanel.setBackground(new Color(56, 107, 170));

		WebPanel bottomButtonPanel = new WebPanel();
		bottomButtonPanel.setBackground(new Color(58, 109, 175));

		WebPanel bottomLogoPanel = new WebPanel();
		bottomLogoPanel.setBackground(new Color(58, 109, 175));

		WebPanel topButtonPanel = new WebPanel();
		topButtonPanel.setBackground(new Color(56, 107, 170));

		WebPanel topPanel = new WebPanel();
		topPanel.setShadeWidth(20);
		topPanel.setRound(2);
		topPanel.setBackground(new Color(120, 192, 217));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(topButtonPanel, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(centerPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(topPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
								.addComponent(bottomButtonPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 777, GroupLayout.PREFERRED_SIZE))
							.addGap(46)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(rightButtonPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(bottomLogoPanel, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
					.addGap(0))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(topButtonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(centerPanel, GroupLayout.PREFERRED_SIZE, 435, GroupLayout.PREFERRED_SIZE))
						.addComponent(rightButtonPanel, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(bottomButtonPanel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(bottomLogoPanel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addGap(4))
		);
		topPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		WebLabel topLogoLabel = new WebLabel("");
		topLogoLabel.setIcon(new ImageIcon(InstructorNoa.class.getResource("/icons/noa/teacher_128.png")));
		topLogoLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		topLogoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		topLogoLabel.setForeground(Color.WHITE);
		topPanel.add(topLogoLabel);
		
		WebLabel topNameLabel = new WebLabel("iCalabo");
		topNameLabel.setForeground(new Color(51, 51, 255));
		topNameLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		topPanel.add(topNameLabel);

		WebDesktopPane desktopPane = new WebDesktopPane();
		setDesktopPane(desktopPane);

		desktopPane.setBackground(new Color(237, 246, 253));
		GroupLayout gl_centerPanel = new GroupLayout(centerPanel);
		gl_centerPanel.setHorizontalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(desktopPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
		);
		gl_centerPanel.setVerticalGroup(
			gl_centerPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_centerPanel.createSequentialGroup()
					.addComponent(desktopPane, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(gl_desktopPane.createParallelGroup(
				Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		gl_desktopPane.setVerticalGroup(gl_desktopPane.createParallelGroup(
				Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		desktopPane.setLayout(gl_desktopPane);
		centerPanel.setLayout(gl_centerPanel);

		WebPanel volumeButtonContainer = new WebPanel();
		volumeButtonContainer.setBackground(new Color(58, 109, 175));

		WebPanel volumeContainer = new WebPanel();
		volumeContainer.setBackground(new Color(58, 109, 175));
		volumeContainer.setLayout(null);

		WebLabel volumeLabel = new WebLabel("Volume Control");
		volumeLabel.setBounds(21, 0, 111, 47);
		volumeLabel.setForeground(Color.WHITE);
		volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		volumeContainer.add(volumeLabel);

		WebButton volumeButton = new WebButton("");
		volumeButton.setBounds(10, 0, 127, 47);
		volumeButton.setBackground(new Color(58, 109, 175));
		volumeContainer.add(volumeButton);
		volumeButton.setUndecorated(true);
		volumeButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/top_orange.png")));
		GroupLayout gl_volumeButtonContainer = new GroupLayout(volumeButtonContainer);
		gl_volumeButtonContainer.setHorizontalGroup(
			gl_volumeButtonContainer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_volumeButtonContainer.createSequentialGroup()
					.addComponent(volumeContainer, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(52, Short.MAX_VALUE))
		);
		gl_volumeButtonContainer.setVerticalGroup(
			gl_volumeButtonContainer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_volumeButtonContainer.createSequentialGroup()
					.addComponent(volumeContainer, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		volumeButtonContainer.setLayout(gl_volumeButtonContainer);
		volumeButton.putClientProperty("key", "volume");

		WebPanel attendanceButtonContainer = new WebPanel();
		attendanceButtonContainer.setBackground(new Color(58, 109, 175));

		WebPanel attendanceContainer = new WebPanel();
		attendanceContainer.setBackground(new Color(58, 109, 175));
		attendanceContainer.setLayout(null);

		WebLabel attendaceLabel = new WebLabel("Attendance Summary");
		attendaceLabel.setForeground(Color.WHITE);
		attendaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		attendaceLabel.setBounds(10, 0, 107, 47);
		attendanceContainer.add(attendaceLabel);

		WebButton attendanceButton = new WebButton("");
		attendanceButton.setBackground(new Color(58, 109, 175));
		attendanceButton.setBounds(0, 0, 127, 47);
		attendanceContainer.add(attendanceButton);
		attendanceButton.setUndecorated(true);
		attendanceButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/top_orange.png")));
		attendanceButton.putClientProperty("key", "attendance");

		WebPanel callAllButtonContainer = new WebPanel();
		callAllButtonContainer.setBackground(new Color(58, 109, 175));

		WebPanel callAllContainer = new WebPanel();
		callAllContainer.setBackground(new Color(58, 109, 175));
		callAllContainer.setLayout(null);

		WebLabel callAllLabel = new WebLabel("Call All");
		callAllLabel.setForeground(Color.WHITE);
		callAllLabel.setHorizontalAlignment(SwingConstants.CENTER);
		callAllLabel.setBounds(10, 0, 107, 47);
		callAllContainer.add(callAllLabel);

		WebButton callAllButton = new WebButton("");
		callAllButton.setBackground(new Color(58, 109, 175));
		callAllButton.setBounds(0, 0, 127, 47);
		callAllContainer.add(callAllButton);
		callAllButton.setUndecorated(true);
		callAllButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/top_orange.png")));
		callAllButton.putClientProperty("key", "callAll");

		WebComboBox viewModeButton = new WebComboBox();
		viewModeButton.putClientProperty("key", "viewMode");
		viewModeButton.setModel(new DefaultComboBoxModel(new String[] {
				"Thumbnail", "List" }));
		GroupLayout gl_topButtonPanel = new GroupLayout(topButtonPanel);
		gl_topButtonPanel.setHorizontalGroup(
			gl_topButtonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topButtonPanel.createSequentialGroup()
					.addComponent(volumeButtonContainer, GroupLayout.PREFERRED_SIZE, 139, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(attendanceButtonContainer, GroupLayout.PREFERRED_SIZE, 125, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(callAllButtonContainer, GroupLayout.PREFERRED_SIZE, 127, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(viewModeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(288))
		);
		gl_topButtonPanel.setVerticalGroup(
			gl_topButtonPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topButtonPanel.createSequentialGroup()
					.addGroup(gl_topButtonPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(volumeButtonContainer, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(attendanceButtonContainer, 0, 0, Short.MAX_VALUE)
						.addComponent(viewModeButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
						.addComponent(callAllButtonContainer, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
					.addContainerGap())
		);
		GroupLayout gl_callAllButtonContainer = new GroupLayout(callAllButtonContainer);
		gl_callAllButtonContainer.setHorizontalGroup(
			gl_callAllButtonContainer.createParallelGroup(Alignment.LEADING)
				.addComponent(callAllContainer, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
		);
		gl_callAllButtonContainer.setVerticalGroup(
			gl_callAllButtonContainer.createParallelGroup(Alignment.LEADING)
				.addComponent(callAllContainer, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
		);
		callAllButtonContainer.setLayout(gl_callAllButtonContainer);
		GroupLayout gl_attendanceButtonContainer = new GroupLayout(attendanceButtonContainer);
		gl_attendanceButtonContainer.setHorizontalGroup(
			gl_attendanceButtonContainer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_attendanceButtonContainer.createSequentialGroup()
					.addComponent(attendanceContainer, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(62, Short.MAX_VALUE))
		);
		gl_attendanceButtonContainer.setVerticalGroup(
			gl_attendanceButtonContainer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_attendanceButtonContainer.createSequentialGroup()
					.addComponent(attendanceContainer, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		attendanceButtonContainer.setLayout(gl_attendanceButtonContainer);
		topButtonPanel.setLayout(gl_topButtonPanel);
		bottomButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));

		WebButton surveyButton = new WebButton("Survey");
		surveyButton.putClientProperty("key", "survey");
		surveyButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/survey.png")));
		surveyButton.setUndecorated(true);
		surveyButton.setText("");
		bottomButtonPanel.add(surveyButton);

		WebButton whiteBoardButton = new WebButton("White Board");
		whiteBoardButton.putClientProperty("key", "whiteBoard");
		
		WebButton powerButton = new WebButton("");
		powerButton.putClientProperty("key", "power");
		powerButton.setIcon(new ImageIcon(InstructorNoa.class.getResource("/icons/noa/power.png")));
		powerButton.setUndecorated(true);
		bottomButtonPanel.add(powerButton);
		whiteBoardButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/whiteboard.png")));
		whiteBoardButton.setText("");
		whiteBoardButton.setUndecorated(true);
		bottomButtonPanel.add(whiteBoardButton);

		WebButton internetButton = new WebButton("Internet & Web Controller");
		internetButton.putClientProperty("key", "internet");
		internetButton.setUndecorated(true);
		internetButton.setText("");
		internetButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/internet.png")));
		bottomButtonPanel.add(internetButton);

		WebButton programStopButton = new WebButton("Allow & Restrict Program");
		programStopButton.putClientProperty("key", "programStop");
		programStopButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/program_stop.png")));
		programStopButton.setText("");
		programStopButton.setUndecorated(true);
		bottomButtonPanel.add(programStopButton);

		WebButton programStartButton = new WebButton("Program Starter");
		programStartButton.putClientProperty("key", "programStart");
		programStartButton.setUndecorated(true);
		programStartButton.setText("");
		programStartButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/program_start.png")));
		bottomButtonPanel.add(programStartButton);
		rightButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		WebButton monitorButton = new WebButton("Monitoring");
		monitorButton.putClientProperty("key", "monitor");
		monitorButton.setText("");
		monitorButton.setUndecorated(true);
		monitorButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/monitor.png")));
		rightButtonPanel.add(monitorButton);

		WebButton intercomButton = new WebButton("Intercom");
		intercomButton.putClientProperty("key", "intercom");
		intercomButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/intercom.png")));
		intercomButton.setUndecorated(true);
		intercomButton.setText("");
		rightButtonPanel.add(intercomButton);

		WebButton groupButton = new WebButton("Grouping");
		groupButton.putClientProperty("key", "group");
		groupButton.setUndecorated(true);
		groupButton.setText("");
		groupButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/group.png")));
		rightButtonPanel.add(groupButton);

		WebButton modelButton = new WebButton("Modeling");
		modelButton.putClientProperty("key", "model");
		modelButton.setText("");
		modelButton.setUndecorated(true);
		modelButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/model.png")));
		rightButtonPanel.add(modelButton);

		WebButton recordButton = new WebButton("Recording");
		recordButton.putClientProperty("key", "record");
		recordButton.setUndecorated(true);
		recordButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/record.png")));
		recordButton.setText("");
		rightButtonPanel.add(recordButton);

		WebButton speechButton = new WebButton("Speech Recongnition");
		speechButton.putClientProperty("key", "speech");
		speechButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/speech.png")));
		speechButton.setText("");
		speechButton.setUndecorated(true);
		rightButtonPanel.add(speechButton);

		WebButton fileButton = new WebButton("File Sharing");
		fileButton.putClientProperty("key", "file");
		fileButton.setText("");
		fileButton.setUndecorated(true);
		fileButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/file.png")));
		rightButtonPanel.add(fileButton);

		WebButton quizButton = new WebButton("Exam");
		quizButton.putClientProperty("key", "quiz");
		quizButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/quiz.png")));
		quizButton.setUndecorated(true);
		quizButton.setText("");
		rightButtonPanel.add(quizButton);

		WebButton reportButton = new WebButton("Reports");
		reportButton.putClientProperty("key", "report");
		reportButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/report.png")));
		reportButton.setUndecorated(true);
		reportButton.setText("");
		rightButtonPanel.add(reportButton);

		WebButton accountButton = new WebButton("Accounts");
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		accountButton.putClientProperty("key", "account");
		accountButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/accounts.png")));
		accountButton.setUndecorated(true);
		accountButton.setText("");
		rightButtonPanel.add(accountButton);

		WebButton chatButton = new WebButton("Chat & Messaging Button");
		chatButton.setIcon(new ImageIcon(InstructorNoa.class.getResource("/icons/noa/chat.png")));
		chatButton.setText("");
		chatButton.setUndecorated(true);
		chatButton.putClientProperty("key", "chat");
		rightButtonPanel.add(chatButton);

		WebLabel bottomLogoLabel = new WebLabel("LOGO");
		bottomLogoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		bottomLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoLabel.setForeground(new Color(255, 127, 80));

		WebLabel copyRightLabel = new WebLabel("Copy right 2013");
		copyRightLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		copyRightLabel.setText("Copy right \u00A9 2013");
		copyRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		bottomLogoPanel.add(bottomLogoLabel);
		bottomLogoPanel.add(copyRightLabel);
		frame.getContentPane().setLayout(groupLayout);

		// Add Event Handlers
		InstructorNoaUtil instructorNoaUtil = new InstructorNoaUtil();
		instructorNoaUtil.addEventsRightPanel(rightButtonPanel);
		instructorNoaUtil.addEventsBottomPanel(bottomButtonPanel);
	}

	public static WebDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(WebDesktopPane desktopPane) {
		InstructorNoa.desktopPane = desktopPane;
	}

	public static AVTransmit2 getTransmitter() {
		return transmitter;
	}

	public static void setTransmitter(AVTransmit2 transmitter) {
		InstructorNoa.transmitter = transmitter;
	}

	public static AVReceive2 getReceiver() {
		return receiver;
	}

	public static void setReceiver(AVReceive2 receiver) {
		InstructorNoa.receiver = receiver;
	}

	public static List<Chat> getChatList() {
		return chatList;
	}

	public static void setChatList(List<Chat> chatList) {
		InstructorNoa.chatList = chatList;
	}

	public static ServerService getServerService() {
		return serverService;
	}

	public static void setServerService(ServerService serverService) {
		InstructorNoa.serverService = serverService;
	}
}
