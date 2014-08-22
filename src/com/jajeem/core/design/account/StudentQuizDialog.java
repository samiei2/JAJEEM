package com.jajeem.core.design.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
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
import com.jajeem.quiz.model.Run;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.MultiLineCellRenderer;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;

public class StudentQuizDialog extends BaseAccountFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private EventList<Run> courseList = new BasicEventList<Run>();
	private EventSelectionModel<Run> courseSelectionModel;

	private Student student;
	private Course course;
	private StudentCourseService studentCourseService = new StudentCourseService();
	WebTextField courseFilterTF = new WebTextField();

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */
	public StudentQuizDialog(final Student student,Course course) throws Exception {
		setTitle("Quiz Results");

		new Config();
		new i18n();

		this.student = student;
		this.course = course;
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 631, 673);
		getMainContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getMainContentPane().add(contentPanel, BorderLayout.CENTER);

		loadData();
		getMainContentPane().add(initCourse());
	}

	private void loadData() throws SQLException {
		ArrayList<Run> courseList = studentCourseService
				.getStudentQuizesById(student.getId(),course.getId());
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

		JLabel filterLabel = new JLabel(i18n.getParam("Quiz Results") + ": ");
		filterLabel.setVisible(false);
		topPanel.add(filterLabel);

		TextFilterator<Run> personTextFilterator = new TextFilterator<Run>() {
			@SuppressWarnings("unchecked")
			@Override
			public void getFilterStrings(java.util.List list, Run c) {
				// field you want to enable filter
				SimpleDateFormat dt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				
				list.add(c.getId());
				list.add(c.getInstructor().getFullName());

				Date start = new Date(c.getStart());
				list.add(dt.format(start));
				Date end = new Date(c.getEnd());
				list.add(dt.format(end));
				list.add(c.getScore());
			}
		};
		MatcherEditor<Run> textMatcherEditor = new TextComponentMatcherEditor<Run>(
				courseFilterTF, personTextFilterator);
		FilterList<Run> filterList = new FilterList<Run>(getCourseList(),
				textMatcherEditor);
		SortedList<Run> sortedCourse = new SortedList<Run>(filterList,
				null);
		AdvancedTableModel<Run> model = GlazedListsSwing
				.eventTableModelWithThreadProxyList(sortedCourse,
						new CourseTableFormat());

		courseSelectionModel = new EventSelectionModel<Run>(filterList);
		courseTable.setSelectionModel(courseSelectionModel);
		courseTable.setModel(model);
		TableComparatorChooser.install(courseTable, sortedCourse,
				AbstractTableComparatorChooser.SINGLE_COLUMN);

		MultiLineCellRenderer multiLineRenderer = new MultiLineCellRenderer(
				SwingConstants.LEFT, SwingConstants.CENTER);

		TableColumnModel tcm = courseTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(10);
		tcm.getColumn(4).setPreferredWidth(10);
		courseTable.setRowHeight(60);

		StripedTableCellRenderer.installInTable(courseTable, Color.lightGray,
				Color.white, null, null);

		return panel;
	}

	public EventList<Run> getCourseList() {
		return courseList;
	}

	public void setCourseList(EventList<Run> courseList) {
		this.courseList = courseList;
	}

	public class CourseTableFormat implements TableFormat<Run>,
			WritableTableFormat<Run> {

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public String getColumnName(int column) {
			try {
				if (column == 0) {
					return i18n.getParam("ID");
				}
				if (column == 1) {
					return i18n.getParam("Instructor Name");
				} else if (column == 2) {
					return i18n.getParam("Start Time");
				} else if (column == 3) {
					return i18n.getParam("End Time");
				} else if (column == 4) {
					return i18n.getParam("Score");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			throw new IllegalStateException();
		}

		@Override
		public Object getColumnValue(Run course, int column) {

			if (column == 0) {
				return course.getId();
			}
			if (column == 1) {
				return course.getInstructor().getFullName();
			} else if (column == 2) {
				Date date = new Date(course.getStart());
				DateFormat formatter = new SimpleDateFormat("YYYY/MM/DD HH:mm");
				String dateFormatted = formatter.format(date);
				return dateFormatted;
			} else if (column == 3) {
				Date date = new Date(course.getEnd());
				DateFormat formatter = new SimpleDateFormat("YYYY/MM/DD HH:mm");
				String dateFormatted = formatter.format(date);
				return dateFormatted;
			} else if (column == 4) {
				return course.getScore();
			} 

			throw new IllegalStateException();
		}

		@Override
		public boolean isEditable(Run baseObject, int column) {
			return false; // which columns to be editable?
		}

		@Override
		public Run setColumnValue(Run baseObject, Object editedValue,
				int column) {
			return baseObject;
		}
	}
}