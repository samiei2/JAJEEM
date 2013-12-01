package com.jajeem.util;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import com.jajeem.ui.textbox.JajeemTextField;
import com.alee.laf.combobox.WebComboBox;

public class testtt extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public testtt() {
		
		JajeemTextField jajeemTextField = new JajeemTextField();
		
		WebComboBox webComboBox = new WebComboBox();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(107, Short.MAX_VALUE)
					.addComponent(jajeemTextField, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addGap(97))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(40)
					.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(83)
					.addComponent(jajeemTextField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(77, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}

	public static void main(String[] args) {
		testtt t = new testtt();
		t.setVisible(true);
	}
}