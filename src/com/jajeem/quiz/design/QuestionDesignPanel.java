package com.jajeem.quiz.design;

import javax.swing.JPanel;

import com.alee.laf.panel.WebPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.combobox.WebComboBox;
import javax.swing.DefaultComboBoxModel;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.radiobutton.WebRadioButton;
import com.alee.laf.text.WebTextField;
import com.alee.extended.image.WebImage;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.border.EtchedBorder;
import com.alee.laf.button.WebButton;

public class QuestionDesignPanel extends WebPanel {
	private WebTextField webTextField;
	private WebTextField webTextField_1;
	private WebTextField webTextField_2;
	private WebTextField webTextField_3;
	private WebTextField webTextField_4;
	private WebTextField webTextField_5;
	private WebTextField webTextField_6;
	private WebTextField webTextField_7;
	private WebTextField webTextField_8;
	private WebTextField webTextField_9;
	private WebTextField webTextField_11;
	private WebTextArea webTextArea;
	private WebRadioButton webRadioButton;
	private WebRadioButton webRadioButton_1;
	private WebRadioButton webRadioButton_2;
	private WebRadioButton webRadioButton_3;
	private WebRadioButton webRadioButton_4;
	private WebCheckBox webCheckBox;
	private WebCheckBox webCheckBox_1;
	private WebCheckBox webCheckBox_2;
	private WebCheckBox webCheckBox_3;
	private WebCheckBox webCheckBox_4;
	private WebTextField webTextField_10;
	private WebComboBox webComboBox;
	private Panel_Bottom_1 parentPanel;


