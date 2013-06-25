package com.jajeem.survey.design;

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
	private WebTextArea webTextArea;
	private WebScrollPane webScrollPane;
	private WebComboBox webComboBox;
	private SurveyTab_1 parentPanel;
	private WebPanel webPanelOptions;
	private WebLabel wblblQuestion;


	/**
	 * Create the panel.
	 */
	public QuestionDesignPanel(SurveyTab_1 panel) {
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
		        if(((String)arg0.getItem()).equals("Essay")){
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
		        	
		        }
		        else{
		        	webTextField.setVisible(true);
		        	webTextField_1.setVisible(true);
		        	webTextField_2.setVisible(true);
		        	webTextField_3.setVisible(true);
		        	webTextField_4.setVisible(true);
		        }
		        if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
		        	parentPanel.getParentPanel().getTablemodel().setValueAt(webComboBox.getSelectedItem().toString(),parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 1);
		        	parentPanel.getParentPanel().getCurrentSurvey().getQuestionList().get(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow()).setType((byte) webComboBox.getSelectedIndex());
		        }
			}
		});
		
		setWebScrollPane(new WebScrollPane((Component) null));
		getWebScrollPane().setEnabled(false);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(webPanel, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addGap(6)
									.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)))
							.addGap(0)))
					.addGap(5))
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
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
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
		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 17, Short.MAX_VALUE)
		);
		gl_card1.setVerticalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 154, Short.MAX_VALUE)
		);
		card1.setLayout(gl_card1);
		webPanelOptions.add(card2,"Multiple Choice");
		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 17, Short.MAX_VALUE)
		);
		gl_card2.setVerticalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 154, Short.MAX_VALUE)
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
					parentPanel.getQuestionListPanel().getWebTable().setValueAt(webTextArea.getText(), parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 2);
					parentPanel.getParentPanel().getCurrentQuestion().setTitle(webTextArea.getText());
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
					parentPanel.getQuestionListPanel().getWebTable().setValueAt(webTextArea.getText(), parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 2);
					parentPanel.getParentPanel().getCurrentQuestion().setTitle(webTextArea.getText());
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		getWebScrollPane().setViewportView(webTextArea);
		setLayout(groupLayout);

	}


	public WebTextArea getWebTextArea() {
		return webTextArea;
	}


	public void setWebTextArea(WebTextArea webTextArea) {
		this.webTextArea = webTextArea;
	}


	public void clear() {
		parentPanel.getParentPanel().setEventsEnabled(false);
		webTextArea.setText("");
        getWebTextField().setText("");
        getWebTextField_1().setText("");
        getWebTextField_2().setText("");
        getWebTextField_3().setText("");
        getWebTextField_4().setText("");
        getWebComboBox().setSelectedIndex(0);
        parentPanel.getParentPanel().setEventsEnabled(true);
	}

	public WebComboBox getWebComboBox() {
		return webComboBox;
	}


	public void setWebComboBox(WebComboBox webComboBox) {
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
}
