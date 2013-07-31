package com.jajeem.core.design;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
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

import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.utils.SwingUtils;
import com.jajeem.core.dao.h2.InstructorDAO;
import com.jajeem.core.service.InstructorService;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;

import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.jajeem.util.*;
import javax.swing.JButton;

public class AdminPanel extends WebFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static AdminPanel frame;
	private WebPanel contentPane;

	private EventList<Course> courseList = new BasicEventList<Course>();
	private EventSelectionModel<Course> courseSelectionModel;

	private EventList<com.jajeem.core.model.Instructor> instructorList = new BasicEventList<com.jajeem.core.model.Instructor>();
	private EventSelectionModel<com.jajeem.core.model.Instructor> instructorSelectionModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new AdminPanel();
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
	 * @throws SQLException
	 */
	public AdminPanel() throws SQLException {
		setTitle("Admin Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 800, 600);
		contentPane = new WebPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		loadData();

		WebTabbedPane tabbedPane = new WebTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		WebPanel userTab = new WebPanel();
		tabbedPane.addTab("Instructors", null, userTab, null);
		userTab.setLayout(new BorderLayout(0, 0));

		WebPanel userPanel = new WebPanel();
		userTab.add(userPanel, BorderLayout.CENTER);
		userPanel.setLayout(new BorderLayout(0, 0));

		WebPanel courseTab = new WebPanel();
		tabbedPane.addTab("Courses", null, courseTab, null);
		courseTab.setLayout(new BorderLayout(0, 0));
		courseTab.add(initCourse());

		WebPanel studentTab = new WebPanel();
		tabbedPane.addTab("Students", null, studentTab, null);

	}

	private void loadData() throws SQLException {

		InstructorService instructorService = new InstructorService();
		ArrayList<com.jajeem.core.model.Instructor> instructorList = instructorService
				.list();
		getInstructorList().addAll(instructorList);
		
		RoomService rs = new RoomService();
		ArrayList<Course> courseList = rs.getCourseDAO().list();
		
		for (Course course : courseList) {
			course.setInstructor(instructorService.getById(course.getInstructorId()).getUsername());
			getCourseList().add(course);
		}

		
	}

	@SuppressWarnings("deprecation")
	private WebPanel initCourse() {

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

		WebButton addButton = new WebButton("Add");
		buttonPanel.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new AddNewCourseDialog(courseList, getInstructorList());
			}
		});

		WebButton deleteButton = new WebButton("Delete");
		buttonPanel.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int resp = WebOptionPane.showConfirmDialog(panel,
						"Do you want to Delete selected item(s)?", "Confirm",
						WebOptionPane.YES_NO_OPTION,
						WebOptionPane.QUESTION_MESSAGE);
				if (resp == 0) {
					if (!courseSelectionModel.isSelectionEmpty()) {
						RoomService rs = new RoomService();
						for (Course course : courseSelectionModel.getSelected()) {
							try {
								rs.getCourseDAO().delete(course);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						getCourseList().removeAll(
								courseSelectionModel.getSelected());

					}
				}
			}
		});

		WebButton editButton = new WebButton("Edit");
		buttonPanel.add(editButton);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!courseSelectionModel.isSelectionEmpty()) {
					Course course = courseSelectionModel.getSelected().get(0);
					new AddNewCourseDialog(courseList, course,
							courseSelectionModel.getSelected(),
							getInstructorList());
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

		JLabel filterLabel = new JLabel("Search: ");
		topPanel.add(filterLabel);
		WebTextField courseFilterTF = new WebTextField();
		topPanel.add(courseFilterTF);

		TextFilterator<Course> personTextFilterator = new TextFilterator<Course>() {
			@SuppressWarnings("unchecked")
			@Override
			public void getFilterStrings(java.util.List list, Course c) {
				// field you want to enable filter
				list.add(c.getName());
				list.add(c.getInstructor());
				list.add(c.getLevel());
				list.add(c.getDay1());
				list.add(c.getDay2());
				list.add(c.getDay3());
				list.add(c.getDay4());
				list.add(c.getDay5());
				list.add(c.getSession());
				list.add(c.getClassType());
				list.add(c.getStartDate());
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

	public class CourseTableFormat implements TableFormat<Course>,
			WritableTableFormat<Course> {

		public int getColumnCount() {
			return 8;
		}

		public String getColumnName(int column) {
			if (column == 0)
				return "ID";
			if (column == 1)
				return "Course Name";
			else if (column == 2)
				return "Instructor Name";
			else if (column == 3)
				return "Class Type";
			else if (column == 4)
				return "Level";
			else if (column == 5)
				return "Start Date";
			else if (column == 6)
				return "Sessions";
			else if (column == 7)
				return "Weekly Time";

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
			else if (column == 5)
				return course.getStartDate(); // TODO: date format
			else if (column == 6)
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

}