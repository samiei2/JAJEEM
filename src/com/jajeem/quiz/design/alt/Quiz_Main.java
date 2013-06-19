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

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.jajeem.command.service.ClientService;
import com.jajeem.quiz.model.Question;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.model.Run;
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
	private boolean eventsEnabled;
	private Quiz_FirstPage firstPage;
	private Quiz_SecondPage secondPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quiz_Main frame = new Quiz_Main();
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

		WebPanel webPanel = new WebPanel();
		webScrollPane.setViewportView(webPanel);
		webPanel.setLayout(new CardLayout(0, 0));
		webPanel_2.setLayout(gl_webPanel_2);

		firstPage = new Quiz_FirstPage();
		secondPage = new Quiz_SecondPage();

		webPanel.add(firstPage, "first");
		webPanel.add(secondPage, "second");

		wbtnNew = new WebButton();
		wbtnNew.setIcon(new ImageIcon(Quiz_Main.class
				.getResource("/com/jajeem/images/Addx16.png")));

		wbtnOpen = new WebButton();

		wbtnSave = new WebButton();

		wbtnSaveResults = new WebButton();

		wbtnStart = new WebButton();

		wbtnContent = new WebButton();
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
				currentRun = new Run();
				currentRun.setQuiz(new Quiz());
				currentRun.getQuiz().addQuestion(new Question());

				currentRun
						.setInstructorId(com.jajeem.util.Session.instructorId);
				currentRun.getQuiz().setInstructorId(
						com.jajeem.util.Session.instructorId);
				currentRun.getQuiz().getQuestionList().get(0)
						.setInstructorId(com.jajeem.util.Session.instructorId);

				((DefaultTableModel) firstPage.getWebQuestionListPanel()
						.getWebTable().getModel()).addRow(new Object[] { 1,
						"Single Choice", 0, "" });
				ListSelectionModel m_modelSelection = firstPage
						.getWebQuestionListPanel().getWebTable()
						.getSelectionModel();
				m_modelSelection.setSelectionInterval(0, 0);
			}
		});
		wbtnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		wbtnContent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		wbtnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		wbtnSaveResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		wbtnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		wbtnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}
}
