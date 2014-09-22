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
import com.jajeem.quiz.service.RunService;
import com.jajeem.room.model.Course;
import com.jajeem.util.Config;
import com.jajeem.util.JasperReport;
import com.jajeem.util.MultiLineCellRenderer;
import com.jajeem.util.Query;
import com.jajeem.util.StripedTableCellRenderer;
import com.jajeem.util.i18n;

import javax.swing.LayoutStyle.ComponentPlacement;

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
		pack();
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

		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		JTable courseTable = new JTable();

		jScrollPane1.setViewportView(courseTable);

		WebPanel bottomPanel = new WebPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, 0, 0));

		WebPanel topPanel = new WebPanel();
		topPanel.setMargin(new Insets(7, 2, 7, 2));
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(topPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
						.addComponent(jScrollPane1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(topPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
		);
		
		CustomAccountButton cstmcntbtnDelete = new CustomAccountButton("/icons/noa_en/accountcourse.png");
		cstmcntbtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RunService runService = new RunService();
				if (!courseSelectionModel.isSelectionEmpty()) {
					if (courseSelectionModel.getSelected().size() > 1) {
						JOptionPane.showMessageDialog(null,
								"Please select one quiz.", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						Run currentRun = courseSelectionModel.getSelected()
								.get(0);
						try {
							runService.delete(currentRun);
							getCourseList().remove(courseSelectionModel.getSelected().get(0));
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null,
							"Please select one quiz.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		cstmcntbtnDelete.setUndecorated(true);
		cstmcntbtnDelete.setText("Delete");
		cstmcntbtnDelete.setMargin(new Insets(0, 5, 0, 0));
		cstmcntbtnDelete.setHorizontalTextPosition(SwingConstants.LEFT);
		cstmcntbtnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(cstmcntbtnDelete, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(479, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(cstmcntbtnDelete, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);

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
		int localCounter = 0;

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
				DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String dateFormatted = formatter.format(date);
				return dateFormatted;
			} else if (column == 3) {
				Date date = new Date(course.getEnd());
				DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
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