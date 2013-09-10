package com.jajeem.quiz.design.alt;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizResponse;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;

public class Quiz_SecondPage_Student_View extends Quiz_AbstractViews {
	private JTextField textField;
	private WebTable webTable;
	private WebComboBox webComboBox;

	private WebLabel wblblScore;
	private Quiz_SecondPage parentPanel;

	private Student currentStudent;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private ArrayList<Student> studentList;
	private Quiz currentQuiz;
	private int id = 1;

	/**
	 * Create the panel.
	 * 
	 * @param parent
	 * 
	 * @param panel_Bottom_2
	 */
	public Quiz_SecondPage_Student_View(Quiz_SecondPage parent) {
		parentPanel = parent;
		studentList = new ArrayList<>();
		WebLabel wblblStudent = new WebLabel();
		wblblStudent.setText("Student");

		webComboBox = new WebComboBox();

		WebLabel wblblStudentName = new WebLabel();
		wblblStudentName.setText("Student Name");

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setColumns(10);

		WebPanel webPanel = new WebPanel();

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		WebLabel webLabel = new WebLabel();
		webLabel.setText("Results");

		WebLabel wblblStudentScore = new WebLabel();
		wblblStudentScore.setText("Student Score : ");

		wblblScore = new WebLabel();
		wblblScore.setText("?");
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel
				.setHorizontalGroup(gl_webPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel
										.createSequentialGroup()
										.addGroup(
												gl_webPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_webPanel
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				webLabel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(308)
																		.addComponent(
																				wblblStudentScore,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				wblblScore,
																				GroupLayout.PREFERRED_SIZE,
																				49,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																webScrollPane,
																GroupLayout.DEFAULT_SIZE,
																760,
																Short.MAX_VALUE))
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
																Alignment.LEADING,
																false)
														.addComponent(
																wblblScore,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																gl_webPanel
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				webLabel,
																				GroupLayout.PREFERRED_SIZE,
																				13,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				wblblStudentScore,
																				GroupLayout.DEFAULT_SIZE,
																				17,
																				Short.MAX_VALUE)))
										.addGap(10)
										.addComponent(webScrollPane,
												GroupLayout.DEFAULT_SIZE, 209,
												Short.MAX_VALUE)
										.addContainerGap()));

		webTable = new WebTable();
		webTable.setEditable(false);
		webTable.setModel(new WebTableModel(new Object[][] {}, new String[] {
				"#", "Title", "Correct Answer", "Answer" }));
		webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		webTable.getColumnModel().getColumn(0).setMaxWidth(35);
		webTable.getColumnModel().getColumn(1).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(1).setMaxWidth(217);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(85);
		webTable.getColumnModel().getColumn(2).setMaxWidth(217);
		webTable.getColumnModel().getColumn(3).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(3).setMaxWidth(217);
		webScrollPane.setViewportView(webTable);
		webPanel.setLayout(gl_webPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.addComponent(
																												wblblStudentName,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(22)
																										.addComponent(
																												wblblStudent,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								webComboBox,
																								GroupLayout.PREFERRED_SIZE,
																								172,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textField,
																								GroupLayout.PREFERRED_SIZE,
																								223,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				webPanel,
																				GroupLayout.PREFERRED_SIZE,
																				770,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(22)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wblblStudent,
																GroupLayout.PREFERRED_SIZE,
																13,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																webComboBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																wblblStudentName,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(webPanel,
												GroupLayout.PREFERRED_SIZE,
												258, Short.MAX_VALUE)
										.addContainerGap()));
		setLayout(groupLayout);
		initEvents();
	}

	private void initEvents() {
		webComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (quizResponse != null) {
					currentStudent = new Student();
					
					if (webComboBox.getSelectedIndex() != -1)
						currentStudent = studentList.get(webComboBox.getSelectedIndex());
//						currentStudent.setId(Integer.parseInt(webComboBox
//								.getSelectedItem().toString()));

					WebTableModel model = (WebTableModel) webTable
							.getModel();
					model.getDataVector().removeAllElements();
					model.fireTableDataChanged();

					for (int i = 0; i < quizResponse.size(); i++) { // iterates
																	// through
																	// questions
						for (int j = 0; j < quizResponse.get(i).size(); j++) { // iterates
																				// through
																				// responses
							QuizResponse ex = quizResponse.get(i).get(j);
							Student student = ex.getStudent();
							Question question = ex.getQuestion();
							if (currentStudent.getId() == student.getId()) {
								ImageIcon imgToolTip = null;
								try {
									imgToolTip = new ImageIcon(
											ImageIO.read(Quiz_SecondPage_Student_View.class
													.getResourceAsStream("/icons/bullet-red.png")));
									if (ex.getQuestion().isResponseValid())
										imgToolTip = new ImageIcon(
												ImageIO.read(Quiz_SecondPage_Student_View.class
														.getResourceAsStream("/icons/bullet-green.png")));
								} catch (Exception exp) {
									JajeemExcetionHandler.logError(exp);
								}

								String StudentOption = "";
								String QuestionOption = "";
								if (question.getType() == 0) { // setting
																// student's
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
									StudentOption = question
											.getStudentTextAnswer();

								Question temp2 = currentQuiz.getQuestionList()
										.get(i);
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
								} else if (temp2.getType() == 1) {
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
										question.getTitle(), QuestionOption,
										StudentOption });
							}
						}
					}

					int score = 0;
					for (int i = 0; i < quizResponse.size(); i++) {
						for (int j = 0; j < quizResponse.get(i).size(); j++) {
							Student temp = quizResponse.get(i).get(j)
									.getStudent();
							Question tempq = quizResponse.get(i).get(j)
									.getQuestion();
							if (temp.getId() == currentStudent.getId()) {
								if (tempq.isResponseValid()) {
									score += tempq.getPoint();
								}
							}
						}
					}
					wblblScore.setText(String.valueOf(score));
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void QuestionAnswered(QuizResponse e) {
		Question question = e.getQuestion();
		Student student = e.getStudent();
		boolean found = false;
//		for (int i = 0; i < (webComboBox.getModel()).getSize(); i++) {
//			if (webComboBox.getItemAt(i).toString()
//					.equals(String.valueOf(student.getId())))
//				found = true;
//		}
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getId() == student.getId())
				found = true;
		}
