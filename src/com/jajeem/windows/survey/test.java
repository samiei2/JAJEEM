package com.jajeem.windows.survey;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class test extends JPanel {
	private JTextField textFieldcard2;
	private JTextField textField_1card2;

	/**
	 * Create the panel.
	 */
	public test() {
		
		JPanel panelcard2 = new JPanel();
		
		JLabel labelcard2 = new JLabel("Directions");
		
		textFieldcard2 = new JTextField();
		textFieldcard2.setColumns(10);
		
		JButton btnShowResultscard2 = new JButton("Show Results");
		GroupLayout gl_panelcard2 = new GroupLayout(panelcard2);
		gl_panelcard2.setHorizontalGroup(
			gl_panelcard2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 883, Short.MAX_VALUE)
				.addGroup(gl_panelcard2.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelcard2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldcard2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
					.addComponent(btnShowResultscard2, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelcard2.setVerticalGroup(
			gl_panelcard2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 79, Short.MAX_VALUE)
				.addGroup(gl_panelcard2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelcard2.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelcard2)
						.addComponent(textFieldcard2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnShowResultscard2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelcard2.setLayout(gl_panelcard2);
		
		JPanel panel_1card2 = new JPanel();
		GroupLayout groupLayoutcard2 = new GroupLayout(this);
		groupLayoutcard2.setHorizontalGroup(
			groupLayoutcard2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutcard2.createSequentialGroup()
					.addGroup(groupLayoutcard2.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1card2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelcard2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE))
					.addGap(1))
		);
		groupLayoutcard2.setVerticalGroup(
			groupLayoutcard2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayoutcard2.createSequentialGroup()
					.addComponent(panelcard2, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1card2, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
		);
		
		JLabel lblQuestionTypescard2 = new JLabel("Question Types");
		
		JLabel lblQuestioncard2 = new JLabel("Question");
		
		JScrollPane scrollPanecard2 = new JScrollPane();
		
		textField_1card2 = new JTextField();
		textField_1card2.setColumns(10);
		
		JLabel lblResultscard2 = new JLabel("Results");
		
		JScrollPane scrollPane_1card2 = new JScrollPane();
		
		JLabel lblansweredcard2 = new JLabel("[Answered]");
		
		JLabel lblNewLabelcard2 = new JLabel("New label");
		GroupLayout gl_panel_1card2 = new GroupLayout(panel_1card2);
		gl_panel_1card2.setHorizontalGroup(
			gl_panel_1card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1card2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1card2.createSequentialGroup()
							.addGroup(gl_panel_1card2.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblQuestionTypescard2)
								.addGroup(gl_panel_1card2.createSequentialGroup()
									.addGroup(gl_panel_1card2.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblQuestioncard2)
										.addComponent(lblResultscard2))
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel_1card2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1card2.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(gl_panel_1card2.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel_1card2.createSequentialGroup()
											.addComponent(scrollPanecard2, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
											.addGap(495))
										.addGroup(gl_panel_1card2.createSequentialGroup()
											.addComponent(lblansweredcard2)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblNewLabelcard2)
											.addGap(82))))
								.addGroup(gl_panel_1card2.createSequentialGroup()
									.addGap(18)
									.addComponent(textField_1card2, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))))
						.addComponent(scrollPane_1card2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 863, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1card2.setVerticalGroup(
			gl_panel_1card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1card2.createSequentialGroup()
					.addGap(44)
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblQuestionTypescard2)
						.addComponent(textField_1card2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPanecard2, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuestioncard2))
					.addGap(8)
					.addGroup(gl_panel_1card2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResultscard2)
						.addComponent(lblansweredcard2)
						.addComponent(lblNewLabelcard2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1card2, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextArea textAreacard2 = new JTextArea();
		scrollPanecard2.setViewportView(textAreacard2);
		panel_1card2.setLayout(gl_panel_1card2);
		setLayout(groupLayoutcard2);

	}
}
