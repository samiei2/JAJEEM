package com.jajeem.quiz.design.alt;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.core.model.Student;
import com.jajeem.events.QuizResponse;
import com.jajeem.quiz.model.Quiz;

@SuppressWarnings("serial")
public class Quiz_SecondPage_Points_View extends Quiz_AbstractViews {

	private WebTable webTable;
	private Quiz_SecondPage parentPanel;

	private ArrayList<Student> studentList;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;

	/**
	 * Create the panel.
	 * 
	 * @param parent
	 * @param quizTab_2
	 */
	public Quiz_SecondPage_Points_View(Quiz_SecondPage parent) {
		parentPanel = parent;
		studentList = new ArrayList<>();
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE,
								717, Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE,
								284, Short.MAX_VALUE).addContainerGap()));

		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Name", "Score" }) {
			boolean[] columnEditables = new boolean[] { false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(1).setMaxWidth(95);
		webScrollPane.setViewportView(webTable);
		setLayout(groupLayout);

	}

	public void QuestionAnswered(QuizResponse e) {
		((DefaultTableModel) webTable.getModel()).getDataVector().clear();
		((DefaultTableModel) webTable.getModel()).fireTableDataChanged();
		boolean found = false;
		for (int i = 0; i < studentList.size(); i++) {
			if (e.getStudent().getId() == studentList.get(i).getId()) {
				found = true;
			}
		}
		if (!found) {
			studentList.add(e.getStudent());
		}

		DefaultTableModel model = (DefaultTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
		webTable.updateUI();
		for (int i = 0; i < studentList.size(); i++) {
			int score = 0;
			Student student = studentList.get(i);
			for (int j = 0; j < quizResponse.size(); j++) {
				for (int k = 0; k < quizResponse.get(j).size(); k++) {
					if (student.getId() == quizResponse.get(j).get(k)
							.getStudent().getId()) {
						if (quizResponse.get(j).get(k).getQuestion()
								.isResponseValid()) {
							score += quizResponse.get(j).get(k).getQuestion()
									.getPoint();
						}
					}
				}
			}

			model.addRow(new Object[] { student.getFullName(), score });
			webTable.updateUI();
		}
	}

	public void clearQuiz() {
		studentList.clear();
		clearTable();
	}

	public void clearTable() {
		((DefaultTableModel) webTable.getModel()).getDataVector().clear();
		((DefaultTableModel) webTable.getModel()).fireTableDataChanged();
		if(quizResponse != null)
			quizResponse.clear();
		quizResponse = null;
	}

	public void LoadQuiz(Quiz currentQuiz2) {
		quizResponse = parentPanel.getQuizResponse();
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
