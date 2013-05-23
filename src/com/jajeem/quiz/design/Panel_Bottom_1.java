package com.jajeem.quiz.design;

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
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;

@SuppressWarnings("serial")
public class Panel_Bottom_1 extends WebPanel {

	private QuestionListPanel questionListPanel;
	private Main parentPanel;
	private WebCheckBox webCheckBox;
	private QuestionDesignPanel questionDesignPanel;
	private WebTextField wbTxtFldTimeLimit;
	private WebCheckBox webCheckBox_1;
	private WebTextField wbTxtFldDirection;
	private WebTextField wbTxtFldPoints;
	//TODO remove the code below
	private int id=0;
	/**
	 * Create the panel.
	 * @param main 
	 */
	public Panel_Bottom_1(Main main) {
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
		this.setParentPanel(main);
		WebPanel webPanel = new WebPanel();
		
		final WebButton wbBtn_Next = new WebButton();
		wbBtn_Next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentPanel.setEventsEnabled(false);
				if(parentPanel.getCurrentQuestion() == null)
                	parentPanel.setCurrentQuestion(new Question());
//                if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex()==0) {
//                	if(!getQuestionDesignPanel().getWebRadioButton().isSelected()
//                			&& !getQuestionDesignPanel().getWebRadioButton_1().isSelected()
//                			&& !getQuestionDesignPanel().getWebRadioButton_2().isSelected()
//                			&& !getQuestionDesignPanel().getWebRadioButton_3().isSelected()
//                			&& !getQuestionDesignPanel().getWebRadioButton_4().isSelected()){
//                		JOptionPane.showMessageDialog(null, "You must select one correct answer!");
//                		return;
//                	}
//                }
//                else if(getQuestionDesignPanel().getWebComboBox().getSelectedIndex()==1){
//                	if(!getQuestionDesignPanel().getWebCheckBox().isSelected() 
//                			&& !getQuestionDesignPanel().getWebCheckBox_1().isSelected()
//                			&& !getQuestionDesignPanel().getWebCheckBox_2().isSelected()
//                			&& !getQuestionDesignPanel().getWebCheckBox_3().isSelected()
//                			&& !getQuestionDesignPanel().getWebCheckBox_4().isSelected()){
//                		JOptionPane.showMessageDialog(null, "You must select at least one correct answer!");
//                		return;
//                	}
//                }
				parentPanel.getCurrentQuestion().setId(id++);
				parentPanel.getCurrentQuestion().setInstructorId(parentPanel.getCurrentQuiz().getInstructorId());
                parentPanel.getCurrentQuestion().setQuizId(parentPanel.getCurrentQuiz().getId());
				parentPanel.getCurrentQuestion().setTitle(getQuestionDesignPanel().getWebTextArea().getText());
				parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField().getText());
				parentPanel.getCurrentQuestion().setAnswer2(getQuestionDesignPanel().getWebTextField_1().getText());
				parentPanel.getCurrentQuestion().setAnswer3(getQuestionDesignPanel().getWebTextField_2().getText());
				parentPanel.getCurrentQuestion().setAnswer4(getQuestionDesignPanel().getWebTextField_3().getText());
				parentPanel.getCurrentQuestion().setAnswer5(getQuestionDesignPanel().getWebTextField_4().getText());
				parentPanel.getCurrentQuestion().setUrl(getQuestionDesignPanel().getWebTextField_5().getText());
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
				}
				catch(Exception ex){
					;
				}
				/// Add the question to the current quiz and question list
				if(questionListPanel.getWebTable().getSelectedRow() == -1){ // meaning no question is selected so the changes are made to a new question not an existing one
					parentPanel.getCurrentQuiz().addQuestion(parentPanel.getCurrentQuestion());
					if(questionListPanel.getWebTable().getRowCount() != 0)
					parentPanel.getTablemodel().addRow(
							new Object[]{
									Integer.parseInt(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getRowCount()-1, 0)))+1,
									getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),
									parentPanel.getCurrentQuestion().getPoint(),
									parentPanel.getCurrentQuestion().getTitle()});
					else
						parentPanel.getTablemodel().addRow(
								new Object[]{
									1,
									getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),
									parentPanel.getCurrentQuestion().getPoint(),
									parentPanel.getCurrentQuestion().getTitle()});
				}
				else{ //changes are made to an existing question because a question is already selected
					
				}
				///empty all fields and ready for new question
				getQuestionDesignPanel().clear();
				
				Question q = new Question();
				q.setId(id++);
				q.setInstructorId(parentPanel.getCurrentQuiz().getInstructorId());
				q.setQuizId(parentPanel.getCurrentQuiz().getId());
				parentPanel.getCurrentQuiz().addQuestion(q);
				parentPanel.getTablemodel().addRow(new Object[]{questionListPanel.getWebTable().getRowCount() + 1,getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),0,""});
				ListSelectionModel m_modelSelection = questionListPanel.getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
				
				if(getWebCheckBox().isSelected() && getParentPanel().getCurrentQuiz().getQuestionList().size()!=0){
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
					parentPanel.setEventsEnabled(true);
				}
				
				parentPanel.setEventsEnabled(true);
			}
		});
		wbBtn_Next.setText("Next");
		
		questionDesignPanel = new QuestionDesignPanel(this);
		getQuestionDesignPanel().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebButton wbBtn_add = new WebButton();
		wbBtn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(paren.getModel().getClass());
				parentPanel.setEventsEnabled(false);
				if(parentPanel.getCurrentQuestion() != null){
					parentPanel.getCurrentQuestion().setId(id++);
					parentPanel.getCurrentQuestion().setInstructorId(parentPanel.getCurrentQuiz().getInstructorId());
					parentPanel.getCurrentQuestion().setQuizId(parentPanel.getCurrentQuiz().getId());
					parentPanel.getCurrentQuestion().setTitle(getQuestionDesignPanel().getWebTextArea().getText());
					parentPanel.getCurrentQuestion().setAnswer1(getQuestionDesignPanel().getWebTextField().getText());
					parentPanel.getCurrentQuestion().setAnswer2(getQuestionDesignPanel().getWebTextField_1().getText());
					parentPanel.getCurrentQuestion().setAnswer3(getQuestionDesignPanel().getWebTextField_2().getText());
					parentPanel.getCurrentQuestion().setAnswer4(getQuestionDesignPanel().getWebTextField_3().getText());
					parentPanel.getCurrentQuestion().setAnswer5(getQuestionDesignPanel().getWebTextField_4().getText());
					parentPanel.getCurrentQuestion().setUrl(getQuestionDesignPanel().getWebTextField_5().getText());
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
                        0,
                        ""};
                else
                	obj = new Object[]{
                		1,
                		getQuestionDesignPanel().getWebComboBox().getSelectedItem().toString(),
                        0,
                        ""};
				model.addRow(obj);
				Question q = new Question();
				q.setId(id++);
				q.setQuizId(parentPanel.getCurrentQuiz().getId());
				q.setInstructorId(parentPanel.getCurrentQuiz().getInstructorId());
                getParentPanel().getCurrentQuiz().addQuestion(q);
                questionListPanel.getWebTable().getSelectionModel().setSelectionInterval(questionListPanel.getWebTable().getRowCount()-1, questionListPanel.getWebTable().getRowCount()-1);
                getQuestionDesignPanel().clear();
                
                //System.out.println(model.getDataVector().get(0));
                
                if(getWebCheckBox().isSelected() && getParentPanel().getCurrentQuiz().getQuestionList().size()!=0){
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
					parentPanel.setEventsEnabled(true);
				}
                
                wbBtn_Next.setEnabled(true);
                questionDesignPanel.setEnabled(true);
                questionDesignPanel.getWebScrollPane().setEnabled(true);
				questionDesignPanel.getWebTextField().setEnabled(true);
				questionDesignPanel.getWebTextField_1().setEnabled(true);
				questionDesignPanel.getWebTextField_2().setEnabled(true);
				questionDesignPanel.getWebTextField_3().setEnabled(true);
				questionDesignPanel.getWebTextField_4().setEnabled(true);
				questionDesignPanel.getWebTextField_6().setEnabled(true);
				questionDesignPanel.getWebRadioButton().setEnabled(true);
				questionDesignPanel.getWebRadioButton_1().setEnabled(true);
				questionDesignPanel.getWebRadioButton_2().setEnabled(true);
				questionDesignPanel.getWebRadioButton_3().setEnabled(true);
				questionDesignPanel.getWebRadioButton_4().setEnabled(true);
				questionDesignPanel.getWebComboBox().setEnabled(true);
			}
		});
		wbBtn_add.setText("Add");
		
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
				if(getWebCheckBox().isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0 && parentPanel.getTablemodel().getRowCount() != 0){
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
				
				if(getQuestionListPanel().getWebTable().getRowCount() == 0){
					wbBtn_Next.setEnabled(false);
					questionDesignPanel.getWebScrollPane().setEnabled(false);
					questionDesignPanel.getWebTextField().setEnabled(false);
					questionDesignPanel.getWebTextField_1().setEnabled(false);
					questionDesignPanel.getWebTextField_2().setEnabled(false);
					questionDesignPanel.getWebTextField_3().setEnabled(false);
					questionDesignPanel.getWebTextField_4().setEnabled(false);
					questionDesignPanel.getWebTextField_6().setEnabled(false);
					questionDesignPanel.getWebRadioButton().setEnabled(false);
					questionDesignPanel.getWebRadioButton_1().setEnabled(false);
					questionDesignPanel.getWebRadioButton_2().setEnabled(false);
					questionDesignPanel.getWebRadioButton_3().setEnabled(false);
					questionDesignPanel.getWebRadioButton_4().setEnabled(false);
					questionDesignPanel.getWebComboBox().setEnabled(false);
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
					
					if(getWebCheckBox().isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0 && parentPanel.getTablemodel().getRowCount() != 0){
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
		
		setWbTxtFldDirection(new WebTextField());
		getWbTxtFldDirection().getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				parentPanel.getCurrentQuiz().setTitle(getWbTxtFldDirection().getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				parentPanel.getCurrentQuiz().setTitle(getWbTxtFldDirection().getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setWbTxtFldPoints(new WebTextField());
		getWbTxtFldPoints().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getCurrentQuiz()!=null && parentPanel.isEventsEnabled()){
					try {
						//System.out.println(getWbTxtFldPoints().getText());
						parentPanel.getCurrentQuiz().setPoints(Integer.parseInt(getWbTxtFldPoints().getText()));
					} catch (Exception e) {
						//JOptionPane.showMessageDialog(null, e.getMessage());
					}
					if(getWebCheckBox().isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0 && parentPanel.getTablemodel().getRowCount() != 0){
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
						//System.out.println(getWbTxtFldPoints().getText());
						parentPanel.getCurrentQuiz().setPoints(Integer.parseInt(getWbTxtFldPoints().getText()));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Invalid value");
					}
					if(getWebCheckBox().isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0 && parentPanel.getTablemodel().getRowCount() != 0){
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
		
		setWebCheckBox(new WebCheckBox());
		getWebCheckBox().setSelected(true);
		getWebCheckBox().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				getQuestionDesignPanel().getWebTextField_6().setEnabled(!getWebCheckBox().isSelected());
				if(getWebCheckBox().isSelected() && parentPanel.getCurrentQuiz().getQuestionList().size()!=0 && parentPanel.getTablemodel().getRowCount() != 0){
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
		getWebCheckBox().setText("Auto");
		
		webCheckBox_1 = new WebCheckBox();
		webCheckBox_1.setEnabled(false);
		webCheckBox_1.setText("Shuffle");
		
		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("Time Limit");
		
		wbTxtFldTimeLimit = new WebTextField();
		wbTxtFldTimeLimit.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getCurrentQuiz() != null && parentPanel.isEventsEnabled()){
					try{
						parentPanel.getCurrentQuiz().setTime(Integer.parseInt(wbTxtFldTimeLimit.getText()));
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
						parentPanel.getCurrentQuiz().setTime(Integer.parseInt(wbTxtFldTimeLimit.getText()));
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
								.addComponent(getWbTxtFldPoints(), GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
								.addComponent(getWebCheckBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(82)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_webPanel_1.createSequentialGroup()
									.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(wbTxtFldTimeLimit, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))))
						.addComponent(getWbTxtFldDirection(), GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(getWbTxtFldDirection(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbTxtFldTimeLimit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWbTxtFldPoints(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebCheckBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(wbBtn_Next, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
						.addComponent(questionDesignPanel, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGap(24)
							.addComponent(wbBtn_add, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(webButton_3, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webButton_4, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(webPanel_1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addComponent(questionListPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 439, Short.MAX_VALUE))
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
						.addComponent(questionDesignPanel, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_webPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(questionListPanel, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
								.addGroup(gl_webPanel.createSequentialGroup()
									.addGap(156)
									.addComponent(webButton_6, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webButton_5, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(wbBtn_Next, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbBtn_add, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
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
	public WebTextField getWbTxtFldDirection() {
		return wbTxtFldDirection;
	}
	public void setWbTxtFldDirection(WebTextField wbTxtFldDirection) {
		this.wbTxtFldDirection = wbTxtFldDirection;
	}
	public WebTextField getWbTxtFldPoints() {
		return wbTxtFldPoints;
	}
	public void setWbTxtFldPoints(WebTextField wbTxtFldPoints) {
		this.wbTxtFldPoints = wbTxtFldPoints;
	}
	public WebCheckBox getWebCheckBox() {
		return webCheckBox;
	}
	public void setWebCheckBox(WebCheckBox webCheckBox) {
		this.webCheckBox = webCheckBox;
	}
	public void clear() {
		wbTxtFldDirection.setText("");
		wbTxtFldPoints.setText("");
		wbTxtFldTimeLimit.setText("");
		getQuestionDesignPanel().clear();
		getQuestionListPanel().clear();
	}
	public void loadCurrentQuiz(Quiz currentQuiz) {
		wbTxtFldDirection.setText(currentQuiz.getTitle());
		wbTxtFldPoints.setText(String.valueOf(currentQuiz.getPoints()));
		wbTxtFldTimeLimit.setText(String.valueOf(currentQuiz.getTime()));
		
		DefaultTableModel model = (DefaultTableModel)getQuestionListPanel().getWebTable().getModel();
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
					model.getRowCount() == 0 ? 1 : Integer.parseInt(String.valueOf(parentPanel.getTablemodel().getValueAt(questionListPanel.getWebTable().getRowCount()-1, 0)))+1,
					type,
					question.getPoint(),
					question.getTitle()
			});
		}
		
		if(model.getRowCount() != 0)
			getQuestionListPanel().getWebTable().getSelectionModel().setSelectionInterval(0, 0);
		
	}
}
