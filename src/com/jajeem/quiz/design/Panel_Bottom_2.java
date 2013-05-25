package com.jajeem.quiz.design;


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
import com.jajeem.events.QuizResponse;
import com.jajeem.events.QuizStop;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;


@SuppressWarnings("serial")
public class Panel_Bottom_2 extends WebPanel {

	private Panel_Bottom_21 panel_bottom_21;
	private Panel_Bottom_22 panel_bottom_22;
	private ArrayList<ArrayList<QuizResponse>> quizResponse;
	private QuizEvent responseRecieved;
	
	private Quiz currentQuiz;
	/**
	 * Create the panel.
	 */
	public Panel_Bottom_2() {
		quizResponse = new ArrayList<>();
		
		WebLabel wblblView = new WebLabel();
		wblblView.setText("View");
		
		final WebPanel cards = new WebPanel(new CardLayout());
		panel_bottom_21 = new Panel_Bottom_21(this);
		panel_bottom_22 = new Panel_Bottom_22(this);
		cards.add(panel_bottom_21,"Question");
		cards.add(panel_bottom_22,"Student");
		
		final WebComboBox webComboBox = new WebComboBox();
		webComboBox.setModel(new DefaultComboBoxModel(new String[] {"Question", "Student"}));
		webComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, webComboBox.getSelectedItem().toString());
			}
		});
		
		
		
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
        responseRecieved.addEventListener(new QuizEventListener() {
			
			@Override
			public void questionAnswered(QuizResponse e) {
				boolean valid = false;
				Question question = e.getQuestion();
				for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
					Question temp = currentQuiz.getQuestionList().get(i);
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
			}

			@Override
			public void quizStoped(QuizStop e) {
				
			}
		});

	}

	public void LoadQuiz(Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
		for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
			quizResponse.add(new ArrayList<QuizResponse>());
		}
		panel_bottom_21.LoadQuiz(currentQuiz);
		panel_bottom_22.LoadQuiz(currentQuiz);
	}
	
	public void ClearQuiz(){
		this.currentQuiz = null;
		quizResponse = new ArrayList<>();
		panel_bottom_21.clearQuiz();
		panel_bottom_22.clearQuiz();
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

	
}
