package com.jajeem.quiz.design.alt;


import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.jajeem.events.QuizEvent;
import com.jajeem.events.QuizEventListener;
import com.jajeem.events.QuizFinished;
import com.jajeem.events.QuizResponse;
import com.jajeem.events.QuizStop;
import com.jajeem.quiz.design.QuizTab_2_Student_View;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;


@SuppressWarnings("serial")
public class Quiz_SecondPage extends Quiz_AbstractViews {

	private Quiz_SecondPage_Question_View panel_bottom_21;
	private Quiz_SecondPage_Student_View panel_bottom_22;
	private Quiz_SecondPage_Points_View panel_bottom_23;
	private WebComboBox webComboBox;
	private WebPanel cards;
	private Quiz_Main parentPanel;
	private Quiz currentQuiz;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private ArrayList<Run> runResults;
	private QuizEvent responseRecieved;
	/**
	 * Create the panel.
	 */
	public Quiz_SecondPage(Quiz_Main parent) {
		parentPanel = parent;
		currentQuiz = parentPanel.getCurrentRun().getQuiz();
		quizResponse = new ArrayList<>();
		runResults = new ArrayList<>();
		
		WebLabel wblblView = new WebLabel();
		wblblView.setText("View");
		
		cards = new WebPanel(new CardLayout());
		panel_bottom_21 = new Quiz_SecondPage_Question_View(this);
		panel_bottom_22 = new Quiz_SecondPage_Student_View(this);
		panel_bottom_23 = new Quiz_SecondPage_Points_View(this);
		cards.add(panel_bottom_21,"Question");
		cards.add(panel_bottom_22,"Student");
		cards.add(panel_bottom_23,"Overall");
		
		webComboBox = new WebComboBox();
		webComboBox.setModel(new DefaultComboBoxModel(new String[] {"Question", "Student", "Overall"}));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(cards, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wblblView, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblView, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		responseRecieved = new QuizEvent();
		initEvents();
	}
	private void initEvents() {
		webComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				CardLayout cl_cards = (CardLayout)(cards.getLayout());
		        cl_cards.show(cards, webComboBox.getSelectedItem().toString());
			}
		});
        
		responseRecieved.addEventListener(new QuizEventListener() {
			
			@Override
			public void questionAnswered(QuizResponse e) {
				boolean valid = false;
				Question question = e.getQuestion();
				for (int i = 0; i < parentPanel.getCurrentRun().getQuiz().getQuestionList().size(); i++) {
					Question temp = parentPanel.getCurrentRun().getQuiz().getQuestionList().get(i);
					if(temp.getId() == question.getId()){
						if(question.getType() == 0 || question.getType() == 1){ ///validate student answer against correct ones : only booleans
							if((temp.getCorrectAnswer()[0] == question.getStudentAnswer()[0]) &&
								(temp.getCorrectAnswer()[1] == question.getStudentAnswer()[1]) &&
								(temp.getCorrectAnswer()[2] == question.getStudentAnswer()[2]) &&
								(temp.getCorrectAnswer()[3] == question.getStudentAnswer()[3]) &&
								(temp.getCorrectAnswer()[4] == question.getStudentAnswer()[4]))
							{
								e.getQuestion().setValidResponse(true);
							}
							else{
								e.getQuestion().setValidResponse(false);
							}
							
						}
						else{ // for the time if text answer is validated
							
						}
					}
				}
				
				panel_bottom_21.QuestionAnswered(e);
				panel_bottom_22.QuestionAnswered(e);
				panel_bottom_23.QuestionAnswered(e);
			}

			@Override
			public void quizStoped(QuizStop e) {
				
			}

			@Override
			public void quizFinished(QuizFinished e) {
				runResults.add(e.getQuizRun());
			}
		});
	}
	public Quiz_Main getParentPanel() {
		return parentPanel;
	}
	public void clear() {
		quizResponse = new ArrayList<>();
		runResults = new ArrayList<>();
		panel_bottom_21.clearQuiz();
		panel_bottom_22.clearQuiz();
		panel_bottom_23.clearQuiz();
	}
	public void LoadQuiz(Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
		for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
			quizResponse.add(new ArrayList<QuizResponse>());
		}
		panel_bottom_21.LoadQuiz(currentQuiz);
		panel_bottom_22.LoadQuiz(currentQuiz);
		panel_bottom_23.LoadQuiz(currentQuiz);
	}
	public ArrayList<ArrayList<QuizResponse>> getQuizResponse() {
		return quizResponse;
	}
	public void setQuizResponse(ArrayList<ArrayList<QuizResponse>> quizResponse) {
		this.quizResponse = quizResponse;
	}
	public Quiz getCurrentQuiz() {
		return currentQuiz;
	}
	public void setCurrentQuiz(Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
	}
	public ArrayList<Run> getRunResults() {
		return runResults;
	}
	public Quiz_SecondPage_Question_View getPanel_bottom_21() {
		return panel_bottom_21;
	}
	public void setPanel_bottom_21(Quiz_SecondPage_Question_View panel_bottom_21) {
		this.panel_bottom_21 = panel_bottom_21;
	}
}
