package com.jajeem.survey.design;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;

import com.alee.laf.table.WebTable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.alee.laf.scroll.WebScrollPane;
import com.jajeem.quiz.model.Question;

import java.awt.Component;

public class QuestionListPanel extends WebPanel {
	private WebTable webTable;
	private Panel_Bottom_1 parentPanel;

	/**
	 * Create the panel.
	 * @param panel_Bottom_1 
	 */
	@SuppressWarnings("serial")
	public QuestionListPanel(Panel_Bottom_1 panel_Bottom_1) {
		this.parentPanel = panel_Bottom_1;
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(webScrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
		);
		
		setWebTable(new WebTable());
		getWebTable().setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Type", "Content"
			}
		));
		webScrollPane.setViewportView(getWebTable());
		setLayout(groupLayout);
		
	
		webTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				////////////////////////////////////////////////////////////////
				///////////////Load Questions//////////////////////////////////
				if(webTable.getSelectedRow() == -1){
                	parentPanel.getParentPanel().setEventsEnabled(false);
                	parentPanel.getParentPanel().setCurrentQuestion(null);
                	parentPanel.getQuestionDesignPanel().clear();
                	parentPanel.getQuestionDesignPanel().getWblblQuestion().setText("Question ?");
                	
                	parentPanel.getParentPanel().setEventsEnabled(true);
                    return;
                }
				//com.jajeem.quiz.model.Question q = parentPanel.getParentPanel().getCurrentQuestion();
				parentPanel.getQuestionDesignPanel().getWblblQuestion().setText("Question "+(webTable.getSelectedRow()+1));
				parentPanel.getParentPanel().setCurrentQuestion(parentPanel.getParentPanel().getCurrentSurvey().getQuestionList().get(webTable.getSelectedRow()));
				parentPanel.getParentPanel().setEventsEnabled(false);
				parentPanel.getQuestionDesignPanel().getWebTextArea().setText(parentPanel.getParentPanel().getCurrentQuestion().getTitle());
				parentPanel.getQuestionDesignPanel().getWebTextField().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer1());
				parentPanel.getQuestionDesignPanel().getWebTextField_1().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer2());
				parentPanel.getQuestionDesignPanel().getWebTextField_2().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer3());
				parentPanel.getQuestionDesignPanel().getWebTextField_3().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer4());
           		parentPanel.getQuestionDesignPanel().getWebTextField_4().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer5());
				//parentPanel.getQuestionDesignPanel().getWebTextField_5().setText(parentPanel.getParentPanel().getCurrentQuestion().getUrl());
           		if(parentPanel.getParentPanel().getCurrentQuestion().getType() == 0){
           			parentPanel.getQuestionDesignPanel().getWebComboBox().setSelectedIndex(0);
           		}
           		else if(parentPanel.getParentPanel().getCurrentQuestion().getType() == 1){
           			parentPanel.getQuestionDesignPanel().getWebComboBox().setSelectedIndex(1);
           		}
           		else if(parentPanel.getParentPanel().getCurrentQuestion().getType() == 2){
           			parentPanel.getQuestionDesignPanel().getWebComboBox().setSelectedIndex(2);
           		}
           		parentPanel.getParentPanel().setEventsEnabled(true);
			}
		});

	}

	public WebTable getWebTable() {
		return webTable;
	}

	public void setWebTable(WebTable webTable) {
		this.webTable = webTable;
	}

	public void clear() {
		DefaultTableModel model = (DefaultTableModel)webTable.getModel();
		model.getDataVector().clear();
		model.fireTableDataChanged();
	}
}
