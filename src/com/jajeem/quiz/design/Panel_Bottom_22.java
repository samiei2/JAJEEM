package com.jajeem.quiz.design;


import com.alee.laf.panel.WebPanel;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.label.WebLabel;
import com.alee.laf.combobox.WebComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import com.alee.laf.table.WebTable;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizResponse;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;

public class Panel_Bottom_22 extends WebPanel {
	private JTextField textField;
	private WebTable webTable;
	private WebComboBox webComboBox;
	
	private Student currentStudent;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private Quiz currentQuiz;
	private Panel_Bottom_2 parentPanel;

	/**
	 * Create the panel.
	 * @param panel_Bottom_2 
	 */
	public Panel_Bottom_22(Panel_Bottom_2 panel_Bottom_2) {
		this.parentPanel = panel_Bottom_2;
		quizResponse = parentPanel.getQuizResponse();
		currentQuiz = parentPanel.getCurrentQuiz();
		
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
		
		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("[Total]");
		
		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("?");
		
		WebLabel webLabel_3 = new WebLabel();
		webLabel_3.setText("[Answered]");
		
		WebLabel webLabel_4 = new WebLabel();
		webLabel_4.setText("?");
		
		WebLabel webLabel_5 = new WebLabel();
		webLabel_5.setText("[Correct]");
		
		WebLabel webLabel_6 = new WebLabel();
		webLabel_6.setText("?");
		
		WebLabel webLabel_7 = new WebLabel();
		webLabel_7.setText("Correct Rate");
		
		WebLabel webLabel_8 = new WebLabel();
		webLabel_8.setText("?");
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(209)
							.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_4, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webLabel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_6, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(webLabel_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_8, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 916, Short.MAX_VALUE))
					.addGap(24))
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(webLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(webLabel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_6, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_7, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_8, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Question", "Correct Answer", "Student Answer"
			}
		));
		webTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		webTable.getColumnModel().getColumn(0).setMaxWidth(30);
		webScrollPane.setViewportView(webTable);
		webPanel.setLayout(gl_webPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(wblblStudentName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(22)
									.addComponent(wblblStudent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 940, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblStudent, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblStudentName, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 258, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);

	}

	public void QuestionAnswered(QuizResponse e) {
		Question question = e.getQuestion();
		Student student = e.getStudent();
		boolean found = false;
		if(currentStudent != null && question != null && student != null){
			int index = -1;
			for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {// find question index in the response list
				if(currentQuiz.getQuestionList().get(i).getId() == question.getId())
					index = i;
			}
			for (int i = 0; i < quizResponse.size(); i++) {// search in results,if this question is already answered by this student,then update,otherwise save
				for (int j = 0; j < quizResponse.get(i).size(); j++) { // search in student's response list of the question quizresponse.get(i) = list of responses of students who answered this question
					if(quizResponse.get(i).get(j).getStudent().getId() == student.getId()){
						quizResponse.get(i).set(j, e);
						found = true;
						break;
					}
				}
			}
			if(!found)
				quizResponse.get(index).add(e);
			
			if(student.getId() == currentStudent.getId()){// if the student id is equal to current students id then show it's result otherwise just save it
				DefaultTableModel model = (DefaultTableModel) webTable.getModel(); 
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
				}
				else if(question.getType() == 0){
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
				}
				else
					StudentOption = question.getStudentTextAnswer();
				
				Question temp2 = currentQuiz.getQuestionList().get(index);
				if(temp2.getType() == 0){ // setting questions correct answer
					if(temp2.getCorrectAnswer()[0])
						QuestionOption = "First Option";
					if(temp2.getCorrectAnswer()[1])
						QuestionOption = "Second Option";
					if(temp2.getCorrectAnswer()[2])
						QuestionOption = "Third Option";
					if(temp2.getCorrectAnswer()[3])
						QuestionOption = "Fourth Option";
					if(temp2.getCorrectAnswer()[4])
						QuestionOption = "Fifth Option";
				}
				else if(temp2.getType() == 0){
					if(temp2.getCorrectAnswer()[0])
						QuestionOption += "First Option,";
					if(temp2.getCorrectAnswer()[1])
						QuestionOption += "Second Option,";
					if(temp2.getCorrectAnswer()[2])
						QuestionOption += "Third Option,";
					if(temp2.getCorrectAnswer()[3])
						QuestionOption += "Fourth Option,";
					if(temp2.getCorrectAnswer()[4])
						QuestionOption += "Fifth Option";
				}
				else
					QuestionOption = "N/A";
				
				ImageIcon imgToolTip = new ImageIcon("icons/bullet-red.png");
				if(e.getQuestion().isResponseValid())
					imgToolTip = new ImageIcon("icons/bullet-green.png");
				
				for (int i = 0; i < webTable.getRowCount(); i++) {
					model.removeRow(i);
				}
				
				for (int i = 0; i < quizResponse.size(); i++) {
					for (int j = 0; j < quizResponse.get(i).size(); j++) {
						if(student.getId() == quizResponse.get(i).get(j).getStudent().getId()){
							model.addRow(new Object[]{
									imgToolTip,
									question.getTitle(),
									QuestionOption,
									StudentOption
							});
						}
					}
				}
			}
		}
	}
}
