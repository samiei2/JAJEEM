package com.jajeem.quiz.design.alt;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.alee.laf.panel.WebPanel;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.ui.combobox.JajeemComboBox;
import com.jajeem.util.CustomPanel;

public class Quiz_QuestionDesignPanel extends Quiz_AbstractViews{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Quiz_FirstPage parentPanel;
	private JTextField textField;
	private JajeemComboBox webComboBoxQuestionType;
	private JTextArea textPaneQuestionContent;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JPanel panel_2;
	private JLabel lblQuestion;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 * 
	 * @param quiz_FirstPage
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Quiz_QuestionDesignPanel(WebPanel quiz_FirstPage) {
		JPanel contentPanel = new JPanel();
		contentPanel.setOpaque(false);
		this.parentPanel = (Quiz_FirstPage) quiz_FirstPage;
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);

		GroupLayout groupLayout = new GroupLayout(contentPanel);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		CustomPanel panelQuestionDetails = new CustomPanel("/icons/noa_en/quizradioback.png");
		panelQuestionDetails.setOpaque(false);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelQuestionDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelQuestionDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JPanel panelCard3 = new JPanel();
		panelCard3.setOpaque(false);
		GroupLayout gl_panelCard3_2 = new GroupLayout(panelCard3);
		gl_panelCard3_2.setHorizontalGroup(
			gl_panelCard3_2.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 50, Short.MAX_VALUE)
		);
		gl_panelCard3_2.setVerticalGroup(
			gl_panelCard3_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 166, Short.MAX_VALUE)
		);
		panelCard3.setLayout(gl_panelCard3_2);
		
		JPanel panelCard2 = new JPanel();
		panelCard2.setOpaque(false);
		
		checkBox = new JCheckBox("");
		checkBox.setOpaque(false);
		
		checkBox_1 = new JCheckBox("");
		checkBox_1.setOpaque(false);
		
		checkBox_2 = new JCheckBox("");
		checkBox_2.setOpaque(false);
		
		checkBox_3 = new JCheckBox("");
		checkBox_3.setOpaque(false);
		
		checkBox_4 = new JCheckBox("");
		checkBox_4.setOpaque(false);
		GroupLayout gl_panelCard2_2 = new GroupLayout(panelCard2);
		gl_panelCard2_2.setHorizontalGroup(
			gl_panelCard2_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCard2_2.createSequentialGroup()
					.addContainerGap(7, Short.MAX_VALUE)
					.addGroup(gl_panelCard2_2.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBox, Alignment.TRAILING)
						.addComponent(checkBox_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_3, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBox_4, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panelCard2_2.setVerticalGroup(
			gl_panelCard2_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCard2_2.createSequentialGroup()
					.addGap(10)
					.addComponent(checkBox)
					.addGap(10)
					.addComponent(checkBox_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(checkBox_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(checkBox_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(checkBox_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1, Short.MAX_VALUE))
		);
		panelCard2.setLayout(gl_panelCard2_2);
		
		JPanel panelCard1 = new JPanel();
		panelCard1.setOpaque(false);
		
		radioButton = new JRadioButton("");
		radioButton.setOpaque(false);
		
		radioButton_1 = new JRadioButton("");
		radioButton_1.setOpaque(false);
		
		radioButton_2 = new JRadioButton("");
		radioButton_2.setOpaque(false);
		
		radioButton_3 = new JRadioButton("");
		radioButton_3.setOpaque(false);
		
		radioButton_4 = new JRadioButton("");
		radioButton_4.setOpaque(false);

		GroupLayout gl_panelCard1_2 = new GroupLayout(panelCard1);
		gl_panelCard1_2.setHorizontalGroup(
			gl_panelCard1_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCard1_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCard1_2.createParallelGroup(Alignment.LEADING)
						.addComponent(radioButton)
						.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		gl_panelCard1_2.setVerticalGroup(
			gl_panelCard1_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCard1_2.createSequentialGroup()
					.addGap(10)
					.addComponent(radioButton)
					.addGap(10)
					.addComponent(radioButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(radioButton_2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(radioButton_3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(radioButton_4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1, Short.MAX_VALUE))
		);
		panelCard1.setLayout(gl_panelCard1_2);
		
		panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setLayout(new CardLayout());
		
		panel_2.add(panelCard1, "Single Choice");
		panel_2.add(panelCard2, "Multiple Choice");
		panel_2.add(panelCard3, "Essay");
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		GroupLayout gl_panelQuestionDetails = new GroupLayout(panelQuestionDetails);
		gl_panelQuestionDetails.setHorizontalGroup(
			gl_panelQuestionDetails.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelQuestionDetails.createSequentialGroup()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelQuestionDetails.setVerticalGroup(
			gl_panelQuestionDetails.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelQuestionDetails.createSequentialGroup()
					.addGroup(gl_panelQuestionDetails.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(1, Short.MAX_VALUE))
		);
		
		textField_1 = new JTextField();
		textField_1.setOpaque(false);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setOpaque(false);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setOpaque(false);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setOpaque(false);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setOpaque(false);
		textField_5.setColumns(10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(textField_3, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(textField_4, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
						.addComponent(textField_5, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		panelQuestionDetails.setLayout(gl_panelQuestionDetails);
		
		panel_1.setLayout(gl_panel_1);
		
		lblQuestion = new JLabel("Question ");
		
		JLabel lblType = new JLabel("Type : ");
		
		webComboBoxQuestionType = new JajeemComboBox();
		webComboBoxQuestionType.setModel(new DefaultComboBoxModel(new String[] {"Single Choice", "Multiple Choice", "Essay"}));
		
		JLabel label = new JLabel("Question ");
		
		scrollPane = new JScrollPane();
		
		JLabel lblPoint = new JLabel("Point : ");
		
		textField = new JTextField();
		textField.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblQuestion)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblPoint, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblType, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(webComboBoxQuestionType, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuestion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(webComboBoxQuestionType, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPoint)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(19)
							.addComponent(label))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		textPaneQuestionContent = new JTextArea();
		scrollPane.setViewportView(textPaneQuestionContent);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(groupLayout);
		GroupLayout gl_mainPanel = new GroupLayout(this);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		initEvents();
	}

	private void initEvents() {
		webComboBoxQuestionType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				_itemStateChanged(e);
			}
		});

		textPaneQuestionContent.getDocument().addDocumentListener(new DocumentListener() {

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
										textPaneQuestionContent.getText(),
										parentPanel.getWebQuestionListPanel()
												.getWebTable().getSelectedRow(),
										3);
						parentPanel.getParentPanel().getCurrentQuestion()
								.setTitle(textPaneQuestionContent.getText());
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
										textPaneQuestionContent.getText(),
										parentPanel.getWebQuestionListPanel()
												.getWebTable().getSelectedRow(),
										3);
						parentPanel.getParentPanel().getCurrentQuestion()
								.setTitle(textPaneQuestionContent.getText());
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

		textField_1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					if (parentPanel.getWebQuestionListPanel().getWebTable()
							.getSelectedRow() != -1
							&& parentPanel.getParentPanel().isEventsEnabled()) {
						parentPanel.getParentPanel().getCurrentQuestion()
								.setAnswer1(textField_1.getText());
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
								.setAnswer1(textField_1.getText());
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});
		
		textField_2.getDocument().addDocumentListener(
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
										.setAnswer2(textField_2.getText());
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
										.setAnswer2(textField_2.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		textField_3.getDocument().addDocumentListener(
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
										.setAnswer3(textField_3.getText());
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
										.setAnswer3(textField_3.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		textField_4.getDocument().addDocumentListener(
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
										.setAnswer4(textField_4.getText());
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
										.setAnswer4(textField_4.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		textField_5.getDocument().addDocumentListener(
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
										.setAnswer5(textField_5.getText());
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
										.setAnswer5(textField_5.getText());
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});

		textField.getDocument().addDocumentListener(
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
													Integer.parseInt(textField
															.getText()));
									parentPanel
											.getWebQuestionListPanel()
											.getWebTable()
											.setValueAt(
													textField.getText(),
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
									JajeemExcetionHandler.logError(ex);
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
										JajeemExcetionHandler.logError(e2);
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
													Integer.parseInt(textField
															.getText()));
									parentPanel
											.getWebQuestionListPanel()
											.getWebTable()
											.setValueAt(
													textField.getText(),
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
									JajeemExcetionHandler.logError(ex);
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
										JajeemExcetionHandler.logError(e2);
									}
								}
							}
						}
					}

					@Override
					public void changedUpdate(DocumentEvent e) {

					}
				});

		radioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					radioButton.setSelected(true);
					radioButton_1.setSelected(false);
					radioButton_2.setSelected(false);
					radioButton_3.setSelected(false);
					radioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											radioButton.isSelected(),
											radioButton_1.isSelected(),
											radioButton_2.isSelected(),
											radioButton_3.isSelected(),
											radioButton_4.isSelected() });
				}
			}
		});

		radioButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					radioButton.setSelected(false);
					radioButton_1.setSelected(true);
					radioButton_2.setSelected(false);
					radioButton_3.setSelected(false);
					radioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											radioButton.isSelected(),
											radioButton_1.isSelected(),
											radioButton_2.isSelected(),
											radioButton_3.isSelected(),
											radioButton_4.isSelected() });
				}
			}
		});

		radioButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					radioButton.setSelected(false);
					radioButton_1.setSelected(false);
					radioButton_2.setSelected(true);
					radioButton_3.setSelected(false);
					radioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											radioButton.isSelected(),
											radioButton_1.isSelected(),
											radioButton_2.isSelected(),
											radioButton_3.isSelected(),
											radioButton_4.isSelected() });
				}
			}
		});

		radioButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					radioButton.setSelected(false);
					radioButton_1.setSelected(false);
					radioButton_2.setSelected(false);
					radioButton_3.setSelected(true);
					radioButton_4.setSelected(false);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											radioButton.isSelected(),
											radioButton_1.isSelected(),
											radioButton_2.isSelected(),
											radioButton_3.isSelected(),
											radioButton_4.isSelected() });
				}
			}
		});

		radioButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					radioButton.setSelected(false);
					radioButton_1.setSelected(false);
					radioButton_2.setSelected(false);
					radioButton_3.setSelected(false);
					radioButton_4.setSelected(true);
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] {
											radioButton.isSelected(),
											radioButton_1.isSelected(),
											radioButton_2.isSelected(),
											radioButton_3.isSelected(),
											radioButton_4.isSelected() });
				}
			}
		});

		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { checkBox.isSelected(),
											checkBox_1.isSelected(),
											checkBox_2.isSelected(),
											checkBox_3.isSelected(),
											checkBox_4.isSelected() });
				}
			}
		});

		checkBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { checkBox.isSelected(),
											checkBox_1.isSelected(),
											checkBox_2.isSelected(),
											checkBox_3.isSelected(),
											checkBox_4.isSelected() });
				}
			}
		});

		checkBox_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { checkBox.isSelected(),
											checkBox_1.isSelected(),
											checkBox_2.isSelected(),
											checkBox_3.isSelected(),
											checkBox_4.isSelected() });
				}
			}
		});

		checkBox_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { checkBox.isSelected(),
											checkBox_1.isSelected(),
											checkBox_2.isSelected(),
											checkBox_3.isSelected(),
											checkBox_4.isSelected() });
				}
			}
		});

		checkBox_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (parentPanel.getParentPanel().isEventsEnabled()) {
					parentPanel
							.getParentPanel()
							.getCurrentQuestion()
							.setCorrectAnswer(
									new boolean[] { checkBox.isSelected(),
											checkBox_1.isSelected(),
											checkBox_2.isSelected(),
											checkBox_3.isSelected(),
											checkBox_4.isSelected() });
				}
			}
		});
	}

	public void _itemStateChanged(ItemEvent arg0) {
		if (parentPanel.getParentPanel().isEventsEnabled()) {
			CardLayout cl = (CardLayout) (panel_2.getLayout());
			cl.show(panel_2, (String) arg0.getItem());
			if (((String) arg0.getItem()).equals("Essay")) {
				textField_1.setVisible(false);
				textField_2.setVisible(false);
				textField_3.setVisible(false);
				textField_4.setVisible(false);
				textField_5.setVisible(false);
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
			} else {
				textField_1.setVisible(true);
				textField_2.setVisible(true);
				textField_3.setVisible(true);
				textField_4.setVisible(true);
				textField_5.setVisible(true);
			}
			radioButton.setSelected(false);
			radioButton_1.setSelected(false);
			radioButton_2.setSelected(false);
			radioButton_3.setSelected(false);
			radioButton_4.setSelected(false);
			checkBox.setSelected(false);
			checkBox_1.setSelected(false);
			checkBox_2.setSelected(false);
			checkBox_3.setSelected(false);
			checkBox_4.setSelected(false);
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
								webComboBoxQuestionType.getSelectedItem().toString(),
								parentPanel.getWebQuestionListPanel()
										.getWebTable().getSelectedRow(), 1);
				parentPanel
						.getParentPanel()
						.getCurrentRun()
						.getQuiz()
						.getQuestionList()
						.get(parentPanel.getWebQuestionListPanel()
								.getWebTable().getSelectedRow())
						.setType((byte) webComboBoxQuestionType.getSelectedIndex());
			}
		}
	}

	public JTextArea getWebTextArea() {
		return textPaneQuestionContent;
	}

	public void setWebTextArea(JTextArea webTextArea) {
		this.textPaneQuestionContent = webTextArea;
	}

	public JajeemComboBox getWebComboBox() {
		return webComboBoxQuestionType;
	}

	public void setWebComboBox(JajeemComboBox webComboBox) {
		this.webComboBoxQuestionType = webComboBox;
	}

	public JRadioButton getWebRadioButton() {
		return radioButton;
	}

	public void setWebRadioButton(JRadioButton webRadioButton) {
		this.radioButton = webRadioButton;
	}

	public JRadioButton getWebRadioButton_1() {
		return radioButton_1;
	}

	public void setWebRadioButton_1(JRadioButton webRadioButton_1) {
		this.radioButton_1 = webRadioButton_1;
	}

	public JRadioButton getWebRadioButton_2() {
		return radioButton_2;
	}

	public void setWebRadioButton_2(JRadioButton webRadioButton_2) {
		this.radioButton_2 = webRadioButton_2;
	}

	public JRadioButton getWebRadioButton_3() {
		return radioButton_3;
	}

	public void setWebRadioButton_3(JRadioButton webRadioButton_3) {
		this.radioButton_3 = webRadioButton_3;
	}

	public JRadioButton getWebRadioButton_4() {
		return radioButton_4;
	}

	public void setWebRadioButton_4(JRadioButton webRadioButton_4) {
		this.radioButton_4 = webRadioButton_4;
	}

	public JCheckBox getWebCheckBox() {
		return checkBox;
	}

	public void setWebCheckBox(JCheckBox webCheckBox) {
		this.checkBox = webCheckBox;
	}

	public JCheckBox getWebCheckBox_1() {
		return checkBox_1;
	}

	public void setWebCheckBox_1(JCheckBox webCheckBox_1) {
		this.checkBox_1 = webCheckBox_1;
	}

	public JCheckBox getWebCheckBox_2() {
		return checkBox_2;
	}

	public void setWebCheckBox_2(JCheckBox webCheckBox_2) {
		this.checkBox_2 = webCheckBox_2;
	}

	public void setWebCheckBox_3(JCheckBox webCheckBox_3) {
		this.checkBox_3 = webCheckBox_3;
	}

	public JCheckBox getWebCheckBox_3() {
		return checkBox_3;
	}

	public JCheckBox getWebCheckBox_4() {
		return checkBox_4;
	}

	public void setWebCheckBox_4(JCheckBox webCheckBox_4) {
		this.checkBox_4 = webCheckBox_4;
	}

	public JTextField getWebTextField() {
		return textField;
	}

	public void setWebTextField(JTextField webTextField) {
		this.textField = webTextField;
	}

	public JTextField getWebTextField_1() {
		return textField_1;
	}

	public void setWebTextField_1(JTextField webTextField_1) {
		this.textField_1 = webTextField_1;
	}

	public JTextField getWebTextField_2() {
		return textField_2;
	}

	public void setWebTextField_2(JTextField webTextField_2) {
		this.textField_2 = webTextField_2;
	}

	public JTextField getWebTextField_3() {
		return textField_3;
	}

	public void setWebTextField_3(JTextField webTextField_3) {
		this.textField_3 = webTextField_3;
	}

	public JTextField getWebTextField_4() {
		return textField_4;
	}

	public void setWebTextField_4(JTextField webTextField_4) {
		this.textField_4 = webTextField_4;
	}
	
	public JTextField getWebTextField_5() {
		return textField_5;
	}

	public void setWebTextField_5(JTextField webTextField_5) {
		this.textField_5 = webTextField_5;
	}

	public void clear() {
		textPaneQuestionContent.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		textField_5.setText("");
		// webTextField_5.setText("");
		textField.setText("");
		checkBox.setSelected(false);
		checkBox_1.setSelected(false);
		checkBox_2.setSelected(false);
		checkBox_3.setSelected(false);
		checkBox_4.setSelected(false);
		radioButton.setSelected(false);
		radioButton_1.setSelected(false);
		radioButton_2.setSelected(false);
		radioButton_3.setSelected(false);
		radioButton_4.setSelected(false);
		webComboBoxQuestionType.setSelectedIndex(0);
		CardLayout cl = (CardLayout) (panel_2.getLayout());
		cl.show(panel_2, "Single Choice");
		textField_1.setVisible(true);
		textField_2.setVisible(true);
		textField_3.setVisible(true);
		textField_4.setVisible(true);
		textField_5.setVisible(true);
	}

	public void WebComboboxSelectionChanged(String item) {
		CardLayout cl = (CardLayout) (panel_2.getLayout());
		cl.show(panel_2, item);
		if (item.equals("Essay")) {
			textField_1.setVisible(false);
			textField_1.setVisible(false);
			textField_1.setVisible(false);
			textField_1.setVisible(false);
			textField_1.setVisible(false);
		} else {
			textField_1.setVisible(true);
			textField_1.setVisible(true);
			textField_1.setVisible(true);
			textField_1.setVisible(true);
			textField_1.setVisible(true);
		}
		radioButton.setSelected(false);
		radioButton_1.setSelected(false);
		radioButton_2.setSelected(false);
		radioButton_3.setSelected(false);
		radioButton_4.setSelected(false);
		checkBox.setSelected(false);
		checkBox_1.setSelected(false);
		checkBox_2.setSelected(false);
		checkBox_3.setSelected(false);
		checkBox_4.setSelected(false);
	}

	public JLabel getWblblQuestion() {
		return lblQuestion;
	}

	public JComponent getWebScrollPane() {
		return scrollPane;
	}
	
	public static void main(String[] args) {
		Quiz_QuestionDesignPanel test = new Quiz_QuestionDesignPanel(null);
		test.setVisible(true);
	}
}
