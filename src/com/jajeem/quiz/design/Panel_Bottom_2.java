package com.jajeem.quiz.design;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.combobox.WebComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.text.WebTextField;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.panel.WebPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import com.alee.laf.table.WebTable;
import javax.swing.table.DefaultTableModel;

public class Panel_Bottom_2 extends WebPanel {

	/**
	 * Create the panel.
	 */
	public Panel_Bottom_2() {
		
		WebLabel wblblQuestionNumber = new WebLabel();
		wblblQuestionNumber.setText("Question Number");
		
		WebComboBox webComboBox = new WebComboBox();
		
		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");
		
		WebTextField webTextField = new WebTextField();
		webTextField.setEnabled(false);
		webTextField.setEditable(false);
		
		WebLabel wblblQuestion = new WebLabel();
		wblblQuestion.setText("Question");
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebPanel webPanel_1 = new WebPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblQuestionNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE))
					.addGap(149)
					.addComponent(webPanel, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
					.addGap(73))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(webPanel_1, GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE)
					.addGap(2))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblQuestionNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webScrollPane, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
						.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 240, Short.MAX_VALUE)
					.addGap(1))
		);
		
		WebLabel wblblResults = new WebLabel();
		wblblResults.setText("Results");
		
		WebLabel wblbltotal = new WebLabel();
		wblbltotal.setText("[Total]");
		
		WebLabel webLabel = new WebLabel();
		webLabel.setText("?");
		
		WebLabel wblblAnswered = new WebLabel();
		wblblAnswered.setText("[Answered]");
		
		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("?");
		
		WebLabel wblblcorrect = new WebLabel();
		wblblcorrect.setText("[Correct]");
		
		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("?");
		
		WebLabel wblblCorrectRate = new WebLabel();
		wblblCorrectRate.setText("Correct Rate");
		
		WebLabel webLabel_3 = new WebLabel();
		webLabel_3.setText("?");
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(375)
							.addComponent(wblbltotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wblblAnswered, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wblblcorrect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(wblblCorrectRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
							.addGap(21)))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblResults, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
								.addComponent(wblbltotal, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE))
							.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
							.addComponent(wblblAnswered, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(webLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(wblblcorrect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblCorrectRate, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webScrollPane_1, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		WebTable webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Number", "Student Name", "Order", "Correct Answer", "Answer"
			}
		));
		webScrollPane_1.setViewportView(webTable);
		webPanel_1.setLayout(gl_webPanel_1);
		
		WebLabel wblblQuizInfo = new WebLabel();
		wblblQuizInfo.setText("Quiz Info.");
		
		WebLabel wblblDirection = new WebLabel();
		wblblDirection.setText("Direction ");
		
		WebTextField webTextField_1 = new WebTextField();
		webTextField_1.setEditable(false);
		webTextField_1.setEnabled(false);
		
		WebLabel wblblTime = new WebLabel();
		wblblTime.setText("Time Left");
		
		WebTextField webTextField_2 = new WebTextField();
		webTextField_2.setEditable(false);
		webTextField_2.setEnabled(false);
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(wblblQuizInfo, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(wblblTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(wblblDirection, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_1, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblQuizInfo, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblTime, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(83, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		
		WebTextArea webTextArea = new WebTextArea();
		webTextArea.setEnabled(false);
		webTextArea.setEditable(false);
		webScrollPane.setViewportView(webTextArea);
		setLayout(groupLayout);
		

	}
}
