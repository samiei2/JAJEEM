package com.jajeem.quiz.design.alt;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CustomQuiz_FirstPage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CustomQuiz_FirstPage() {
		setOpaque(false);

		JPanel panelLeft = new JPanel();

		JPanel panelRight = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(panelLeft, GroupLayout.PREFERRED_SIZE,
								201, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelRight, GroupLayout.DEFAULT_SIZE,
								243, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addComponent(panelLeft, GroupLayout.DEFAULT_SIZE, 300,
						Short.MAX_VALUE)
				.addComponent(panelRight, GroupLayout.DEFAULT_SIZE, 300,
						Short.MAX_VALUE));
		setLayout(groupLayout);

	}
}
