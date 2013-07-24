package com.jajeem.survey.design.alt;

import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;

public class Survey_QuestionListPanel extends Survey_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable webTable;
	private WebScrollPane webScrollPane;
	private Survey_FirstPage parentPanel;

	/**
	 * Create the panel.
	 * 
	 * @param panel_Bottom_1
	 */
	@SuppressWarnings("serial")
	public Survey_QuestionListPanel(WebPanel parent) {
		this.parentPanel = (Survey_FirstPage) parent;

		webScrollPane = new WebScrollPane((Component) null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.TRAILING).addComponent(webScrollPane,
				Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 465,
				Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(webScrollPane,
				GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE));

		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "Type", "Content" }) {
			boolean[] columnEditables = new boolean[] { false, false, 
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		webTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		webScrollPane.setViewportView(getWebTable());
		setLayout(groupLayout);

		initEvents();

	}

	private void initEvents() {
		webTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {

						// //////////////////////////////////////////////////////////////
						// /////////////Load
						// Questions//////////////////////////////////
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (webTable.getSelectedRow() == -1) {
								parentPanel.getParentPanel().setEventsEnabled(
										false);
								parentPanel.getParentPanel()
										.setCurrentQuestion(null);
								parentPanel.getWebQuestionDesignPanel().clear();
								parentPanel.getWebQuestionDesignPanel()
										.getWblblQuestion()
										.setText("Question ?");

								parentPanel.getParentPanel().setEventsEnabled(
										true);
								return;
							}
							// com.jajeem.survey.model.Question q =
							// parentPanel.getParentPanel().getCurrentQuestion();
							parentPanel
									.getWebQuestionDesignPanel()
									.getWblblQuestion()
									.setText(
											"Question "
													+ (webTable
															.getSelectedRow() + 1));
							parentPanel.getParentPanel().setCurrentQuestion(
									parentPanel.getParentPanel()
											.getCurrentRun().getSurvey()
											.getQuestionList()
											.get(webTable.getSelectedRow()));
							parentPanel.getParentPanel()
									.setEventsEnabled(false);
							parentPanel
									.getWebQuestionDesignPanel()
									.getWebTextArea()
									.setText(
											parentPanel.getParentPanel()
													.getCurrentQuestion()
													.getTitle());
							parentPanel
									.getWebQuestionDesignPanel()
									.getWebTextField()
									.setText(
											parentPanel.getParentPanel()
													.getCurrentQuestion()
													.getAnswer1());
							parentPanel
									.getWebQuestionDesignPanel()
									.getWebTextField_1()
									.setText(
											parentPanel.getParentPanel()
													.getCurrentQuestion()
													.getAnswer2());
							parentPanel
									.getWebQuestionDesignPanel()
									.getWebTextField_2()
									.setText(
											parentPanel.getParentPanel()
													.getCurrentQuestion()
													.getAnswer3());
							parentPanel
									.getWebQuestionDesignPanel()
									.getWebTextField_3()
									.setText(
											parentPanel.getParentPanel()
													.getCurrentQuestion()
													.getAnswer4());
							parentPanel
									.getWebQuestionDesignPanel()
									.getWebTextField_4()
									.setText(
											parentPanel.getParentPanel()
													.getCurrentQuestion()
													.getAnswer5());
							if (parentPanel.getParentPanel()
									.getCurrentQuestion().getType() == 0) {
								parentPanel.getWebQuestionDesignPanel()
										.getWebComboBox().setSelectedIndex(0);
								// ///////////////////////////////////////////////
								parentPanel.getWebQuestionDesignPanel()
										.WebComboboxSelectionChanged(
												"Single Choice");
								// ///////////////////////////////////////////////
								
							} else if (parentPanel.getParentPanel()
									.getCurrentQuestion().getType() == 1) {
								parentPanel.getWebQuestionDesignPanel()
										.getWebComboBox().setSelectedIndex(1);
								// ///////////////////////////////////////////////
								parentPanel.getWebQuestionDesignPanel()
										.WebComboboxSelectionChanged(
												"Multiple Choice");
								// ///////////////////////////////////////////////
								
							} else if (parentPanel.getParentPanel()
									.getCurrentQuestion().getType() == 2) {
								parentPanel.getWebQuestionDesignPanel()
										.getWebComboBox().setSelectedIndex(2);
								// ///////////////////////////////////////////////
								parentPanel.getWebQuestionDesignPanel()
										.WebComboboxSelectionChanged("Essay");
								// ///////////////////////////////////////////////
							}
							parentPanel.getParentPanel().setEventsEnabled(true);
						}
					}
				});
	}

	public WebTable getWebTable() {
		return webTable;
	}

	public void setWebTable(WebTable webTable) {
		this.webTable = webTable;
	}

	public void clear() {
		DefaultTableModel model = (DefaultTableModel) webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
	}
}