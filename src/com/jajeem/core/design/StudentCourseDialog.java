package com.jajeem.core.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

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
import com.alee.laf.text.WebTextField;
import com.alee.managers.tooltip.TooltipManager;
import com.jajeem.core.design.AdminPanel.StudentTableFormat;
import com.jajeem.core.model.Instructor;
import com.jajeem.core.model.Student;
import com.jajeem.core.model.StudentCourse;
import com.jajeem.core.service.InstructorService;
import com.jajeem.core.service.StudentCourseService;
import com.jajeem.core.service.StudentService;
import com.jajeem.room.model.Course;
import com.jajeem.room.service.RoomService;
import com.jajeem.util.StripedTableCellRenderer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentCourseDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private EventList<com.jajeem.core.model.Student> studentList = new BasicEventList<com.jajeem.core.model.Student>();
	private EventSelectionModel<com.jajeem.core.model.Student> studentSelectionModel;
	private Course course;
	private StudentCourseService studentCourseService = new StudentCourseService();

	/**
	 * Create the dialog.
	 * 
	 * @throws SQLException
	 */
	public StudentCourseDialog(final Course course) throws SQLException {
		setTitle("Students");
		this.course = course;
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 610, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		final StudentCourseDialog frame = this;
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JPanel panel = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				buttonPane.add(panel);
				{
					JButton addButton = new JButton("Add");
					panel.add(addButton);
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
					JButton deleteButton = new JButton("Delete");
					panel.add(deleteButton);
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
			}
		}

		loadData();
		getContentPane().add(initStudent());
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
