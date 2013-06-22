package com.jajeem.quiz.design.alt;

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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Instructor;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.quiz.service.RunService;
import com.jajeem.room.model.Session;
import com.jajeem.util.Config;

public class Quiz_Main extends WebFrame {

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quiz_Main frame = new Quiz_Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Quiz_Main() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Quiz_Main.class.getResource("/com/jajeem/images/quiz.png")));
		setTitle("Quiz");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 891, 600);
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
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																webPanel_1,
																Alignment.CENTER,
																GroupLayout.PREFERRED_SIZE,
																0,
																Short.MAX_VALUE)
														.addComponent(
																webPanel_2,
																Alignment.CENTER,
																GroupLayout.PREFERRED_SIZE,
																774,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addGap(11)
						.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE,
								51, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webPanel_2, GroupLayout.PREFERRED_SIZE,
								483, Short.MAX_VALUE).addContainerGap()));

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

		firstPage = new Quiz_FirstPage(this);
		firstPage.setName("firstPage");
		secondPage = new Quiz_SecondPage();
		secondPage.setName("secondPage");

		webPanel.add(firstPage, "firstPage");
		webPanel.add(secondPage, "secondPage");

		wbtnNew = new WebButton();
		wbtnNew.setIcon(new ImageIcon(Quiz_Main.class
				.getResource("/com/jajeem/images/Addx16.png")));

		wbtnOpen = new WebButton();

		wbtnSave = new WebButton();

		wbtnSaveResults = new WebButton();
		wbtnSaveResults.setVisible(false);

		wbtnStart = new WebButton();

		wbtnContent = new WebButton();
		wbtnContent.setEnabled(false);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(gl_webPanel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel_1
						.createSequentialGroup()
						.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 48,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 48,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 48,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(wbtnSaveResults,
								GroupLayout.PREFERRED_SIZE, 48,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 450,
								Short.MAX_VALUE)
						.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE,
								48, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE,
								48, GroupLayout.PREFERRED_SIZE)));
		gl_webPanel_1.setVerticalGroup(gl_webPanel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel_1
						.createSequentialGroup()
						.addGroup(
								gl_webPanel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(wbtnNew,
												GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wbtnOpen,
												GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wbtnSave,
												GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wbtnSaveResults,
												GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wbtnStart,
												GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(wbtnContent,
												GroupLayout.PREFERRED_SIZE, 48,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		webPanel_1.setLayout(gl_webPanel_1);
		contentPane.setLayout(gl_contentPane);
	
		initEvents();
		initQuizEventListener();
//		if(!ValidateSession())
//			return;
		setVisible(true);
	}

	private boolean ValidateSession() {
		if (com.jajeem.util.Session.getSession() == null) {
			JOptionPane.showMessageDialog(null, "No class has started yet!");
			dispose();
			return false;
		}	
		if (com.jajeem.util.Session.getInstructor() == null) {
			JOptionPane.showMessageDialog(null,
					"No instructor has logged in.Please first Log in!");
			dispose();
			return false;
		}
		return true;
	}

	private void initQuizEventListener() {
		new Config();
		ClientService clientService2 = null;
		try {
			clientService2 = new ClientService(
					Config.getParam("broadcastingIp"), Integer.parseInt(Config
							.getParam("quizport")));
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		clientService2.start();
	}

	private void initEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				newQuizRun();
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
									"If you leave this page the quiz will stop and results will be lost.Are you sure you want to continue?");
					if (i == 0) {
						try {
							StopQuizCommand();
						} catch (Exception e) {
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
							.getResource("/com/jajeem/images/start.png")));
					wbtnStart.setEnabled(true);

					eventsEnabled = false;
//					secondPage.clear();
					eventsEnabled = true;
				}

				eventsEnabled = false;
				firstPage.clear();
				newQuizRun();
				eventsEnabled = true;

			}
		});
		wbtnContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane
						.showConfirmDialog(
								null,
								"If you leave this page the quiz will stop and results will be lost.Are you sure you want to continue?");
				if (i == 0) {
					try {
						StopQuizCommand();
					} catch (Exception e) {
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
						.getResource("/com/jajeem/images/start.png")));
				wbtnStart.setEnabled(true);

				eventsEnabled = false;
