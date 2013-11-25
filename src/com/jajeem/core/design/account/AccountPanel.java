package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

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
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.core.service.InstructorService;
import com.jajeem.core.service.StudentCourseService;
import com.jajeem.core.service.StudentService;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.Config;
import com.jajeem.util.MultiLineCellRenderer;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;

public class AccountPanel extends WebFrame {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private static AccountPanel frame;
	private WebPanel contentPane;
	Instructor instructorModel;
	Course courseModel;

	private EventList<Course> courseList = new BasicEventList<Course>();
	private EventSelectionModel<Course> courseSelectionModel;

	private EventList<com.jajeem.core.model.Instructor> instructorList = new BasicEventList<com.jajeem.core.model.Instructor>();
	private EventSelectionModel<com.jajeem.core.model.Instructor> instructorSelectionModel;

	private EventList<com.jajeem.core.model.Student> studentList = new BasicEventList<com.jajeem.core.model.Student>();
	private EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Config();
					new i18n();

					Instructor ins = new Instructor();
					ins.setId(1);
					Course co = new Course();
					co.setId(1);
					frame = new AccountPanel(ins, co);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public AccountPanel(Instructor ins, Course co) throws Exception {

		instructorModel = ins;
		courseModel = co;

		setTitle(i18n.getParam("My account" + " - "
				+ instructorModel.getFullName()));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 50, 800, 600);
		contentPane = new WebPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		loadData();

		WebTabbedPane tabbedPane = new WebTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		WebPanel courseTab = new WebPanel();
		tabbedPane.addTab(i18n.getParam("Courses"), null, courseTab, null);
		courseTab.setLayout(new BorderLayout(0, 0));
		courseTab.add(initCourse());

		WebPanel studentTab = new WebPanel();
		tabbedPane.addTab(i18n.getParam("Students"), null, studentTab, null);
		studentTab.setLayout(new BorderLayout(0, 0));
		studentTab.add(initStudent());
		
