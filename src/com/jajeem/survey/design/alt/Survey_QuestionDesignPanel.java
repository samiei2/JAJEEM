package com.jajeem.survey.design.alt;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.ui.combobox.JajeemComboBox;

public class Survey_QuestionDesignPanel extends Survey_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTextField webTextField;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;
	private WebTextField webTextField_3;
	private WebTextField webTextField_4;
	private WebTextArea webTextArea;
	private WebScrollPane webScrollPane;
	private JajeemComboBox webComboBox;
	private WebLabel wblblQuestion;
	private Survey_FirstPage parentPanel;

	/**
	 * Create the panel.
	 * 
	 * @param survey_FirstPage
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Survey_QuestionDesignPanel(WebPanel survey_FirstPage) {
		this.parentPanel = (Survey_FirstPage) survey_FirstPage;

		setBorder(UIManager.getBorder("TitledBorder.border"));
		setWblblQuestion(new WebLabel());
		getWblblQuestion().setText("Question ?");

		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");

		WebLabel wblblQuestion_1 = new WebLabel();
		wblblQuestion_1.setText("Question");

		webComboBox = new JajeemComboBox();
		webComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Single Choice", "Multiple Choice" }));

		setWebScrollPane(new WebScrollPane((Component) null));
		getWebScrollPane().setEnabled(false);

		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								wblblQuestion,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												wblblQuestionType,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												webComboBox,
																												GroupLayout.PREFERRED_SIZE,
																												115,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(12)
																										.addComponent(
																												wblblQuestion_1,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												webScrollPane,
																												GroupLayout.PREFERRED_SIZE,
																												278,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(0,
																												0,
																												Short.MAX_VALUE))))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(4)
																		.addComponent(
																				webPanel,
																				0,
																				0,
																				Short.MAX_VALUE)))
										.addGap(10)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(wblblQuestion,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(22)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								webComboBox,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								wblblQuestionType,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webScrollPane,
																				GroupLayout.PREFERRED_SIZE,
																				76,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(56)
																		.addComponent(
																				wblblQuestion_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(webPanel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(150, Short.MAX_VALUE)));

		webTextField = new WebTextField();

		webTextField_1 = new WebTextField();

		webTextField_2 = new WebTextField();

		webTextField_3 = new WebTextField();

		webTextField_4 = new WebTextField();

		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(gl_webPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addGap(33)
						.addGroup(
								gl_webPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webTextField_4,
												GroupLayout.DEFAULT_SIZE, 296,
												Short.MAX_VALUE)
										.addComponent(webTextField_3,
												GroupLayout.DEFAULT_SIZE, 296,
												Short.MAX_VALUE)
										.addComponent(webTextField_2,
												GroupLayout.DEFAULT_SIZE, 296,
												Short.MAX_VALUE)
										.addComponent(webTextField_1,
												GroupLayout.DEFAULT_SIZE, 296,
												Short.MAX_VALUE)
										.addComponent(webTextField,
												GroupLayout.DEFAULT_SIZE, 296,
												Short.MAX_VALUE))
						.addContainerGap()));
		gl_webPanel.setVerticalGroup(gl_webPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webTextField_1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webTextField_2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webTextField_3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webTextField_4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		webPanel.setLayout(gl_webPanel);

		webTextArea = new WebTextArea();

		getWebScrollPane().setViewportView(webTextArea);
		setLayout(groupLayout);

		initEvents();
	}

	private void initEvents() {
		webComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				_itemStateChanged(e);
			}
		});

		webTextArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					if (parentPanel.getWebQuestionListPanel().getWebTable()
							.getSelectedRow() != -1
							&& parentPanel.getParentPanel().isEventsEnabled()) {
						parentPanel
								.getWebQuestionListPanel()
								.getWebTable()
								.setValueAt(
										webTextArea.getText(),
										parentPanel.getWebQuestionListPanel()
												.getWebTable().getSelectedRow(),
										2);
						parentPanel.getParentPanel().getCurrentQuestion()
								.setTitle(webTextArea.getText());
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					if (parentPanel.getWebQuestionListPanel().getWebTable()
							.getSelectedRow() != -1
							&& parentPanel.getParentPanel().isEventsEnabled()) {
						parentPanel
								.getWebQuestionListPanel()
								.getWebTable()
								.setValueAt(
										webTextArea.getText(),
										parentPanel.getWebQuestionListPanel()
												.getWebTable().getSelectedRow(),
										2);
						parentPanel.getParentPanel().getCurrentQuestion()
								.setTitle(webTextArea.getText());
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

		webTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					if (parentPanel.getWebQuestionListPanel().getWebTable()
							.getSelectedRow() != -1
							&& parentPanel.getParentPanel().isEventsEnabled()) {
						parentPanel.getParentPanel().getCurrentQuestion()
								.setAnswer1(webTextField.getText());
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					if (parentPanel.getWebQuestionListPanel().getWebTable()
							.getSelectedRow() != -1
							&& parentPanel.getParentPanel().isEventsEnabled()) {
						parentPanel.getParentPanel().getCurrentQuestion()
								.setAnswer1(webTextField.getText());
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

		webTextField_1.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer2(webTextField_1.getText());
							}
						}
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer2(webTextField_1.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		webTextField_2.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer3(webTextField_2.getText());
							}
						}
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer3(webTextField_2.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		webTextField_3.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer4(webTextField_3.getText());
							}
						}
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer4(webTextField_3.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		webTextField_4.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer5(webTextField_4.getText());
							}
						}
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								parentPanel.getParentPanel()
										.getCurrentQuestion()
										.setAnswer5(webTextField_4.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});
	}

	public void _itemStateChanged(ItemEvent arg0) {
		if (parentPanel.getParentPanel().isEventsEnabled()) {
			if (((String) arg0.getItem()).equals("Essay")) {
				webTextField.setVisible(false);
				webTextField_1.setVisible(false);
				webTextField_2.setVisible(false);
				webTextField_3.setVisible(false);
				webTextField_4.setVisible(false);
				webTextField.setText("");
				webTextField_1.setText("");
				webTextField_2.setText("");
				webTextField_3.setText("");
				webTextField_4.setText("");
			} else {
				webTextField.setVisible(true);
				webTextField_1.setVisible(true);
				webTextField_2.setVisible(true);
				webTextField_3.setVisible(true);
				webTextField_4.setVisible(true);
			}
			if (parentPanel != null
					&& parentPanel.getParentPanel().getCurrentRun() != null
					&& parentPanel.getWebQuestionListPanel().getWebTable()
							.getSelectedRow() != -1
					&& parentPanel.getParentPanel().isEventsEnabled()) {
				parentPanel
						.getWebQuestionListPanel()
						.getWebTable()
						.getModel()
						.setValueAt(
								webComboBox.getSelectedItem().toString(),
								parentPanel.getWebQuestionListPanel()
										.getWebTable().getSelectedRow(), 1);
				parentPanel
						.getParentPanel()
						.getCurrentRun()
						.getSurvey()
						.getQuestionList()
						.get(parentPanel.getWebQuestionListPanel()
								.getWebTable().getSelectedRow())
						.setType((byte) webComboBox.getSelectedIndex());
			}
		}
	}

	public WebTextArea getWebTextArea() {
		return webTextArea;
	}

	public void setWebTextArea(WebTextArea webTextArea) {
		this.webTextArea = webTextArea;
	}

	public JajeemComboBox getWebComboBox() {
		return webComboBox;
	}

	public void setWebComboBox(JajeemComboBox webComboBox) {
		this.webComboBox = webComboBox;
	}

	public WebTextField getWebTextField() {
		return webTextField;
	}

	public void setWebTextField(WebTextField webTextField) {
		this.webTextField = webTextField;
	}

	public WebTextField getWebTextField_1() {
		return webTextField_1;
	}

	public void setWebTextField_1(WebTextField webTextField_1) {
		this.webTextField_1 = webTextField_1;
	}

	public WebTextField getWebTextField_2() {
		return webTextField_2;
	}

	public void setWebTextField_2(WebTextField webTextField_2) {
		this.webTextField_2 = webTextField_2;
	}

	public WebTextField getWebTextField_3() {
		return webTextField_3;
	}

	public void setWebTextField_3(WebTextField webTextField_3) {
		this.webTextField_3 = webTextField_3;
	}

	public WebTextField getWebTextField_4() {
		return webTextField_4;
	}

	public void setWebTextField_4(WebTextField webTextField_4) {
		this.webTextField_4 = webTextField_4;
	}

	public WebLabel getWblblQuestion() {
		return wblblQuestion;
	}

	public void setWblblQuestion(WebLabel wblblQuestion) {
		this.wblblQuestion = wblblQuestion;
	}

	public WebScrollPane getWebScrollPane() {
		return webScrollPane;
	}

	public void setWebScrollPane(WebScrollPane webScrollPane) {
		this.webScrollPane = webScrollPane;
	}

	public void clear() {
		webTextArea.setText("");
		webTextField.setText("");
		webTextField_1.setText("");
		webTextField_2.setText("");
		webTextField_3.setText("");
		webTextField_4.setText("");
		// webTextField_5.setText("");
		webComboBox.setSelectedIndex(0);
		webTextField.setVisible(true);
		webTextField_1.setVisible(true);
		webTextField_2.setVisible(true);
		webTextField_3.setVisible(true);
		webTextField_4.setVisible(true);
	}

	public void WebComboboxSelectionChanged(String item) {
		if (item.equals("Essay")) {
			webTextField.setVisible(false);
			webTextField_1.setVisible(false);
			webTextField_2.setVisible(false);
			webTextField_3.setVisible(false);
			webTextField_4.setVisible(false);
		} else {
			webTextField.setVisible(true);
			webTextField_1.setVisible(true);
			webTextField_2.setVisible(true);
			webTextField_3.setVisible(true);
			webTextField_4.setVisible(true);
		}
	}
}
