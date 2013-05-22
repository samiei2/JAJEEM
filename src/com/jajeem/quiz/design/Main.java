package com.jajeem.quiz.design;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.model.StopQuizCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.events.QuizEvent;
import com.jajeem.quiz.design.client.QuizWindow;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.util.Config;

public class Main extends JFrame {
	
	private WebPanel contentPane;
	private Main frame;
	private Question currentQuestion;
	private com.jajeem.quiz.model.Quiz currentQuiz;
	//private ArrayList<com.jajeem.quiz.model.Quiz> quizList;
	private boolean eventsEnabled;
	private QuizEvent quizEvent;
	private DefaultTableModel tablemodel;
	private WebPanel cards;
	private Panel_Bottom_1 panel_bottom_1;
	private Panel_Bottom_2 panel_bottom_2;
	private WebPanel panel_bottom_3;
	private WebButton wbtnStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		frame = this;
		////////////////////////////////////////
		////////////////////////////////////////
//		new com.jajeem.util.Config();
//		ClientService clientService = null;
//		try {
//			clientService = new ClientService(Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
//		} catch (NumberFormatException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		} catch (Exception e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		clientService.start();
		new Config();
		ClientService clientService2 = null;
		try {
			clientService2 = new ClientService(Config.getParam("broadcastingIp"), 9092);
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		clientService2.start();
		//////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
//				QuizService qs = new QuizService();
//				try {
//					quizList = new ArrayList<com.jajeem.quiz.model.Quiz>();
//					ArrayList<com.jajeem.quiz.model.Quiz> temp = qs.list();
//					if(temp != null)
//						quizList.addAll(qs.list());
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				quizList.add(new com.jajeem.quiz.model.Quiz());
//                setCurrentQuiz(quizList.get(0));
                setCurrentQuiz(new Quiz());
                getCurrentQuiz().addQuestion(new Question());
                getTablemodel().addRow(new Object[]{1,"Single Choice",0,""});
                ListSelectionModel m_modelSelection =  panel_bottom_1.getQuestionListPanel().getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(0,0);
			}
		});
		setBackground(new Color(245, 245, 245));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1103, 729);
		contentPane = new WebPanel();
		contentPane.setBackground(new Color(245, 245, 245));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		WebPanel panel = new WebPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 1023, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		
		WebPanel panel_top = new WebPanel();
		panel_top.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		cards = new WebPanel(new CardLayout());
		
		panel_bottom_1 = new Panel_Bottom_1(this);
		setTablemodel((DefaultTableModel)panel_bottom_1.getQuestionListPanel().getWebTable().getModel());
		panel_bottom_2 = new Panel_Bottom_2();
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
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_top, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(cards, GroupLayout.PREFERRED_SIZE, 1019, Short.MAX_VALUE))
					.addGap(4))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_top, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		WebButton wbtnNew = new WebButton();
		wbtnNew.setText("New");
		wbtnNew.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnNew.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnNew.setVerticalAlignment(SwingConstants.TOP);
		wbtnNew.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/Add.png")));
		
		WebButton wbtnReports = new WebButton();
		wbtnReports.setEnabled(false);
		wbtnReports.setText("Reports");
		wbtnReports.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnReports.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnReports.setVerticalAlignment(SwingConstants.TOP);
		wbtnReports.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/gnome-mime-application-vnd.lotus-1-2-3.png")));
		
		WebButton wbtnResponse = new WebButton();
		wbtnResponse.setEnabled(false);
		wbtnResponse.setVerticalAlignment(SwingConstants.TOP);
		wbtnResponse.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnResponse.setText("Response");
		wbtnResponse.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnResponse.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/distributor-report.png")));
		
		WebButton wbtnOpen = new WebButton();
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OpenDialog open = new OpenDialog(frame);
				open.setVisible(true);
			}
		});
		wbtnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnOpen.setVerticalAlignment(SwingConstants.TOP);
		wbtnOpen.setText("Open");
		wbtnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnOpen.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/folder_green_open.png")));
		
		WebButton wbtnSave = new WebButton();
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
		wbtnSave.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/document-save-as.png")));
		
		final WebButton wbtnContent = new WebButton();
		wbtnContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, "Page 1");
				wbtnContent.setEnabled(false);
				wbtnStart.setText("Start");
				wbtnStart.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/start.png")));
				//TODO BroadCast Stop Quiz
			}
		});
		wbtnContent.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/content.png")));
		wbtnContent.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnContent.setVerticalAlignment(SwingConstants.TOP);
		wbtnContent.setText("Content");
		wbtnContent.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnContent.setEnabled(false);
		
		wbtnStart = new WebButton();
		wbtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(wbtnStart.getText() == "Start"){
					wbtnStart.setText("Stop");
					wbtnContent.setEnabled(true);
					wbtnStart.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/stop-red.png")));
					CardLayout cl = (CardLayout)(cards.getLayout());
			        cl.show(cards, "Page 2");
			        panel_bottom_2.LoadQuiz(currentQuiz);
			        //TODO Code must be changed
			        try {
			        	QuizWindow wind =new QuizWindow(currentQuiz);
			        	
			        	new Config();
						ServerService serv = new ServerService();
						StartQuizCommand cmd = new StartQuizCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						//StartQuizCommand cmd = new StartQuizCommand("127.0.0.1", 9090);
						cmd.setServer(InetAddress.getLocalHost().getHostAddress());
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
					wbtnStart.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/start.png")));
					//quizEvent.fireStopEvent(new QuizStop(this));
				}
			}
		});
		wbtnStart.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnStart.setText("Start");
		wbtnStart.setVerticalAlignment(SwingConstants.TOP);
		wbtnStart.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnStart.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/start.png")));
		
		GroupLayout gl_panel_top = new GroupLayout(panel_top);
		gl_panel_top.setHorizontalGroup(
			gl_panel_top.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_top.createSequentialGroup()
					.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 472, Short.MAX_VALUE)
					.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnResponse, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnReports, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_top.setVerticalGroup(
			gl_panel_top.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_top.createSequentialGroup()
					.addGroup(gl_panel_top.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnNew, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnStart, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnReports, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnSave, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnResponse, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnContent, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_top.setLayout(gl_panel_top);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
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
		
	}
}
