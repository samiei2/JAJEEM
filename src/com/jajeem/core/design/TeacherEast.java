package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.model.BlackoutCommand;
import com.jajeem.command.model.InternetCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.quiz.design.Main;
import com.jajeem.util.Config;

public class TeacherEast {
	
	public static WebPanel createPanel(WebPanel panel2) {
		
		new Config();
		
		WebPanel panel = new WebPanel();
		
		GridLayout grid = new GridLayout(0, 1);
		panel.setLayout(grid);

		ImageIcon imgMonitor = new ImageIcon("icons/applications/monitor.png");
		WebButton monitorButton = new WebButton("Monitor", new ImageIcon(imgMonitor.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		panel.add(monitorButton);
		
		ImageIcon imgIntercom = new ImageIcon("icons/applications/intercom.png");
		WebButton intercomButton = new WebButton("Intercom", new ImageIcon(imgIntercom.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		panel.add(intercomButton);
		
		ImageIcon imgStopInternet = new ImageIcon("icons/applications/stopInternet.png");
		WebButton stopInternetButton = new WebButton("Stop Internet", new ImageIcon(imgStopInternet.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		panel.add(stopInternetButton);
		
		ImageIcon imgBlackout = new ImageIcon("icons/applications/blackout_text.png");
		WebButton blackoutButton = new WebButton("", imgBlackout);
		panel.add(blackoutButton);
		
		ImageIcon imgQuiz = new ImageIcon("icons/applications/quiz.png");
		WebButton quizButton = new WebButton("Quiz", new ImageIcon(imgQuiz.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		panel.add(quizButton);
		
		quizButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main quiz = new Main();
				quiz.setVisible(true);
			}
		});
		
		ImageIcon imgSurvey = new ImageIcon("icons/applications/survey.png");
		WebButton surveyButton = new WebButton("Survey", new ImageIcon(imgSurvey.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH)));
		panel.add(surveyButton);
		
		blackoutButton.addActionListener(new ActionListener() {

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
		
		stopInternetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) throws NumberFormatException {
				if (TeacherCenter.desktopPane.getSelectedFrame() != null) {
					String selectedStudent = "";
					selectedStudent = (String) TeacherCenter.desktopPane.getSelectedFrame().getClientProperty("ip");
					try {
						ServerService ss = new ServerService();
						InternetCommand ic = new InternetCommand(selectedStudent,Integer.parseInt(Config.getParam("port")));
						ss.send(ic);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel, BorderLayout.NORTH);

		return panel;
	}
}