//				secondPage.clear();
				eventsEnabled = true;
			}
		});
		wbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane
						.showConfirmDialog(
								null,
								"Are you sure you want to save current quiz?\nNote:If you select No current quiz will be disposed.");
				RunService qs = new RunService();
				if (i == 0) {
					try {
						qs.create(currentRun);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (i == 1) {
					eventsEnabled = false;
					firstPage.clear();
					newQuizRun();
					eventsEnabled = true;
				} else {
					return;
				}
			}
		});
		wbtnSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		wbtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (wbtnStart.getText() == "Start") {
					if (firstPage.getWebQuestionListPanel().getWebTable()
							.getRowCount() == 0) {
						JOptionPane
								.showMessageDialog(null,
										"At least one question is required for the quiz to start!");
						return;
					}

					Quiz currentQuiz = currentRun.getQuiz();
					for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
						Question question = currentQuiz.getQuestionList()
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
						if (question.getType() == 0 || question.getType() == 1) {
							if (!question.getCorrectAnswer()[0]
									&& !question.getCorrectAnswer()[1]
									&& !question.getCorrectAnswer()[2]
									&& !question.getCorrectAnswer()[3]
									&& !question.getCorrectAnswer()[4]) {
								JOptionPane.showMessageDialog(null,
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
							.getResource("/com/jajeem/images/stop-red.png")));
					CardLayout cl = (CardLayout) (webPanel.getLayout());
					cl.show(webPanel, "secondPage");

					StartQuizCommand();
				} // / end wbtnStart.getText() == "Start" if
				else {
					StopQuizCommand();
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(Quiz_Main.class
							.getResource("/com/jajeem/images/start.png")));
					wbtnStart.setEnabled(false);
				} // / end else
			}
		});
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}

	protected void newQuizRun() {
		currentRun = new Run();
		currentRun.setQuiz(new Quiz());
		currentRun.getQuiz().addQuestion(new Question());

		currentRun.setSession(getCurrentSession());
		currentRun.setInstructor(getCurrentInstructor());
		currentRun.setInstructorId(getCurrentInstructor()
				.getId());
		currentRun.getQuiz().setInstructorId(
				getCurrentInstructor().getId());
		currentRun
				.getQuiz()
				.getQuestionList()
				.get(0)
				.setInstructorId(
						getCurrentInstructor().getId());

		((DefaultTableModel) firstPage.getWebQuestionListPanel().getWebTable()
				.getModel()).addRow(new Object[] { 1, "Single Choice", 0, "" });
		ListSelectionModel m_modelSelection = firstPage
				.getWebQuestionListPanel().getWebTable().getSelectionModel();
		m_modelSelection.setSelectionInterval(0, 0);
	}

	@SuppressWarnings("unused")
	private void StopQuizCommand() {
		try {
			new Config();
			ServerService serv = new ServerService();
			StopQuizCommand cmd = new StopQuizCommand(InetAddress
					.getLocalHost().getHostAddress(),
					Config.getParam("broadcastingIp"), Integer.parseInt(Config
							.getParam("port")));
			serv.send(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void StartQuizCommand() {
		try {
			currentRun.setStart(System.currentTimeMillis());
			new Config();
			ServerService serv = new ServerService();
			StartQuizCommand cmd = new StartQuizCommand(InetAddress
					.getLocalHost().getHostAddress(),
					Config.getParam("broadcastingIp"), Integer.parseInt(Config
							.getParam("port")));
			// StartQuizCommand cmd = new StartQuizCommand("","127.0.0.1",
			// 9090);
			cmd.setServer(InetAddress.getLocalHost().getHostAddress());
			cmd.setRun(currentRun);
			cmd.setQuiz(currentRun.getQuiz());
			serv.send(cmd);
		} catch (Exception ex) {
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
			JOptionPane.showMessageDialog(null,
					"No instructor has logged in.Please first Log in!");
			//TODO CHANGE
			return new Instructor();
//			dispose();
		}
		return com.jajeem.util.Session.getInstructor();
	}

	Session getCurrentSession() {
		if (com.jajeem.util.Session.getSession() == null) {
			JOptionPane.showMessageDialog(null, "No class has started yet!");
			//TODO CHANGE LINE
			return new Session();
//			dispose();
		}
		return com.jajeem.util.Session.getSession();
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
}
