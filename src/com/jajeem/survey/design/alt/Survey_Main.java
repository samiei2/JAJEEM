package com.jajeem.survey.design.alt;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.room.model.Session;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.service.ResultService;
import com.jajeem.survey.service.RunService;
import com.jajeem.survey.service.SurveyService;
import com.jajeem.util.Config;

public class Survey_Main extends WebFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private WebButton wbtnNew;
	private WebButton wbtnContent;
	private WebButton wbtnStart;
	private WebButton wbtnSaveResults;
	private WebButton wbtnSave;
	private WebButton wbtnOpen;

	private Run currentRun;
	private Question currentQuestion;
	private boolean eventsEnabled = true;
	private Survey_FirstPage firstPage;
	private Survey_SecondPage secondPage;
	private WebPanel webPanel;
	private Survey_Main mainFrame;
	private List<String> studentIps;
	private int gIndex;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Survey_Main frame = new Survey_Main(-1,null);
				} catch (Exception e) {
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param list 
	 * @param groupIndex 
	 */
	public Survey_Main(int groupIndex, List<String> list) {
		studentIps = list;
		gIndex = groupIndex;
		currentRun = new Run();
		mainFrame = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Survey_Main.class.getResource("/icons/noa_en/survey.png")));
		setTitle("Survey");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 987, 683);
		contentPane = new JPanel();
		setContentPane(contentPane);

		WebPanel webPanel_1 = new WebPanel();
		webPanel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		webPanel_1.setBackground(SystemColor.control);

		WebPanel webPanel_2 = new WebPanel();
		webPanel_2.setBorder(new TitledBorder(null, "", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		webPanel_2.setBackground(SystemColor.control);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(webPanel_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 855, Short.MAX_VALUE)
						.addComponent(webPanel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webPanel_2, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
					.addContainerGap())
		);

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_2 = new GroupLayout(webPanel_2);
		gl_webPanel_2.setHorizontalGroup(gl_webPanel_2.createParallelGroup(
				Alignment.LEADING).addComponent(webScrollPane,
				GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE));
		gl_webPanel_2.setVerticalGroup(gl_webPanel_2.createParallelGroup(
				Alignment.LEADING).addComponent(webScrollPane,
				GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE));

		webPanel = new WebPanel();
		webScrollPane.setViewportView(webPanel);
		webPanel.setLayout(new CardLayout(0, 0));
		webPanel_2.setLayout(gl_webPanel_2);

		firstPage = new Survey_FirstPage(this);
		firstPage.setName("firstPage");
		secondPage = new Survey_SecondPage(this);
		secondPage.setName("secondPage");

		webPanel.add(firstPage, "firstPage");
		webPanel.add(secondPage, "secondPage");

		wbtnNew = new WebButton();
		wbtnNew.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnNew.setVerticalAlignment(SwingConstants.TOP);
		wbtnNew.setText("New");
		wbtnNew.setIcon(new ImageIcon(Survey_Main.class
				.getResource("/icons/noa_en/Addx16.png")));

		wbtnOpen = new WebButton();
		wbtnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnOpen.setIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/folder_green_open-x16.png")));
		wbtnOpen.setText("Open");

		wbtnSave = new WebButton();
		wbtnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnSave.setIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/documentx16.png")));
		wbtnSave.setText("Save");

		wbtnSaveResults = new WebButton();
		wbtnSaveResults.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSaveResults.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnSaveResults.setIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/documentx16.png")));
		wbtnSaveResults.setText("Save Results");
		wbtnSaveResults.setVisible(false);

		wbtnStart = new WebButton();
		wbtnStart.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnStart.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnStart.setIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/startx16.png")));
		wbtnStart.setText("Start");

		wbtnContent = new WebButton();
		wbtnContent.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnContent.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnContent.setIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/contentx16.png")));
		wbtnContent.setText("Content");
		wbtnContent.setEnabled(false);
		
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSaveResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 461, Short.MAX_VALUE)
					.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
		);
		gl_webPanel_1.setVerticalGroup(
			gl_webPanel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_webPanel_1.createSequentialGroup()
					.addGroup(gl_webPanel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_webPanel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnSaveResults, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(115, Short.MAX_VALUE))
		);
		webPanel_1.setLayout(gl_webPanel_1);
		contentPane.setLayout(gl_contentPane);
	
		initEvents();
		initSurveyEventListener();
		if(!ValidateSession())
			return;
		setVisible(true);
		
	}

	private void initSurveyEventListener() {
		if(gIndex!=-1){
			if(studentIps != null){
				if(studentIps.size()!=0){
					new Config();
					ClientService clientService2 = null;
					try {
						clientService2 = new ClientService(
								Config.getParam("broadcastingIp"), Integer.parseInt(Config
										.getParam("surveyport"))+gIndex+1);
					} catch (NumberFormatException e2) {
						JajeemExcetionHandler.logError(e2);
						e2.printStackTrace();
					} catch (Exception e2) {
						JajeemExcetionHandler.logError(e2);
						e2.printStackTrace();
					}
					clientService2.start();
				}
			}
		}
		else{
			new Config();
			ClientService clientService2 = null;
			try {
				clientService2 = new ClientService(
						Config.getParam("broadcastingIp"), Integer.parseInt(Config
								.getParam("surveyport")));
			} catch (NumberFormatException e2) {
				JajeemExcetionHandler.logError(e2);
				e2.printStackTrace();
			} catch (Exception e2) {
				JajeemExcetionHandler.logError(e2);
				e2.printStackTrace();
			}
			clientService2.start();
		}
	}

	private boolean ValidateSession() {
		if (com.jajeem.util.Session.getCourse() == null) {
			int i = JOptionPane.showConfirmDialog(null,
					"No class has started yet!\n Do you want to continue?");
			if(i==0)
				return true;
			else{
				dispose();
				return false;
			}
		}	
		if (com.jajeem.util.Session.getInstructor() == null) {
			int i = JOptionPane.showConfirmDialog(null,
					"No instructor has logged in.Please first Log in!\n Do you want to continue?");
			if(i==0)
				return true;
			else{
				dispose();
				return false;
			}
		}
		return true;
	}

	private void initEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				if(gIndex==-1)
					com.jajeem.util.Session.setSurveyWindowOpen(true);
				else
					com.jajeem.util.Session.getIsSurveyWindowsOpen()[gIndex] = true;
				newSurveyRun();
			}
			
			@Override
			public void windowClosing(WindowEvent arg0){
				if(gIndex==-1)
					com.jajeem.util.Session.setSurveyWindowOpen(false);
				else
					com.jajeem.util.Session.getIsSurveyWindowsOpen()[gIndex] = false;
				StopSurveyCommand();
			}
		});
		wbtnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (getCurrentCardName().equals("secondPage")) { // if we are on
																	// the
																	// second
																	// page
					int i = JOptionPane
							.showConfirmDialog(
									null,
									"If you leave this page the survey will stop and results will be lost.Are you sure you want to continue?");
					if (i == 0) {
						try {
							StopSurveyCommand();
						} catch (Exception e) {
							JajeemExcetionHandler.logError(e);
							e.printStackTrace();
						}
					} else {
						return;
					}

					CardLayout cl = (CardLayout) (webPanel.getLayout());
					cl.show(webPanel, "firstPage");
					wbtnContent.setEnabled(false);
					wbtnOpen.setEnabled(true);
					wbtnSave.setEnabled(true);
					wbtnSaveResults.setVisible(false);
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(Survey_Main.class
							.getResource("/icons/noa_en/startx16.png")));
					wbtnStart.setEnabled(true);

					eventsEnabled = false;
					secondPage.clear();
					eventsEnabled = true;
				}

				eventsEnabled = false;
				firstPage.clear();
				newSurveyRun();
				eventsEnabled = true;

			}
		});
		wbtnContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane
						.showConfirmDialog(
								null,
								"If you leave this page the survey will stop and results will be lost.Are you sure you want to continue?");
				if (i == 0) {
					try {
						StopSurveyCommand();
					} catch (Exception e) {
						JajeemExcetionHandler.logError(e);
						e.printStackTrace();
					}
				} else {
					return;
				}

				CardLayout cl = (CardLayout) (webPanel.getLayout());
				cl.show(webPanel, "firstPage");
				wbtnContent.setEnabled(false);
				wbtnOpen.setEnabled(true);
				wbtnSave.setEnabled(true);
				wbtnSaveResults.setVisible(false);
				wbtnStart.setText("Start");
				wbtnStart.setIcon(new ImageIcon(Survey_Main.class
						.getResource("/icons/noa_en/startx16.png")));
				wbtnStart.setEnabled(true);

				eventsEnabled = false;
				secondPage.clear();
				eventsEnabled = true;
			}
		});
		wbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane
						.showConfirmDialog(
								null,
								"Are you sure you want to save current survey?\nNote:If you select No current survey will be disposed.");
				SurveyService qs = new SurveyService();
				if (i == 0) {
					try {
						qs.create(currentRun.getSurvey());
						eventsEnabled = false;
//						firstPage.clear();
//						newSurveyRun();
						eventsEnabled = true;
					} catch (SQLException e1) {
						JajeemExcetionHandler.logError(e1);
						e1.printStackTrace();
					}
				} else if (i == 1) {
					eventsEnabled = false;
//					firstPage.clear();
//					newSurveyRun();
					eventsEnabled = true;
				} else {
					return;
				}
			}
		});
		wbtnSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Run> results = secondPage.getRunResults();
				ResultService service = new ResultService();
				service.create(secondPage.getSurveyResponse(), results);
				JOptionPane.showMessageDialog(null, "Results Saved!");
			}
		});
		wbtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (wbtnStart.getText() == "Start") {
					if (firstPage.getWebQuestionListPanel().getWebTable()
							.getRowCount() == 0) {
						JOptionPane
								.showMessageDialog(null,
										"At least one question is required for the survey to start!");
						return;
					}

					Survey currentSurvey = currentRun.getSurvey();
					for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
						Question question = currentSurvey.getQuestionList()
								.get(i);
						if (question.getTitle().equals("")) {
							JOptionPane
									.showMessageDialog(
											null,
											"Question "
													+ (i + 1)
													+ " has no title.Please enter a title for it.");
							return;
						} // / end if
						if (question.getAnswer1().equals("")
								&& question.getAnswer2().equals("")
								&& question.getAnswer3().equals("")
								&& question.getAnswer4().equals("")
								&& question.getAnswer5().equals("")
								&& question.getType() != 2) {
							JOptionPane.showMessageDialog(null,
									"No answer is entered for the question "
											+ (i + 1)
											+ ".Please enter at least one.");
							return;
						} // // end if
					} // / end for loop

					wbtnSave.setEnabled(false);
					wbtnOpen.setEnabled(false);
					wbtnSaveResults.setVisible(true);
					wbtnStart.setText("Stop");
					wbtnContent.setEnabled(true);
					wbtnStart.setIcon(new ImageIcon(Survey_Main.class
							.getResource("/icons/noa_en/stop-redx16.png")));
					CardLayout cl = (CardLayout) (webPanel.getLayout());
					cl.show(webPanel, "secondPage");
					secondPage.LoadSurvey(currentSurvey);
					StartSurveyCommand();
				} // / end wbtnStart.getText() == "Start" if
				else {
					StopSurveyCommand();
					if(secondPage.getPanel_bottom_21().getTimer()!=null){
						if(secondPage.getPanel_bottom_21().getTimer().isRunning()){
							secondPage.getPanel_bottom_21().getTimer().stop();
						}
					}
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(Survey_Main.class
							.getResource("/icons/noa_en/startx16.png")));
					wbtnStart.setEnabled(false);
				} // / end else
			}
		});
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Survey_OpenDialog open = new Survey_OpenDialog(mainFrame);
				open.setVisible(true);
			}
		});
	}

	protected void newSurveyRun() {
		currentRun.setId(UUID.randomUUID());
		currentRun.setSurvey(new Survey());
		currentRun.setSurveyId(UUID.randomUUID());
		currentRun.getSurvey().addQuestion(new Question());

		currentRun.setCourse(getCurrentCourse());
		currentRun.setInstructor(getCurrentInstructor());
		currentRun.setInstructorId(getCurrentInstructor()
				.getId());
		currentRun.getSurvey().setInstructorId(
				getCurrentInstructor().getId());
		currentRun.getSurvey().setCourse(getCurrentCourse());
		currentRun
				.getSurvey()
				.getQuestionList()
				.get(0)
				.setInstructorId(
						getCurrentInstructor().getId());
		
		currentRun.getSurvey().getQuestionList()
		.get(0).setId(UUID.randomUUID());
		
		currentQuestion = currentRun.getSurvey().getQuestionList().get(0);

		((DefaultTableModel) firstPage.getWebQuestionListPanel().getWebTable()
				.getModel()).addRow(new Object[] { 1, "Single Choice", 0, "" });
		ListSelectionModel m_modelSelection = firstPage
				.getWebQuestionListPanel().getWebTable().getSelectionModel();
		m_modelSelection.setSelectionInterval(0, 0);
	}

	private void StopSurveyCommand() {
		try {
			if(gIndex!=-1){
				if(studentIps!=null){
					if(studentIps.size()!=0){
						new Config();
						ServerService service;
						if(InstructorNoa.getServerService() == null)
							service = new ServerService();
						else
							service = InstructorNoa.getServerService();
						for (int i = 0; i < studentIps.size(); i++) {
							StopSurveyCommand cmd = new StopSurveyCommand(InetAddress
									.getLocalHost().getHostAddress(),
									studentIps.get(i), Integer.parseInt(Config
											.getParam("port")));
							service.send(cmd);
						}
					}
				}
			}
			else{
				new Config();
				ServerService service;
				if(InstructorNoa.getServerService() == null)
					service = new ServerService();
				else
					service = InstructorNoa.getServerService();
				StopSurveyCommand cmd = new StopSurveyCommand(InetAddress
						.getLocalHost().getHostAddress(),
						Config.getParam("broadcastingIp"), Integer.parseInt(Config
								.getParam("port")));
				service.send(cmd);
			}
			
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	protected void StartSurveyCommand() {
		try {
			if(gIndex!=-1){
				if(studentIps!=null){
					if(!studentIps.isEmpty()){
						currentRun.setStart(System.currentTimeMillis());
						new Config();
						ServerService service;
						if(InstructorNoa.getServerService() == null)
							service = new ServerService();
						else
							service = InstructorNoa.getServerService();
						for (int i = 0; i < studentIps.size(); i++) {
							StartSurveyCommand cmd = new StartSurveyCommand(InetAddress
									.getLocalHost().getHostAddress(),
									studentIps.get(i), Integer.parseInt(Config
											.getParam("port")));

							cmd.setServer(InetAddress.getLocalHost().getHostAddress());
							cmd.setRun(currentRun);
							cmd.setSurvey(currentRun.getSurvey());
							cmd.setReceivingPort(Integer.parseInt(Config.getParam("surveyport"))+gIndex+1);
							service.send(cmd);
						}
					}
				}
			}
			else{
				currentRun.setStart(System.currentTimeMillis());
				new Config();
				ServerService service;
				if(InstructorNoa.getServerService() == null)
					service = new ServerService();
				else
					service = InstructorNoa.getServerService();
				StartSurveyCommand cmd = new StartSurveyCommand(InetAddress
						.getLocalHost().getHostAddress(),
						Config.getParam("broadcastingIp"), Integer.parseInt(Config
								.getParam("port")));
				cmd.setServer(InetAddress.getLocalHost().getHostAddress());
				cmd.setRun(currentRun);
				cmd.setSurvey(currentRun.getSurvey());
				cmd.setReceivingPort(Integer.parseInt(Config.getParam("surveyport")));
				service.send(cmd);
			}
		} catch (Exception ex) {
			JajeemExcetionHandler.logError(ex);
			ex.printStackTrace();
		}
	}

	private String getCurrentCardName() {
		WebPanel card = null;
		for (Component comp : webPanel.getComponents()) {
			if (comp.isVisible() == true) {
				card = (WebPanel) comp;
			}
		}
		return card.getName();
	}

	Instructor getCurrentInstructor() {
		if (com.jajeem.util.Session.getInstructor() == null) {
//			int i = JOptionPane.showConfirmDialog(null,
//					"No instructor has logged in.Please first Log in!\n Do you want to continue?");
//			if(i==0)
				return new Instructor();
//			else{
//				dispose();
//			}
		}
		return com.jajeem.util.Session.getInstructor();
	}

	Course getCurrentCourse() {
		if (com.jajeem.util.Session.getCourse() == null) {
//			int i = JOptionPane.showConfirmDialog(null, "No class has started yet!\n Do you want to continue?");
//			if(i==0)
				return new Course();
//			else{
//				dispose();
//			}
		}
		return com.jajeem.util.Session.getCourse();
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question object) {
		currentQuestion = object;
	}

	public void setEventsEnabled(boolean b) {
		eventsEnabled = b;
	}

	public Run getCurrentRun() {
		return currentRun;
	}

	public boolean isEventsEnabled() {
		return eventsEnabled;
	}

	public void setCurrentSurvey(Survey survey) {
		currentRun.setSurvey(survey);
	}

	public void loadCurrentSurvey() {
		firstPage.clear();
		firstPage.loadCurrentSurvey(currentRun.getSurvey());
	}

	public int listeningPort() {
		try {
			return Integer.parseInt(Config.getParam("surveyport"))+gIndex+1;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
