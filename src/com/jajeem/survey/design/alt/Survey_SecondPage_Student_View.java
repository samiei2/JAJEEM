package com.jajeem.survey.design.alt;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.model.Student;
import com.jajeem.events.SurveyResponse;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.ui.combobox.JajeemComboBox;

public class Survey_SecondPage_Student_View extends Survey_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private WebTable webTable;
	private JajeemComboBox webComboBox;
	private Survey_SecondPage parentPanel;

	private Student currentStudent;
	private ArrayList<ArrayList<SurveyResponse>> surveyResponse;
	private Survey currentSurvey;
	private int id = 1;

	/**
	 * Create the panel.
	 * 
	 * @param parent
	 * 
	 * @param panel_Bottom_2
	 */
	public Survey_SecondPage_Student_View(Survey_SecondPage parent) {
		parentPanel = parent;
		WebLabel wblblStudent = new WebLabel();
		wblblStudent.setText("Student");

		webComboBox = new JajeemComboBox();

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
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																webScrollPane,
																GroupLayout.DEFAULT_SIZE,
																760,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_webPanel.setVerticalGroup(gl_webPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 13,
								GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE,
								235, Short.MAX_VALUE).addContainerGap()));

		webTable = new WebTable();
		webTable.setEditable(false);
		webTable.setModel(new WebTableModel(new Object[][] {}, new String[] {
				"#", "Title", "Answer" }));
		webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		webTable.getColumnModel().getColumn(0).setMaxWidth(35);
		webTable.getColumnModel().getColumn(1).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(1).setMaxWidth(217);
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
				if (surveyResponse != null) {
					currentStudent = new Student();
					if (webComboBox.getSelectedIndex() != -1) {
						currentStudent.setId(Integer.parseInt(webComboBox
								.getSelectedItem().toString()));
					}

					WebTableModel model = (WebTableModel) webTable.getModel();
					model.getDataVector().removeAllElements();
					model.fireTableDataChanged();

					for (int i = 0; i < surveyResponse.size(); i++) { // iterates
																		// through
																		// questions
						for (int j = 0; j < surveyResponse.get(i).size(); j++) { // iterates
																					// through
																					// responses
							SurveyResponse ex = surveyResponse.get(i).get(j);
							Student student = ex.getStudent();
							Question question = ex.getQuestion();
							if (currentStudent.getId() == student.getId()) {

								String StudentOption = "";
								if (question.getType() == 0) { // setting
																// student's
																// answer
									if (question.getStudentAnswer()[0]) {
										StudentOption = "First Option";
									}
									if (question.getStudentAnswer()[1]) {
										StudentOption = "Second Option";
									}
									if (question.getStudentAnswer()[2]) {
										StudentOption = "Third Option";
									}
									if (question.getStudentAnswer()[3]) {
										StudentOption = "Fourth Option";
									}
									if (question.getStudentAnswer()[4]) {
										StudentOption = "Fifth Option";
									}
									if (StudentOption == "") {
										StudentOption = "None Selected";
									}
								} else if (question.getType() == 1) {
									if (question.getStudentAnswer()[0]) {
										StudentOption += "First Option,";
									}
									if (question.getStudentAnswer()[1]) {
										StudentOption += "Second Option,";
									}
									if (question.getStudentAnswer()[2]) {
										StudentOption += "Third Option,";
									}
									if (question.getStudentAnswer()[3]) {
										StudentOption += "Fourth Option,";
									}
									if (question.getStudentAnswer()[4]) {
										StudentOption += "Fifth Option";
									}
									if (StudentOption == "") {
										StudentOption = "None Selected";
									}
								} else {
									StudentOption = question
											.getStudentTextAnswer();
								}

								model.addRow(new Object[] {
										webTable.getRowCount() == 0 ? 1
												: webTable.getRowCount() + 1,
										question.getTitle(), StudentOption });
							}
						}
					}
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void QuestionAnswered(SurveyResponse e) {
		Question question = e.getQuestion();
		Student student = e.getStudent();
		boolean found = false;
		for (int i = 0; i < (webComboBox.getModel()).getSize(); i++) {
			if (webComboBox.getItemAt(i).toString()
					.equals(String.valueOf(student.getId()))) {
				found = true;
			}
		}
		if (!found) {
			webComboBox.addItem(student.getId());
			id = student.getId();
		}
		if (webComboBox.getSelectedIndex() == -1) {
			webComboBox.setSelectedIndex(0);
		}

		found = false;
		if (currentStudent != null && question != null && student != null) {
			int index = -1;
			for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {// find
																				// question
																				// index
																				// in
																				// the
																				// response
																				// list
				if (currentSurvey.getQuestionList().get(i).getId()
						.equals(question.getId())) {
					index = i;
				}
			}
			for (int i = 0; i < surveyResponse.size(); i++) {// search in
																// results,if
																// this
																// question is
																// already
																// answered
																// by this
																// student,then
																// update,otherwise
																// save
				for (int j = 0; j < surveyResponse.get(i).size(); j++) { // search
																			// in
																			// student's
																			// response
																			// list
																			// of
																			// the
																			// question
																			// surveyresponse.get(i)
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
					if (surveyResponse.get(i).get(j).getStudent().getId() == student
							.getId()
							&& surveyResponse.get(i).get(j).getQuestion()
									.getId().equals(question.getId())) {
						surveyResponse.get(i).set(j, e);
						found = true;
						break;
					}
				}
			}
			if (!found) {
				surveyResponse.get(index).add(e);
			}

			if (student.getId() == currentStudent.getId()) {// if the student id
															// is equal to
															// current students
															// id then show it's
															// result otherwise
															// just save it
				WebTableModel model = (WebTableModel) webTable.getModel();
				model.getDataVector().removeAllElements();
				model.fireTableDataChanged();
				for (int i = 0; i < surveyResponse.size(); i++) { // iterate
																	// through
																	// questions
					for (int j = 0; j < surveyResponse.get(i).size(); j++) { // iterate
																				// through
																				// answers
						SurveyResponse resp = surveyResponse.get(i).get(j);
						Question tempq = resp.getQuestion();
						Student temps = resp.getStudent();

						if (student.getId() == temps.getId()) {
							String StudentOption = "";
							if (tempq.getType() == 0) { // setting student's
														// answer
								if (tempq.getStudentAnswer()[0]) {
									StudentOption = "First Option";
								}
								if (tempq.getStudentAnswer()[1]) {
									StudentOption = "Second Option";
								}
								if (tempq.getStudentAnswer()[2]) {
									StudentOption = "Third Option";
								}
								if (tempq.getStudentAnswer()[3]) {
									StudentOption = "Fourth Option";
								}
								if (tempq.getStudentAnswer()[4]) {
									StudentOption = "Fifth Option";
								}
								if (StudentOption == "") {
									StudentOption = "None Selected";
								}
							} else if (tempq.getType() == 1) {
								if (tempq.getStudentAnswer()[0]) {
									StudentOption += "First Option,";
								}
								if (tempq.getStudentAnswer()[1]) {
									StudentOption += "Second Option,";
								}
								if (tempq.getStudentAnswer()[2]) {
									StudentOption += "Third Option,";
								}
								if (tempq.getStudentAnswer()[3]) {
									StudentOption += "Fourth Option,";
								}
								if (tempq.getStudentAnswer()[4]) {
									StudentOption += "Fifth Option";
								}
								if (StudentOption == "") {
									StudentOption = "None Selected";
								}
							} else {
								StudentOption = tempq.getStudentTextAnswer();
							}

							model.addRow(new Object[] {
									webTable.getRowCount() == 0 ? 1 : webTable
											.getRowCount() + 1,
									tempq.getTitle(), StudentOption });
						}
					}
				}
			}
		}
	}

	public void LoadSurvey(Survey currentSurvey2) {
		currentSurvey = currentSurvey2;
		surveyResponse = parentPanel.getSurveyResponse();
	}

	public void clearSurvey() {
		WebTableModel model = (WebTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
		webComboBox.removeAllItems();
		currentStudent = null;
		currentSurvey = null;
		surveyResponse.clear();
		surveyResponse = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@SuppressWarnings("serial")
	class WebTableModel extends DefaultTableModel {
		public WebTableModel(Object[][] objects, String[] strings) {
			super(objects, strings);
		}

		@Override
		public Class<?> getColumnClass(int arg0) {
			if (arg0 == 0) {
				return Icon.class;
			}
			return super.getColumnClass(arg0);
		}
	}
}
