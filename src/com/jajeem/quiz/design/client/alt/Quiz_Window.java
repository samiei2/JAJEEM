package com.jajeem.quiz.design.client.alt;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
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
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizEventListener;
import com.jajeem.events.QuizFinished;
import com.jajeem.events.QuizResponse;
import com.jajeem.events.QuizStop;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
import com.jajeem.util.ClientSession;
import java.awt.Toolkit;
import java.awt.Color;

@SuppressWarnings("serial")
public class Quiz_Window extends WebFrame {

	private WebPanel contentPane;

	private DefaultListModel model;
	private WebTextField webTextField_1;
	private WebList webList;
	// private Quiz currentRun.getQuiz();
	private WebRadioButton webRadioButton;
	private WebRadioButton webRadioButton_1;
	private WebRadioButton webRadioButton_2;
	private WebRadioButton webRadioButton_3;
	private WebRadioButton webRadioButton_4;
	private WebTextField webTextField;
	private WebTextField webTextField_2;
	private WebTextField webTextField_3;
	private WebTextField webTextField_4;
	private WebTextField webTextField_5;
	private WebTextField webTextField_6;
	private WebCheckBox webCheckBox;
	private WebCheckBox webCheckBox_1;
	private WebCheckBox webCheckBox_2;
	private WebCheckBox webCheckBox_3;
	private WebCheckBox webCheckBox_4;
	private WebTextField webTextField_12;
	private WebTextField webTextField_13;
	private WebTextField webTextField_14;
	private WebTextField webTextField_15;
	private WebTextField webTextField_16;

	protected Question currentQuestion;

	private QuizEvent quizEvent;
	private ArrayList<Question> sendQueue = new ArrayList<>();
	// TODO remove code below
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// QuizWindow frame = new QuizWindow();
					// frame.setVisible(true);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Quiz_Window(Run run) {
		setTitle("Quiz");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Quiz_Window.class.getResource("/icons/noa_en/quiz.png")));
		// TODO remove code below
		// sid = new Random().nextInt(Integer.MAX_VALUE);

		privateStudent = com.jajeem.core.design.Student.getStudentModel();

