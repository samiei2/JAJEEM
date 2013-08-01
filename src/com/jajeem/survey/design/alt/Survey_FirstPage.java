package com.jajeem.survey.design.alt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextField;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;

public class Survey_FirstPage extends Survey_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Survey_QuestionListPanel webQuestionListPanel;
	private Survey_QuestionDesignPanel webQuestionDesignPanel;
	private WebTextField wbtxtfldDirection;
	private WebButton wbtnAdd;
	private WebButton wbtnCopy;
	private WebButton wbtnDelete;
	private Survey_Main parentPanel;


	/**
	 * Create the panel.
	 */
	public Survey_FirstPage(WebFrame parent) {
		this.parentPanel = (Survey_Main) parent;

		webQuestionDesignPanel = new Survey_QuestionDesignPanel(this);
		webQuestionDesignPanel.setBorder(UIManager
				.getBorder("TitledBorder.border"));

		webQuestionListPanel = new Survey_QuestionListPanel(this);
		webQuestionListPanel.setBorder(new LineBorder(new Color(0, 0, 0)));

		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		wbtnAdd = new WebButton();
		wbtnAdd.setText("Add");

		wbtnCopy = new WebButton();
		wbtnCopy.setText("Copy");

		wbtnDelete = new WebButton();
		wbtnDelete.setText("Delete");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webQuestionDesignPanel, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webQuestionListPanel, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
						.addComponent(webPanel, 0, 0, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnAdd, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnCopy, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnDelete, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webQuestionDesignPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webQuestionListPanel, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(wbtnAdd, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnCopy, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnDelete, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(7))
		);

		WebLabel wblblDirection = new WebLabel();
		wblblDirection.setText("Direction : ");

		wbtxtfldDirection = new WebTextField();
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtxtfldDirection, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
					.addGap(6))
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtxtfldDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(41, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		setLayout(groupLayout);

		initEvents();
	}

	private void initEvents() {
		wbtnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.setEventsEnabled(false);
				if (parentPanel.getCurrentQuestion() != null) {
					parentPanel.getCurrentQuestion().setId(
							UUID.randomUUID());
					parentPanel.getCurrentQuestion().setSurveyId(
							parentPanel.getCurrentRun().getSurvey().getId());
					parentPanel.getCurrentQuestion().setInstructorId(
							parentPanel.getCurrentInstructor().getId());
					parentPanel.getCurrentQuestion().setTitle(
							webQuestionDesignPanel.getWebTextArea().getText());
					parentPanel.getCurrentQuestion().setAnswer1(
							webQuestionDesignPanel.getWebTextField().getText());
					parentPanel.getCurrentQuestion().setAnswer2(
							webQuestionDesignPanel.getWebTextField_1()
									.getText());
					parentPanel.getCurrentQuestion().setAnswer3(
							webQuestionDesignPanel.getWebTextField_2()
									.getText());
					parentPanel.getCurrentQuestion().setAnswer4(
							webQuestionDesignPanel.getWebTextField_3()
									.getText());
					parentPanel.getCurrentQuestion().setAnswer5(
							webQuestionDesignPanel.getWebTextField_4()
									.getText());
					
					try {
						if (webQuestionDesignPanel.getWebComboBox()
								.getSelectedIndex() == 0) {
							parentPanel.getCurrentQuestion().setType(
									Byte.parseByte("0"));
						} else if (webQuestionDesignPanel.getWebComboBox()
								.getSelectedIndex() == 1) {
							parentPanel.getCurrentQuestion().setType(
									Byte.parseByte("1"));
						} else if (webQuestionDesignPanel.getWebComboBox()
								.getSelectedIndex() == 2) {
							parentPanel.getCurrentQuestion().setType(
									Byte.parseByte("2"));

						}
					} catch (Exception ex) {
						JajeemExcetionHandler.logError(ex);
					}
				}// end if

				webQuestionDesignPanel.clear();

				DefaultTableModel model = (DefaultTableModel) webQuestionListPanel
						.getWebTable().getModel();
				Object[] obj;
				if (webQuestionListPanel.getWebTable().getRowCount() != 0)
					obj = new Object[] {
							Integer.parseInt(String.valueOf(model.getValueAt(
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1, 0))) + 1,
							webQuestionDesignPanel.getWebComboBox()
									.getSelectedItem().toString(), 0, "" };
				else
					obj = new Object[] {
							1,
							webQuestionDesignPanel.getWebComboBox()
									.getSelectedItem().toString(), 0, "" };
				model.addRow(obj);

				Question q = new Question();
				q.setId(UUID.randomUUID());
				q.setSurveyId(parentPanel.getCurrentRun().getSurvey().getId());
				q.setInstructorId(parentPanel.getCurrentInstructor().getId());
				parentPanel.getCurrentRun().getSurvey().addQuestion(q);
				
				parentPanel.setEventsEnabled(true);
				
				webQuestionListPanel
						.getWebTable()
						.getSelectionModel()
						.setSelectionInterval(
								webQuestionListPanel.getWebTable()
										.getRowCount() - 1,
								webQuestionListPanel.getWebTable()
										.getRowCount() - 1);


				webQuestionDesignPanel.setEnabled(true);
				webQuestionDesignPanel.getWebScrollPane().setEnabled(true);
				webQuestionDesignPanel.getWebTextField().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_1().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_2().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_3().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_4().setEnabled(true);
				webQuestionDesignPanel.getWebComboBox().setEnabled(true);
			}
		});
		wbtnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parentPanel.setEventsEnabled(false);
				if (parentPanel.getCurrentRun().getSurvey().getQuestionList()
						.size() != 0
						&& webQuestionListPanel.getWebTable().getRowCount() != 0) {
					Question toCopy = new Question(parentPanel
							.getCurrentRun()
							.getSurvey()
							.getQuestionList()
							.get(webQuestionListPanel.getWebTable()
									.getSelectedRow()));
					toCopy.setId(UUID.randomUUID());
					parentPanel.getCurrentRun().getSurvey().addQuestion(toCopy);
					String type = "";
					if (toCopy.getType() == 0) {
						type = "Single Choice";
					} else if (toCopy.getType() == 1) {
						type = "Multiple Choice";
					} else if (toCopy.getType() == 2) {
						type = "Essay";
					}
					((DefaultTableModel) webQuestionListPanel.getWebTable()
							.getModel()).addRow(new Object[] {
							Integer.parseInt(String
									.valueOf(webQuestionListPanel
											.getWebTable()
											.getModel()
											.getValueAt(
													webQuestionListPanel
															.getWebTable()
															.getRowCount() - 1,
													0))) + 1, type,
							toCopy.getTitle() });
					
					parentPanel.setEventsEnabled(true);
					webQuestionListPanel
							.getWebTable()
							.getSelectionModel()
							.setSelectionInterval(
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1,
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1);

				}
			}
		});
		wbtnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (webQuestionListPanel.getWebTable().getSelectedRow() == -1)
					return;

				DefaultTableModel model = (DefaultTableModel) webQuestionListPanel
						.getWebTable().getModel();
				int index = webQuestionListPanel.getWebTable().getSelectedRow();
				model.removeRow(index);
				parentPanel.getCurrentRun().getSurvey().getQuestionList()
						.remove(index);
				if (webQuestionListPanel.getWebTable().getRowCount() != 0) {
					ListSelectionModel m_modelSelection = webQuestionListPanel
							.getWebTable().getSelectionModel();
					m_modelSelection
							.setSelectionInterval(webQuestionListPanel
									.getWebTable().getRowCount() - 1,
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1);
				}
				

				if (webQuestionListPanel.getWebTable().getRowCount() == 0) {
					webQuestionDesignPanel.getWebScrollPane().setEnabled(false);
					webQuestionDesignPanel.getWebTextField().setEnabled(false);
					webQuestionDesignPanel.getWebTextField_1()
							.setEnabled(false);
					webQuestionDesignPanel.getWebTextField_2()
							.setEnabled(false);
					webQuestionDesignPanel.getWebTextField_3()
							.setEnabled(false);
					webQuestionDesignPanel.getWebTextField_4()
							.setEnabled(false);
					webQuestionDesignPanel.getWebComboBox().setEnabled(false);
				}
			}
		});
		wbtxtfldDirection.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						parentPanel.getCurrentRun().getSurvey()
								.setTitle(wbtxtfldDirection.getText());
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						parentPanel.getCurrentRun().getSurvey()
								.setTitle(wbtxtfldDirection.getText());
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

	}

	public Survey_QuestionListPanel getWebQuestionListPanel() {
		return webQuestionListPanel;
	}

	public void setWebQuestionListPanel(
			Survey_QuestionListPanel webQuestionListPanel) {
		this.webQuestionListPanel = webQuestionListPanel;
	}

	public Survey_QuestionDesignPanel getWebQuestionDesignPanel() {
		return webQuestionDesignPanel;
	}

	public void setWebQuestionDesignPanel(
			Survey_QuestionDesignPanel webQuestionDesignPanel) {
		this.webQuestionDesignPanel = webQuestionDesignPanel;
	}

	public void clear() {
		wbtxtfldDirection.setText("");
		webQuestionDesignPanel.clear();
		webQuestionListPanel.clear();
	}

	public Survey_Main getParentPanel() {
		return parentPanel;
	}

	public void loadCurrentSurvey(Survey currentSurvey) {
		parentPanel.setEventsEnabled(false);
		wbtxtfldDirection.setText(currentSurvey.getTitle());
		
		DefaultTableModel model = (DefaultTableModel)webQuestionListPanel.getWebTable().getModel();
		for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
			Question question = currentSurvey.getQuestionList().get(i);
			String type = "";
			if(question.getType() == 0)
				type = "Single Choice";
			else if(question.getType() == 1)
				type = "Multiple Choice";
			else
				type = "Essay";
			
			model.addRow(new Object[]{
					model.getRowCount() == 0 ? 1 : Integer.parseInt(String.valueOf(webQuestionListPanel.getWebTable().getValueAt(webQuestionListPanel.getWebTable().getRowCount()-1, 0)))+1,
					type,
					question.getTitle()
			});
		}
		
		parentPanel.setEventsEnabled(true);
		if(model.getRowCount() != 0)
			webQuestionListPanel.getWebTable().getSelectionModel().setSelectionInterval(0, 0);
	}
}
