package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.label.WebLabel;

public class QuizMain2 extends BaseQuizFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuizMain2() {

		mainFrame = this;

		CustomQuizButton webButtonAdd = new CustomQuizButton(
				"/icons/noa_en/quizadd.png");

		CustomQuizButton webButtonOpen = new CustomQuizButton(
				"/icons/noa_en/quizopen.png");

		CustomQuizButton webButtonSave = new CustomQuizButton(
				"/icons/noa_en/quizsave.png");

		CustomQuizButton webButtonStart = new CustomQuizButton(
				"/icons/noa_en/quizstart.png");

		CustomQuizButton webButtonContent = new CustomQuizButton(
				"/icons/noa_en/quizcontent.png");
		GroupLayout groupLayout = new GroupLayout(getTopPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webButtonAdd, GroupLayout.PREFERRED_SIZE,
								33, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webButtonOpen,
								GroupLayout.PREFERRED_SIZE, 33,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webButtonSave,
								GroupLayout.PREFERRED_SIZE, 33,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 227,
								Short.MAX_VALUE)
						.addComponent(webButtonContent,
								GroupLayout.PREFERRED_SIZE, 33,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webButtonStart,
								GroupLayout.PREFERRED_SIZE, 33,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webButtonContent,
												GroupLayout.PREFERRED_SIZE, 31,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webButtonStart,
												GroupLayout.PREFERRED_SIZE, 31,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webButtonSave,
												GroupLayout.PREFERRED_SIZE, 31,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webButtonOpen,
												GroupLayout.PREFERRED_SIZE, 31,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(webButtonAdd,
												GroupLayout.PREFERRED_SIZE, 31,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		getTopPane().setLayout(groupLayout);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		getMainContentPane().add(panel, BorderLayout.CENTER);
		
		WebLabel wblblQuestion = new WebLabel();
		wblblQuestion.setText("Question : ");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(509, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(356, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);

	}

	public static void main(String[] args) {
		QuizMain2 main = new QuizMain2();
		main.pack();
		main.setVisible(true);
	}
}
