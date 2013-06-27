package com.jajeem.survey.design;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.InetAddress;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
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
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Student;
import com.jajeem.events.SurveyResponse;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.util.Config;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SurveyTab_2_Question_View extends WebPanel {

	private Question currentQuestion;
	private WebTextField webTextField;
	private WebTextArea webTextArea;
	private WebComboBox webComboBox;
	private WebTextField webTextField_1;
	private Timer timer; // Updates the count every second
	private long remaining; // How many milliseconds remain in the countdown.
	private long lastUpdate; // When count was last updated
	private int total;
	private SurveyTab_2 parentPanel;
	private ArrayList<ArrayList<SurveyResponse>> surveyResponse;
	private ArrayList<Student> studentList;
	private Survey currentSurvey;
	private WebTable webTable;
	
	/**
	 * Create the panel.
	 */
	public SurveyTab_2_Question_View(SurveyTab_2 Panel_Bottom_2) {
		this.parentPanel = Panel_Bottom_2;
		surveyResponse = parentPanel.getSurveyResponse();
		currentSurvey = parentPanel.getCurrentSurvey();
		studentList = new ArrayList<>();
		webComboBox = new WebComboBox();
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
						String QuestionOption = "";
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
		
		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");
		
		webTextField = new WebTextField();
		webTextField.setEnabled(false);
		webTextField.setEditable(false);
		
		WebLabel wblblQuestion = new WebLabel();
		wblblQuestion.setText("Question");
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebPanel webPanel_1 = new WebPanel();
		
		WebLabel wblblQuestion_1 = new WebLabel();
		wblblQuestion_1.setText("Question");
		
		WebLabel wblblResults = new WebLabel();
		wblblResults.setText("Results");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 732, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
								.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
							.addGap(156)
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 272, Short.MAX_VALUE)
							.addGap(9))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(706, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
									.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel_1, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setEditable(false);
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Comment", "#"
			}
		));
		webScrollPane_1.setViewportView(webTable);
		webPanel_1.setLayout(gl_webPanel_1);
		
		WebLabel wblblSurveyInfo = new WebLabel();
		wblblSurveyInfo.setText("Survey Info.");
		
		WebLabel wblblDirection = new WebLabel();
		wblblDirection.setText("Direction ");
		
		webTextField_1 = new WebTextField();
		webTextField_1.setEditable(false);
		webTextField_1.setEnabled(false);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(wblblSurveyInfo, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_1, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblSurveyInfo, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(52, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		
		webTextArea = new WebTextArea();
		webTextArea.setEnabled(false);
		webTextArea.setEditable(false);
		webScrollPane.setViewportView(webTextArea);
		setLayout(groupLayout);
		
	}
	
	public void LoadSurvey(Survey survey){
		currentSurvey = survey;
		surveyResponse = parentPanel.getSurveyResponse();
		if(currentSurvey != null){
			webTextField_1.setText(currentSurvey.getTitle());
			for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
				webComboBox.addItem("Question " + (i+1));
			}
			
			webComboBox.setSelectedIndex(0);
			
		}
	}

	public SurveyTab_2 getParentPanel() {
		return parentPanel;
	}

	public void setParentPanel(SurveyTab_2 parentPanel) {
		this.parentPanel = parentPanel;
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
	
	@SuppressWarnings("rawtypes")
	public void clearSurvey() {
		webTextArea.clear();
		webTextField.clear();
		webTextField_1.clear();
		DefaultTableModel model = (DefaultTableModel) webTable.getModel();
		model.getDataVector().clear();
		for (int i = 0; i < webTable.getRowCount(); i++) {
			model.removeRow(i);
		}
		model.fireTableDataChanged();
		webTable.updateUI();
		if(timer != null && timer.isRunning()){
			timer.stop();
		}
		
		currentSurvey = null;
		currentQuestion = null;
		surveyResponse.clear();
		studentList.clear();
//		surveyResponse = null;
		webComboBox.removeAllItems();
		
		
	}
}


