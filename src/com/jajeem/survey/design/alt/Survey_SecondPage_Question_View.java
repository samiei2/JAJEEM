package com.jajeem.survey.design.alt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
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
import com.jajeem.events.SurveyResponse;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.Config;

@SuppressWarnings("serial")
public class Survey_SecondPage_Question_View extends Survey_AbstractViews {

	private WebTable webTable;
	private WebComboBox webComboBox;
	private WebTextArea webTextArea;
	private WebTextField webTextField;
	private Survey_SecondPage parentPanel;
	private Question currentQuestion;
	private WebTextField webTextField_2;

	private Timer timer; // Updates the count every second
	private ArrayList<ArrayList<SurveyResponse>> surveyResponse;
	private ArrayList<Student> studentList;
	private Survey currentSurvey;

	/**
	 * Create the panel.
	 */
	public Survey_SecondPage_Question_View(Survey_SecondPage parent) {
		parentPanel = parent;
		studentList = new ArrayList<>();
		surveyResponse = parentPanel.getSurveyResponse();
		currentSurvey = parentPanel.getCurrentSurvey();

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
		webTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"#", "Answer" }));
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
		webLabel_3.setText("Survey Info.");

		WebLabel webLabel_5 = new WebLabel();
		webLabel_5.setText("Direction ");

		webTextField_2 = new WebTextField();
		webTextField_2.setEnabled(false);
		webTextField_2.setEditable(false);
		GroupLayout gl_webPanel_3 = new GroupLayout(webPanel_3);
		gl_webPanel_3.setHorizontalGroup(
			gl_webPanel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_webPanel_3.createSequentialGroup()
							.addComponent(webLabel_5, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_webPanel_3.setVerticalGroup(
			gl_webPanel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(webLabel_5, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		webPanel_3.setLayout(gl_webPanel_3);
		GroupLayout gl_webPanel_2 = new GroupLayout(webPanel_2);
		gl_webPanel_2.setHorizontalGroup(
			gl_webPanel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_2.createSequentialGroup()
					.addComponent(webPanel_3, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_webPanel_2.setVerticalGroup(
			gl_webPanel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_webPanel_2.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(webPanel_3, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
		);
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
				if(currentSurvey != null){
				currentQuestion = currentSurvey.getQuestionList().get(webComboBox.getSelectedIndex());
				webTextArea.setText(currentQuestion.getTitle());
				if(currentQuestion.getType() == 0){
					webTextField.setText("Single Choice");
				}
				else if(currentQuestion.getType() == 1){
					webTextField.setText("Multiple Choice");
				}
				else {
					webTextField.setText("Essay");
				}
				
				DefaultTableModel model = (DefaultTableModel) webTable.getModel();
				for (int i = 0; i < webTable.getRowCount(); i++) {
					model.removeRow(i);
				}
				
				if(webComboBox.getSelectedIndex() != -1 && surveyResponse.size() != 0){
					for (int i = 0; i < surveyResponse.get(webComboBox.getSelectedIndex()).size(); i++) {
						SurveyResponse ex = surveyResponse.get(webComboBox.getSelectedIndex()).get(i);
						Student student = ex.getStudent();
						Question question = ex.getQuestion();
						
						String StudentOption = "";
						if(question.getType() == 0){ // setting student's answer
							if(question.getStudentAnswer()[0])
								StudentOption = "First Option";
							if(question.getStudentAnswer()[1])
								StudentOption = "Second Option";
							if(question.getStudentAnswer()[2])
								StudentOption = "Third Option";
							if(question.getStudentAnswer()[3])
								StudentOption = "Fourth Option";
							if(question.getStudentAnswer()[4])
								StudentOption = "Fifth Option";
							if(StudentOption == "")
								StudentOption = "None Selected";
						}
						else if(question.getType() == 1){
							if(question.getStudentAnswer()[0])
								StudentOption += "First Option,";
							if(question.getStudentAnswer()[1])
								StudentOption += "Second Option,";
							if(question.getStudentAnswer()[2])
								StudentOption += "Third Option,";
							if(question.getStudentAnswer()[3])
								StudentOption += "Fourth Option,";
							if(question.getStudentAnswer()[4])
								StudentOption += "Fifth Option";
							if(StudentOption == "")
								StudentOption = "None Selected";
						}
						else
							StudentOption = question.getStudentTextAnswer();
						
						
						
						if(currentSurvey.getQuestionList().get(webComboBox.getSelectedIndex()).getType() == 0 ||
								currentSurvey.getQuestionList().get(webComboBox.getSelectedIndex()).getType() == 1){
								webTable.setModel(new DefaultTableModel(
										new Object[][] {
										},
										new String[] {
											"#", "Rates"
										}
									));
								webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
								int totalNumofStudents = studentList.size();
								for (int k = 1; k <= 5; k++) { // for each choice of the
									int rate = 0;
									for (int j = 0; j < surveyResponse.get(webComboBox.getSelectedIndex()).size(); j++) { //for each students answer of that question
										StudentOption = "";
										if(currentSurvey.getQuestionList().get(webComboBox.getSelectedIndex()).getType() == 0){
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[0])
												StudentOption = "First Option";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[1])
												StudentOption = "Second Option";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[2])
												StudentOption = "Third Option";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[3])
												StudentOption = "Fourth Option";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[4])
												StudentOption = "Fifth Option";
											if(StudentOption == "")
												StudentOption = "None Selected";
										}
										else if(currentSurvey.getQuestionList().get(webComboBox.getSelectedIndex()).getType() == 1){
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[0])
												StudentOption += "First Option ";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[1])
												StudentOption += "Second Option ";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[2])
												StudentOption += "Third Option ";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[3])
												StudentOption += "Fourth Option ";
											if(surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentAnswer()[4])
												StudentOption += "Fifth Option ";
											if(StudentOption == "")
												StudentOption = "None Selected";
										}
										else{
											StudentOption = surveyResponse.get(webComboBox.getSelectedIndex()).get(j).getQuestion().getStudentTextAnswer();
										}
										if(k == 1 && StudentOption.contains("First Option"))
											rate++;
										if(k == 2 && StudentOption.contains("Second Option"))
											rate++;	
										if(k == 3 && StudentOption.contains("Third Option"))
											rate++;	
										if(k == 4 && StudentOption.contains("Fourth Option"))
											rate++;	
										if(k == 5 && StudentOption.contains("Fifth Option"))
											rate++;	
									}
									((DefaultTableModel)webTable.getModel()).addRow(new Object[]{
											"Choice " + k,
											rate + " of " + totalNumofStudents
									});
								}
							}
							else{
								webTable.setModel(new DefaultTableModel(
										new Object[][] {
										},
										new String[] {
											"#", "Answer"
										}
									));
									webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
								for (int k = 0; k < surveyResponse.get(webComboBox.getSelectedIndex()).size(); k++) {
									System.out.println(student.getId());
									((DefaultTableModel)webTable.getModel()).addRow(new Object[]{
											k+1,
											question.getStudentTextAnswer()
									});
								}
							}
					}
				}
				}
			}
			
		});
	}

	public void clearSurvey() {
		webTextArea.clear();
		webTextField.clear();
		webTextField_2.clear();
		DefaultTableModel model = (DefaultTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
		webTable.updateUI();
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}

		currentSurvey = null;
		currentQuestion = null;
		surveyResponse.clear();
		surveyResponse = null;
		webComboBox.removeAllItems();
	}

	@SuppressWarnings("unchecked")
	public void LoadSurvey(Survey survey) {
		currentSurvey = survey;
		surveyResponse = parentPanel.getSurveyResponse();
		if (currentSurvey != null) {
			webTextField_2.setText(currentSurvey.getTitle());
			
			for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
				webComboBox.addItem("Question " + (i + 1));
			}

			webComboBox.setSelectedIndex(0);
		}
	}

	protected void StopSurveyCommand() {
		try {
			new Config();
			ServerService serv = new ServerService();
			com.jajeem.command.model.StopSurveyCommand cmd = new com.jajeem.command.model.StopSurveyCommand(
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

	public void QuestionAnswered(SurveyResponse e) {
		Question question = e.getQuestion();
		Student student = e.getStudent();
		boolean found = false;
		for (int i = 0; i < studentList.size(); i++) {
			if(student.getId() == studentList.get(i).getId())
				found = true;
		}
		if(!found)
			studentList.add(student);
		
		found = false;
		
		if(currentQuestion != null && question != null && student != null){
			int index = -1;
//			System.out.println(currentSurvey.getQuestionList().size());
			for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {// find question index in the response list
//				System.out.println(i+","+currentSurvey.getQuestionList().get(i).getId()+","+currentSurvey.getQuestionList().size());
				if(currentSurvey.getQuestionList().get(i).getId() == question.getId()){
					index = i;
					break;
				}
			}
			for (int i = 0; i < surveyResponse.size(); i++) {// search in results,if this question is already answered by this student,then update,otherwise save
				for (int j = 0; j < surveyResponse.get(i).size(); j++) { // search in student's response list of the question surveyresponse.get(i) = list of responses of students who answered this question
					if(surveyResponse.get(i).get(j).getStudent().getId() == student.getId() 
					&& surveyResponse.get(i).get(j).getQuestion().getId() == question.getId()){
						surveyResponse.get(i).set(j, e);
						found = true;
						break;
					}
				}
			}
			if(!found)
				surveyResponse.get(index).add(e);
			
			if(question.getId() == currentQuestion.getId()){// if the student id is equal to current students id then show it's result otherwise just save it
				DefaultTableModel model = (DefaultTableModel) webTable.getModel(); 
				String StudentOption = "";
				for (int i = 0; i < webTable.getRowCount(); i++) {
					model.removeRow(i);
				}
				
				if(currentSurvey.getQuestionList().get(index).getType() == 0 ||
					currentSurvey.getQuestionList().get(index).getType() == 1){
					webTable.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"#", "Rates"
							}
						));
//					webTable.getColumnModel().getColumn(1).setHeaderValue("Rates");
//					webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
//					((DefaultTableModel)webTable.getModel()).fireTableStructureChanged();
//					((DefaultTableModel)webTable.getModel()).fireTableDataChanged();
					int totalNumofStudents = studentList.size();
					for (int i = 1; i <= 5; i++) { // for each choice of the
						int rate = 0;
						for (int j = 0; j < surveyResponse.get(index).size(); j++) { //for each students answer of that question
							StudentOption = "";
							if(currentSurvey.getQuestionList().get(index).getType() == 0){
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[0])
									StudentOption = "First Option";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[1])
									StudentOption = "Second Option";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[2])
									StudentOption = "Third Option";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[3])
									StudentOption = "Fourth Option";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[4])
									StudentOption = "Fifth Option";
								if(StudentOption == "")
									StudentOption = "None Selected";
							}
							else if(currentSurvey.getQuestionList().get(index).getType() == 1){
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[0])
									StudentOption += "First Option ";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[1])
									StudentOption += "Second Option ";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[2])
									StudentOption += "Third Option ";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[3])
									StudentOption += "Fourth Option ";
								if(surveyResponse.get(index).get(j).getQuestion().getStudentAnswer()[4])
									StudentOption += "Fifth Option ";
								if(StudentOption == "")
									StudentOption = "None Selected";
							}
							else{
								StudentOption = surveyResponse.get(index).get(j).getQuestion().getStudentTextAnswer();
							}
							if(i == 1 && StudentOption.contains("First Option"))
								rate++;
							if(i == 2 && StudentOption.contains("Second Option"))
								rate++;	
							if(i == 3 && StudentOption.contains("Third Option"))
								rate++;	
							if(i == 4 && StudentOption.contains("Fourth Option"))
								rate++;	
							if(i == 5 && StudentOption.contains("Fifth Option"))
								rate++;	
						}
						((DefaultTableModel)webTable.getModel()).addRow(new Object[]{
								"Choice " + i,
								rate + " of " + totalNumofStudents
						});
					}
				}
				else{
					webTable.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"#", "Answer"
							}
						));
						webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
					for (int i = 0; i < surveyResponse.get(index).size(); i++) {
						System.out.println(student.getId());
						((DefaultTableModel)webTable.getModel()).addRow(new Object[]{
								i+1,
								question.getStudentTextAnswer()
						});
					}
				}
				
			}
		}
	}
	
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
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
