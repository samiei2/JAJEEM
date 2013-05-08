package com.jajeem.core.design;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.model.BlackoutCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.util.Config;

public class TeacherEast {
	
	public static WebPanel createPanel(WebPanel panel) {
		
		new Config();
		
		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);
		panel.add(new WebButton("Share"));
		panel.add(new WebButton("Audio"));
		panel.add(new WebButton("Intercom"));
		panel.add(new WebButton("Chat"));
		panel.add(new WebButton("Video"));
		panel.add(new WebButton("Groupwork"));
		panel.add(new WebButton("Lock"));
		WebButton lockButton = new WebButton("Blackout");
		lockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) throws NumberFormatException {
				if (TeacherCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) TeacherCenter.desktopPane.getSelectedFrame().getClientProperty("ip");
					try {
						ServerService ss = new ServerService();
						BlackoutCommand bc = new BlackoutCommand(selectedStudent,Integer.parseInt(Config.getParam("port")));
						ss.send(bc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		panel.add(lockButton);
		panel.add(new WebButton("Remote"));

		return panel;
	}
}
