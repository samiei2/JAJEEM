package com.jajeem.quiz.design;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.table.WebTable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;

public class QuestionListPanel extends WebPanel {
	private WebTable webTable;
	private Panel_Bottom_1 parentPanel;

	/**
	 * Create the panel.
	 * @param panel_Bottom_1 
	 */
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
				"#", "Type", "Point", "Content"
			}
		));
		webScrollPane.setViewportView(getWebTable());
		setLayout(groupLayout);
		
	
		webTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(webTable.getSelectedRow() == -1){
                	parentPanel.getParentPanel().setEventsEnabled(false);
                	parentPanel.getParentPanel().setCurrentQuestion(null);
                	parentPanel.getQuestionDesignPanel().clear();
                	
                	parentPanel.getParentPanel().setEventsEnabled(true);
                    return;
                }
				parentPanel.getParentPanel().setCurrentQuestion(parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().get(webTable.getSelectedRow()));
				parentPanel.getParentPanel().setEventsEnabled(false);
				parentPanel.getQuestionDesignPanel().getWebTextArea().setText(parentPanel.getParentPanel().getCurrentQuestion().getTitle());
				parentPanel.getQuestionDesignPanel().getWebTextField().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer1());
				parentPanel.getQuestionDesignPanel().getWebTextField_1().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer2());
				parentPanel.getQuestionDesignPanel().getWebTextField_2().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer3());
				parentPanel.getQuestionDesignPanel().getWebTextField_3().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer4());
           		parentPanel.getQuestionDesignPanel().getWebTextField_4().setText(parentPanel.getParentPanel().getCurrentQuestion().getAnswer5());
				parentPanel.getQuestionDesignPanel().getWebTextField_5().setText(parentPanel.getParentPanel().getCurrentQuestion().getUrl());
           		parentPanel.getQuestionDesignPanel().getWebTextField_6().setText(String.valueOf(parentPanel.getParentPanel().getCurrentQuestion().getPoint()));
           		if(parentPanel.getParentPanel().getCurrentQuestion().getType() == 0){
           			parentPanel.getQuestionDesignPanel().getWebComboBox().setSelectedIndex(0);
           			boolean[] answers = parentPanel.getParentPanel().getCurrentQuestion().getCorrectAnswer();
           			if(answers != null){
           				parentPanel.getQuestionDesignPanel().getWebRadioButton().setSelected(answers[0]);
           				parentPanel.getQuestionDesignPanel().getWebRadioButton_1().setSelected(answers[1]);
           				parentPanel.getQuestionDesignPanel().getWebRadioButton_2().setSelected(answers[2]);
           				parentPanel.getQuestionDesignPanel().getWebRadioButton_3().setSelected(answers[3]);
           				parentPanel.getQuestionDesignPanel().getWebRadioButton_4().setSelected(answers[4]);
           			}
           		}
           		else if(parentPanel.getParentPanel().getCurrentQuestion().getType() == 1){
           			parentPanel.getQuestionDesignPanel().getWebComboBox().setSelectedIndex(1);
           			boolean[] answers = parentPanel.getParentPanel().getCurrentQuestion().getCorrectAnswer();
           			if(answers != null){
           				parentPanel.getQuestionDesignPanel().getWebCheckBox().setSelected(answers[0]);
           				parentPanel.getQuestionDesignPanel().getWebCheckBox_1().setSelected(answers[1]);
           				parentPanel.getQuestionDesignPanel().getWebCheckBox_2().setSelected(answers[2]);
           				parentPanel.getQuestionDesignPanel().getWebCheckBox_3().setSelected(answers[3]);
           				parentPanel.getQuestionDesignPanel().getWebCheckBox_4().setSelected(answers[4]);
           			}
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
}
