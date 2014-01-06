package com.jajeem.quiz.design.alt;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.alee.laf.scroll.WebScrollPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alee.laf.table.WebTable;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.util.WindowResizeAdapter;

public class Quiz_OpenDialog extends BaseQuizOpenFrame {

	private JPanel contentPane;
	private WebTable wbTblQuestion;
	private WebTable wbTblQuiz;
	private CustomQuizButton wbtnOpen;
	private CustomQuizButton wbtnCancel;
	
	private ArrayList<Quiz> quizList = new ArrayList<>();
	private Quiz_Main parentFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Quiz_OpenDialog frame = new Quiz_OpenDialog(null);
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
	public Quiz_OpenDialog(Quiz_Main frame) {
		WindowResizeAdapter.install(this, SwingConstants.SOUTH_EAST);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Quiz_OpenDialog.class.getResource("/icons/noa_en/quiz.png")));
		parentFrame = frame;
		setAlwaysOnTop(true);
		setLocationByPlatform(true);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		GroupLayout groupLayout = new GroupLayout(getMainContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE));

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panel_1,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														390, Short.MAX_VALUE)
												.addComponent(
														panel_2,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														390, Short.MAX_VALUE))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 253,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 59,
								Short.MAX_VALUE).addContainerGap()));

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE,
								183, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webScrollPane_1,
								GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webScrollPane_1,
												GroupLayout.DEFAULT_SIZE, 231,
												Short.MAX_VALUE)
										.addComponent(webScrollPane,
												GroupLayout.DEFAULT_SIZE, 231,
												Short.MAX_VALUE))
						.addContainerGap()));

		wbTblQuestion = new WebTable();
		wbTblQuestion.setEditable(false);
		wbTblQuestion.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "Question Title", "Point" }));
		wbTblQuestion.getColumnModel().getColumn(0).setPreferredWidth(33);
		wbTblQuestion.getColumnModel().getColumn(0).setMaxWidth(33);
		wbTblQuestion.getColumnModel().getColumn(2).setPreferredWidth(53);
		wbTblQuestion.getColumnModel().getColumn(2).setMaxWidth(53);
		webScrollPane_1.setViewportView(wbTblQuestion);

		wbTblQuiz = new WebTable();
		wbTblQuiz.setEditable(false);
		wbTblQuiz.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "#", "Direction", "# of Questions" }));
		wbTblQuiz.getColumnModel().getColumn(0).setPreferredWidth(33);
		wbTblQuiz.getColumnModel().getColumn(0).setMaxWidth(33);
		wbTblQuiz.getColumnModel().getColumn(2).setPreferredWidth(93);
		wbTblQuiz.getColumnModel().getColumn(2).setMaxWidth(93);
		webScrollPane.setViewportView(wbTblQuiz);
		panel_1.setLayout(gl_panel_1);

		wbtnOpen = new CustomQuizButton("/icons/noa_en/quizopenbutton.png");
		wbtnOpen.setUndecorated(true);

		wbtnCancel = new CustomQuizButton("/icons/noa_en/quizcancelbutton.png");
		wbtnCancel.setUndecorated(true);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(286, Short.MAX_VALUE)
					.addComponent(wbtnCancel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(wbtnCancel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(wbtnOpen, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		getMainContentPane().setLayout(groupLayout);

		initEvents();
		pack();
	}

	private void initEvents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				QuizService qs = new QuizService();
				DefaultTableModel model = (DefaultTableModel) wbTblQuiz
						.getModel();
				try {
					ArrayList<Quiz> list = qs.listByCourseId(parentFrame
							.getCurrentRun().getCourseId());
					if (list != null) {
						// quizList.addAll(list);
						for (int i = 0; i < list.size(); i++) {
							Quiz z = list.get(i);
							quizList.add(z);
							String title = z.getTitle();
							if (title == "" || title == null) {
								title = "No Title";
							}
							if (wbTblQuiz.getRowCount() == 0) {
								model.addRow(new Object[] { 1, title,
										z.getQuestionList().size() });
							} else {
								model.addRow(new Object[] {
										Integer.parseInt(String.valueOf(model.getValueAt(
												wbTblQuiz.getRowCount() - 1, 0))) + 1,
										title, z.getQuestionList().size() });
							}
						}
					}
					if (wbTblQuiz.getRowCount() != 0) {
						wbTblQuiz.getSelectionModel()
								.setSelectionInterval(0, 0);
					}
				} catch (SQLException e) {
					JajeemExceptionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});

		wbtnOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parentFrame.setCurrentQuiz(quizList.get(wbTblQuiz
						.getSelectedRow()));
				parentFrame.loadCurrentQuiz();
				dispose();
			}
		});

		wbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		wbTblQuiz.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						Quiz quiz = quizList.get(wbTblQuiz.getSelectedRow());
						DefaultTableModel model = (DefaultTableModel) wbTblQuestion
								.getModel();
						model.getDataVector().removeAllElements();
						model.fireTableDataChanged();
						for (int i = 0; i < quiz.getQuestionList().size(); i++) {
							model.addRow(new Object[] {
									wbTblQuestion.getRowCount() == 0 ? 1
											: Integer.parseInt(String.valueOf(model
													.getValueAt(wbTblQuestion
															.getRowCount() - 1,
															0))) + 1,
									quiz.getQuestionList().get(i).getTitle(),
									quiz.getQuestionList().get(i).getPoint() });
						}
						wbTblQuestion.getSelectionModel().setSelectionInterval(
								0, 0);
					}
		});
		
		((CustomQuizButton)getCloseButton()).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

}
