package com.jajeem.windows;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class wind2 extends JPanel {
	private JTextField textField_4;
	private JTable table;
	private DefaultTableModel resulttablemodel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public wind2() {
		
		JSeparator separator = new JSeparator();
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Directions");
		
		JLabel label_1 = new JLabel("Points");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel label_2 = new JLabel("( 0)");
		
		JCheckBox checkBox = new JCheckBox("Auto");
		checkBox.setSelected(true);
		
		JLabel label_3 = new JLabel("Time Limit");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel label_5 = new JLabel("Show Results");
		
		JComboBox comboBox_1 = new JComboBox();
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JButton button = new JButton("New");
		
		JButton button_1 = new JButton("Open");
		
		JLabel label_6 = new JLabel("Quiz");
		
		JButton button_2 = new JButton("Save");
		
		JButton button_3 = new JButton("Save As");
		
		JComboBox comboBox_2 = new JComboBox();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 893, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(label)
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
							.addComponent(checkBox)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(56)
							.addComponent(label_5)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_1)
							.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
							.addComponent(label_6)))
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_2, 0, 205, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 79, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button)
						.addComponent(button_1)
						.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_6))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_5)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_3)
						.addComponent(label_1)
						.addComponent(button_2)
						.addComponent(checkBox)
						.addComponent(label_3)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 777, Short.MAX_VALUE))
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblQuestionNumber = new JLabel("Question Number");
		
		JComboBox comboBox = new JComboBox();
		
		JLabel lblQuestionType = new JLabel("Question Type");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		JLabel lblQuestion = new JLabel("Question");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblResults = new JLabel("Results");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lbltotal = new JLabel("[Total]");
		
		JLabel lblanswered = new JLabel("[Answered]");
		
		JLabel lblcorrect = new JLabel("[Correct]");
		
		JLabel lblcorrectRate = new JLabel("[Correct Rate]");
		
		JLabel label_4 = new JLabel("New label");
		
		JLabel lblNewLabel = new JLabel("New label");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblResults)
								.addComponent(lblQuestion)
								.addComponent(lblQuestionType)
								.addComponent(lblQuestionNumber))
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
											.addComponent(textField_4)
											.addComponent(comboBox, 0, 110, Short.MAX_VALUE))
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(242)
									.addComponent(lbltotal)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label_4)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblanswered)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblcorrect)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_1)
									.addGap(6)
									.addComponent(lblcorrectRate)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_2)))))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblQuestionNumber)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblQuestionType)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblQuestion)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblResults)
						.addComponent(lbltotal)
						.addComponent(label_4)
						.addComponent(lblanswered)
						.addComponent(lblNewLabel)
						.addComponent(lblcorrect)
						.addComponent(lblNewLabel_1)
						.addComponent(lblcorrectRate)
						.addComponent(lblNewLabel_2))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		resulttablemodel = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Number", "Number", "Student name", "Order","Correct Answer","Answer"
				});
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Number", "Number", "Student name", "Order", "Correct Answer", "Answer"
			}
		));
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		scrollPane_1.setViewportView(table);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		panel_1.setLayout(gl_panel_1);
		setLayout(groupLayout);

	}
}
