package com.jajeem.quiz.design.alt;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.alee.laf.button.WebButton;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.alee.laf.checkbox.WebCheckBox;

public class Quiz_FirstPage extends Quiz_AbstractViews {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Quiz_QuestionListPanel webQuestionListPanel;
	private Quiz_QuestionDesignPanel webQuestionDesignPanel;
	private WebTextField wbtxtfldDirection;
	private WebTextField wbtxtfldPoints;
	private WebTextField wbtxtfldTime;
	private WebButton wbtnAdd;
	private WebButton wbtnCopy;
	private WebButton wbtnDelete;
	private Quiz_Main parentPanel;
	private WebCheckBox wbchckbxAuto;

	private static final AtomicInteger counter = new AtomicInteger();

	/**
	 * Create the panel.
	 */
	public Quiz_FirstPage(WebFrame parent) {
		this.parentPanel = (Quiz_Main) parent;

		webQuestionDesignPanel = new Quiz_QuestionDesignPanel(this);
		webQuestionDesignPanel.setBorder(UIManager
				.getBorder("TitledBorder.border"));

		webQuestionListPanel = new Quiz_QuestionListPanel(this);
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
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wbtnAdd, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnCopy, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtnDelete, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addComponent(webQuestionListPanel, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
						.addComponent(webPanel, 0, 0, Short.MAX_VALUE))
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(webQuestionDesignPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webQuestionListPanel, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(wbtnAdd, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnCopy, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnDelete, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);

		WebLabel wblblDirection = new WebLabel();
		wblblDirection.setText("Direction : ");

		wbtxtfldDirection = new WebTextField();

		WebLabel wblblPoints = new WebLabel();
		wblblPoints.setText("Total : ");

		wbtxtfldPoints = new WebTextField();

		WebLabel wblblT = new WebLabel();
		wblblT.setText("Time : ");

		wbtxtfldTime = new WebTextField();

		wbchckbxAuto = new WebCheckBox();
		wbchckbxAuto.setText("Auto");
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtxtfldDirection, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wbtxtfldPoints, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wbchckbxAuto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(44)
							.addComponent(wblblT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wbtxtfldTime, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
					.addGap(6))
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtxtfldDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtxtfldPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbchckbxAuto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtxtfldTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
							counter.incrementAndGet());
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
										.getWebTextField_6().getText()));
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
				q.setId(Integer.parseInt(String.valueOf(parentPanel.getCurrentRun().getQuiz().getId()) + String.valueOf(counter.incrementAndGet())));
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
				webQuestionDesignPanel.getWebTextField_6().setEnabled(true);
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
					toCopy.setId(Integer.parseInt(String.valueOf(parentPanel.getCurrentRun().getQuiz().getId()) + String.valueOf(counter.incrementAndGet())));
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
				if (webQuestionListPanel.getWebTable().getSelectedRow() == -1)
					return;

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
					webQuestionDesignPanel.getWebTextField_6()
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
				webQuestionDesignPanel.getWebTextField_6().setEnabled(
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
							.getWebTextField_6()
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
			webQuestionDesignPanel.getWebTextField_6().setText(
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

	public WebCheckBox getWbchckbxAuto() {
		return wbchckbxAuto;
	}

	public void setWbchckbxAuto(WebCheckBox wbchckbxAuto) {
		this.wbchckbxAuto = wbchckbxAuto;
	}

	public WebTextField getWbtxtfldPoints() {
		return wbtxtfldPoints;
	}

	public void setWbtxtfldPoints(WebTextField wbtxtfldPoints) {
		this.wbtxtfldPoints = wbtxtfldPoints;
	}

	public void loadCurrentQuiz(Quiz currentQuiz) {
		parentPanel.setEventsEnabled(false);
		wbtxtfldDirection.setText(currentQuiz.getTitle());
		wbtxtfldPoints.setText(String.valueOf(currentQuiz.getPoints()));
		wbtxtfldTime.setText(String.valueOf(currentQuiz.getTime()));
		
		DefaultTableModel model = (DefaultTableModel)webQuestionListPanel.getWebTable().getModel();
		for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
			Question question = currentQuiz.getQuestionList().get(i);
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
					question.getPoint(),
					question.getTitle()
			});
		}
		
		parentPanel.setEventsEnabled(true);
		if(model.getRowCount() != 0)
			webQuestionListPanel.getWebTable().getSelectionModel().setSelectionInterval(0, 0);
	}
}
