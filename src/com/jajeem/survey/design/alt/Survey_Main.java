package com.jajeem.survey.design.alt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.panel.WebPanel;
import java.awt.CardLayout;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.design.teacher.InstructorNoa;
import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.service.ResultService;
import com.jajeem.survey.service.SurveyService;
import com.jajeem.util.Config;
import com.jajeem.util.WindowResizeAdapter;

public class Survey_Main {

	private BaseSurveyFrame frame;
	
	private Run currentRun;
	private Question currentQuestion;
	private boolean eventsEnabled = true;
	private Survey_FirstPage firstPage;
	private Survey_SecondPage secondPage;
	private Survey_Main mainFrame;
	private List<String> studentIps;
	private int gIndex;
	private CustomSurveyButton wbtnNew;
	private CustomSurveyButton wbtnOpen;
	private CustomSurveyButton wbtnSave;
	private CustomSurveyButton wbtnSaveResults;
	private CustomSurveyButton wbtnContent;
	private CustomSurveyButton wbtnStart;
	private WebPanel webPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Survey_Main frame = new Survey_Main(0,null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Survey_Main(int groupIndex, List<String> list) {
		studentIps = list;
		gIndex = groupIndex;
		currentRun = new Run();
		frame = new BaseSurveyFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0,0,0,0));
		mainFrame = this;
		WindowResizeAdapter.install(frame, SwingConstants.SOUTH_EAST);
		
		wbtnNew = new CustomSurveyButton("/icons/noa_en/emptybutton.png");
		wbtnNew.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnNew.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizaddicon.png")).getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
		wbtnNew.setUndecorated(true);
		wbtnNew.setText("New");
		
		wbtnOpen = new CustomSurveyButton("/icons/noa_en/emptybutton.png");
		wbtnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnOpen.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizopenicon.png")).getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
		wbtnOpen.setUndecorated(true);
		wbtnOpen.setText("Open");
		
		wbtnSave = new CustomSurveyButton("/icons/noa_en/emptybutton.png");
		wbtnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSave.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizsaveicon.png")).getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
		wbtnSave.setUndecorated(true);
		wbtnSave.setText("Save");
		
		wbtnSaveResults = new CustomSurveyButton("/icons/noa_en/emptybutton.png");
		wbtnSaveResults.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnSaveResults.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSaveResults.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizsaveicon.png")).getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
		wbtnSaveResults.setUndecorated(true);
		wbtnSaveResults.setText("Save Results");
		
		wbtnStart = new CustomSurveyButton("/icons/noa_en/emptybutton.png");
		wbtnStart.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnStart.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnStart.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizstarticon.png")).getImage().getScaledInstance(20, 17, Image.SCALE_SMOOTH)));
		wbtnStart.putClientProperty("action","Start");
		wbtnStart.setUndecorated(true);
		wbtnStart.setText("Start");
		
		wbtnContent = new CustomSurveyButton("/icons/noa_en/emptybutton.png");
		wbtnContent.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnContent.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnContent.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizcontenticon.png")).getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH)));
		wbtnContent.setUndecorated(true);
		wbtnContent.setText("Content");
		GroupLayout groupLayout = new GroupLayout(frame.getTopPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSaveResults, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 552, Short.MAX_VALUE)
					.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnSaveResults, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addGap(13))
		);
		frame.getTopPane().setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout_1 = new GroupLayout(frame.getMainContentPane());
		groupLayout_1.setHorizontalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		groupLayout_1.setVerticalGroup(
			groupLayout_1.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		
		webPanel = new WebPanel();
		scrollPane.setViewportView(webPanel);
		webPanel.setLayout(new CardLayout(0, 0));
		
		firstPage = new Survey_FirstPage(this);
		firstPage.setName("firstPage");
		secondPage = new Survey_SecondPage(this);
		secondPage.setName("secondPage");
		
		webPanel.add(firstPage, "firstPage");
		webPanel.add(secondPage, "secondPage");
		
		frame.getMainContentPane().setLayout(groupLayout_1);
		frame.pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2
				- frame.getSize().height / 2);
		initEvents();
		initSurveyEventListener();
		if (!ValidateSession()) {
			return;
		}
