package com.jajeem.quiz.design.alt;


import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;

public class Quiz_FirstPage extends Quiz_AbstractViews {
	private Quiz_QuestionListPanel webQuestionListPanel;
	private Quiz_QuestionDesignPanel webQuestionDesignPanel;
	private WebTextField webTextField;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;

	/**
	 * Create the panel.
	 */
	public Quiz_FirstPage() {
		
		webQuestionDesignPanel = new Quiz_QuestionDesignPanel();
		webQuestionDesignPanel.setBorder(UIManager.getBorder("TitledBorder.border"));
		
		webQuestionListPanel = new Quiz_QuestionListPanel();
		webQuestionListPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		WebPanel webPanel = new WebPanel();
		webPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webQuestionDesignPanel, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webQuestionListPanel, GroupLayout.PREFERRED_SIZE, 358, Short.MAX_VALUE)
						.addComponent(webPanel, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(webPanel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webQuestionListPanel, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
						.addComponent(webQuestionDesignPanel, GroupLayout.PREFERRED_SIZE, 583, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		WebLabel wblblDirection = new WebLabel();
		wblblDirection.setText("Direction : ");
		
		webTextField = new WebTextField();
		
		WebLabel wblblPoints = new WebLabel();
		wblblPoints.setText("Points : ");
		
		webTextField_1 = new WebTextField();
		
		WebLabel wblblT = new WebLabel();
		wblblT.setText("Time : ");
		
		webTextField_2 = new WebTextField();
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(wblblT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel.createSequentialGroup()
							.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(webTextField, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblDirection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_webPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblPoints, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(57, Short.MAX_VALUE))
		);
		webPanel.setLayout(gl_webPanel);
		setLayout(groupLayout);

	}

	public Quiz_QuestionListPanel getWebQuestionListPanel() {
		return webQuestionListPanel;
	}

	public void setWebQuestionListPanel(Quiz_QuestionListPanel webQuestionListPanel) {
		this.webQuestionListPanel = webQuestionListPanel;
	}

	public Quiz_QuestionDesignPanel getWebQuestionDesignPanel() {
		return webQuestionDesignPanel;
	}

	public void setWebQuestionDesignPanel(Quiz_QuestionDesignPanel webQuestionDesignPanel) {
		this.webQuestionDesignPanel = webQuestionDesignPanel;
	}
}
