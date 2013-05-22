package com.jajeem.survey.design;


import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.label.WebLabel;
import com.alee.laf.combobox.WebComboBox;
import com.jajeem.core.model.Student;
import com.jajeem.events.SurveyEvent;
import com.jajeem.events.SurveyEventListener;
import com.jajeem.events.SurveyResponse;
import com.jajeem.events.SurveyStop;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.model.Survey;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;


@SuppressWarnings("serial")
public class Panel_Bottom_2 extends WebPanel {

	private Panel_Bottom_21 panel_bottom_21;
	private Panel_Bottom_22 panel_bottom_22;
	private ArrayList<ArrayList<SurveyResponse>> quizResponse;
	private SurveyEvent responseRecieved;
	
	private Survey currentSurvey;
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
		
		responseRecieved = new SurveyEvent();
        responseRecieved.addEventListener(new SurveyEventListener() {
			
        	@Override
			public void questionAnswered(SurveyResponse e) {
						
				panel_bottom_21.QuestionAnswered(e);
				panel_bottom_22.QuestionAnswered(e);
			}

			@Override
			public void surveyStoped(SurveyStop e) {
				
			}
		});

	}

	public void LoadSurvey(Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
		for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
			quizResponse.add(new ArrayList<SurveyResponse>());
		}
		panel_bottom_21.LoadSurvey(currentSurvey);
		panel_bottom_22.LoadSurvey(currentSurvey);
	}

	public ArrayList<ArrayList<SurveyResponse>> getSurveyResponse() {
		return quizResponse;
	}

	public void setSurveyResponse(ArrayList<ArrayList<SurveyResponse>> quizResponse) {
		this.quizResponse = quizResponse;
	}

	public Survey getCurrentSurvey() {
		return currentSurvey;
	}

	public void setCurrentSurvey(Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
	}
}