//		frame.setSize(808, 528);
		frame.setVisible(true);
	}
	
	private void initSurveyEventListener() {
		if (gIndex != -1) {
			if (studentIps != null) {
				if (studentIps.size() != 0) {
					new Config();
					ClientService clientService2 = null;
					try {
						clientService2 = new ClientService(
								Config.getParam("broadcastingIp"),
								Integer.parseInt(Config.getParam("surveyport"))
										+ gIndex + 1);
					} catch (NumberFormatException e2) {
						JajeemExceptionHandler.logError(e2);
						e2.printStackTrace();
					} catch (Exception e2) {
						JajeemExceptionHandler.logError(e2);
						e2.printStackTrace();
					}
					clientService2.start();
				}
			}
		} else {
			new Config();
			ClientService clientService2 = null;
			try {
				clientService2 = new ClientService(
						Config.getParam("broadcastingIp"),
						Integer.parseInt(Config.getParam("surveyport")));
			} catch (NumberFormatException e2) {
				JajeemExceptionHandler.logError(e2);
				e2.printStackTrace();
			} catch (Exception e2) {
				JajeemExceptionHandler.logError(e2);
				e2.printStackTrace();
			}
			clientService2.start();
		}
	}

	private boolean ValidateSession() {
		if (com.jajeem.util.Session.getCourse() == null) {
			int i = WebOptionPane.showConfirmDialog(null,
					"No class has started yet!\n Do you want to continue?");
			if (i == 0) {
				return true;
			} else {
				frame.dispose();
				return false;
			}
		}
		if (com.jajeem.util.Session.getInstructor() == null) {
			int i = WebOptionPane
					.showConfirmDialog(null,
							"No instructor has logged in.Please first Log in!\n Do you want to continue?");
			if (i == 0) {
				return true;
			} else {
				frame.dispose();
				return false;
			}
		}
		return true;
	}

	private void initEvents() {
		((CustomSurveyButton)frame.getCloseButton()).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				if (gIndex == -1) {
					com.jajeem.util.Session.setSurveyWindowOpen(false);
				} else {
					com.jajeem.util.Session.getIsSurveyWindowsOpen()[gIndex] = false;
				}
				StopSurveyCommand();
				super.componentHidden(e);
				frame.dispose();
			}
		});
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				if (gIndex == -1) {
					com.jajeem.util.Session.setSurveyWindowOpen(true);
				} else {
					com.jajeem.util.Session.getIsSurveyWindowsOpen()[gIndex] = true;
				}
				newSurveyRun();
			}
		});
		wbtnNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (getCurrentCardName().equals("secondPage")) { // if we are on
																	// the
																	// second
																	// page
					int i = WebOptionPane
							.showConfirmDialog(
									null,
									"If you leave this page the survey will stop and results will be lost.Are you sure you want to continue?");
					if (i == 0) {
						try {
							StopSurveyCommand();
						} catch (Exception e) {
							JajeemExceptionHandler.logError(e);
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
//					wbtnStart.setIcon(new ImageIcon(Survey_Main.class
//							.getResource("/icons/noa_en/startx16.png")));
//					wbtnStart.setEnabled(true);
					wbtnStart.putClientProperty("action","Start");
					wbtnStart.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizstarticon.png")).getImage().getScaledInstance(20, 17, Image.SCALE_SMOOTH)));
					
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
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = WebOptionPane
						.showConfirmDialog(
								null,
								"If you leave this page the survey will stop and results will be lost.Are you sure you want to continue?");
				if (i == 0) {
					try {
						StopSurveyCommand();
					} catch (Exception e) {
						JajeemExceptionHandler.logError(e);
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
//				wbtnStart.setIcon(new ImageIcon(Survey_Main.class
//						.getResource("/icons/noa_en/startx16.png")));
				wbtnStart.putClientProperty("action","Start");
				wbtnStart.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizstarticon.png")).getImage().getScaledInstance(20, 17, Image.SCALE_SMOOTH)));
				wbtnStart.setEnabled(true);

				eventsEnabled = false;
				secondPage.clear();
				eventsEnabled = true;
			}
		});
		wbtnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = WebOptionPane
						.showConfirmDialog(
								null,
								"Are you sure you want to save current survey?\nNote:If you select No current survey will be disposed.");
				SurveyService qs = new SurveyService();
				if (i == 0) {
					try {
						qs.create(currentRun.getSurvey());
						eventsEnabled = false;
						// firstPage.clear();
						// newSurveyRun();
						eventsEnabled = true;
					} catch (SQLException e1) {
						JajeemExceptionHandler.logError(e1);
						e1.printStackTrace();
					}
				} else if (i == 1) {
					eventsEnabled = false;
					eventsEnabled = true;
				} else {
					return;
				}
			}
		});
		wbtnSaveResults.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Run> results = secondPage.getRunResults();
				ResultService service = new ResultService();
				service.create(secondPage.getSurveyResponse(), results);
				WebOptionPane.showMessageDialog(null, "Results Saved!");
			}
		});
		wbtnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (wbtnStart.getClientProperty("action").toString() == "Start") {
					if (firstPage.getWebQuestionListPanel().getWebTable()
							.getRowCount() == 0) {
						WebOptionPane
								.showMessageDialog(null,
										"At least one question is required for the survey to start!");
						return;
					}

					Survey currentSurvey = currentRun.getSurvey();
					for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
						Question question = currentSurvey.getQuestionList()
								.get(i);
						if (question.getTitle().equals("")) {
							WebOptionPane
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
							WebOptionPane.showMessageDialog(null,
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
					wbtnStart.putClientProperty("action","Stop");
					wbtnContent.setEnabled(true);
//					wbtnStart.setIcon(new ImageIcon(Survey_Main.class
//							.getResource("/icons/noa_en/stop-redx16.png")));
					wbtnStart.putClientProperty("action","Stop");
					wbtnStart.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizstopicon.png")).getImage().getScaledInstance(20, 17, Image.SCALE_SMOOTH)));
					CardLayout cl = (CardLayout) (webPanel.getLayout());
					cl.show(webPanel, "secondPage");
					secondPage.LoadSurvey(currentSurvey);
					StartSurveyCommand();
				} // / end wbtnStart.getText() == "Start" if
				else {
					StopSurveyCommand();
					if (secondPage.getPanel_bottom_21().getTimer() != null) {
						if (secondPage.getPanel_bottom_21().getTimer()
								.isRunning()) {
							secondPage.getPanel_bottom_21().getTimer().stop();
						}
					}
					wbtnStart.setText("Start");
//					wbtnStart.setIcon(new ImageIcon(Survey_Main.class
//							.getResource("/icons/noa_en/startx16.png")));
					wbtnStart.setIcon(new ImageIcon(new ImageIcon(Survey_Main.class.getResource("/icons/noa_en/quizstarticon.png")).getImage().getScaledInstance(20, 17, Image.SCALE_SMOOTH)));
					wbtnStart.putClientProperty("action","Start");
					wbtnStart.setEnabled(false);
				} // / end else
			}
		});
		wbtnOpen.addActionListener(new ActionListener() {
			@Override
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
		currentRun.setInstructorId(getCurrentInstructor().getId());
		currentRun.getSurvey().setInstructorId(getCurrentInstructor().getId());
		currentRun.getSurvey().setCourse(getCurrentCourse());
		currentRun.getSurvey().getQuestionList().get(0)
				.setInstructorId(getCurrentInstructor().getId());

		currentRun.getSurvey().getQuestionList().get(0)
				.setId(UUID.randomUUID());

		currentQuestion = currentRun.getSurvey().getQuestionList().get(0);

		((DefaultTableModel) firstPage.getWebQuestionListPanel().getWebTable()
				.getModel()).addRow(new Object[] { 1, "Single Choice", 0, "" });
		ListSelectionModel m_modelSelection = firstPage
				.getWebQuestionListPanel().getWebTable().getSelectionModel();
		m_modelSelection.setSelectionInterval(0, 0);
	}

	private void StopSurveyCommand() {
		try {
			if (gIndex != -1) {
				if (studentIps != null) {
					if (studentIps.size() != 0) {
						new Config();
						ServerService service;
						if (InstructorNoa.getServerService() == null) {
							service = new ServerService();
						} else {
							service = InstructorNoa.getServerService();
						}
						for (int i = 0; i < studentIps.size(); i++) {
							StopSurveyCommand cmd = new StopSurveyCommand(
									InetAddress.getLocalHost().getHostAddress(),
									studentIps.get(i), Integer.parseInt(Config
											.getParam("port")));
							service.send(cmd);
						}
					}
				}
			} else {
				new Config();
				ServerService service;
				if (InstructorNoa.getServerService() == null) {
					service = new ServerService();
				} else {
					service = InstructorNoa.getServerService();
				}
				StopSurveyCommand cmd = new StopSurveyCommand(InetAddress
						.getLocalHost().getHostAddress(),
						Config.getParam("broadcastingIp"),
						Integer.parseInt(Config.getParam("port")));
				service.send(cmd);
			}

		} catch (Exception e) {
			JajeemExceptionHandler.logError(e);
			e.printStackTrace();
		}
	}

	protected void StartSurveyCommand() {
		try {
			if (gIndex != -1) {
				if (studentIps != null) {
					if (!studentIps.isEmpty()) {
						currentRun.setStart(System.currentTimeMillis());
						new Config();
						ServerService service;
						if (InstructorNoa.getServerService() == null) {
							service = new ServerService();
						} else {
							service = InstructorNoa.getServerService();
						}
						for (int i = 0; i < studentIps.size(); i++) {
							StartSurveyCommand cmd = new StartSurveyCommand(
									InetAddress.getLocalHost().getHostAddress(),
									studentIps.get(i), Integer.parseInt(Config
											.getParam("port")));

							cmd.setServer(InetAddress.getLocalHost()
									.getHostAddress());
							cmd.setRun(currentRun);
							cmd.setSurvey(currentRun.getSurvey());
							cmd.setReceivingPort(Integer.parseInt(Config
									.getParam("surveyport")) + gIndex + 1);
							service.send(cmd);
						}
					}
				}
			} else {
				currentRun.setStart(System.currentTimeMillis());
				new Config();
				ServerService service;
				if (InstructorNoa.getServerService() == null) {
					service = new ServerService();
				} else {
					service = InstructorNoa.getServerService();
				}
				StartSurveyCommand cmd = new StartSurveyCommand(InetAddress
						.getLocalHost().getHostAddress(),
						Config.getParam("broadcastingIp"),
						Integer.parseInt(Config.getParam("port")));
				cmd.setServer(InetAddress.getLocalHost().getHostAddress());
				cmd.setRun(currentRun);
				cmd.setSurvey(currentRun.getSurvey());
				cmd.setReceivingPort(Integer.parseInt(Config
						.getParam("surveyport")));
				service.send(cmd);
			}
		} catch (Exception ex) {
			JajeemExceptionHandler.logError(ex);
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
			// int i = JOptionPane.showConfirmDialog(null,
			// "No instructor has logged in.Please first Log in!\n Do you want to continue?");
			// if(i==0)
			return new Instructor();
			// else{
			// dispose();
			// }
		}
		return com.jajeem.util.Session.getInstructor();
	}

	Course getCurrentCourse() {
		if (com.jajeem.util.Session.getCourse() == null) {
			// int i = JOptionPane.showConfirmDialog(null,
			// "No class has started yet!\n Do you want to continue?");
			// if(i==0)
			return new Course();
			// else{
			// dispose();
			// }
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
			return Integer.parseInt(Config.getParam("surveyport")) + gIndex + 1;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
