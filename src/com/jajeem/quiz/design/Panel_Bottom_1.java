package com.jajeem.quiz.design;

import javax.swing.JPanel;
import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import com.alee.laf.button.WebButton;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;
import com.alee.laf.checkbox.WebCheckBox;
import com.jajeem.quiz.model.Question;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Panel_Bottom_1 extends WebPanel {

	private QuestionListPanel questionListPanel;
	private Main parentPanel;
	private WebCheckBox webCheckBox;
	private QuestionDesignPanel questionDesignPanel;
	private WebTextField webTextField_2;
	private WebCheckBox webCheckBox_1;
	/**
	 * Create the panel.
	 * @param main 
	 */
	public Panel_Bottom_1(Main main) {
		this.setParentPanel(main);
		WebPanel webPanel = new WebPanel();
		
		WebButton webButton = new WebButton();
		webButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListSelectionModel m_modelSelection = questionListPanel.getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
			}
		});
		webButton.setText("Previous");
		
		WebButton webButton_1 = new WebButton();
		webButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentPanel.setEventsEnabled(false);
                if(parentPanel.getCurrentQuestion() == null)
                	parentPanel.setCurrentQuestion(new Question());
                if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex()==0) {
                	if(!getQuestionDesignPanel().getWebRadioButton().isSelected()
                			&& !getQuestionDesignPanel().getWebRadioButton_1().isSelected()
                			&& !getQuestionDesignPanel().getWebRadioButton_2().isSelected()
                			&& !getQuestionDesignPanel().getWebRadioButton_3().isSelected()
                			&& !getQuestionDesignPanel().getWebRadioButton_4().isSelected()){
                		JOptionPane.showMessageDialog(null, "You must select one correct answer!");
                		return;
                	}
                }
                else if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex()==1){
                	if(!getQuestionDesignPanel().getWebCheckBox().isSelected() 
                			&& !getQuestionDesignPanel().getWebCheckBox_1().isSelected()
                			&& !getQuestionDesignPanel().getWebCheckBox_2().isSelected()
                			&& !getQuestionDesignPanel().getWebCheckBox_3().isSelected()
                			&& !getQuestionDesignPanel().getWebCheckBox_4().isSelected()){
                		JOptionPane.showMessageDialog(null, "You must select at least one correct answer!");
                		return;
                	}
                }
				parentPanel.getCurrentQuestion().setTitle(getQuestionDesignPanel().getWebTextArea().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField_1().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField_2().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField_3().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField_4().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField_5().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField_6().getText());
				parentPanel.getCurrentQuestion().setInstructorId(parentPanel.getInstructorId());
				try{
					parentPanel.getCurrentQuestion().setPoint(Integer.parseInt(getQuestionDesignPanel().getWebTextField_6().getText()));
				}
				catch(Exception ex){
					;
				}
				try{
					if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex() == 0){
						parentPanel.getCurrentQuestion().setType(Byte.parseByte("0"));
						parentPanel.getCurrentQuestion().setCorrectAnswer(
								new boolean[]{
										getQuestionDesignPanel().getWebRadioButton().isSelected(),
										getQuestionDesignPanel().getWebRadioButton_1().isSelected(),
										getQuestionDesignPanel().getWebRadioButton_2().isSelected(),
										getQuestionDesignPanel().getWebRadioButton_3().isSelected(),
										getQuestionDesignPanel().getWebRadioButton_4().isSelected()});
					}
					else if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex() == 1){
						parentPanel.getCurrentQuestion().setType(Byte.parseByte("1"));
						parentPanel.getCurrentQuestion().setCorrectAnswer(
								new boolean[]{
										getQuestionDesignPanel().getWebCheckBox().isSelected(),
										getQuestionDesignPanel().getWebCheckBox_1().isSelected(),
										getQuestionDesignPanel().getWebCheckBox_2().isSelected(),
										getQuestionDesignPanel().getWebCheckBox_3().isSelected(),
										getQuestionDesignPanel().getWebCheckBox_4().isSelected()});
					}
					else if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex() == 2){
						parentPanel.getCurrentQuestion().setType(Byte.parseByte("2"));
						
					}
					
					parentPanel.getCurrentQuestion().setUrl(getQuestionDesignPanel().getWebTextField_5().getText());
				}
				catch(Exception ex){
					;
				}
				
				/// Add the question to the current quiz and question list
				if(questionListPanel.getWebTable().getSelectedRow() == -1){ // meaning no question is selected so the changes are made to a new question not an existing one
					parentPanel.getCurrentQuiz().addQuestion(parentPanel.getCurrentQuestion());
					parentPanel.getTablemodel().addRow(
							new Object[]{questionListPanel.getWebTable().getRowCount() + 1,
									getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),
									parentPanel.getCurrentQuestion().getPoint(),
									parentPanel.getCurrentQuestion().getTitle()});
				}
				else{ //changes are made to an existing question because a question is already selected
					
				}
				///empty all fields and ready for new question
				getQuestionDesignPanel().getWebTextArea().setText("");
				getQuestionDesignPanel().getWebTextField().setText("");
				getQuestionDesignPanel().getWebTextField_1().setText("");
				getQuestionDesignPanel().getWebTextField_2().setText("");
				getQuestionDesignPanel().getWebTextField_3().setText("");
				getQuestionDesignPanel().getWebTextField_4().setText("");
				getQuestionDesignPanel().getWebTextField_5().setText("");
				getQuestionDesignPanel().getWebTextField_6().setText("");
				getQuestionDesignPanel().getWebRadioButton().setSelected(false);
				getQuestionDesignPanel().getWebRadioButton_1().setSelected(false);
				getQuestionDesignPanel().getWebRadioButton_2().setSelected(false);
				getQuestionDesignPanel().getWebRadioButton_3().setSelected(false);
				getQuestionDesignPanel().getWebRadioButton_4().setSelected(false);
				getQuestionDesignPanel().getWebCheckBox().setSelected(false);
				getQuestionDesignPanel().getWebCheckBox_1().setSelected(false);
				getQuestionDesignPanel().getWebCheckBox_2().setSelected(false);
				getQuestionDesignPanel().getWebCheckBox_3().setSelected(false);
				getQuestionDesignPanel().getWebCheckBox_4().setSelected(false);
				
				parentPanel.getCurrentQuiz().addQuestion(new Question());
				parentPanel.getTablemodel().addRow(new Object[]{questionListPanel.getWebTable().getRowCount(),getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),0,""});
				ListSelectionModel m_modelSelection = questionListPanel.getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
				parentPanel.setEventsEnabled(true);
			}
		});
		webButton_1.setText("Next");
		
		setQuestionDesignPanel(new QuestionDesignPanel(this));
		getQuestionDesignPanel().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebButton webButton_2 = new WebButton();
		webButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(paren.getModel().getClass());
				DefaultTableModel model = (DefaultTableModel) questionListPanel.getWebTable().getModel();
				Object[] obj;
                if(questionListPanel.getWebTable().getRowCount() != 0)
                	obj = new Object[]{
                		Integer.parseInt(String.valueOf(model.getValueAt(questionListPanel.getWebTable().getRowCount()-1, 0)))+1,
                        "Single Choice",
                        "",
                        ""};
                else
                	obj = new Object[]{
                		1,
                        "Single Choice",
                        "",
                        ""};
				model.addRow(obj);
                getParentPanel().getCurrentQuiz().addQuestion(new Question());
                questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
                getQuestionDesignPanel().clear();
                
                //System.out.println(model.getDataVector().get(0));
                
                if(webCheckBox.isSelected() && getParentPanel().getCurrentQuiz().getQuestionList().size()!=0){
	                int point = getParentPanel().getCurrentQuiz().getPoints()/getParentPanel().getCurrentQuiz().getQuestionList().size();
					for (int i=0;i<getParentPanel().getCurrentQuiz().getQuestionList().size();i++) {
						getParentPanel().getCurrentQuiz().getQuestionList().get(i).setPoint(point);
						getParentPanel().getTablemodel().setValueAt(point, i, 2);
					}
					int remainder = (getParentPanel().getCurrentQuiz().getPoints() - getParentPanel().getCurrentQuiz().getQuestionList().size() * point);
					if(remainder!=0){
						getParentPanel().getCurrentQuiz().getQuestionList().get(getParentPanel().getCurrentQuiz().getQuestionList().size()-1).setPoint(point+remainder);
						getParentPanel().getTablemodel().setValueAt(point+remainder, questionListPanel.getWebTable().getRowCount()-1, 2);
					}
					getQuestionDesignPanel().getWebTextField_6().setText(String.valueOf(getParentPanel().getTablemodel().getValueAt(questionListPanel.getWebTable().getSelectedRow(), 2)));
				}
			}
		});
		webButton_2.setText("Add");
		
		WebButton webButton_3 = new WebButton();
		webButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(questionListPanel.getWebTable().getSelectedRow() == -1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)questionListPanel.getWebTable().getModel();
				int index = questionListPanel.getWebTable().getSelectedRow();
				model.removeRow(index);
				parentPanel.getCurrentQuiz().getQuestionList().remove(index);
				if(questionListPanel.getWebTable().getRowCount() != 0){
					ListSelectionModel m_modelSelection = questionListPanel.getWebTable().getSelectionModel();
					m_modelSelection.setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
				}
				if(webCheckBox.isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0){
	                int point = parentPanel.getCurrentQuiz().getPoints()/parentPanel.getCurrentQuiz().getQuestionList().size();
					for (int i=0;i<parentPanel.getCurrentQuiz().getQuestionList().size();i++) {
						parentPanel.getCurrentQuiz().getQuestionList().get(i).setPoint(point);
						parentPanel.getTablemodel().setValueAt(point, i, 2);
					}
					int remainder = (parentPanel.getCurrentQuiz().getPoints() - parentPanel.getCurrentQuiz().getQuestionList().size() * point);
					if(remainder!=0){
						parentPanel.getCurrentQuiz().getQuestionList().get(parentPanel.getCurrentQuiz().getQuestionList().size()-1).setPoint(point+remainder);
						parentPanel.getTablemodel().setValueAt(point+remainder, questionListPanel.getWebTable().getRowCount()-1, 2);
					}
					getQuestionDesignPanel().getWebTextField_6().setText(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getSelectedRow(), 2)));
				}
			}
		});
		webButton_3.setText("Delete");
		
		WebButton webButton_4 = new WebButton();
		webButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.setEventsEnabled(false);
				if(parentPanel.getCurrentQuiz().getQuestionList().size() != 0 && questionListPanel.getWebTable().getRowCount() != 0){
					Question toCopy = parentPanel.getCurrentQuiz().getQuestionList().get(questionListPanel.getWebTable().getSelectedRow());
					parentPanel.getCurrentQuiz().addQuestion(toCopy);
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
						toCopy.getPoint(),
						toCopy.getTitle()
					});
					questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
					
					if(webCheckBox.isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0){
		                int point = parentPanel.getCurrentQuiz().getPoints()/parentPanel.getCurrentQuiz().getQuestionList().size();
						for (int i=0;i<parentPanel.getCurrentQuiz().getQuestionList().size();i++) {
							parentPanel.getCurrentQuiz().getQuestionList().get(i).setPoint(point);
							parentPanel.getTablemodel().setValueAt(point, i, 2);
						}
						int remainder = (parentPanel.getCurrentQuiz().getPoints() - parentPanel.getCurrentQuiz().getQuestionList().size() * point);
						if(remainder!=0){
							parentPanel.getCurrentQuiz().getQuestionList().get(parentPanel.getCurrentQuiz().getQuestionList().size()-1).setPoint(point+remainder);
							parentPanel.getTablemodel().setValueAt(point+remainder, questionListPanel.getWebTable().getRowCount()-1, 2);
						}
						getQuestionDesignPanel().getWebTextField_6().setText(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getSelectedRow(), 2)));
					}
				}
				parentPanel.setEventsEnabled(true);
			}
		});
		webButton_4.setText("Copy");
		
		WebPanel webPanel_1 = new WebPanel();
		webPanel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebLabel webLabel = new WebLabel();
		webLabel.setText("Direction");
		
		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("Points");
		
		WebTextField webTextField = new WebTextField();
		
		final WebTextField webTextField_1 = new WebTextField();
		webTextField_1.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getCurrentQuiz()!=null && parentPanel.isEventsEnabled()){
					try {
						parentPanel.getCurrentQuiz().setPoints(Integer.parseInt(webTextField_1.getText()));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					if(webCheckBox.isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0){
		                int point = parentPanel.getCurrentQuiz().getPoints()/parentPanel.getCurrentQuiz().getQuestionList().size();
						for (int i=0;i<parentPanel.getCurrentQuiz().getQuestionList().size();i++) {
							parentPanel.getCurrentQuiz().getQuestionList().get(i).setPoint(point);
							parentPanel.getTablemodel().setValueAt(point, i, 2);
						}
						int remainder = (parentPanel.getCurrentQuiz().getPoints() - parentPanel.getCurrentQuiz().getQuestionList().size() * point);
						if(remainder!=0){
							parentPanel.getCurrentQuiz().getQuestionList().get(parentPanel.getCurrentQuiz().getQuestionList().size()-1).setPoint(point+remainder);
							parentPanel.getTablemodel().setValueAt(point+remainder, questionListPanel.getWebTable().getRowCount()-1, 2);
						}
						getQuestionDesignPanel().getWebTextField_6().setText(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getSelectedRow(), 2)));
					}
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getCurrentQuiz()!=null && parentPanel.isEventsEnabled()){
					try {
						parentPanel.getCurrentQuiz().setPoints(Integer.parseInt(webTextField_1.getText()));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					if(webCheckBox.isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0){
		                int point = parentPanel.getCurrentQuiz().getPoints()/parentPanel.getCurrentQuiz().getQuestionList().size();
						for (int i=0;i<parentPanel.getCurrentQuiz().getQuestionList().size();i++) {
							parentPanel.getCurrentQuiz().getQuestionList().get(i).setPoint(point);
							parentPanel.getTablemodel().setValueAt(point, i, 2);
						}
						int remainder = (parentPanel.getCurrentQuiz().getPoints() - parentPanel.getCurrentQuiz().getQuestionList().size() * point);
						if(remainder!=0){
							parentPanel.getCurrentQuiz().getQuestionList().get(parentPanel.getCurrentQuiz().getQuestionList().size()-1).setPoint(point+remainder);
							parentPanel.getTablemodel().setValueAt(point+remainder, questionListPanel.getWebTable().getRowCount()-1, 2);
						}
						getQuestionDesignPanel().getWebTextField_6().setText(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getSelectedRow(), 2)));
					}
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		webCheckBox = new WebCheckBox();
		webCheckBox.setSelected(true);
		webCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				getQuestionDesignPanel().getWebTextField_6().setEnabled(!webCheckBox.isSelected());
			}
		});
		webCheckBox.setText("Auto");
		
		webCheckBox_1 = new WebCheckBox();
		webCheckBox_1.setText("Shuffle");
		
		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("Time Limit");
		
		webTextField_2 = new WebTextField();
		webTextField_2.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getCurrentQuiz() != null && parentPanel.isEventsEnabled()){
					try{
						parentPanel.getCurrentQuiz().setTime(Integer.parseInt(webTextField_2.getText()));
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getCurrentQuiz() != null && parentPanel.isEventsEnabled()){
					try{
						parentPanel.getCurrentQuiz().setTime(Integer.parseInt(webTextField_2.getText()));
					}
					catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
				}
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
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
								.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(82)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_webPanel_1.createSequentialGroup()
									.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))))
						.addComponent(webTextField, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		webPanel_1.setLayout(gl_webPanel_1);
		
		questionListPanel = new QuestionListPanel(this);
		setQuestionListPanel(questionListPanel);
		
		WebButton webButton_6 = new WebButton();
		webButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentIndex = questionListPanel.getWebTable().getSelectedRow();
				if(currentIndex == 0)
					return;
				
				DefaultTableModel model = (DefaultTableModel)questionListPanel.getWebTable().getModel();
				model.moveRow(currentIndex, currentIndex, currentIndex - 1);
				Question temp = parentPanel.getCurrentQuiz().getQuestionList().get(currentIndex);
				parentPanel.getCurrentQuiz().getQuestionList().remove(currentIndex);
				parentPanel.getCurrentQuiz().getQuestionList().add(currentIndex - 1, temp);
			}
		});
		webButton_6.setIcon(new ImageIcon(Panel_Bottom_1.class.getResource("/com/jajeem/images/Stock Index Up.png")));
		
		WebButton webButton_5 = new WebButton();
		webButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int currentIndex = questionListPanel.getWebTable().getSelectedRow();
				if(currentIndex == questionListPanel.getWebTable().getRowCount()-1)
					return;
				
				DefaultTableModel model = (DefaultTableModel)questionListPanel.getWebTable().getModel();
				model.moveRow(currentIndex, currentIndex, currentIndex+1);
				Question temp = parentPanel.getCurrentQuiz().getQuestionList().get(currentIndex);
				parentPanel.getCurrentQuiz().getQuestionList().remove(currentIndex);
				parentPanel.getCurrentQuiz().getQuestionList().add(currentIndex + 1, temp);
			}
		});
		webButton_5.setIcon(new ImageIcon(Panel_Bottom_1.class.getResource("/com/jajeem/images/Stock Index Down.png")));
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(webButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webButton_1, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addComponent(getQuestionDesignPanel(), GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGap(24)
							.addComponent(webButton_2, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(webButton_3, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webButton_4, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(webPanel_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addComponent(getQuestionListPanel(), Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 439, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(webButton_5, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addComponent(webButton_6, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(getQuestionDesignPanel(), GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_webPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(getQuestionListPanel(), GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
								.addGroup(gl_webPanel.createSequentialGroup()
									.addGap(156)
									.addComponent(webButton_6, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webButton_5, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(webButton_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton_3, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButton_4, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		webPanel.setLayout(gl_webPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(webPanel, GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
					.addGap(2))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(webPanel, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
					.addGap(3))
		);
		setLayout(groupLayout);

	}
	public QuestionListPanel getQuestionListPanel() {
		return questionListPanel;
	}
	public void setQuestionListPanel(QuestionListPanel questionListPanel) {
		this.questionListPanel = questionListPanel;
	}
	public Main getParentPanel() {
		return parentPanel;
	}
	public void setParentPanel(Main parentPanel) {
		this.parentPanel = parentPanel;
	}
	public QuestionDesignPanel getQuestionDesignPanel() {
		return questionDesignPanel;
	}
	public void setQuestionDesignPanel(QuestionDesignPanel questionDesignPanel) {
		this.questionDesignPanel = questionDesignPanel;
	}
}
