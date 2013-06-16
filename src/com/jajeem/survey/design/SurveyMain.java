package com.jajeem.survey.design;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.jajeem.command.model.StartSurveyCommand;
import com.jajeem.command.model.StopSurveyCommand;
import com.jajeem.command.service.ClientService;
import com.jajeem.command.service.ServerService;
import com.jajeem.events.SurveyEvent;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.service.SurveyService;
import com.jajeem.util.Config;

public class SurveyMain extends WebFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageNumber = 1;
	private WebPanel contentPane;
	private SurveyMain frame;
	private Question currentQuestion;
	private com.jajeem.survey.model.Survey currentSurvey;
	//private ArrayList<com.jajeem.survey.model.Survey> surveyList;
	private boolean eventsEnabled;
	private SurveyEvent surveyEvent;
	private DefaultTableModel tablemodel;
	private WebPanel cards;
	private Panel_Bottom_1 panel_bottom_1;
	private Panel_Bottom_2 panel_bottom_2;
	private WebPanel panel_bottom_3;
	private WebButton wbtnStart;
	private WebButton wbtnOpen;
	private WebButton wbtnSave;
	private WebButton wbtnContent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SurveyMain frame = new SurveyMain();
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
	public SurveyMain() {
		setBounds(100, 0, 1160, 780);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		WebPanel webPanel = new WebPanel();
		getContentPane().add(webPanel, BorderLayout.CENTER);

		setTitle("Survey");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SurveyMain.class.getResource("/com/jajeem/images/survey.png")));
		frame = this;
		////////////////////////////////////////
		////////////////////////////////////////
		new Config();
		ClientService clientService2 = null;
		try {
		clientService2 = new ClientService(Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("surveyport")));
		} catch (NumberFormatException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
		} catch (Exception e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
		}
		clientService2.start();
		//////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				setCurrentSurvey(new Survey());
				getCurrentSurvey().addQuestion(new Question());
				// TODO remove these lines
				getCurrentSurvey().getQuestionList().get(0).setInstructorId(1);
				getCurrentSurvey().setInstructorId(1);
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
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_top, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1038, Short.MAX_VALUE)
						.addComponent(cards, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1038, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_top, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cards, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
							StopSurveyCommand cmd = new StopSurveyCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
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
					wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
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
		wbtnNew.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				setCurrentSurvey(new Survey());
                getCurrentSurvey().addQuestion(new Question());
                // TODO remove these lines
                getCurrentSurvey().getQuestionList().get(0).setInstructorId(1);
                getCurrentSurvey().setInstructorId(1);
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
		wbtnNew.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/Add.png")));
		
		WebButton wbtnReports = new WebButton();
		wbtnReports.setEnabled(false);
		wbtnReports.setText("Reports");
		wbtnReports.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnReports.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnReports.setVerticalAlignment(SwingConstants.TOP);
		wbtnReports.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/gnome-mime-application-vnd.lotus-1-2-3.png")));
		
		WebButton wbtnResponse = new WebButton();
		wbtnResponse.setEnabled(false);
		wbtnResponse.setVerticalAlignment(SwingConstants.TOP);
		wbtnResponse.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnResponse.setText("Response");
		wbtnResponse.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnResponse.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/distributor-report.png")));
		
		wbtnOpen = new WebButton();
		wbtnOpen.setEnabled(false);
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
		wbtnOpen.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/folder_green_open.png")));
		
		wbtnSave = new WebButton();
		wbtnSave.setEnabled(false);
		wbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to save current survey?");
				SurveyService qs = new SurveyService();
				if (i == 0){
					try {
						qs.create(currentSurvey);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if (i == 1){
					//TODO dispose currentsurvey
				}
				else{
					
				}
			}
		});
		wbtnSave.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnSave.setText("Save");
		wbtnSave.setVerticalAlignment(SwingConstants.TOP);
		wbtnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnSave.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/document-save-as.png")));
		
		wbtnContent = new WebButton();
		wbtnContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, "Page 1");
				wbtnContent.setEnabled(false);
				wbtnStart.setText("Start");
				wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
				//TODO BroadCast Stop Survey
			}
		});
		wbtnContent.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/content.png")));
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
					wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/stop-red.png")));
					CardLayout cl = (CardLayout)(cards.getLayout());
			        cl.show(cards, "Page 2");
			        panel_bottom_2.LoadSurvey(currentSurvey);
			        //TODO Code must be changed
			        try {
			        	//SurveyWindow wind =new SurveyWindow(currentSurvey);
			        	
			        	new Config();
						ServerService serv = new ServerService();
						StartSurveyCommand cmd = new StartSurveyCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						//StartSurveyCommand cmd = new StartSurveyCommand("127.0.0.1","127.0.0.1", 9090);
						cmd.setServer(InetAddress.getLocalHost().getHostAddress());
						cmd.setSurvey(currentSurvey);
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
						StopSurveyCommand cmd = new StopSurveyCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						serv.send(cmd);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					wbtnStart.setText("Start");
					pageNumber=2;
					wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
					//surveyEvent.fireStopEvent(new SurveyStop(this));
				}
			}
		});
		wbtnStart.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnStart.setText("Start");
		wbtnStart.setVerticalAlignment(SwingConstants.TOP);
		wbtnStart.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
		
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
		
		webPanel.add(panel);
	}
	
	public com.jajeem.survey.model.Survey getCurrentSurvey() {
		return currentSurvey;
	}

	public void setCurrentSurvey(com.jajeem.survey.model.Survey currentSurvey) {
		this.currentSurvey = currentSurvey;
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

	public void loadCurrentSurvey() {
		
	}
}
