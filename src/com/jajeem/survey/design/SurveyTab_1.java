package com.jajeem.survey.design;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;

@SuppressWarnings("serial")
public class SurveyTab_1 extends WebPanel {

	private QuestionListPanel questionListPanel;
	private SurveyMain parentPanel;
	private QuestionDesignPanel questionDesignPanel;
	private WebTextField wbTxtFldDirection;
	//TODO remove the code below
	private int id=0;
	/**
	 * Create the panel.
	 * @param surveyMain 
	 */
	public SurveyTab_1(SurveyMain surveyMain) {
		//TODO remove the code below
		try {
			id = Integer.parseInt(com.jajeem.util.Config.getParam("qid"));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setParentPanel(surveyMain);
		WebPanel webPanel = new WebPanel();
		
		questionDesignPanel = new QuestionDesignPanel(this);
		questionDesignPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebButton wbBtn_add = new WebButton();
		wbBtn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(paren.getModel().getClass());
				parentPanel.setEventsEnabled(false);
				if(parentPanel.getCurrentQuestion() != null){
					parentPanel.getCurrentQuestion().setId(id++);
					parentPanel.getCurrentQuestion().setInstructorId(parentPanel.getCurrentSurvey().getInstructorId());
					parentPanel.getCurrentQuestion().setSurveyId(parentPanel.getCurrentSurvey().getId());
					parentPanel.getCurrentQuestion().setTitle(getQuestionDesignPanel().getWebTextArea().getText());
					parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField().getText());
					parentPanel.getCurrentQuestion().setAnswer2(getQuestionDesignPanel().getWebTextField_1().getText());
					parentPanel.getCurrentQuestion().setAnswer3(getQuestionDesignPanel().getWebTextField_2().getText());
					parentPanel.getCurrentQuestion().setAnswer4(getQuestionDesignPanel().getWebTextField_3().getText());
					parentPanel.getCurrentQuestion().setAnswer5(getQuestionDesignPanel().getWebTextField_4().getText());
					
					try{
						if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex() == 0){
							parentPanel.getCurrentQuestion().setType(Byte.parseByte("0"));
						}
						else if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex() == 1){
							parentPanel.getCurrentQuestion().setType(Byte.parseByte("1"));
						}
						else if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex() == 2){
							parentPanel.getCurrentQuestion().setType(Byte.parseByte("2"));
							
						}
					}
					catch(Exception ex){
						;
					}
				}
				DefaultTableModel model = (DefaultTableModel) questionListPanel.getWebTable().getModel();
				Object[] obj;
                if(questionListPanel.getWebTable().getRowCount() != 0)
                	obj = new Object[]{
                		Integer.parseInt(String.valueOf(model.getValueAt(questionListPanel.getWebTable().getRowCount()-1, 0)))+1,
                		getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),
                		""
                		};
                else
                	obj = new Object[]{
                		1,
                		getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),
                		""
                		};
				model.addRow(obj);
				Question q = new Question();
				q.setId(id++);
				q.setSurveyId(parentPanel.getCurrentSurvey().getId());
				q.setInstructorId(parentPanel.getCurrentSurvey().getInstructorId());
                getParentPanel().getCurrentSurvey().addQuestion(q);
                questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
                getQuestionDesignPanel().clear();
                
                //System.out.println(model.getDataVector().get(0));
                
                questionDesignPanel.setEnabled(true);
                questionDesignPanel.getWebScrollPane().setEnabled(true);
				questionDesignPanel.getWebTextField().setEnabled(true);
				questionDesignPanel.getWebTextField_1().setEnabled(true);
				questionDesignPanel.getWebTextField_2().setEnabled(true);
				questionDesignPanel.getWebTextField_3().setEnabled(true);
				questionDesignPanel.getWebTextField_4().setEnabled(true);
				questionDesignPanel.getWebComboBox().setEnabled(true);
			}
		});
		wbBtn_add.setText("Add");
		
		WebButton wBtn_delete = new WebButton();
		wBtn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(questionListPanel.getWebTable().getSelectedRow() == -1)
					return;	
				
				DefaultTableModel model = (DefaultTableModel)questionListPanel.getWebTable().getModel();
				int index = questionListPanel.getWebTable().getSelectedRow();
				model.removeRow(index);
				parentPanel.getCurrentSurvey().getQuestionList().remove(index);
				if(questionListPanel.getWebTable().getRowCount() != 0){
					ListSelectionModel m_modelSelection = questionListPanel.getWebTable().getSelectionModel();
					m_modelSelection.setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
				}
				
				
				if(getQuestionListPanel().getWebTable().getRowCount() == 0){
					questionDesignPanel.getWebScrollPane().setEnabled(false);
					questionDesignPanel.getWebTextField().setEnabled(false);
					questionDesignPanel.getWebTextField_1().setEnabled(false);
					questionDesignPanel.getWebTextField_2().setEnabled(false);
					questionDesignPanel.getWebTextField_3().setEnabled(false);
					questionDesignPanel.getWebTextField_4().setEnabled(false);
					questionDesignPanel.getWebComboBox().setEnabled(false);
				}
			}
		});
		wBtn_delete.setText("Delete");
		
		WebButton wBtn_copy = new WebButton();
		wBtn_copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.setEventsEnabled(false);
				if(parentPanel.getCurrentSurvey().getQuestionList().size() != 0 && questionListPanel.getWebTable().getRowCount() != 0){
					Question toCopy = new Question(parentPanel.getCurrentSurvey().getQuestionList().get(questionListPanel.getWebTable().getSelectedRow()));
					parentPanel.getCurrentSurvey().addQuestion(toCopy);
					String type = "";
					if(toCopy.getType()==0){
						type = "Single Choice";
					}
					else if(toCopy.getType()==1){
						type = "Multiple Choice";
					}
					else if(toCopy.getType()==2){
						type = "Essay";
					}
					parentPanel.getTablemodel().addRow(new Object[]{
						Integer.parseInt(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getRowCount()-1, 0)))+1,
						type,
						toCopy.getTitle()
					});
					questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
					
					
				}
				parentPanel.setEventsEnabled(true);
			}
		});
		wBtn_copy.setText("Copy");
		
		WebPanel webPanel_1 = new WebPanel();
		webPanel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebLabel webLabel = new WebLabel();
		webLabel.setText("Direction");
		
		setWbTxtFldDirection(new WebTextField());
		getWbTxtFldDirection().getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				parentPanel.getCurrentSurvey().setTitle(getWbTxtFldDirection().getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				parentPanel.getCurrentSurvey().setTitle(getWbTxtFldDirection().getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(wbTxtFldDirection, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(wbTxtFldDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		webPanel_1.setLayout(gl_webPanel_1);
		
		questionListPanel = new QuestionListPanel(this);
		setQuestionListPanel(questionListPanel);
		
		WebButton wBtn_up = new WebButton();
		wBtn_up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentIndex = questionListPanel.getWebTable().getSelectedRow();
				if(currentIndex == 0)
					return;
				
				DefaultTableModel model = (DefaultTableModel)questionListPanel.getWebTable().getModel();
				model.moveRow(currentIndex, currentIndex, currentIndex - 1);
				Question temp = parentPanel.getCurrentSurvey().getQuestionList().get(currentIndex);
				parentPanel.getCurrentSurvey().getQuestionList().remove(currentIndex);
				parentPanel.getCurrentSurvey().getQuestionList().add(currentIndex - 1, temp);
				questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(currentIndex - 1,currentIndex - 1);
				
			}
		});
		wBtn_up.setIcon(new ImageIcon(SurveyTab_1.class.getResource("/com/jajeem/images/Stock Index Up.png")));
		
		WebButton wBtn_down = new WebButton();
		wBtn_down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentIndex = questionListPanel.getWebTable().getSelectedRow();
				if(currentIndex == questionListPanel.getWebTable().getRowCount()-1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)questionListPanel.getWebTable().getModel();
				model.moveRow(currentIndex, currentIndex, currentIndex+1);
				Question temp = parentPanel.getCurrentSurvey().getQuestionList().get(currentIndex);
				parentPanel.getCurrentSurvey().getQuestionList().remove(currentIndex);
				parentPanel.getCurrentSurvey().getQuestionList().add(currentIndex + 1, temp);
				questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(currentIndex + 1,currentIndex + 1);
			}
		});
		wBtn_down.setIcon(new ImageIcon(SurveyTab_1.class.getResource("/com/jajeem/images/Stock Index Down.png")));
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addComponent(questionDesignPanel, GroupLayout.PREFERRED_SIZE, 457, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(questionListPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(wBtn_up, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(wBtn_down, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wbBtn_add, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wBtn_delete, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(wBtn_copy, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)))
					.addGap(191))
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_webPanel.createSequentialGroup()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_webPanel.createSequentialGroup()
							.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_webPanel.createSequentialGroup()
									.addGap(64)
									.addComponent(wBtn_up, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(wBtn_down, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_webPanel.createSequentialGroup()
									.addGap(10)
									.addComponent(questionListPanel, GroupLayout.PREFERRED_SIZE, 243, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(wbBtn_add, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(wBtn_delete, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(wBtn_copy, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
						.addComponent(questionDesignPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(368, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 950, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(184, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(363, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	public QuestionListPanel getQuestionListPanel() {
		return questionListPanel;
	}
	public void setQuestionListPanel(QuestionListPanel questionListPanel) {
		this.questionListPanel = questionListPanel;
	}
	public SurveyMain getParentPanel() {
		return parentPanel;
	}
	public void setParentPanel(SurveyMain parentPanel) {
		this.parentPanel = parentPanel;
	}
	public QuestionDesignPanel getQuestionDesignPanel() {
		return questionDesignPanel;
	}
	public void setQuestionDesignPanel(QuestionDesignPanel questionDesignPanel) {
		this.questionDesignPanel = questionDesignPanel;
	}
	public WebTextField getWbTxtFldDirection() {
		return wbTxtFldDirection;
	}
	public void setWbTxtFldDirection(WebTextField wbTxtFldDirection) {
		this.wbTxtFldDirection = wbTxtFldDirection;
	}
	
	public void clear() {
		parentPanel.setEventsEnabled(false);
		wbTxtFldDirection.setText("");
		getQuestionDesignPanel().clear();
		getQuestionListPanel().clear();
		parentPanel.setEventsEnabled(true);
	}
	public void loadCurrentSurvey(Survey currentSurvey) {
		parentPanel.setEventsEnabled(false);
		wbTxtFldDirection.setText(currentSurvey.getTitle());
		
		DefaultTableModel model = (DefaultTableModel)getQuestionListPanel().getWebTable().getModel();
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
					model.getRowCount() == 0 ? 1 : Integer.parseInt(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getRowCount()-1, 0)))+1,
					type,
					question.getTitle()
			});
		}
		
		parentPanel.setEventsEnabled(true);
		if(model.getRowCount() != 0)
			getQuestionListPanel().getWebTable().getSelectionModel().setSelectionInterval(0, 0);
		
	}
}
