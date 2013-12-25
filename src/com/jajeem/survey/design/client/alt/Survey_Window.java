package com.jajeem.survey.design.client.alt;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.list.WebList;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.command.model.FinishedSurveyCommand;
import com.jajeem.command.model.SendSurveyResponseCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Student;
import com.jajeem.events.SurveyEvent;
import com.jajeem.events.SurveyEventListener;
import com.jajeem.events.SurveyFinished;
import com.jajeem.events.SurveyResponse;
import com.jajeem.events.SurveyStop;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.design.client.alt.Quiz_Window;
import com.jajeem.survey.design.alt.CustomSurveyButton;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.ClientSession;

public class Survey_Window extends BaseSurveyClientFrame {

	private WebTextField txtDirection;
	private WebTextField txtTime;
	private WebTextField textField;
	private WebTextField textField_1;
	private WebTextField textField_2;
	private WebTextField textField_3;
	private WebTextField textField_4;
	private WebTextField textField_5;
	private WebTextField textField_6;
	private WebTextField textField_7;
	private WebTextField textField_8;
	private WebTextField textField_9;
	
	protected Question currentQuestion;

	private SurveyEvent surveyEvent;
	private ArrayList<Question> sendQueue = new ArrayList<>();
	// TODO remove code below
	private int sid;
	private Student privateStudent = new Student();
	private Run currentRun;

	private String server;

	private int listenPort;
	
