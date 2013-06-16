package com.jajeem.survey.design;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;

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
import com.jajeem.core.model.Instructor;
import com.jajeem.events.SurveyEvent;
import com.jajeem.survey.model.Question;
import com.jajeem.survey.model.Survey;
import com.jajeem.survey.model.Run;
import com.jajeem.survey.service.ResultService;
import com.jajeem.survey.service.SurveyService;
//import com.jajeem.survey.service.ResultService;
import com.jajeem.room.model.Session;
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
	private SurveyTab_1 panel_bottom_1;
	private SurveyTab_2 panel_bottom_2;
	private WebPanel panel_bottom_3;
	private WebButton wbtnStart;
	private WebButton wbtnContent;
	private WebButton wbtnOpen;
	private WebButton wbtnSave;
	private WebButton wbtnSaveResults;
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
		setTitle("Survey");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SurveyMain.class.getResource("/com/jajeem/images/survey.png")));
		
		setBounds(100, 0, 1200, 780);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame = this;
		
		WebPanel webPanel = new WebPanel();
		getContentPane().add(webPanel, BorderLayout.CENTER);
		
		
		new Config();
		ClientService clientService2 = null;
		try {
			clientService2 = new ClientService(Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("surveyport")));
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
		
		panel_bottom_1 = new SurveyTab_1(this);
		setTablemodel((DefaultTableModel)panel_bottom_1.getQuestionListPanel().getWebTable().getModel());
		panel_bottom_2 = new SurveyTab_2();
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
					int i = JOptionPane.showConfirmDialog(null, "If you leave this page the survey will stop and results will be lost.Are you sure you want to continue?");
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
					wbtnSaveResults.setVisible(false);
					wbtnStart.setText("Start");
					wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
					wbtnStart.setEnabled(true);
					panel_bottom_2.ClearSurvey();
				}
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
		
		wbtnOpen = new WebButton();
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SurveyOpenDialog open = new SurveyOpenDialog(frame);
				open.setVisible(true);
			}
		});
		wbtnOpen.setVerticalTextPosition(SwingConstants.BOTTOM);
		wbtnOpen.setVerticalAlignment(SwingConstants.TOP);
		wbtnOpen.setText("Open");
		wbtnOpen.setHorizontalTextPosition(SwingConstants.CENTER);
		wbtnOpen.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/folder_green_open.png")));
		
		wbtnSave = new WebButton();
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
				int i = JOptionPane.showConfirmDialog(null, "If you leave this page the survey will stop and results will be lost.Are you sure you want to continue?");
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
				wbtnSaveResults.setVisible(false);
				//wbtnSaveResults.setVisible(false);
				wbtnStart.setText("Start");
				wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
				wbtnStart.setEnabled(true);
				panel_bottom_2.ClearSurvey();
				//Broadcast survey stop
				
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
					if(panel_bottom_1.getQuestionListPanel().getWebTable().getRowCount() == 0){
						JOptionPane.showMessageDialog(null, "At least one question is required for the survey to start!");
						return;
					}
					
					for (int i = 0; i < currentSurvey.getQuestionList().size(); i++) {
						Question question = currentSurvey.getQuestionList().get(i);
						if(question.getTitle().equals("")){
							JOptionPane.showMessageDialog(null, "Question "+(i+1)+" has no title.Please enter a title for it.");
							return;
						}
						if(question.getAnswer1().equals("") && question.getAnswer2().equals("") && question.getAnswer3().equals("") 
								&& question.getAnswer4().equals("") && question.getAnswer5().equals("") && question.getType()!=2){
							JOptionPane.showMessageDialog(null, "No answer is entered for the question "+(i+1)+".Please enter at least one.");
							return;
						}
					}
					
					wbtnSaveResults.setVisible(true);
					wbtnSave.setEnabled(false);
					wbtnOpen.setEnabled(false);
					wbtnSaveResults.setVisible(true);
					wbtnStart.setText("Stop");
					wbtnContent.setEnabled(true);
					wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/stop-red.png")));
					CardLayout cl = (CardLayout)(cards.getLayout());
			        cl.show(cards, "Page 2");
			        pageNumber=2;
			        panel_bottom_2.LoadSurvey(currentSurvey);
			        //TODO Code must be changed
			        try {
			        	Run run = new Run();
			        	run.setSurvey(currentSurvey);
			        	run.setInstructor(getCurrentInstructor());
			        	run.setSession(getCurrentSession());
			        	run.setStart(System.currentTimeMillis());
			        	//SurveyWindow wind =new SurveyWindow(run);
			        	
			        	new Config();
						ServerService serv = new ServerService();
						StartSurveyCommand cmd = new StartSurveyCommand(InetAddress.getLocalHost().getHostAddress(),Config.getParam("broadcastingIp"), Integer.parseInt(Config.getParam("port")));
						//StartSurveyCommand cmd = new StartSurveyCommand("","127.0.0.1", 9090);
						cmd.setServer(InetAddress.getLocalHost().getHostAddress());
						cmd.setRun(run);
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
					wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
					wbtnStart.setEnabled(false);
					//surveyEvent.fireStopEvent(new SurveyStop(this));
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
		wbtnStart.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/start.png")));
		
		wbtnSaveResults = new WebButton();
		wbtnSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Run> results = panel_bottom_2.getRunResults();
				ResultService service = new ResultService();
				service.create(panel_bottom_2.getSurveyResponse(), results);
			}
		});
		wbtnSaveResults.setIcon(new ImageIcon(SurveyMain.class.getResource("/com/jajeem/images/document-save-as.png")));
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
		panel_bottom_1.clear();
		panel_bottom_1.loadCurrentSurvey(currentSurvey);
	}
}