//		if (!found) {
//			webComboBox.addItem(student.getId());
//			id = student.getId();
//		}
		if (!found) {
			studentList.add(student);
			webComboBox.addItem(student.getFullName());
			id = student.getId();
		}
		if (webComboBox.getSelectedIndex() == -1)
			webComboBox.setSelectedIndex(0);

		found = false;
		if (currentStudent != null && question != null && student != null) {
			int index = -1;
			for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {// find
																			// question
																			// index
																			// in
																			// the
																			// response
																			// list
				if (currentQuiz.getQuestionList().get(i).getId().equals(question
						.getId()))
					index = i;
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
							&& quizResponse.get(i).get(j).getQuestion().getId().equals(question
									.getId())) {
						quizResponse.get(i).set(j, e);
						found = true;
						break;
					}
				}
			}
			if (!found)
				quizResponse.get(index).add(e);

			if (student.getId() == currentStudent.getId()) {// if the student id
															// is equal to
															// current students
															// id then show it's
															// result otherwise
															// just save it
				WebTableModel model = (WebTableModel) webTable.getModel();
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				for (int i = 0; i < quizResponse.size(); i++) { // iterate
																// through
																// questions
					for (int j = 0; j < quizResponse.get(i).size(); j++) { // iterate
																			// through
																			// answers
						QuizResponse resp = quizResponse.get(i).get(j);
						Question tempq = resp.getQuestion();
						Student temps = resp.getStudent();

						if (student.getId() == temps.getId()) {
							String StudentOption = "";
							String QuestionOption = "";
							if (tempq.getType() == 0) { // setting student's
														// answer
								if (tempq.getStudentAnswer()[0])
									StudentOption = "First Option";
								if (tempq.getStudentAnswer()[1])
									StudentOption = "Second Option";
								if (tempq.getStudentAnswer()[2])
									StudentOption = "Third Option";
								if (tempq.getStudentAnswer()[3])
									StudentOption = "Fourth Option";
								if (tempq.getStudentAnswer()[4])
									StudentOption = "Fifth Option";
								if (StudentOption == "")
									StudentOption = "None Selected";
							} else if (tempq.getType() == 1) {
								if (tempq.getStudentAnswer()[0])
									StudentOption += "First Option,";
								if (tempq.getStudentAnswer()[1])
									StudentOption += "Second Option,";
								if (tempq.getStudentAnswer()[2])
									StudentOption += "Third Option,";
								if (tempq.getStudentAnswer()[3])
									StudentOption += "Fourth Option,";
								if (tempq.getStudentAnswer()[4])
									StudentOption += "Fifth Option";
								if (StudentOption == "")
									StudentOption = "None Selected";
							} else
								StudentOption = tempq.getStudentTextAnswer();

							Question temp2 = currentQuiz.getQuestionList().get(
									i);
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
							} else if (temp2.getType() == 1) {
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
										ImageIO.read(Quiz_SecondPage_Student_View.class
												.getResourceAsStream("/icons/bullet-red.png")));
								if (tempq.isResponseValid())
									imgToolTip = new ImageIcon(
											ImageIO.read(Quiz_SecondPage_Student_View.class
													.getResourceAsStream("/icons/bullet-green.png")));
							} catch (Exception exp) {
								JajeemExcetionHandler.logError(exp);
							}

							model.addRow(new Object[] { imgToolTip,
									tempq.getTitle(), QuestionOption,
									StudentOption });
						}
					}
				}
			}

			int score = 0;
			for (int i = 0; i < quizResponse.size(); i++) {
				for (int j = 0; j < quizResponse.get(i).size(); j++) {
					Student temp = quizResponse.get(i).get(j).getStudent();
					Question tempq = quizResponse.get(i).get(j).getQuestion();
					if (temp.getId() == student.getId()) {
						if (tempq.isResponseValid()) {
							score += tempq.getPoint();
						}
					}
				}
			}

			wblblScore.setText(String.valueOf(score));
		}
	}
	
	public void LoadQuiz(Quiz currentQuiz2) {
		currentQuiz = currentQuiz2;
		quizResponse = parentPanel.getQuizResponse();
	}

	@SuppressWarnings("rawtypes")
	public void clearQuiz() {
		WebTableModel model = (WebTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
		webComboBox.removeAllItems();
		studentList.clear();
		currentStudent = null;
		currentQuiz = null;
		quizResponse.clear();
		quizResponse = null;
	}
	
	@SuppressWarnings("serial")
	class WebTableModel extends DefaultTableModel {
		public WebTableModel(Object[][] objects, String[] strings) {
			super(objects, strings);
		}

		@Override
		public Class<?> getColumnClass(int arg0) {
			if (arg0 == 0)
				return Icon.class;
			return super.getColumnClass(arg0);
		}
	}
}
