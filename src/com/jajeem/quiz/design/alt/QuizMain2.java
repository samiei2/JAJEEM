package com.jajeem.quiz.design.alt;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class QuizMain2 extends CustomQuizFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuizMain2() {

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

		JLabel lblQuestion = new JLabel("Question : ");

		JLabel lblQuestionType = new JLabel("Question Type : ");

		JLabel lblQuestion_1 = new JLabel("Question : ");

		CustomQuizComboBox comboBox = new CustomQuizComboBox(
				"/icons/noa_en/quizcombobox.png");
		GroupLayout groupLayout_1 = new GroupLayout(getMainContentPane());
		groupLayout_1
				.setHorizontalGroup(groupLayout_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout_1
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout_1
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblQuestion)
														.addGroup(
																groupLayout_1
																		.createSequentialGroup()
																		.addComponent(
																				lblQuestionType)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboBox,
																				GroupLayout.PREFERRED_SIZE,
																				110,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																lblQuestion_1))
										.addContainerGap(368, Short.MAX_VALUE)));
		groupLayout_1
				.setVerticalGroup(groupLayout_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout_1
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblQuestion)
										.addGap(18)
										.addGroup(
												groupLayout_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblQuestionType)
														.addComponent(
																comboBox,
																GroupLayout.PREFERRED_SIZE,
																30,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18).addComponent(lblQuestion_1)
										.addContainerGap(292, Short.MAX_VALUE)));
		getMainContentPane().setLayout(groupLayout_1);
	}
}
