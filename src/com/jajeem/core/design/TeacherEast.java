package com.jajeem.core.design;

import java.awt.GridLayout;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;

public class TeacherEast {
	public static WebPanel createPanel(WebPanel panel) {
		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);
		panel.add(new WebButton("Share"));
		panel.add(new WebButton("Audio"));
		panel.add(new WebButton("Intercom"));
		panel.add(new WebButton("Chat"));
		panel.add(new WebButton("Video"));
		panel.add(new WebButton("Groupwork"));
		panel.add(new WebButton("Blackout"));
		panel.add(new WebButton("Lock"));
		panel.add(new WebButton("Remote"));
		
		return panel;
	}
}
