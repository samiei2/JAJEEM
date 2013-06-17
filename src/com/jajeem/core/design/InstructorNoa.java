package com.jajeem.core.design;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.alee.laf.desktoppane.WebDesktopPane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.command.service.ServerService;
import com.jajeem.message.design.Chat;
import com.jajeem.util.BackgroundPanel;
import com.jajeem.util.Config;
import java.awt.CardLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class InstructorNoa {

	private WebFrame frame;
	private static WebDesktopPane desktopPane;
	private static WebPanel centerPanel;
	private static WebTable studentListTable; 

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
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame = new WebFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				InstructorNoa.class.getResource("/icons/noa/teacher.png")));
		frame.setShowMinimizeButton(false);
		frame.setTitle("iCalabo");
		frame.setShowResizeCorner(false);
		frame.getContentPane().setBackground(new Color(56, 107, 170));
		frame.setBounds(200, 0, 1021, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		WebPanel centerPanel = new WebPanel();
		InstructorNoa.setCenterPanel(centerPanel);
		WebPanel centerListPanel = new WebPanel();
		centerPanel.add(centerListPanel);
		centerPanel.setBackground(new Color(255, 255, 255));

		DefaultTableModel model = new DefaultTableModel();  

		model.addColumn("PC IP"); 
		model.addColumn("Student name"); 

		WebTable table = new WebTable(model);
		setStudentListTable(table);
		table.setBackground(new Color(237, 246, 253));
		table.setEditable(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
		WebScrollPane scrollPanel = new WebScrollPane(table);
		scrollPanel.setDrawBorder(false);
		

		WebPanel rightButtonPanel = new WebPanel();
		rightButtonPanel.setBackground(new Color(56, 107, 170));

		WebPanel bottomButtonPanel = new WebPanel();
		bottomButtonPanel.setBackground(new Color(58, 109, 175));

		WebPanel bottomLogoPanel = new WebPanel();
		bottomLogoPanel.setBackground(new Color(58, 109, 175));

		WebPanel topButtonPanel = new WebPanel();
		topButtonPanel.setShadeWidth(20);
		topButtonPanel.setBackground(new Color(56, 107, 170));

		WebButton volumeButton = new WebButton();
		volumeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		volumeButton.setDrawShade(false);
		volumeButton.setRound(10);
		volumeButton.setTopSelectedBgColor(new Color(75, 113, 158));
		volumeButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		volumeButton.setForeground(Color.WHITE);
		volumeButton.putClientProperty("key", "volume");
		volumeButton.setText("Volume Control");
		volumeButton.setBottomBgColor(new Color(235, 105, 11));
		volumeButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(volumeButton);

		WebButton attendanceButton = new WebButton();
		attendanceButton.putClientProperty("key", "attendance");
		attendanceButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		attendanceButton.setDrawShade(false);
		attendanceButton.setRound(10);
		attendanceButton.setTopSelectedBgColor(new Color(75, 113, 158));
		attendanceButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		attendanceButton.setForeground(Color.WHITE);
		attendanceButton.setText("Attendance Summary");
		attendanceButton.setBottomBgColor(new Color(235, 105, 11));
		attendanceButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(attendanceButton);

		WebButton callAllButton = new WebButton();
		callAllButton.putClientProperty("key", "callAll");
		callAllButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		callAllButton.setDrawShade(false);
		callAllButton.setRound(10);
		callAllButton.setTopSelectedBgColor(new Color(75, 113, 158));
		callAllButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		callAllButton.setForeground(Color.WHITE);
		callAllButton.setText("Call All");
		callAllButton.setBottomBgColor(new Color(235, 105, 11));
		callAllButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(callAllButton);

		WebButton viewModeButton = new WebButton();
		viewModeButton.putClientProperty("key", "viewMode");
		viewModeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		viewModeButton.setDrawShade(false);
		viewModeButton.setRound(10);
		viewModeButton.setTopSelectedBgColor(new Color(75, 113, 158));
		viewModeButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		viewModeButton.setForeground(Color.WHITE);
		viewModeButton.setText("View Mode");
		viewModeButton.setBottomBgColor(new Color(235, 105, 11));
		viewModeButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(viewModeButton);

		WebButton languageButton = new WebButton();
		languageButton.putClientProperty("key", "language");
		languageButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		languageButton.setDrawShade(false);
		languageButton.setRound(10);
		languageButton.setTopSelectedBgColor(new Color(75, 113, 158));
		languageButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		languageButton.setForeground(Color.WHITE);
		languageButton.setText("EN/FN");
		languageButton.setBottomBgColor(new Color(235, 105, 11));
		languageButton.setTopBgColor(new Color(235, 105, 11));
		topButtonPanel.add(languageButton);

		BackgroundPanel topPanel = new BackgroundPanel(Toolkit
				.getDefaultToolkit().getImage(
						InstructorNoa.class
								.getResource("/icons/noa/top_logo.png")));
		topPanel.setUndecorated(false);
		topPanel.setWebColored(false);
		topPanel.setTransparentAdd(false);
		topPanel.setDrawBackground(false);
		topPanel.setBackground(new Color(56, 107, 170));
		topPanel.setShadeWidth(20);
		topPanel.setRound(2);
		topPanel.setBackground(new Color(56, 107, 170));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				bottomButtonPanel,
																				0,
																				0,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED))
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								centerPanel,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								753,
																								Short.MAX_VALUE)
																						.addComponent(
																								topButtonPanel,
																								Alignment.LEADING,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addGroup(
																								Alignment.LEADING,
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												topPanel,
																												GroupLayout.PREFERRED_SIZE,
																												741,
																												Short.MAX_VALUE)
																										.addGap(12)))
																		.addGap(6)))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																rightButtonPanel,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																bottomLogoPanel,
																GroupLayout.DEFAULT_SIZE,
																226,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				topPanel,
																				GroupLayout.PREFERRED_SIZE,
																				129,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				topButtonPanel,
																				GroupLayout.PREFERRED_SIZE,
																				37,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				centerPanel,
																				GroupLayout.DEFAULT_SIZE,
																				361,
																				Short.MAX_VALUE))
														.addComponent(
																rightButtonPanel,
																GroupLayout.DEFAULT_SIZE,
																539,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																bottomButtonPanel,
																GroupLayout.PREFERRED_SIZE,
																51,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																bottomLogoPanel,
																GroupLayout.PREFERRED_SIZE,
																42,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(gl_topPanel.createParallelGroup(
				Alignment.LEADING).addGap(0, 860, Short.MAX_VALUE));
		gl_topPanel.setVerticalGroup(gl_topPanel.createParallelGroup(
				Alignment.LEADING).addGap(0, 107, Short.MAX_VALUE));
		topPanel.setLayout(gl_topPanel);

		WebDesktopPane desktopPane = new WebDesktopPane();
		setDesktopPane(desktopPane);

		desktopPane.setBackground(new Color(237, 246, 253));
		centerPanel.setBackground(new Color(237, 246, 253));
		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(gl_desktopPane.createParallelGroup(
				Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		gl_desktopPane.setVerticalGroup(gl_desktopPane.createParallelGroup(
				Alignment.LEADING).addGap(0, 10, Short.MAX_VALUE));
		desktopPane.setLayout(gl_desktopPane);
		centerPanel.setLayout(new CardLayout(0, 0));
		centerPanel.add(centerListPanel, "name_3466370417827");
		centerListPanel.setLayout(new BorderLayout(0, 0));
		centerListPanel.add(scrollPanel);
		centerPanel.add(desktopPane, "name_3466416048915");
		topButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		topButtonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		bottomButtonPanel.setLayout(new GridLayout(1, 0, 0, 0));
		topButtonPanel.setLayout(new GridLayout(0, 5, 0, 0));
		centerListPanel.setBackground(new Color(237, 246, 253));
		centerPanel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{desktopPane, centerListPanel}));

		WebButton surveyButton = new WebButton();
		surveyButton.setIconTextGap(30);
		surveyButton.putClientProperty("key", "survey");
		surveyButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		surveyButton.setDrawShade(false);
		surveyButton.setRound(10);
		surveyButton.setTopSelectedBgColor(new Color(75, 113, 158));
		surveyButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		surveyButton.setForeground(Color.WHITE);
		surveyButton.setText("Survey");
		surveyButton.setBottomBgColor(new Color(225, 234, 244));
		surveyButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(surveyButton);

		WebButton whiteBoardButton = new WebButton();
		whiteBoardButton.setIconTextGap(30);
		whiteBoardButton.putClientProperty("key", "whiteBoard");
		whiteBoardButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		whiteBoardButton.setDrawShade(false);
		whiteBoardButton.setRound(10);
		whiteBoardButton.setTopSelectedBgColor(new Color(75, 113, 158));
		whiteBoardButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		whiteBoardButton.setForeground(Color.WHITE);
		whiteBoardButton.setText("White board");
		whiteBoardButton.setBottomBgColor(new Color(225, 234, 244));
		whiteBoardButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(whiteBoardButton);

		WebButton powerButton = new WebButton();
		powerButton.setIconTextGap(30);
		powerButton.putClientProperty("key", "power");
		powerButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		powerButton.setDrawShade(false);
		powerButton.setRound(10);
		powerButton.setTopSelectedBgColor(new Color(75, 113, 158));
		powerButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		powerButton.setForeground(Color.WHITE);
		powerButton.setText("PC Controller");
		powerButton.setBottomBgColor(new Color(225, 234, 244));
		powerButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(powerButton);

		WebButton internetButton = new WebButton();
		internetButton.setIconTextGap(30);
		internetButton.putClientProperty("key", "internet");
		internetButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		internetButton.setDrawShade(false);
		internetButton.setRound(10);
		internetButton.setTopSelectedBgColor(new Color(75, 113, 158));
		internetButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		internetButton.setForeground(Color.WHITE);
		internetButton.setText("Internet Controller");
		internetButton.setBottomBgColor(new Color(225, 234, 244));
		internetButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(internetButton);

		WebButton programStopButton = new WebButton();
		programStopButton.setIconTextGap(30);
		programStopButton.putClientProperty("key", "programStop");
		programStopButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programStopButton.setDrawShade(false);
		programStopButton.setRound(10);
		programStopButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programStopButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programStopButton.setForeground(Color.WHITE);
		programStopButton.setText("Allow & Restrict Program");
		programStopButton.setBottomBgColor(new Color(225, 234, 244));
		programStopButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(programStopButton);

		WebButton programStartButton = new WebButton();
		programStartButton.setIconTextGap(30);
		programStartButton.putClientProperty("key", "programStart");
		programStartButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		programStartButton.setDrawShade(false);
		programStartButton.setRound(10);
		programStartButton.setTopSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		programStartButton.setForeground(Color.WHITE);
		programStartButton.setText("Program Starter");
		programStartButton.setBottomBgColor(new Color(225, 234, 244));
		programStartButton.setTopBgColor(new Color(116, 166, 219));
		bottomButtonPanel.add(programStartButton);

		rightButtonPanel.setLayout(new GridLayout(0, 1, 0, 0));

		WebButton monitorButton = new WebButton();
		monitorButton.setHorizontalAlignment(SwingConstants.LEADING);
		monitorButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/monitor.png")));
		monitorButton.setIconTextGap(30);
		monitorButton.putClientProperty("key", "monitor");
		monitorButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		monitorButton.setDrawShade(false);
		monitorButton.setRound(10);
		monitorButton.setTopSelectedBgColor(new Color(75, 113, 158));
		monitorButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		monitorButton.setForeground(Color.WHITE);
		monitorButton.setText("Monitoring");
		monitorButton.setBottomBgColor(new Color(225, 234, 244));
		monitorButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(monitorButton);

		WebButton intercomButton = new WebButton();
		intercomButton.setHorizontalAlignment(SwingConstants.LEADING);
		intercomButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/intercom.png")));
		intercomButton.setIconTextGap(30);
		intercomButton.putClientProperty("key", "intercom");
		intercomButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		intercomButton.setDrawShade(false);
		intercomButton.setRound(10);
		intercomButton.setTopSelectedBgColor(new Color(75, 113, 158));
		intercomButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		intercomButton.setForeground(Color.WHITE);
		intercomButton.setText("Intercom");
		intercomButton.setBottomBgColor(new Color(225, 234, 244));
		intercomButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(intercomButton);

		WebButton groupButton = new WebButton();
		groupButton.setHorizontalAlignment(SwingConstants.LEADING);
		groupButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/group.png")));
		groupButton.setIconTextGap(30);
		groupButton.putClientProperty("key", "group");
		groupButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		groupButton.setDrawShade(false);
		groupButton.setRound(10);
		groupButton.setTopSelectedBgColor(new Color(75, 113, 158));
		groupButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		groupButton.setForeground(Color.WHITE);
		groupButton.setText("Grouping");
		groupButton.setBottomBgColor(new Color(225, 234, 244));
		groupButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(groupButton);

		WebButton modelButton = new WebButton();
		modelButton.setHorizontalAlignment(SwingConstants.LEADING);
		modelButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/group.png")));
		modelButton.setIconTextGap(30);
		modelButton.putClientProperty("key", "model");
		modelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		modelButton.setDrawShade(false);
		modelButton.setRound(10);
		modelButton.setTopSelectedBgColor(new Color(75, 113, 158));
		modelButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		modelButton.setForeground(Color.WHITE);
		modelButton.setText("Modeling");
		modelButton.setBottomBgColor(new Color(225, 234, 244));
		modelButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(modelButton);

		WebButton recordButton = new WebButton();
		recordButton.setHorizontalAlignment(SwingConstants.LEADING);
		recordButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/record.png")));
		recordButton.setIconTextGap(30);
		recordButton.putClientProperty("key", "record");
		recordButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		recordButton.setDrawShade(false);
		recordButton.setRound(10);
		recordButton.setTopSelectedBgColor(new Color(75, 113, 158));
		recordButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		recordButton.setForeground(Color.WHITE);
		recordButton.setText("Recording");
		recordButton.setBottomBgColor(new Color(225, 234, 244));
		recordButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(recordButton);

		WebButton speechButton = new WebButton();
		speechButton.setHorizontalAlignment(SwingConstants.LEADING);
		speechButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/speech.png")));
		speechButton.setIconTextGap(30);
		speechButton.putClientProperty("key", "speech");
		speechButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		speechButton.setDrawShade(false);
		speechButton.setRound(10);
		speechButton.setTopSelectedBgColor(new Color(75, 113, 158));
		speechButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		speechButton.setForeground(Color.WHITE);
		speechButton.setText("Speech Recognition");
		speechButton.setBottomBgColor(new Color(225, 234, 244));
		speechButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(speechButton);

		WebButton fileButton = new WebButton();
		fileButton.setHorizontalAlignment(SwingConstants.LEADING);
		fileButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/file.png")));
		fileButton.setIconTextGap(30);
		fileButton.putClientProperty("key", "file");
		fileButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		fileButton.setDrawShade(false);
		fileButton.setRound(10);
		fileButton.setTopSelectedBgColor(new Color(75, 113, 158));
		fileButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		fileButton.setForeground(Color.WHITE);
		fileButton.setText("File Sharing");
		fileButton.setBottomBgColor(new Color(225, 234, 244));
		fileButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(fileButton);

		WebButton quizButton = new WebButton();
		quizButton.setHorizontalAlignment(SwingConstants.LEADING);
		quizButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/quiz.png")));
		quizButton.setIconTextGap(30);
		quizButton.putClientProperty("key", "quiz");
		quizButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		quizButton.setDrawShade(false);
		quizButton.setRound(10);
		quizButton.setTopSelectedBgColor(new Color(75, 113, 158));
		quizButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		quizButton.setForeground(Color.WHITE);
		quizButton.setText("Exam");
		quizButton.setBottomBgColor(new Color(225, 234, 244));
		quizButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(quizButton);

		WebButton reportButton = new WebButton();
		reportButton.setHorizontalAlignment(SwingConstants.LEADING);
		reportButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/report.png")));
		reportButton.setIconTextGap(30);
		reportButton.putClientProperty("key", "report");
		reportButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		reportButton.setDrawShade(false);
		reportButton.setRound(10);
		reportButton.setTopSelectedBgColor(new Color(75, 113, 158));
		reportButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		reportButton.setForeground(Color.WHITE);
		reportButton.setText("Reports");
		reportButton.setBottomBgColor(new Color(225, 234, 244));
		reportButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(reportButton);

		WebButton accountButton = new WebButton();
		accountButton.setHorizontalAlignment(SwingConstants.LEADING);
		accountButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/account.png")));
		accountButton.setIconTextGap(30);
		accountButton.putClientProperty("key", "account");
		accountButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		accountButton.setDrawShade(false);
		accountButton.setRound(10);
		accountButton.setTopSelectedBgColor(new Color(75, 113, 158));
		accountButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		accountButton.setForeground(Color.WHITE);
		accountButton.setText("Accounts");
		accountButton.setBottomBgColor(new Color(225, 234, 244));
		accountButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(accountButton);

		WebButton chatButton = new WebButton();
		chatButton.setHorizontalAlignment(SwingConstants.LEADING);
		chatButton.setIcon(new ImageIcon(InstructorNoa.class
				.getResource("/icons/noa/right_panel/chat.png")));
		chatButton.setIconTextGap(30);
		chatButton.putClientProperty("key", "chat");
		chatButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		chatButton.setDrawShade(false);
		chatButton.setRound(10);
		chatButton.setTopSelectedBgColor(new Color(75, 113, 158));
		chatButton.setBottomSelectedBgColor(new Color(75, 113, 158));
		chatButton.setForeground(Color.WHITE);
		chatButton.setText("Chat & Messaging");
		chatButton.setBottomBgColor(new Color(225, 234, 244));
		chatButton.setTopBgColor(new Color(116, 166, 219));
		rightButtonPanel.add(chatButton);

		WebLabel bottomLogoLabel = new WebLabel("LOGO");
		bottomLogoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		bottomLogoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoLabel.setForeground(new Color(255, 127, 80));

		WebLabel copyRightLabel = new WebLabel("Copy right 2013");
		copyRightLabel.setForeground(Color.WHITE);
		copyRightLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		copyRightLabel.setText("Copy Right \u00A9 2013");
		copyRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLogoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		bottomLogoPanel.add(bottomLogoLabel);
		bottomLogoPanel.add(copyRightLabel);
		frame.getContentPane().setLayout(groupLayout);

		// Add Event Handlers
		InstructorNoaUtil instructorNoaUtil = new InstructorNoaUtil();
		instructorNoaUtil.addEventsRightPanel(rightButtonPanel);
		instructorNoaUtil.addEventsBottomPanel(bottomButtonPanel);
		instructorNoaUtil.addEventsTopPanel(topButtonPanel);
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

	public static WebPanel getCenterPanel() {
		return centerPanel;
	}

	public static void setCenterPanel(WebPanel centerPanel) {
		InstructorNoa.centerPanel = centerPanel;
	}

	public static WebTable getStudentListTable() {
		return studentListTable;
	}

	public static void setStudentListTable(WebTable studentListTable) {
		InstructorNoa.studentListTable = studentListTable;
	}
}
