package com.jajeem.survey.design;


import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Dictionary;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.jajeem.events.SurveyEvent;
import com.jajeem.events.SurveyEventListener;
import com.jajeem.events.SurveyFinished;
//import com.jajeem.events.SurveyFinished;
import com.jajeem.events.SurveyResponse;
import com.jajeem.events.SurveyStop;
import com.jajeem.quiz.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.model.Run;


@SuppressWarnings("serial")
public class SurveyTab_2 extends WebPanel {

	private SurveyTab_2_Question_View panel_bottom_21;
	private SurveyTab_2_Student_View panel_bottom_22;
	private ArrayList<ArrayList<SurveyResponse>> surveyResponse;
	private ArrayList<Run> runResults;
	private SurveyEvent responseRecieved;
	private ArrayList<Integer> responseCount;
	
	private Survey currentSurvey;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SurveyTab_2() {
		surveyResponse = new ArrayList<>();
		runResults = new ArrayList<>();
		responseCount = new ArrayList<>();
		
		WebLabel wblblView = new WebLabel();
		wblblView.setText("View");
		
		final WebPanel cards = new WebPanel(new CardLayout());
		panel_bottom_21 = new SurveyTab_2_Question_View(this);
		panel_bottom_22 = new SurveyTab_2_Student_View(this);
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
						.addComponent(cards, GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
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
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
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

			@Override
			public void surveyFinished(SurveyFinished e) {
				runResults.add(e.getSurveyRun());
			}
		});
	}

	public void LoadSurvey(Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
		surveyResponse.clear();
		for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
			surveyResponse.add(new ArrayList<SurveyResponse>());
		}
		panel_bottom_21.LoadSurvey(currentSurvey);
		panel_bottom_22.LoadSurvey(currentSurvey);
	}
	
	public void ClearSurvey(){
		this.currentSurvey = null;
		surveyResponse = new ArrayList<>();
		panel_bottom_21.clearSurvey();
		panel_bottom_22.clearSurvey();
	}

	public ArrayList<ArrayList<SurveyResponse>> getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(ArrayList<ArrayList<SurveyResponse>> surveyResponse) {
		this.surveyResponse = surveyResponse;
	}

	public Survey getCurrentSurvey() {
		return currentSurvey;
	}

	public void setCurrentSurvey(Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
	}

	public ArrayList<Run> getRunResults() {
		return runResults;
	}

	public void setRunResults(ArrayList<Run> runResults) {
		this.runResults = runResults;
	}

	
}
