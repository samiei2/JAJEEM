package com.jajeem.survey.design;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alee.extended.image.WebImage;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class QuestionDesignPanel extends WebPanel {
	private WebTextField webTextField;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;
	private WebTextField webTextField_3;
	private WebTextField webTextField_4;
	private WebTextArea webTextArea;
	private WebComboBox webComboBox;
	private Panel_Bottom_1 parentPanel;
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
		        if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
		        	parentPanel.getParentPanel().getTablemodel().setValueAt(webComboBox.getSelectedItem().toString(),parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 1);
		        	parentPanel.getParentPanel().getCurrentSurvey().getQuestionList().get(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow()).setType((byte) webComboBox.getSelectedIndex());
		        }
			}
		});
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
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
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)))
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
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addComponent(wblblQuestion_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(97, Short.MAX_VALUE))
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
		
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(webTextField_4, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addComponent(webTextField_3, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addComponent(webTextField_1, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addComponent(webTextField, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webTextField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webTextField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
}
