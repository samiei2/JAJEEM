package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.core.design.InstructorNoa;
import com.jajeem.core.model.Instructor;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.quiz.service.ResultService;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;

public class Quiz_Main extends BaseQuizFrame {
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
	private Quiz_FirstPage firstPage;
	private Quiz_SecondPage secondPage;
	private WebPanel webPanel;
	private Quiz_Main mainFrame;
	private List<String> studentIps;
	private int gIndex;
	private WebPanel webPanelCards;

	public Quiz_Main(int groupIndex, List<String> list) {
		studentIps = list;
		gIndex = groupIndex;
		currentRun = new Run();
		mainFrame = this;

		CustomQuizButton webButtonAdd = new CustomQuizButton(
				"/icons/noa_en/quizadd.png");
		webButtonAdd.setUndecorated(true);
		wbtnNew  = webButtonAdd;

		CustomQuizButton webButtonOpen = new CustomQuizButton(
				"/icons/noa_en/quizopen.png");
		webButtonOpen.setUndecorated(true);
		wbtnOpen = webButtonOpen;

		CustomQuizButton webButtonSave = new CustomQuizButton(
				"/icons/noa_en/quizsave.png");
		webButtonSave.setUndecorated(true);
		wbtnSave = webButtonSave;

		CustomQuizButton webButtonStart = new CustomQuizButton(
				"/icons/noa_en/quizstart.png");
		webButtonStart.setUndecorated(true);
		wbtnStart = webButtonStart;

		CustomQuizButton webButtonContent = new CustomQuizButton(
				"/icons/noa_en/quizcontent.png");
		webButtonContent.setUndecorated(true);
		wbtnContent = webButtonContent;
		
		CustomQuizButton webButtonSaveResults = new CustomQuizButton("/icons/noa_en/quizsave.png");
		webButtonSaveResults.setVisible(false);
		webButtonSaveResults.setUndecorated(true);
		wbtnSaveResults = webButtonSaveResults;
		
		
		GroupLayout groupLayout = new GroupLayout(getTopPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(webButtonAdd, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButtonOpen, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButtonSave, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButtonSaveResults, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 491, Short.MAX_VALUE)
					.addComponent(webButtonContent, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(webButtonStart, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(webButtonSaveResults, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButtonContent, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButtonStart, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButtonSave, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButtonOpen, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(webButtonAdd, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		getTopPane().setLayout(groupLayout);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		getMainContentPane().add(panel, BorderLayout.CENTER);
		
		WebScrollPane webScrollPane = new WebScrollPane((Component) null);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
		);
		
		webPanelCards = new WebPanel();
		webScrollPane.setViewportView(webPanelCards);
		webPanelCards.setLayout(new CardLayout(0,0));
		panel.setLayout(gl_panel);
		
		firstPage = new Quiz_FirstPage(this);
		firstPage.setName("firstPage");
		secondPage = new Quiz_SecondPage(this);
		secondPage.setName("secondPage");

		webPanelCards.add(firstPage, "firstPage");
		webPanelCards.add(secondPage, "secondPage");
		
		initEvents();
		initQuizEventListener();
		if (!ValidateSession()) {
			return;
		}
		
		setVisible(true);
	}
	
	private void initQuizEventListener() {
		if (gIndex != -1) {
			if (studentIps != null) {
				if (studentIps.size() != 0) {
					new Config();
					ClientService clientService2 = null;
					try {
						clientService2 = new ClientService(
								Config.getParam("broadcastingIp"),
								Integer.parseInt(Config.getParam("quizport"))
										+ gIndex + 1);
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
		} else {
			new Config();
			ClientService clientService2 = null;
			try {
				clientService2 = new ClientService(
						Config.getParam("broadcastingIp"),
						Integer.parseInt(Config.getParam("quizport")));
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
			int i = WebOptionPane.showConfirmDialog(null,
					"No class has started yet!\n Do you want to continue?");
			if (i == 0) {
				return true;
			} else {
				dispose();
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
				if (gIndex == -1) {
					com.jajeem.util.Session.setQuizWindowOpen(true);
				} else {
					com.jajeem.util.Session.getIsQuizWindowsOpen()[gIndex] = true;
				}
				newQuizRun();
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				if (gIndex == -1) {
					com.jajeem.util.Session.setQuizWindowOpen(false);
				} else {
					com.jajeem.util.Session.getIsQuizWindowsOpen()[gIndex] = false;
				}
				StopQuizCommand();
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
									"If you leave this page the quiz will stop and results will be lost.Are you sure you want to continue?");
					if (i == 0) {
						try {
							StopQuizCommand();
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
					wbtnStart.setIcon(new ImageIcon(Quiz_Main.class
							.getResource("/icons/noa_en/startx16.png")));
					wbtnStart.setEnabled(true);

					eventsEnabled = false;
					secondPage.clear();
					eventsEnabled = true;
				}

				eventsEnabled = false;
				firstPage.clear();
				newQuizRun();
				eventsEnabled = true;

			}
		});
		wbtnContent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = WebOptionPane
						.showConfirmDialog(
								null,
								"If you leave this page the quiz will stop and results will be lost.Are you sure you want to continue?");
				if (i == 0) {
					try {
						StopQuizCommand();
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
				wbtnStart.setIcon(new ImageIcon(Quiz_Main.class
						.getResource("/icons/noa_en/startx16.png")));
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
								"Are you sure you want to save current quiz?\nNote:If you select No current quiz will be disposed.");
				QuizService qs = new QuizService();
				if (i == 0) {
					try {
						qs.create(currentRun.getQuiz());
						eventsEnabled = false;
						// firstPage.clear();
						// newQuizRun();
						eventsEnabled = true;
					} catch (SQLException e1) {
						JajeemExcetionHandler.logError(e1);
						e1.printStackTrace();
					}
				} else if (i == 1) {
					eventsEnabled = false;
					// firstPage.clear();
					// newQuizRun();
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
				service.create(secondPage.getQuizResponse(), results);
				WebOptionPane.showMessageDialog(null, "Results Saved!");
			}
		});
		wbtnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (wbtnStart.getText() == "Start") {
					if (firstPage.getWebQuestionListPanel().getWebTable()
							.getRowCount() == 0) {
						WebOptionPane
								.showMessageDialog(null,
										"At least one question is required for the quiz to start!");
						return;
					}

					Quiz currentQuiz = currentRun.getQuiz();
					for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
						Question question = currentQuiz.getQuestionList()
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
						if (question.getType() == 0 || question.getType() == 1) {
							if (!question.getCorrectAnswer()[0]
									&& !question.getCorrectAnswer()[1]
									&& !question.getCorrectAnswer()[2]
									&& !question.getCorrectAnswer()[3]
									&& !question.getCorrectAnswer()[4]) {
								WebOptionPane.showMessageDialog(null,
										"No correct answer is selected for question "
												+ (i + 1)
												+ ".Please select one.");
								return;
							}
						} // / end if
					} // / end for loop

					wbtnSave.setEnabled(false);
					wbtnOpen.setEnabled(false);
					wbtnSaveResults.setVisible(true);
					wbtnStart.setText("Stop");
					wbtnContent.setEnabled(true);
					wbtnStart.setIcon(new ImageIcon(Quiz_Main.class
							.getResource("/icons/noa_en/stop-redx16.png")));
					CardLayout cl = (CardLayout) (webPanel.getLayout());
					cl.show(webPanel, "secondPage");
					secondPage.LoadQuiz(currentQuiz);
					StartQuizCommand();
				} // / end wbtnStart.getText() == "Start" if
				else {
					StopQuizCommand();
					if (secondPage.getPanel_bottom_21().getTimer() != null) {
						if (secondPage.getPanel_bottom_21().getTimer()
								.isRunning()) {
							secondPage.getPanel_bottom_21().getTimer().stop();
							secondPage.getPanel_bottom_21().getWebTextField_1()
									.setText("");
						}
					}
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(Quiz_Main.class
							.getResource("/icons/noa_en/startx16.png")));
					wbtnStart.setEnabled(false);
				} // / end else
			}
		});
		wbtnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Quiz_OpenDialog open = new Quiz_OpenDialog(mainFrame);
				open.setVisible(true);
			}
		});
	}

	protected void newQuizRun() {
		currentRun.setQuiz(new Quiz());
		currentRun.setCourse(com.jajeem.util.Session.getCourse());

		currentRun.setId(UUID.randomUUID());
		currentRun.setQuizId(UUID.randomUUID());
		currentRun.getQuiz().addQuestion(new Question());

		currentRun.setCourse(getCurrentCourse());
		currentRun.setInstructor(getCurrentInstructor());
		currentRun.setInstructorId(getCurrentInstructor().getId());
		currentRun.setCourseId(getCurrentCourse().getId());
		currentRun.getQuiz().setInstructorId(getCurrentInstructor().getId());
		currentRun.getQuiz().setCourseId(getCurrentCourse().getId());

		currentRun.getQuiz().getQuestionList().get(0)
				.setInstructorId(getCurrentInstructor().getId());
		currentRun.getQuiz().getQuestionList().get(0).setId(UUID.randomUUID());
		currentQuestion = currentRun.getQuiz().getQuestionList().get(0);

		((DefaultTableModel) firstPage.getWebQuestionListPanel().getWebTable()
				.getModel()).addRow(new Object[] { 1, "Single Choice", 0, "" });
		ListSelectionModel m_modelSelection = firstPage
				.getWebQuestionListPanel().getWebTable().getSelectionModel();
		m_modelSelection.setSelectionInterval(0, 0);
	}

	private void StopQuizCommand() {
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
							StopQuizCommand cmd = new StopQuizCommand(
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
				StopQuizCommand cmd = new StopQuizCommand(InetAddress
						.getLocalHost().getHostAddress(),
						Config.getParam("broadcastingIp"),
						Integer.parseInt(Config.getParam("port")));
				service.send(cmd);
			}

		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	protected void StartQuizCommand() {
		try {
			if (gIndex != -1) {
				if (studentIps != null) {
					if (!studentIps.isEmpty()) {
						currentRun.setStart(System.currentTimeMillis());
						// currentRun.setId(UUID.randomUUID());

						new Config();
						ServerService service;
						if (InstructorNoa.getServerService() == null) {
							service = new ServerService();
						} else {
							service = InstructorNoa.getServerService();
						}
						for (int i = 0; i < studentIps.size(); i++) {
							StartQuizCommand cmd = new StartQuizCommand(
									InetAddress.getLocalHost().getHostAddress(),
									studentIps.get(i), Integer.parseInt(Config
											.getParam("port")));

							cmd.setServer(InetAddress.getLocalHost()
									.getHostAddress());
							cmd.setRun(currentRun);
							cmd.setQuiz(currentRun.getQuiz());
							cmd.setReceivePort(Integer.parseInt(Config
									.getParam("quizport")) + gIndex + 1);
							service.send(cmd);
						}
					}
				}
			} else {
				// currentRun.setId(UUID.randomUUID());
				currentRun.setStart(System.currentTimeMillis());
				new Config();
				ServerService service;
				if (InstructorNoa.getServerService() == null) {
					service = new ServerService();
				} else {
					service = InstructorNoa.getServerService();
				}
				StartQuizCommand cmd = new StartQuizCommand(InetAddress
						.getLocalHost().getHostAddress(),
						Config.getParam("broadcastingIp"),
						Integer.parseInt(Config.getParam("port")));
				cmd.setServer(InetAddress.getLocalHost().getHostAddress());
				cmd.setRun(currentRun);
				cmd.setQuiz(currentRun.getQuiz());
				cmd.setReceivePort(Integer.parseInt(Config.getParam("quizport")));
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
			// int i = WebOptionPane.showConfirmDialog(null,
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
			// int i = WebOptionPane.showConfirmDialog(null,
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

	public void setCurrentQuiz(Quiz quiz) {
		currentRun.setQuiz(quiz);
	}

	public void loadCurrentQuiz() {
		firstPage.clear();
		firstPage.loadCurrentQuiz(currentRun.getQuiz());
	}

	public int listeningPort() {
		try {
			return Integer.parseInt(Config.getParam("quizport")) + gIndex + 1;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void main(String[] args) {
		Quiz_Main main = new Quiz_Main(-1, null);
		main.pack();
		main.setVisible(true);
	}
}
