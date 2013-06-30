package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizResponse;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.Config;

@SuppressWarnings("serial")
public class Quiz_SecondPage_Question_View extends Quiz_AbstractViews {

	private WebTable webTable;
	private WebComboBox webComboBox;
	private WebTextArea webTextArea;
	private WebTextField webTextField;
	private Quiz_SecondPage parentPanel;
	private Question currentQuestion;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;

	private Timer timer; // Updates the count every second
	private long remaining; // How many milliseconds remain in the countdown.
	private long lastUpdate; // When count was last updated
	private int total;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private Quiz currentQuiz;

	/**
	 * Create the panel.
	 */
	public Quiz_SecondPage_Question_View(Quiz_SecondPage parent) {
		parentPanel = parent;
		quizResponse = parentPanel.getQuizResponse();
		currentQuiz = parentPanel.getCurrentQuiz();

		WebPanel webPanel = new WebPanel();

		WebPanel webPanel_4 = new WebPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webPanel_4,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																780,
																Short.MAX_VALUE)
														.addComponent(
																webPanel,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																780,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webPanel, GroupLayout.PREFERRED_SIZE,
								143, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webPanel_4, GroupLayout.PREFERRED_SIZE,
								262, Short.MAX_VALUE).addContainerGap()));

		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		webPanel_4.add(webScrollPane_1, BorderLayout.CENTER);

		webTable = new WebTable();
		webTable.setModel(new WebTableModel(new Object[][] {}, new String[] {
				"#", "Id", "Name", "Correct Answer", "Answer" }));
		webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		webTable.getColumnModel().getColumn(0).setMaxWidth(35);
		webTable.getColumnModel().getColumn(1).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(1).setMaxWidth(217);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(2).setMaxWidth(217);
		webTable.getColumnModel().getColumn(3).setPreferredWidth(85);
		webTable.getColumnModel().getColumn(3).setMaxWidth(217);
		webTable.getColumnModel().getColumn(4).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(4).setMaxWidth(217);
		webScrollPane_1.setViewportView(webTable);

		WebPanel webPanel_1 = new WebPanel();

		WebPanel webPanel_2 = new WebPanel();
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(gl_webPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE,
								368, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(webPanel_2, GroupLayout.PREFERRED_SIZE,
								402, Short.MAX_VALUE)));
		gl_webPanel.setVerticalGroup(gl_webPanel
				.createParallelGroup(Alignment.TRAILING)
				.addComponent(webPanel_1, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						gl_webPanel
								.createSequentialGroup()
								.addComponent(webPanel_2,
										GroupLayout.DEFAULT_SIZE, 143,
										Short.MAX_VALUE).addGap(11)));

		WebPanel webPanel_3 = new WebPanel();
		webPanel_3
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		WebLabel webLabel_3 = new WebLabel();
		webLabel_3.setText("Quiz Info.");

		WebLabel webLabel_4 = new WebLabel();
		webLabel_4.setText("Time Left");

		WebLabel webLabel_5 = new WebLabel();
		webLabel_5.setText("Direction ");

		webTextField_1 = new WebTextField();
		webTextField_1.setEnabled(false);
		webTextField_1.setEditable(false);

		webTextField_2 = new WebTextField();
		webTextField_2.setEnabled(false);
		webTextField_2.setEditable(false);
		GroupLayout gl_webPanel_3 = new GroupLayout(webPanel_3);
		gl_webPanel_3
				.setHorizontalGroup(gl_webPanel_3
						.createParallelGroup(Alignment.LEADING)
						.addGap(0, 483, Short.MAX_VALUE)
						.addGroup(
								gl_webPanel_3
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_webPanel_3
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																webLabel_3,
																GroupLayout.PREFERRED_SIZE,
																75,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_webPanel_3
																		.createSequentialGroup()
																		.addGroup(
																				gl_webPanel_3
																						.createParallelGroup(
																								Alignment.TRAILING,
																								false)
																						.addComponent(
																								webLabel_4,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								webLabel_5,
																								Alignment.LEADING,
																								GroupLayout.DEFAULT_SIZE,
																								55,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_webPanel_3
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								webTextField_1,
																								GroupLayout.PREFERRED_SIZE,
																								147,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								webTextField_2,
																								GroupLayout.DEFAULT_SIZE,
																								400,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		gl_webPanel_3
				.setVerticalGroup(gl_webPanel_3
						.createParallelGroup(Alignment.LEADING)
						.addGap(0, 115, Short.MAX_VALUE)
						.addGroup(
								gl_webPanel_3
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(webLabel_3,
												GroupLayout.PREFERRED_SIZE, 18,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_3
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																webLabel_5,
																GroupLayout.PREFERRED_SIZE,
																17,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webTextField_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_webPanel_3
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																webLabel_4,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webTextField_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(22, Short.MAX_VALUE)));
		webPanel_3.setLayout(gl_webPanel_3);
		GroupLayout gl_webPanel_2 = new GroupLayout(webPanel_2);
		gl_webPanel_2.setHorizontalGroup(gl_webPanel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel_2
						.createSequentialGroup()
						.addComponent(webPanel_3, GroupLayout.PREFERRED_SIZE,
								394, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(99, Short.MAX_VALUE)));
		gl_webPanel_2.setVerticalGroup(gl_webPanel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_webPanel_2
						.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(webPanel_3, GroupLayout.PREFERRED_SIZE,
								115, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		webPanel_2.setLayout(gl_webPanel_2);

		WebLabel webLabel = new WebLabel();
		webLabel.setText("Question");

		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("Question Type");

		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("Question");

		webComboBox = new WebComboBox();

		webTextField = new WebTextField();
		webTextField.setEnabled(false);
		webTextField.setEditable(false);

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1
				.setHorizontalGroup(gl_webPanel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel_1
										.createSequentialGroup()
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_webPanel_1
																		.createSequentialGroup()
																		.addComponent(
																				webLabel,
																				GroupLayout.PREFERRED_SIZE,
																				43,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(31)
																		.addComponent(
																				webComboBox,
																				GroupLayout.PREFERRED_SIZE,
																				114,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_webPanel_1
																		.createSequentialGroup()
																		.addGroup(
																				gl_webPanel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								webLabel_1,
																								GroupLayout.PREFERRED_SIZE,
																								70,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								webLabel_2,
																								GroupLayout.PREFERRED_SIZE,
																								43,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(4)
																		.addGroup(
																				gl_webPanel_1
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								webScrollPane,
																								GroupLayout.DEFAULT_SIZE,
																								284,
																								Short.MAX_VALUE)
																						.addComponent(
																								webTextField,
																								GroupLayout.PREFERRED_SIZE,
																								154,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));
		gl_webPanel_1
				.setVerticalGroup(gl_webPanel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel_1
										.createSequentialGroup()
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																webLabel,
																GroupLayout.PREFERRED_SIZE,
																21,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webComboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(6)
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_webPanel_1
																		.createSequentialGroup()
																		.addGap(5)
																		.addComponent(
																				webLabel_1,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																webTextField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_webPanel_1
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_webPanel_1
																		.createSequentialGroup()
																		.addGap(8)
																		.addComponent(
																				webLabel_2,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_webPanel_1
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webScrollPane,
																				GroupLayout.DEFAULT_SIZE,
																				74,
																				Short.MAX_VALUE)))
										.addContainerGap()));

		webTextArea = new WebTextArea();
		webTextArea.setEditable(false);
		webTextArea.setEnabled(false);
		webScrollPane.setViewportView(webTextArea);
		webPanel_1.setLayout(gl_webPanel_1);
		webPanel.setLayout(gl_webPanel);
		setLayout(groupLayout);

		initEvents();

	}

	private void initEvents() {
		webComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Quiz currentQuiz = parentPanel.getParentPanel().getCurrentRun()
						.getQuiz();
				if (currentQuiz != null && webComboBox.getSelectedIndex()!=-1) {
					System.out.println(webComboBox.getSelectedIndex());
					currentQuestion = currentQuiz.getQuestionList().get(
							webComboBox.getSelectedIndex());
					webTextArea.setText(currentQuestion.getTitle());
					if (currentQuestion.getType() == 0) {
						webTextField.setText("Single Choice");
					} else if (currentQuestion.getType() == 1) {
						webTextField.setText("Multiple Choice");
					} else {
						webTextField.setText("Essay");
					}

					DefaultTableModel model = (DefaultTableModel) webTable
							.getModel();
					for (int i = 0; i < webTable.getRowCount(); i++) {
						model.removeRow(i);
					}

					if (webComboBox.getSelectedIndex() != -1
							&& quizResponse.size() != 0) {
						for (int i = 0; i < quizResponse.get(
								webComboBox.getSelectedIndex()).size(); i++) {
							QuizResponse ex = quizResponse.get(
									webComboBox.getSelectedIndex()).get(i);
							Student student = ex.getStudent();
							Question question = ex.getQuestion();
							ImageIcon imgToolTip = null;
							try {
								imgToolTip = new ImageIcon(
										ImageIO.read(Quiz_SecondPage_Question_View.class
												.getResourceAsStream("/icons/bullet-red.png")));
								if (ex.getQuestion().isResponseValid())
									imgToolTip = new ImageIcon(
											ImageIO.read(Quiz_SecondPage_Question_View.class
													.getResourceAsStream("/icons/bullet-green.png")));
							} catch (Exception exp) {
								JajeemExcetionHandler.logError(exp);
							}

							String StudentOption = "";
							String QuestionOption = "";
							if (question.getType() == 0) { // setting student's
															// answer
								if (question.getStudentAnswer()[0])
									StudentOption = "First Option";
								if (question.getStudentAnswer()[1])
									StudentOption = "Second Option";
								if (question.getStudentAnswer()[2])
									StudentOption = "Third Option";
								if (question.getStudentAnswer()[3])
									StudentOption = "Fourth Option";
								if (question.getStudentAnswer()[4])
									StudentOption = "Fifth Option";
								if (StudentOption == "")
									StudentOption = "None Selected";
							} else if (question.getType() == 1) {
								if (question.getStudentAnswer()[0])
									StudentOption += "First Option,";
								if (question.getStudentAnswer()[1])
									StudentOption += "Second Option,";
								if (question.getStudentAnswer()[2])
									StudentOption += "Third Option,";
								if (question.getStudentAnswer()[3])
									StudentOption += "Fourth Option,";
								if (question.getStudentAnswer()[4])
									StudentOption += "Fifth Option";
								if (StudentOption == "")
									StudentOption = "None Selected";
							} else
								StudentOption = question.getStudentTextAnswer();

							Question temp2 = currentQuestion;
							if (temp2.getType() == 0) { // setting questions
														// correct answer
								if (temp2.getCorrectAnswer()[0])
									QuestionOption = "First Option";
								if (temp2.getCorrectAnswer()[1])
									QuestionOption = "Second Option";
								if (temp2.getCorrectAnswer()[2])
									QuestionOption = "Third Option";
								if (temp2.getCorrectAnswer()[3])
									QuestionOption = "Fourth Option";
								if (temp2.getCorrectAnswer()[4])
									QuestionOption = "Fifth Option";
								if (QuestionOption == "")
									QuestionOption = "None Selected";
							} else if (temp2.getType() == 0) {
								if (temp2.getCorrectAnswer()[0])
									QuestionOption += "First Option,";
								if (temp2.getCorrectAnswer()[1])
									QuestionOption += "Second Option,";
								if (temp2.getCorrectAnswer()[2])
									QuestionOption += "Third Option,";
								if (temp2.getCorrectAnswer()[3])
									QuestionOption += "Fourth Option,";
								if (temp2.getCorrectAnswer()[4])
									QuestionOption += "Fifth Option";
								if (QuestionOption == "")
									QuestionOption = "None Selected";
							} else
								QuestionOption = "N/A";

							model.addRow(new Object[] { imgToolTip,
									student.getId(), student.getFullName(),
									QuestionOption, StudentOption });
						}
					}
				}
			}
		});
	}

	public void clearQuiz() {
		webTextArea.clear();
		webTextField.clear();
		webTextField_1.clear();
		webTextField_2.clear();
		WebTableModel model = (WebTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
		webTable.updateUI();
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}

		currentQuiz = null;
		currentQuestion = null;
		quizResponse.clear();
		quizResponse = null;
		webComboBox.removeAllItems();
	}

	@SuppressWarnings("unchecked")
	public void LoadQuiz(Quiz quiz) {
		currentQuiz = quiz;
		quizResponse = parentPanel.getQuizResponse();
		if (currentQuiz != null) {
			webTextField_2.setText(currentQuiz.getTitle());
			webTextField_1.setText(String.valueOf(currentQuiz.getTime()));

			// ///Setting the timer
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					updateDisplay();
				}

				private void updateDisplay() {
					NumberFormat format = NumberFormat.getInstance();

					long now = System.currentTimeMillis(); // current time in ms
					long elapsed = now - lastUpdate; // ms elapsed since last
														// update
					remaining -= elapsed; // adjust remaining time
					lastUpdate = now; // remember this update time
					// Convert remaining milliseconds to mm:ss format and
					// display
					if (remaining < 0)
						remaining = 0;
					int minutes = (int) (remaining / 60000);
					int seconds = (int) ((remaining % 60000) / 1000);
					webTextField_1.setText(format.format(minutes) + ":"
							+ format.format(seconds));

					// If we've completed the countdown beep and display new
					// page
					if (remaining == 0) {
						// Stop updating now.
						timer.stop();
						try {
							StopQuizCommand();
						} catch (NumberFormatException e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}

					}
				}
			};

			if (currentQuiz.getTime() != 0) {
				webTextField_1.setText(String.valueOf(currentQuiz.getTime())
						.concat(":00"));
				remaining = currentQuiz.getTime() * 60000;
				timer = new Timer(1000, taskPerformer);
				timer.setInitialDelay(0);
				lastUpdate = System.currentTimeMillis();
				timer.start();
			} else {
				webTextField_1.setText("");
			}
			// webTextField_2.setText(String.valueOf(new
			// SimpleDateFormat("dd/MMM/yyyy HH:mm").format(Calendar.getInstance().getTime())));

			for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
				webComboBox.addItem("Question " + (i + 1));
			}

			webComboBox.setSelectedIndex(0);
		}
	}

	protected void StopQuizCommand() {
		try {
			new Config();
			ServerService serv = new ServerService();
			com.jajeem.command.model.StopQuizCommand cmd = new com.jajeem.command.model.StopQuizCommand(
					InetAddress.getLocalHost().getHostAddress(),
					Config.getParam("broadcastingIp"), Integer.parseInt(Config
							.getParam("port")));
			serv.send(cmd);
		} catch (NumberFormatException e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}

	}

	public void QuestionAnswered(QuizResponse e) {
		Question question = e.getQuestion();
		Student student = e.getStudent();
		boolean found = false;

		if (currentQuestion != null && question != null && student != null) {
			int index = -1;
			for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {// find
																			// question
																			// index
																			// in
																			// the
																			// response
																			// list
				if (currentQuiz.getQuestionList().get(i).getId() == question
						.getId()) {
					index = i;
					break;
				}
			}
			for (int i = 0; i < quizResponse.size(); i++) {// search in
															// results,if this
															// question is
															// already answered
															// by this
															// student,then
															// update,otherwise
															// save
				for (int j = 0; j < quizResponse.get(i).size(); j++) { // search
																		// in
																		// student's
																		// response
																		// list
																		// of
																		// the
																		// question
																		// quizresponse.get(i)
																		// =
																		// list
																		// of
																		// responses
																		// of
																		// students
																		// who
																		// answered
																		// this
																		// question
					if (quizResponse.get(i).get(j).getStudent().getId() == student
							.getId()
							&& quizResponse.get(i).get(j).getQuestion().getId() == question
									.getId()) {
						quizResponse.get(i).set(j, e);
						found = true;
						break;
					}
				}
			}
			if (!found)
				quizResponse.get(index).add(e);

			if (question.getId() == currentQuestion.getId()) {// if the student
																// id is equal
																// to current
																// students id
																// then show
																// it's result
																// otherwise
																// just save it
				DefaultTableModel model = (DefaultTableModel) webTable
						.getModel();
				String StudentOption = "";
				String QuestionOption = "";
				if (question.getType() == 0) { // setting student's answer
					if (question.getStudentAnswer()[0])
						StudentOption = "First Option";
					if (question.getStudentAnswer()[1])
						StudentOption = "Second Option";
					if (question.getStudentAnswer()[2])
						StudentOption = "Third Option";
					if (question.getStudentAnswer()[3])
						StudentOption = "Fourth Option";
					if (question.getStudentAnswer()[4])
						StudentOption = "Fifth Option";
					if (StudentOption == "")
						StudentOption = "None Selected";
				} else if (question.getType() == 1) {
					if (question.getStudentAnswer()[0])
						StudentOption += "First Option,";
					if (question.getStudentAnswer()[1])
						StudentOption += "Second Option,";
					if (question.getStudentAnswer()[2])
						StudentOption += "Third Option,";
					if (question.getStudentAnswer()[3])
						StudentOption += "Fourth Option,";
					if (question.getStudentAnswer()[4])
						StudentOption += "Fifth Option";
					if (StudentOption == "")
						StudentOption = "None Selected";
				} else
					StudentOption = question.getStudentTextAnswer();

				Question temp2 = currentQuiz.getQuestionList().get(index);
				if (temp2.getType() == 0) { // setting questions correct answer
					if (temp2.getCorrectAnswer()[0])
						QuestionOption = "First Option";
					if (temp2.getCorrectAnswer()[1])
						QuestionOption = "Second Option";
					if (temp2.getCorrectAnswer()[2])
						QuestionOption = "Third Option";
					if (temp2.getCorrectAnswer()[3])
						QuestionOption = "Fourth Option";
					if (temp2.getCorrectAnswer()[4])
						QuestionOption = "Fifth Option";
					if (QuestionOption == "")
						QuestionOption = "None Selected";
				} else if (temp2.getType() == 0) {
					if (temp2.getCorrectAnswer()[0])
						QuestionOption += "First Option,";
					if (temp2.getCorrectAnswer()[1])
						QuestionOption += "Second Option,";
					if (temp2.getCorrectAnswer()[2])
						QuestionOption += "Third Option,";
					if (temp2.getCorrectAnswer()[3])
						QuestionOption += "Fourth Option,";
					if (temp2.getCorrectAnswer()[4])
						QuestionOption += "Fifth Option";
					if (QuestionOption == "")
						QuestionOption = "None Selected";
				} else
					QuestionOption = "N/A";

				ImageIcon imgToolTip = null;
				try {
					imgToolTip = new ImageIcon(
							ImageIO.read(Quiz_SecondPage_Question_View.class
									.getResourceAsStream("/icons/bullet-red.png")));
					if (e.getQuestion().isResponseValid())
						imgToolTip = new ImageIcon(
								ImageIO.read(Quiz_SecondPage_Question_View.class
										.getResourceAsStream("/icons/bullet-green.png")));
				} catch (Exception exp) {
					JajeemExcetionHandler.logError(exp);
				}

				for (int i = 0; i < webTable.getRowCount(); i++) {
					model.removeRow(i);
				}

				for (int i = 0; i < quizResponse.get(index).size(); i++) {
					model.addRow(new Object[] { imgToolTip, student.getId(),
							student.getFullName(), QuestionOption,
							StudentOption });
				}
			}
		}
	}

	@SuppressWarnings("serial")
	class WebTableModel extends DefaultTableModel {
		public WebTableModel(Object[][] objects, String[] strings) {
			super(objects, strings);
		}

		@Override
		public Class<?> getColumnClass(int arg0) {
			// TODO Auto-generated method stub
			if (arg0 == 0)
				return Icon.class;
			return super.getColumnClass(arg0);
		}
	}
}