//		if (com.jajeem.util.Session.getStudent() != null)
//			if (com.jajeem.util.Session.getStudent().getFullName() != null
//					&& com.jajeem.util.Session.getStudent().getFullName() != "")
//				privateStudent.setFullName(com.jajeem.util.Session.getStudent()
//						.getFullName());
//			else
//				privateStudent.setFullName("Anonymous");

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

				webList.setSelectedIndex(0);

				// ///Setting the timer
				taskPerformer = new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						updateDisplay();
					}
				};

				if (currentRun.getQuiz().getTime() != 0) {
					webTextField_1.setText(String.valueOf(
							currentRun.getQuiz().getTime()).concat(":00"));
					remaining = currentRun.getQuiz().getTime() * 60000;
					timer = new Timer(1000, taskPerformer);
					timer.setInitialDelay(0);
					lastUpdate = System.currentTimeMillis();
					timer.start();

				} else {
					webTextField_1.setText("");
				}

				// webTextField_1.setText(String.valueOf(new
				// SimpleDateFormat("dd/MMM/yyyy HH:mm").format(Calendar.getInstance().getTime())));
				webTextField.setText(currentRun.getQuiz().getTitle());
			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 0, 720, 700);
		contentPane = new WebPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		final WebLabel wblblQuestion = new WebLabel();
		wblblQuestion.setText("Question ?");

		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);

		final WebPanel mainPanel = new WebPanel(new CardLayout());
		final WebPanel webPanel_1 = new WebPanel();
		WebPanel webPanel_2 = new WebPanel();
		WebPanel webPanel_3 = new WebPanel();
		mainPanel.add(webPanel_1, "0");
		mainPanel.add(webPanel_2, "1");
		mainPanel.add(webPanel_3, "2");

		WebScrollPane webScrollPane_2 = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_3 = new GroupLayout(webPanel_3);
		gl_webPanel_3.setHorizontalGroup(gl_webPanel_3.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel_3
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webScrollPane_2,
								GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
						.addContainerGap()));
		gl_webPanel_3.setVerticalGroup(gl_webPanel_3.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel_3
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webScrollPane_2,
								GroupLayout.PREFERRED_SIZE, 107,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(118, Short.MAX_VALUE)));

		final WebTextArea webTextArea_1 = new WebTextArea();

		webScrollPane_2.setViewportView(webTextArea_1);
		webPanel_3.setLayout(gl_webPanel_3);

		final WebButton wbtnNext = new WebButton();
		wbtnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (webList.getSelectedIndex() != model.getSize() - 1) {
					webList.setSelectedIndex(webList.getSelectedIndex() + 1);
				} else {
					if (currentQuestion != null) {
						if (currentQuestion.getType() == 0) {
							currentQuestion.setStudentAnswer(new boolean[] {
									webRadioButton.isSelected(),
									webRadioButton_1.isSelected(),
									webRadioButton_2.isSelected(),
									webRadioButton_3.isSelected(),
									webRadioButton_4.isSelected() });
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
									QuizResponse resp = new QuizResponse(
											question);
									if (question.getResponse().getId() == null)
										question.getResponse().setId(
												UUID.randomUUID());
									if (question.getResponse().getRunId() == null)
										question.getResponse().setRunId(
												currentRun.getId());
									resp.setQuestion(question);
									resp.setStudent(getStudent());
									resp.setQuizRun(currentRun);
									resp.setListeningPort(listenPort);
									new QuizEvent().fireResponseEvent(resp);
									SendQuizResponseCommand cmd = new SendQuizResponseCommand(
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

						if (timer != null)
							if (timer.isRunning()) {
								timer.stop();
								timer.removeActionListener(taskPerformer);
							}

						try {
							FinishedQuizCommand cmd = new FinishedQuizCommand(
									InetAddress.getLocalHost().getHostAddress(),
									server, listenPort);

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
		wbtnNext.setText("Next");

		final WebButton wbtnPrevious = new WebButton();
		wbtnPrevious.setText("Previous");

		WebButton wbtnPrevious_1 = new WebButton();
		wbtnPrevious_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (webList.getSelectedIndex() != 0) {
					webList.setSelectedIndex(webList.getSelectedIndex() - 1);
				}
			}
		});
		wbtnPrevious_1.setText("Previous");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(webPanel, GroupLayout.PREFERRED_SIZE,
								668, Short.MAX_VALUE)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(webScrollPane,
												GroupLayout.PREFERRED_SIZE,
												156, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				wblblQuestion,
																				GroupLayout.PREFERRED_SIZE,
																				68,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(280))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				webScrollPane_1,
																				GroupLayout.DEFAULT_SIZE,
																				496,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGap(4)
																		.addComponent(
																				mainPanel,
																				GroupLayout.DEFAULT_SIZE,
																				492,
																				Short.MAX_VALUE)
																		.addContainerGap())))
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap(495, Short.MAX_VALUE)
										.addComponent(wbtnPrevious_1,
												GroupLayout.PREFERRED_SIZE, 80,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(wbtnNext,
												GroupLayout.PREFERRED_SIZE, 77,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addComponent(webPanel,
												GroupLayout.PREFERRED_SIZE,
												101, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				wblblQuestion,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webScrollPane_1,
																				GroupLayout.PREFERRED_SIZE,
																				154,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				mainPanel,
																				GroupLayout.DEFAULT_SIZE,
																				355,
																				Short.MAX_VALUE)
																		.addGap(12)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								wbtnNext,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								wbtnPrevious_1,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																webScrollPane,
																GroupLayout.PREFERRED_SIZE,
																455,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		webTextField_2 = new WebTextField();
		webTextField_2.setInputPromptForeground(Color.BLACK);
		webTextField_2.setEnabled(false);
		webTextField_2.setEditable(false);

		webTextField_3 = new WebTextField();
		webTextField_3.setInputPromptForeground(Color.BLACK);
		webTextField_3.setEditable(false);
		webTextField_3.setEnabled(false);

		webTextField_4 = new WebTextField();
		webTextField_4.setInputPromptForeground(Color.BLACK);
		webTextField_4.setEditable(false);
		webTextField_4.setEnabled(false);

		webTextField_5 = new WebTextField();
		webTextField_5.setInputPromptForeground(Color.BLACK);
		webTextField_5.setEditable(false);
		webTextField_5.setEnabled(false);

		webTextField_6 = new WebTextField();
		webTextField_6.setInputPromptForeground(Color.BLACK);
		webTextField_6.setEditable(false);
		webTextField_6.setEnabled(false);

		webRadioButton = new WebRadioButton();
		webRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webRadioButton.setSelected(true);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected() });
			}
		});

		webRadioButton_1 = new WebRadioButton();
		webRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(true);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected() });
			}
		});

		webRadioButton_2 = new WebRadioButton();
		webRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(true);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected() });
			}
		});

		webRadioButton_3 = new WebRadioButton();
		webRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(true);
				webRadioButton_4.setSelected(false);
				currentQuestion.setStudentAnswer(new boolean[] {
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected() });
			}
		});

		webRadioButton_4 = new WebRadioButton();
		webRadioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(true);
				currentQuestion.setStudentAnswer(new boolean[] {
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected() });
			}
		});

		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1
				.setHorizontalGroup(gl_webPanel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel_1
										.createSequentialGroup()
										.addGap(19)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webRadioButton,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_4,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																webTextField_3,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_2,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_4,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_5,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_6,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE))));
		gl_webPanel_1
				.setVerticalGroup(gl_webPanel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webRadioButton,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webTextField_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_4,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_5,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_6,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webRadioButton_4,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		webPanel_1.setLayout(gl_webPanel_1);

		webTextField_12 = new WebTextField();
		webTextField_12.setEnabled(false);
		webTextField_12.setEditable(false);

		webTextField_13 = new WebTextField();
		webTextField_13.setEnabled(false);
		webTextField_13.setEditable(false);

		webTextField_14 = new WebTextField();
		webTextField_14.setEnabled(false);
		webTextField_14.setEditable(false);

		webTextField_15 = new WebTextField();
		webTextField_15.setEnabled(false);
		webTextField_15.setEditable(false);

		webTextField_16 = new WebTextField();
		webTextField_16.setEnabled(false);
		webTextField_16.setEditable(false);

		webCheckBox = new WebCheckBox();
		webCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_1 = new WebCheckBox();
		webCheckBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_2 = new WebCheckBox();
		webCheckBox_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_3 = new WebCheckBox();
		webCheckBox_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});

		webCheckBox_4 = new WebCheckBox();
		webCheckBox_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentQuestion.setStudentAnswer(new boolean[] {
						webCheckBox.isSelected(), webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(), webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected() });
			}
		});
		GroupLayout gl_webPanel_2 = new GroupLayout(webPanel_2);
		gl_webPanel_2
				.setHorizontalGroup(gl_webPanel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel_2
										.createSequentialGroup()
										.addGap(19)
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webCheckBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_4,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																webTextField_12,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_13,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_14,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_15,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE)
														.addComponent(
																webTextField_16,
																GroupLayout.DEFAULT_SIZE,
																291,
																Short.MAX_VALUE))));
		gl_webPanel_2
				.setVerticalGroup(gl_webPanel_2
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webCheckBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webTextField_12,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_13,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_14,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_15,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_3,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webTextField_16,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webCheckBox_4,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		webPanel_2.setLayout(gl_webPanel_2);

		final WebTextArea webTextArea = new WebTextArea();
		webTextArea.setEditable(false);
		webTextArea.setEnabled(false);
		webScrollPane_1.setViewportView(webTextArea);

		model = new DefaultListModel();
		webList = new WebList(model);
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
											webRadioButton.isSelected(),
											webRadioButton_1.isSelected(),
											webRadioButton_2.isSelected(),
											webRadioButton_3.isSelected(),
											webRadioButton_4.isSelected() });
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
											QuizResponse resp = new QuizResponse(
													question);
											if (question.getResponse().getId() == null)
												question.getResponse().setId(
														UUID.randomUUID());
											if (question.getResponse()
													.getRunId() == null)
												question.getResponse()
														.setRunId(
																currentRun
																		.getId());
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
							currentQuestion = currentRun.getQuiz()
									.getQuestionList()
									.get(webList.getSelectedIndex());
							if (currentQuestion.getResponse().getId() == null)
								currentQuestion.getResponse().setId(
										UUID.randomUUID());
							if (currentQuestion.getResponse().getRunId() == null)
								currentQuestion.getResponse().setRunId(
										currentRun.getId());
							wblblQuestion.setText("Question "
									+ (webList.getSelectedIndex() + 1));
							webTextArea.setText(currentQuestion.getTitle());

							if (currentQuestion.getType() == 0) {
								CardLayout cl = (CardLayout) (mainPanel
										.getLayout());
								cl.show(mainPanel, "0");
								// lblInputAnswer.setText("Select an answer");
								webRadioButton.setEnabled(true);
								webRadioButton_1.setEnabled(true);
								webRadioButton_2.setEnabled(true);
								webRadioButton_3.setEnabled(true);
								webRadioButton_4.setEnabled(true);
								webTextField_2.setText(currentQuestion
										.getAnswer1());
								webTextField_3.setText(currentQuestion
										.getAnswer2());
								webTextField_4.setText(currentQuestion
										.getAnswer3());
								webTextField_5.setText(currentQuestion
										.getAnswer4());
								webTextField_6.setText(currentQuestion
										.getAnswer5());

								if (currentQuestion.getAnswer1().equals(""))
									webRadioButton.setEnabled(false);
								if (currentQuestion.getAnswer2().equals(""))
									webRadioButton_1.setEnabled(false);
								if (currentQuestion.getAnswer3().equals(""))
									webRadioButton_2.setEnabled(false);
								if (currentQuestion.getAnswer4().equals(""))
									webRadioButton_3.setEnabled(false);
								if (currentQuestion.getAnswer5().equals(""))
									webRadioButton_4.setEnabled(false);

								boolean[] answers = currentQuestion
										.getStudentAnswer();
								if (answers != null) {
									webRadioButton.setSelected(answers[0]);
									webRadioButton_1.setSelected(answers[1]);
									webRadioButton_2.setSelected(answers[2]);
									webRadioButton_3.setSelected(answers[3]);
									webRadioButton_4.setSelected(answers[4]);
								} else {
									webRadioButton.setSelected(false);
									webRadioButton_1.setSelected(false);
									webRadioButton_2.setSelected(false);
									webRadioButton_3.setSelected(false);
									webRadioButton_4.setSelected(false);
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

								webTextField_12.setText(currentQuestion
										.getAnswer1());
								webTextField_13.setText(currentQuestion
										.getAnswer2());
								webTextField_14.setText(currentQuestion
										.getAnswer3());
								webTextField_15.setText(currentQuestion
										.getAnswer4());
								webTextField_16.setText(currentQuestion
										.getAnswer5());

								if (currentQuestion.getAnswer1().equals(""))
									webCheckBox.setEnabled(false);
								if (currentQuestion.getAnswer2().equals(""))
									webCheckBox_1.setEnabled(false);
								if (currentQuestion.getAnswer3().equals(""))
									webCheckBox_2.setEnabled(false);
								if (currentQuestion.getAnswer4().equals(""))
									webCheckBox_3.setEnabled(false);
								if (currentQuestion.getAnswer5().equals(""))
									webCheckBox_4.setEnabled(false);
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
								if (text != null)
									webTextArea_1.setText(text);
								else
									webTextArea_1.setText("");
							}

							if (webList.getSelectedIndex() == 0)
								wbtnPrevious.setVisible(false);
							else
								wbtnPrevious.setVisible(true);
							if (webList.getSelectedIndex() == model.getSize() - 1)
								wbtnNext.setText("Submit");
							else
								wbtnNext.setText("Next");
						}
					}
				});

		webScrollPane.setViewportView(webList);

		WebLabel wblblNumberOfQuestions = new WebLabel();
		wblblNumberOfQuestions.setText("Number of Questions");

		WebLabel wblblAnswered = new WebLabel();
		wblblAnswered.setText("Answered");

		WebLabel wblblRemainingTime = new WebLabel();
		wblblRemainingTime.setText("Remaining Time");

		WebLabel wblblDirections = new WebLabel();
		wblblDirections.setText("Directions");

		webTextField = new WebTextField();
		webTextField.setEditable(false);
		webTextField.setEnabled(false);

		webTextField_1 = new WebTextField();
		webTextField_1.setEditable(false);
		webTextField_1.setEnabled(false);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel
				.setHorizontalGroup(gl_webPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_webPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																Alignment.TRAILING,
																gl_webPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_webPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								wblblNumberOfQuestions,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								wblblAnswered,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				209,
																				Short.MAX_VALUE)
																		.addGroup(
																				gl_webPanel
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								webTextField_1,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								wblblRemainingTime,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)))
														.addGroup(
																gl_webPanel
																		.createSequentialGroup()
																		.addComponent(
																				wblblDirections,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webTextField,
																				GroupLayout.DEFAULT_SIZE,
																				328,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		gl_webPanel
				.setVerticalGroup(gl_webPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_webPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wblblNumberOfQuestions,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wblblRemainingTime,
																GroupLayout.PREFERRED_SIZE,
																16,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wblblAnswered,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webTextField_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																webTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																wblblDirections,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(60, Short.MAX_VALUE)));
		webPanel.setLayout(gl_webPanel);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);

		quizEvent = new QuizEvent();
		quizEvent.addEventListener(new QuizEventListener() {

			@Override
			public void quizStoped(QuizStop e) {
				JOptionPane.showMessageDialog(null,
						"Quiz stopped by the teacher!");
				if (timer != null)
					if (timer.isRunning())
						timer.stop();
				dispose();
			}

			@Override
			public void questionAnswered(QuizResponse e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void quizFinished(QuizFinished e) {
				// TODO Auto-generated method stub

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
		if (remaining < 0)
			remaining = 0;
		int minutes = (int) (remaining / 60000);
		int seconds = (int) ((remaining % 60000) / 1000);
		webTextField_1.setText(format.format(minutes) + ":"
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
}