	/**
	 * Create the panel.
	 */
	public QuestionDesignPanel(Panel_Bottom_1 panel) {
		this.parentPanel = panel;
		WebLabel wblblQuestion = new WebLabel();
		wblblQuestion.setText("Question ?");
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		
		WebLabel wblblQuestionType = new WebLabel();
		wblblQuestionType.setText("Question Type");
		
		
		
		final WebPanel webPanelOption = new WebPanel(new CardLayout());
		webPanelOption.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		WebPanel card1 = new WebPanel();
		WebPanel card2 = new WebPanel();
		WebPanel card3 = new WebPanel();
		
		webPanelOption.add(card1,"Single Choice");
		
		webTextField = new WebTextField();
		
		webTextField_1 = new WebTextField();
		
		webTextField_2 = new WebTextField();
		
		webTextField_3 = new WebTextField();
		
		webTextField_4 = new WebTextField();
		
		setWebRadioButton(new WebRadioButton());
		
		setWebRadioButton_1(new WebRadioButton());
		
		setWebRadioButton_2(new WebRadioButton());
		
		setWebRadioButton_3(new WebRadioButton());
		
		setWebRadioButton_4(new WebRadioButton());
		GroupLayout gl_card1 = new GroupLayout(card1);
		gl_card1.setHorizontalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card1.createParallelGroup(Alignment.TRAILING)
							.addComponent(getWebRadioButton(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(getWebRadioButton_1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(getWebRadioButton_2(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebRadioButton_3(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebRadioButton_4(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card1.createParallelGroup(Alignment.LEADING)
						.addComponent(webTextField, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
						.addComponent(webTextField_3, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
						.addComponent(webTextField_2, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
						.addComponent(webTextField_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
						.addComponent(webTextField_4, GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_card1.setVerticalGroup(
			gl_card1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card1.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebRadioButton(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card1.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebRadioButton_1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card1.createParallelGroup(Alignment.TRAILING)
						.addComponent(webTextField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebRadioButton_2(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card1.createParallelGroup(Alignment.TRAILING)
						.addComponent(webTextField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebRadioButton_3(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addGroup(gl_card1.createParallelGroup(Alignment.TRAILING)
						.addComponent(webTextField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebRadioButton_4(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		card1.setLayout(gl_card1);
		webPanelOption.add(card2,"Multiple Choice");
		
		webTextField_5 = new WebTextField();
		
		webTextField_6 = new WebTextField();
		
		webTextField_7 = new WebTextField();
		
		webTextField_8 = new WebTextField();
		
		webTextField_9 = new WebTextField();
		
		setWebCheckBox(new WebCheckBox());
		
		setWebCheckBox_1(new WebCheckBox());
		
		setWebCheckBox_2(new WebCheckBox());
		
		setWebCheckBox_3(new WebCheckBox());
		
		setWebCheckBox_4(new WebCheckBox());
		GroupLayout gl_card2 = new GroupLayout(card2);
		gl_card2.setHorizontalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_card2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
							.addComponent(getWebCheckBox_1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(getWebCheckBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(getWebCheckBox_2(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebCheckBox_3(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebCheckBox_4(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card2.createParallelGroup(Alignment.LEADING)
						.addComponent(webTextField_9, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addComponent(webTextField_8, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addComponent(webTextField_7, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addComponent(webTextField_6, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addComponent(webTextField_5, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_card2.setVerticalGroup(
			gl_card2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_card2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebCheckBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebCheckBox_1(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebCheckBox_2(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebCheckBox_3(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_card2.createParallelGroup(Alignment.TRAILING)
						.addComponent(getWebCheckBox_4(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webTextField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		card2.setLayout(gl_card2);
		webPanelOption.add(card3,"Essay");
		
		JPanel panelimage = new JPanel();
		panelimage.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		webTextField_10 = new WebTextField();
		
		WebButton wbtnInsert = new WebButton();
		wbtnInsert.setText("Insert");
		
		WebLabel wblblUrl = new WebLabel();
		wblblUrl.setText("Url");
		
		WebLabel wblblImage = new WebLabel();
		wblblImage.setText("Image");
		
		setWebComboBox(new WebComboBox());
		getWebComboBox().setModel(new DefaultComboBoxModel(new String[] {"Single Choice", "Multiple Choice", "Essay"}));
		getWebComboBox().addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent evt) {
				CardLayout cl = (CardLayout)(webPanelOption.getLayout());
		        cl.show(webPanelOption, (String)evt.getItem());
		        getWebRadioButton().setSelected(false);
		        getWebRadioButton_1().setSelected(false);
		        getWebRadioButton_2().setSelected(false);
		        getWebRadioButton_3().setSelected(false);
		        getWebRadioButton_4().setSelected(false);
		        getWebCheckBox().setSelected(false);
		        getWebCheckBox_1().setSelected(false);
		        getWebCheckBox_2().setSelected(false);
		        getWebCheckBox_3().setSelected(false);
		        getWebCheckBox_4().setSelected(false);
		        if(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow() != -1 && parentPanel.getParentPanel().isEventsEnabled()){
		        	parentPanel.getQuestionListPanel().getWebTable().setValueAt(getWebComboBox().getSelectedItem().toString(),parentPanel.getQuestionListPanel().getWebTable().getSelectedRow(), 1);
		        	parentPanel.getParentPanel().getCurrentQuiz().getQuestionList().get(parentPanel.getQuestionListPanel().getWebTable().getSelectedRow()).setType((byte) getWebComboBox().getSelectedIndex());
		        }
			}
		});
		
		WebLabel wblblPoint = new WebLabel();
		wblblPoint.setText("Point");
		
		setWebTextField_11(new WebTextField());
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(getWebComboBox(), GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
									.addGap(65)
									.addComponent(wblblPoint, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(getWebTextField_11(), GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))
							.addGap(21))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(0)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(82)
											.addComponent(wblblImage, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(wbtnInsert, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(wblblUrl, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(webTextField_10, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE))))
									.addGap(6)
									.addComponent(panelimage, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
									.addGap(7))
								.addComponent(webPanelOption, GroupLayout.PREFERRED_SIZE, 463, Short.MAX_VALUE))
							.addGap(21))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(wblblQuestion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(wblblQuestionType, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebComboBox(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(wblblPoint, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(getWebTextField_11(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanelOption, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(wblblUrl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(webTextField_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
							.addComponent(wblblImage, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(wbtnInsert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelimage, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		WebImage webImage = new WebImage();
		GroupLayout gl_panelimage = new GroupLayout(panelimage);
		gl_panelimage.setHorizontalGroup(
			gl_panelimage.createParallelGroup(Alignment.LEADING)
				.addComponent(webImage, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
		);
		gl_panelimage.setVerticalGroup(
			gl_panelimage.createParallelGroup(Alignment.LEADING)
				.addComponent(webImage, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
		);
		panelimage.setLayout(gl_panelimage);
		
		setWebTextArea(new WebTextArea());
		webScrollPane.setViewportView(getWebTextArea());
		setLayout(groupLayout);

	}


	public WebTextArea getWebTextArea() {
		return webTextArea;
	}


	public void setWebTextArea(WebTextArea webTextArea) {
		this.webTextArea = webTextArea;
	}


	public void clear() {
		webTextArea.setText("");
        webTextField.setText("");
        webTextField_1.setText("");
        webTextField_2.setText("");
        webTextField_3.setText("");
        webTextField_4.setText("");
        webTextField_5.setText("");
        webTextField_6.setText("");
        webTextField_7.setText("");
        webTextField_8.setText("");
        webTextField_9.setText("");
        getWebComboBox().setSelectedIndex(0);
        getWebRadioButton().setSelected(false);
        getWebRadioButton_1().setSelected(false);
        getWebRadioButton_2().setSelected(false);
        getWebRadioButton_3().setSelected(false);
        getWebRadioButton_4().setSelected(false);
        getWebCheckBox().setSelected(false);
        getWebCheckBox_1().setSelected(false);
        getWebCheckBox_2().setSelected(false);
        getWebCheckBox_3().setSelected(false);
        getWebCheckBox_4().setSelected(false);
	}


	public WebTextField getWebTextField_11() {
		return webTextField_11;
	}


	public void setWebTextField_11(WebTextField webTextField_11) {
		this.webTextField_11 = webTextField_11;
	}


	public WebComboBox getWebComboBox() {
		return webComboBox;
	}


	public void setWebComboBox(WebComboBox webComboBox) {
		this.webComboBox = webComboBox;
	}


	public WebRadioButton getWebRadioButton() {
		return webRadioButton;
	}


	public void setWebRadioButton(WebRadioButton webRadioButton) {
		this.webRadioButton = webRadioButton;
	}


	public WebRadioButton getWebRadioButton_1() {
		return webRadioButton_1;
	}


	public void setWebRadioButton_1(WebRadioButton webRadioButton_1) {
		this.webRadioButton_1 = webRadioButton_1;
	}


	public WebRadioButton getWebRadioButton_2() {
		return webRadioButton_2;
	}


	public void setWebRadioButton_2(WebRadioButton webRadioButton_2) {
		this.webRadioButton_2 = webRadioButton_2;
	}


	public WebRadioButton getWebRadioButton_3() {
		return webRadioButton_3;
	}


	public void setWebRadioButton_3(WebRadioButton webRadioButton_3) {
		this.webRadioButton_3 = webRadioButton_3;
	}


	public WebRadioButton getWebRadioButton_4() {
		return webRadioButton_4;
	}


	public void setWebRadioButton_4(WebRadioButton webRadioButton_4) {
		this.webRadioButton_4 = webRadioButton_4;
	}


	public WebCheckBox getWebCheckBox() {
		return webCheckBox;
	}


	public void setWebCheckBox(WebCheckBox webCheckBox) {
		this.webCheckBox = webCheckBox;
	}


	public WebCheckBox getWebCheckBox_1() {
		return webCheckBox_1;
	}


	public void setWebCheckBox_1(WebCheckBox webCheckBox_1) {
		this.webCheckBox_1 = webCheckBox_1;
	}


	public WebCheckBox getWebCheckBox_2() {
		return webCheckBox_2;
	}


	public void setWebCheckBox_2(WebCheckBox webCheckBox_2) {
		this.webCheckBox_2 = webCheckBox_2;
	}


	public WebCheckBox getWebCheckBox_3() {
		return webCheckBox_3;
	}


	public void setWebCheckBox_3(WebCheckBox webCheckBox_3) {
		this.webCheckBox_3 = webCheckBox_3;
	}


	public WebCheckBox getWebCheckBox_4() {
		return webCheckBox_4;
	}


	public void setWebCheckBox_4(WebCheckBox webCheckBox_4) {
		this.webCheckBox_4 = webCheckBox_4;
	}
}
