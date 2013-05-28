package com.jajeem.whiteboard.client.Client.design;


import com.alee.laf.panel.WebPanel;
import com.alee.laf.button.WebButton;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.alee.laf.combobox.WebComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.alee.laf.button.WebToggleButton;
import javax.swing.ImageIcon;

public class FontPanel2 extends WebPanel {

	/**
	 * Create the panel.
	 */
	public FontPanel2() {
		setPreferredSize(new Dimension(300,36));
		WebComboBox webComboBox = new WebComboBox();
		
		WebComboBox webComboBox_1 = new WebComboBox();
		
		WebToggleButton webToggleButton = new WebToggleButton();
		webToggleButton.setIcon(new ImageIcon(FontPanel2.class.getResource("/com/jajeem/whiteboard/client/Client/Image/Bold.gif")));
		webToggleButton.setPreferredSize(new Dimension(25,25));
		
		WebToggleButton webToggleButton_1 = new WebToggleButton();
		webToggleButton_1.setIcon(new ImageIcon(FontPanel2.class.getResource("/com/jajeem/whiteboard/client/Client/Image/Italic.gif")));
		webToggleButton_1.setPreferredSize(new Dimension(25, 25));
		
		WebToggleButton webToggleButton_2 = new WebToggleButton();
		webToggleButton_2.setIcon(new ImageIcon(FontPanel2.class.getResource("/com/jajeem/whiteboard/client/Client/Image/Baseline.gif")));
		webToggleButton_2.setPreferredSize(new Dimension(25, 25));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webComboBox_1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webToggleButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webToggleButton_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webToggleButton_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(webComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(webComboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(webToggleButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(webToggleButton_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(webToggleButton_2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
	}
}
