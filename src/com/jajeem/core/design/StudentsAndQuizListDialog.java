package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.alee.laf.button.WebButton;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.core.service.StudentCourseService;
import com.jajeem.exception.JajeemExcetionHandler;
import com.jajeem.quiz.model.Quiz;
import com.jajeem.quiz.service.QuizService;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion.Setting;

public class StudentsAndQuizListDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			StudentsAndQuizListDialog dialog = new StudentsAndQuizListDialog(new Course(),true
					,0,"course");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public StudentsAndQuizListDialog(final Course course, boolean isAdmin,int id,String type) {
		setTitle("Course Details");
		
		new Config();
		new i18n();
		
		setBounds(100, 100, 710, 622);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		WebPanel webPanel = new WebPanel();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(webPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(webPanel, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
		);
		
		WebTabbedPane webTabbedPane = new WebTabbedPane();
		webPanel.add(webTabbedPane, BorderLayout.CENTER);
		try {
			webTabbedPane.add("Students",new StudentList(course, isAdmin));
			webTabbedPane.add("Quizes",new QuizList(id, type));
		} catch (Exception e) {
			e.printStackTrace();
		}
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				WebButton okButton = new WebButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
			}
		}
		setVisible(true);
	}
}

class StudentList extends WebPanel
{
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private EventList<com.jajeem.core.model.Student> studentList = new BasicEventList<com.jajeem.core.model.Student>();
	private EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel;
	private Course course;
	private StudentCourseService studentCourseService = new StudentCourseService();
	
