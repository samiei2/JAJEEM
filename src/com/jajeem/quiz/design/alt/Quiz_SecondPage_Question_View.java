package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;

@SuppressWarnings("serial")
public class Quiz_SecondPage_Question_View extends Quiz_AbstractViews {

	private WebTable webTable;
	
	/**
	 * Create the panel.
	 */
	public Quiz_SecondPage_Question_View() {
		
		WebPanel webPanel = new WebPanel();
		
		WebPanel webPanel_4 = new WebPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(webPanel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(webPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel_4, GroupLayout.PREFERRED_SIZE, 262, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		webPanel_4.add(webScrollPane_1, BorderLayout.CENTER);
		
		webTable = new WebTable();
		webTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Id", "Name", "Correct Answer", "Answer"
			}
		));
		webTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		webTable.getColumnModel().getColumn(0).setMaxWidth(35);
		webTable.getColumnModel().getColumn(1).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(1).setMaxWidth(217);
		webTable.getColumnModel().getColumn(2).setPreferredWidth(105);
		webTable.getColumnModel().getColumn(2).setMaxWidth(217);
		webTable.getColumnModel().getColumn(3).setPreferredWidth(85);
		webTable.getColumnModel().getColumn(3).setMaxWidth(217);
		webTable.getColumnModel().getColumn(4).setPreferredWidth(55);
		webTable.getColumnModel().getColumn(4).setMaxWidth(217);
		webScrollPane_1.setViewportView(webTable);
		
		WebPanel webPanel_1 = new WebPanel();
		
		WebPanel webPanel_2 = new WebPanel();
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(webPanel_2, GroupLayout.PREFERRED_SIZE, 402, Short.MAX_VALUE))
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(webPanel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addComponent(webPanel_2, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
					.addGap(11))
		);
		
		WebPanel webPanel_3 = new WebPanel();
		webPanel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		WebLabel webLabel_3 = new WebLabel();
		webLabel_3.setText("Quiz Info.");
		
		WebLabel webLabel_4 = new WebLabel();
		webLabel_4.setText("Time Left");
		
		WebLabel webLabel_5 = new WebLabel();
		webLabel_5.setText("Direction ");
		
		WebTextField webTextField_1 = new WebTextField();
		webTextField_1.setEnabled(false);
		webTextField_1.setEditable(false);
		
		WebTextField webTextField_2 = new WebTextField();
		webTextField_2.setEnabled(false);
		webTextField_2.setEditable(false);
		GroupLayout gl_webPanel_3 = new GroupLayout(webPanel_3);
		gl_webPanel_3.setHorizontalGroup(
			gl_webPanel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 483, Short.MAX_VALUE)
				.addGroup(gl_webPanel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_webPanel_3.createSequentialGroup()
							.addGroup(gl_webPanel_3.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(webLabel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(webLabel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_webPanel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_webPanel_3.setVerticalGroup(
			gl_webPanel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 115, Short.MAX_VALUE)
				.addGroup(gl_webPanel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(webLabel_3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(webLabel_5, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_webPanel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(webLabel_4, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		webPanel_3.setLayout(gl_webPanel_3);
		GroupLayout gl_webPanel_2 = new GroupLayout(webPanel_2);
		gl_webPanel_2.setHorizontalGroup(
			gl_webPanel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_2.createSequentialGroup()
					.addComponent(webPanel_3, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(99, Short.MAX_VALUE))
		);
		gl_webPanel_2.setVerticalGroup(
			gl_webPanel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_webPanel_2.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(webPanel_3, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		webPanel_2.setLayout(gl_webPanel_2);
		
		WebLabel webLabel = new WebLabel();
		webLabel.setText("Question");
		
		WebLabel webLabel_1 = new WebLabel();
		webLabel_1.setText("Question Type");
		
		WebLabel webLabel_2 = new WebLabel();
		webLabel_2.setText("Question");
		
		WebComboBox webComboBox = new WebComboBox();
		
		WebTextField webTextField = new WebTextField();
		webTextField.setEnabled(false);
		webTextField.setEditable(false);
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addGap(31)
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
								.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(webLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addGap(5)
							.addComponent(webLabel_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addGap(8)
							.addComponent(webLabel_2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		WebTextArea webTextArea = new WebTextArea();
		webTextArea.setEditable(false);
		webTextArea.setEnabled(false);
		webScrollPane.setViewportView(webTextArea);
		webPanel_1.setLayout(gl_webPanel_1);
		webPanel.setLayout(gl_webPanel);
		setLayout(groupLayout);
		
	}
}


