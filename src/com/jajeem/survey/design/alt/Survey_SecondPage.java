package com.jajeem.survey.design.alt;

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
import com.jajeem.events.SurveyEvent;
import com.jajeem.events.SurveyEventListener;
import com.jajeem.events.SurveyFinished;
import com.jajeem.events.SurveyResponse;
import com.jajeem.events.SurveyStop;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;
import com.jajeem.ui.combobox.JajeemComboBox;

@SuppressWarnings("serial")
public class Survey_SecondPage extends Survey_AbstractViews {

	private Survey_SecondPage_Question_View panel_bottom_21;
	private Survey_SecondPage_Student_View panel_bottom_22;
	private JajeemComboBox webComboBox;
	private WebPanel cards;
	private Survey_Main parentPanel;
	private Survey currentSurvey;
	private ArrayList<ArrayList<SurveyResponse>> surveyResponse;
	private ArrayList<Run> runResults;
	private SurveyEvent responseRecieved;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Survey_SecondPage(Survey_Main parent) {
		parentPanel = parent;
		currentSurvey = parentPanel.getCurrentRun().getSurvey();
		surveyResponse = new ArrayList<>();
		runResults = new ArrayList<>();

		WebLabel wblblView = new WebLabel();
		wblblView.setText("View");

		cards = new WebPanel(new CardLayout());
		panel_bottom_21 = new Survey_SecondPage_Question_View(this);
		panel_bottom_22 = new Survey_SecondPage_Student_View(this);
		cards.add(panel_bottom_21, "Question");
		cards.add(panel_bottom_22, "Student");

		webComboBox = new JajeemComboBox();
		webComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Question", "Student" }));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(cards, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wblblView, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
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
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		responseRecieved = new SurveyEvent();
		initEvents();
	}

	private void initEvents() {
		webComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				CardLayout cl_cards = (CardLayout) (cards.getLayout());
				cl_cards.show(cards, webComboBox.getSelectedItem().toString());
			}
		});

		responseRecieved.addEventListener(new SurveyEventListener() {

			@Override
			public void questionAnswered(SurveyResponse e) {
				if (e.getListeningPort() != parentPanel.listeningPort()) {
					return;
				}

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

	public Survey_Main getParentPanel() {
		return parentPanel;
	}

	public void clear() {
		surveyResponse = new ArrayList<>();
		runResults = new ArrayList<>();
		panel_bottom_21.clearSurvey();
		panel_bottom_22.clearSurvey();
	}

	public void LoadSurvey(Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
		for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
			surveyResponse.add(new ArrayList<SurveyResponse>());
		}
		panel_bottom_21.LoadSurvey(currentSurvey);
		panel_bottom_22.LoadSurvey(currentSurvey);
	}

	public ArrayList<ArrayList<SurveyResponse>> getSurveyResponse() {
		return surveyResponse;
	}

	public void setSurveyResponse(
			ArrayList<ArrayList<SurveyResponse>> surveyResponse) {
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

	public Survey_SecondPage_Question_View getPanel_bottom_21() {
		return panel_bottom_21;
	}

	public void setPanel_bottom_21(
			Survey_SecondPage_Question_View panel_bottom_21) {
		this.panel_bottom_21 = panel_bottom_21;
	}
}