	public StudentList(final Course course, boolean isAdmin) throws Exception{
		this.course = course;
		setVisible(true);
		setBounds(400, 100, 610, 500);
		setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		final StudentList frame = this;
		add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				buttonPane.add(panel);
				{
					WebButton addButton = new WebButton("Add");
					if (isAdmin) {
						panel.add(addButton);
					}
					addButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							try {
								new StudentDialog(frame);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});
				}
				{
					WebButton deleteButton = new WebButton("Delete");
					if (isAdmin) {
						panel.add(deleteButton);
					}
					deleteButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							int resp = WebOptionPane.showConfirmDialog(
									contentPanel,
									"Do you want to Delete selected item(s)?",
									"Confirm", WebOptionPane.YES_NO_OPTION,
									WebOptionPane.QUESTION_MESSAGE);
							if (resp == 0) {
								if (!studentSelectionModel.isSelectionEmpty()) {
									for (Student student : studentSelectionModel
											.getSelected()) {
										try {
											studentCourseService.delete(
													student.getId(),
													course.getId());
										} catch (SQLException e1) {
											e1.printStackTrace();
										}
									}
									getStudentList()
											.removeAll(
													studentSelectionModel
															.getSelected());

								}
							}
						}
					});
				}
			}
			
			
			{
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.TRAILING);
				buttonPane.add(panel);
				{
					WebButton okButton = new WebButton(
							i18n.getParam("Export to pdf"));
					panel.add(okButton);
					okButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							String timeStamp = new SimpleDateFormat(
									"yyyy-MM-dd_HH-mm").format(Calendar
									.getInstance().getTime());
							JasperReport.generate("StudentsByCourse",
									course.getName() + "_" + course.getId()
											+ "_" + timeStamp,
									Query.studentByCourse(course.getId()));
						}
					});
				}
			}
		}

		loadData();
		add(initStudent());
	}
	
	private void loadData() throws SQLException {
		ArrayList<com.jajeem.core.model.Student> studentList = studentCourseService
				.getcourseStudentsById(course.getId());
		getStudentList().addAll(studentList);
	}

	public void addStudents(EventList<Student> stuList) throws SQLException {
		studentList.addAll(stuList);
		StudentCourse sc = new StudentCourse();
		for (Student student : stuList) {
			sc.setCourseId(course.getId());
			sc.setStudentId(student.getId());
			sc.setScore(0);
			studentCourseService.create(sc);
		}
	}

	@SuppressWarnings("deprecation")
	private WebPanel initStudent() {

		final WebPanel panel = new WebPanel();
		panel.setMargin(new Insets(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable studentTable = new JTable();

		jScrollPane1.setViewportView(studentTable);
		panel.add(jScrollPane1);

		WebPanel topPanel = new WebPanel();
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		panel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel("Search: ");
		topPanel.add(filterLabel);
		WebTextField studentFilterTF = new WebTextField();
		topPanel.add(studentFilterTF);

		TextFilterator<Student> personTextFilterator = new TextFilterator<Student>() {
			@SuppressWarnings("unchecked")
			@Override
			public void getFilterStrings(java.util.List list, Student s) {
				// field you want to enable filter
				list.add(s.getId());
				list.add(s.getFirstName());
				list.add(s.getLastName());
				list.add(s.getUsername());
			}
		};

		// Table Configuration
		MatcherEditor<Student> textMatcherEditor = new TextComponentMatcherEditor<Student>(
				studentFilterTF, personTextFilterator);
		FilterList<Student> filterList = new FilterList<Student>(
				getStudentList(), textMatcherEditor);
		SortedList<Student> sortedStudent = new SortedList<Student>(filterList,
				null);
		AdvancedTableModel<Student> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedStudent,
						new StudentTableFormat());

		studentSelectionModel = new EventSelectionModel<Student>(filterList);
		studentTable.setSelectionModel(studentSelectionModel);
		studentTable.setModel(model);
		TableComparatorChooser<Student> tableSorter = TableComparatorChooser
				.install(studentTable, sortedStudent,
						TableComparatorChooser.SINGLE_COLUMN);

		StripedTableCellRenderer.installInTable(studentTable, Color.lightGray,
				Color.white, null, null);
		studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);

		return panel;
	}

	public class StudentTableFormat implements TableFormat<Student>,
			WritableTableFormat<Student> {

		public int getColumnCount() {
			return 4;
		}

		public String getColumnName(int column) {
			if (column == 0)
				return "ID";
			if (column == 1)
				return "First name";
			else if (column == 2)
				return "Last Name";
			else if (column == 3)
				return "Username";

			throw new IllegalStateException();
		}

		public Object getColumnValue(Student student, int column) {

			if (column == 0)
				return student.getId();
			if (column == 1)
				return student.getFirstName();
			else if (column == 2)
				return student.getLastName();
			else if (column == 3)
				return student.getUsername();

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Student baseObject, int column) {
			return false;
		}

		@Override
		public Student setColumnValue(Student baseObject, Object editedValue,
				int column) {
			return baseObject;
		}
	}

	public EventList<com.jajeem.core.model.Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(
			EventList<com.jajeem.core.model.Student> studentList) {
		this.studentList = studentList;
	}
}
class QuizList extends WebPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WebTable wbTblQuestion;
	private WebTable wbTblQuiz;
	private ArrayList<Quiz> quizList = new ArrayList<>();
	
	public QuizList(final int id, final String type){
		WebPanel webPanel = new WebPanel();
		add(webPanel, BorderLayout.CENTER);
		WebPanel webPanel_1 = new WebPanel();
		webPanel_1
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout gl_webPanel = new GroupLayout(webPanel);
		gl_webPanel.setHorizontalGroup(gl_webPanel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_webPanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(webPanel_1,
												GroupLayout.DEFAULT_SIZE, 617,
												Short.MAX_VALUE))
						.addContainerGap()));
		gl_webPanel.setVerticalGroup(gl_webPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_webPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(webPanel_1, GroupLayout.PREFERRED_SIZE,
								387, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addContainerGap(13, Short.MAX_VALUE)));

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
		Populate(id,type);
	}
	
	public void Populate(final int id, final String type){
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
					if (title == "" || title == null)
						title = "No Title";
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
			if (wbTblQuiz.getRowCount() != 0)
				wbTblQuiz.getSelectionModel()
						.setSelectionInterval(0, 0);
		} catch (SQLException e) {
			JajeemExcetionHandler.logError(e);
			e.printStackTrace();
		}
	}
}