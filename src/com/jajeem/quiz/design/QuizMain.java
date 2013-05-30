package com.jajeem.quiz.design;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.core.model.Instructor;
import com.jajeem.events.QuizEvent;
import com.jajeem.quiz.design.client.QuizWindow;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.quiz.service.ResultService;
import com.jajeem.room.model.Session;
import com.jajeem.util.Config;

public class QuizMain extends WebFrame {

	private int pageNumber = 1;
	private WebPanel contentPane;
	private QuizMain frame;
	private Question currentQuestion;
	private com.jajeem.quiz.model.Quiz currentQuiz;
	//private ArrayList<com.jajeem.quiz.model.Quiz> quizList;
	private boolean eventsEnabled;
	private QuizEvent quizEvent;
	private DefaultTableModel tablemodel;
	private WebPanel cards;
	private QuizTab_1 panel_bottom_1;
	private QuizTab_2 panel_bottom_2;
	private WebPanel panel_bottom_3;
	private WebButton wbtnStart;
	private WebButton wbtnContent;
	private WebButton wbtnOpen;
	private WebButton wbtnSave;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizMain frame = new QuizMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public QuizMain() {
		setTitle("Quiz");
		setIconImage(Toolkit.getDefaultToolkit().getImage(QuizMain.class.getResource("/com/jajeem/images/quiz.png")));
		
		setBounds(100, 0, 1200, 780);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame = this;
		
		WebPanel webPanel = new WebPanel();
		getContentPane().add(webPanel, BorderLayout.CENTER);
		
		
		new Config();
		ClientService clientService2 = null;
		try {
			clientService2 = new ClientService(Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("quizport")));
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		clientService2.start();
		
//		new Config();
//		ClientService clientService = null;
//		try {
//			clientService = new ClientService(Config.getParam("broadcastingIp"), 9090);
//		} catch (NumberFormatException e2) {
//			e2.printStackTrace();
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//		clientService.start();
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
                setCurrentQuiz(new Quiz());
                getCurrentQuiz().addQuestion(new Question());
                // TODO remove these lines
                getCurrentQuiz().getQuestionList().get(0).setInstructorId(1);
                getCurrentQuiz().setInstructorId(1);
                /////////////////////
                getTablemodel().addRow(new Object[]{1,"Single Choice",0,""});
                ListSelectionModel m_modelSelection =  panel_bottom_1.getQuestionListPanel().getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(0,0);
			}
		});
		
		WebPanel panel = new WebPanel();
		
		WebPanel panel_top = new WebPanel();
		panel_top.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		cards = new WebPanel(new CardLayout());
		
		panel_bottom_1 = new QuizTab_1(this);
		setTablemodel((DefaultTableModel)panel_bottom_1.getQuestionListPanel().getWebTable().getModel());
		panel_bottom_2 = new QuizTab_2();
		panel_bottom_3 = new WebPanel();
		
		cards.add(panel_bottom_1,"Page 1");
		cards.add(panel_bottom_2,"Page 2");
		cards.add(panel_bottom_3,"Page 3");
		GroupLayout gl_panel_bottom_3 = new GroupLayout(panel_bottom_3);
		gl_panel_bottom_3.setHorizontalGroup(
			gl_panel_bottom_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1023, Short.MAX_VALUE)
		);
		gl_panel_bottom_3.setVerticalGroup(
			gl_panel_bottom_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 570, Short.MAX_VALUE)
		);
		panel_bottom_3.setLayout(gl_panel_bottom_3);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(cards, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1071, Short.MAX_VALUE)
						.addComponent(panel_top, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1071, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_top, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cards, GroupLayout.PREFERRED_SIZE, 616, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		WebButton wbtnNew = new WebButton();
		wbtnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(pageNumber == 2){
					int i = JOptionPane.showConfirmDialog(null, "If you leave this page the quiz will stop and results will be lost.Are you sure you want to continue?");
					if(i == 0){
						try {
							new Config();
							ServerService serv = new ServerService();
							StopQuizCommand cmd = new StopQuizCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
							serv.send(cmd);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else if(i == 1){
						return;
					}
					else{
						return;
					}
					CardLayout cl = (CardLayout)(cards.getLayout());
			        cl.show(cards, "Page 1");
					wbtnContent.setEnabled(false);
					wbtnOpen.setEnabled(true);
					wbtnSave.setEnabled(true);
					//wbtnSaveResults.setVisible(false);
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/start.png")));
					wbtnStart.setEnabled(true);
					panel_bottom_2.ClearQuiz();
				}
				setCurrentQuiz(new Quiz());
                getCurrentQuiz().addQuestion(new Question());
                // TODO remove these lines
                getCurrentQuiz().getQuestionList().get(0).setInstructorId(1);
                getCurrentQuiz().setInstructorId(1);
                /////////////////////
                panel_bottom_1.clear();
                getTablemodel().addRow(new Object[]{1,"Single Choice",0,""});
                ListSelectionModel m_modelSelection =  panel_bottom_1.getQuestionListPanel().getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(0,0);
			}
		});
		wbtnNew.setText("New");
		wbtnNew.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnNew.setVerticalAlignment(SwingConstants.TOP);
		wbtnNew.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/Add.png")));
		
		wbtnOpen = new WebButton();
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				QuizOpenDialog open = new QuizOpenDialog(frame);
				open.setVisible(true);
			}
		});
		wbtnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnOpen.setVerticalAlignment(SwingConstants.TOP);
		wbtnOpen.setText("Open");
		wbtnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnOpen.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/folder_green_open.png")));
		
		wbtnSave = new WebButton();
		wbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to save current quiz?");
				QuizService qs = new QuizService();
				if (i == 0){
					try {
						qs.create(currentQuiz);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if (i == 1){
					//TODO dispose currentquiz
				}
				else{
					
				}
			}
		});
		wbtnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnSave.setText("Save");
		wbtnSave.setVerticalAlignment(SwingConstants.TOP);
		wbtnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSave.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/document-save-as.png")));
		
		wbtnContent = new WebButton();
		wbtnContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = JOptionPane.showConfirmDialog(null, "If you leave this page the quiz will stop and results will be lost.Are you sure you want to continue?");
				if(i == 0){
					try {
						new Config();
						ServerService serv = new ServerService();
						StopQuizCommand cmd = new StopQuizCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						serv.send(cmd);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(i == 1){
					return;
				}
				else{
					return;
				}
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, "Page 1");
				wbtnContent.setEnabled(false);
				wbtnOpen.setEnabled(true);
				wbtnSave.setEnabled(true);
				//wbtnSaveResults.setVisible(false);
				wbtnStart.setText("Start");
				wbtnStart.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/start.png")));
				wbtnStart.setEnabled(true);
				panel_bottom_2.ClearQuiz();
				//Broadcast quiz stop
				
			}
		});
		wbtnContent.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/content.png")));
		wbtnContent.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnContent.setVerticalAlignment(SwingConstants.TOP);
		wbtnContent.setText("Content");
		wbtnContent.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnContent.setEnabled(false);
		
		wbtnStart = new WebButton();
		wbtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(wbtnStart.getText() == "Start"){
					if(panel_bottom_1.getQuestionListPanel().getWebTable().getRowCount() == 0){
						JOptionPane.showMessageDialog(null, "At least one question is required for the quiz to start!");
						return;
					}
					
					for (int i = 0; i < currentQuiz.getQuestionList().size(); i++) {
						Question question = currentQuiz.getQuestionList().get(i);
						if(question.getTitle().equals("")){
							JOptionPane.showMessageDialog(null, "Question "+(i+1)+" has no title.Please enter a title for it.");
							return;
						}
						if(question.getAnswer1().equals("") && question.getAnswer2().equals("") && question.getAnswer3().equals("") 
								&& question.getAnswer4().equals("") && question.getAnswer5().equals("") && question.getType()!=2){
							JOptionPane.showMessageDialog(null, "No answer is entered for the question "+(i+1)+".Please enter at least one.");
							return;
						}
						if(question.getType() == 0 || question.getType() == 1){
							if(!question.getCorrectAnswer()[0] && !question.getCorrectAnswer()[1] && !question.getCorrectAnswer()[2] &&
									!question.getCorrectAnswer()[3] && !question.getCorrectAnswer()[4]){
								JOptionPane.showMessageDialog(null, "No correct answer is selected for question "+(i+1)+".Please select one.");
								return;
							}
						}
					}
					
					//wbtnSaveResults.setVisible(true);
					wbtnSave.setEnabled(false);
					wbtnOpen.setEnabled(false);
					
					wbtnStart.setText("Stop");
					wbtnContent.setEnabled(true);
					wbtnStart.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/stop-red.png")));
					CardLayout cl = (CardLayout)(cards.getLayout());
			        cl.show(cards, "Page 2");
			        pageNumber=2;
			        panel_bottom_2.LoadQuiz(currentQuiz);
			        //TODO Code must be changed
			        try {
			        	Run run = new Run();
			        	run.setQuiz(currentQuiz);
			        	run.setInstructor(getCurrentInstructor());
			        	run.setSession(getCurrentSession());
			        	run.setStart(System.currentTimeMillis());
			        	//QuizWindow wind =new QuizWindow(run);
			        	
			        	new Config();
						ServerService serv = new ServerService();
						StartQuizCommand cmd = new StartQuizCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						//StartQuizCommand cmd = new StartQuizCommand("","127.0.0.1", 9090);
						cmd.setServer(InetAddress.getLocalHost().getHostAddress());
						cmd.setRun(run);
						cmd.setQuiz(currentQuiz);
						serv.send(cmd);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				else{
					try {
						new Config();
						ServerService serv = new ServerService();
						StopQuizCommand cmd = new StopQuizCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						serv.send(cmd);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/start.png")));
					wbtnStart.setEnabled(false);
					//quizEvent.fireStopEvent(new QuizStop(this));
				}
			}

			private Session getCurrentSession() {
				// TODO Auto-generated method stub
				return null;
			}

			private Instructor getCurrentInstructor() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		wbtnStart.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnStart.setText("Start");
		wbtnStart.setVerticalAlignment(SwingConstants.TOP);
		wbtnStart.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnStart.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/start.png")));
		
		WebButton wbtnSaveResults = new WebButton();
		wbtnSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Run> results = panel_bottom_2.getRunResults();
				ResultService service = new ResultService();
				service.create(panel_bottom_2.getQuizResponse(), results);
			}
		});
		wbtnSaveResults.setIcon(new ImageIcon(QuizMain.class.getResource("/com/jajeem/images/document-save-as.png")));
		wbtnSaveResults.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSaveResults.setVerticalAlignment(SwingConstants.TOP);
		wbtnSaveResults.setText("Save Results");
		wbtnSaveResults.setHorizontalTextPosition(SwingConstants.CENTER);
		
		GroupLayout gl_panel_top = new GroupLayout(panel_top);
		gl_panel_top.setHorizontalGroup(
			gl_panel_top.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_top.createSequentialGroup()
					.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSaveResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 681, Short.MAX_VALUE)
					.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_top.setVerticalGroup(
			gl_panel_top.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_top.createSequentialGroup()
					.addGroup(gl_panel_top.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_top.createParallelGroup(Alignment.BASELINE)
							.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnSaveResults, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_top.setLayout(gl_panel_top);
		panel.setLayout(gl_panel);
		
		webPanel.add(panel);
	}

	public com.jajeem.quiz.model.Quiz getCurrentQuiz() {
		return currentQuiz;
	}

	public void setCurrentQuiz(com.jajeem.quiz.model.Quiz currentQuiz) {
		this.currentQuiz = currentQuiz;
	}

	public DefaultTableModel getTablemodel() {
		return tablemodel;
	}

	public void setTablemodel(DefaultTableModel tablemodel) {
		this.tablemodel = tablemodel;
	}

	public boolean isEventsEnabled() {
		return eventsEnabled;
	}

	public void setEventsEnabled(boolean eventsEnabled) {
		this.eventsEnabled = eventsEnabled;
	}

	public Question getCurrentQuestion() {
		return currentQuestion;
	}

	public void setCurrentQuestion(Question currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public int getInstructorId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void loadCurrentQuiz() {
		panel_bottom_1.clear();
		panel_bottom_1.loadCurrentQuiz(currentQuiz);
	}
}
