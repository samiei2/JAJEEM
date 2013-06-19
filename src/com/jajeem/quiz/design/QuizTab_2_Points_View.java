package com.jajeem.quiz.design;


import java.awt.Component;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizResponse;
import com.jajeem.quiz.model.Quiz;

@SuppressWarnings("serial")
public class QuizTab_2_Points_View extends WebPanel {

	private WebTable webTable;
	private ArrayList<Student> studentList = new ArrayList<>();
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private Quiz currentQuiz;
	private QuizTab_2 parentPanel;
	/**
	 * Create the panel.
	 * @param quizTab_2 
	 */
	public QuizTab_2_Points_View(QuizTab_2 quizTab_2) {
		parentPanel = quizTab_2;
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Score"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(1).setMaxWidth(95);
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);
		
	}

	public void QuestionAnswered(QuizResponse e) {
		clearTable();
		if(studentList.size() == 0){
			studentList.add(e.getStudent());
		}
		else{
			for (int i = 0; i < studentList.size(); i++) {
				if(studentList.get(i).getId() != e.getStudent().getId() && i == studentList.size()){
					studentList.add(e.getStudent());
				}
			}
		}
		
		
		
		DefaultTableModel model = (DefaultTableModel)webTable.getModel();
		for (int i = 0; i < studentList.size(); i++) {
			int score = 0;
			Student student = studentList.get(i);
			for (int j = 0; j < quizResponse.size(); j++) {
				for (int k = 0; k < quizResponse.get(j).size(); k++) {
					if(student.getId() == quizResponse.get(j).get(k).getStudent().getId()){
						if(quizResponse.get(j).get(k).getQuestion().isResponseValid()){
							score += quizResponse.get(j).get(k).getQuestion().getPoint();
						}
					}
				}
			}
			model.addRow(new Object[]{
//					student.getFirstName() + " " + student.getLastName(),
					student.getId(),
					score
			});
		}
	}

	public void clearQuiz() {
		clearTable();
	}
	
	public void clearTable(){
		((DefaultTableModel)webTable.getModel()).getDataVector().clear();
		((DefaultTableModel)webTable.getModel()).fireTableDataChanged();
		currentQuiz = null;
		quizResponse.clear();
		quizResponse = null;
	}
	
	public void LoadQuiz(Quiz currentQuiz2) {
		currentQuiz = currentQuiz2;
		quizResponse = parentPanel.getQuizResponse();
	}
}
