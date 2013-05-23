package com.jajeem.quiz.design;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

public class QuestionDesignPanel extends WebPanel {
	private WebTextField webTextField;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;
	private WebTextField webTextField_3;
	private WebTextField webTextField_4;
	private WebTextField webTextField_6;
	private WebTextArea webTextArea;
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
	private WebTextField webTextField_5;
	private WebComboBox webComboBox;
	private Panel_Bottom_1 parentPanel;
	private WebPanel webPanelOptions;
	private WebLabel wblblUrl;
	private WebLabel wblblQuestion;


	/**
	 * Create the panel.
	 */
	public QuestionDesignPanel(Panel_Bottom_1 panel) {
		this.parentPanel = panel;
		setWblblQuestion(new WebLabel());
		getWblblQuestion().setText("Question ?");
		
		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");
		
		WebLabel wblblQuestion_1 = new WebLabel();
		wblblQuestion_1.setText("Question");
		
		webComboBox = new WebComboBox();
		webComboBox.setModel(new DefaultComboBoxModel(new String[] {"Single Choice", "Multiple Choice", "Essay"}));
		webComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				CardLayout cl = (CardLayout)(webPanelOptions.getLayout());
		        cl.show(webPanelOptions, (String)arg0.getItem());
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
		        if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
		        	parentPanel.getParentPanel().getTablemodel().setValueAt(webComboBox.getSelectedItem().toString(),parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 1);
		        	parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().get(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow()).setType((byte) webComboBox.getSelectedIndex());
		        }
			}
		});
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebLabel wblblPoint = new WebLabel();
		wblblPoint.setText("Point");
		
		webTextField_6 = new WebTextField();
		webTextField_6.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getQuestionListPanel().getWebTable().setValueAt(webTextField_6.getText(), parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 2);
					try{
						parentPanel.getParentPanel().getCurrentQuestion().setPoint(Integer.parseInt(webTextField_6.getText()));
					}
					catch(Exception ex){
						parentPanel.getParentPanel().getCurrentQuestion().setPoint(0);
					}
					if(!parentPanel.getWebCheckBox().isSelected()){
						int total = 0;
						try {
							for (int i = 0; i < parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().size(); i++) {
								total+=parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().get(i).getPoint();
							}
							parentPanel.getWbTxtFldPoints().setText(String.valueOf(total));
						} catch (Exception e2) {
							//total = parentPanel.getParentPanel().getCurrentQuestion().getPoint();
							parentPanel.getWbTxtFldPoints().setText(String.valueOf(0));
						}
					}
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getQuestionListPanel().getWebTable().setValueAt(webTextField_6.getText(), parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 2);
					try{
						parentPanel.getParentPanel().getCurrentQuestion().setPoint(Integer.parseInt(webTextField_6.getText()));
					}
					catch(Exception ex){
						parentPanel.getParentPanel().getCurrentQuestion().setPoint(0);
					}
					if(!parentPanel.getWebCheckBox().isSelected()){
						int total = 0;
						try {
							for (int i = 0; i < parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().size(); i++) {
								total+=parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().get(i).getPoint();
							}
							parentPanel.getWbTxtFldPoints().setText(String.valueOf(total));
						} catch (Exception e2) {
							//total = parentPanel.getParentPanel().getCurrentQuestion().getPoint();
							parentPanel.getWbTxtFldPoints().setText(String.valueOf(0));
						}
					}
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		
		WebButton wbtnInsert = new WebButton();
		wbtnInsert.setEnabled(false);
		wbtnInsert.setText("Insert");
		
		wblblUrl = new WebLabel();
		wblblUrl.setText("Url");
		
		webTextField_5 = new WebTextField();
		webTextField_5.setEditable(false);
		webTextField_5.setEnabled(false);
		webTextField_5.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setUrl(webTextField_5.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setUrl(webTextField_5.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webPanel, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
						.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addGap(96)
							.addComponent(wblblPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_6, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(6)
								.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(webScrollPane_1, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(wblblUrl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(webTextField_5, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
									.addComponent(wbtnInsert, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
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
								.addComponent(wblblPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(webScrollPane_1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblUrl, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(wbtnInsert, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		WebImage webImage = new WebImage();
		webScrollPane_1.setViewportView(webImage);
		
		webTextField = new WebTextField();
		webTextField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer1(webTextField.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer1(webTextField.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		webTextField_1 = new WebTextField();
		webTextField_1.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer2(webTextField_1.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer2(webTextField_1.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		webTextField_2 = new WebTextField();
		webTextField_2.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer3(webTextField_2.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer3(webTextField_2.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		webTextField_3 = new WebTextField();
		webTextField_3.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer4(webTextField_3.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer4(webTextField_3.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		webTextField_4 = new WebTextField();
		webTextField_4.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer5(webTextField_4.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getParentPanel().getCurrentQuestion().setAnswer5(webTextField_4.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		webPanelOptions = new WebPanel(new CardLayout());
		
		WebPanel card1 = new WebPanel();
		WebPanel card2 = new WebPanel();
		WebPanel card3 = new WebPanel();
		
		webPanelOptions.add(card1,"Single Choice");
		
		webRadioButton = new WebRadioButton();
		webRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(webTextField.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Answer is empty,please first type in your answer!");
				webRadioButton.setSelected(true);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(false);
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected()
				});
			}
		});
		
		webRadioButton_1 = new WebRadioButton();
		webRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(webTextField_1.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Answer is empty,please first type in your answer!");
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(true);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(false);
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected()
				});
			}
		});
		
		webRadioButton_2 = new WebRadioButton();
		webRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(webTextField_2.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Answer is empty,please first type in your answer!");
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(true);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(false);
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected()
				});
			}
		});
		
		webRadioButton_3 = new WebRadioButton();
		webRadioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(webTextField_3.getText().equals(""))
					JOptionPane.showMessageDialog(null, "Answer is empty,please first type in your answer!");
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(true);
				webRadioButton_4.setSelected(false);
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected()
				});
			}
		});
		
		webRadioButton_4 = new WebRadioButton();
		webRadioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(webTextField_4.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Answer is empty,please first type in your answer!");
					return;
				}
				webRadioButton.setSelected(false);
				webRadioButton_1.setSelected(false);
				webRadioButton_2.setSelected(false);
				webRadioButton_3.setSelected(false);
				webRadioButton_4.setSelected(true);
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webRadioButton.isSelected(),
						webRadioButton_1.isSelected(),
						webRadioButton_2.isSelected(),
						webRadioButton_3.isSelected(),
						webRadioButton_4.isSelected()
				});
			}
		});
		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card1.createSequentialGroup()
					.addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
						.addComponent(webRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webRadioButton_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webRadioButton_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webRadioButton_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webRadioButton_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_card1.setVerticalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card1.createSequentialGroup()
					.addComponent(webRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webRadioButton_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webRadioButton_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webRadioButton_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(webRadioButton_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		card1.setLayout(gl_card1);
		webPanelOptions.add(card2,"Multiple Choice");
		
		webCheckBox = new WebCheckBox();
		webCheckBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webCheckBox.isSelected(),
						webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(),
						webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected()
				});
			}
		});
		
		webCheckBox_1 = new WebCheckBox();
		webCheckBox_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webCheckBox.isSelected(),
						webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(),
						webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected()
				});
			}
		});
		
		webCheckBox_2 = new WebCheckBox();
		webCheckBox_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webCheckBox.isSelected(),
						webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(),
						webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected()
				});
			}
		});
		
		webCheckBox_3 = new WebCheckBox();
		webCheckBox_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webCheckBox.isSelected(),
						webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(),
						webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected()
				});
			}
		});
		
		webCheckBox_4 = new WebCheckBox();
		webCheckBox_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.getParentPanel().getCurrentQuestion().setCorrectAnswer(new boolean[]{
						webCheckBox.isSelected(),
						webCheckBox_1.isSelected(),
						webCheckBox_2.isSelected(),
						webCheckBox_3.isSelected(),
						webCheckBox_4.isSelected()
				});
			}
		});
		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card2.createSequentialGroup()
					.addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
						.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webCheckBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_card2.setVerticalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card2.createSequentialGroup()
					.addComponent(webCheckBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webCheckBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webCheckBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webCheckBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(webCheckBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		card2.setLayout(gl_card2);
		webPanelOptions.add(card3,"Essay");
		
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(webPanelOptions, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(webTextField_4, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addComponent(webTextField_3, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addComponent(webTextField_1, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addComponent(webTextField, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(webPanelOptions, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_webPanel.createSequentialGroup()
							.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		webPanel.setLayout(gl_webPanel);
		
		webTextArea = new WebTextArea();
		webTextArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getQuestionListPanel().getWebTable().setValueAt(webTextArea.getText(), parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 3);
					parentPanel.getParentPanel().getCurrentQuestion().setTitle(webTextArea.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getQuestionListPanel().getWebTable().setValueAt(webTextArea.getText(), parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 3);
					parentPanel.getParentPanel().getCurrentQuestion().setTitle(webTextArea.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		webScrollPane.setViewportView(webTextArea);
		setLayout(groupLayout);

	}


	public WebTextArea getWebTextArea() {
		return webTextArea;
	}


	public void setWebTextArea(WebTextArea webTextArea) {
		this.webTextArea = webTextArea;
	}


	public void clear() {
		webTextArea.setText("");
        getWebTextField().setText("");
        getWebTextField_1().setText("");
        getWebTextField_2().setText("");
        getWebTextField_3().setText("");
        getWebTextField_4().setText("");
        getWebComboBox().setSelectedIndex(0);
        getWebRadioButton().setSelected(false);
        getWebRadioButton_1().setSelected(false);
        getWebRadioButton_2().setSelected(false);
        getWebRadioButton_3().setSelected(false);
        getWebRadioButton_4().setSelected(false);
        getWebCheckBox().setSelected(false);
        getWebCheckBox_1().setSelected(false);
        getWebCheckBox_2().setSelected(false);
        getWebCheckBox_3().setSelected(false);
        getWebCheckBox_4().setSelected(false);
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


	public WebCheckBox getWebCheckBox_3() {
		return webCheckBox_3;
	}


	public void setWebCheckBox_3(WebCheckBox webCheckBox_3) {
		this.webCheckBox_3 = webCheckBox_3;
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
	
	public WebTextField getWebTextField_5() {
		return webTextField_5;
	}


	public void setWebTextField_5(WebTextField webTextField_5) {
		this.webTextField_5 = webTextField_5;
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
}
