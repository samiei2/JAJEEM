package com.jajeem.quiz.design.alt;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebTextField;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.util.CustomButton;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Quiz_FirstPage extends Quiz_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Quiz_QuestionListPanel webQuestionListPanel;
	private Quiz_QuestionDesignPanel webQuestionDesignPanel;
	private JTextField wbtxtfldDirection;
	private JTextField wbtxtfldPoints;
	private JTextField wbtxtfldTime;
	private WebButton wbtnAdd;
	private WebButton wbtnCopy;
	private WebButton wbtnDelete;
	private Quiz_Main parentPanel;
	private JCheckBox wbchckbxAuto;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	// private static final AtomicInteger counter = new AtomicInteger();

	/**
	 * Create the panel.
	 */
	public Quiz_FirstPage(WebFrame parent) {
		setOpaque(false);
		this.parentPanel = (Quiz_Main) parent;
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		JLabel lblDirection = new JLabel("Direction : ");
		
		textField = new JTextField();
		textField.setColumns(10);
		wbtxtfldDirection = textField;
		
		JLabel lblPoints = new JLabel("Total : ");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		wbtxtfldPoints = textField_1;
		
		JCheckBox chckbxAuto = new JCheckBox("Auto");
		chckbxAuto.setOpaque(false);
		wbchckbxAuto = chckbxAuto;
		
		JLabel lblTime = new JLabel("Time : ");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		wbtxtfldTime = textField_2;
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblDirection)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblPoints)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(chckbxAuto)
							.addGap(54)
							.addComponent(lblTime, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDirection)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPoints)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxAuto)
						.addComponent(lblTime)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		Quiz_QuestionListPanel quiz_QuestionListPanel = new Quiz_QuestionListPanel(this);
		quiz_QuestionListPanel.setOpaque(false);
		webQuestionListPanel = quiz_QuestionListPanel;
		
		CustomQuizButton cstmqzbtnAdd = new CustomQuizButton("/icons/noa_en/quizaddbutton.png");
		cstmqzbtnAdd.setMargin(new Insets(0, 10, 0, 0));
		cstmqzbtnAdd.setHorizontalAlignment(SwingConstants.LEFT);
		cstmqzbtnAdd.setUndecorated(true);
		cstmqzbtnAdd.setText("Add");
		wbtnAdd = cstmqzbtnAdd;
		
		CustomQuizButton cstmqzbtnCopy = new CustomQuizButton("/icons/noa_en/quizcopybutton.png");
		cstmqzbtnCopy.setUndecorated(true);
		cstmqzbtnCopy.setText("Copy");
		cstmqzbtnCopy.setMargin(new Insets(0, 10, 0, 0));
		cstmqzbtnCopy.setHorizontalAlignment(SwingConstants.LEFT);
		wbtnCopy = cstmqzbtnCopy;
		
		CustomQuizButton cstmqzbtnDelete = new CustomQuizButton("/icons/noa_en/quizdeletebutton.png");
		cstmqzbtnDelete.setUndecorated(true);
		cstmqzbtnDelete.setText("Delete");
		cstmqzbtnDelete.setMargin(new Insets(0, 10, 0, 0));
		cstmqzbtnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		wbtnDelete = cstmqzbtnDelete;
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(quiz_QuestionListPanel, GroupLayout.PREFERRED_SIZE, 388, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(cstmqzbtnAdd, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cstmqzbtnCopy, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cstmqzbtnDelete, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(quiz_QuestionListPanel, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(cstmqzbtnAdd, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(cstmqzbtnCopy, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(cstmqzbtnDelete, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		Quiz_QuestionDesignPanel quiz_QuestionDesignPanel = new Quiz_QuestionDesignPanel(this);
		webQuestionDesignPanel = quiz_QuestionDesignPanel;
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(quiz_QuestionDesignPanel, GroupLayout.PREFERRED_SIZE, 239, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(quiz_QuestionDesignPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);



//		initEvents();
	}

	private void initEvents() {
		wbtnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.setEventsEnabled(false);
				if (parentPanel.getCurrentQuestion() != null) {
					if (parentPanel.getCurrentQuestion().getId().equals(null)) {
						parentPanel.getCurrentQuestion().setId(
								UUID.randomUUID());
					}
					parentPanel.getCurrentQuestion().setQuizId(
							parentPanel.getCurrentRun().getQuiz().getId());
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
					// parentPanel.getCurrentQuestion().setUrl(getQuestionDesignPanel().getWebTextField_5().getText());
					try {
						parentPanel.getCurrentQuestion().setPoint(
								Integer.parseInt(webQuestionDesignPanel
										.getWebTextField().getText()));
					} catch (Exception ex) {
						JajeemExcetionHandler.logError(ex);
					}
					try {
						if (webQuestionDesignPanel.getWebComboBox()
								.getSelectedIndex() == 0) {
							parentPanel.getCurrentQuestion().setType(
									Byte.parseByte("0"));
							parentPanel.getCurrentQuestion().setCorrectAnswer(
									new boolean[] {
											webQuestionDesignPanel
													.getWebRadioButton()
													.isSelected(),
											webQuestionDesignPanel
													.getWebRadioButton_1()
													.isSelected(),
											webQuestionDesignPanel
													.getWebRadioButton_2()
													.isSelected(),
											webQuestionDesignPanel
													.getWebRadioButton_3()
													.isSelected(),
											webQuestionDesignPanel
													.getWebRadioButton_4()
													.isSelected() });
						} else if (webQuestionDesignPanel.getWebComboBox()
								.getSelectedIndex() == 1) {
							parentPanel.getCurrentQuestion().setType(
									Byte.parseByte("1"));
							parentPanel.getCurrentQuestion().setCorrectAnswer(
									new boolean[] {
											webQuestionDesignPanel
													.getWebCheckBox()
													.isSelected(),
											webQuestionDesignPanel
													.getWebCheckBox_1()
													.isSelected(),
											webQuestionDesignPanel
													.getWebCheckBox_2()
													.isSelected(),
											webQuestionDesignPanel
													.getWebCheckBox_3()
													.isSelected(),
											webQuestionDesignPanel
													.getWebCheckBox_4()
													.isSelected() });
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
				if (webQuestionListPanel.getWebTable().getRowCount() != 0) {
					obj = new Object[] {
							Integer.parseInt(String.valueOf(model.getValueAt(
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1, 0))) + 1,
							webQuestionDesignPanel.getWebComboBox()
									.getSelectedItem().toString(), 0, "" };
				} else {
					obj = new Object[] {
							1,
							webQuestionDesignPanel.getWebComboBox()
									.getSelectedItem().toString(), 0, "" };
				}
				model.addRow(obj);

				Question q = new Question();
				q.setId(UUID.randomUUID());
				q.setQuizId(parentPanel.getCurrentRun().getQuiz().getId());
				q.setInstructorId(parentPanel.getCurrentInstructor().getId());
				parentPanel.getCurrentRun().getQuiz().addQuestion(q);

				parentPanel.setEventsEnabled(true);

				webQuestionListPanel
						.getWebTable()
						.getSelectionModel()
						.setSelectionInterval(
								webQuestionListPanel.getWebTable()
										.getRowCount() - 1,
								webQuestionListPanel.getWebTable()
										.getRowCount() - 1);

				// System.out.println(model.getDataVector().get(0));

				CalculateQuestionPoint();

				webQuestionDesignPanel.setEnabled(true);
				webQuestionDesignPanel.getWebScrollPane().setEnabled(true);
				webQuestionDesignPanel.getWebTextField().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_1().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_2().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_3().setEnabled(true);
				webQuestionDesignPanel.getWebTextField_4().setEnabled(true);
				// webQuestionDesignPanel.getWebTextField_6().setEnabled(true);
				webQuestionDesignPanel.getWebRadioButton().setEnabled(true);
				webQuestionDesignPanel.getWebRadioButton_1().setEnabled(true);
				webQuestionDesignPanel.getWebRadioButton_2().setEnabled(true);
				webQuestionDesignPanel.getWebRadioButton_3().setEnabled(true);
				webQuestionDesignPanel.getWebRadioButton_4().setEnabled(true);
				webQuestionDesignPanel.getWebComboBox().setEnabled(true);
			}
		});
		wbtnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parentPanel.setEventsEnabled(false);
				if (parentPanel.getCurrentRun().getQuiz().getQuestionList()
						.size() != 0
						&& webQuestionListPanel.getWebTable().getRowCount() != 0) {
					Question toCopy = new Question(parentPanel
							.getCurrentRun()
							.getQuiz()
							.getQuestionList()
							.get(webQuestionListPanel.getWebTable()
									.getSelectedRow()));
					toCopy.setId(UUID.randomUUID());
					parentPanel.getCurrentRun().getQuiz().addQuestion(toCopy);
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
							toCopy.getPoint(), toCopy.getTitle() });

					parentPanel.setEventsEnabled(true);
					webQuestionListPanel
							.getWebTable()
							.getSelectionModel()
							.setSelectionInterval(
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1,
									webQuestionListPanel.getWebTable()
											.getRowCount() - 1);

					CalculateQuestionPoint();
				}
			}
		});
		wbtnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (webQuestionListPanel.getWebTable().getSelectedRow() == -1) {
					return;
				}

				DefaultTableModel model = (DefaultTableModel) webQuestionListPanel
						.getWebTable().getModel();
				int index = webQuestionListPanel.getWebTable().getSelectedRow();
				model.removeRow(index);
				parentPanel.getCurrentRun().getQuiz().getQuestionList()
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

				CalculateQuestionPoint();

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
					webQuestionDesignPanel.getWebTextField()
							.setEnabled(false);
					webQuestionDesignPanel.getWebRadioButton()
							.setEnabled(false);
					webQuestionDesignPanel.getWebRadioButton_1().setEnabled(
							false);
					webQuestionDesignPanel.getWebRadioButton_2().setEnabled(
							false);
					webQuestionDesignPanel.getWebRadioButton_3().setEnabled(
							false);
					webQuestionDesignPanel.getWebRadioButton_4().setEnabled(
							false);
					webQuestionDesignPanel.getWebComboBox().setEnabled(false);
				}
			}
		});
		wbtxtfldDirection.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						parentPanel.getCurrentRun().getQuiz()
								.setTitle(wbtxtfldDirection.getText());
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						parentPanel.getCurrentRun().getQuiz()
								.setTitle(wbtxtfldDirection.getText());
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});
		wbtxtfldPoints.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						if (parentPanel.getCurrentRun() != null
								&& parentPanel.getCurrentRun().getQuiz() != null
								&& parentPanel.isEventsEnabled()) {
							try {
								// System.out.println(getWbTxtFldPoints().getText());
								parentPanel
										.getCurrentRun()
										.getQuiz()
										.setPoints(
												Integer.parseInt(wbtxtfldPoints
														.getText()));
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
								// JOptionPane.showMessageDialog(null,
								// e.getMessage());
							}
							CalculateQuestionPoint();
						}
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						if (parentPanel.getCurrentRun() != null
								&& parentPanel.getCurrentRun().getQuiz() != null
								&& parentPanel.isEventsEnabled()) {
							try {
								// System.out.println(getWbTxtFldPoints().getText());
								parentPanel
										.getCurrentRun()
										.getQuiz()
										.setPoints(
												Integer.parseInt(wbtxtfldPoints
														.getText()));
							} catch (Exception e) {
								JajeemExcetionHandler.logError(e);
								JOptionPane.showMessageDialog(null,
										e.getMessage());
							}
							CalculateQuestionPoint();
						}
					}

					@Override
					public void changedUpdate(DocumentEvent arg0) {

					}
				});
		wbchckbxAuto.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				webQuestionDesignPanel.getWebTextField().setEnabled(
						!wbchckbxAuto.isSelected());
				if (wbchckbxAuto.isSelected()
						&& parentPanel.getCurrentRun().getQuiz()
								.getQuestionList().size() != 0
						&& webQuestionListPanel.getWebTable().getRowCount() != 0) {
					int point = parentPanel.getCurrentRun().getQuiz()
							.getPoints()
							/ parentPanel.getCurrentRun().getQuiz()
									.getQuestionList().size();
					for (int i = 0; i < parentPanel.getCurrentRun().getQuiz()
							.getQuestionList().size(); i++) {
						parentPanel.getCurrentRun().getQuiz().getQuestionList()
								.get(i).setPoint(point);
						((DefaultTableModel) webQuestionListPanel.getWebTable()
								.getModel()).setValueAt(point, i, 2);
					}
					int remainder = (parentPanel.getCurrentRun().getQuiz()
							.getPoints() - parentPanel.getCurrentRun()
							.getQuiz().getQuestionList().size()
							* point);
					if (remainder != 0) {
						parentPanel
								.getCurrentRun()
								.getQuiz()
								.getQuestionList()
								.get(parentPanel.getCurrentRun().getQuiz()
										.getQuestionList().size() - 1)
								.setPoint(point + remainder);
						((DefaultTableModel) webQuestionListPanel.getWebTable()
								.getModel()).setValueAt(point + remainder,
								webQuestionListPanel.getWebTable()
										.getRowCount() - 1, 2);
					}
					webQuestionDesignPanel
							.getWebTextField()
							.setText(
									String.valueOf(((DefaultTableModel) webQuestionListPanel
											.getWebTable().getModel())
											.getValueAt(webQuestionListPanel
													.getWebTable()
													.getSelectedRow(), 2)));
				}
			}
		});
		wbtxtfldTime.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (parentPanel.getCurrentRun().getQuiz() != null
						&& parentPanel.isEventsEnabled()) {
					try {
						parentPanel
								.getCurrentRun()
								.getQuiz()
								.setTime(
										Integer.parseInt(wbtxtfldTime.getText()));
					} catch (Exception ex) {
						JajeemExcetionHandler.logError(ex);
						// JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (parentPanel.getCurrentRun().getQuiz() != null
						&& parentPanel.isEventsEnabled()) {
					try {
						parentPanel
								.getCurrentRun()
								.getQuiz()
								.setTime(
										Integer.parseInt(wbtxtfldTime.getText()));
					} catch (Exception ex) {
						JajeemExcetionHandler.logError(ex);
						JOptionPane.showMessageDialog(null, "Invalid value!");
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {

			}
		});

	}

	protected void CalculateQuestionPoint() {
		Quiz quiz = parentPanel.getCurrentRun().getQuiz();
		if (wbchckbxAuto.isSelected() && quiz.getQuestionList().size() != 0) {
			int point = quiz.getPoints() / quiz.getQuestionList().size();
			for (int i = 0; i < quiz.getQuestionList().size(); i++) {
				quiz.getQuestionList().get(i).setPoint(point);
				webQuestionListPanel.getWebTable().getModel()
						.setValueAt(point, i, 2);
			}
			int remainder = (quiz.getPoints() - quiz.getQuestionList().size()
					* point);
			if (remainder != 0) {
				quiz.getQuestionList().get(quiz.getQuestionList().size() - 1)
						.setPoint(point + remainder);
				webQuestionListPanel
						.getWebTable()
						.getModel()
						.setValueAt(
								point + remainder,
								webQuestionListPanel.getWebTable()
										.getRowCount() - 1, 2);
			}
			webQuestionDesignPanel.getWebTextField().setText(
					String.valueOf(webQuestionListPanel
							.getWebTable()
							.getModel()
							.getValueAt(
									webQuestionListPanel.getWebTable()
											.getSelectedRow(), 2)));
		}
	}

	public Quiz_QuestionListPanel getWebQuestionListPanel() {
		return webQuestionListPanel;
	}

	public void setWebQuestionListPanel(
			Quiz_QuestionListPanel webQuestionListPanel) {
		this.webQuestionListPanel = webQuestionListPanel;
	}

	public Quiz_QuestionDesignPanel getWebQuestionDesignPanel() {
		return webQuestionDesignPanel;
	}

	public void setWebQuestionDesignPanel(
			Quiz_QuestionDesignPanel webQuestionDesignPanel) {
		this.webQuestionDesignPanel = webQuestionDesignPanel;
	}

	public void clear() {
		wbtxtfldDirection.setText("");
		wbtxtfldPoints.setText("");
		wbtxtfldTime.setText("");
		webQuestionDesignPanel.clear();
		webQuestionListPanel.clear();
	}

	public Quiz_Main getParentPanel() {
		return parentPanel;
	}

	public JCheckBox getWbchckbxAuto() {
		return wbchckbxAuto;
	}

	public void setWbchckbxAuto(JCheckBox wbchckbxAuto) {
		this.wbchckbxAuto = wbchckbxAuto;
	}

	public JTextField getWbtxtfldPoints() {
		return wbtxtfldPoints;
	}

	public void setWbtxtfldPoints(JTextField wbtxtfldPoints) {
		this.wbtxtfldPoints = wbtxtfldPoints;
	}

	public void loadCurrentQuiz(Quiz currentQuiz) {
		parentPanel.setEventsEnabled(false);
		wbtxtfldDirection.setText(currentQuiz.getTitle());
		wbtxtfldPoints.setText(String.valueOf(currentQuiz.getPoints()));
		wbtxtfldTime.setText(String.valueOf(currentQuiz.getTime()));

		DefaultTableModel model = (DefaultTableModel) webQuestionListPanel
				.getWebTable().getModel();
		for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
			Question question = currentQuiz.getQuestionList().get(i);
			String type = "";
			if (question.getType() == 0) {
				type = "Single Choice";
			} else if (question.getType() == 1) {
				type = "Multiple Choice";
			} else {
				type = "Essay";
			}

			model.addRow(new Object[] {
					model.getRowCount() == 0 ? 1
							: Integer.parseInt(String
									.valueOf(webQuestionListPanel.getWebTable()
											.getValueAt(
													webQuestionListPanel
															.getWebTable()
															.getRowCount() - 1,
													0))) + 1, type,
					question.getPoint(), question.getTitle() });
		}

		parentPanel.setEventsEnabled(true);
		if (model.getRowCount() != 0) {
			webQuestionListPanel.getWebTable().getSelectionModel()
					.setSelectionInterval(0, 0);
		}
	}
}
