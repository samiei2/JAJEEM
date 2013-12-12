package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.service.QuizService;

@SuppressWarnings("serial")
public class Quiz_OpenDialog extends BaseAccountFrame {
	private WebTable wbTblQuestion;
	private WebTable wbTblQuiz;
	private ArrayList<Quiz> quizList = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Quiz_OpenDialog dialog = new Quiz_OpenDialog(1, "course");
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @param actionListener
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Quiz_OpenDialog(final int id, final String type) {
		setTitle("Quizzes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Quiz_OpenDialog.class.getResource("/icons/noa_en/quiz.png")));
		setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				QuizService qs = new QuizService();
				DefaultTableModel model = (DefaultTableModel) wbTblQuiz
						.getModel();
				try {
					ArrayList<Quiz> list = null;
					if (type.equals("course")) {
						list = qs.listByCourseId(id);
					} else if (type.equals("instructor")) {
						list = qs.listByInstructorId(id);
					}

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
					JajeemExcetionHandler.logError(e);
					e.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		setBounds(100, 0, 686, 516);
		setLocationByPlatform(true);

		WebPanel webPanel = new WebPanel();
		webPanel.setOpaque(false);
		getMainContentPane().add(webPanel, BorderLayout.CENTER);

		CustomAccountButton wbtnOk = new CustomAccountButton("/icons/noa_en/accountokbutton.png");
		wbtnOk.setUndecorated(true);
		wbtnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		WebPanel webPanel_1 = new WebPanel();
		webPanel_1.setOpaque(false);
		webPanel_1
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(
			gl_webPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_webPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(wbtnOk, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(webPanel_1, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_webPanel.setVerticalGroup(
			gl_webPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_webPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wbtnOk, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(13, Short.MAX_VALUE))
		);

		WebScrollPane webScrollPane = new WebScrollPane((Component) null);

		WebScrollPane webScrollPane_1 = new WebScrollPane((Component) null);
		GroupLayout gl_webPanel_1 = new GroupLayout(webPanel_1);
		gl_webPanel_1.setHorizontalGroup(gl_webPanel_1.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_webPanel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webScrollPane, GroupLayout.DEFAULT_SIZE,
								239, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(webScrollPane_1,
								GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
		gl_webPanel_1.setVerticalGroup(gl_webPanel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel_1
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_webPanel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webScrollPane,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 601,
												Short.MAX_VALUE)
										.addComponent(webScrollPane_1,
												Alignment.TRAILING,
												GroupLayout.DEFAULT_SIZE, 601,
												Short.MAX_VALUE)).addGap(14)));

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

		webScrollPane.setViewportView(wbTblQuiz);
		webPanel_1.setLayout(gl_webPanel_1);
		webPanel.setLayout(gl_webPanel);
		setVisible(true);
	}

	public Quiz getValue() {
		return null;
	}
}