	private DefaultListModel model;
	private WebList webList;
	private WebRadioButton radioButton;
	private WebRadioButton radioButton_1;
	private WebRadioButton radioButton_2;
	private WebRadioButton radioButton_3;
	private WebRadioButton radioButton_4;
	private WebCheckBox webCheckBox;
	private WebCheckBox webCheckBox_1;
	private WebCheckBox webCheckBox_2;
	private WebCheckBox webCheckBox_3;
	private WebCheckBox webCheckBox_4;
	private WebTextArea webTextArea;
	private WebTextArea webTextArea_1;
	private CustomSurveyButton wbtnNext;
	private CustomSurveyButton wbtnPrevious;
	private JLabel lblQuestion;
	private JPanel mainPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Survey_Window frame = new Survey_Window(new Run());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Survey_Window(Run run) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getTopPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
		);
		
		JLabel lblNumberOfQuestions = new JLabel("Number of Questions");
		
		JLabel lblAnswererd = new JLabel("Answered");
		
		JLabel lblDirections = new JLabel("Directions");
		
		txtDirection = new WebTextField();
		txtDirection.setColumns(10);
		
		JLabel lblRemainingTime = new JLabel("Remaining Time");
		
		txtTime = new WebTextField();
		txtTime.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNumberOfQuestions)
							.addPreferredGap(ComponentPlacement.RELATED, 263, Short.MAX_VALUE)
							.addComponent(lblRemainingTime))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAnswererd)
							.addPreferredGap(ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
							.addComponent(txtTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblDirections)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtDirection, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumberOfQuestions)
						.addComponent(lblRemainingTime))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAnswererd)
						.addComponent(txtTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirections)
						.addComponent(txtDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		getTopPane().setLayout(groupLayout);
		
		JPanel panel_1 = new JPanel();
		GroupLayout groupLayout_1 = new GroupLayout(getMainContentPane());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
		);
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		JPanel panel_2 = new JPanel();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
						.addComponent(webScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		lblQuestion = new JLabel("Question ? ");
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		
		JPanel panel_3 = new JPanel();
		
		wbtnNext = new CustomSurveyButton("/icons/noa_en/quiznextbutton.png");
		wbtnNext.setUndecorated(true);
		
		wbtnPrevious = new CustomSurveyButton("/icons/noa_en/quizbackbutton.png");
		wbtnPrevious.setUndecorated(true);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
						.addComponent(webScrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
						.addComponent(lblQuestion, Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(wbtnPrevious, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnNext, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webScrollPane_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnNext, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnPrevious, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		mainPanel = new JPanel();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		mainPanel.setLayout(new CardLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		mainPanel.add(panel_6, "0");
		
		radioButton = new WebRadioButton("");
		
		radioButton_1 = new WebRadioButton("");
		
		radioButton_2 = new WebRadioButton("");
		
		radioButton_3 = new WebRadioButton("");
		
		radioButton_4 = new WebRadioButton("");
		
		textField = new WebTextField();
		textField.setColumns(10);
		
		textField_1 = new WebTextField();
		textField_1.setColumns(10);
		
		textField_2 = new WebTextField();
		textField_2.setColumns(10);
		
		textField_3 = new WebTextField();
		textField_3.setColumns(10);
		
		textField_4 = new WebTextField();
		textField_4.setColumns(10);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(radioButton, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(radioButton, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26))
		);
		panel_6.setLayout(gl_panel_6);
		
		JPanel panel_7 = new JPanel();
		mainPanel.add(panel_7, "1");
		
		webCheckBox = new WebCheckBox();
		
		webCheckBox_1 = new WebCheckBox();
		
		webCheckBox_2 = new WebCheckBox();
		
		webCheckBox_3 = new WebCheckBox();
		
		webCheckBox_4 = new WebCheckBox();
		
		textField_5 = new WebTextField();
		textField_5.setColumns(10);
		
		textField_6 = new WebTextField();
		textField_6.setColumns(10);
		
		textField_7 = new WebTextField();
		textField_7.setColumns(10);
		
		textField_8 = new WebTextField();
		textField_8.setColumns(10);
		
		textField_9 = new WebTextField();
		textField_9.setColumns(10);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_5, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_6, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(webCheckBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_7, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(webCheckBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_8, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(webCheckBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_9, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(webCheckBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(webCheckBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		
		JPanel panel_8 = new JPanel();
		mainPanel.add(panel_8, "2");
		
		WebScrollPane webScrollPane_2 = new WebScrollPane((Component) null);
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane_2, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane_2, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		
		webTextArea_1 = new WebTextArea();
		webScrollPane_2.setViewportView(webTextArea_1);
		panel_8.setLayout(gl_panel_8);
		panel_3.setLayout(gl_panel_3);
		
		webTextArea = new WebTextArea();
		webScrollPane_1.setViewportView(webTextArea);
		panel_2.setLayout(gl_panel_2);
		
		WebList webList = new WebList();
		model = new DefaultListModel();
		webList = new WebList(model);
		webScrollPane.setViewportView(webList);
		panel_1.setLayout(gl_panel_1);
		getMainContentPane().setLayout(groupLayout_1);
		
		initEvents(run);
	}
	
	private void initEvents(Run run) {
		
		// TODO remove code below
		sid = new Random().nextInt(Integer.MAX_VALUE);
		privateStudent.setId(sid);
		if (com.jajeem.util.Session.getStudent() != null) {
			if (com.jajeem.util.Session.getStudent().getFullName() != null
					&& com.jajeem.util.Session.getStudent().getFullName() != "") {
				privateStudent.setFullName(com.jajeem.util.Session.getStudent()
						.getFullName());
			} else {
				privateStudent.setFullName("Anonymous");
			}
		}

		currentRun = run;
		currentRun.setStudent(privateStudent);
		if (currentRun.getId() == null) {
			currentRun.setId(UUID.randomUUID());
		}

		ClientSession.setSurvey_WindowHndl(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				if (currentRun.getSurvey() == null
						|| currentRun.getSurvey().getQuestionList().size() == 0) {
					JOptionPane.showMessageDialog(null,
							"An error occured while opening the survey");
					dispose();
					return;
				}

				for (int i = 0; i < currentRun.getSurvey().getQuestionList()
						.size(); i++) {
					model.addElement("Question " + (i + 1));
				}

				webList.setSelectedIndex(0);

				// webTextField_1.setText(String.valueOf(new
				// SimpleDateFormat("dd/MMM/yyyy HH:mm").format(Calendar.getInstance().getTime())));
				txtDirection.setText(currentRun.getSurvey().getTitle());
			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}
		});
		
		surveyEvent = new SurveyEvent();
		surveyEvent.addEventListener(new SurveyEventListener() {

			@Override
			public void surveyStoped(SurveyStop e) {
				JOptionPane.showMessageDialog(null,
						"Survey stopped by the teacher!");
				dispose();
			}

			@Override
			public void questionAnswered(SurveyResponse e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void surveyFinished(SurveyFinished e) {
				// TODO Auto-generated method stub

			}
		});
		
		ListSelectionModel listSelectionModel = webList.getSelectionModel();
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
													webCheckBox.isSelected(),
													webCheckBox_1.isSelected(),
													webCheckBox_2.isSelected(),
													webCheckBox_3.isSelected(),
													webCheckBox_4.isSelected() });
								}
								if (currentQuestion.getType() == 2) {
									currentQuestion
											.setStudentAnswer(webTextArea_1
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
											SurveyResponse resp = new SurveyResponse(
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
											resp.setSurveyRun(currentRun);
											resp.setListeningPort(listenPort);
											// new
											// SurveyEvent().fireResponseEvent(resp);
											SendSurveyResponseCommand cmd = new SendSurveyResponseCommand(
													InetAddress.getLocalHost()
															.getHostAddress(),
													server, listenPort);
											cmd.setEvent(resp);

											ServerService service = new ServerService();
											service.send(cmd);
										} catch (NumberFormatException e) {
											JajeemExcetionHandler.logError(e);
											e.printStackTrace();
										} catch (Exception e) {
											JajeemExcetionHandler.logError(e);
											e.printStackTrace();
										}
									}

									private Student getStudent() {
										return privateStudent;// TODO correct
																// this code
									}
								}).start();
							}
							// Load Next Question
							currentQuestion = currentRun.getSurvey()
									.getQuestionList()
									.get(webList.getSelectedIndex());
							if (currentQuestion.getResponse().getId() == null) {
								currentQuestion.getResponse().setId(
										UUID.randomUUID());
							}
							if (currentQuestion.getResponse().getRunId() == null) {
								currentQuestion.getResponse().setRunId(
										currentRun.getId());
							}
							lblQuestion.setText("Question "
									+ (webList.getSelectedIndex() + 1));
							webTextArea.setText(currentQuestion.getTitle());

							if (currentQuestion.getType() == 0) {
								CardLayout cl = (CardLayout) (mainPanel
										.getLayout());
								cl.show(mainPanel, "0");
								// lblInputAnswer.setText("Select an answer");
								radioButton.setEnabled(true);
								radioButton_1.setEnabled(true);
								radioButton_2.setEnabled(true);
								radioButton_3.setEnabled(true);
								radioButton_4.setEnabled(true);
								textField.setText(currentQuestion
										.getAnswer1());
								textField_1.setText(currentQuestion
										.getAnswer2());
								textField_2.setText(currentQuestion
										.getAnswer3());
								textField_3.setText(currentQuestion
										.getAnswer4());
								textField_4.setText(currentQuestion
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
								CardLayout cl = (CardLayout) (mainPanel
										.getLayout());
								cl.show(mainPanel, "1");
								// lblInputAnswer.setText("Select answer");
								webCheckBox.setEnabled(true);
								webCheckBox_1.setEnabled(true);
								webCheckBox_2.setEnabled(true);
								webCheckBox_3.setEnabled(true);
								webCheckBox_4.setEnabled(true);

								textField_5.setText(currentQuestion
										.getAnswer1());
								textField_6.setText(currentQuestion
										.getAnswer2());
								textField_7.setText(currentQuestion
										.getAnswer3());
								textField_8.setText(currentQuestion
										.getAnswer4());
								textField_9.setText(currentQuestion
										.getAnswer5());

								if (currentQuestion.getAnswer1().equals("")) {
									webCheckBox.setEnabled(false);
								}
								if (currentQuestion.getAnswer2().equals("")) {
									webCheckBox_1.setEnabled(false);
								}
								if (currentQuestion.getAnswer3().equals("")) {
									webCheckBox_2.setEnabled(false);
								}
								if (currentQuestion.getAnswer4().equals("")) {
									webCheckBox_3.setEnabled(false);
								}
								if (currentQuestion.getAnswer5().equals("")) {
									webCheckBox_4.setEnabled(false);
								}
								boolean[] answers = currentQuestion
										.getStudentAnswer();
								if (answers != null) {
									webCheckBox.setSelected(answers[0]);
									webCheckBox_1.setSelected(answers[1]);
									webCheckBox_2.setSelected(answers[2]);
									webCheckBox_3.setSelected(answers[3]);
									webCheckBox_4.setSelected(answers[4]);
								} else {
									webCheckBox.setSelected(false);
									webCheckBox_1.setSelected(false);
									webCheckBox_2.setSelected(false);
									webCheckBox_3.setSelected(false);
									webCheckBox_4.setSelected(false);
								}

							}
							if (currentQuestion.getType() == 2) {
								CardLayout cl = (CardLayout) (mainPanel
										.getLayout());
								cl.show(mainPanel, "2");
								// lblInputAnswer.setText("Input Answer");
								String text = currentQuestion
										.getStudentTextAnswer();
								if (text != null) {
									webTextArea_1.setText(text);
								} else {
									webTextArea_1.setText("");
								}
							}

							if (webList.getSelectedIndex() == 0) {
								wbtnPrevious.setVisible(false);
							} else {
								wbtnPrevious.setVisible(true);
							}
							if (webList.getSelectedIndex() == model.getSize() - 1) {
//								wbtnNext.setText("Submit");
							} else {
//								wbtnNext.setText("Next");
							}
						}
					}
				});

		radioButton = new WebRadioButton();
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

		radioButton_1 = new WebRadioButton();
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

		radioButton_2 = new WebRadioButton();
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

		radioButton_3 = new WebRadioButton();
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

		radioButton_4 = new WebRadioButton();
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
		
		webCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});
		
		wbtnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (webList.getSelectedIndex() != 0) {
					webList.setSelectedIndex(webList.getSelectedIndex() - 1);
				}
			}
		});
		
		wbtnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (webList.getSelectedIndex() != model.getSize() - 1) {
					webList.setSelectedIndex(webList.getSelectedIndex() + 1);
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
									webCheckBox.isSelected(),
									webCheckBox_1.isSelected(),
									webCheckBox_2.isSelected(),
									webCheckBox_3.isSelected(),
									webCheckBox_4.isSelected() });
						}
						if (currentQuestion.getType() == 2) {
							currentQuestion.setStudentAnswer(webTextArea_1
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
									SurveyResponse resp = new SurveyResponse(
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
									resp.setSurveyRun(currentRun);
									resp.setListeningPort(listenPort);
									new SurveyEvent().fireResponseEvent(resp);
									SendSurveyResponseCommand cmd = new SendSurveyResponseCommand(
											InetAddress.getLocalHost()
													.getHostAddress(), server,
											listenPort);
									cmd.setEvent(resp);

									ServerService service = new ServerService();
									service.send(cmd);
								} catch (NumberFormatException e) {
									JajeemExcetionHandler.logError(e);
									e.printStackTrace();
								} catch (Exception e) {
									JajeemExcetionHandler.logError(e);
									e.printStackTrace();
								}
							}

							private Student getStudent() {
								return privateStudent;// TODO correct this code
							}
						}).start();

						try {
							FinishedSurveyCommand cmd = new FinishedSurveyCommand(
									InetAddress.getLocalHost().getHostAddress(),
									server, listenPort);
							currentRun.setEnd(System.currentTimeMillis());

							cmd.setRun(currentRun);

							ServerService service = new ServerService();
							service.send(cmd);
						} catch (Exception ex) {
							JajeemExcetionHandler.logError(ex,
									Quiz_Window.class);
						}
					}
					dispose();
				}
			}
		});
	}

	public void setSurvey(Survey survey) {
		currentRun.setSurvey(survey);
	}

	public void setServer(String serv) {
		server = serv;
	}

	public void setReceivePort(int receivePort) {
		listenPort = receivePort;
	}
}
