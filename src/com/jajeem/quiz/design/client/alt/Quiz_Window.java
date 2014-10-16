package com.jajeem.quiz.design.client.alt;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.DefaultListModel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.command.model.FinishedQuizCommand;
import com.jajeem.command.model.SendQuizResponseCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.student.StudentLogin;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizEventListener;
import com.jajeem.events.QuizFinished;
import com.jajeem.events.QuizResponse;
import com.jajeem.events.QuizStop;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
import com.jajeem.util.ClientSession;
import com.jajeem.util.WindowResizeAdapter;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.jajeem.quiz.design.alt.CustomQuizButton;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class Quiz_Window extends BaseQuizClientFrame {
	private JTextField textFieldDirection;
	private JTextField textFieldTime;
	
	private DefaultListModel model;
	// private Quiz currentRun.getQuiz();

	protected Question currentQuestion;

	private QuizEvent quizEvent;
	private ArrayList<Question> sendQueue = new ArrayList<>();
	@SuppressWarnings("unused")
	private int sid;
	private Student privateStudent = new Student();
	private Run currentRun;
	private ActionListener taskPerformer;

	long remaining; // How many milliseconds remain in the countdown.

	long lastUpdate; // When count was last updated

	Timer timer; // Updates the count every second

	NumberFormat format; // Format minutes:seconds with leading zeros

	private String server;

	private int listenPort;
	private JTextField textFieldAnswer1Card1;
	private JTextField textFieldAnswer2Card1;
	private JTextField textFieldAnswer4Card1;
	private JTextField textFieldAnswer3Card1;
	private JTextField textFieldAnswer5Card1;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JTextField textFieldAnswer1Card2;
	private JTextField textFieldAnswer2Card2;
	private JTextField textFieldAnswer3Card2;
	private JTextField textFieldAnswer4Card2;
	private JTextField textFieldAnswer5Card2;
	private WebList webList_1;
	private CustomQuizButton wbtnNext;
	private CustomQuizButton wbtnPrev;
	private WebTextArea webTextAreaEssayAnswer;
	private WebTextArea webTextAreaQuestion;
	private JPanel panelCards;
	private JLabel lblQuestion;
	private JLabel lblAnswered;
	private JLabel lblNumOfQuestion;
	
	public Quiz_Window(Run run) {
		setTitle("Quiz");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Quiz_Window.class.getResource("/icons/noa_en/quiz.png")));
		WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
		WindowResizeAdapter.install(this, SwingConstants.EAST);
		WindowResizeAdapter.install(this, SwingConstants.SOUTH);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getTopPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		
		JLabel lblNumberOfQuestions = new JLabel("Number of Questions : ");
		lblNumberOfQuestions.setVisible(false);
		
		lblAnswered = new JLabel("Answered");
		lblAnswered.setVisible(false);
		
		JLabel lblDirections = new JLabel("Directions : ");
		
		textFieldDirection = new JTextField();
		textFieldDirection.setEnabled(false);
		textFieldDirection.setEditable(false);
		textFieldDirection.setColumns(10);
		
		textFieldTime = new JTextField();
		textFieldTime.setEnabled(false);
		textFieldTime.setEditable(false);
		textFieldTime.setColumns(10);
		
		JLabel lblTime = new JLabel("Remaining Time");
		
		lblNumOfQuestion = new JLabel("?");
		lblNumOfQuestion.setVisible(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNumberOfQuestions)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNumOfQuestion)
							.addPreferredGap(ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
							.addComponent(lblTime))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAnswered)
							.addPreferredGap(ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
							.addComponent(textFieldTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblDirections)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldDirection, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfQuestions)
						.addComponent(lblTime)
						.addComponent(lblNumOfQuestion))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAnswered)
						.addComponent(textFieldTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirections)
						.addComponent(textFieldDirection, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(35, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getTopPane().setLayout(groupLayout);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		GroupLayout groupLayout_1 = new GroupLayout(getMainContentPane());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout_1.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		
		panelCards = new JPanel();
		panelCards.setOpaque(false);
		panelCards.setLayout(new CardLayout(0,0));
		
		wbtnNext = new CustomQuizButton("/icons/noa_en/quiznextbutton.png");
		wbtnNext.setUndecorated(true);
//		wbtnNext.setText("");
		
		wbtnPrev = new CustomQuizButton("/icons/noa_en/quizbackbutton.png");
		wbtnPrev.setUndecorated(true);
//		wbtnPrev.setText("");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelCards, GroupLayout.PREFERRED_SIZE, 282, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addContainerGap(215, Short.MAX_VALUE)
					.addComponent(wbtnPrev, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnNext, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelCards, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnNext, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnPrev, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setOpaque(false);
		panelCards.add(webPanel, "0");
		
		radioButton = new JRadioButton("");
		
		radioButton_1 = new JRadioButton("");
		
		radioButton_2 = new JRadioButton("");
		
		radioButton_3 = new JRadioButton("");
		
		radioButton_4 = new JRadioButton("");
		
		textFieldAnswer1Card1 = new JTextField();
		textFieldAnswer1Card1.setEnabled(false);
		textFieldAnswer1Card1.setEditable(false);
		textFieldAnswer1Card1.setColumns(10);
		
		textFieldAnswer2Card1 = new JTextField();
		textFieldAnswer2Card1.setEnabled(false);
		textFieldAnswer2Card1.setEditable(false);
		textFieldAnswer2Card1.setColumns(10);
		
		textFieldAnswer3Card1 = new JTextField();
		textFieldAnswer3Card1.setEnabled(false);
		textFieldAnswer3Card1.setEditable(false);
		textFieldAnswer3Card1.setColumns(10);
		
		textFieldAnswer4Card1 = new JTextField();
		textFieldAnswer4Card1.setEnabled(false);
		textFieldAnswer4Card1.setEditable(false);
		textFieldAnswer4Card1.setColumns(10);
		
		textFieldAnswer5Card1 = new JTextField();
		textFieldAnswer5Card1.setEnabled(false);
		textFieldAnswer5Card1.setEditable(false);
		textFieldAnswer5Card1.setColumns(10);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldAnswer5Card1, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(radioButton))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldAnswer4Card1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
								.addComponent(textFieldAnswer3Card1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
								.addComponent(textFieldAnswer2Card1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
								.addComponent(textFieldAnswer1Card1, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldAnswer1Card1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton))
					.addGap(9)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer2Card1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer3Card1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer4Card1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer5Card1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		
		WebPanel webPanel_1 = new WebPanel();
		webPanel_1.setOpaque(false);
		panelCards.add(webPanel_1, "1");
		
		checkBox = new JCheckBox("");
		
		checkBox_1 = new JCheckBox("");
		
		checkBox_2 = new JCheckBox("");
		
		checkBox_3 = new JCheckBox("");
		
		checkBox_4 = new JCheckBox("");
		
		textFieldAnswer1Card2 = new JTextField();
		textFieldAnswer1Card2.setEnabled(false);
		textFieldAnswer1Card2.setEditable(false);
		textFieldAnswer1Card2.setColumns(10);
		
		textFieldAnswer2Card2 = new JTextField();
		textFieldAnswer2Card2.setEnabled(false);
		textFieldAnswer2Card2.setEditable(false);
		textFieldAnswer2Card2.setColumns(10);
		
		textFieldAnswer3Card2 = new JTextField();
		textFieldAnswer3Card2.setEnabled(false);
		textFieldAnswer3Card2.setEditable(false);
		textFieldAnswer3Card2.setColumns(10);
		
		textFieldAnswer4Card2 = new JTextField();
		textFieldAnswer4Card2.setEnabled(false);
		textFieldAnswer4Card2.setEditable(false);
		textFieldAnswer4Card2.setColumns(10);
		
		textFieldAnswer5Card2 = new JTextField();
		textFieldAnswer5Card2.setEnabled(false);
		textFieldAnswer5Card2.setEditable(false);
		textFieldAnswer5Card2.setColumns(10);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(checkBox_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldAnswer3Card2, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(checkBox_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldAnswer4Card2, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(checkBox_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldAnswer5Card2, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(checkBox))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldAnswer1Card2, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
								.addComponent(textFieldAnswer2Card2, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldAnswer1Card2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox))
					.addGap(9)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer2Card2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer3Card2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer4Card2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldAnswer5Card2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		webPanel_1.setLayout(gl_webPanel_1);
		
		WebPanel webPanel_2 = new WebPanel();
		webPanel_2.setOpaque(false);
		panelCards.add(webPanel_2, "2");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_webPanel_2 = new GroupLayout(webPanel_2);
		gl_webPanel_2.setHorizontalGroup(
			gl_webPanel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_webPanel_2.setVerticalGroup(
			gl_webPanel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		webTextAreaEssayAnswer = new WebTextArea();
		scrollPane_2.setViewportView(webTextAreaEssayAnswer);
		webPanel_2.setLayout(gl_webPanel_2);
		
		lblQuestion = new JLabel("Question ?");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setOpaque(false);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(lblQuestion))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		webTextAreaQuestion = new WebTextArea();
		webTextAreaQuestion.setEnabled(false);
		webTextAreaQuestion.setEditable(false);
		webTextAreaQuestion.setOpaque(false);
		scrollPane_1.setViewportView(webTextAreaQuestion);
		panel_3.setLayout(gl_panel_3);
		panel_2.setLayout(gl_panel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		
		WebList webList = new WebList();
		model = new DefaultListModel();
		webList_1 = new WebList(model);
		scrollPane.setViewportView(webList_1);
		panel_1.setLayout(gl_panel_1);
		getMainContentPane().setLayout(groupLayout_1);
		pack();
		
		initEvents(run);
	}
	
	private void initEvents(Run run) {
		privateStudent = com.jajeem.core.design.student.Student.getStudentModel();
		currentRun = run;
		currentRun.setStudent(privateStudent);

		if (currentRun.getId() == null) {
			currentRun.setId(UUID.randomUUID());
		}

		ClientSession.setQuizWindowHndl(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				if (currentRun.getQuiz() == null
						|| currentRun.getQuiz().getQuestionList().size() == 0) {
					JOptionPane.showMessageDialog(null,
							"An error occured while opening the quiz");
					dispose();
					return;
				}

				for (int i = 0; i < currentRun.getQuiz().getQuestionList()
						.size(); i++) {
					model.addElement("Question " + (i + 1));
				}

				webList_1.setSelectedIndex(0);

				// ///Setting the timer
				taskPerformer = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						updateDisplay();
					}
				};

				if (currentRun.getQuiz().getTime() != 0) {
					textFieldTime.setText(String.valueOf(
							currentRun.getQuiz().getTime()).concat(":00"));
					remaining = currentRun.getQuiz().getTime() * 60000;
					timer = new Timer(1000, taskPerformer);
					timer.setInitialDelay(0);
					lastUpdate = System.currentTimeMillis();
					timer.start();

				} else {
					textFieldTime.setText("");
				}

				textFieldDirection.setText(currentRun.getQuiz().getTitle());
				lblNumOfQuestion.setText(String.valueOf(currentRun.getQuiz().getQuestionList().size()));
			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}
		});
		
		wbtnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (webList_1.getSelectedIndex() != model.getSize() - 1) {
					webList_1.setSelectedIndex(webList_1.getSelectedIndex() + 1);
				} else {
					if (currentQuestion != null) {
						if (currentQuestion.getType() == 0) {
							currentQuestion.setStudentAnswer(new boolean[] {
									radioButton.isSelected(),
									radioButton_1.isSelected(),
									radioButton_2.isSelected(),
									radioButton_3.isSelected(),
									radioButton_4.isSelected() });
						}
						if (currentQuestion.getType() == 1) {
							currentQuestion.setStudentAnswer(new boolean[] {
									checkBox.isSelected(),
									checkBox_1.isSelected(),
									checkBox_2.isSelected(),
									checkBox_3.isSelected(),
									checkBox_4.isSelected() });
						}
						if (currentQuestion.getType() == 2) {
							currentQuestion.setStudentAnswer(webTextAreaEssayAnswer
									.getText());
						}

						currentRun.setEnd(System.currentTimeMillis());

						// TODO clean up this code
						synchronized (sendQueue) {
							sendQueue.add(currentQuestion);
						}

						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									Question question = null;
									synchronized (sendQueue) {
										if (!sendQueue.isEmpty()) {
											question = sendQueue.get(0);
											sendQueue.remove(0);
										}
									}
									QuizResponse resp = new QuizResponse(
											question);
									if (question.getResponse().getId() == null) {
										question.getResponse().setId(
												UUID.randomUUID());
									}
									if (question.getResponse().getRunId() == null) {
										question.getResponse().setRunId(
												currentRun.getId());
									}
									resp.setQuestion(question);
									resp.setStudent(getStudent());
									resp.setQuizRun(currentRun);
									resp.setListeningPort(listenPort);
									// new QuizEvent().fireResponseEvent(resp);
									SendQuizResponseCommand cmd = new SendQuizResponseCommand(
											InetAddress.getLocalHost()
													.getHostAddress(), server,
											listenPort);
									cmd.setEvent(resp);

									ServerService service = StudentLogin
											.getServerService();
									service.send(cmd);
								} catch (NumberFormatException e) {
									JajeemExceptionHandler.logError(e);
									e.printStackTrace();
								} catch (Exception e) {
									JajeemExceptionHandler.logError(e);
									e.printStackTrace();
								}
							}

							private Student getStudent() {
								Student temp;
								temp = ClientSession.getCurrentStudent();
								return temp;// TODO correct this code
							}
						}).start();

						if (timer != null) {
							if (timer.isRunning()) {
								timer.stop();
								timer.removeActionListener(taskPerformer);
							}
						}

						try {
							FinishedQuizCommand cmd = new FinishedQuizCommand(
									InetAddress.getLocalHost().getHostAddress(),
									server, listenPort);

							cmd.setRun(currentRun);

							ServerService service = new ServerService();
							service.send(cmd);
						} catch (Exception ex) {
							JajeemExceptionHandler.logError(ex,
									Quiz_Window.class);
						}
					}
					dispose();
				}
			}
		});
		
		wbtnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (webList_1.getSelectedIndex() != 0) {
					webList_1.setSelectedIndex(webList_1.getSelectedIndex() - 1);
				}
			}
		});
		
		radioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(true);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						radioButton.isSelected(),
						radioButton_1.isSelected(),
						radioButton_2.isSelected(),
						radioButton_3.isSelected(),
						radioButton_4.isSelected() });
			}
		});
		
		radioButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(true);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						radioButton.isSelected(),
						radioButton_1.isSelected(),
						radioButton_2.isSelected(),
						radioButton_3.isSelected(),
						radioButton_4.isSelected() });
			}
		});
		
		radioButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(true);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						radioButton.isSelected(),
						radioButton_1.isSelected(),
						radioButton_2.isSelected(),
						radioButton_3.isSelected(),
						radioButton_4.isSelected() });
			}
		});
		
		radioButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(true);
				radioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						radioButton.isSelected(),
						radioButton_1.isSelected(),
						radioButton_2.isSelected(),
						radioButton_3.isSelected(),
						radioButton_4.isSelected() });
			}
		});
		
		radioButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
				radioButton_3.setSelected(false);
				radioButton_4.setSelected(true);
				currentQuestion.setStudentAnswer(new boolean[] {
						radioButton.isSelected(),
						radioButton_1.isSelected(),
						radioButton_2.isSelected(),
						radioButton_3.isSelected(),
						radioButton_4.isSelected() });
			}
		});
		
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						checkBox.isSelected(), checkBox_1.isSelected(),
						checkBox_2.isSelected(), checkBox_3.isSelected(),
						checkBox_4.isSelected() });
			}
		});
		
		checkBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						checkBox.isSelected(), checkBox_1.isSelected(),
						checkBox_2.isSelected(), checkBox_3.isSelected(),
						checkBox_4.isSelected() });
			}
		});
		
		checkBox_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						checkBox.isSelected(), checkBox_1.isSelected(),
						checkBox_2.isSelected(), checkBox_3.isSelected(),
						checkBox_4.isSelected() });
			}
		});
		
		checkBox_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						checkBox.isSelected(), checkBox_1.isSelected(),
						checkBox_2.isSelected(), checkBox_3.isSelected(),
						checkBox_4.isSelected() });
			}
		});
		
		checkBox_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						checkBox.isSelected(), checkBox_1.isSelected(),
						checkBox_2.isSelected(), checkBox_3.isSelected(),
						checkBox_4.isSelected() });
			}
		});
		
		ListSelectionModel listSelectionModel = webList_1.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						// Save CurrentQuestion and send to teacher
						if (!arg0.getValueIsAdjusting()) {
							if (currentQuestion != null) {
								if (currentQuestion.getType() == 0) {
									currentQuestion.setStudentAnswer(new boolean[] {
											radioButton.isSelected(),
											radioButton_1.isSelected(),
											radioButton_2.isSelected(),
											radioButton_3.isSelected(),
											radioButton_4.isSelected() });
								}
								if (currentQuestion.getType() == 1) {
									currentQuestion
											.setStudentAnswer(new boolean[] {
													checkBox.isSelected(),
													checkBox_1.isSelected(),
													checkBox_2.isSelected(),
													checkBox_3.isSelected(),
													checkBox_4.isSelected() });
								}
								if (currentQuestion.getType() == 2) {
									currentQuestion
											.setStudentAnswer(webTextAreaEssayAnswer
													.getText());
								}

								// TODO clean up this code
								synchronized (sendQueue) {
									sendQueue.add(currentQuestion);
								}

								new Thread(new Runnable() {

									@Override
									public void run() {
										try {
											Question question = null;
											synchronized (sendQueue) {
												if (!sendQueue.isEmpty()) {
													question = sendQueue.get(0);
													sendQueue.remove(0);
												}
											}
											QuizResponse resp = new QuizResponse(
													question);
											if (question.getResponse().getId() == null) {
												question.getResponse().setId(
														UUID.randomUUID());
											}
											if (question.getResponse()
													.getRunId() == null) {
												question.getResponse()
														.setRunId(
																currentRun
																		.getId());
											}
											resp.setQuestion(question);
											resp.setStudent(getStudent());
											resp.setQuizRun(currentRun);
											resp.setListeningPort(listenPort);
											// new
											// QuizEvent().fireResponseEvent(resp);
											SendQuizResponseCommand cmd = new SendQuizResponseCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													server, listenPort);
											cmd.setEvent(resp);

											ServerService service = StudentLogin
													.getServerService();
											service.send(cmd);
										} catch (NumberFormatException e) {
											JajeemExceptionHandler.logError(e);
											e.printStackTrace();
										} catch (Exception e) {
											JajeemExceptionHandler.logError(e);
											e.printStackTrace();
										}
									}

									private Student getStudent() {
										return ClientSession
												.getCurrentStudent();// TODO
																		// correct
										// this code
									}
								}).start();
							}
							// Load Next Question
							currentQuestion = currentRun.getQuiz()
									.getQuestionList()
									.get(webList_1.getSelectedIndex());
							if (currentQuestion.getResponse().getId() == null) {
								currentQuestion.getResponse().setId(
										UUID.randomUUID());
							}
							if (currentQuestion.getResponse().getRunId() == null) {
								currentQuestion.getResponse().setRunId(
										currentRun.getId());
							}
							lblQuestion.setText("Question "
									+ (webList_1.getSelectedIndex() + 1));
							webTextAreaQuestion.setText(currentQuestion.getTitle());

							if (currentQuestion.getType() == 0) {
								CardLayout cl = (CardLayout) (panelCards
										.getLayout());
								cl.show(panelCards, "0");
								// lblInputAnswer.setText("Select an answer");
								radioButton.setEnabled(true);
								radioButton_1.setEnabled(true);
								radioButton_2.setEnabled(true);
								radioButton_3.setEnabled(true);
								radioButton_4.setEnabled(true);
								textFieldAnswer1Card1.setText(currentQuestion
										.getAnswer1());
								textFieldAnswer2Card1.setText(currentQuestion
										.getAnswer2());
								textFieldAnswer3Card1.setText(currentQuestion
										.getAnswer3());
								textFieldAnswer4Card1.setText(currentQuestion
										.getAnswer4());
								textFieldAnswer5Card1.setText(currentQuestion
										.getAnswer5());

								if (currentQuestion.getAnswer1().equals("")) {
									radioButton.setEnabled(false);
								}
								if (currentQuestion.getAnswer2().equals("")) {
									radioButton_1.setEnabled(false);
								}
								if (currentQuestion.getAnswer3().equals("")) {
									radioButton_2.setEnabled(false);
								}
								if (currentQuestion.getAnswer4().equals("")) {
									radioButton_3.setEnabled(false);
								}
								if (currentQuestion.getAnswer5().equals("")) {
									radioButton_4.setEnabled(false);
								}

								boolean[] answers = currentQuestion
										.getStudentAnswer();
								if (answers != null) {
									radioButton.setSelected(answers[0]);
									radioButton_1.setSelected(answers[1]);
									radioButton_2.setSelected(answers[2]);
									radioButton_3.setSelected(answers[3]);
									radioButton_4.setSelected(answers[4]);
								} else {
									radioButton.setSelected(false);
									radioButton_1.setSelected(false);
									radioButton_2.setSelected(false);
									radioButton_3.setSelected(false);
									radioButton_4.setSelected(false);
								}
							}
							if (currentQuestion.getType() == 1) {
								CardLayout cl = (CardLayout) (panelCards
										.getLayout());
								cl.show(panelCards, "1");
								// lblInputAnswer.setText("Select answer");
								checkBox.setEnabled(true);
								checkBox_1.setEnabled(true);
								checkBox_2.setEnabled(true);
								checkBox_3.setEnabled(true);
								checkBox_4.setEnabled(true);

								textFieldAnswer1Card2.setText(currentQuestion
										.getAnswer1());
								textFieldAnswer2Card2.setText(currentQuestion
										.getAnswer2());
								textFieldAnswer3Card2.setText(currentQuestion
										.getAnswer3());
								textFieldAnswer4Card2.setText(currentQuestion
										.getAnswer4());
								textFieldAnswer5Card2.setText(currentQuestion
										.getAnswer5());

								if (currentQuestion.getAnswer1().equals("")) {
									checkBox.setEnabled(false);
								}
								if (currentQuestion.getAnswer2().equals("")) {
									checkBox_1.setEnabled(false);
								}
								if (currentQuestion.getAnswer3().equals("")) {
									checkBox_2.setEnabled(false);
								}
								if (currentQuestion.getAnswer4().equals("")) {
									checkBox_3.setEnabled(false);
								}
								if (currentQuestion.getAnswer5().equals("")) {
									checkBox_4.setEnabled(false);
								}
								boolean[] answers = currentQuestion
										.getStudentAnswer();
								if (answers != null) {
									checkBox.setSelected(answers[0]);
									checkBox_1.setSelected(answers[1]);
									checkBox_2.setSelected(answers[2]);
									checkBox_3.setSelected(answers[3]);
									checkBox_4.setSelected(answers[4]);
								} else {
									checkBox.setSelected(false);
									checkBox_1.setSelected(false);
									checkBox_2.setSelected(false);
									checkBox_3.setSelected(false);
									checkBox_4.setSelected(false);
								}

							}
							if (currentQuestion.getType() == 2) {
								CardLayout cl = (CardLayout) (panelCards
										.getLayout());
								cl.show(panelCards, "2");
								// lblInputAnswer.setText("Input Answer");
								String text = currentQuestion
										.getStudentTextAnswer();
								if (text != null) {
									webTextAreaEssayAnswer.setText(text);
								} else {
									webTextAreaEssayAnswer.setText("");
								}
							}

							if (webList_1.getSelectedIndex() == 0) {
								wbtnPrev.setVisible(false);
							} else {
								wbtnPrev.setVisible(true);
							}
							if (webList_1.getSelectedIndex() == model.getSize() - 1) {
//								wbtnNext.setText("Submit");
							} else {
//								wbtnNext.setText("Next");
							}
						}
					}
				});
		
		quizEvent = new QuizEvent();
		quizEvent.addEventListener(new QuizEventListener() {

			@Override
			public void quizStoped(QuizStop e) {
				JOptionPane.showMessageDialog(null,
						"Quiz stopped by the teacher!");
				if (timer != null) {
					if (timer.isRunning()) {
						timer.stop();
					}
				}
				dispose();
			}

			@Override
			public void questionAnswered(QuizResponse e) {

			}

			@Override
			public void quizFinished(QuizFinished e) {

			}

			@Override
			public UUID getQuizId() {
				return currentRun.getQuizId();
			}
		});
	}
	
	void updateDisplay() {
		NumberFormat format = NumberFormat.getInstance();

		long now = System.currentTimeMillis(); // current time in ms
		long elapsed = now - lastUpdate; // ms elapsed since last update
		remaining -= elapsed; // adjust remaining time
		lastUpdate = now; // remember this update time

		// Convert remaining milliseconds to mm:ss format and display
		if (remaining < 0) {
			remaining = 0;
		}
		int minutes = (int) (remaining / 60000);
		int seconds = (int) ((remaining % 60000) / 1000);
		textFieldTime.setText(format.format(minutes) + ":"
				+ format.format(seconds));

		// If we've completed the countdown beep and display new page
		if (remaining == 0) {
			// Stop updating now.
			JOptionPane.showMessageDialog(null, "Times Up!");
			timer.stop();
			dispose();
		}
	}

	public void setQuiz(Quiz quiz) {
		currentRun.setQuiz(quiz);
	}

	public void setServer(String serv) {
		server = serv;
	}

	public void setReceivePort(int receivePort) {
		listenPort = receivePort;
	}

	public static void main(String[] args) {
		Quiz_Window test = new Quiz_Window(new Run());
		test.setVisible(true);
	}
}
