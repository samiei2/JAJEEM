package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.gui.AbstractTableComparatorChooser;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.jajeem.core.model.Student;
import com.jajeem.core.service.StudentCourseService;
import com.jajeem.exception.JajeemExceptionHandler;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.MultiLineCellRenderer;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;

public class StudentCourseDialog extends BaseAccountFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private EventList<Course> courseList = new BasicEventList<Course>();
	private EventSelectionModel<Course> courseSelectionModel;

	private Student student;
	private StudentCourseService studentCourseService = new StudentCourseService();

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public StudentCourseDialog(final Student student) throws Exception {
		setTitle("Courses");

		new Config();
		new i18n();

		this.student = student;
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 631, 673);
		getMainContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getMainContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			getMainContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JPanel panel = new JPanel();
				buttonPane.add(panel);
				
				CustomAccountButton quizButton = new CustomAccountButton("/icons/noa_en/accountcourse.png");
				quizButton.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent arg0) {
						if (!courseSelectionModel.isSelectionEmpty()) {
							if (courseSelectionModel.getSelected().size() > 1) {
								JOptionPane.showMessageDialog(null,
										"Please select one student.", "Message",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								Course course = courseSelectionModel.getSelected()
										.get(0);
								try {
									new StudentQuizDialog(student,course);
								} catch (Exception e1) {
									JajeemExceptionHandler.logError(e1);
									e1.printStackTrace();
								}
							}
						}
						else{
							JOptionPane.showMessageDialog(null,
									"Please select one student.", "Message",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				
				quizButton.setUndecorated(true);
				quizButton.setText(i18n.getParam("Quizes"));
				quizButton.setMargin(new Insets(0, 5, 0, 0));
				quizButton.setHorizontalTextPosition(SwingConstants.LEFT);
				quizButton.setHorizontalAlignment(SwingConstants.LEFT);
				
				GroupLayout gl_panel = new GroupLayout(panel);
				gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(quizButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(181, Short.MAX_VALUE))
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addContainerGap(24, Short.MAX_VALUE)
							.addComponent(quizButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
				);
				panel.setLayout(gl_panel);
			}
			{
				JPanel panel = new JPanel();
				buttonPane.add(panel);
				{
					CustomAccountButton okButton = new CustomAccountButton("/icons/noa_en/accountpdf.png");
					okButton.setUndecorated(true);
					GroupLayout gl_panel = new GroupLayout(panel);
					gl_panel.setHorizontalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(230)
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
					gl_panel.setVerticalGroup(
						gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
					panel.setLayout(gl_panel);
					okButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							String timeStamp = new SimpleDateFormat(
									"yyyy-MM-dd_HH-mm").format(Calendar
									.getInstance().getTime());
							JasperReport.generate("CoursesByStudent",
									student.getFullName() + "_" + timeStamp,
									Query.courseByStudent(student.getId()));
						}
					});
				}
			}
		}

		loadData();
		getMainContentPane().add(initCourse());
		pack();
	}

	private void loadData() throws SQLException {
		ArrayList<Course> courseList = studentCourseService
				.getStudentCoursesById(student.getId());
		getCourseList().addAll(courseList);
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
		TableComparatorChooser.install(courseTable, sortedCourse,
				AbstractTableComparatorChooser.SINGLE_COLUMN);

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

	public EventList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(EventList<Course> courseList) {
		this.courseList = courseList;
	}

	public class CourseTableFormat implements TableFormat<Course>,
			WritableTableFormat<Course> {

		@Override
		public int getColumnCount() {
			return 8;
		}

		@Override
		public String getColumnName(int column) {
			try {
				if (column == 0) {
					return i18n.getParam("ID");
				}
				if (column == 1) {
					return i18n.getParam("Course Name");
				} else if (column == 2) {
					return i18n.getParam("Instructor Name");
				} else if (column == 3) {
					return i18n.getParam("Class Type");
				} else if (column == 4) {
					return i18n.getParam("Level");
				} else if (column == 5) {
					return i18n.getParam("Start Date");
				} else if (column == 6) {
					return i18n.getParam("Sessions");
				} else if (column == 7) {
					return i18n.getParam("Weekly Time");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		@Override
		public Object getColumnValue(Course course, int column) {

			if (column == 0) {
				return course.getId();
			}
			if (column == 1) {
				return course.getName();
			} else if (column == 2) {
				return course.getInstructor();
			} else if (column == 3) {
				return course.getClassType();
			} else if (column == 4) {
				return course.getLevel();
			} else if (column == 5) {
				Date startDate = new Date(course.getStartDate());
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
				return dt.format(startDate);
			} else if (column == 6) {
				return course.getSession();
			} else if (column == 7) {
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
}
