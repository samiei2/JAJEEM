package com.jajeem.quiz.design.alt;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

import com.alee.extended.filechooser.WebFileChooser;
import com.alee.extended.filechooser.WebFileChooserField;
import com.alee.extended.filefilter.DefaultFileFilter;
import com.alee.extended.image.WebImage;
import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import com.jajeem.whiteboard.client.Client.design.MyFileFilter;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import sun.awt.image.ToolkitImage;

public class Quiz_QuestionDesignPanel extends Quiz_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTextField webTextField;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;
	private WebTextField webTextField_3;
	private WebTextField webTextField_4;
	private WebTextField webTextField_6;
	private WebTextArea webTextArea;
	private WebScrollPane webScrollPane;
	private WebRadioButton webRadioButton;
	private WebRadioButton webRadioButton_1;
	private WebRadioButton webRadioButton_2;
	private WebRadioButton webRadioButton_3;
	private WebRadioButton webRadioButton_4;
	private WebCheckBox webCheckBox;
	private WebCheckBox webCheckBox_1;
	private WebCheckBox webCheckBox_2;
	private WebCheckBox webCheckBox_3;
	private WebCheckBox webCheckBox_4;
	private WebComboBox webComboBox;
	private WebPanel webPanelOptions;
	private WebLabel wblblQuestion;
	private Quiz_FirstPage parentPanel;

	/**
	 * Create the panel.
	 * 
	 * @param quiz_FirstPage
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Quiz_QuestionDesignPanel(WebPanel quiz_FirstPage) {
		this.parentPanel = (Quiz_FirstPage) quiz_FirstPage;

		setBorder(UIManager.getBorder("TitledBorder.border"));
		setWblblQuestion(new WebLabel());
		getWblblQuestion().setText("Question ?");

		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");

		WebLabel wblblQuestion_1 = new WebLabel();
		wblblQuestion_1.setText("Question");

		webComboBox = new WebComboBox();
		webComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Single Choice", "Multiple Choice", "Essay" }));

		setWebScrollPane(new WebScrollPane((Component) null));
		getWebScrollPane().setEnabled(false);

		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		WebLabel wblblPoint = new WebLabel();
		wblblPoint.setText("Point");

		webTextField_6 = new WebTextField();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
									.addComponent(wblblPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webTextField_6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(12)
									.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)
									.addGap(0, 0, Short.MAX_VALUE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(4)
							.addComponent(webPanel, 0, 0, Short.MAX_VALUE)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblblPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(150, Short.MAX_VALUE))
		);

		webTextField = new WebTextField();

		webTextField_1 = new WebTextField();

		webTextField_2 = new WebTextField();

		webTextField_3 = new WebTextField();

		webTextField_4 = new WebTextField();

		webPanelOptions = new WebPanel(new CardLayout());

		WebPanel card1 = new WebPanel();
		WebPanel card2 = new WebPanel();
		WebPanel card3 = new WebPanel();

		webPanelOptions.add(card1, "Single Choice");

		webRadioButton = new WebRadioButton();

		webRadioButton_1 = new WebRadioButton();

		webRadioButton_2 = new WebRadioButton();

		webRadioButton_3 = new WebRadioButton();

		webRadioButton_4 = new WebRadioButton();

		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(gl_card1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card1.createSequentialGroup()
						.addGroup(
								gl_card1.createParallelGroup(Alignment.LEADING)
										.addComponent(webRadioButton,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webRadioButton_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webRadioButton_4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webRadioButton_3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webRadioButton_2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		gl_card1.setVerticalGroup(gl_card1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card1.createSequentialGroup()
						.addComponent(webRadioButton,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(webRadioButton_1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(webRadioButton_2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(11)
						.addComponent(webRadioButton_3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(13)
						.addComponent(webRadioButton_4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(14, Short.MAX_VALUE)));
		card1.setLayout(gl_card1);
		webPanelOptions.add(card2, "Multiple Choice");

		webCheckBox = new WebCheckBox();

		webCheckBox_1 = new WebCheckBox();

		webCheckBox_2 = new WebCheckBox();

		webCheckBox_3 = new WebCheckBox();

		webCheckBox_4 = new WebCheckBox();

		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(gl_card2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card2.createSequentialGroup()
						.addGroup(
								gl_card2.createParallelGroup(Alignment.LEADING)
										.addComponent(webCheckBox,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webCheckBox_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webCheckBox_2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webCheckBox_3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webCheckBox_4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		gl_card2.setVerticalGroup(gl_card2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_card2.createSequentialGroup()
						.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(webCheckBox_1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(webCheckBox_2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(webCheckBox_3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 20,
								Short.MAX_VALUE)
						.addComponent(webCheckBox_4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)));
		card2.setLayout(gl_card2);
		webPanelOptions.add(card3, "Essay");

		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(gl_webPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webPanelOptions,
								GroupLayout.PREFERRED_SIZE, 17,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_webPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webTextField_4,
												GroupLayout.DEFAULT_SIZE, 342,
												Short.MAX_VALUE)
										.addComponent(webTextField_3,
												GroupLayout.DEFAULT_SIZE, 342,
												Short.MAX_VALUE)
										.addComponent(webTextField_2,
												GroupLayout.DEFAULT_SIZE, 342,
												Short.MAX_VALUE)
										.addComponent(webTextField_1,
												GroupLayout.DEFAULT_SIZE, 342,
												Short.MAX_VALUE)
										.addComponent(webTextField,
												GroupLayout.DEFAULT_SIZE, 342,
												Short.MAX_VALUE))
						.addContainerGap()));
		gl_webPanel
				.setVerticalGroup(gl_webPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_webPanel
										.createSequentialGroup()
										.addGroup(
												gl_webPanel
														.createParallelGroup(
																Alignment.TRAILING,
																false)
														.addComponent(
																webPanelOptions,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.LEADING,
																gl_webPanel
																		.createSequentialGroup()
																		.addComponent(
																				webTextField,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webTextField_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webTextField_2,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webTextField_3,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				webTextField_4,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
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
										3);
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
										3);
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

		webTextField_6.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {
								try {
									parentPanel
											.getParentPanel()
											.getCurrentQuestion()
											.setPoint(
													Integer.parseInt(webTextField_6
															.getText()));
									parentPanel
											.getWebQuestionListPanel()
											.getWebTable()
											.setValueAt(
													webTextField_6.getText(),
													parentPanel
															.getWebQuestionListPanel()
															.getWebTable()
															.getSelectedRow(),
													2);
								} catch (Exception ex) {
									parentPanel.getParentPanel()
											.getCurrentQuestion().setPoint(0);
									parentPanel
											.getWebQuestionListPanel()
											.getWebTable()
											.setValueAt(
													0,
													parentPanel
															.getWebQuestionListPanel()
															.getWebTable()
															.getSelectedRow(),
													2);
								}
								if (!parentPanel.getWbchckbxAuto().isSelected()) {
									int total = 0;
									try {
										for (int i = 0; i < parentPanel
												.getParentPanel()
												.getCurrentRun().getQuiz()
												.getQuestionList().size(); i++) {
											total += parentPanel
													.getParentPanel()
													.getCurrentRun().getQuiz()
													.getQuestionList().get(i)
													.getPoint();
										}
										parentPanel.getWbtxtfldPoints()
												.setText(String.valueOf(total));
									} catch (Exception e2) {
										// total =
										// parentPanel.getParentPanel().getCurrentQuestion().getPoint();
										parentPanel.getWbtxtfldPoints()
												.setText(String.valueOf(0));
									}
								}
							}
						}
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						if (parentPanel.getParentPanel().isEventsEnabled()) {
							if (parentPanel.getWebQuestionListPanel()
									.getWebTable().getSelectedRow() != -1
									&& parentPanel.getParentPanel()
											.isEventsEnabled()) {

								try {
									parentPanel
											.getParentPanel()
											.getCurrentQuestion()
											.setPoint(
													Integer.parseInt(webTextField_6
															.getText()));
									parentPanel
											.getWebQuestionListPanel()
											.getWebTable()
											.setValueAt(
													webTextField_6.getText(),
													parentPanel
															.getWebQuestionListPanel()
															.getWebTable()
															.getSelectedRow(),
													2);
								} catch (Exception ex) {
									parentPanel.getParentPanel()
											.getCurrentQuestion().setPoint(0);
									parentPanel
											.getWebQuestionListPanel()
											.getWebTable()
											.setValueAt(
													0,
													parentPanel
															.getWebQuestionListPanel()
															.getWebTable()
															.getSelectedRow(),
													2);
								}
								if (!parentPanel.getWbchckbxAuto().isSelected()) {
									int total = 0;
									try {
										for (int i = 0; i < parentPanel
												.getParentPanel()
												.getCurrentRun().getQuiz()
												.getQuestionList().size(); i++) {
											total += parentPanel
													.getParentPanel()
													.getCurrentRun().getQuiz()
													.getQuestionList().get(i)
													.getPoint();
										}
										parentPanel.getWbtxtfldPoints()
												.setText(String.valueOf(total));
									} catch (Exception e2) {
										// total =
										// parentPanel.getParentPanel().getCurrentQuestion().getPoint();
										parentPanel.getWbtxtfldPoints()
												.setText(String.valueOf(0));
									}
								}
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});

		webRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webRadioButton.setSelected(false);
//						return;
//					}
//					if (webTextField.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webRadioButton.setSelected(false);
//						return;
//					}
					webRadioButton.setSelected(true);
					webRadioButton_1.setSelected(false);
					webRadioButton_2.setSelected(false);
					webRadioButton_3.setSelected(false);
					webRadioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											webRadioButton.isSelected(),
											webRadioButton_1.isSelected(),
											webRadioButton_2.isSelected(),
											webRadioButton_3.isSelected(),
											webRadioButton_4.isSelected() });
				}
			}
		});

		webRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webRadioButton_1.setSelected(false);
//						return;
//					}
//					if (webTextField_1.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webRadioButton_1.setSelected(false);
//						return;
//					}
					webRadioButton.setSelected(false);
					webRadioButton_1.setSelected(true);
					webRadioButton_2.setSelected(false);
					webRadioButton_3.setSelected(false);
					webRadioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											webRadioButton.isSelected(),
											webRadioButton_1.isSelected(),
											webRadioButton_2.isSelected(),
											webRadioButton_3.isSelected(),
											webRadioButton_4.isSelected() });
				}
			}
		});

		webRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webRadioButton_2.setSelected(false);
//						return;
//					}
//					if (webTextField_2.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webRadioButton_2.setSelected(false);
//						return;
//					}
					webRadioButton.setSelected(false);
					webRadioButton_1.setSelected(false);
					webRadioButton_2.setSelected(true);
					webRadioButton_3.setSelected(false);
					webRadioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											webRadioButton.isSelected(),
											webRadioButton_1.isSelected(),
											webRadioButton_2.isSelected(),
											webRadioButton_3.isSelected(),
											webRadioButton_4.isSelected() });
				}
			}
		});

		webRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webRadioButton_3.setSelected(false);
//						return;
//					}
//					if (webTextField_3.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webRadioButton_3.setSelected(false);
//						return;
//					}
					webRadioButton.setSelected(false);
					webRadioButton_1.setSelected(false);
					webRadioButton_2.setSelected(false);
					webRadioButton_3.setSelected(true);
					webRadioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											webRadioButton.isSelected(),
											webRadioButton_1.isSelected(),
											webRadioButton_2.isSelected(),
											webRadioButton_3.isSelected(),
											webRadioButton_4.isSelected() });
				}
			}
		});

		webRadioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webRadioButton_4.setSelected(false);
//						return;
//					}
//					if (webTextField_4.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webRadioButton_4.setSelected(false);
//						return;
//					}
					webRadioButton.setSelected(false);
					webRadioButton_1.setSelected(false);
					webRadioButton_2.setSelected(false);
					webRadioButton_3.setSelected(false);
					webRadioButton_4.setSelected(true);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											webRadioButton.isSelected(),
											webRadioButton_1.isSelected(),
											webRadioButton_2.isSelected(),
											webRadioButton_3.isSelected(),
											webRadioButton_4.isSelected() });
				}
			}
		});

		webCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webCheckBox.setSelected(false);
//						return;
//					}
//					if (webTextField.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webCheckBox.setSelected(false);
//						return;
//					}
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { webCheckBox.isSelected(),
											webCheckBox_1.isSelected(),
											webCheckBox_2.isSelected(),
											webCheckBox_3.isSelected(),
											webCheckBox_4.isSelected() });
				}
			}
		});

		webCheckBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webCheckBox_1.setSelected(false);
//						return;
//					}
//					if (webTextField_1.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webCheckBox_1.setSelected(false);
//						return;
//					}
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { webCheckBox.isSelected(),
											webCheckBox_1.isSelected(),
											webCheckBox_2.isSelected(),
											webCheckBox_3.isSelected(),
											webCheckBox_4.isSelected() });
				}
			}
		});

		webCheckBox_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webCheckBox_2.setSelected(false);
//						return;
//					}
//					if (webTextField_2.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webCheckBox_2.setSelected(false);
//						return;
//					}
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { webCheckBox.isSelected(),
											webCheckBox_1.isSelected(),
											webCheckBox_2.isSelected(),
											webCheckBox_3.isSelected(),
											webCheckBox_4.isSelected() });
				}
			}
		});

		webCheckBox_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webCheckBox_3.setSelected(false);
//						return;
//					}
//					if (webTextField_3.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webCheckBox_3.setSelected(false);
//						return;
//					}
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { webCheckBox.isSelected(),
											webCheckBox_1.isSelected(),
											webCheckBox_2.isSelected(),
											webCheckBox_3.isSelected(),
											webCheckBox_4.isSelected() });
				}
			}
		});

		webCheckBox_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
//					if (webTextArea.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Question is empty,please first type in the question!");
//						webCheckBox_4.setSelected(false);
//						return;
//					}
//					if (webTextField_4.getText().equals("")) {
//						JOptionPane
//								.showMessageDialog(null,
//										"Answer is empty,please first type in the answer!");
//						webCheckBox_4.setSelected(false);
//						return;
//					}
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { webCheckBox.isSelected(),
											webCheckBox_1.isSelected(),
											webCheckBox_2.isSelected(),
											webCheckBox_3.isSelected(),
											webCheckBox_4.isSelected() });
				}
			}
		});
	}

	public void _itemStateChanged(ItemEvent arg0) {
		if (parentPanel.getParentPanel().isEventsEnabled()) {
			CardLayout cl = (CardLayout) (webPanelOptions.getLayout());
			cl.show(webPanelOptions, (String) arg0.getItem());
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
			webRadioButton.setSelected(false);
			webRadioButton_1.setSelected(false);
			webRadioButton_2.setSelected(false);
			webRadioButton_3.setSelected(false);
			webRadioButton_4.setSelected(false);
			webCheckBox.setSelected(false);
			webCheckBox_1.setSelected(false);
			webCheckBox_2.setSelected(false);
			webCheckBox_3.setSelected(false);
			webCheckBox_4.setSelected(false);
			parentPanel
					.getParentPanel()
					.getCurrentQuestion()
					.setCorrectAnswer(
							new boolean[] { false, false, false, false, false });
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
						.getQuiz()
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

	public WebComboBox getWebComboBox() {
		return webComboBox;
	}

	public void setWebComboBox(WebComboBox webComboBox) {
		this.webComboBox = webComboBox;
	}

	public WebRadioButton getWebRadioButton() {
		return webRadioButton;
	}

	public void setWebRadioButton(WebRadioButton webRadioButton) {
		this.webRadioButton = webRadioButton;
	}

	public WebRadioButton getWebRadioButton_1() {
		return webRadioButton_1;
	}

	public void setWebRadioButton_1(WebRadioButton webRadioButton_1) {
		this.webRadioButton_1 = webRadioButton_1;
	}

	public WebRadioButton getWebRadioButton_2() {
		return webRadioButton_2;
	}

	public void setWebRadioButton_2(WebRadioButton webRadioButton_2) {
		this.webRadioButton_2 = webRadioButton_2;
	}

	public WebRadioButton getWebRadioButton_3() {
		return webRadioButton_3;
	}

	public void setWebRadioButton_3(WebRadioButton webRadioButton_3) {
		this.webRadioButton_3 = webRadioButton_3;
	}

	public WebRadioButton getWebRadioButton_4() {
		return webRadioButton_4;
	}

	public void setWebRadioButton_4(WebRadioButton webRadioButton_4) {
		this.webRadioButton_4 = webRadioButton_4;
	}

	public WebCheckBox getWebCheckBox() {
		return webCheckBox;
	}

	public void setWebCheckBox(WebCheckBox webCheckBox) {
		this.webCheckBox = webCheckBox;
	}

	public WebCheckBox getWebCheckBox_1() {
		return webCheckBox_1;
	}

	public void setWebCheckBox_1(WebCheckBox webCheckBox_1) {
		this.webCheckBox_1 = webCheckBox_1;
	}

	public WebCheckBox getWebCheckBox_2() {
		return webCheckBox_2;
	}

	public void setWebCheckBox_2(WebCheckBox webCheckBox_2) {
		this.webCheckBox_2 = webCheckBox_2;
	}

	public void setWebCheckBox_3(WebCheckBox webCheckBox_3) {
		this.webCheckBox_3 = webCheckBox_3;
	}

	public WebCheckBox getWebCheckBox_3() {
		return webCheckBox_3;
	}

	public WebCheckBox getWebCheckBox_4() {
		return webCheckBox_4;
	}

	public void setWebCheckBox_4(WebCheckBox webCheckBox_4) {
		this.webCheckBox_4 = webCheckBox_4;
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

	public WebTextField getWebTextField_6() {
		return webTextField_6;
	}

	public void setWebTextField_6(WebTextField webTextField_6) {
		this.webTextField_6 = webTextField_6;
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
//		webTextField_5.setText("");
		webTextField_6.setText("");
		webCheckBox.setSelected(false);
		webCheckBox_1.setSelected(false);
		webCheckBox_2.setSelected(false);
		webCheckBox_3.setSelected(false);
		webCheckBox_4.setSelected(false);
		webRadioButton.setSelected(false);
		webRadioButton_1.setSelected(false);
		webRadioButton_2.setSelected(false);
		webRadioButton_3.setSelected(false);
		webRadioButton_4.setSelected(false);
		webComboBox.setSelectedIndex(0);
		CardLayout cl = (CardLayout) (webPanelOptions.getLayout());
		cl.show(webPanelOptions, "Single Choice");
		webTextField.setVisible(true);
		webTextField_1.setVisible(true);
		webTextField_2.setVisible(true);
		webTextField_3.setVisible(true);
		webTextField_4.setVisible(true);
	}

	public void WebComboboxSelectionChanged(String item) {
		CardLayout cl = (CardLayout) (webPanelOptions.getLayout());
		cl.show(webPanelOptions, item);
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
		webRadioButton.setSelected(false);
		webRadioButton_1.setSelected(false);
		webRadioButton_2.setSelected(false);
		webRadioButton_3.setSelected(false);
		webRadioButton_4.setSelected(false);
		webCheckBox.setSelected(false);
		webCheckBox_1.setSelected(false);
		webCheckBox_2.setSelected(false);
		webCheckBox_3.setSelected(false);
		webCheckBox_4.setSelected(false);
	}
}
