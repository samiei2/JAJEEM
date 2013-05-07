package com.jajeem.quiz.design;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.jajeem.command.handler.StartQuizCommandHandler;
import com.jajeem.command.model.StartQuizCommand;
import com.jajeem.command.service.ServerService;
import com.jajeem.events.QuizEvent;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.util.Config;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {

	private WebPanel contentPane;
	
	private Question currentQuestion;
	private com.jajeem.quiz.model.Quiz currentQuiz;
	private ArrayList<com.jajeem.quiz.model.Quiz> quizList;
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				QuizService qs = new QuizService();
				try {
					quizList = new ArrayList<com.jajeem.quiz.model.Quiz>();
					ArrayList<com.jajeem.quiz.model.Quiz> temp = qs.list();
					if(temp != null)
						quizList.addAll(qs.list());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				quizList.add(new com.jajeem.quiz.model.Quiz());
                setCurrentQuiz(quizList.get(0));
                
                getCurrentQuiz().addQuestion(new Question());
                getTablemodel().addRow(new Object[]{1,"Single Choice",0,""});
                ListSelectionModel m_modelSelection =  panel_bottom_1.getQuestionListPanel().getWebTable().getSelectionModel();
				m_modelSelection.setSelectionInterval(0,0);
			}
		});
		setBackground(new Color(245, 245, 245));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1049, 686);
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
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(cards, GroupLayout.PREFERRED_SIZE, 1019, Short.MAX_VALUE)
							.addGap(4))
						.addComponent(panel_top, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_top, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cards, GroupLayout.PREFERRED_SIZE, 570, Short.MAX_VALUE)
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
				OpenDialog open = new OpenDialog();
				currentQuiz = open.getValue();
			}
		});
		wbtnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnOpen.setVerticalAlignment(SwingConstants.TOP);
		wbtnOpen.setText("Open");
		wbtnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnOpen.setIcon(new ImageIcon(Main.class.getResource("/com/jajeem/images/folder_green_open.png")));
		
		WebButton wbtnSave = new WebButton();
		wbtnSave.setEnabled(false);
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
			        	new Config();
						ServerService serv = new ServerService();
						StartQuizCommand cmd = new StartQuizCommand("192.168.0.15", 9090);
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
}