		setVisible(true);

	}

	private void loadData() throws SQLException {

		InstructorService instructorService = new InstructorService();

		RoomService rs = new RoomService();
		ArrayList<Course> courseList = rs
				.getCoursesByInstructorId(instructorModel.getId());

		StudentCourseService studentCourseService = new StudentCourseService();
		ArrayList<com.jajeem.core.model.Student> studentList = studentCourseService
				.getcourseStudentsById(courseModel.getId());
		getStudentList().addAll(studentList);

		for (Course course : courseList) {
			Instructor ins = instructorService
					.getById(course.getInstructorId());

			if (ins != null) {
				course.setInstructor(instructorService.getById(
						course.getInstructorId()).getUsername());
				getCourseList().add(course);
			} else {
				course.setInstructor("");
				getCourseList().add(course);
			}
		}

	}

	@SuppressWarnings("deprecation")
	private WebPanel initCourse() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setMargin(new Insets(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable courseTable = new JTable();

		jScrollPane1.setViewportView(courseTable);
		panel.add(jScrollPane1);

		WebPanel bottomPanel = new WebPanel();
		panel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		bottomPanel.add(buttonPanel);

		WebButton studentButton = new WebButton(i18n.getParam("Students"));
		buttonPanel.add(studentButton);
		studentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						try {

							boolean isAdmin = false;
							if (instructorModel.getUsername().equals("admin")) {
								isAdmin = true;
							}

							new CourseStudentDialog(course, isAdmin);

						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		WebButton instructorButton = new WebButton(i18n.getParam("Instructors"));
		// buttonPanel.add(instructorButton);
		instructorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						try {
							new InstructorCourseDialog(course);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		WebButton quizButton = new WebButton(i18n.getParam("Quizzes"));
		buttonPanel.add(quizButton);
		quizButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						WebOptionPane.showMessageDialog(frame,
								"Please select one course.", "Message",
								WebOptionPane.INFORMATION_MESSAGE);
					} else {
						Course course = courseSelectionModel.getSelected().get(
								0);
						new Quiz_OpenDialog(course.getId(), "course");
					}
				}
			}
		});

		JPanel paginationPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		/*
		 * 
		 * Pagination, for now we will comment it.
		 * 
		 * 
		 * WebButton previousButton = new WebButton("Previous");
		 * previousButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * } }); paginationPanel.add(previousButton);
		 * 
		 * WebButton nextButton = new WebButton("Next");
		 * nextButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * 
		 * } }); paginationPanel.add(nextButton);
		 */

		WebPanel topPanel = new WebPanel();
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		panel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel(i18n.getParam("Search") + ": ");
		topPanel.add(filterLabel);
		WebTextField courseFilterTF = new WebTextField();
		topPanel.add(courseFilterTF);

		TextFilterator<Course> personTextFilterator = new TextFilterator<Course>() {
			@SuppressWarnings("unchecked")
			@Override
			public void getFilterStrings(java.util.List list, Course c) {
				// field you want to enable filter
				list.add(c.getId());
				list.add(c.getName());
				list.add(c.getInstructor());
				list.add(c.getLevel());

				list.add(c.getDay1() + ", " + c.getStartTime1() + "-"
						+ c.getEndTime1());
				list.add(c.getDay2() + ", " + c.getStartTime2() + "-"
						+ c.getEndTime2());
				list.add(c.getDay3() + ", " + c.getStartTime3() + "-"
						+ c.getEndTime3());
				list.add(c.getDay4() + ", " + c.getStartTime4() + "-"
						+ c.getEndTime4());
				list.add(c.getDay5() + ", " + c.getStartTime5() + "-"
						+ c.getEndTime1());

				list.add(c.getSession());
				list.add(c.getClassType());

				Date startDate = new Date(c.getStartDate());
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				list.add(dt.format(startDate));
			}
		};

		// Table Configuration
		MatcherEditor<Course> textMatcherEditor = new TextComponentMatcherEditor<Course>(
				courseFilterTF, personTextFilterator);
		FilterList<Course> filterList = new FilterList<Course>(getCourseList(),
				textMatcherEditor);
		SortedList<Course> sortedCourse = new SortedList<Course>(filterList,
				null);
		AdvancedTableModel<Course> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedCourse,
						new CourseTableFormat());

		courseSelectionModel = new EventSelectionModel<Course>(filterList);
		courseTable.setSelectionModel(courseSelectionModel);
		courseTable.setModel(model);
		TableComparatorChooser<Course> tableSorter = TableComparatorChooser
				.install(courseTable, sortedCourse,
						TableComparatorChooser.SINGLE_COLUMN);

		MultiLineCellRenderer multiLineRenderer = new MultiLineCellRenderer(
				SwingConstants.LEFT, SwingConstants.CENTER);

		TableColumnModel tcm = courseTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(10);
		tcm.getColumn(4).setPreferredWidth(10);
		tcm.getColumn(6).setPreferredWidth(10);
		tcm.getColumn(7).setCellRenderer(multiLineRenderer);
		courseTable.setRowHeight(60);

		StripedTableCellRenderer.installInTable(courseTable, Color.lightGray,
				Color.white, null, null);

		return panel;
	}

	@SuppressWarnings("deprecation")
	private WebPanel initStudent() throws Exception {

		final WebPanel panel = new WebPanel();
		panel.setMargin(new Insets(5, 5, 5, 5));
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable studentTable = new JTable();

		jScrollPane1.setViewportView(studentTable);
		panel.add(jScrollPane1);

		WebPanel bottomPanel = new WebPanel();
		panel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel buttonPanel = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) buttonPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		bottomPanel.add(buttonPanel);

		JPanel paginationPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) paginationPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		bottomPanel.add(paginationPanel);

		WebPanel topPanel = new WebPanel();
		topPanel.setMargin(new Insets(7, 2, 7, 2));
		panel.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		JLabel filterLabel = new JLabel(i18n.getParam("Search") + ": ");
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

	public class CourseTableFormat implements TableFormat<Course>,
			WritableTableFormat<Course> {

		public int getColumnCount() {
			return 8;
		}

		public String getColumnName(int column) {
			try {
				if (column == 0)
					return i18n.getParam("ID");
				if (column == 1)
					return i18n.getParam("Course Name");
				else if (column == 2)
					return i18n.getParam("Instructor Name");
				else if (column == 3)
					return i18n.getParam("Class Type");
				else if (column == 4)
					return i18n.getParam("Level");
				else if (column == 5)
					return i18n.getParam("Start Date");
				else if (column == 6)
					return i18n.getParam("Sessions");
				else if (column == 7)
					return i18n.getParam("Weekly Time");
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		public Object getColumnValue(Course course, int column) {

			if (column == 0)
				return course.getId();
			if (column == 1)
				return course.getName();
			else if (column == 2)
				return course.getInstructor();
			else if (column == 3)
				return course.getClassType();
			else if (column == 4)
				return course.getLevel();
			else if (column == 5) {
				Date startDate = new Date(course.getStartDate());
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				return dt.format(startDate);
			} else if (column == 6)
				return course.getSession();
			else if (column == 7) {
				ArrayList<String> daysCell = new ArrayList<String>();

				for (int i = 0; i < 5; i++) {
					if (i == 0 && !course.getDay1().equals("")) {
						daysCell.add((course.getDay1() + ", "
								+ course.getStartTime1() + "-" + course
								.getEndTime1()));
					} else if (i == 1 && !course.getDay2().equals("")) {
						daysCell.add((course.getDay2() + ", "
								+ course.getStartTime2() + "-" + course
								.getEndTime2()));
					} else if (i == 2 && !course.getDay3().equals("")) {
						daysCell.add((course.getDay3() + ", "
								+ course.getStartTime3() + "-" + course
								.getEndTime3()));
					} else if (i == 3 && !course.getDay4().equals("")) {
						daysCell.add((course.getDay4() + ", "
								+ course.getStartTime4() + "-" + course
								.getEndTime4()));
					} else if (i == 4 && !course.getDay5().equals("")) {
						daysCell.add((course.getDay5() + ", "
								+ course.getStartTime5() + "-" + course
								.getEndTime5()));
					}
				}

				return daysCell.toArray();
			}

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Course baseObject, int column) {
			return false; // which columns to be editable?
		}

		@Override
		public Course setColumnValue(Course baseObject, Object editedValue,
				int column) {
			return baseObject;
		}
	}

	public class StudentTableFormat implements TableFormat<Student>,
			WritableTableFormat<Student> {

		public int getColumnCount() {
			return 4;
		}

		public String getColumnName(int column) {
			try {
				if (column == 0)
					return i18n.getParam("ID");
				if (column == 1)
					return i18n.getParam("First name");
				else if (column == 2)
					return i18n.getParam("Last Name");
				else if (column == 3)
					return i18n.getParam("Username");

			} catch (Exception e) {
				e.printStackTrace();
			}
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
			if (column == 1) {
				baseObject.setFirstName((String) editedValue);
			} else if (column == 2) {
				baseObject.setLastName((String) editedValue);
			} else if (column == 3) {
				baseObject.setUsername((String) editedValue);
			} else if (column == 4) {
				baseObject.setPassword((String) editedValue);
			}

			StudentService stuService = new StudentService();
			try {
				stuService.update(baseObject);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return baseObject;
		}
	}

	public EventList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(EventList<Course> courseList) {
		this.courseList = courseList;
	}

	public EventList<com.jajeem.core.model.Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(
			EventList<com.jajeem.core.model.Instructor> instructorList) {
		this.instructorList = instructorList;
	}

	public EventSelectionModel<com.jajeem.core.model.Instructor> getInstructorSelectionModel() {
		return instructorSelectionModel;
	}

	public void setInstructorSelectionModel(
			EventSelectionModel<com.jajeem.core.model.Instructor> instructorSelectionModel) {
		this.instructorSelectionModel = instructorSelectionModel;
	}

	public EventList<com.jajeem.core.model.Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(
			EventList<com.jajeem.core.model.Student> studentList) {
		this.studentList = studentList;
	}

	public EventSelectionModel<com.jajeem.core.model.Student> getStudentSelectionModel() {
		return studentSelectionModel;
	}

	public void setStudentSelectionModel(
			EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel) {
		this.studentSelectionModel = studentSelectionModel;
	}

}